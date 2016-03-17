package com.czy.porterduff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 16/3/1.
 */
public class MyView extends View {

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    Rect mRedRect;
    Rect mBlueRect;

    Paint mRedPaint;
    Paint mBluePaint;

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mRedRect = new Rect();
        mBlueRect = new Rect();

        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mRedPaint.setAntiAlias(true);
        mRedPaint.setStyle(Paint.Style.FILL);

        mBluePaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mBluePaint.setAntiAlias(true);
        mBluePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = 300;

        // 保存为单独的层
//        int saveCount = canvas.saveLayer(0, 0, 1080, 1920, null, Canvas.ALL_SAVE_FLAG);
        mBluePaint.setColor(Color.GRAY);
        int refLeft = 30;
        mRedRect.set(refLeft, refLeft, refLeft + width, refLeft + width);
        canvas.drawRect(mRedRect, mBluePaint);

        mBluePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mBluePaint.setColor(Color.BLUE);
        int blueLeft = 200;
        mBlueRect.set(blueLeft, blueLeft, blueLeft + width, blueLeft + width);
        canvas.drawRect(mBlueRect, mBluePaint);
//        canvas.restoreToCount(saveCount);

    }
}
