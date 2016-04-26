package com.imczy.aidldemo;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

import com.imczy.aidldemo.Book;
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    private TextView mTextView;
    private Button mGotoP2ActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.process_txt);
        mGotoP2ActivityBtn = (Button) findViewById(R.id.gotoP2Activity);


        LogUtil.d(TAG, "mProcessTxt = " + mTextView);
        mTextView.setText("pid = " + android.os.Process.myPid());
        mGotoP2ActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Process2Activity.class);
                startActivity(intent);
            }
        });
    }

}
