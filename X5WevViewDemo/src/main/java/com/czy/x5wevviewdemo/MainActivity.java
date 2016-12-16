package com.czy.x5wevviewdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    MyWebView webView;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）

        webView = (MyWebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 4);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        String appCachePath = getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        CookieManager.getInstance().setAcceptCookie(true);

        webView.loadUrl("http://nicedesignslave2.zuimeia.com/product/mobile/webview/1508/");


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished: ");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG, "shouldOverrideUrlLoading: url = " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {
                TbsLog.d(TAG, "title: " + title);
            }

            @Override
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {

            }

            @Override
            public boolean onJsPrompt(com.tencent.smtt.sdk.WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                Log.d(TAG, "onJsPrompt() called with: webView = [" + webView + "], s = [" + s + "], s1 = [" + s1 + "], s2 = [" + s2 + "], jsPromptResult = [" + jsPromptResult + "]");
                return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }

        });
    }

    private Context getContext() {
        return this;
    }
}
