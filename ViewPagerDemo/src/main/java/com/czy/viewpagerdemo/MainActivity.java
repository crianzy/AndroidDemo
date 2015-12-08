package com.czy.viewpagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;

    private ViewPagerAdapter1 mViewPagerAdapter1;
    private ViewPagerAdapter2 mViewPagerAdapter2;
    private ViewPagerAdapter3 mViewPagerAdapter3;

    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPagerAdapter2 = new ViewPagerAdapter2(getSupportFragmentManager());
        mViewPagerAdapter3 = new ViewPagerAdapter3(getSupportFragmentManager());

        mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDataList.add("String  " + i);
            mViewPagerAdapter3.addFragment(MyFragment.getInstances("Fragment _ StatePagerAdapter " + i));
            mViewPagerAdapter2.addFragment(MyFragment.getInstances("Fragment _ " + i));
        }
        mViewPagerAdapter1 = new ViewPagerAdapter1(this, mDataList);

        mViewPager.setAdapter(mViewPagerAdapter1);
//        mViewPager.setAdapter(mViewPagerAdapter2);

//        mViewPager.setAdapter(mViewPagerAdapter3);
    }
}
