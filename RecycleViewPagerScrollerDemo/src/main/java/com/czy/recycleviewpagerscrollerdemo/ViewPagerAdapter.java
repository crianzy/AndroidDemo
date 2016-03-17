package com.czy.recycleviewpagerscrollerdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imczy.common_util.adapter.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 16/1/31.
 */
public class ViewPagerAdapter extends PagerAdapter {

    public static final String TAG = ViewPagerAdapter.class.getSimpleName();

    private List<String> mDataList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ViewPagerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDataList.add("data" + i);
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item = mLayoutInflater.inflate(R.layout.viewpager_item, container, false);
        RecyclerView recyclerView = (RecyclerView) item.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new RecycleViewAdapter(mContext));
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
