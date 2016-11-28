package com.maian.mmd.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.activity.LoginActivity;
import com.maian.mmd.activity.NavationActivity;
import com.maian.mmd.activity.WorkeActivity;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        judgeNet();
        //judgeIsLogin();
    }

    //优先查找本地是否登录过
    private void judgeIsLogin() {
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        System.out.println("----device"+szImei);
    }



    Intent intent=null;
    private void init() {
       /* SharedPreferences sp=getSharedPreferences("args", Context.MODE_PRIVATE);
        boolean isGuide=sp.getBoolean("isGuid",false);
        if(isGuide){
            intent=new Intent(this,WorkeActivity.class);
        }else{
            intent=new Intent(this,NavationActivity.class);
        }*/
        intent = new Intent(this,LoginActivity.class);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void judgeNet(){
        if(NetworkMonitor.isNetworkAvailable(this)){
            login();
        }else{
            Toast.makeText(this, "当前没有网络", Toast.LENGTH_SHORT).show();
        }
    }
  private void login(){

     // Login.login("js","1",this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
