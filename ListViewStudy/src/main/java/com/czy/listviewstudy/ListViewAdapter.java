package com.czy.listviewstudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 16/1/31.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<String> mData = new ArrayList<>();
    private Context mContext;

    public ListViewAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 100; i++) {
            mData.add(" String " + i);
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, false);
            holder = new Holder();
            holder.txt = (TextView) convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.txt.setText(mData.get(position));
        return convertView;
    }

    static class Holder {
        TextView txt;
    }
}
