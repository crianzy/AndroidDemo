package com.imczy.scoller;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/10/31.
 */
public class MyViewGroup extends RelativeLayout {
    public static final String TAG = "MyViewGroup";

    boolean flag = true;
    private Scroller mScroller;

    public MyViewGroup(Context context) {
        super(context);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();

            LogUtil.d(TAG, "mScroller.getCurrX() = " + mScroller.getCurrX() + " , getScrollX() = " + getScrollX());
        }
    }

    public void bingScroll() {
        LogUtil.d(TAG, "bingScroll falg = " + flag);
        if (flag) {
            mScroller.startScroll(0, 0, 500, 0, 1000);
        } else {
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 1000);
        }
        flag = !flag;
        invalidate();
    }
}
