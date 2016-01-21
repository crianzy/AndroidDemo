package com.zuimeia.android5activityanimator.image;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.imczy.common_util.PhoneUtil;
import com.imczy.common_util.log.LogUtil;

import java.util.Map;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class MyChangeImageTransform extends Transition {
    private static final String TAG = "MyChangeImageTransform";

    private static final String PROPNAME_TRANSLATION_X = "android:changeImageTransform:transX";
    private static final String PROPNAME_TRANSLATION_Y = "android:changeImageTransform:transY";

    private static final String[] sTransitionProperties = {
            PROPNAME_TRANSLATION_X,
            PROPNAME_TRANSLATION_Y
    };
    Context mContext;

    public MyChangeImageTransform(Context context) {
        init(context);
    }

    public MyChangeImageTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    private void captureValues(TransitionValues transitionValues, boolean isStarted) {
        View view = transitionValues.view;

        if (view.getVisibility() != View.VISIBLE
                || TextUtils.isEmpty(view.getTransitionName())
                || !"img".equals(view.getTransitionName())
                ) {
            return;
        }
        LogUtil.d(TAG, "captureValues , view = " + view + " , transitionValues.view.getTransitionName() = " + transitionValues.view.getTransitionName());

        Map<String, Object> values = transitionValues.values;

        if (isStarted) {
            int tranX = (int) view.getTranslationX();
            int tranY = (int) view.getTranslationY();
            values.put(PROPNAME_TRANSLATION_X, tranX);
            values.put(PROPNAME_TRANSLATION_Y, tranY);

        } else {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            int screenWidth = PhoneUtil.getDisplayWidth(mContext);
            int screenHeight = PhoneUtil.getDisplayHeight(mContext) - PhoneUtil.getStatusBarHeight();


            int viewCenterX = (rect.left + rect.right) / 2;
            int viewCenterY = (rect.top + rect.bottom) / 2;

            int tranX = screenWidth / 2 - viewCenterX;
            int tranY = screenHeight / 2 + PhoneUtil.getStatusBarHeight() - viewCenterY;

            LogUtil.d(TAG, "rect = " + rect + " , screenWidth = " + PhoneUtil.getScreentHeight(mContext) + " , screenHeight = " + screenHeight + " , viewCenterX = " + viewCenterX + " , viewCenterY = " + viewCenterY);
            values.put(PROPNAME_TRANSLATION_X, tranX);
            values.put(PROPNAME_TRANSLATION_Y, tranY);
        }

    }


    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues, true);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues, false);
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        int startTranx = (int) startValues.values.get(PROPNAME_TRANSLATION_X);
        int startTranY = (int) startValues.values.get(PROPNAME_TRANSLATION_Y);

        int endTranx = (int) endValues.values.get(PROPNAME_TRANSLATION_X);
        int endTranY = (int) endValues.values.get(PROPNAME_TRANSLATION_Y);

        View view = endValues.view;
        AnimatorSet anim = new AnimatorSet();
        Animator mObjectAnimator1 = ObjectAnimator.ofFloat(view, "translationX", startTranx, endTranx);
        Animator mObjectAnimator2 = ObjectAnimator.ofFloat(view, "translationY", startTranY, endTranY);
        anim.playTogether(mObjectAnimator1, mObjectAnimator2);
        return anim;
    }
}
