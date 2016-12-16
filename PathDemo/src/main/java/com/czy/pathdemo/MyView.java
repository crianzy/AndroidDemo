package com.czy.pathdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenzhiyong on 16/8/16.
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;
    Path mPath = new Path();

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        int avatarWidth = 200;
//        int avatarRadius = 100;
//
//        int width = getWidth();
//        int height = getHeight();
//
//        mPath.moveTo(0, avatarRadius);
//
//        int avatrLeft = (width - avatarWidth) / 2;
//        int avatrRight = avatrLeft + avatarWidth;
//
//        mPath.lineTo(avatrLeft, avatarRadius);
//
//        mPath.arcTo(avatrLeft, 0, avatrRight, avatarWidth, 0, -180, true);
//
////        mPath.moveTo(avatrRight, avatarRadius);
//        mPath.lineTo(width, avatarRadius);
//
//        mPath.lineTo(width, height);
//        mPath.lineTo(0, height);
////        mPath.lineTo(0, avatarRadius);
//        mPath.close();
//
//        Log.e(TAG, "onDraw: mPath.isConvex() = " + mPath.isConvex());
//
//        canvas.drawPath(mPath, mPaint);
//
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int avatarWidth = 200;
        int avatarRadius = 100;

        int width = getWidth();
        int height = getHeight();


        int avatarLeft = (width - avatarWidth) / 2;
        int avatarRight = avatarLeft + avatarWidth;


//        mPath.moveTo(0, avatarRadius);
//        mPath.lineTo(0, height);
//        mPath.lineTo(width, height);
//        mPath.lineTo(width, avatarRadius);
//        mPath.lineTo(avatarRight, avatarRadius);

        mPath.arcTo(avatarLeft, 0, avatarRight, avatarWidth, 0, -180, false);
        mPath.close();

        Log.e(TAG, "onDraw: mPath.isConvex() = " + mPath.isConvex());
        canvas.drawPath(mPath, mPaint);


//        wait();


//        notify();


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "onDetachedFromWindow: ");
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        Log.e(TAG, "onStartTemporaryDetach: ");
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        Log.e(TAG, "onFinishTemporaryDetach: ");
    }
}
