package com.czy.coordinatorlayoutbehavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class FooterBehaviorDependAppBar extends CoordinatorLayout.Behavior<View> {
    public static final String TAG = "FooterBehaviorDependAppBar";


    public FooterBehaviorDependAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final View child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            ((AppBarLayout) dependency).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    child.setTranslationY(-verticalOffset);
                }
            });
            return true;
        }
        return false;
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        LogUtil.d(TAG, "onLayoutChild child = " + child);

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        LogUtil.d(TAG, "onInterceptTouchEvent ev = " + ev.getAction() + " childe = " + child);
        return false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        LogUtil.i(TAG, "onTouchEvent ev = " + ev.getAction() + " childe = " + child);
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return true;
    }
}
