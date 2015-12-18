package com.czy.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/12/14.
 */
public class Child extends LinearLayout implements NestedScrollingChild {
    public static final String TAG = "Child";

    private NestedScrollingChildHelper mNestedScrollingChildHelper;

    public Child(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
    }


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }


    private void init() {
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        mNestedScrollingChildHelper.setNestedScrollingEnabled(true);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

        float density = getContext().getResources().getDisplayMetrics().density;
        mFlingDistance = (int) (density * MIN_DISTANCE_FOR_FLING);

        for (int i = 0; i < 100; i++) {
            TextView textView = new TextView(getContext());
            textView.setText("text -- " + i);
            addView(textView);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(19000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    private int lastDownX;
    private int lastDownY;

    private int initDownX;
    private int initDownY;

    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;

    private int mMinVelocity;
    private int mMaxVelocity;
    private int mFlingDistance;

    private static final int MIN_DISTANCE_FOR_FLING = 25; // dips

    int[] consumed = new int[2];
    int[] offsetInWindow = new int[2];

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                initDownX = lastDownX = (int) event.getX();
                initDownY = lastDownY = (int) event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = lastDownY - (int) event.getY();
                LogUtil.d(TAG, "ACTION_MOVE dy = " + dy);

                if (dispatchNestedPreScroll(0, dy, consumed, offsetInWindow)) {
                    dy = dy - consumed[1];

                }
                LogUtil.d(TAG, "dispatchNestedPreScroll consumed[1] =  " + consumed[1] + " , offsetInWindow = " + offsetInWindow[1]);

                scrollBy(0, dy);

                int unconsumedX = 0, unconsumedY = 0;
                int consumedX = 0, consumedY = 0;
                lastDownY = (int) event.getY();
                if (dispatchNestedScroll(consumedX, consumedY, unconsumedX, unconsumedY, offsetInWindow)) {
                    lastDownY -= offsetInWindow[0];
                    lastDownY -= offsetInWindow[1];
                    LogUtil.d(TAG, " offsetInWindow = " + offsetInWindow[1]);

                    if (event != null) {
                        event.offsetLocation(offsetInWindow[0], offsetInWindow[1]);
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                break;
        }

        return true;
    }
}
