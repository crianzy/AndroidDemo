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

/**
 * Created by chenzhiyong on 15/12/1.
 */
public class MyExpandTextView2 extends TextView implements View.OnClickListener {
    public static final String TAG = "MyExpandTextView";
    public static final int MAX_COLLAPSED_LINES = 4;
    public static final int DEFAULT_ANIM_DURATION = 300;

    private Context mContext;


    public MyExpandTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    String yourText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Ut volutpat interdum interdum. Nulla laoreet lacus diam, vitae " +
            "sodales sapien commodo faucibus. Vestibulum et feugiat enim. Donec " +
            "semper mi et euismod tempor. Sed sodales eleifend mi id varius. Nam " +
            "et ornare enim, sit amet gravida sapien. Quisque gravida et enim vel " +
            "volutpat. Vivamus egestas ut felis a blandit. Vivamus fringilla " +
            "dignissim mollis. Maecenas imperdiet interdum hendrerit. Aliquam" +
            " dictum hendrerit ultrices. Ut vitae vestibulum dolor. Donec auctor ante" +
            " eget libero molestie porta. Nam tempor fringilla ultricies. Nam sem " +
            "lectus, feugiat eget ullamcorper vitae, ornare et sem. Fusce dapibus ipsum";

    private int mAnimationDuration;
    private int mMaxCollapsedLines;
    private Drawable mExpandDrawable;
    private Drawable mCollapseDrawable;

    private int mExpandDrawablePadding;
    private Drawable mDrawable;

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
        setEllipsize(TextUtils.TruncateAt.valueOf("END"));

    }


    boolean mIsCollapsed = true;
    boolean isAnimating = false;

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

    ValueAnimator animator;

    public void collapseTxt() {
        LogUtil.d(TAG, "collapseTxt");
        if (isAnimating) {
            return;
        }
        animator = ValueAnimator.ofInt(mLineCount, mMaxCollapsedLines);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setMaxLines(value);
                requestLayout();
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
        mDrawable = mExpandDrawable;
        animator.start();
        isAnimating = true;
        mIsCollapsed = true;
        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mIsCollapsed);
        }
    }

    public void expandTxt() {
        LogUtil.d(TAG, "expandTxt");
        if (isAnimating) {
            return;
        }
        animator = ValueAnimator.ofInt(mMaxCollapsedLines, mLineCount);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setMaxLines(value);
                requestLayout();
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

    private SparseBooleanArray mCollapsedStatus;
    private int mPosition;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mHaveGetLineCount) {
            mHaveGetLineCount = true;
            mLineCount = getLineCount();
            LogUtil.e(TAG, "onMeasure mLineCount = " + mLineCount + " , mMaxCollapsedLines = " + mMaxCollapsedLines);
            // 获取行数  判断是否需要 显示收起 按钮
            if (mLineCount < mMaxCollapsedLines) {
                mIsNeedExpand = false;
                LogUtil.d(TAG, "mLineCount < mMaxCollapsedLines");
                post(new Runnable() {
                    @Override
                    public void run() {
                        setCompoundDrawables(null, null, null, null);
                        requestLayout();
                    }
                });
                return;
            }
            LogUtil.d(TAG, "show  expand");
            post(new Runnable() {
                @Override
                public void run() {
                    mIsNeedExpand = true;
                    mExpandDrawable.setBounds(0, 0, 0, mExpandDrawable.getMinimumHeight() + mExpandDrawablePadding * 2);
                    setCompoundDrawables(null, null, null, mExpandDrawable);
                    if (mIsCollapsed) {
                        setMaxLines(mMaxCollapsedLines);
                        mDrawable = mExpandDrawable;
                    } else {
                        setMaxLines(Integer.MAX_VALUE);
                        mDrawable = mCollapseDrawable;
                    }

                }
            });
        }

    }

}
