package com.czy.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    RelativeLayout mViewPagerBox;

    private ViewPagerAdapter1 mViewPagerAdapter1;


    protected DisplayMetrics getScreenDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

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

    static class ListAdapter extends BaseAdapter {
        Context mContext;
        List<String> mStringList;

        public ListAdapter(Context context) {
            mContext = context;
            mStringList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                mStringList.add(" " + i + "");
            }
        }

        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            TextView textView = new TextView(mContext);
            textView.setText(mStringList.get(position));
            return textView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(mStringList.get(position));
            return textView;
        }
    }


    private static float defaultScale = (float) 14 / (float) 15;

    public class MyPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            View cardView = view.findViewById(R.id.img_box);
            View img = view.findViewById(R.id.img);
            cardView.invalidate();

            int diffWidth = img.getWidth();

            if (position < -1) { // [-Infinity,-1)
//                cardView.setScaleX(defaultScale);
//                cardView.setScaleY(defaultScale);
                cardView.bringToFront();
                img.setTranslationX(diffWidth);

            } else if (position <= 0) { // [-1,0]
//                cardView.setScaleX((float) 1 + position / (float) 15);
//                cardView.setScaleY((float) 1 + position / (float) 15);
                img.setTranslationX((0 - position) * diffWidth);

            } else if (position <= 1) { // (0,1]
//                cardView.setScaleX((float) 1 - position / (float) 15);
//                cardView.setScaleY((float) 1 - position / (float) 15);
                img.setTranslationX((0 - position) * diffWidth);

            } else { // (1,+Infinity]
//                cardView.setScaleX(defaultScale);
//                cardView.setScaleY(defaultScale);
                img.setTranslationX(-diffWidth);
            }
        }
    }

    public class MyPageTransformer2 implements ViewPager.PageTransformer {
        private static final float ROT_MOD = -15f;

        @Override
        public void transformPage(View view, float position) {
            final float width = view.getWidth();
            final float rotation = ROT_MOD * position;

            view.setPivotX(width * 0.5f);
            view.setPivotY(0f);
            view.setTranslationX(0f);
            view.setRotation(rotation);
        }
    }
}
