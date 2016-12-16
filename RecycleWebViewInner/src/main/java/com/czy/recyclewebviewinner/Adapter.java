package com.czy.recyclewebviewinner;

/**
 * Created by chenzhiyong on 2016/10/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mStringList;

    public Adapter(Context context) {
        mContext = context;
        mStringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mStringList.add("RecycleViewAdapter -- " + i);
        }
    }

    WebViewHolder mWebViewHolder;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item, parent, false);
            return new TxtHolder(view);
        } else {
            if (mWebViewHolder != null) {
                return mWebViewHolder;
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_web_viewitem, parent, false);
            mWebViewHolder = new WebViewHolder(view, mContext);
            return mWebViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TxtHolder) {
            ((TxtHolder) holder).txt.setText("RecycleViewAdapter -- ");
        } else if (holder instanceof WebViewHolder) {
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 5) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    static class TxtHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public TxtHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }

    static class WebViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "WebViewHolder";
        WebView mWebView;

        public WebViewHolder(View itemView, Context context) {
            super(itemView);
            mWebView = (WebView) itemView.findViewById(R.id.web_view);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.getSettings().setAppCacheEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 4);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
            mWebView.getSettings().setAppCachePath(appCachePath);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.loadUrl("http://designtest.zuimeia.com/article/mobile/webview/40/");
            CookieManager.getInstance().setAcceptCookie(true);
            Log.e(TAG, "WebViewHolder: ");

            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });

            setIsRecyclable(true);
        }
    }

}
