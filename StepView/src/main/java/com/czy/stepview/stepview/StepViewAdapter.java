package com.czy.stepview.stepview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.czy.stepview.R;

import java.util.List;

/**
 * Created by chenzhiyong on 2016/10/29.
 */

public class StepViewAdapter implements BaseStepViewAdapter {
    private static final String TAG = "StepViewAdapter";

    List<String> mStringList;
    Context mContext;

    public StepViewAdapter(List<String> stringList, Context context) {
        mStringList = stringList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public VerticalStepView.ViewHolder getViewHolder(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        ItemHolder itemHolder = new ItemHolder(view);
        itemHolder.mItemTxt.setText(mStringList.get(position));
        return itemHolder;
    }

    static class ItemHolder extends VerticalStepView.ViewHolder {
        TextView mItemTxt;


        public ItemHolder(View view) {
            super(view);
            mItemTxt = (TextView) view.findViewById(R.id.item_txt);
        }
    }
}
