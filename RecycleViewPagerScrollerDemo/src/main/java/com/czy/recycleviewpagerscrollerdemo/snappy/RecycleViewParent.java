package com.czy.recycleviewpagerscrollerdemo.snappy;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/20.
 */
public class RecycleViewParent extends LinearLayout implements NestedScrollingParent {
    public static final String TAG = "RecycleViewParent";
    private NestedScrollingParentHelper mNestedScrollingParentHelper;

    public RecycleViewParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        LogUtil.d("onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        LogUtil.w(TAG, "onNestedScrollAccepted nestedScrollAxes = " + nestedScrollAxes);
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        LogUtil.i(TAG, "onStopNestedScroll ");
        mNestedScrollingParentHelper.onStopNestedScroll(target);
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
        LogUtil.d(TAG, "onNestedFling velocityY = " + velocityY + "  , consumed = " + consumed);
        return false;
    }


    @Override
    public boolean onNestedPreFling(final View target, float velocityX, float velocityY) {
        LogUtil.i(TAG, "onNestedPreFling velocityY = " + velocityY);
        return false;
    }

    public int getNestedScrollAxes() {
        LogUtil.i(TAG, "getNestedScrollAxes");
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }
}
