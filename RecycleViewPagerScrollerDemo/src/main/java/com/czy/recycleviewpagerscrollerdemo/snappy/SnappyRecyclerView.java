package com.czy.recycleviewpagerscrollerdemo.snappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/20.
 */
public final class SnappyRecyclerView extends RecyclerView {
    public static final String TAG = "SnappyRecyclerView";

    public SnappyRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public SnappyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private int mFastFlingVelocity;
    private static final float FAST_FLING_VELOCITY_RATE = 0.8f;

    public SnappyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    private void init(Context context) {
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mFastFlingVelocity = (int) (mMaxFlingVelocity * FAST_FLING_VELOCITY_RATE);

        LogUtil.e(TAG, "mMinFlingVelocity = " + mMinFlingVelocity + " , mMaxFlingVelocity = " + mMaxFlingVelocity + " , mFastFlingVelocity = " + mFastFlingVelocity);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        final LayoutManager lm = getLayoutManager();

        LogUtil.d(TAG, " , velocityY = " + velocityY);

        int originaleVelocityX = velocityX;
        int originaleVelocityY = velocityY;

        if (!(lm instanceof ISnappyLayoutManager)) {
            super.smoothScrollToPosition(((ISnappyLayoutManager) getLayoutManager()).getPositionForVelocity(velocityX, velocityY));
            return super.fling(velocityX, velocityY);
        }

        ISnappyLayoutManager iSnappyLayoutManager = (ISnappyLayoutManager) lm;
        if (isLayoutFrozen()) {
            return false;
        }

        final boolean canScrollHorizontal = lm.canScrollHorizontally();
        final boolean canScrollVertical = lm.canScrollVertically();

        if (!canScrollHorizontal || Math.abs(velocityX) < mMinFlingVelocity) {
            velocityX = 0;
        }
        if (!canScrollVertical || Math.abs(velocityY) < mMinFlingVelocity) {
            velocityY = 0;
        }
        if (velocityX == 0 && velocityY == 0) {
            // 这里表示时Scroll 没有fling  则只需要 修正位置即可
            int fixedPosition = iSnappyLayoutManager.getFixScrollPos();
            super.smoothScrollToPosition(fixedPosition);
            return true;
        }

        if (!dispatchNestedPreFling(velocityX, velocityY)) {
            final boolean canScroll = canScrollHorizontal || canScrollVertical;
            dispatchNestedFling(velocityX, velocityY, canScroll);

            if (canScroll) {
                // 这里可以滑动

                velocityX = Math.max(-mMaxFlingVelocity, Math.min(velocityX, mMaxFlingVelocity));
                velocityY = Math.max(-mMaxFlingVelocity, Math.min(velocityY, mMaxFlingVelocity));
                LogUtil.d(TAG, "changed velocityY = " + velocityY);

                if (Math.abs(velocityY) > mFastFlingVelocity || Math.abs(velocityX) > mFastFlingVelocity) {
                    // 如果 速度很大, 那么 直接fling  但是 在停止是 也要修正位置
//                    super.fling(originaleVelocityX, originaleVelocityY);
                    int fixedPosition = iSnappyLayoutManager.getPositionForVelocity(velocityX, velocityY);
                    LogUtil.d(TAG, "fast     fixedPosition = " + fixedPosition);
                    super.smoothScrollToPosition(fixedPosition);

                } else {
                    int fixedPosition = iSnappyLayoutManager.getNextFixedScrollPos(velocityX, velocityY);
                    LogUtil.d(TAG, "fixedPosition = " + fixedPosition);
                    super.smoothScrollToPosition(fixedPosition);
                    // 如数速度 适中 那么 只滑动一项
                }


                return true;
            }
        }
        // 父View 吃掉了滑动事件
        return false;
    }

}