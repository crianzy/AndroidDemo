package com.imczy.progressbar;

import android.view.animation.Interpolator;

/**
 * Created by chenzhiyong on 2016/11/24.
 */

public class AccAndDecInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return ((4 * input - 2) * (4 * input - 2) * (4 * input - 2)) / 16f + 0.5f;
    }

}
