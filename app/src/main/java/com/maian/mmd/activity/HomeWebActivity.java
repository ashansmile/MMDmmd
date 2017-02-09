package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ChildResult;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.MyWebViewClient;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.SharedpreferencesUtil;

public class HomeWebActivity extends BaseActivity {


    private WebView webView;
    private ChildResult entity;
    private TextView textView_home_net;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web);
        init();
    }
    private void init() {
        Intent intent = getIntent();
        entity = (ChildResult) intent.getSerializableExtra("web");
        webView = (WebView) findViewById(R.id.web_home);
        textView_home_net = (TextView) findViewById(R.id.textView_home_net);
        TextView textView = (TextView) findViewById(R.id.textView_web_title);
        textView.setText(entity.alias);


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


        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        url = Contact.getwebUrl(entity.id, MMDApplication.user.name,
                MMDApplication.user.pwd);
        System.out.println("----"+url);
        getwebUrl();
        webView.setWebViewClient(new MyWebViewClient(this,webView));
        webView.setVisibility(View.VISIBLE);
    }

    private void getwebUrl() {
        if (NetworkMonitor.isNetworkAvailable(this)) {
            textView_home_net.setVisibility(View.GONE);
            Login.tongZhiPhone();
            webView.loadUrl(url);
        } else {
            Toast.makeText(this, "当前网络未连接", Toast.LENGTH_SHORT).show();
            textView_home_net.setVisibility(View.VISIBLE);

        }
    }

    public void changeHome(View view){
        MMDApplication.isWeb = false;
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activiy_leftin, R.anim.activity_leftout);
    }
}
