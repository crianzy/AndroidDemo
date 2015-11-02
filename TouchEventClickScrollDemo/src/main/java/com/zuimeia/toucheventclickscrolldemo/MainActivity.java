package com.zuimeia.toucheventclickscrolldemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.imczy.common_util.log.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";


    Button mButton;
    View mView;
    CustomScrollViewGroup mCustomScrollViewGroup;

    int maxTranY;

    float currentTransY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);
        mView = findViewById(R.id.scrolledView);
        mCustomScrollViewGroup = (CustomScrollViewGroup) findViewById(R.id.customscrollviewgroup);

        maxTranY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click btn", Toast.LENGTH_SHORT).show();
            }
        });

        mCustomScrollViewGroup.setOnScrollListener(new CustomScrollViewGroup.OnScrollListener() {
            /**
             * 滑动 的箭头
             * @param distanceX 向左滑动 为正
             * @param distanceY 向上滑动 为正
             */
            @Override
            public void onScroll(float distanceX, float distanceY) {
                if (isAnimating) {
                    return;
                }
                currentTransY = currentTransY - distanceY;
                if (currentTransY < -maxTranY) {
                    currentTransY = -maxTranY;
                }

                if (currentTransY > 0) {
                    currentTransY = 0;
                }
                mView.setTranslationY(currentTransY);
                LogUtil.d(TAG, "onScroll distanceX = " + distanceX + " , distanceY = " + distanceY + ", currentTransY = " + currentTransY);
            }

            /**
             * 快速滑动的回调
             * @param isFlingUp 是否是向上快速滑动
             */
            @Override
            public void onFling(boolean isFlingUp) {
                if (isAnimating) {
                    return;
                }
                LogUtil.d(TAG, "onFling isFlingUp = " + isFlingUp);
                if (isFlingUp) {
                    animatorScrollUp();
                } else {
                    animatorScrollDowm();
                }
            }

            /**
             * 滑动结束的监听
             */
            @Override
            public void onScrollOver() {
                if (isAnimating) {
                    return;
                }
                LogUtil.d(TAG, "onScrollOver");
                if (currentTransY < -maxTranY / 2) {// 向上滑超过了 1/2
                    animatorScrollUp();
                } else {
                    animatorScrollDowm();
                }
            }
        });

    }

    boolean isAnimating = false;


    private void animatorScrollUp() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, "TranslationY", mView.getTranslationY(), -maxTranY);
        objectAnimator.setDuration(300);
        isAnimating = true;
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentTransY = -maxTranY;
                isAnimating = false;
            }
        });
        objectAnimator.start();
    }

    private void animatorScrollDowm() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, "TranslationY", mView.getTranslationY(), 0);
        objectAnimator.setDuration(300);
        isAnimating = true;
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentTransY = 0;
                isAnimating = false;
            }
        });
        objectAnimator.start();
    }
}
