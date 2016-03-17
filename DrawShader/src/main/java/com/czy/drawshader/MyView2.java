package com.czy.drawshader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/2/29.
 */
public class MyView2 extends View {

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mPaint;
    Bitmap mBitmap = null;                              //Bitmap对象
    Bitmap mReflectBitmap = null;


    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //加载图像资源
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.demo)).getBitmap();

        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 倒影效果
        matrix.preScale(1, -1);
        //bmp.getWidth(), 500分别表示重绘后的位图宽高
        mReflectBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
                matrix, true);

    }

    final int reflectionGap = 4;

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect srcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        Rect desRect = new Rect(0, 0, 800, 600);
        canvas.drawBitmap(mBitmap, srcRect, desRect, mPaint);


        Rect srcRect2 = new Rect(0, 0, mBitmap.getWidth(), (int) (mBitmap.getHeight() * 0.8));
        Rect desRect2 = new Rect(0, 600, 800, (int) (mBitmap.getHeight() * 0.8) + mBitmap.getHeight());
        Rect desRect3 = new Rect(0, 600, 800, (int) (mBitmap.getHeight() * 0.2) + mBitmap.getHeight());
//        canvas.drawRect(desRect2, mPaint);


        int count = canvas.saveLayer(0, 600, 0, (float) (mBitmap.getHeight() * 0.8) + mBitmap.getHeight(), null, Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        LinearGradient shader = new LinearGradient(0, 600, 0, (float) (mBitmap.getHeight() * 0.8) + mBitmap.getHeight()
                , 0xee4f34ff
                , 0x9065ff87
                , Shader.TileMode.CLAMP);

        canvas.drawBitmap(mReflectBitmap, srcRect2, desRect3, mPaint);


        mPaint.setShader(shader);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mPaint.setColor(Color.BLACK);
        canvas.drawRect(desRect2, mPaint);

        canvas.restoreToCount(count);
    }

}
