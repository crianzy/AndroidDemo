package com.czy.viewpagerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by chenzhiyong on 16/4/27.
 */
public class ImageBox extends RelativeLayout {
    private static final String TAG = "ImageBox";
    Paint mClearPaint;


    public ImageBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mClearPaint = new Paint();
        mClearPaint.setColor(Color.RED);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);
    }

    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 500);
        path.lineTo(200, 500);
        path.lineTo(20, 200);
        path.close();

        Rect rect = new Rect(100, 200, 400, 600);


        if (isClean) {
            int s = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            Log.e(TAG, "onDraw: s = " + s + " , getWidth() = " + getWidth() + " , getHeight() = " + getHeight());
            super.draw(canvas);
            canvas.drawRect(rect, mClearPaint);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }


    boolean isClean = false;


    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }
}
