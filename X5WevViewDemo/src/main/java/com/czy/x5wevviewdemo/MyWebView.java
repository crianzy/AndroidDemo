package com.czy.x5wevviewdemo;


import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by chenzhiyong on 2016/11/7.
 */

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MyWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {

    }
}
