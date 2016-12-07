package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.x;

public class WebViewActivity extends BaseActivity {
    private ErJiLiebiao entity;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
        intiView();
    }

    private void intiView() {
        TextView textView_back = (TextView) findViewById(R.id.textView_goBack);
        textView_back.setClickable(true);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView textView_title = (TextView) findViewById(R.id.textView_head_title);
        textView_title.setTextSize(14);
        textView_title.setText(entity.name);
    }

    private void init() {
        Intent intent = getIntent();
        entity = (ErJiLiebiao) intent.getSerializableExtra("web");
        webView = (WebView) findViewById(R.id.webView);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        webView.setHorizontalScrollBarEnabled(false);//水平不显示
//        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        url = Contact.getwebUrl(entity.id, MMDApplication.user.name,
                MMDApplication.user.pwd);
        getwebUrl();
    }

    private void getwebUrl() {
        if(NetworkMonitor.isNetworkAvailable(this)){
            Login.tongZhiPhone();
            if (Login.isLogin(MMDApplication.user.name)) {
                webView.loadUrl(url);
            } else {
                x.http().post(Login.loginParms(MMDApplication.user.name, MMDApplication.user.pwd), new xutilsCallBack<String>() {
                            @Override
                            public void onSuccess(String result) {
                                webView.loadUrl(url);
                            }
                        }
                );
            }
        }else {
            Toast.makeText(this, "当前网络未连接", Toast.LENGTH_SHORT).show();
        }
    }

}
