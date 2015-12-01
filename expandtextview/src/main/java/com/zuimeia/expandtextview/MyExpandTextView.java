package com.zuimeia.expandtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhiyong on 15/12/1.
 */
public class MyExpandTextView extends View {
    public static final int MAX_COLLAPSED_LINES = 8;
    public static final int DEFAULT_ANIM_DURATION = 300;

    private Context mContext;


    public MyExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }


    private String contentString;
    private Drawable mDrawable;

    private int mAnimationDuration;
    private int mMaxCollapsedLines;
    private Drawable mExpandDrawable;
    private Drawable mCollapseDrawable;

    private Paint mTxtPaint;

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);

        if (mExpandDrawable == null) {
            mExpandDrawable = mContext.getResources().getDrawable(R.drawable.ic_expand_small_holo_light);
        }
        if (mCollapseDrawable == null) {
            mCollapseDrawable = mContext.getResources().getDrawable(R.drawable.ic_collapse_small_holo_light);
        }

        typedArray.recycle();

        mTxtPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec) + getPaddingLeft() + getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) + getPaddingTop() + getPaddingBottom();
    }

}
