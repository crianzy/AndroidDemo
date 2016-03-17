package com.czy.drawhelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by chenzhiyong on 16/3/3.
 */
public class MyViewGroup2 extends LinearLayout {
    private static final String TAG = "MyViewGroup2";

    ViewDragHelper mViewDragHelper;


    public MyViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 这里使用的 ViewDragHelper.create 不是new
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return false;
            }
        });

    }

    private View header;
    private View listBox;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        header = getChildAt(0);
        listBox = getChildAt(1);

        Log.d(TAG, "onAttachedToWindow: header = " + header + " , listBox = " + listBox);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mViewDragHelper.shouldInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
