package com.zuimeia.imagewidthnumview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.imczy.common_util.log.LogUtil;

/**
 * Created by chenzhiyong on 15/11/17.
 */
public class ImageWithNumView extends RelativeLayout {
    public static final String TAG = "ImageWithNumView";

    public ImageWithNumView(Context context) {
        super(context);
    }

    public ImageWithNumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.View);
        int textColor = typedArray.getColor(R.styleable.MyView_textColor, 0XFFFFFFFF);
        float textSize = typedArray.getDimension(R.styleable.MyView_textSize, 36);
        LogUtil.d(TAG, "textColor = " + textColor);
        LogUtil.d(TAG, "textSize = " + textSize);

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            if ("layout_height".equals(attrs.getAttributeName(i))) {
                String h = attrs.getAttributeValue(i);
                LogUtil.d(TAG, "h = " + h);
            } else if ("layout_width".equals(attrs.getAttributeName(i))) {
                String w = attrs.getAttributeValue(i);
                LogUtil.d(TAG, "w = " + w);
            }
        }

    }

    public ImageWithNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
