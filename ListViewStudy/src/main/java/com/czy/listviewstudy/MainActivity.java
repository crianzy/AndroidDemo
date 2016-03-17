package com.czy.listviewstudy;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ListView mListView;
    ViewPager mViewPager;
    ScrollableLinearLayout mScrollableLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(new ListViewAdapter(this));

        mScrollableLinearLayout = (ScrollableLinearLayout) findViewById(R.id.ll);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter1(this));

        mScrollableLinearLayout.setMaxScrollDistance(600);
        mScrollableLinearLayout.setOnScrollListener(new ScrollableLinearLayout.OnScrollYChangeListener() {
            @Override
            public boolean canScrollUp() {
                return mScrollableLinearLayout.getScrollY() < 600;
            }

            @Override
            public boolean canScrollDown() {
                return mListView.getFirstVisiblePosition() == 0;
            }

            @Override
            public ListView getListView() {
                return mListView;
            }

            @Override
            public ViewPager getViewPager() {
                return mViewPager;
            }

            @Override
            public void onScrollChanged(int scrollY) {
//                LogUtil.v(TAG, "scrollY = " + scrollY);
            }
        });

    }
}
