package com.czy.viewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    ViewPager mViewPager;
//    RelativeLayout mViewPagerBox;
//
//    private ViewPagerAdapter1 mViewPagerAdapter1;

	private ListView mListView;


	protected DisplayMetrics getScreenDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.list_view);


		View header = getHeader();
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320, getScreenDisplayMetrics()));
		header.setLayoutParams(layoutParams);
		mListView.addHeaderView(header);

		mListView.setAdapter(new ListAdapter(this));

//        mViewPager = (ViewPager) findViewById(R.id.view_pager);
//        mViewPagerBox = (RelativeLayout) findViewById(R.id.view_pager_box);
//
//        mViewPager.setOffscreenPageLimit(3);
//
//        mViewPagerAdapter1 = new ViewPagerAdapter1(this);
//        mViewPager.setAdapter(mViewPagerAdapter1);
//
//        mViewPagerBox.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return mViewPager.dispatchTouchEvent(event);
//            }
//        });
////        mViewPager.setPageTransformer(true, new MyPageTransformer());

	}

	private View getHeader() {
		View view = LayoutInflater.from(this).inflate(R.layout.activity_view_pager_header, null);
		ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
		ViewPagerAdapter1 viewPagerAdapter1 = new ViewPagerAdapter1(this);
		viewPager.setAdapter(viewPagerAdapter1);

		return view;
	}


	static class ListAdapter extends BaseAdapter {
		Context mContext;
		List<String> mStringList;

		public ListAdapter(Context context) {
			mContext = context;
			mStringList = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				mStringList.add(" " + i + "");
			}
		}

		@Override
		public int getCount() {
			return mStringList.size();
		}

		@Override
		public Object getItem(int position) {
			TextView textView = new TextView(mContext);
			textView.setText(mStringList.get(position));
			return textView;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(mContext);
			textView.setText(mStringList.get(position));
			return textView;
		}
	}


	private static float defaultScale = (float) 14 / (float) 15;

	public class MyPageTransformer implements ViewPager.PageTransformer {
		@Override
		public void transformPage(View view, float position) {
			View cardView = view.findViewById(R.id.img_box);
			View img = view.findViewById(R.id.img);

			int diffWidth = (cardView.getWidth() - img.getWidth()) / 2;

			if (position < -1) { // [-Infinity,-1)
				cardView.setScaleX(defaultScale);
				cardView.setScaleY(defaultScale);
				img.setTranslationX(diffWidth);

			} else if (position <= 0) { // [-1,0]
				cardView.setScaleX((float) 1 + position / (float) 15);
				cardView.setScaleY((float) 1 + position / (float) 15);
				img.setTranslationX((0 - position) * diffWidth);

			} else if (position <= 1) { // (0,1]
				cardView.setScaleX((float) 1 - position / (float) 15);
				cardView.setScaleY((float) 1 - position / (float) 15);
				img.setTranslationX((0 - position) * diffWidth);

			} else { // (1,+Infinity]
				cardView.setScaleX(defaultScale);
				cardView.setScaleY(defaultScale);
				img.setTranslationX(-diffWidth);
			}
		}
	}
}
