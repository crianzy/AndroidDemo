package com.imczy.p2pdemo;

import android.content.Context;
import android.util.Log;

import com.imczy.common_util.db.SettingUtils;
import com.imczy.common_util.log.LogUtil;
import com.imczy.p2pdemo.message.Message;
import com.imczy.p2pdemo.message.ReceiverMessage;
import com.imczy.p2pdemo.message.SendMessage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chenzhiyong on 15/11/2.
 */
public class P2PClient implements Runnable {

    public static final String TAG = "P2PClient";
    public static final int REPEAT_CACHE_SIZE = 3;

    private OnReceiverMessageListener mOnReceiverMessageListener;
    private Context mContext;

    protected DatagramSocket mClient;
    protected int mAppid = 1;
    protected byte[] mUuid;

    protected long mLastSent = 0;
    protected long mLastReceived = 0;

    protected int mRemotePort = Constant.DANMAKU_SERVER_PORT;
    protected String mRemoteAddress = null;
    private InetSocketAddress mServerSocketAddress;

    protected ConcurrentLinkedQueue<ReceiverMessage> mMessageQueue = new ConcurrentLinkedQueue<ReceiverMessage>();
    protected AtomicLong mQueueIn = new AtomicLong(0);
    protected AtomicLong mQueueOut = new AtomicLong(0);

    protected int mBufferSize = 1024;
    protected int mHeartbeatInterval = 50;

    protected byte[] mBufferArray;
    protected ByteBuffer mBuffer;
    protected boolean mNeedReset = true;

    protected boolean mStarted = false;
    protected boolean mStoped = false;

    protected Thread mReceiverT;
    protected Thread mWorkerT;

    private long mSentPackets;
    private long mReceivedPackets;

    private boolean mIsLogin = false;

    private Executor mExecutor;
    private int receiverMessageNum = 0;

    private List<byte[]> cacheDataList = new ArrayList<>();

    public P2PClient(String uudi, String ip, String port, Context context) {
        this.mRemoteAddress = ip;
        this.mRemotePort = Integer.parseInt(port);
        this.mUuid = DanmakuUtil.asBytes(SettingUtils.getDanmukuUuid());
        this.mAppid = 1;
        mContext = context;
        mExecutor = Executors.newSingleThreadExecutor();
    }

    private synchronized void init() {
        mBufferArray = new byte[mBufferSize];
        mBuffer = ByteBuffer.wrap(mBufferArray);
    }

    /**
     * 开始 循环接收信息线程
     *
     * @throws Exception
     */
    public synchronized void start() {
        try {
            if (this.mStarted) {
                return;
            }
            this.init();
            this.mStarted = true;
            this.mStoped = false;

            mReceiverT = new Thread(this, "udp-mClient-receiver");
            mReceiverT.setDaemon(true);
            synchronized (mReceiverT) {
                mReceiverT.start();
                mReceiverT.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // reset cliient
    protected synchronized void reset() throws Exception {
        if (!mNeedReset) {
            return;
        }

        if (mClient != null) {
            try {
                mClient.close();
            } catch (Exception e) {
            }
        }
        if (hasNetworkConnection()) {
            mClient = new DatagramSocket();
            mNeedReset = false;
        } else {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void run() {
        synchronized (mReceiverT) {
            mReceiverT.notifyAll();
        }

        while (!mStoped) {
            try {
                if (!hasNetworkConnection()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    continue;
                }
                reset();

                if (mServerSocketAddress == null) {
                    try {
                        mServerSocketAddress = new InetSocketAddress(mRemoteAddress, mRemotePort);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                heartbeat();// 发送心跳包
                receiveData();
            } catch (java.net.SocketTimeoutException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                this.mNeedReset = true;
            } catch (Throwable t) {
                t.printStackTrace();
                this.mNeedReset = true;
            } finally {
                if (mNeedReset) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }

                if (!hasNetworkConnection()) {
                    mIsLogin = false;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (mClient != null) {
            try {
                mClient.close();
            } catch (Exception e) {
            }
            mClient = null;
        }
    }

    /**
     * 发送心跳包
     *
     * @throws Exception
     */
    private void heartbeat() {
        if (System.currentTimeMillis() - mLastSent < mHeartbeatInterval * 1000) {
            return;
        }
        try {
            if (mClient == null) {
                return;
            }
            LogUtil.d(TAG, "heartbeat ");
            SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_HEARTBEAT, mUuid, "");
            send(message.getData());
            mLastSent = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收 数据
     *
     * @throws Exception
     */
    private void receiveData() throws Exception {
        LogUtil.e(TAG, "receiveData1");
        DatagramPacket dp = new DatagramPacket(mBufferArray, mBufferArray.length);
        mClient.setSoTimeout(5 * 1000);
        mClient.receive(dp);
        LogUtil.e(TAG, "-------------------- receiveData");
        if (dp.getLength() <= 0 || dp.getData() == null || dp.getData().length == 0) {
            return;
        }
        byte[] data = new byte[dp.getLength()];
        System.arraycopy(dp.getData(), 0, data, 0, dp.getLength());
        for (int i = 0; i < data.length; i++) {
            LogUtil.e(TAG, "data [" + i + "] = " + data[i]);
        }
        // 服务端下发消息 表示说为登陆
        if (data.length == 2) {
            if (data[0] == Message.CMD_RELOGIN) {
                mIsLogin = false;
            }

            if (data[0] == Message.CMD_LOGIN_SUCC) {
                mIsLogin = true;
            }

            return;
        }

        ReceiverMessage m = new ReceiverMessage(data);

        this.mReceivedPackets++;
        this.mLastReceived = System.currentTimeMillis();
    }

    private void send(byte[] data) throws Exception {
        if (data == null) {
            return;
        }
        if (mClient == null) {
            return;
        }

        if (mServerSocketAddress == null) {
            return;
        }

        for (int i = 0; i < data.length; i++) {
            LogUtil.d(TAG, "data [" + i + " ] = " + data[i]);
        }

        DatagramPacket dp = new DatagramPacket(data, data.length);
        dp.setSocketAddress(mServerSocketAddress);
        mClient.send(dp);
        this.mSentPackets++;
    }


    public interface OnReceiverMessageListener {
        void onReceiverMessage(ReceiverMessage message);
    }

    public boolean hasNetworkConnection() {
        //TODO hasNetworkConnection
        return true;
    }

    /**
     * 发送登录信息
     */
    public void logout() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mClient == null) {
                        return;
                    }
                    LogUtil.d(TAG, "log out");
                    mIsLogin = false;
                    SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_LOGOUT, mUuid, "");
                    send(message.getData());
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 停止 结束数据 关闭 mClient
     *
     * @throws Exception
     */
    public synchronized void stop() throws Exception {
        Log.w(TAG, "stop");
        mLastSent = 0;
        mStoped = true;
        this.mStarted = false;
        if (mClient != null) {
            try {
                mClient.close();
            } catch (Exception e) {
            }
            mClient = null;
        }
        if (mReceiverT != null) {
            try {
                mReceiverT.interrupt();
            } catch (Exception e) {
            }
        }

        if (mWorkerT != null) {
            try {
                mWorkerT.interrupt();
            } catch (Exception e) {
            }
        }

        if (mMessageQueue != null) {
            mMessageQueue.clear();
        }

        if (cacheDataList != null) {
            cacheDataList.clear();
        }

    }

}
