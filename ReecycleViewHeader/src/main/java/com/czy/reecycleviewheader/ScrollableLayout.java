package com.czy.reecycleviewheader;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollableLayout extends LinearLayout {
    private Scroller mScroller;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastMotionX;
    private float mLastMotionY;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    // 方向
    private DIRECTION mDirection;
    private int mHeaderHeight;
    private View mHeaderView;
    private int mExpandHeight = 0;
    private int mSystemVersion;
    private ViewPager mViewPager;
    private int mLastScrollerY;
    private boolean mDisallowIntercept;
    private static final int MIN_FLING_VELOCITY = 400; // dips

    enum DIRECTION {
        UP,
        DOWN
    }

    private int mActivePointerId;
    private static final int INVALID_POINTER = -1;
    private int mMinTouchY = 0;
    private int mMaxTouchY = 0;

    private int mCurrScrollY;
    private boolean mClickHeader;
    private boolean mHeaderExpanded;

    public interface OnScrollListener {

        void onScroll(int currentY, int maxY);

    }

    private OnScrollListener mOnScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    private ScrollableHelper mHelper;

    public ScrollableHelper getHelper() {
        return mHelper;
    }

    public ScrollableLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        mHelper = new ScrollableHelper();
        mScroller = new Scroller(context);
        final float density = context.getResources().getDisplayMetrics().density;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mSystemVersion = Build.VERSION.SDK_INT;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDisallowIntercept = false;
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);

                checkIsClickHeader((int) mLastMotionX, mHeaderHeight, getScrollY());
                checkIsClickHeadExpand((int) mLastMotionY, mHeaderHeight, getScrollY());
                initVelocityTrackerIfNeeded();
                mVelocityTracker.addMovement(ev);
                mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int pointDownIndex = MotionEventCompat.getActionIndex(ev);
                if (pointDownIndex == INVALID_POINTER)
                    break;
                mLastMotionX = MotionEventCompat.getX(ev, pointDownIndex);
                mLastMotionY = MotionEventCompat.getY(ev, pointDownIndex);
                mActivePointerId = MotionEventCompat.getPointerId(ev, pointDownIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDisallowIntercept) {
                    break;
                }
                final int activePointerIndex = getPointerIndex(ev, mActivePointerId);
                if (mActivePointerId == INVALID_POINTER || activePointerIndex == INVALID_POINTER) {
                    break;
                }
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                float x = MotionEventCompat.getX(ev, activePointerIndex);
                float y = MotionEventCompat.getY(ev, activePointerIndex);
                float xDiff = Math.abs(x - mLastMotionX);
                float yDiff = Math.abs(y - mLastMotionY);
                if (Math.atan2(yDiff, xDiff) * (180 / Math.PI) >= 45) {
                    if ((!isHeaderExpandedCompleted() || mHelper.isTop() || mHeaderExpanded)) {
                        if (mViewPager != null) {
                            mViewPager.requestDisallowInterceptTouchEvent(true);
                        }
                        scrollBy(0, (int) ((mLastMotionY - y) + 0.5));
                    }
                }
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
                final int activePointerIndexUp = getPointerIndex(ev, mActivePointerId);
                if (mActivePointerId == INVALID_POINTER || activePointerIndexUp == INVALID_POINTER) {
                    break;
                }
                float xUp = MotionEventCompat.getX(ev, activePointerIndexUp);
                float yUp = MotionEventCompat.getY(ev, activePointerIndexUp);
                float totalXUpDiff = Math.abs(mInitialMotionX - xUp);
                float totalYUpDiff = Math.abs(mInitialMotionY - yUp);
                if (totalYUpDiff >= totalXUpDiff) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    float yVelocity = -mVelocityTracker.getYVelocity();
                    if (Math.abs(yVelocity) > mMinimumVelocity) {
                        mDirection = yVelocity > 0 ? DIRECTION.UP : DIRECTION.DOWN;
                        if (mDirection == DIRECTION.UP && isHeaderExpandedCompleted()) {
                        } else {
                            mScroller.fling(0, getScrollY(), 0, (int) yVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                            mScroller.computeScrollOffset();
                            mLastScrollerY = getScrollY();
                            invalidate();
                        }
                    }
                    if (mClickHeader || !isHeaderExpandedCompleted()) {
                        int action = ev.getAction();
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        boolean dd = super.dispatchTouchEvent(ev);
                        ev.setAction(action);
                        return dd;
                    }
                }
                recycleVelocityTracker();
                reset();
                break;
            case MotionEvent.ACTION_CANCEL:
                final int activePointerIndexCancel = getPointerIndex(ev, mActivePointerId);
                if (mActivePointerId == INVALID_POINTER || activePointerIndexCancel == INVALID_POINTER) {
                    break;
                }
                float xCancel = MotionEventCompat.getX(ev, activePointerIndexCancel);
                float yCancel = MotionEventCompat.getY(ev, activePointerIndexCancel);
                float totalXCancelDiff = Math.abs(mLastMotionX - xCancel);
                float totalYCancelDiff = Math.abs(mLastMotionY - yCancel);
                if (mClickHeader && (totalXCancelDiff > mTouchSlop || totalYCancelDiff > mTouchSlop)) {
                    int action = ev.getAction();
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    boolean dd = super.dispatchTouchEvent(ev);
                    ev.setAction(action);
                    return dd;
                }
                recycleVelocityTracker();
                reset();
                break;
            default:
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    private int getPointerIndex(MotionEvent ev, int id) {
        int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
        if (activePointerIndex == -1) {
            mActivePointerId = INVALID_POINTER;
        }
        return activePointerIndex;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
            mLastMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void reset() {
        mActivePointerId = INVALID_POINTER;
        mLastMotionX = INVALID_POINTER;
        mLastMotionY = INVALID_POINTER;
    }

    public void requestScrollableLayoutDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        mDisallowIntercept = disallowIntercept;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private int getScrollerVelocity(int distance, int duration) {
        if (mScroller == null) {
            return 0;
        } else if (mSystemVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return (int) mScroller.getCurrVelocity();
        } else {
            return distance / duration;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int currY = mScroller.getCurrY();
            if (mDirection == DIRECTION.UP) {// 手势向上划
                if (isHeaderExpandedCompleted()) {
                    int distance = mScroller.getFinalY() - currY;
                    int duration = calcDuration(mScroller.getDuration(), mScroller.timePassed());
                    mHelper.smoothScrollBy(getScrollerVelocity(distance, duration), distance, duration);
                    mScroller.forceFinished(true);
                    return;
                } else {
                    scrollTo(0, currY);
                }
            } else {
                // 手势向下划
                if (mHelper.isTop() || mHeaderExpanded) {
                    int deltaY = (currY - mLastScrollerY);
                    int toY = getScrollY() + deltaY;
                    scrollTo(0, toY);
                    if (mCurrScrollY <= mMinTouchY) {
                        mScroller.forceFinished(true);
                        return;
                    }
                }
                invalidate();
            }
            mLastScrollerY = currY;
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        int scrollY = getScrollY();
        int toY = scrollY + y;
        if (toY >= mMaxTouchY) {
            toY = mMaxTouchY;
        } else if (toY <= mMinTouchY) {
            toY = mMinTouchY;
        }
        y = toY - scrollY;
        super.scrollBy(x, y);
    }


    public boolean isHeaderExpandedCompleted() {
        return mCurrScrollY == mMaxTouchY;
    }

    public boolean isHeaderExpanded() {
        return getScrollY() > 0;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (isHeaderExpanded()) {
            return true;
        }
        return super.canScrollVertically(direction);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y >= mMaxTouchY) {
            y = mMaxTouchY;
        } else if (y <= mMinTouchY) {
            y = mMinTouchY;
        }
        mCurrScrollY = y;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(y, mMaxTouchY);
        }
        super.scrollTo(x, y);
    }

    private void initVelocityTrackerIfNeeded() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void checkIsClickHeader(int downY, int headHeight, int scrollY) {
        mClickHeader = downY + scrollY <= headHeight;
    }

    private void checkIsClickHeadExpand(int downY, int headHeight, int scrollY) {
        if (mExpandHeight <= 0) {
            mHeaderExpanded = false;
        }
        mHeaderExpanded = (downY + scrollY) <= (headHeight + mExpandHeight);
    }

    private int calcDuration(int duration, int timePass) {
        return duration - timePass;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeaderView = getChildAt(0);
        measureChildWithMargins(mHeaderView, widthMeasureSpec, 0, MeasureSpec.UNSPECIFIED, 0);
        mHeaderHeight = mMaxTouchY = mHeaderView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + mMaxTouchY, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onFinishInflate() {
        if (mHeaderView != null && !mHeaderView.isClickable()) {
            mHeaderView.setClickable(true);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt instanceof ViewPager) {
                mViewPager = (ViewPager) childAt;
            }
        }
        super.onFinishInflate();
    }
}
