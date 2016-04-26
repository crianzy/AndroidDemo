package com.squareup.recyclesourcecode2;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v71.widget.LinearLayoutManager;
import android.support.v71.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.imczy.common_util.log.LogUtil;

public class MainActivity2 extends AppCompatActivity {
	private static final String TAG = "MainActivity";


	RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.addItemDecoration(new ItemDecoration());
		mRecyclerView.setAdapter(new RecycleViewAdapter(this));

		mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);

				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
					lastScrollTime = 0;
					LogUtil.e(TAG, "OnScroll 滑动结束");
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				// dy > 0 表示向上滑动
				// 这里计算 滑动速度
				if (lastScrollTime == 0 && dy != 0) {
					lastScrollTime = System.currentTimeMillis();
					LogUtil.d(TAG, "OnScroll  first lastScrollTime = " + lastScrollTime);
				} else if (lastScrollTime != 0) {
					long currentTime = System.currentTimeMillis();
					speed = ((float) dy) / ((float) (currentTime - lastScrollTime));
					LogUtil.d(TAG, "OnScroll dy = " + dy + " , speed = " + speed + " , time del = " + ((currentTime - lastScrollTime)));
					lastScrollTime = System.currentTimeMillis();
					recyclerView.requestLayout();
				}
			}
		});
	}


	private long lastScrollTime = 0;
	private float speed = 0;
	private int minHeight = 50;

	class ItemDecoration extends RecyclerView.ItemDecoration {

		@Override
		public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
			super.onDraw(c, parent, state);
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			int index = parent.indexOfChild(view);
			outRect.set(0, 0, 0, minHeight);
			LogUtil.d(TAG, "index = " + index);

		}
	}
}
