package com.czy.rippledrawable;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView1;

    Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = (TextView) findViewById(R.id.txt1);
        mButton1 = (Button) findViewById(R.id.btn1);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mTextView1.setBackground(getResources().getDrawable(R.drawable.ripple));
        }

        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
