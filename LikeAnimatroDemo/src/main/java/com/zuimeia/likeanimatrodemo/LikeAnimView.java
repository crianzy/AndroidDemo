package com.zuimeia.likeanimatrodemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chenzhiyong on 16/9/14.
 */

public class LikeAnimView extends View {

    private static final String TAG = "LikeAnimView";


    public LikeAnimView(Context context) {
        super(context);
        init();
    }

    public LikeAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    Paint mCirclePaint;
    int mCircleStrokeWidth;
    int mCircleRadius = 1;
    int mCx;
    int mCy;
    int mMinSize;

    Rect topRect = new Rect();
    Rect topInitRect = new Rect();

    Rect leftRect = new Rect();
    Rect leftInitRect = new Rect();

    Rect rightRect = new Rect();
    Rect rightInitRect = new Rect();

    Rect bottomRect = new Rect();
    Rect bottomInitRect = new Rect();


    int stickWidth;
    Paint stickPaint;

    Drawable loveDrawable;

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        loveDrawable = getResources().getDrawable(R.drawable.like);

        stickPaint = new Paint();
        stickPaint.setColor(getResources().getColor(R.color.deep_red));
        stickPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMinSize = Math.min(getWidth(), getHeight());
        mCx = mCy = mMinSize / 2;

