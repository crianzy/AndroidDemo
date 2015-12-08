package com.czy.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
 * Created by chenzhiyong on 15/12/4.
 */
public class WaveView extends View {

    // 波纹颜色
    private static final int WAVE_PAINT_COLOR = 0x880000aa;

    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;

    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    // 第二条水波移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 5;

    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;

    private int mWidth;
    private int mHeight;

    float mCycleFactorW;

    float[] mYPositions;
    float[] mResetOneYPositions;
    float[] mResetTwoYPositions;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
// 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRANSLATE_X_SPEED_ONE, getResources().getDisplayMetrics());
        mXOffsetSpeedTwo = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRANSLATE_X_SPEED_TWO, getResources().getDisplayMetrics());

        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        // 去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        resetPositonY();
        for (int i = 0; i < mWidth; i++) {

            // 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
            // 绘制第一条水波纹
            canvas.drawLine(i, mHeight - mResetOneYPositions[i] - 400, i,
                    mHeight,
                    mWavePaint);

            // 绘制第二条水波纹
            canvas.drawLine(i, mHeight - mResetTwoYPositions[i] - 400, i,
                    mHeight,
                    mWavePaint);
        }

        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mWidth) {
            mXTwoOffset = 0;
        }

        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
//        postInvalidate();
        postInvalidateDelayed(30);
    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 记录下view的宽高
        mWidth = w;
        mHeight = h;

        // 用于保存原始波纹的y值
        mYPositions = new float[mWidth];
        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mWidth];
        // 用于保存波纹二的y值
        mResetTwoYPositions = new float[mWidth];

        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mWidth);

        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }
}
