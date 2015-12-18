package com.czy.textviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView1, mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView1 = (TextView) findViewById(R.id.txt1);
        mTextView2 = (TextView) findViewById(R.id.txt2);
        mTextView2.setIncludeFontPadding(false);
    }
}
