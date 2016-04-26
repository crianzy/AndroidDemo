package com.czy.stickscrollrecycleview;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	RecyclerView mRecyclerView;

	private int touchIndex;
	private View toucView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRecyclerView = (RecyclerView) findViewById(R.id.list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.set(0, 0, 0, 30);
			}
		});
		mRecyclerView.setAdapter(new RecycleViewAdapter(this));


		mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					int x = (int) event.getRawX();
					int y = (int) event.getRawY();
					for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
						View child = mRecyclerView.getChildAt(i);
						Log.i(TAG, "onTouch: child = " + child);
						Rect rect = new Rect();
						child.getGlobalVisibleRect(rect);
						if (rect.contains(x, y)) {
							touchIndex = i;
							toucView = child;
							Log.i(TAG, "onTouch: touch in  i = " + i);
						}
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
					touchIndex = -1;
					toucView = null;
				}
				return false;
			}
		});

		mRecyclerView.addOnScrollListener(new ScrollLisnter());

		mTransitionEffect = new ZipperEffect();
	}

	class ScrollLisnter extends RecyclerView.OnScrollListener {
		JazzyHelper mHelper;

		long lastScolledTime = -1;
		int speed = -1;

		public ScrollLisnter() {
			mHelper = new JazzyHelper();
		}

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			switch (newState) {
				case RecyclerView.SCROLL_STATE_SETTLING: // fall through
				case RecyclerView.SCROLL_STATE_DRAGGING:
					mHelper.setScrolling(true);
					break;
				case RecyclerView.SCROLL_STATE_IDLE:
					lastScolledTime = -1;
					speed = -1;
					mHelper.setScrolling(false);

					break;
				default:
					break;
			}

		}

		private int lastDy = 0;

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			int firstVisibleItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
			int visibleItemCount = recyclerView.getChildCount();
			int totalItemCount = recyclerView.getAdapter().getItemCount();
			// 最后一个可见的 Iten的位置
			int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;

			long nowTime = System.currentTimeMillis();
			if (lastScolledTime != -1 && dy != 0) {
				long timed = nowTime - lastScolledTime;
				speed = (int) (dy * 1000 / timed);
				Log.d(TAG, "onScrolled: speed = " + speed);
			}

			if (dy != 0) {
				lastScolledTime = nowTime;
			}

			// 开始滑动的时设置 TransY

			// 然后咋减速的时候 再transY 设置为0
			if (toucView != null && Math.abs(speed) > 80) {
				int touchItem = recyclerView.getChildLayoutPosition(toucView);
				Log.e(TAG, "onScrolled: touchItem = " + touchItem
								+ " , firstVisibleItem = " + firstVisibleItem
								+ " , visibleItemCount = " + visibleItemCount
								+ " , totalItemCount = " + totalItemCount
								+ " , lastVisibleItem = " + lastVisibleItem

				);

				if (lastDy > 0) {
					if (dy < 0) {
						resetTransY();
					}
				} else if (lastDy < 0) {
					if (dy > 0) {
						resetTransY();
					}
				}

				if (dy > 0) {
					for (int i = touchItem + 1; i - touchItem <= 3; i++) {
						if (i > totalItemCount) {
							break;
						}
						if (i > lastVisibleItem) {
							break;
						}
						if (i < firstVisibleItem) {
							break;
						}
						View item = recyclerView.getChildAt(i - firstVisibleItem);
						Log.d(TAG, "onScrolled: dy > 0 i = " + i + " index = " + (i - firstVisibleItem));
						doJazziness(item, i, dy, touchItem);
					}
				} else {
					for (int i = touchItem - 1; touchItem - i <= 3; i--) {
						if (i > totalItemCount) {
							break;
						}
						if (i > lastVisibleItem) {
							break;
						}
						if (i < firstVisibleItem) {
							break;
						}
						Log.d(TAG, "onScrolled:     dy < 0 i = " + i + " index = " + (i - firstVisibleItem));
						View item = recyclerView.getChildAt(i - firstVisibleItem);
						doJazziness(item, i, dy, touchItem);
					}
				}


			} else {
				resetTransY();
			}
			lastDy = dy;
			super.onScrolled(recyclerView, dx, dy);
		}
	}

	private JazzyEffect mTransitionEffect = null;

	private void doJazziness(View item, int position, int scrollDirection, int touchPos) {
		if (item == null) {
			return;
		}
		ViewPropertyAnimator animator = item.animate()
				.setDuration(300)
				.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.cancel();

		scrollDirection = scrollDirection > 0 ? 1 : -1;
		mTransitionEffect.initView(item, position, scrollDirection, touchPos);
		mTransitionEffect.setupAnimation(item, position, scrollDirection, animator);
		animator.start();
	}

	private void resetTransY() {
		for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
			View child = mRecyclerView.getChildAt(i);
			if (child.getTranslationX() != 0) {
				child.animate().cancel();

				ViewPropertyAnimator animator = child.animate()
						.setDuration(300)
						.setInterpolator(new AccelerateDecelerateInterpolator());
				animator.translationY(0);
				animator.start();
			}
		}
	}

}

