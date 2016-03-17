package com.czy.tintdemo;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewOutlineProvider;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);

//        mButton.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
//        mButton.getBackground().setTint(Color.RED);

//        Drawable leftDrawable = mButton.getBackground();
//        if (leftDrawable != null) {// 在5.0 以下无效
//            DrawableCompat.setTintMode(leftDrawable, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(leftDrawable, Color.RED);
//        }


//        leftDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

    }
}
