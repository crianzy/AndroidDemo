package com.czy.im.intentservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent1 = new Intent(this, IntentService1.class);
        Intent intent2 = new Intent(this, IntentService1.class);
        Intent intent3 = new Intent(this, IntentService1.class);
        Intent intent4 = new Intent(this, IntentService1.class);
        Intent intent5 = new Intent(this, IntentService1.class);
        Intent intent6 = new Intent(this, IntentService2.class);

        startService(intent1);
        startService(intent2);
        startService(intent3);
        startService(intent4);
        startService(intent5);
        startService(intent6);
    }
}
