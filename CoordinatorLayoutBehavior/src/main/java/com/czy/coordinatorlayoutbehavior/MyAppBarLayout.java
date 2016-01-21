package com.czy.coordinatorlayoutbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.imczy.common_util.log.LogUtil;

import java.util.List;

/**
 * Created by chenzhiyong on 16/1/12.
 */
@CoordinatorLayout.DefaultBehavior(MyAppBarLayout.Behavior.class)
public class MyAppBarLayout extends LinearLayout {
    public static final String TAG = "MyAppBarLayout";

    public MyAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }


    private boolean hasScrollableChildren() {
//        return getTotalScrollRange() != 0;
        return true;
    }


    public static class Behavior extends HeaderBehavior<MyAppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            LogUtil.d(TAG, "onStartNestedScroll");
            return true;
        }


        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View target, int dx, int dy, int[] consumed) {
            LogUtil.d(TAG, "onNestedPreScroll dy = " + dy);
            if (dy != 0) {
                int min, max;
                if (dy < 0) {
                    // We're scrolling down
                    min = -300;
                    max = 0;
                } else {
                    // We're scrolling up
                    min = -300;
                    max = 0;
                }
                consumed[1] = scroll(coordinatorLayout, child, dy, min, max);

                // 判断是否可以滑动
            }

        }


        @Override
        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View target, float velocityX, float velocityY) {
            LogUtil.d(TAG, "onNestedPreFling");
            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }


        @Override
        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
            LogUtil.d(TAG, "onNestedFling");
            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }


        @Override
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
            LogUtil.d(TAG, "onNestedScroll");
            scroll(coordinatorLayout, child, dyUnconsumed,
                    -300, 0);
        }


        @Override
        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            LogUtil.d(TAG, "onNestedScrollAccepted");
            super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        }

        @Override
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, MyAppBarLayout child, View target) {
            LogUtil.d(TAG, "onStopNestedScroll");
            super.onStopNestedScroll(coordinatorLayout, child, target);
        }
    }


    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {

        private int mOverlayTop;

//
//        @Override
//        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//            LogUtil.w(TAG, "onStartNestedScroll");
//            return true;
//        }
//
//
//        @Override
//        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
//            LogUtil.w(TAG, "onNestedPreScroll");
//            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
//        }
//
//
//        @Override
//        public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
//            LogUtil.w(TAG, "onNestedPreFling");
//            return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//        }
//
//
//        @Override
//        public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//            LogUtil.w(TAG, "onNestedFling");
//            return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//        }
//
//
//        @Override
//        public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//            LogUtil.w(TAG, "onNestedScroll");
//            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        }
//
//
//        @Override
//        public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//            LogUtil.w(TAG, "onNestedScrollAccepted");
//            super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//        }
//
//        @Override
//        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
//            LogUtil.w(TAG, "onStopNestedScroll");
//            super.onStopNestedScroll(coordinatorLayout, child, target);
//        }

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);

            TypedArray a = context.obtainStyledAttributes(attrs,
                    android.support.design.R.styleable.ScrollingViewBehavior_Params);
            mOverlayTop = a.getDimensionPixelSize(
                    android.support.design.R.styleable.ScrollingViewBehavior_Params_behavior_overlapTop, 0);
            a.recycle();
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            // We depend on any AppBarLayouts
            return dependency instanceof MyAppBarLayout;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
            // First lay out the child as normal
            super.onLayoutChild(parent, child, layoutDirection);

            // Now offset us correctly to be in the correct position. This is important for things
            // like activity transitions which rely on accurate positioning after the first layout.
            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, z = dependencies.size(); i < z; i++) {
                if (updateOffset(parent, child, dependencies.get(i))) {
                    // If we updated the offset, break out of the loop now
                    break;
                }
            }
            return true;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                              View dependency) {
            LogUtil.w(TAG, "onDependentViewChanged ");
            updateOffset(parent, child, dependency);
            return false;
        }

        private boolean updateOffset(CoordinatorLayout parent, View child, View dependency) {
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();

            LogUtil.w(TAG, "behavior =  " + behavior);
            if (behavior instanceof Behavior) {
                // Offset the child so that it is below the app-bar (with any overlap)
                final int offset = ((Behavior) behavior).getTopBottomOffsetForScrollingSibling();
                setTopAndBottomOffset(dependency.getHeight() + offset
                        - getOverlapForOffset(dependency, offset));
                return true;
            }
            return false;
        }

        private int getOverlapForOffset(final View dependency, final int offset) {
            if (mOverlayTop != 0 && dependency instanceof MyAppBarLayout) {
                final MyAppBarLayout abl = (MyAppBarLayout) dependency;
                final int totalScrollRange = 300;
                final int preScrollDown = 200;

                if (preScrollDown != 0 && (totalScrollRange + offset) <= preScrollDown) {
                    // If we're in a pre-scroll down. Don't use the offset at all.
                    return 0;
                } else {
                    final int availScrollRange = totalScrollRange - preScrollDown;
                    if (availScrollRange != 0) {
                        // Else we'll use a interpolated ratio of the overlap, depending on offset
                        final float percScrolled = offset / (float) availScrollRange;
                        return MathUtils.constrain(
                                Math.round((1f + percScrolled) * mOverlayTop), 0, mOverlayTop);
                    }
                }
            }
            return mOverlayTop;
        }

        public void setOverlayTop(int overlayTop) {
            mOverlayTop = overlayTop;
        }

        public int getOverlayTop() {
            return mOverlayTop;
        }

        @Override
        View findFirstDependency(List<View> views) {
            for (int i = 0, z = views.size(); i < z; i++) {
                View view = views.get(i);
                if (view instanceof MyAppBarLayout) {
                    return view;
                }
            }
            return null;
        }

        @Override
        int getScrollRange(View v) {
            if (v instanceof MyAppBarLayout) {
                return 300;
            } else {
                return super.getScrollRange(v);
            }
        }
    }


}
