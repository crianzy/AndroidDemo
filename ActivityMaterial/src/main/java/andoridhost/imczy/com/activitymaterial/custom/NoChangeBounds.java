package andoridhost.imczy.com.activitymaterial.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenzhiyong on 16/6/8.
 */
public class NoChangeBounds extends Transition {

    private static final String TAG = "NoChangeBounds";

    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";


    public NoChangeBounds() {
    }

    public NoChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() == View.GONE) {
            return;
        }

        Rect clip = view.getClipBounds();
        Log.e(TAG, "captureValues: clip = " + clip);
        values.values.put(PROPNAME_CLIP, clip);
        if (clip == null) {
            Rect bounds = new Rect(0, 0, view.getWidth(), view.getHeight());
            values.values.put(PROPNAME_BOUNDS, bounds);
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues,
                                   final TransitionValues endValues) {
        Log.d(TAG, "createAnimator() called with: " + "sceneRoot = [" + sceneRoot + "], startValues = [" + startValues + "], endValues = [" + endValues + "]");

        if (startValues == null || endValues == null) {
            return null;
        }
        final Rect start = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect end = (Rect) endValues.values.get(PROPNAME_BOUNDS);

        Log.e(TAG, "createAnimator: start = " + start + " , end = " + end);
        if (start.equals(end)) {
            return null;
        }

        endValues.view.setClipBounds(start);
        RectEvaluator evaluator = new RectEvaluator(new Rect());


        endValues.view.setClipBounds(start);

        ViewGroup.LayoutParams layoutParams = endValues.view.getLayoutParams();
        layoutParams.height = start.height();
        layoutParams.width = start.width();
        endValues.view.setLayoutParams(layoutParams);

        endValues.view.requestLayout();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 12);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endValues.view.setClipBounds(start);

                ViewGroup.LayoutParams layoutParams = endValues.view.getLayoutParams();
                layoutParams.height = start.height();
                layoutParams.width = start.width();
                endValues.view.setLayoutParams(layoutParams);

                endValues.view.requestLayout();
            }
        });
        return valueAnimator;
    }
}
