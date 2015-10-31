package com.imczy.animatordemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by chenzhiyong on 15/10/30.
 */
public class AlphaAnimationActivity extends Activity {

    ImageView mImageView;
    Animation alphaAnimation;

    ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_animation);
        mImageView = (ImageView) findViewById(R.id.img);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
    }

    public void doStart(View view) {
        mImageView.startAnimation(alphaAnimation);
    }

    public void doStartAlpha(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "alpha", mImageView.getAlpha(), 0.1f);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doStartTransX(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "translationX", mImageView.getTranslationX(), 200);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doStartTransY(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "translationY", mImageView.getTranslationY(), 200);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doScaleX(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", mImageView.getScaleX(), 0.1f);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doScaleY(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "scaleY", mImageView.getScaleY(), 0.1f);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doRotationX(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "rotationX", mImageView.getRotationX(), 300);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }

    public void doRotationY(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "rotationY", mImageView.getRotationY(), 300);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();
    }
    public void doRotation(View view) {
        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", mImageView.getRotation(), 300);
        mObjectAnimator.setDuration(3000);
        mObjectAnimator.start();

        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(1,30);
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f,1f);
    }
    public void doClean(View view) {
        mImageView.setAlpha(1.0f);
        mImageView.setTranslationX(0);
        mImageView.setTranslationY(0);
        mImageView.setScaleX(1);
        mImageView.setScaleY(1);
        mImageView.setRotation(1);
        mImageView.setRotationX(0);
        mImageView.setRotationY(0);

        mImageView.setPivotX(0);
        mImageView.setPivotX(0);
    }
}
