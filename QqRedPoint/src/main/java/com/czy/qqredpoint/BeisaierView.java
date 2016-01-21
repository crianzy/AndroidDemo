package com.czy.qqredpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/1/18.
 */
public class BeisaierView extends View {

    public BeisaierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint;
    Path mPath1;
    Path mPath2;
    Path mPath3;
    Path mPath4;
    Path mPath5;
    Path mPath6;
    Path mPath8;
    private float mX;
    private float mY;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath1 = new Path();
        mPath1.moveTo(100, 200);
        mPath1.cubicTo(91f, 14f, 91f, 87f, 300, 500);

        mPath2 = new Path();
        mPath2.lineTo(200, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //通过画布绘制多点形成的图形
        canvas.drawPath(mPath2, mPaint);
        canvas.drawPath(mPath1, mPaint);
    }

}
