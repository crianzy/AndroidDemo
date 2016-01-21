package com.czy.qqredpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.imczy.common_util.PhoneUtil;
import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 16/1/18.
 */
public class DrawView extends View {
    public static final String TAG = "DrawView";

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);

    }


    private Paint mPaint;
    private boolean isFirst = true;

    private int originRadius; // 初始的圆的半径
    private int curRadius; // 当前点的半径
    private int originWidth;
    private int originHeight;

    private Point startPoint = new Point();
    private Point currentPosint = new Point();

    RelativeLayout.LayoutParams originLp; // 实际的layoutparams
    ViewGroup.LayoutParams newLp; // 触摸时候的LayoutParams

    private int[] location;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirst && w > 0 && h > 0) {
            isFirst = false;
            originWidth = w;
            originHeight = h;

            originRadius = Math.min(originWidth, originHeight) / 2;
            curRadius = originRadius;

            location = new int[2];
            this.getLocationInWindow(location);
            refreshStartPoint();

            ViewGroup.LayoutParams lp = this.getLayoutParams();
            originLp = (RelativeLayout.LayoutParams) lp;
            newLp = new RelativeLayout.LayoutParams(lp.width, lp.height);
        }
    }

    /**
     * 修改layoutParams后，需要重新设置startPoint
     */
    private void refreshStartPoint() {
        location = new int[2];
        this.getLocationInWindow(location);
        LogUtil.d(TAG, "refreshStartPoint location = " + location[0] + "," + location[1] + ", PhoneUtil.getStatusBarHeight(getContext()=" + PhoneUtil.getStatusBarHeight(getContext()));
        try {
            location[1] = location[1] - PhoneUtil.getStatusBarHeight(getContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LogUtil.d(TAG, "refreshStartPoint location = " + location[0] + "," + location[1]);
        startPoint.set(location[0], location[1]);
        currentPosint.set(location[0], location[1]);
    }

    private boolean isTouched; // 是否是触摸状态

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startCircleX = 0, startCircleY = 0;
        if (isTouched) {
            startCircleX = currentPosint.x + originRadius;
            startCircleY = currentPosint.y + originRadius;
            LogUtil.d(TAG, "onDraw isTouched startCircleX = " + startCircleX + " , startCircleY = " + startCircleY);
            canvas.drawCircle(startCircleX, startCircleY, originRadius, mPaint);
        } else {
            startCircleX = startCircleX + originRadius;
            startCircleY = startCircleY + originRadius;
            canvas.drawCircle(startCircleX, startCircleY, originRadius, mPaint);
        }
    }

    /**
     * 改变某控件的高度
     *
     * @param view
     * @param height
     */

    private void changeViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    private float initDownX;
    private float lastDownX;
    private float initDownY;
    private float lastDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                changeViewSize(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                postInvalidate();
                lastDownX = initDownX = event.getX() + location[0];
                lastDownY = initDownY = event.getY() + location[1];
                LogUtil.d(TAG, "onTouchEvent initDownX = " + initDownX + " initDownY = " + initDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                float dx = x - initDownX;
                float dy = y - initDownY;
                LogUtil.d(TAG, "onTouchEvent dx = " + dx + " , dy = " + dy);
                currentPosint.x = startPoint.x + (int) dx;
                currentPosint.y = startPoint.y + (int) dy;

                LogUtil.d(TAG, "onTouchEvent startPoint.x = " + startPoint.x + " ,  startPoint.y  = " + startPoint.y);
                invalidate();

                lastDownX = x;
                lastDownY = y;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setLayoutParams(originLp);
                isTouched = false;
                break;
        }
        return true;
    }
}
