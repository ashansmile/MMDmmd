package com.maian.mmd.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by admin on 2017/1/19.
 */

public class MyWebViewClient extends WebViewClient {
    private ProgressDialog progressDialog;
    Activity activity;
    WebView webView;
    public MyWebViewClient(Activity activity,WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("数据加载中，请稍后。。。");
            progressDialog.show();
            webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
        }
        super.onPageStarted(view, url, favicon);
    }


    @Override
    public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
            webView.setEnabled(true);
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) { // 网页加载时的连接的网址
        view.loadUrl(url);
        return true;
    }
}