package com.czy.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/12/14.
 */
public class Parent extends LinearLayout implements NestedScrollingParent {
    public static final String TAG = "Parent";

    private NestedScrollingParentHelper mNestedScrollingParentHelper;

    private int consumedy = 0;

    public Parent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtil.d(TAG, "onNestedScroll target = " + target + " , dxConsumed = " + dxConsumed + " , dyConsumed = " + dyConsumed + " , dxUnconsumed = " + dxUnconsumed + " , dyUnconsumed = " + dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        consumedy += dy;
        if (consumedy <= 600 && consumedy > 0) {
            consumed[1] = dy;
        } else if (consumedy < 0) {
            consumed[1] = dy - consumedy;
            consumedy = 0;
        } else if (consumedy > 600) {
            consumed[1] = dy - (consumedy - 600);
            consumedy = 600;
        }

        setTranslationY(-consumedy);
        LogUtil.d(TAG, "onNestedPreScroll dx = " + dx + " dy = " + dy);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }
}
