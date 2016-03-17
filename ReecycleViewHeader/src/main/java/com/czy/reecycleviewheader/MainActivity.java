package com.czy.reecycleviewheader;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.imczy.common_util.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    View mHeaderView;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    ScrollableLayout mScrollableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.header_view);
        mScrollableLayout = (ScrollableLayout) findViewById(R.id.scroll_layout);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        MyFragment myFragment1 =
                MyFragment.getInstances();
        MyFragment myFragment2 =
                MyFragment.getInstances();
        mViewPagerAdapter.addFragment(myFragment1);
        mViewPagerAdapter.addFragment(myFragment2);

        mScrollableLayout.getHelper().setCurrentScrollableContainer(myFragment1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollableLayout.getHelper().setCurrentScrollableContainer((MyFragment) mViewPagerAdapter.getItem(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(mViewPagerAdapter);
    }
}
