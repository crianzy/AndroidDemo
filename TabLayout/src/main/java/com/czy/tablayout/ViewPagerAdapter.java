package com.czy.tablayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 15/12/6.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private static final String TAG = "ViewPagerAdapter";

    private List<String> mDataList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ViewPagerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mDataList = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            mDataList.add("data" + i);
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item = mLayoutInflater.inflate(R.layout.item, container, false);
        TextView txt = (TextView) item.findViewById(R.id.txt);
        txt.setText("page - " + position);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Pasdasd" + position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
