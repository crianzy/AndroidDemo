package com.czy.rectlayout;

import android.content.Context;
import android.content.res.TypedArray;
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
public class RectLayout2 extends RelativeLayout {

    public static final String TAG = "RectLayout";

    public RectLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private Drawable mBgBorderDrawable;
    private Rect mBgBorderRect;

    private Rect mTopTxtRect;
    private Rect mBottomTxtRect;

    private Rect mTopCleanRect;
    private Rect mBottomCleanRect;
    private int cleanRectPadding = 0;


    private Paint mCleanPaint;
    private Paint mTxtPaint;

    private String mTopTxt = "小鹿记";
    private String mBottomTxt = "2016年2月8日";

    private int mTxtSize;
    private int txtColor;

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RectLayout);
        mBottomTxt = typedArray.getString(R.styleable.RectLayout_bottomTxt);
        mTopTxt = typedArray.getString(R.styleable.RectLayout_topTxt);
        mTxtSize = typedArray.getDimensionPixelSize(R.styleable.RectLayout_txtSize, 36);
        txtColor = typedArray.getColor(R.styleable.RectLayout_txtColor, getResources().getColor(R.color.colorAccent));
        mBgBorderDrawable = typedArray.getDrawable(R.styleable.RectLayout_rectBg);


        mCleanPaint = new Paint();
        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(getResources().getColor(R.color.colorAccent));
        mTxtPaint.setTextSize(mTxtSize);
        mTxtPaint.setColor(txtColor);


        mBgBorderRect = new Rect();

        mTopCleanRect = new Rect();
        mBottomCleanRect = new Rect();
        cleanRectPadding = 30;

        mTopTxtRect = new Rect();
        mBottomTxtRect = new Rect();

        setWillNotDraw(false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int txtHeight = getTxtHeight();
        mBgBorderRect.set(txtHeight / 2, txtHeight / 2, r - l - txtHeight / 2, b - t - txtHeight / 2);
        mBgBorderDrawable.setBounds(mBgBorderRect);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int txtHeight = getTxtHeight();
        int topTxtWidth = getTxtWidth(mTopTxt);
        int bottomWidth = getTxtWidth(mBottomTxt);

        int topCleanRectLeft = getWidth() / 2 - topTxtWidth / 2 - cleanRectPadding;
        int topCleanRectRight = getWidth() / 2 + topTxtWidth / 2 + cleanRectPadding;
        int topCleanRectTop = 0;
        int topCleanRectBottom = txtHeight;
        mTopCleanRect.set(topCleanRectLeft, topCleanRectTop, topCleanRectRight, topCleanRectBottom);

        int bottomCleanRectLeft = getWidth() / 2 - bottomWidth / 2 - cleanRectPadding;
        int bottomCleanRectRight = getWidth() / 2 + bottomWidth / 2 + cleanRectPadding;
        int bottomCleanRectTop = getHeight() - txtHeight;
        int bottomCleanRectBottom = getHeight();
        mBottomCleanRect.set(bottomCleanRectLeft, bottomCleanRectTop, bottomCleanRectRight, bottomCleanRectBottom);


        int topTxtLeft = 0;
        int topTxtTop = 0;
        int topTxtRight = getWidth();
        int topTxtBottom = txtHeight;
        mTopTxtRect.set(topTxtLeft, topTxtTop, topTxtRight, topTxtBottom);

        int bottomTxtLeft = 0;
        int bottomTxtTop = getHeight() - txtHeight;
        int bottomTxtRight = getWidth();
        int bottomTxtBottom = getHeight();
        mBottomTxtRect.set(bottomTxtLeft, bottomTxtTop, bottomTxtRight, bottomTxtBottom);


//        mBgBorderDrawable.draw(canvas);
//        mCleanPaint.setColor(Color.WHITE);
//        mCleanPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(mTopCleanRect, mCleanPaint);
//        canvas.drawRect(mBottomCleanRect, mCleanPaint);


        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        mBgBorderDrawable.draw(canvas);
        mCleanPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(mTopCleanRect, mCleanPaint);
        canvas.drawRect(mBottomCleanRect, mCleanPaint);
        canvas.restoreToCount(saveCount);


        // 水平居中
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = mTxtPaint.getFontMetricsInt();

        int topBaseline = (mTopTxtRect.bottom + mTopTxtRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(mTopTxt, mTopTxtRect.centerX(), topBaseline, mTxtPaint);

        int bottomBaseline = (mBottomTxtRect.bottom + mBottomTxtRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(mBottomTxt, mBottomTxtRect.centerX(), bottomBaseline, mTxtPaint);

    }

    private int getTxtWidth(String str) {
        return (int) mTxtPaint.measureText(str);
    }

    private int getTxtHeight() {
        Paint.FontMetrics fr = mTxtPaint.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;  //ceil() 函数向上舍入为最接近的整数。
    }


    public void setTopTxt(String topTxt) {
        mTopTxt = topTxt;
        invalidate();
    }

    public void setBottomTxt(String bottomTxt) {
        mBottomTxt = bottomTxt;
        invalidate();
    }

    public void setTxtSize(int txtSize) {
        mTxtSize = txtSize;
        mTxtPaint.setTextSize(mTxtSize);
        invalidate();
    }


}