        stickWidth = mMinSize / 20;
        initTopRect();
        initLeftRect();
        initRightRect();
        initBottomRect();

    }

    private void initTopRect() {
        topRect.left = (mMinSize - stickWidth) / 2;
        topRect.right = topRect.left + stickWidth;
        topRect.bottom = mMinSize / 2;
        topRect.top = topRect.bottom;
        topInitRect.set(topRect);
    }

    private void initLeftRect() {
        leftRect.left = mMinSize / 2;
        leftRect.right = mMinSize / 2;
        leftRect.top = (mMinSize - stickWidth) / 2;
        leftRect.bottom = leftRect.top + stickWidth;
        leftInitRect.set(leftRect);
    }

    private void initRightRect() {
        rightRect.left = mMinSize / 2;
        rightRect.right = mMinSize / 2;
        rightRect.top = (mMinSize - stickWidth) / 2;
        rightRect.bottom = rightRect.top + stickWidth;

        rightInitRect.set(rightRect);
    }

    private void initBottomRect() {
        bottomRect.left = (mMinSize - stickWidth) / 2;
        bottomRect.right = bottomRect.left + stickWidth;
        bottomRect.bottom = mMinSize / 2;
        bottomRect.top = topRect.bottom;

        bottomInitRect.set(bottomRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        canvas.drawCircle(mCx, mCy, mCircleRadius, mCirclePaint);

        loveDrawable.draw(canvas);

        canvas.drawRoundRect(new RectF(topRect), 30, 30, stickPaint);
        canvas.drawRoundRect(new RectF(leftRect), 30, 30, stickPaint);
        canvas.drawRoundRect(new RectF(rightRect), 30, 30, stickPaint);
        canvas.drawRoundRect(new RectF(bottomRect), 30, 30, stickPaint);

    }

    private static int STROKE_INCREASE_ANIM_TIME = 300;
    private static int CIRCLE_RADIUS_INCREASE_ANIM_TIME = 300;
    private static int LIKE_DRAWABLE_ANIM_START_DELAY_TIME = STROKE_INCREASE_ANIM_TIME + CIRCLE_RADIUS_INCREASE_ANIM_TIME * 8 / 9;
    private static int LIKE_DRAWABLE_ANIM_TIME = 300;

    private static final int RECT_INCREASE_ANIM_TIME = STROKE_INCREASE_ANIM_TIME;
    private static final int RECT_TRANS_ANIM_TIME = 100;
    private static final int RECT_DECREASE_ANIM_TIME = STROKE_INCREASE_ANIM_TIME;
    private static final int RECT_ALPHA_ANIM_TIME = STROKE_INCREASE_ANIM_TIME;

    private static float MAX_STROKE_SCALE = 2f / 3f;


    private ValueAnimator strokeIncreaseAnim;
    private ValueAnimator circleRadiusIncreaseAnim;
    private ValueAnimator strokeDecreaseAnim;
    private ValueAnimator likeDrawableAnim;

    public boolean isAnimating() {
        Log.e(TAG, "isAnimating: (alphaAnim != null && alphaAnim.isRunning() = " + (alphaAnim != null && alphaAnim.isRunning()));
        Log.e(TAG, "isAnimating: (alphaAnim != null && alphaAnim.isRunning() = " + (circleRadiusIncreaseAnim != null && circleRadiusIncreaseAnim.isRunning()));
        Log.e(TAG, "isAnimating: (strokeDecreaseAnim != null && strokeDecreaseAnim.isRunning())  = " + (strokeDecreaseAnim != null && strokeDecreaseAnim.isRunning()));
        Log.e(TAG, "isAnimating: (likeDrawableAnim != null && likeDrawableAnim.isRunning()) = " + ((likeDrawableAnim != null && likeDrawableAnim.isRunning())));

        if ((alphaAnim != null && alphaAnim.isRunning()) ||
                (circleRadiusIncreaseAnim != null && circleRadiusIncreaseAnim.isRunning()) ||
                (strokeDecreaseAnim != null && strokeDecreaseAnim.isRunning()) ||
                (likeDrawableAnim != null && likeDrawableAnim.isRunning()) || isStickAnim
                ) {
            return true;
        }


        return false;
    }


    public void startAnim() {
        if (isAnimating()) {
            Log.e(TAG, "startAnim: isAnimating = ");
            return;
        }
        mCircleRadius = 1;
        mCircleStrokeWidth = 0;
        loveDrawable.setBounds(0, 0, 0, 0);
        topRect.set(topInitRect);
        leftRect.set(leftInitRect);
        rightRect.set(rightInitRect);
        bottomRect.set(bottomInitRect);
        postInvalidate();

        int maxStrokeWidth = (int) (mMinSize * MAX_STROKE_SCALE);

        strokeIncreaseAnim = ValueAnimator.ofInt(0, maxStrokeWidth);
        strokeIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                mCircleStrokeWidth = val;
                postInvalidate();
            }
        });

        circleRadiusIncreaseAnim = ValueAnimator.ofInt(1, mMinSize / 2);
        circleRadiusIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                mCircleRadius = val;
                postInvalidate();
            }
        });
        circleRadiusIncreaseAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCircleRadius = 1;
                mCircleStrokeWidth = 0;
                postInvalidate();
            }
        });

        strokeDecreaseAnim = ValueAnimator.ofInt(maxStrokeWidth, 0);
        strokeDecreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                mCircleStrokeWidth = val;

                if (mCircleRadius + mCircleStrokeWidth / 2 >= mMinSize / 2) {
                    mCircleStrokeWidth = (mMinSize / 2 - mCircleRadius) * 2;
                }
                postInvalidate();
            }
        });

        strokeIncreaseAnim.setDuration(STROKE_INCREASE_ANIM_TIME);
        strokeIncreaseAnim.setInterpolator(new AccelerateInterpolator());

        circleRadiusIncreaseAnim.setDuration(STROKE_INCREASE_ANIM_TIME);
        circleRadiusIncreaseAnim.setStartDelay(CIRCLE_RADIUS_INCREASE_ANIM_TIME);
        circleRadiusIncreaseAnim.setInterpolator(new DecelerateInterpolator());

        strokeDecreaseAnim.setDuration(STROKE_INCREASE_ANIM_TIME);
        strokeDecreaseAnim.setStartDelay(CIRCLE_RADIUS_INCREASE_ANIM_TIME);
        strokeDecreaseAnim.setInterpolator(new DecelerateInterpolator());


        strokeIncreaseAnim.start();
        strokeDecreaseAnim.start();
        circleRadiusIncreaseAnim.start();

        likeDrawableAnim = ValueAnimator.ofInt(0, loveDrawable.getIntrinsicWidth());
        likeDrawableAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                int left = (mMinSize - val) / 2;
                int right = left + val;
                int top = (mMinSize - val) / 2;
                int bottom = top + val;
                loveDrawable.setBounds(left, top, right, bottom);
                postInvalidate();
            }
        });
        likeDrawableAnim.setInterpolator(new OvershootInterpolator());
        likeDrawableAnim.setDuration(LIKE_DRAWABLE_ANIM_TIME);
        likeDrawableAnim.setStartDelay(LIKE_DRAWABLE_ANIM_START_DELAY_TIME);
        likeDrawableAnim.start();

        startStickAnim(DIRECTION_TOP);
        startStickAnim(DIRECTION_LEFT);
        startStickAnim(DIRECTION_RIGHT);
        startStickAnim(DIRECTION_BOTTOM);

        startStickAlphaAnim();
    }


    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_BOTTOM = 3;

    @IntDef({DIRECTION_TOP, DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STICK_DIRECTION {

    }

    private boolean isStickAnim = false;

    public void startStickAnim(@STICK_DIRECTION final int direction) {

        final int firstTop = mMinSize / 6;
        final int firstBottom = mMinSize / 2 - mMinSize / 6;
        int initIncreaseVal = mMinSize / 2;

        int transY = -mMinSize / 16;
        stickPaint.setAlpha(255);

        isStickAnim = true;
        ValueAnimator topIncreaseAnim = ValueAnimator.ofInt(initIncreaseVal, firstTop);
        topIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                if (direction == DIRECTION_TOP) {
                    topRect.top = val;
                } else if (direction == DIRECTION_LEFT) {
                    leftRect.left = val;
                } else if (direction == DIRECTION_RIGHT) {
                    rightRect.right = mMinSize - val;
                } else if (direction == DIRECTION_BOTTOM) {
                    bottomRect.bottom = mMinSize - val;
                }
                postInvalidate();
            }
        });

        ValueAnimator bottomIncreaseAnim = ValueAnimator.ofInt(initIncreaseVal, firstBottom);
        bottomIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                if (direction == DIRECTION_TOP) {
                    topRect.bottom = val;
                } else if (direction == DIRECTION_LEFT) {
                    leftRect.right = val;
                } else if (direction == DIRECTION_RIGHT) {
                    rightRect.left = mMinSize - val;
                } else if (direction == DIRECTION_BOTTOM) {
                    bottomRect.top = mMinSize - val;
                }
                postInvalidate();
            }
        });


        ValueAnimator transYAnim = ValueAnimator.ofInt(0, transY);
        transYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                if (direction == DIRECTION_TOP) {
                    topRect.top = firstTop + val;
                    topRect.bottom = firstBottom + val;

                } else if (direction == DIRECTION_LEFT) {
                    leftRect.left = firstTop + val;
                    leftRect.right = firstBottom + val;

                } else if (direction == DIRECTION_RIGHT) {
                    rightRect.right = mMinSize - (firstTop + val);
                    rightRect.left = mMinSize - (firstBottom + val);

                } else if (direction == DIRECTION_BOTTOM) {
                    bottomRect.top = mMinSize - (firstBottom + val);
                    bottomRect.bottom = mMinSize - (firstTop + val);
                }
                postInvalidate();
            }
        });
        ValueAnimator bottomDecreaseAnim = null;
        if (direction == DIRECTION_TOP) {
            bottomDecreaseAnim = ValueAnimator.ofInt(transY + firstBottom, firstTop);
        } else if (direction == DIRECTION_LEFT) {
            bottomDecreaseAnim = ValueAnimator.ofInt(transY + firstBottom, firstTop);
        } else if (direction == DIRECTION_RIGHT) {
            bottomDecreaseAnim = ValueAnimator.ofInt(mMinSize - (firstBottom + transY), mMinSize - firstTop);
        } else if (direction == DIRECTION_BOTTOM) {
            bottomDecreaseAnim = ValueAnimator.ofInt(mMinSize - (firstBottom + transY), mMinSize - firstTop);
        }
        bottomDecreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                if (direction == DIRECTION_TOP) {
                    topRect.bottom = val;
                } else if (direction == DIRECTION_LEFT) {
                    leftRect.right = val;
                } else if (direction == DIRECTION_RIGHT) {
                    rightRect.left = val;
                } else if (direction == DIRECTION_BOTTOM) {
                    bottomRect.top = val;
                }
                postInvalidate();
            }
        });


        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(topIncreaseAnim, bottomIncreaseAnim);
        animationSet.setInterpolator(new AccelerateInterpolator());

        animationSet.setDuration(RECT_INCREASE_ANIM_TIME);
        animationSet.start();
        animationSet.setInterpolator(new AccelerateInterpolator());

        transYAnim.setDuration(RECT_TRANS_ANIM_TIME);
        transYAnim.setStartDelay(RECT_INCREASE_ANIM_TIME);
        transYAnim.start();

        bottomDecreaseAnim.setStartDelay(RECT_TRANS_ANIM_TIME + RECT_INCREASE_ANIM_TIME);
        bottomDecreaseAnim.setDuration(RECT_DECREASE_ANIM_TIME);
        bottomDecreaseAnim.start();
        bottomDecreaseAnim.setInterpolator(new AccelerateInterpolator());

    }

    private ValueAnimator alphaAnim;

    private void startStickAlphaAnim() {
        alphaAnim = ValueAnimator.ofInt(255, 0);
        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (int) animation.getAnimatedValue();
                stickPaint.setAlpha(val);
                postInvalidate();
            }
        });
        alphaAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isStickAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isStickAnim = false;
            }
        });

        alphaAnim.setDuration(RECT_ALPHA_ANIM_TIME);
        alphaAnim.setStartDelay(RECT_TRANS_ANIM_TIME + RECT_INCREASE_ANIM_TIME + RECT_DECREASE_ANIM_TIME);
        alphaAnim.setInterpolator(new DecelerateInterpolator());
        alphaAnim.start();
    }


