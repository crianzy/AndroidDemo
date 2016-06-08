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
import android.widget.ImageView;

/**
 * Created by chenzhiyong on 16/4/26.
 */
public class MyImageView extends ImageView {

    private static final String TAG = "MyImageView";

    Paint mClearPaint;

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mClearPaint = new Paint();
        mClearPaint.setColor(Color.RED);
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 500);
        path.lineTo(200, 500);
        path.lineTo(20, 200);
        path.close();

        Rect rect = new Rect(100, 200, 400, 600);

        if (isClean) {
            int s = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            Log.e(TAG, "onDraw: s = " + s + " , getWidth() = " + getWidth() + " , getHeight() = " + getHeight());
            super.onDraw(canvas);
            canvas.drawRect(rect, mClearPaint);
            canvas.restore();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    boolean isClean = true;

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }
}
