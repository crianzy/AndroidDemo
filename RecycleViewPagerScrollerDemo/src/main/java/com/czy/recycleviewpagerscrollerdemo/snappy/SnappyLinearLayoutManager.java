package com.czy.recycleviewpagerscrollerdemo.snappy;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.SensorManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.czy.recycleviewpagerscrollerdemo.R;
import com.imczy.common_util.PhoneUtil;
import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/20.
 */
public class SnappyLinearLayoutManager extends LinearLayoutManager implements ISnappyLayoutManager {

    public static final String TAG = "SnappyLinearLayoutManager";

    // These variables are from android.widget.Scroller, which is used, via ScrollerCompat, by
    // Recycler View. The scrolling distance calculation logic originates from the same place. Want
    // to use their variables so as to approximate the look of normal Android scrolling.
    // Find the Scroller fling implementation in android.widget.Scroller.fling().
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private static double FRICTION = 0.84;

    private double deceleration;

    public SnappyLinearLayoutManager(Context context) {
        super(context);
        displayHeight = PhoneUtil.getDisplayHeight(context) - PhoneUtil.getStatusBarHeight();
        calculateDeceleration(context);
    }

    public SnappyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        calculateDeceleration(context);
    }

    private void calculateDeceleration(Context context) {
        deceleration = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.3700787 // inches per meter
                // pixels per inch. 160 is the "default" dpi, i.e. one dip is one pixel on a 160 dpi
                // screen
                * context.getResources().getDisplayMetrics().density * 160.0f * FRICTION;
    }

    @Override
    public int getPositionForVelocity(int velocityX, int velocityY) {
        if (getChildCount() == 0) {
            return 0;
        }
        View view = getChildAt(0);
        TextView txt = (TextView) view.findViewById(R.id.txt);
        LogUtil.d(TAG, "getPositionForVelocity getChildAt(0) = " + txt.getText());

        if (getOrientation() == HORIZONTAL) {
            return calcPosForVelocity(velocityX, getChildAt(0).getLeft(), getChildAt(0).getWidth(), getPosition(getChildAt(0)));
        } else {
            return calcPosForVelocity(velocityY, getChildAt(0).getTop(), getChildAt(0).getHeight(), getPosition(getChildAt(0)));
        }
    }

    private int calcPosForVelocity(int velocity, int scrollPos, int childSize, int currPos) {
        final double v = Math.sqrt(velocity * velocity);
        final double dist = getSplineFlingDistance(v);

        final double tempScroll = scrollPos + (velocity > 0 ? dist : -dist);

        if (velocity < 0) {
            // Not sure if I need to lower bound this here.
            return (int) Math.max(currPos + tempScroll / childSize, 0);
        } else {
            return (int) (currPos + (tempScroll / childSize) + 1);
        }
    }


    private double getSplineFlingDistance(double velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return ViewConfiguration.getScrollFriction() * deceleration * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    private double getSplineDeceleration(double velocity) {
        return Math.log(INFLEXION * Math.abs(velocity)  / (ViewConfiguration.getScrollFriction() * deceleration));
    }

    /**
     * This implementation obviously doesn't take into account the direction of the
     * that preceded it, but there is no easy way to get that information without more
     * hacking than I was willing to put into it.
     */
    @Override
    public int getFixScrollPos() {
        if (this.getChildCount() == 0) {
            return 0;
        }

        final View child = getChildAt(0);
        final int childPos = getPosition(child);

        LogUtil.d(TAG, "getFixScrollPos childPos = " + childPos + " , child.getTop() = " + child.getTop() + " ,  child.getMeasuredHeight() = " + child.getMeasuredHeight());

        if (getOrientation() == HORIZONTAL && Math.abs(child.getLeft()) > child.getMeasuredWidth() / 2) {
            // Scrolled first view more than halfway offscreen
            return childPos + 1;
        } else if (getOrientation() == VERTICAL && Math.abs(child.getTop()) > child.getMeasuredHeight() / 2) {
            // Scrolled first view more than halfway offscreen
            return childPos + 1;
        }
        return childPos;
    }

    private int displayHeight;
    private static final float MILLISECONDS_PER_INCH = 50f;


    @Override
    public int getNextFixedScrollPos(int velocityX, int velocityY) {
        if (this.getChildCount() == 0) {
            return 0;
        }

        final View child = getChildAt(0);
        final int childPos = getPosition(child);

        if (getOrientation() == VERTICAL) {
            if (velocityY > 0) {
                if (childPos == 0 && Math.abs(child.getTop()) < child.getMeasuredHeight() / 2) {
                    return childPos + 1;
                }
                return childPos + 2;
            } else {
                return childPos;
            }
        }
        return childPos;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                       int position) {
        RecyclerView.SmoothScroller smoothScroller = new SnappyLinearLayoutManager.SmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }


    private class SmoothScroller extends LinearSmoothScroller {

        public SmoothScroller(Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return SnappyLinearLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDyToMakeVisible(View view, int snapPreference) {
            final RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (!layoutManager.canScrollVertically()) {
                return 0;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            final int top = layoutManager.getDecoratedTop(view) - params.topMargin;
            final int bottom = layoutManager.getDecoratedBottom(view) + params.bottomMargin;
            final int viewHeight = bottom - top;

//            LogUtil.d(TAG, "calculateDyToMakeVisible top = " + top + " , bottom = " + bottom + " , viewHeight = " + viewHeight);

            final int start = (displayHeight - viewHeight) / 2;
//            LogUtil.d(TAG, "calculateDyToMakeVisible start = " + start);
            final int end = start + viewHeight;

            return calculateDtToFit(top, bottom, start, end, snapPreference);
        }


        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            return super.calculateTimeForScrolling(dx);
        }

        @Override
        protected int calculateTimeForDeceleration(int dx) {
            return super.calculateTimeForDeceleration(dx);
        }

        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        protected int getVerticalSnapPreference() {
            return SNAP_TO_ANY;
        }
    }
}
