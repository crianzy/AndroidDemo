package com.zuimeia.webviewheaderdemo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    MyWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mWebView = (MyWebView) findViewById(R.id.webview);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 4);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);
        CookieManager.getInstance().setAcceptCookie(true);

        mWebView.setWebChromeClient(chromeClient);

//        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");

        mWebView.loadUrl("http://192.168.88.18:8000/article/mobile/webview/40/");

        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);//true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);//false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);//1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }

    }


    private com.tencent.smtt.sdk.WebChromeClient chromeClient = new com.tencent.smtt.sdk.WebChromeClient() {

        @Override
        public boolean onJsConfirm(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2, JsResult arg3) {
            Log.d(TAG, "onJsConfirm() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "], arg2 = [" + arg2 + "], arg3 = [" + arg3 + "]");

            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;


        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            Log.d(TAG, "onShowCustomView() called with: view = [" + view + "], customViewCallback = [" + customViewCallback + "]");
            FrameLayout normalView = (FrameLayout) findViewById(R.id.frame_web_video);
            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
            viewGroup.removeView(normalView);
            viewGroup.addView(view);
            myVideoView = view;
            myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            Log.d(TAG, "onHideCustomView() called");
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        @Override
        public boolean onJsAlert(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2, JsResult arg3) {
            Log.d(TAG, "onJsAlert() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "], arg2 = [" + arg2 + "], arg3 = [" + arg3 + "]");
            /**
             * 这里写入你自定义的window alert
             */
            // AlertDialog.Builder builder = new Builder(getContext());
            // builder.setTitle("X5内核");
            // builder.setPositiveButton("确定", new
            // DialogInterface.OnClickListener() {
            //
            // @Override
            // public void onClick(DialogInterface dialog, int which) {
            // // TODO Auto-generated method stub
            // dialog.dismiss();
            // }
            // });
            // builder.show();
            // arg3.confirm();
            // return true;
            Log.i("yuanhaizhou", "setMyWebView = null");
            return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
        }

        /**
         * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
         */
        @Override
        public boolean onJsPrompt(com.tencent.smtt.sdk.WebView arg0, String arg1, String arg2, String arg3, JsPromptResult arg4) {
            Log.d(TAG, "onJsPrompt() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "], arg2 = [" + arg2 + "], arg3 = [" + arg3 + "], arg4 = [" + arg4 + "]");
            // 在这里可以判定js传过来的数据，用于调起android native 方法
//            if (isMsgPrompt(arg1)) {
//                if (onJsPrompt(arg2, arg3)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
            return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
        }

        @Override
        public void onReceivedTitle(com.tencent.smtt.sdk.WebView arg0, final String arg1) {
            super.onReceivedTitle(arg0, arg1);
            Log.d(TAG, "onReceivedTitle() called with: arg0 = [" + arg0 + "], arg1 = [" + arg1 + "]");
            Log.i("yuanhaizhou", "webpage title is " + arg1);

        }
    };

    /**
     * 判定当前的prompt消息是否为用于调用native方法的消息
     *
     * @param msg 消息名称
     * @return true 属于prompt消息方法的调用
     */
    private boolean isMsgPrompt(String msg) {
        if (msg != null && msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
     *
     * @param methodName 方法名称
     * @param blockName  区块名称
     * @return true ：调用成功 ； false ：调用失败
     */
    private boolean onJsPrompt(String methodName, String blockName) {
        String tag = SecurityJsBridgeBundle.BLOCK + blockName + "-" + SecurityJsBridgeBundle.METHOD + methodName;

//        if (this.mJsBridges != null && this.mJsBridges.containsKey(tag)) {
//            ((SecurityJsBridgeBundle) this.mJsBridges.get(tag)).onCallMethod();
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }


    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            //
            Log.e(TAG, "openImage: img = " + img);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

