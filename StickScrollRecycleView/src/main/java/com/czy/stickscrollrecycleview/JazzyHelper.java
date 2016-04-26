/*
 * Copyright (C) 2015 Two Toasters
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.czy.stickscrollrecycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;

import java.util.HashSet;

public class JazzyHelper {
	private static final String TAG = "JazzyHelper";
	public static final int DURATION = 300;
	public static final int OPAQUE = 255, TRANSPARENT = 0;

	private boolean mIsScrolling = false;
	private int mFirstVisibleItem = -1;
	private int mLastVisibleItem = -1;
	private int mPreviousFirstVisibleItem = 0;
	private long mPreviousEventTime = -1;
	private double mSpeed = 0;
	private int mMaxVelocity = 0;
	public static final int MAX_VELOCITY_OFF = 0;

	private JazzyEffect mTransitionEffect = null;

	private AbsListView.OnScrollListener mAdditionalOnScrollListener;

	// 是否只在新的Item 上动画
	private boolean mOnlyAnimateNewItems;

	// 是否只在fling 动画
	private boolean mOnlyAnimateOnFling;

	// 是否是fling 事件
	private boolean mIsFlingEvent;

	// 是否是 Grid List
	private boolean mSimulateGridWithList;

	// 已经动画了的集合
	private final HashSet<Integer> mAlreadyAnimatedItems;

	private int touchPos = -1;

	public JazzyHelper() {
		this(null, null);
	}

	public JazzyHelper(Context context, AttributeSet attrs) {
		mAlreadyAnimatedItems = new HashSet<>();
		int maxVelocity = 0;

		setTransitionEffect(new ZipperEffect());
		setMaxAnimationVelocity(maxVelocity);
	}

	public void setOnScrollListener(AbsListView.OnScrollListener l) {
		// hijack the scroll listener setter and have this list also notify the additional listener
		mAdditionalOnScrollListener = l;
	}

	public final void onScrolled(ViewGroup viewGroup, int touchItem, int firstVisibleItem, int visibleItemCount, int totalItemCount, int dy) {
		touchPos = touchItem;
		// 是否应该动画 判断参数
		boolean shouldAnimateItems = (mFirstVisibleItem != -1 && mLastVisibleItem != -1);

//		Log.d(TAG, "onScrolled: touchPos = " + touchPos
//						+ " , mFirstVisibleItem = " + mFirstVisibleItem
//						+ " , visibleItemCount = " + visibleItemCount
//						+ " , totalItemCount = " + totalItemCount
//						+ " , mIsScrolling = " + mIsScrolling
//		);

		// 最后一个可见的 Iten的位置
		int lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
		if (mIsScrolling && shouldAnimateItems) {
			// 如果在滑动 且 可以动画

			// 计算 滑动速度
			setVelocity(dy);

			Log.e(TAG, "onScrolled: speed = " + mSpeed);
			int indexAfterFirst = 0;
			// 第一个 Item 之前的动画
			while (firstVisibleItem + indexAfterFirst < touchItem) {
				View item = viewGroup.getChildAt(indexAfterFirst);
				// 执行动画
				doJazziness(item, firstVisibleItem + indexAfterFirst, dy);
				indexAfterFirst++;
			}

			// 最后一个Item 之后的动画
			int indexBeforeLast = 0;
			while (lastVisibleItem - indexBeforeLast > touchItem) {
				View item = viewGroup.getChildAt(lastVisibleItem - firstVisibleItem - indexBeforeLast);
				doJazziness(item, lastVisibleItem - indexBeforeLast, dy);
				indexBeforeLast++;
			}
		} else if (!shouldAnimateItems) {
			for (int i = firstVisibleItem; i < visibleItemCount; i++) {
				mAlreadyAnimatedItems.add(i);
			}
		}

		mFirstVisibleItem = firstVisibleItem;
		mLastVisibleItem = lastVisibleItem;
	}

	/**
	 * Should be called in onScroll to keep take of current Velocity.
	 * 计算 滑动速度
	 */
	private void setVelocity(int dy) {
		if (mMaxVelocity > MAX_VELOCITY_OFF) {
			long currTime = System.currentTimeMillis();
			long timeToScrollOneItem = currTime - mPreviousEventTime;
			if (mPreviousEventTime > -1) {
				double newSpeed = ((dy / timeToScrollOneItem) * 1000);
				// We need to normalize velocity so different size item don't
				// give largely different velocities.
				if (newSpeed < (0.9f * mSpeed)) {
					mSpeed *= 0.9f;
				} else if (newSpeed > (1.1f * mSpeed)) {
					mSpeed *= 1.1f;
				} else {
					mSpeed = newSpeed;
				}
			}
			mPreviousEventTime = currTime;
		}
	}


	/**
	 * Initializes the item view and triggers the animation.
	 *
	 * @param item            The view to be animated.
	 * @param position        The index of the view in the list.
	 * @param scrollDirection Positive number indicating scrolling down, or negative number indicating scrolling up.
	 */
	private void doJazziness(View item, int position, int scrollDirection) {
		if (mIsScrolling) {
			if (mOnlyAnimateNewItems && mAlreadyAnimatedItems.contains(position))
				return;

			if (mOnlyAnimateOnFling && !mIsFlingEvent)
				return;

			if (mMaxVelocity > MAX_VELOCITY_OFF && mMaxVelocity < mSpeed)
				return;

			if (mSimulateGridWithList) {
				ViewGroup itemRow = (ViewGroup) item;
				for (int i = 0; i < itemRow.getChildCount(); i++)
					doJazzinessImpl(itemRow.getChildAt(i), position, scrollDirection);
			} else {
				doJazzinessImpl(item, position, scrollDirection);
			}

			mAlreadyAnimatedItems.add(position);
		}
	}

	private void doJazzinessImpl(View item, int position, int scrollDirection) {
		ViewPropertyAnimator animator = item.animate()
				.setDuration(DURATION)
				.setInterpolator(new AccelerateDecelerateInterpolator());

		scrollDirection = scrollDirection > 0 ? 1 : -1;
		mTransitionEffect.initView(item, position, scrollDirection, touchPos);
		mTransitionEffect.setupAnimation(item, position, scrollDirection, animator);
		animator.start();
	}

	public void setTransitionEffect(JazzyEffect transitionEffect) {
		mTransitionEffect = transitionEffect;
	}

	public void setShouldOnlyAnimateNewItems(boolean onlyAnimateNew) {
		mOnlyAnimateNewItems = onlyAnimateNew;
	}

	public void setShouldOnlyAnimateFling(boolean onlyFling) {
		mOnlyAnimateOnFling = onlyFling;
	}

	public void setMaxAnimationVelocity(int itemsPerSecond) {
		mMaxVelocity = itemsPerSecond;
	}

	public void setSimulateGridWithList(boolean simulateGridWithList) {
		mSimulateGridWithList = simulateGridWithList;
	}

	public void setScrolling(boolean isScrolling) {
		mIsScrolling = isScrolling;
		//
	}

	public void setFlingEvent(boolean isFlingEvent) {
		mIsFlingEvent = isFlingEvent;
	}

	/**
	 * Notifies the OnScrollListener of an onScroll event, since JazzyListView is the primary listener for onScroll events.
	 */
	private void notifyAdditionalOnScrollListener(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mAdditionalOnScrollListener != null) {
			mAdditionalOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/**
	 * Notifies the OnScrollListener of an onScrollStateChanged event, since JazzyListView is the primary listener for onScrollStateChanged events.
	 */
	private void notifyAdditionalOnScrollStateChangedListener(AbsListView view, int scrollState) {
		if (mAdditionalOnScrollListener != null) {
			mAdditionalOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
}
