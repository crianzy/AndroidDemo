/*
 *Copyright 2014 DDPush
 *Author: AndyKwok(in English) GuoZhengzhu(in Chinese)
 *Email: ddpush@126.com
 *

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/
package com.imczy.p2pdemo;

import android.content.Context;
import android.util.Log;

import com.imczy.common_util.NetworkUtil;
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
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class UDPClient implements Runnable {
    public static final String TAG = "UDPClient";
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
    protected Worker mWorker;
    protected Thread mWorkerT;

    private long mSentPackets;
    private long mReceivedPackets;

    private boolean mIsLogin = false;

    private Executor mExecutor;
    private int receiverMessageNum = 0;

    private List<byte[]> cacheDataList = new ArrayList<>();
    public P2PClient mP2PClient;

    private static UDPClient udpClient;

    public static UDPClient getInstance(Context context, UUID uuid) throws Exception {
        if (udpClient == null) {
            byte[] uuidBytes = DanmakuUtil.asBytes(uuid);
            LogUtil.d(TAG, "mUuid length = " + uuidBytes.length + " , mUuid = " + uuid.toString());
            udpClient = new UDPClient(uuidBytes, 1, Constant.DANMAKU_SERVER_HOSTNAME, Constant.DANMAKU_SERVER_PORT, context);
        }
        return udpClient;
    }

    public UDPClient(byte[] uuid, int appid, String serverAddr, int serverPort, Context context) throws Exception {

        if (uuid == null || uuid.length != 16) {
            throw new IllegalArgumentException("mUuid byte array must be not null and length of 16 bytes");
        }
        if (appid < 1 || appid > 255) {
            throw new IllegalArgumentException("mAppid must be from 1 to 255");
        }
        if (serverAddr == null || serverAddr.trim().length() == 0) {
            throw new IllegalArgumentException("server address illegal: " + serverAddr);
        }

        this.mUuid = uuid;
        this.mAppid = appid;
        this.mRemoteAddress = serverAddr;
        this.mRemotePort = serverPort;
        mContext = context;
        mExecutor = Executors.newSingleThreadExecutor();
    }

    //消息  入队列
    protected boolean enqueue(ReceiverMessage message) {
        boolean result = mMessageQueue.add(message);
        if (result == true) {
            mQueueIn.addAndGet(1);
        }
        return result;
    }

    // 从队列中获取消息
    protected ReceiverMessage dequeue() {
        ReceiverMessage m = mMessageQueue.poll();
        if (m != null) {
            mQueueOut.addAndGet(1);
        }
        return m;
    }

    private synchronized void init() {
        mBufferArray = new byte[mBufferSize];
        mBuffer = ByteBuffer.wrap(mBufferArray);
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

            mWorker = new Worker();

            mWorkerT = new Thread(mWorker, "udp-mClient-mWorker");
            mWorkerT.setDaemon(true);
            synchronized (mWorkerT) {
                mWorkerT.start();
                mWorkerT.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    /**
     * 循环 获取msg   保存长连接
     */
    public void run() {

        synchronized (mReceiverT) {
            mReceiverT.notifyAll();
        }

        while (!mStoped) {
            try {
                if (!hasNetworkConnection()) {
                    try {
                        trySystemSleep();
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

                if (!mIsLogin) {
                    login();
                    Thread.sleep(30);//防止login和heartbeat两次请求间隔太短，导致heartbeat命令包丢失
                }

                heartbeat();// 发送心跳包
                receiveData();
            } catch (java.net.SocketTimeoutException e) {
            } catch (Exception e) {
                e.printStackTrace();
                this.mNeedReset = true;
            } catch (Throwable t) {
                t.printStackTrace();
                this.mNeedReset = true;
            } finally {
                if (mNeedReset) {
                    try {
                        trySystemSleep();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }

                if (!hasNetworkConnection()) {
                    mIsLogin = false;
                    try {
                        trySystemSleep();
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


    private void login() {
        int userid = 0;
        String gps = "0,0";
        String group = "A:ALL";
        String ipPort = "" + NetworkUtil.getLocalIpAddress(mContext) + ":" + mClient.getLocalPort();
        LogUtil.d(TAG, "gps = " + gps + " , group = " + group + "ipPort = " + ipPort);
        login(userid, group, gps);
    }

    /**
     * 发送 登录信息
     *
     * @param userId
     * @throws Exception
     */
    private void login(int userId, String group, String gps) {
        try {
            if (mClient == null) {
                return;
            }
            LogUtil.e(TAG, "login ------------------");
            // 需要加上 用户信息
//            mIsLogin = true;
            SendMessage loginMessage = SendMessage.newMessage(mAppid, SendMessage.CMD_LOGIN,
                    mUuid, userId + "|" + group + "|" + gps + "|" + NetworkUtil.getLocalIpAddress(mContext) + ":" + mClient.getLocalPort());
            send(loginMessage.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    mP2PClient.logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送 弹幕信息
     *
     * @param danmaku 弹幕信息
     */
    public void postDanmaku(final String danmaku) {
        final String msg = "{" + danmaku + "}";
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mClient == null) {
                        return;
                    }

                    SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_CHAT, mUuid, msg);
                    send(message.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postIwantToChatP(final String uuid) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mClient == null) {
                        return;
                    }
                    SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_CHAT_P2P, mUuid, uuid);
                    send(message.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postIhandCall2A(final String uuid) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mClient == null) {
                        return;
                    }
                    SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_I_HAD_CALL_A, mUuid, uuid);
                    send(message.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 跟新 gps 信息
     *
     * @param postion
     */
    public void updateGps(final String postion) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mClient == null) {
                        return;
                    }
                    LogUtil.d(TAG, "postion " + postion);

                    SendMessage message = SendMessage.newMessage(mAppid, SendMessage.CMD_POSITION, mUuid, postion);
                    send(message.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        this.enqueue(m);
        mWorker.wakeup();
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

    public long getSentPackets() {
        return this.mSentPackets;
    }

    public long getReceivedPackets() {
        return this.mReceivedPackets;
    }

    public void setServerPort(int port) {
        this.mRemotePort = port;
    }

    public int getServerPort() {
        return this.mRemotePort;
    }

    public void setServerAddress(String addr) {
        this.mRemoteAddress = addr;
    }

    public String getServerAddress() {
        return this.mRemoteAddress;
    }

    public void setBufferSize(int bytes) {
        this.mBufferSize = bytes;
    }

    public int getBufferSize() {
        return this.mBufferSize;
    }

    public long getLastHeartbeatTime() {
        return mLastSent;
    }

    public long getLastReceivedTime() {
        return mLastReceived;
    }

    /*
     * send heart beat every given seconds
     */
    public void setHeartbeatInterval(int second) {
        if (second <= 0) {
            return;
        }
        this.mHeartbeatInterval = second;
    }

    public int getHeartbeatInterval() {
        return this.mHeartbeatInterval;
    }

    public boolean hasNetworkConnection() {
        //TODO hasNetworkConnection
        return true;
    }

    /**
     * 取数据的 处理数据的薪酬
     */
    class Worker implements Runnable {
        public void run() {
            synchronized (mWorkerT) {
                mWorkerT.notifyAll();
            }
            while (!mStoped) {
                try {
                    handleEvent();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    waitMsg();
                }
            }
        }

        private void waitMsg() {
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void wakeup() {
            synchronized (this) {
                this.notifyAll();
            }
        }


        // 冲队列里面获取 数据
        private synchronized void handleEvent() throws Exception {
            ReceiverMessage m = null;
            while (true) {
                m = dequeue();
                if (m == null) {
                    return;
                }
                if (!m.isRightFomat()) {
                    continue;
                }


                if (m.getCmd() == Message.CMD_CHAT_RECEIVE_P2P) {
                    //   ip, 端口, uuid,
                    LogUtil.d(TAG, "m.getContent() = " + m.getContent());
                    final String conetnt = m.getContent();
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            String[] ips = conetnt.split("\\|");
                            if (ips.length == 3) {
                                mP2PClient = new P2PClient(ips[0], ips[1], ips[2], mContext);
                                mP2PClient.start();
                                postIhandCall2A(ips[0]);
                            }
                        }
                    });
                    return;
                }
                if (m.getCmd() == Message.CMD_B_HAD_CALL_YOU) {
                    //   ip, 端口, uuid,
                    LogUtil.d(TAG, "m.getContent() = " + m.getContent());
                    final String conetnt = m.getContent();
//                    mExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            String[] ips = conetnt.split("\\|");
//                            if (ips.length == 3) {
//                                mP2PClient = new P2PClient(ips[0], ips[1], ips[2], mContext);
//                                mP2PClient.start();
//                                postIhandCall2A(ips[0]);
//                            }
//                        }
//                    });
                    return;
                }

                if (m.getCmd() != Message.CMD_CHAT) {
                    continue;
                }


                if (System.currentTimeMillis() > lastPushReceiveTime + 3 * 1000) {
//                    LogUtil.d(TAG, "清除 记录的重复数据");
                    cacheDataList.clear();
                }

                byte[] data = m.getData();
                boolean isRepeat = false;
                for (int i = 0; i < cacheDataList.size(); i++) {
                    byte[] cacheData = cacheDataList.get(i);
                    if (data.length != cacheData.length) {
                        continue;
                    }

                    boolean isEqThisCacheData = true;
                    for (int j = 0; j < cacheData.length; j++) {
                        if (data[j] != cacheData[j]) {
                            isEqThisCacheData = false;
                            break;
                        }
                    }
                    if (isEqThisCacheData) {
                        isRepeat = true;
                        break;
                    }
                }

//                String dataStr = "";
//                for (int i = 0; i < data.length; i++) {
//                    dataStr += data[i];
//                }
//                LogUtil.d(TAG, "dataStr = " + dataStr);
//                LogUtil.d(TAG, "dataStr cacheDataList.size() = " + cacheDataList.size());
                if (isRepeat) {
//                    LogUtil.d(TAG, "dataStr 重复");
                    continue;
                } else {
                    if (cacheDataList.size() >= REPEAT_CACHE_SIZE) {
                        cacheDataList.remove(0);
                    }
                    lastPushReceiveTime = System.currentTimeMillis();
                    cacheDataList.add(data);
                }


                //real work here
                receiverMessageNum++;
                LogUtil.e(TAG, "onPushMessage receiverMessageNum = " + receiverMessageNum + " , content = " + m.getContent());
                if (mOnReceiverMessageListener != null) {
                    mOnReceiverMessageListener.onReceiverMessage(m);
                }
            }
            //finish work here, such as release wake lock
        }

    }

    private long lastPushReceiveTime = 0;

    private void trySystemSleep() {
    }

    public OnReceiverMessageListener getOnReceiverMessageListener() {
        return mOnReceiverMessageListener;
    }

    public void setOnReceiverMessageListener(OnReceiverMessageListener onReceiverMessageListener) {
        mOnReceiverMessageListener = onReceiverMessageListener;
    }

    public interface OnReceiverMessageListener {
        void onReceiverMessage(ReceiverMessage message);
    }

}
