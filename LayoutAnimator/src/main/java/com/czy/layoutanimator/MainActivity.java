package com.czy.layoutanimator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewGroup mViewGroup;
    Button mAddBtn;
    Button mRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewGroup = (ViewGroup) findViewById(R.id.view_group);

        mAddBtn = (Button) findViewById(R.id.add_btn);
        mRemoveBtn = (Button) findViewById(R.id.remove_btn);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewGroup.addView(makeText());
            }
        });
        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewGroup.removeView(mViewGroup.getChildAt(mViewGroup.getChildCount() - 2));
            }
        });
        mViewGroup.setLayoutAnimation(new LayoutAnimationController());
    }

    private TextView makeText() {
        TextView txtView = new TextView(this);
        txtView.setText("asdasd");
        return txtView;
    }
}
