package com.zuimeia.expandtextview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    //    ExpandableTextView mExpandableTextView;
    TextView mTextView;
    TextView mTextView2;
    MyExpandTextView mMyExpandTextView;

    String yourText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
            "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt);
        mTextView2 = (TextView) findViewById(R.id.txt2);
        mTextView.setText(yourText);
        mTextView2.setText(yourText);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyExpandTextView.setText(yourText, true);
            }
        });

        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyExpandTextView.setText("hello asdasdasdasdasdasdasdasdasdasdasdasdasd");
            }
        });


        mMyExpandTextView = (MyExpandTextView) findViewById(R.id.mytxt);
        mMyExpandTextView.setText(yourText);

    }

    boolean isExpand = false;

    private void expandTextView(final TextView tv) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", 4, tv.getLineCount());
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "tv height = " + tv.getLayoutParams().height);
            }
        });
        animation.setDuration(200).start();
        isExpand = true;
    }

    private void collapseTextView(final TextView tv, int numLines) {
        ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount(), numLines);
        animation.setDuration(200).start();
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "tv height = " + tv.getLayoutParams().height);
            }
        });
        isExpand = false;
    }
}
