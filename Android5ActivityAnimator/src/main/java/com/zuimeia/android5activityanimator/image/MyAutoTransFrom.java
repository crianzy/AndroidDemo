package com.zuimeia.android5activityanimator.image;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class MyAutoTransFrom extends TransitionSet {
    public MyAutoTransFrom(Context context) {
        init(context);

    }

    public MyAutoTransFrom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrdering(ORDERING_SEQUENTIAL);
        addTransition(new MyChangeImageTransform(context))
                .addTransition(new ChangeBounds());
    }
}
