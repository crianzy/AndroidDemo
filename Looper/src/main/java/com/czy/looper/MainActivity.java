package com.czy.looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    public static final int SIGNAL_1 = 0x1;
    public static final int SIGNAL_2 = 0x2;

    public static int flagValue = 0;
    private LooperThread thread;
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case SIGNAL_1:
                    Log.v("MainActivity", "uiHandler 主线程收到子线程发来的消息");
                    flagValue++;
                    if (flagValue == 5) {
                        Log.v("MainActivity", "uiHandler now flagvalue is over 5");
                        Log.v("MainActivity",
                                "uiHandler quit 前 thread.isAlive?" + thread.isAlive());
                        thread.cHandler.getLooper().quit();
                        Log.v("MainActivity",
                                "uiHandler quit 后 thread.isAlive?" + thread.isAlive());
                    } else {
                        Log.v("MainActivity", "uiHandler thread.isAlive?" + thread.isAlive());
                        thread.cHandler.sendEmptyMessageDelayed(SIGNAL_1, 3000);
                    }
                    break;
                case SIGNAL_2:
                    thread.cHandler.sendEmptyMessage(SIGNAL_1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread = new LooperThread();
        thread.start();
        uiHandler.sendEmptyMessage(SIGNAL_2);

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.v("MainActivity",
                            " new Thread() 第三个子线程里面每隔10s判断 thread.isAlive?"
                                    + thread.isAlive());
                }
            }
        }.start();

    }

    class LooperThread extends Thread {
        public Handler cHandler;

        @Override
        public void run() {

            // 实例化messagequeue
            Looper.prepare();

            cHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    switch (msg.what) {
                        case SIGNAL_1:
                            Log.v("MainActivity", "LooperThread 子线程收到主线程发来的消息");
                            uiHandler.sendEmptyMessageDelayed(SIGNAL_1, 3000);
                            break;
                        default:
                            break;
                    }
                }
            };
            Log.v("MainActivity", "LooperThread loop以前的语句");
            Looper.loop();

            Log.v("MainActivity", "LooperThread loop以后的语句");

        }

    }
}
