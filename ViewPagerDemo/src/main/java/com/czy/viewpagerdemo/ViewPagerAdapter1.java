package com.czy.viewpagerdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

import java.util.List;

/**
 * Created by chenzhiyong on 15/12/6.
 */
public class ViewPagerAdapter1 extends PagerAdapter {
    public static final String TAG = ViewPagerAdapter1.class.getSimpleName();

    private List<String> mDataList;
    private LayoutInflater mLayoutInflater;

    public ViewPagerAdapter1(Context context, List<String> dataList) {
        mDataList = dataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.d(TAG, "instantiateItem position = " + position);
        // 这里 必须是 null , 不然报错
        View item = mLayoutInflater.inflate(R.layout.item_1, null);
        TextView textView = (TextView) item.findViewById(R.id.txt);
        textView.setText(mDataList.get(position));
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtil.d(TAG, "isViewFromObject position " + position);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        LogUtil.d(TAG, "isViewFromObject view == object = " + (view == object) + "   view = " + view + "  , object = " + object);
        return view == object;
    }
}
