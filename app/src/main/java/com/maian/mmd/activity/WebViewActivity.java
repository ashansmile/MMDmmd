package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.maian.mmd.R;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

public class WebViewActivity extends AppCompatActivity {
    ErJiLiebiao entity;
    WebView webView;

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
                /*Intent intent = new Intent(getBaseContext(),ErJiTableActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
        TextView textView_title = (TextView) findViewById(R.id.textView_head_title);
        textView_title.setText(entity.name);


    }

    private void init() {
        Intent intent = getIntent();
        entity = (ErJiLiebiao) intent.getSerializableExtra("web");
        System.out.println("-----传到web:" + entity.name);
        webView = (WebView) findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        getwebUrl();

    }

    private void getwebUrl() {
        if (Login.isLogin(MMDApplication.user.name)) {
            webView.loadUrl(Contact.getwebUrl(entity.id, MMDApplication.user.name,
                    MMDApplication.user.pwd));
        } else {
            x.http().post(Login.loginParms(MMDApplication.user.name, MMDApplication.user.pwd), new xutilsCallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            webView.loadUrl(Contact.getwebUrl(entity.id, MMDApplication.user.name,
                                    MMDApplication.user.pwd));
                        }
                    }
            );
        }
    }

}
