package com.squareup.recyclesourcecode2;

import android.content.Context;
import android.support.v71.widget.RecyclerView;
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

	public RecycleViewAdapter(Context context) {
		mContext = context;
		mStringList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			mStringList.add("RecycleViewAdapter -- " + i);
		}
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.item2, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.txt.setText(mStringList.get(position));

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
