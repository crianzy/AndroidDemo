package com.czy.recycleviewpagerscrollerdemo;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.imczy.common_util.PhoneUtil;
import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/19.
 */
public class MyScrollLayout extends FrameLayout implements NestedScrollingParent {
    public static final String TAG = "MyScrollLayout";

    private Handler mHandler = new Handler();

    public MyScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private static final int MIN_FLING_VELOCITY = 400; // dips
    private int mMinimumVelocity;


    private void init(Context context) {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        final float density = context.getResources().getDisplayMetrics().density;
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
    }

    private RecyclerView mChildRecyclerView;

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        LogUtil.d(TAG, "onViewAdded child = " + child);
        if (child instanceof RecyclerView) {
            mChildRecyclerView = (RecyclerView) child;

            mChildRecyclerView.addOnScrollListener(mOnScrollListener);
        }
    }

    private Runnable doAfterRunable;

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                LogUtil.d(TAG, "onScrollStateChanged doAfterRunable = " + doAfterRunable);
                if (doAfterRunable != null) {
                    mHandler.post(doAfterRunable);
                }
                doAfterRunable = null;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            LogUtil.d(TAG, "addOnScrollListener  -------- onScrolled dy = " + dy);
        }
    };


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
//        LogUtil.w(TAG, "onNestedScrollAccepted nestedScrollAxes = " + nestedScrollAxes);
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        LogUtil.i(TAG, "onStopNestedScroll ");
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        if (!isFling) {
            // 移动到中间位置;
            doAfterRunable = new Runnable() {
                @Override
                public void run() {
                    LogUtil.e(TAG, " --------------   scroll ------------ ");

                    final MyLinearLayoutManager layoutManager = (MyLinearLayoutManager) mChildRecyclerView.getLayoutManager();
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                    RecyclerView.ViewHolder viewHolder = mChildRecyclerView.findViewHolderForAdapterPosition(firstVisiblePosition);
                    View firstView = viewHolder.itemView;

                    int[] location = new int[2];
                    firstView.getLocationInWindow(location);
                    int firstViewTop = location[1] = location[1] - PhoneUtil.getStatusBarHeight();
                    int firstViewHeight = firstView.getHeight();
                    LogUtil.e(TAG, "firstViewTop = " + firstViewTop + " , firstViewHeight = " + firstViewHeight);
                    int targetPosition = firstVisiblePosition + 1;
                    if (Math.abs(firstViewTop) < firstViewHeight / 2) {
                        targetPosition = firstVisiblePosition;
                    }
                    layoutManager.smoothScrollToPosition(mChildRecyclerView, null, targetPosition);
                    doAfterRunable = null;
                }
            };
        } else {
//            doAfterRunable = new Runnable() {
//                @Override
//                public void run() {
//
//                    LogUtil.e(TAG, " --------------   fling");
//                    final MyLinearLayoutManager layoutManager = (MyLinearLayoutManager) mChildRecyclerView.getLayoutManager();
//                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
//                    if (mFling_direction == FLING_DIRECTION.Down) {
//                        layoutManager.smoothScrollToPosition(mChildRecyclerView, null, firstVisiblePosition + 3);
//                    } else {
//                        int targetPosition = firstVisiblePosition - 3;
//                        if (targetPosition < 0) {
//                            targetPosition = 0;
//                        }
//                        layoutManager.smoothScrollToPosition(mChildRecyclerView, null, targetPosition);
//                    }
//                }
//            };
        }

        isFling = false;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtil.d(TAG, "onNestedScroll dyConsumed = " + dyConsumed + "  , dyUnconsumed = " + dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        LogUtil.e(TAG, "onNestedPreScroll dy = " + dy + "  , consumed = " + consumed[1]);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        LogUtil.d(TAG, "onNestedFling velocityY = " + velocityY + "  , consumed = " + consumed);
        return true;
    }


    @Override
    public boolean onNestedPreFling(final View target, float velocityX, float velocityY) {
//        LogUtil.i(TAG, "onNestedPreFling velocityY = " + velocityY);
        if (Math.abs(velocityY) > mMinimumVelocity) {
            isFling = true;
            mFling_direction = velocityY < 0 ? FLING_DIRECTION.Up : FLING_DIRECTION.Down;

            doAfterRunable = new Runnable() {
                @Override
                public void run() {

                    LogUtil.e(TAG, " --------------   fling");
                    final MyLinearLayoutManager layoutManager = (MyLinearLayoutManager) mChildRecyclerView.getLayoutManager();
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                    if (mFling_direction == FLING_DIRECTION.Down) {
                        layoutManager.smoothScrollToPosition(mChildRecyclerView, null, firstVisiblePosition + 3);
                    } else {
                        int targetPosition = firstVisiblePosition - 1;
                        if (targetPosition < 0) {
                            targetPosition = 0;
                        }
                        layoutManager.smoothScrollToPosition(mChildRecyclerView, null, targetPosition);
                    }
                }
            };
        }
        return true;
    }


    boolean isFling = false;
    private FLING_DIRECTION mFling_direction;

    enum FLING_DIRECTION {
        Up, Down
    }

    @Override
    public int getNestedScrollAxes() {
//        LogUtil.i(TAG, "getNestedScrollAxes");
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }
}
