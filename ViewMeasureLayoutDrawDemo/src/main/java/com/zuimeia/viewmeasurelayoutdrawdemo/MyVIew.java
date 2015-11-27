package com.zuimeia.viewmeasurelayoutdrawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/11/7.
 */
public class MyVIew extends View {
    public static final String TAG = "MyVIew";

    public MyVIew(Context context) {
        super(context);
        init();
    }

    public MyVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        String widthMode = "", heightMode = "";
//        switch (MeasureSpec.getMode(widthMeasureSpec)) {
//            case MeasureSpec.AT_MOST:
//                widthMode = "AT_MOST";
//                break;
//            case MeasureSpec.EXACTLY:
//                widthMode = "EXACTLY";
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                widthMode = "UNSPECIFIED";
//                break;
//        }
//        switch (MeasureSpec.getMode(heightMeasureSpec)) {
//            case MeasureSpec.AT_MOST:
//                heightMode = "AT_MOST";
//                break;
//            case MeasureSpec.EXACTLY:
//                heightMode = "EXACTLY";
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                heightMode = "UNSPECIFIED";
//                break;
//        }
//        LogUtil.d(TAG, "widthMeasureSpec  = " + Integer.toBinaryString(MeasureSpec.getMode(widthMeasureSpec)));
//        LogUtil.d(TAG, "heightMeasureSpec = " + Integer.toBinaryString(MeasureSpec.getMode(heightMeasureSpec)));
//
//        LogUtil.d(TAG, "widthMode = " + MeasureSpec.getSize(widthMeasureSpec)
//                + " widthMode = " + widthMode
//                + " heightMode = " + heightMode
//                + " height = " + MeasureSpec.getSize(heightMeasureSpec));
//
//
////        int height = MeasureSpec.makeMeasureSpec(200, MeasureSpec.UNSPECIFIED
////        );
////        setMeasuredDimension(height, height);
//
//    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
