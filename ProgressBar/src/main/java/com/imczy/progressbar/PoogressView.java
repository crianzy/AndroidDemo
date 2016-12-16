package com.imczy.progressbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by chenzhiyong on 2016/11/24.
 */

public class PoogressView extends ProgressBar {
    private static final String TAG = "PoogressView";

    public PoogressView(Context context) {
        super(context);
        init(context);
    }

    public PoogressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PoogressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Drawable animationDrawable = context.getResources().getDrawable(R.drawable.loading_animd);

        RotateDrawable
//        Log.e(TAG, "init: animationDrawable = " + animationDrawable);
//        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);
//        animation.setInterpolator(new AccAndDecInterpolator());

        setPadding(60, 60, 60, 60);
        setIndeterminateDrawable(animationDrawable);

    }
}
