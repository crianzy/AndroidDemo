package com.czy.nestedscrolling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 15/12/15.
 */
public class MyAdpater extends RecyclerView.Adapter<MyAdpater.MyHolder> {

    private List<String> mStringList;
    private Context mContext;

    public MyAdpater(Context context) {
        mStringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mStringList.add("string -- " + i);
        }
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = (TextView) itemView;
        }
    }
}
