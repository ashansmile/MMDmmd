package com.maian.mmd.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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
import com.maian.mmd.utils.SdcardUtils;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.x;

public class WebViewActivity extends BaseActivity {
    private ChildResult entity;
    private WebView webView;
    private TextView textView_net;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
        intiView();
    }

    private void intiView() {
        ImageView textView_back = (ImageView) findViewById(R.id.img_goBack);
        textView_back.setVisibility(View.VISIBLE);
        textView_back.setClickable(true);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView img_share = (ImageView) findViewById(R.id.imageView_share);
        img_share.setVisibility(View.VISIBLE);
        img_share.setClickable(true);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jupActivity();
            }
        });
        TextView textView_title = (TextView) findViewById(R.id.textView_head_title1);
        textView_title.setTextSize(14);
        textView_title.setText(entity.alias);
    }

    private void jupActivity(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        String imgPath = captureScreen();
        Intent intent = new Intent(getBaseContext(), CutImgActivity.class);
        intent.putExtra("img", imgPath);
        startActivity(intent);
    }

    private void init() {
        Intent intent = getIntent();
        entity = (ChildResult) intent.getSerializableExtra("web");
        webView = (WebView) findViewById(R.id.webView);
        textView_net = (TextView) findViewById(R.id.textView_net);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setDrawingCacheEnabled(true);

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
     //   System.out.println("----"+url);
        getwebUrl();
        webView.setWebViewClient(new MyWebViewClient(this,webView));
        webView.setVisibility(View.VISIBLE);
    }

    private void getwebUrl() {
        if (NetworkMonitor.isNetworkAvailable(this)) {
            textView_net.setVisibility(View.GONE);
            Login.tongZhiPhone();
            webView.loadUrl(url);
        } else {
            Toast.makeText(this, "当前网络未连接", Toast.LENGTH_SHORT).show();
            textView_net.setVisibility(View.VISIBLE);

        }
    }

    private String captureScreen() {
        Bitmap bmp = webView.getDrawingCache();
        String imgPath = SdcardUtils.saveBitmap(bmp);
        return imgPath;
    }

}
