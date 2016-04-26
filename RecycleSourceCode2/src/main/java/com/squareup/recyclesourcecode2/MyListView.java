package com.squareup.recyclesourcecode2;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.HashMap;

/**
 * Created by chenzhiyong on 16/3/26.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener, View.OnTouchListener {

	private static int SCROLLING_UP = 1;
	private static int SCROLLING_DOWN = 2;

	private int mScrollState;
	private int mScrollDirection;
	private int mTouchedIndex;

	private View mTouchedView;

	private int mScrollOffset;
	private int mStartScrollOffset;

	private boolean mAnimate;

	private HashMap<View, ViewPropertyAnimator> animatedItems;


	public MyListView(Context context) {
		super(context);
		init();
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mScrollState = SCROLL_STATE_IDLE;
		mScrollDirection = 0;
		mStartScrollOffset = -1;
		mTouchedIndex = Integer.MAX_VALUE;
		mAnimate = true;
		animatedItems = new HashMap<>();
		this.setOnTouchListener(this);
		this.setOnScrollListener(this);

	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollState != scrollState) {
			mScrollState = scrollState;
			mAnimate = true;

		}
		if (scrollState == SCROLL_STATE_IDLE) {
			mStartScrollOffset = Integer.MAX_VALUE;
			mAnimate = true;
			startAnimations();
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		if (mScrollState == SCROLL_STATE_TOUCH_SCROLL) {

			if (mStartScrollOffset == Integer.MAX_VALUE) {
				mTouchedView = getChildAt(mTouchedIndex - getPositionForView(getChildAt(0)));
				if (mTouchedView == null) return;

				mStartScrollOffset = mTouchedView.getTop();
			} else if (mTouchedView == null) return;

			mScrollOffset = mTouchedView.getTop() - mStartScrollOffset;
			int tmpScrollDirection;
			if (mScrollOffset > 0) {

				tmpScrollDirection = SCROLLING_UP;

			} else {
				tmpScrollDirection = SCROLLING_DOWN;
			}

			if (mScrollDirection != tmpScrollDirection) {
				startAnimations();
				mScrollDirection = tmpScrollDirection;
			}


			if (Math.abs(mScrollOffset) > 200) {
				mAnimate = false;
				startAnimations();
			}
			Log.d("test", "direction:" + (mScrollDirection == SCROLLING_UP ? "up" : "down") + ", scrollOffset:" + mScrollOffset + ", toucheId:" + mTouchedIndex + ", fvisible:" + firstVisibleItem + ", " +
					"visibleItemCount:" + visibleItemCount + ", " +
					"totalCount:" + totalItemCount);
			int indexOfLastAnimatedItem = mScrollDirection == SCROLLING_DOWN ?
					getPositionForView(getChildAt(0)) + getChildCount() :
					getPositionForView(getChildAt(0));

			//check for bounds
			if (indexOfLastAnimatedItem >= getChildCount()) {
				indexOfLastAnimatedItem = getChildCount() - 1;
			} else if (indexOfLastAnimatedItem < 0) {
				indexOfLastAnimatedItem = 0;
			}

			if (mScrollDirection == SCROLLING_DOWN) {
				setAnimationForScrollingDown(mTouchedIndex - getPositionForView(getChildAt(0)), indexOfLastAnimatedItem, firstVisibleItem);
			} else {
				setAnimationForScrollingUp(mTouchedIndex - getPositionForView(getChildAt(0)), indexOfLastAnimatedItem, firstVisibleItem);
			}
			if (Math.abs(mScrollOffset) > 200) {
				mAnimate = false;
				startAnimations();
				mTouchedView = null;
				mScrollDirection = 0;
				mStartScrollOffset = -1;
				mTouchedIndex = Integer.MAX_VALUE;
				mAnimate = true;
			}
		}
	}

	private void startAnimations() {
		for (ViewPropertyAnimator animator : animatedItems.values()) {
			animator.start();
		}
		animatedItems.clear();
	}

	private void setAnimationForScrollingDown(int indexOfTouchedChild, int indexOflastAnimatedChild, int firstVisibleIndex) {
		for (int i = indexOfTouchedChild + 1; i <= indexOflastAnimatedChild; i++) {
			View v = getChildAt(i);
			v.setTranslationY((-1f * mScrollOffset));
			if (!animatedItems.containsKey(v)) {
				animatedItems.put(v, v.animate().translationY(0).setDuration(300).setStartDelay(50 * i));
			}

		}
	}

	private void setAnimationForScrollingUp(int indexOfTouchedChild, int indexOflastAnimatedChild, int firstVisibleIndex) {
		for (int i = indexOfTouchedChild - 1; i > 0; i--) {
			View v = getChildAt(i);

			v.setTranslationY((-1 * mScrollOffset));
			if (!animatedItems.containsKey(v)) {
				animatedItems.put(v, v.animate().translationY(0).setDuration(300).setStartDelay(50 * (indexOfTouchedChild - i)));
			}

		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				Rect rect = new Rect();
				int childCount = getChildCount();
				int[] listViewCoords = new int[2];
				getLocationOnScreen(listViewCoords);
				int x = (int) event.getRawX() - listViewCoords[0];
				int y = (int) event.getRawY() - listViewCoords[1];
				View child;
				for (int i = 0; i < childCount; i++) {
					child = getChildAt(i);
					child.getHitRect(rect);
					if (rect.contains(x, y)) {
						mTouchedIndex = getPositionForView(child);
						break;
					}
				}
				return false;

		}
		return false;

	}

}
