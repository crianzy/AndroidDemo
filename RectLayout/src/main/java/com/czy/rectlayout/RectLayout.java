package com.czy.rectlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/13.
 */
public class RectLayout extends RelativeLayout {

    public static final String TAG = "RectLayout";

    public RectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mTextPaint;
    Rect mBorderRect;

    private String mTitle = "小鹿记";
    private int mTextHeight = 0;
    private int mTextWidth = 0;

    private int mTitleTextSize = 0;

    private Drawable mBorderDrawable;
    private Rect textRect;

    private Rect mCleanRect;
    private Paint mCleanPaint;

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        mTextPaint.setColor(getResources().getColor(R.color.colorAccent));

        mCleanPaint = new Paint();
        mCleanPaint.setColor(getResources().getColor(R.color.white));
        mCleanPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mBorderDrawable = getResources().getDrawable(R.drawable.rect);

        mTitleTextSize = 36;
        mTextPaint.setTextSize(mTitleTextSize);

        mBorderRect = new Rect();
        textRect = new Rect();
        mCleanRect = new Rect();
        setTitle(mTitle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mBorderRect.set(mTextHeight / 2, mTextHeight / 2, r - l - mTextHeight / 2, b - t - mTextHeight / 2);
        mBorderDrawable.setBounds(mBorderRect);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int txtBgLeft = getWidth() / 2 - mTextWidth / 2 - 30;
        int txtBgRight = getWidth() / 2 + mTextWidth / 2 + 30;
        int txtBgTop = 0;
        int txtBgBottom = mTextHeight;
        mCleanRect.set(txtBgLeft, txtBgTop, txtBgRight, txtBgBottom);

        // 两种方式清除 背景横线

//        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//        mBorderDrawable.draw(canvas);
//        canvas.drawRect(mCleanRect, mCleanPaint);
//        canvas.restoreToCount(saveCount);


        mBorderDrawable.draw(canvas);
        mCleanPaint.setXfermode(null);
        mCleanPaint.setColor(getResources().getColor(R.color.white));
        canvas.drawRect(mCleanRect, mCleanPaint);


        int textLeft = 0;
        int textTop = 0;
        int textRight = getWidth();
        int textBottom = mTextHeight;
        textRect.set(textLeft, textTop, textRight, textBottom);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 水平居中
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mTitle, textRect.centerX(), baseline, mTextPaint);

    }


    public void setTitle(String title) {
        mTitle = title;
        mTextHeight = getStringHeight();
        mTextWidth = getStringWidth(title);

        LogUtil.d(TAG, "mTextHeight = " + mTextHeight + " , mTextWidth = " + mTextWidth);
    }


    private int getStringWidth(String str) {
        return (int) mTextPaint.measureText(str);
    }

    private int getStringHeight() {
        Paint.FontMetrics fr = mTextPaint.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;  //ceil() 函数向上舍入为最接近的整数。
    }


}
