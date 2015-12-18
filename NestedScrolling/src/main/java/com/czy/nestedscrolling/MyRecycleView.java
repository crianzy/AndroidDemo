package com.czy.nestedscrolling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.imczy.common_util.log.LogUtil;


/**
 * Created by chenzhiyong on 15/12/14.
 */
public class MyRecycleView extends RecyclerView {

    public static final String TAG = "MyRecycleView";

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        LogUtil.d(TAG, "dispatchNestedPreScroll dx = " + dx
                        + " , dy = " + dy
                        + " , consumed[0] = " + consumed[0]
                        + " , consumed[1] = " + consumed[1]
                        + " , offsetInWindow[1] = " + offsetInWindow[0]
                        + " , offsetInWindow[1] = " + offsetInWindow[1]
                        + " \n "
        );

        boolean r = super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);

        LogUtil.d(TAG, "after dispatchNestedPreScroll dx = " + dx
                        + " , dy = " + dy
                        + " , consumed[0] = " + consumed[0]
                        + " , consumed[1] = " + consumed[1]
                        + " , offsetInWindow[0] = " + offsetInWindow[0]
                        + " , offsetInWindow[1] = " + offsetInWindow[1]
                        + " \n "
        );
        return r;
    }


    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {

        LogUtil.e(TAG, "dispatchNestedScroll dxConsumed = " + dxConsumed
                        + " , dyConsumed = " + dyConsumed
                        + " , dxUnconsumed = " + dxUnconsumed
                        + " , dyUnconsumed = " + dyUnconsumed
                        + " , offsetInWindow[0] = " + offsetInWindow[0]
                        + " , offsetInWindow[1] = " + offsetInWindow[1]
                        + " \n "
        );

        boolean r = super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);

        LogUtil.e(TAG, "dispatchNestedScroll dxConsumed = " + dxConsumed
                        + " , dyConsumed = " + dyConsumed
                        + " , dxUnconsumed = " + dxUnconsumed
                        + " , dyUnconsumed = " + dyUnconsumed
                        + " , offsetInWindow[0] = " + offsetInWindow[0]
                        + " , offsetInWindow[1] = " + offsetInWindow[1]
                        + " \n "
        );

        return r;
    }
}
