package com.imczy.toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/11/1.
 */
public class MyButtom extends TextView {

    public static final String TAG = "MyButtom";

    public MyButtom(Context context) {
        super(context);
    }

    public MyButtom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButtom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ViewParent mParent;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mParent = getParent();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "dispatchTouchEvent event = " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "onTouchEvent event = " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mParent != null) {
                    mParent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mParent != null) {
                    mParent.requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
