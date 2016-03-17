package com.czy.drawpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
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

    PathEffect mPathEffect;
    Paint mPaint;
    Path mPath;

    private void init() {
        mPathEffect = new CornerPathEffect(3 * 3);
        mPaint = new Paint();
        mPaint.setStrokeWidth(3 * 3);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mPath.moveTo(0, 0);

        for (int i = 1; i <= 15; i++) {
            //生成15个点,随机生成它们的坐标,并将它们连成一条Path
            mPath.lineTo(i * 40, (float) Math.random() * 70);
        }

        colors = new int[]{
                Color.BLACK, Color.BLUE, Color.CYAN,
                Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW
        };
    }

    float phase;
    int[] colors;
    PathEffect[] effects = new PathEffect[7];

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将背景填充成白色
        canvas.drawColor(Color.WHITE);
        //-------下面开始初始化7中路径的效果
        //使用路径效果
        effects[0] = null;
        //使用CornerPathEffect路径效果
        effects[1] = new CornerPathEffect(10);
        //初始化DiscretePathEffect
        effects[2] = new DiscretePathEffect(3.0f, 5.0f);
        //初始化DashPathEffect
        effects[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, phase);
        //初始化PathDashPathEffect
        Path p = new Path();
        p.addRect(0, 0, 8, 8, Path.Direction.CCW);
        effects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE);
        //初始化PathDashPathEffect
        effects[5] = new ComposePathEffect(effects[2], effects[4]);
        effects[6] = new SumPathEffect(effects[4], effects[3]);
        //将画布移到8,8处开始绘制
        canvas.translate(8, 8);
        //依次使用7中不同路径效果,7种不同的颜色来绘制路径
        for (int i = 0; i < effects.length; i++) {
            mPaint.setPathEffect(effects[i]);
            mPaint.setColor(colors[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 80);
        }
        //改变phase值,形成动画效果
        phase += 1;
        invalidate();
    }
}
