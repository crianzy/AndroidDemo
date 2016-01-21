package com.czy.recycleviewpagerscrollerdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mStringList;

    private final int[] MODEL_COLORS = {
            0xffff2f45,
            0xfff8c218,
            0xff019aea,
            0xff5c6bc0,
            0xff6aa440,
            0xffe3ae14,
            0xfffa660e,
            0xffd83a5b,
            0xff682781,
            0xff5b45ce,
            0xff50abae,
            0xff10a498,
            0xff8ebc25,
            0xff33a744,
            0xffe3c12a,
            0xffe77d31,
            0xff3485d4,
            0xff2cbebd,
            0xffc2c63e,
            0xffeb7945,
            0xff3a5daf,
            0xff019aea,
            0xffe9bb2a,
            0xff604570,
            0xffdd4f4e,
            0xffec7b37,
            0xfff5a908,
            0xffc84a33,
            0xff3769b0
    };


    public RecycleViewAdapter(Context context) {
        mContext = context;
        mStringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mStringList.add("RecycleViewAdapter -- " + i);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt.setText(mStringList.get(position));
        holder.itemView.setBackgroundColor(MODEL_COLORS[position % MODEL_COLORS.length]);

    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }

}
