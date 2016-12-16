package com.czy.autoscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by chenzhiyong on 2016/10/20.
 */

public class AutoHeightScrollView extends ScrollView {
    private static final String TAG = "AutoHeightScrollView";

    private static final int DEFAULT_MAX_HEIGHT_DP = 100;

    private int mMaxHeight;

    public AutoHeightScrollView(Context context) {
        super(context);
        init(null);
    }

    public AutoHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AutoHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        int defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_MAX_HEIGHT_DP, getResources().getDisplayMetrics());

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AutoHeightScrollView);
            mMaxHeight = typedArray.getDimensionPixelSize(R.styleable.AutoHeightScrollView_maxHeight, defaultHeight);
            typedArray.recycle();
        } else {
            mMaxHeight = defaultHeight;
        }

        Log.e(TAG, "init: mMaxHeight = " + mMaxHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            child.measure(widthMeasureSpec, 0);
            Log.e(TAG, "onMeasure: child.getMeasuredHeight() = " + child.getMeasuredHeight());
            if (child.getMeasuredHeight() > mMaxHeight) {
                int heightMs = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
                setMeasuredDimension(widthMeasureSpec, heightMs);
                return;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
