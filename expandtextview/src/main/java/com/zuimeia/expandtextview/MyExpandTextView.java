package com.zuimeia.expandtextview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.imczy.common_util.log.LogUtil;

import java.io.File;

/**
 * Created by chenzhiyong on 15/12/1.
 */
public class MyExpandTextView extends TextView implements View.OnClickListener {
    public static final String TAG = "MyExpandTextView";
    public static final int MAX_COLLAPSED_LINES = 4;
    public static final int DEFAULT_ANIM_DURATION = 300;

    public MyExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private Context mContext;

    private int mAnimationDuration;
    private int mMaxCollapsedLines;

    private Drawable mExpandDrawable;
    private Drawable mCollapseDrawable;
    private Drawable mDrawable;

    private int mExpandDrawablePadding;
    private int mDrawableHeight;

    private boolean mIsCollapsed = true;
    private boolean isAnimating = false;

    private ValueAnimator animator;

    private SparseBooleanArray mCollapsedStatus;
    private int mPosition;

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);

        mExpandDrawablePadding = 3 * 3;

        if (mExpandDrawable == null) {
            mExpandDrawable = mContext.getResources().getDrawable(R.drawable.ic_expand_small_holo_light);
        }

        if (mCollapseDrawable == null) {
            mCollapseDrawable = mContext.getResources().getDrawable(R.drawable.ic_collapse_small_holo_light);
        }
        typedArray.recycle();

        setOnClickListener(this);

        // 设置 当显示不下时 ,结尾显示  ...
        setEllipsize(TextUtils.TruncateAt.valueOf("END"));

        // 设置 textView 下面的 展开收缩图片的高度
        mDrawableHeight = mExpandDrawable.getMinimumHeight() + mExpandDrawablePadding * 2;

    }


    @Override
    public void onClick(View v) {
        if (!mIsNeedExpand || isAnimating) {
            return;
        }

        if (mIsCollapsed) {
            expandTxt();
        } else {
            collapseTxt();
        }
    }

    public void collapseTxt() {
        LogUtil.d(TAG, "collapseTxt  getHeight = " + getHeight());
        if (isAnimating) {
            return;
        }
        animator = ValueAnimator.ofInt(getHeight(), mCollapsedTextHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setMaxHeight(value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setMaxLines(mMaxCollapsedLines);
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                setMaxLines(mMaxCollapsedLines);
                isAnimating = false;
            }
        });
        animator.setDuration(mAnimationDuration);
        mDrawable = mExpandDrawable;
        animator.start();
        isAnimating = true;
        mIsCollapsed = true;
        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mIsCollapsed);
        }
    }

    public void expandTxt() {
        LogUtil.d(TAG, "expandTxt  getHeight = " + getHeight());
        if (isAnimating) {
            return;
        }
        animator = ValueAnimator.ofInt(getHeight(), mExpandTextHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setMaxHeight(value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAnimating = false;
            }
        });
        animator.setDuration(mAnimationDuration);
        mDrawable = mCollapseDrawable;
        animator.start();
        mIsCollapsed = false;
        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mIsCollapsed);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        mHaveGetLineCount = false;
        setMaxLines(Integer.MAX_VALUE);
        requestLayout();
        if (animator != null && isAnimating) {
            animator.cancel();
        }
        super.setText(text, type);
    }

    public void setText(CharSequence txt, boolean isCollapsed) {
        LogUtil.w(TAG, "setText");
        mHaveGetLineCount = false;
        mIsCollapsed = isCollapsed;
        setText(txt);
    }


    public void setText(@Nullable CharSequence text, @NonNull SparseBooleanArray collapsedStatus, int position) {
        mCollapsedStatus = collapsedStatus;
        mPosition = position;
        mIsCollapsed = collapsedStatus.get(position, true);
        setText(text, mIsCollapsed);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsNeedExpand && mDrawable != null) {
            int left = getWidth() - mDrawable.getIntrinsicWidth() - mExpandDrawablePadding;
            int right = left + mDrawable.getIntrinsicWidth();
            int top = getHeight() - mDrawable.getIntrinsicHeight() - mExpandDrawablePadding;
            int bottom = top + mDrawable.getIntrinsicHeight();
            Rect drawableRect = new Rect(left, top, right, bottom);
            mDrawable.setBounds(drawableRect);
            mDrawable.draw(canvas);
            canvas.restore();
        }

    }

    int mLineCount;
    boolean mHaveGetLineCount = false;
    boolean mIsNeedExpand = true;

    int mExpandTextHeight;
    int mCollapsedTextHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mHaveGetLineCount || getVisibility() == GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mHaveGetLineCount = true;
        setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mLineCount = getLineCount();
        // 获取展开的Textview 的文字的高度
        mExpandTextHeight = getLayout().getLineTop(getLineCount()) + getCompoundPaddingTop() + getCompoundPaddingBottom();


        if (getLineCount() <= mMaxCollapsedLines) {
            mIsNeedExpand = false;
            setCompoundDrawables(null, null, null, null);
            return;
        }

        // 获取收缩时 的文字的高度
        mCollapsedTextHeight = getLayout().getLineTop(mMaxCollapsedLines) + getCompoundPaddingTop() + getCompoundPaddingBottom();
        LogUtil.w(TAG, "onMeasure mLineCount = " + mLineCount
                        + " , mExpandTextHeight = " + mExpandTextHeight
                        + " , mCollapsedTextHeight = " + mCollapsedTextHeight
        );

        mIsNeedExpand = true;
        mExpandTextHeight = mExpandTextHeight + mDrawableHeight;
        mCollapsedTextHeight = mCollapsedTextHeight + mDrawableHeight;
        LogUtil.e(TAG, "onMeasure"
                        + " , mExpandTextHeight = " + mExpandTextHeight
                        + " , mCollapsedTextHeight = " + mCollapsedTextHeight
                        + " , mDrawableHeight = " + mDrawableHeight
        );

        // 这里需要通过post 放去执行, 才会去重新测量改变了View
        mExpandDrawable.setBounds(0, 0, 0, mDrawableHeight);
        // 这 TextView 底部的 drawable
        setCompoundDrawables(null, null, null, mExpandDrawable);

        if (mIsCollapsed) {
            setMaxLines(mMaxCollapsedLines);
            mDrawable = mExpandDrawable;
        } else {
            setMaxLines(Integer.MAX_VALUE);
            mDrawable = mCollapseDrawable;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }


}
