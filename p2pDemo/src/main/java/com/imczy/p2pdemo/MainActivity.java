package com.imczy.p2pdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.imczy.common_util.db.SettingUtils;
import com.imczy.common_util.log.LogUtil;
import com.imczy.p2pdemo.message.ReceiverMessage;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements UDPClient.OnReceiverMessageListener {
    public static final String TAG = "MainActivity";

    UDPClient mUDPClient;

    Button linkBtn;
    Button snedBtn;
    Button logout;
    Button chat2Bbtn;

    // 红米  6e19f465-be91-404d-bf90-910594a7292f
    // 一家  409647df-f3e4-4c4c-a527-7826153d8bc2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingUtils.init(getApplicationContext());
        setContentView(R.layout.activity_main);

        linkBtn = (Button) findViewById(R.id.link_btn);
        snedBtn = (Button) findViewById(R.id.send_btn);
        logout = (Button) findViewById(R.id.logout_btn);
        chat2Bbtn = (Button) findViewById(R.id.chat_2_b);

        LogUtil.e(TAG, "uuid = " + SettingUtils.getDanmukuUuid());
        try {
            mUDPClient = new UDPClient(DanmakuUtil.asBytes(SettingUtils.getDanmukuUuid()), 1, Constant.DANMAKU_SERVER_HOSTNAME, Constant.DANMAKU_SERVER_PORT, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mUDPClient.setOnReceiverMessageListener(this);


        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkToServer();
            }
        });

        snedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDanmaku();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        chat2Bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUDPClient.postIwantToChatP("{6e19f465-be91-404d-bf90-910594a7292f}".replace("-",""));
            }
        });


    }


    private void linkToServer() {
        mUDPClient.start();
    }

    private void sendDanmaku() {
        mUDPClient.postDanmaku("sendDanmaku" + System.currentTimeMillis());
    }

    private void logout() {
        mUDPClient.logout();
    }

    @Override
    public void onReceiverMessage(ReceiverMessage message) {
        String data = message.getContent();
        int msgStart = data.indexOf("{");
        int msgEnd = data.lastIndexOf("}");

        if (msgStart == -1 || msgEnd == -1 || msgStart > msgEnd) {
            return;
        }

        String header = data.substring(0, msgStart);
        final String danmaKuContent = data.substring(msgStart + 1, msgEnd);
        String[] headerArr = header.split("\\|");
        long userId = Long.parseLong(headerArr[0]);
        LogUtil.d(TAG, "userId = " + userId + " , danmaKuContent = " + danmaKuContent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUDPClient.logout();
    }
}
