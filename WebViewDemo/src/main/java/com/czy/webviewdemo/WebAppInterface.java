package com.czy.webviewdemo;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by chenzhiyong on 16/2/17.
 */
public class WebAppInterface {

    Context mContext;

    /**
     * Instantiatethe interface and set the context
     */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /**
     * Show a toastfrom the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Log.e("WebAppInterface", "showToast toast = " + toast);
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
