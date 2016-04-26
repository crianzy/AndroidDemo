package com.squareup.recyclesourcecode2;

import android.content.Context;
import android.support.v71.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhiyong on 16/3/26.
 */
public class ListAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mStringList;

	public ListAdapter(Context context) {
		mContext = context;
		mStringList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			mStringList.add("RecycleViewAdapter -- " + i);
		}
	}

	@Override
	public int getCount() {
		return mStringList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
			holder.txt = (TextView) convertView.findViewById(R.id.txt);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt.setText(mStringList.get(position));
		return convertView;
	}


	static class ViewHolder {
		TextView txt;
	}

}
