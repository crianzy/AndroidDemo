package com.czy.drawshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/2/29.
 */
public class MyView extends View {

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint;

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1 = new RectF(150, 30, 150 + 300, 30 + 300);
        canvas.drawArc(oval1, 0, 120, true, mPaint);//小弧形
    }
}
