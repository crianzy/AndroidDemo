package com.czy.coordinatorlayoutbehavior;

/**
 * Created by chenzhiyong on 16/1/12.
 */
public class MathUtils {

    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
