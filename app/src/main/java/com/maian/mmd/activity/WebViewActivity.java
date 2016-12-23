package com.maian.mmd.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.BitmapHandlerUtil;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.ScreenHelper;
import com.maian.mmd.utils.SdcardUtils;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.x;

import java.io.File;

public class WebViewActivity extends BaseActivity {
    private ErJiLiebiao entity;
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
        TextView textView_back = (TextView) findViewById(R.id.textView_goBack);
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
                String imgPath = captureScreen();
               // String imgPath = GetandSaveCurrentImage();
                Intent intent = new Intent(getBaseContext(),CanvasDrawActivity.class);
                intent.putExtra("img",imgPath);
                startActivity(intent);

                Toast.makeText(WebViewActivity.this, "分享", Toast.LENGTH_SHORT).show();
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
        textView_net = (TextView) findViewById(R.id.textView_net);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setDrawingCacheEnabled(true);

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
            textView_net.setVisibility(View.GONE);
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
            textView_net.setVisibility(View.VISIBLE);

        }
    }

    private String captureScreen(){
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        bmp = BitmapHandlerUtil.zoomImg(bmp,ScreenHelper.getScreenWidth(this),ScreenHelper.getScreenHeight(this)-50);
        String imgPath = SdcardUtils.saveBitmap(bmp);
        return imgPath;
    }
    /**
     * 获取和保存当前屏幕的截图
     */
    private String  GetandSaveCurrentImage()
    {
        Bitmap Bmp = Bitmap.createBitmap( ScreenHelper.getScreenWidth(this), ScreenHelper.getScreenHeight(this), Bitmap.Config.ARGB_8888 );
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);

        Bmp = decorview.getDrawingCache();
       // Bmp = BitmapHandlerUtil.cropBitmap(Bmp);
        String imgPath = SdcardUtils.saveBitmap(Bmp);

        return imgPath;

    }




}
