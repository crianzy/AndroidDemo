package com.zuimeia.toucheventclickscrolldemo;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.imczy.common_util.log.LogUtil;

/**
 * 只处理 纵向滑动事件, 其他事件不做 处理
 */
public class CustomScrollViewGroup extends RelativeLayout {

    public static final String TAG = "CustomScrollViewGroup";

    public CustomScrollViewGroup(Context context) {
        super(context);
        init();
    }

    public CustomScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static final int MIN_DISTANCE_FOR_FLING = 25; // dips

    private OnScrollListener mOnScrollListener;

    private int mTouchSlop;
    protected VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;

    private float mInitialMotionY, mInitialMotionX;


    protected int mActivePointerId = INVALID_POINTER;
    private static final int INVALID_POINTER = -1;

    private boolean mIsUnableToDrag = false;

    private int mFlingDistance;

    private boolean mIsBeingDragged = false;
    boolean mScrollToEnd = false;//标记View是否已经完全消失，OvershootInterpolator回弹时间忽略
    private boolean mQuickReturn = false;

    boolean isTouchOnRecycleView = false;

    private void init() {

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        final float density = getContext().getResources().getDisplayMetrics().density;
        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG, "onInterceptTouchEvent ev = " + ev.getAction());
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP
                || (action != MotionEvent.ACTION_DOWN && mIsUnableToDrag)) {
            endDrag();
            return false;
        }

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // Remember where the motion event started
                int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                if (mActivePointerId == INVALID_POINTER)
                    break;
                mLastMotionX = mInitialMotionX = MotionEventCompat.getX(ev, index);
                mInitialMotionY = mLastMotionY = MotionEventCompat.getY(ev, index);

                mIsBeingDragged = false;
                mIsUnableToDrag = false;
                break;

            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = getPointerIndex(ev, mActivePointerId);
                if (mActivePointerId == INVALID_POINTER) {
                    break;
                }
                final float currentY = MotionEventCompat.getY(ev, activePointerIndex);
                final float deltaY = mLastMotionY - currentY;
                determineDrag(ev);
                break;
        }

        if (!mIsBeingDragged) {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(ev);
        }
        return mIsBeingDragged || mQuickReturn;
    }

    private int getPointerIndex(MotionEvent ev, int id) {
        int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
        if (activePointerIndex == -1)
            mActivePointerId = INVALID_POINTER;
        return activePointerIndex;
    }

    private int mScrollY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG, "onTouchEvent ev = " + ev.getAction());
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        final int action = ev.getAction();


        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // Remember where the motion event started
                int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                mLastMotionY = mInitialMotionY = ev.getY();
                mLastMotionX = mInitialMotionX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    determineDrag(ev);

                    if (mIsUnableToDrag)
                        return false;
                }

                if (mIsBeingDragged) {
                    final int activePointerIndex = getPointerIndex(ev, mActivePointerId);
                    if (mActivePointerId == INVALID_POINTER)
                        break;
                    final float y = MotionEventCompat.getY(ev, activePointerIndex);
                    final float deltaY = mLastMotionY - y;
                    mLastMotionY = y;
                    float oldScrollY = mScrollY;
                    float scrollY = oldScrollY + deltaY;
                    mLastMotionX += scrollY - (int) scrollY;
                    mScrollY = (int) scrollY;

                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScroll(0, deltaY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, mActivePointerId);
                    final int activePointerIndex = getPointerIndex(ev, mActivePointerId);
                    if (mActivePointerId != INVALID_POINTER) {
                        final float y = MotionEventCompat.getY(ev, activePointerIndex);
                        final int totalDelta = (int) (y - mInitialMotionY);// doww -> up detal
                        if (Math.abs(totalDelta) > mFlingDistance && Math.abs(initialVelocity) > mMinimumVelocity) {
                            if (initialVelocity > 0 && totalDelta > 0) {
                                // 向下滑
                                if (mOnScrollListener != null) {
                                    mOnScrollListener.onFling(false);
                                }
                            } else if (initialVelocity < 0 && totalDelta < 0) {
                                // 向上滑
                                if (mOnScrollListener != null) {
                                    mOnScrollListener.onFling(true);
                                }
                            } else {
                                if (mOnScrollListener != null) {
                                    mOnScrollListener.onScrollOver();
                                }
                            }
                        } else {
                            if (mOnScrollListener != null) {
                                mOnScrollListener.onScrollOver();
                            }
                            // 不是 fling 最后是 划上 还是划下  外面判断
                        }

                    }
                    mActivePointerId = INVALID_POINTER;
                    endDrag();
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int indexx = MotionEventCompat.getActionIndex(ev);
                mLastMotionY = MotionEventCompat.getY(ev, indexx);
                mLastMotionX = MotionEventCompat.getX(ev, indexx);
                mActivePointerId = MotionEventCompat.getPointerId(ev, indexx);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                int pointerIndex = getPointerIndex(ev, mActivePointerId);
                if (mActivePointerId == INVALID_POINTER)
                    break;
                mLastMotionY = MotionEventCompat.getY(ev, pointerIndex);
                mLastMotionX = MotionEventCompat.getX(ev, pointerIndex);
                break;
        }


        return true;
    }

    private void determineDrag(MotionEvent ev) {
        final int activePointerId = mActivePointerId;
        final int pointerIndex = getPointerIndex(ev, activePointerId);
        if (activePointerId == INVALID_POINTER || pointerIndex == INVALID_POINTER)
            return;
        final float x = MotionEventCompat.getX(ev, pointerIndex);
        final float dx = x - mLastMotionX;
        final float xDiff = Math.abs(dx);
        final float y = MotionEventCompat.getY(ev, pointerIndex);
        final float dy = y - mLastMotionY;
        final float yDiff = Math.abs(dy);
        if (yDiff > mTouchSlop && yDiff > xDiff) {
            startDrag();
            mLastMotionX = x;
            mLastMotionY = y;
        } else if (xDiff > mTouchSlop) {
            mIsUnableToDrag = true;
        }
    }


    // 开始滑动
    private void startDrag() {
        mIsBeingDragged = true;
        mQuickReturn = false;
        mScrollToEnd = false;
    }

    private void endDrag() {
        mQuickReturn = false;
        mIsBeingDragged = false;
        mIsUnableToDrag = false;
        mActivePointerId = INVALID_POINTER;


        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public interface OnScrollListener {
        void onScroll(float distanceX, float distanceY);

        void onFling(boolean isFlingUp);

        void onScrollOver();

    }
}