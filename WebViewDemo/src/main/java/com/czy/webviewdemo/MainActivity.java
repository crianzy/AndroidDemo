package com.czy.webviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private static final String TEXT2 = "<p>&nbsp;</p>\n<p>这两年的 Gucci，在新任主理人 Alessandro Michele 的打理下，春色无限。</p>\n<p>错过这两款包，你就错过了春色中最夺目的那一抹。</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/a0276f9c-e614-4b18-911a-12e6bfdd6de1_640x637.png\" style=\"width:468px\" /></p>\n<p>&nbsp;</p>\n<p>这是 Michele 亲自设计的第一款包。</p>\n<p>Gucci Dionysos，俘获了无数女神的酒神包。</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/dd0c2ff5-6314-4853-a9d3-e0ed75ca5405_640x637.png\" style=\"width:452px\" /></p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; Alessandro Michele 和他拎着酒神包的女神粉们</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>狄俄尼索斯（Dionysos）是希腊神话中的葡萄酒之神，传授人们种植和酿造葡萄酒之法，同时又象征着纵情的欲望和自由享乐。</p>\n<p>相传他是宙斯和美丽的人类公主塞墨勒的儿子，天后赫拉的嫉妒害死了塞墨勒，宙斯抢救出不足月的他，缝进自己的大腿，直到足月才取出，所以有着「二次出生」的酒神又象征着「重生」，像极了 Michele 接手后的 Gucci。</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/007ce0f5-007b-4992-a16e-8fafa0fc7e9d_640x637.png\" style=\"width:363px\" /></p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;16&nbsp;世纪意大利画家卡拉瓦乔画中的酒神</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>Michele 曾先后任 Fendi 和 Gucci 配饰主理人，对配饰的打磨别具匠心。</p>\n<p>酒神包最出彩的地方在于正中央精致迷人的双虎头马蹄搭扣。</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/1d7d0586-4291-44e9-b8bd-0fbd2ef7a0af_640x637.png\" style=\"width:309px\" /></p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/56d90096-c6fa-4cda-aab5-fdf5c68c08be_640x637.png\" style=\"width:317px\" /></p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 双虎头马蹄搭扣精致的细节</p>\n<p>&nbsp;</p>\n<p>两只雕琢精细的霸气虎头，像不像下面这两只？</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/74dae2f9-45c6-47e3-a9b2-2509cc84479a_640x637.png\" /></p>\n<p>&nbsp;</p>\n<p>据传老虎是酒神的标志性圣物之一，各种插图中多见头戴葡萄藤，手执酒神杖，侧身骑着猛虎的酒神。这也是酒神包与神话最贴近的地方。</p>\n<p>&nbsp;</p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/4d0c31bf-171c-4e3c-b8a6-6840d0c3d50f_640x637.png\" style=\"width:492px\" /></p>\n<p><img src=\"http://7xnz8b.com2.z0.glb.qiniucdn.com/media/article/image/2016/5/10/76d98680-4a8f-46bb-9bf9-cfd3c397c784_640x637.png\" /></p>\n<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 传说中头戴葡萄藤骑着老虎拿着酒神杖的酒神</p>\n<p>&nbsp;</p>\n<p>&nbsp;</p>\n<p>而人们祭祀酒神的盛典，则是日后对西方文学有深远影响的希腊悲剧的源头。</p>\n<p>源自希腊神话的设计灵感，蕴含哲学和文学多重隐喻，更何况酒神包还那么美，美得醉人，像酒神呈上的美酒一样忘忧解乏。</p>\n";


    WebView mWebView;

    String url;

    Button btn;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.web_view);
        btn = (Button) findViewById(R.id.btn);
        url = "https://www.baidu.com/";
        url = "file:///android_asset/web.html";


        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");

        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 4);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);
        CookieManager.getInstance().setAcceptCookie(true);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //  有可能快速返回 导致出问题 , 所以这里要判断 mTitleTxt 是否为空
                Log.d(TAG, "onReceivedTitle title = " + title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


        });

//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//                return super.onJsPrompt(view, url, message, defaultValue, result);
//            }
//        });

//        mWebView.loadData(url, "text/html", "unicode");
        mWebView.loadUrl(url);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");

                String js = "javascript:(function JsAddJavascriptInterface_(){  \n" +
                        "    if (typeof(window.app)!='undefined') {      \n" +
                        "        console.log('window.jsInterface_js_interface_name is exist!!');}   \n" +
                        "    else {  \n" +
                        "        window.app = {          \n" +
                        "            makeToast:function(arg0) {   \n" +
                        "                return prompt('MyApp:'+JSON.stringify({obj:'app',func:'onButtonClick',args:[arg0]}));  \n" +
                        "            },  \n" +
                        "              \n" +
                        "            onImageClick:function(arg0,arg1,arg2) {   \n" +
                        "                prompt('MyApp:'+JSON.stringify({obj:'app',func:'onImageClick',args:[arg0,arg1,arg2]}));  \n" +
                        "            },  \n" +
                        "        };  \n" +
                        "    }  \n" +
                        "}  \n" +
                        ")() ";

                mWebView.loadUrl(js);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    class WebViewJavaScriptInterface {
        private Context context;

        public WebViewJavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void makeToast(final String message) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });

//           getClass().forName("android.telephony.SmsManager").getMethod("getDefault",null).invoke(null,null);
//            objSmsManager.sendTextMessage("10086",null,"this message is sent by JS when webview is loading",null,null);

        }

        public void makeToast() {
            Toast.makeText(context, "asd", Toast.LENGTH_LONG).show();
        }

    }
}
