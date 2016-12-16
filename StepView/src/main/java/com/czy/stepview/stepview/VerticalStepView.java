package com.czy.stepview.stepview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.czy.stepview.R;


/**
 * Created by chenzhiyong on 2016/10/29.
 */

public class VerticalStepView extends ViewGroup {
    private static final String TAG = "VerticalStepView";


    public VerticalStepView(Context context) {
        super(context);
        init(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private BaseStepViewAdapter mAdapter;
    private Context mContext;

    private Drawable mCompleteDrawable;
    private Drawable mDoingDrawable;
    private int drawableWidth;
    private int mLineColor;

    private int mItemDieviderHeight;
    private int mDrawablePaddingItemVal;

    private Paint mLinePaint;

    private void init(Context context, AttributeSet attrs) {
        mCompleteDrawable = context.getResources().getDrawable(R.drawable.icon_point_light);
        mDoingDrawable = context.getResources().getDrawable(R.drawable.icon_point);
        mLineColor = Color.BLACK;

        drawableWidth = mDoingDrawable.getIntrinsicWidth();
        mItemDieviderHeight = 3 * 20;
        mDrawablePaddingItemVal = 3 * 20;

        mLinePaint = new Paint();
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int contentWidth = widthSize - getPaddingLeft() - getPaddingRight() - drawableWidth - mDrawablePaddingItemVal;

        int childMeasureWidthSpec = MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.AT_MOST);
        int childMeasureHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        int allItemHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(childMeasureWidthSpec, childMeasureHeightSpec);
            if (i != getChildCount() - 1) {
                allItemHeight = allItemHeight + getChildAt(i).getMeasuredHeight() + mItemDieviderHeight;
            } else {
                allItemHeight += getChildAt(i).getMeasuredHeight();
            }
        }

        int viewHeight = allItemHeight + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(widthSize, viewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineLeft = getPaddingLeft() + drawableWidth / 2;
        int lineRight = lineLeft;
        int lineTop = getPaddingTop();
        int lineBottom = getHeight() - getPaddingBottom();

        if (getChildCount() > 0) {
            lineBottom = getHeight() - getChildAt(getChildCount() - 1).getMeasuredHeight() - getPaddingBottom();
        }

        canvas.drawLine(lineLeft
                , lineTop
                , lineRight
                , lineBottom
                , mLinePaint);

        int top = getPaddingTop();
        for (int i = 0; i < getChildCount(); i++) {
            int drawableLeft = getPaddingLeft();
            int drawableRight = getPaddingLeft() + drawableWidth;
            int drawableTop = top + 9;
            int drawableBottom = drawableTop + drawableWidth;

            if (i == 0) {
                mDoingDrawable.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
                mDoingDrawable.draw(canvas);
            } else {
                mCompleteDrawable.setBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
                mCompleteDrawable.draw(canvas);
            }

            View view = getChildAt(i);

            if (i != getChildCount() - 1) {
                top = top + view.getMeasuredHeight() + mItemDieviderHeight;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        int layoutLeft = l + getPaddingLeft() + mDoingDrawable.getIntrinsicWidth() + mDrawablePaddingItemVal;
        int layoutRight = b - getPaddingRight();

        int layoutTop = t + getPaddingTop();
        int layoutBottom;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            layoutBottom = layoutTop + view.getMeasuredHeight();
            view.layout(layoutLeft, layoutTop, layoutRight, layoutBottom);

            layoutTop = layoutBottom;
            if (i != getChildCount() - 1) {
                layoutTop += mItemDieviderHeight;
            }
        }
    }

    public void setAdapter(@NonNull BaseStepViewAdapter adapter) {
        mAdapter = adapter;
        handleAdapter(adapter);
        requestLayout();
    }

    private void handleAdapter(@NonNull BaseStepViewAdapter adapter) {
        for (int i = 0; i < adapter.getCount(); i++) {
            addView(adapter.getViewHolder(i).itemView);
        }
    }


    static class ViewHolder {
        View itemView;

        public ViewHolder(View view) {
            itemView = view;
        }
    }
}
