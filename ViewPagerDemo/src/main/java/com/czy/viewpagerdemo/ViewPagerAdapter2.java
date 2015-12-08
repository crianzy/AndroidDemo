package com.czy.viewpagerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.imczy.common_util.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 15/12/6.
 */
public class ViewPagerAdapter2 extends FragmentPagerAdapter {
    public static final String TAG = ViewPagerAdapter2.class.getSimpleName();

    private List<Fragment> mFragments = new ArrayList<>();

    public ViewPagerAdapter2(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void removeFragment(Fragment fragment) {
        mFragments.remove(fragment);
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public void clear() {
        for (Fragment fragment : mFragments) {
            if (fragment != null && fragment.isAdded()) {
                fragment.onDestroy();
            }
        }
        mFragments.clear();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.d(TAG, "instantiateItem position = " + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
