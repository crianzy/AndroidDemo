package com.imczy.scoller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MyViewGroup mViewGroup;
    View mView;
    View mScrollerByBtn;
    View mScrollerToBtn;
    View mScrollerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrollerByBtn = findViewById(R.id.scroll_by_btn);
        mScrollerToBtn = (Button) findViewById(R.id.scroll_to_btn);
        mScrollerBtn = (Button) findViewById(R.id.scroller_btn);
        mViewGroup = (MyViewGroup) findViewById(R.id.view_group);
        mView = findViewById(R.id.child_view);

        mScrollerByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewGroup.scrollBy(10, 10);
//                mViewGroup.setTranslationX(10);
//                mViewGroup.setTranslationY(10);
//                mScrollerByBtn.scrollBy(0, 10);
            }
        });
        mScrollerToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewGroup.scrollTo(10, 10);
                mScrollerByBtn.scrollTo(0, 10);
            }
        });

        mScrollerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewGroup.bingScroll();
            }
        });
    }
}
