package com.czy.viewpagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    RelativeLayout mViewPagerBox;

    private ViewPagerAdapter1 mViewPagerAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerBox = (RelativeLayout) findViewById(R.id.view_pager_box);

        mViewPager.setOffscreenPageLimit(3);

        mViewPagerAdapter1 = new ViewPagerAdapter1(this);
        mViewPager.setAdapter(mViewPagerAdapter1);

        mViewPagerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mViewPager.setPageTransformer(true, new MyPageTransformer());
    }


    private static float defaultScale = (float) 14 / (float) 15;

    public class MyPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            View cardView = view.findViewById(R.id.img_box);
            View img = view.findViewById(R.id.img);

            int diffWidth = (cardView.getWidth() - img.getWidth()) / 2;

            if (position < -1) { // [-Infinity,-1)
                cardView.setScaleX(defaultScale);
                cardView.setScaleY(defaultScale);
                img.setTranslationX(diffWidth);

            } else if (position <= 0) { // [-1,0]
                cardView.setScaleX((float) 1 + position / (float) 15);
                cardView.setScaleY((float) 1 + position / (float) 15);
                img.setTranslationX((0 - position) * diffWidth);

            } else if (position <= 1) { // (0,1]
                cardView.setScaleX((float) 1 - position / (float) 15);
                cardView.setScaleY((float) 1 - position / (float) 15);
                img.setTranslationX((0 - position) * diffWidth);

            } else { // (1,+Infinity]
                cardView.setScaleX(defaultScale);
                cardView.setScaleY(defaultScale);
                img.setTranslationX(-diffWidth);
            }
        }
    }
}
