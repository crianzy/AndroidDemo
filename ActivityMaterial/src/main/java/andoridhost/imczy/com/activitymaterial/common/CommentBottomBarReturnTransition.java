package andoridhost.imczy.com.activitymaterial.common;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CommentBottomBarReturnTransition extends Transition {

    private static final String TAG = "CommentBottomBarReturnTransition";


    private static final String PROPNAME_TRANSITION_Y = "custom_bottom_box_return_transition:change_transY:transitionY";


    private View animView;

    public CommentBottomBarReturnTransition(View animView) {
        this.animView = animView;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_TRANSITION_Y, 0);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        animView.measure(0, 0);
        int transY = animView.getMeasuredHeight();
        transitionValues.values.put(PROPNAME_TRANSITION_Y, transY);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }

        final View view = endValues.view;

        if (view == animView) {

            int startTransY = (int) startValues.values.get(PROPNAME_TRANSITION_Y);
            int endTransY = (int) endValues.values.get(PROPNAME_TRANSITION_Y);

            Log.d(TAG, "createAnimator: view == animView startTransY = " + startTransY + " , endTransY = " + endTransY);
            if (startTransY != endTransY) {
                ValueAnimator animator = ValueAnimator.ofInt(startTransY, endTransY);
                // Add an update listener to the Animator object.
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object value = animation.getAnimatedValue();
                        // Each time the ValueAnimator produces a new frame in the animation, change
                        // the background color of the target. Ensure that the value isn't null.
                        if (null != value) {
                            view.setTranslationY((Integer) value);
                        }
                    }
                });
                return animator;
            }
        }

        return null;
    }

}
