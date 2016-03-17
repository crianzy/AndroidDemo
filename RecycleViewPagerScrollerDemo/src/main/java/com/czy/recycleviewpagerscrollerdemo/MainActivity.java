package com.czy.recycleviewpagerscrollerdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/19.
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ViewPager mViewPager;
    ScrollableLinearLayout mScrollableLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mScrollableLinearLayout = (ScrollableLinearLayout) findViewById(R.id.ll);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(this));

        mScrollableLinearLayout.setOnScrollListener(new ScrollableLinearLayout.OnScrollYChangeListener() {
            @Override
            public boolean canScrollUp() {
                return true;
            }

            @Override
            public boolean canScrollDown() {
                return true;
            }

            @Override
            public RecyclerView getListView() {
                return null;
            }

            @Override
            public ViewPager getViewPager() {
                return null;
            }

            @Override
            public void onScrollChanged(int scrollY) {
                LogUtil.d(TAG, "onScrollChanged scroll Y = " + scrollY);
            }
        });


    }
}