//
//    public void startLeftStickAnim() {
//        final int firstLeft = mMinSize / 6;
//        final int firstRight = mMinSize / 2 - mMinSize / 6;
//        int transY = -mMinSize / 16;
//        stickPaint.setAlpha(255);
//
//        ValueAnimator topIncreaseAnim = ValueAnimator.ofInt(leftRect.left, firstLeft);
//        topIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int val = (int) animation.getAnimatedValue();
//                leftRect.left = val;
//                postInvalidate();
//            }
//        });
//
//        ValueAnimator bottomIncreaseAnim = ValueAnimator.ofInt(leftRect.right, firstRight);
//        bottomIncreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int val = (int) animation.getAnimatedValue();
//                leftRect.right = val;
//                postInvalidate();
//            }
//        });
//
//
//        ValueAnimator transYAnim = ValueAnimator.ofInt(0, transY);
//        transYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int val = (int) animation.getAnimatedValue();
//                leftRect.right = firstRight + val;
//                leftRect.left = firstLeft + val;
//                postInvalidate();
//            }
//        });
//
//        ValueAnimator bottomDecreaseAnim = ValueAnimator.ofInt(transY + firstRight, firstLeft);
//        bottomDecreaseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int val = (int) animation.getAnimatedValue();
//                leftRect.right = val;
//                postInvalidate();
//            }
//        });
//
//        ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 0);
//        alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int val = (int) animation.getAnimatedValue();
//                stickPaint.setAlpha(val);
//                postInvalidate();
//            }
//        });
//
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.playTogether(topIncreaseAnim, bottomIncreaseAnim);
//        animationSet.setInterpolator(new AccelerateInterpolator());
//
//        animationSet.setDuration(RECT_INCREASE_ANIM_TIME);
//        animationSet.start();
//        animationSet.setInterpolator(new AccelerateInterpolator());
//
//        transYAnim.setDuration(RECT_TRANS_ANIM_TIME);
//        transYAnim.setStartDelay(RECT_INCREASE_ANIM_TIME);
//        transYAnim.start();
//
//        bottomDecreaseAnim.setStartDelay(RECT_TRANS_ANIM_TIME + RECT_INCREASE_ANIM_TIME);
//        bottomDecreaseAnim.setDuration(RECT_DECREASE_ANIM_TIME);
//        bottomDecreaseAnim.start();
//        bottomDecreaseAnim.setInterpolator(new AccelerateInterpolator());
//
//        alphaAnim.setDuration(RECT_ALPHA_ANIM_TIME);
//        alphaAnim.setStartDelay(RECT_TRANS_ANIM_TIME + RECT_INCREASE_ANIM_TIME + RECT_DECREASE_ANIM_TIME);
//        alphaAnim.setInterpolator(new DecelerateInterpolator());
//        alphaAnim.start();
//    }
}
