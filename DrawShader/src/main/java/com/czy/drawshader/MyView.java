package com.czy.drawshader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
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
    Bitmap mBitmap = null;                              //Bitmap对象
    Shader mBitmapShader = null;               //Bitmap渲染对象
    Shader mLinearGradient = null;             //线性渐变渲染对象
    Shader mComposeShader = null;           //混合渲染对象
    Shader mRadialGradient = null;             //环形渲染对象
    Shader mSweepGradient = null;             //梯度渲染对象


    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //加载图像资源
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();

        //创建Bitmap渲染对象
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);

        //创建线性渲染对象
        int mColorLinear[] = {0x904f34ff, 0x0065ff87};
        mLinearGradient = new LinearGradient(0, 0, 1, 300, mColorLinear, null,
                Shader.TileMode.CLAMP);
//        mLinearGradient = new LinearGradient(0, 0, 0, 300
//                , 0x904f34ff
//                , 0x0065ff87
//                , Shader.TileMode.CLAMP);

        //创建环形渲染对象
        int mColorRadial[] = {Color.GREEN, Color.RED, Color.BLUE, Color.WHITE};
        mRadialGradient = new RadialGradient(350, 325, 75, mColorRadial, null,
                Shader.TileMode.REPEAT);

        //创建混合渲染对象
        mComposeShader = new ComposeShader(mLinearGradient, mRadialGradient, PorterDuff.Mode.DARKEN);

        //创建梯形渲染对象
        int mColorSweep[] = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        mSweepGradient = new SweepGradient(370, 495, mColorSweep, null);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);      //背景置为灰色

        //绘制Bitmap渲染的椭圆
//        mPaint.setShader(mBitmapShader);
//        canvas.drawOval(new RectF(90, 20, 90 + mBitmap.getWidth() * 2,
//                20 + mBitmap.getHeight() * 2), mPaint);

        //绘制线性渐变的矩形

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));

        mPaint.setColor(Color.RED);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(0, 0, 300, 300, mPaint);
//
//        //绘制环形渐变的圆
//        mPaint.setShader(mRadialGradient);
//        canvas.drawCircle(350, 325, 200, mPaint);
//
//        //绘制混合渐变(线性与环形混合)的矩形
//        mPaint.setShader(mComposeShader);
//        canvas.drawRect(10, 420, 250, 570, mPaint);
//
//        //绘制梯形渐变的矩形
//        mPaint.setShader(mSweepGradient);
//        canvas.drawRect(270, 420, 470, 570, mPaint);
    }

}
