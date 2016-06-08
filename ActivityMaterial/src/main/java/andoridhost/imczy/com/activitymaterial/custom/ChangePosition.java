/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package andoridhost.imczy.com.activitymaterial.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

public class ChangePosition extends Transition {
    private static final String TAG = "ChangePosition";

    private static final String PROPNAME_POSITION = "custom_position:change_position:position";


    public ChangePosition() {
        setPathMotion(new PathMotion() {
            @Override
            public Path getPath(float startX, float startY, float endX, float endY) {
                Path path = new Path();
                path.moveTo(startX, startY);

                Log.d(TAG, "getPath() called with: " + "startX = [" + startX + "], startY = [" + startY + "], endX = [" + endX + "], endY = [" + endY + "]");

                float controlPointX = (startX + endX) / 3;
                float controlPointY = (startY + endY) / 2;

                path.quadTo(controlPointX, controlPointY, endX, endY);
                return path;
            }
        });
    }

    private void captureValues(TransitionValues values) {
        values.values.put(PROPNAME_POSITION, values.view.getBackground());

        Rect rect = new Rect();
        values.view.getGlobalVisibleRect(rect);
        values.values.put(PROPNAME_POSITION, rect);
    }


    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    //    @Override
//    public Animator createAnimator(ViewGroup sceneRoot,
//                                   TransitionValues startValues, TransitionValues endValues) {
//        if (null == startValues || null == endValues) {
//            return null;
//        }
//
////        Log.d(TAG, "createAnimator() called with: " + "sceneRoot = [" + sceneRoot + "], startValues = [" + startValues + "], endValues = [" + endValues + "]");
////
////        Log.e(TAG, "createAnimator: startValues.view.getId() = " + startValues.view.getId());
//
//        if (startValues.view.getId() > 0) {
//            Rect startRect = (Rect) startValues.values.get(PROPNAME_POSITION);
//            Rect endRect = (Rect) endValues.values.get(PROPNAME_POSITION);
//
//            final View view = endValues.view;
//
//            AnimatorSet animatorSet = new AnimatorSet();
//
//            int transY = startRect.centerY() - endRect.centerY();
//            int transX = startRect.centerX() - endRect.centerX();
//
//            Log.e(TAG, "createAnimator: transY = " + transY + " , transX = " + transX + "during = " + getDuration());
//
//            ValueAnimator trnasYAnim = ValueAnimator.ofInt(transY, 0);
//            trnasYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    view.setTranslationY((int) animation.getAnimatedValue());
//                }
//            });
//
//            ValueAnimator trnasXAnim = ValueAnimator.ofInt(transX, 0);
//            trnasXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    view.setTranslationX((int) animation.getAnimatedValue());
//                }
//            });
//
//            if (transY > 0) {
//                trnasXAnim.setInterpolator(new FastOutSlowInInterpolator());
////                trnasYAnim.setInterpolator(new FastOutLinearInInterpolator());
//            } else {
////                trnasYAnim.setInterpolator(new FastOutSlowInInterpolator());
//                trnasXAnim.setInterpolator(new FastOutLinearInInterpolator());
//            }
//
//            animatorSet.playTogether(trnasYAnim, trnasXAnim);
//
//            return animatorSet;
//        }
//        return null;
//
//    }
    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }

        if (startValues.view.getId() > 0) {
            Rect startRect = (Rect) startValues.values.get(PROPNAME_POSITION);
            Rect endRect = (Rect) endValues.values.get(PROPNAME_POSITION);

            final View view = endValues.view;

            Log.e(TAG, "createAnimator: startRect = " + startRect + " , endRect = " + endRect);

            Path changePosPath = getPathMotion().getPath(startRect.centerX(), startRect.centerY(), endRect.centerX(), endRect.centerY());

            int radius = startRect.centerY() - endRect.centerY();

            ObjectAnimator objectAnimator = ObjectAnimator.ofObject(view, new PropPosition(PointF.class, "position", new PointF(endRect.centerX(), endRect.centerY())), null, changePosPath);
            objectAnimator.setInterpolator(new FastOutSlowInInterpolator());

            return objectAnimator;
        }
        return null;

    }

    static class PropPosition extends Property<View, PointF> {

        public PropPosition(Class<PointF> type, String name) {
            super(type, name);
        }

        public PropPosition(Class<PointF> type, String name, PointF startPos) {
            super(type, name);
            this.startPos = startPos;
            Log.e(TAG, "PropPosition: startPos = " + startPos);
        }

        PointF startPos;

        @Override
        public void set(View view, PointF topLeft) {
            Log.d(TAG, "set() called with: " + "view = [" + view + "], topLeft = [" + topLeft + "]");

            int x = Math.round(topLeft.x);
            int y = Math.round(topLeft.y);

            int startX = Math.round(startPos.x);
            int startY = Math.round(startPos.y);

            int transY = y - startY;
            int transX = x - startX;

            view.setTranslationX(transX);
            view.setTranslationY(transY);
        }

        @Override
        public PointF get(View object) {
            return null;
        }
    }

}
