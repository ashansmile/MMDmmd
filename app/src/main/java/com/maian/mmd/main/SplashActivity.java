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
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.PersonService;
import com.maian.mmd.entity.User;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.HDbManager;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.List;


public class SplashActivity extends BaseActivity {
    private String name;
    private String pwd;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //judgeIsLogin();
        judgeIsGUide();
    }

    //优先查找本地是否登录过
    private void judgeIsLogin() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        System.out.println("----device" + szImei);
    }
    private void judgeIsGUide() {
        SharedPreferences sp=getSharedPreferences("args", Context.MODE_PRIVATE);
        boolean isGuide=sp.getBoolean("isGuid",false);
        if(isGuide){
            judgeNet();
        }else{
            MMDApplication.ISFIRSTUSE = 1;
            intent=new Intent(this,NavationActivity.class);
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }

    Intent intent = null;

    private void init() {
        intent = new Intent(this, LoginActivity.class);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void judgeNet() {
        if (NetworkMonitor.isNetworkAvailable(this)) {
            isLogin();
        } else {
            Toast.makeText(this, "当前没有网络", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void isLogin() {
        List<User> list = getUserFromDB();
        if (list != null && list.size() != 0) {
            login(list);
        } else {
            skip();
        }
    }

    private void skip() {
        init();
    }

    private void login(List<User> list) {
        name = list.get(list.size() - 1).name;
        pwd = list.get(list.size() - 1).pwd;
        url = list.get(list.size() - 1).inServiceUrl;
        Contact.serviceUrl = url;
        x.http().post(Login.loginParms(name, pwd), new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonLogin = new JSONObject(result);
                    String loginResult = jsonLogin.getString("result");
                    if (loginResult.equals("true")) {
                        MMDApplication.user = new User(name,pwd,url);
                        Intent intent = new Intent(getBaseContext(), WorkeActivity.class);
                        startActivity(intent);
                        DbCookieStore instance = DbCookieStore.INSTANCE;
                        List<HttpCookie> cookies = instance.getCookies();

                    } else if ("false".equals(loginResult)) {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                super.onError(ex, isOnCallback);
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<User> getUserFromDB() {
        List<User> list_db = null;
        DbManager db = x.getDb(HDbManager.getUserDB());
        try {
            list_db = db.selector(User.class).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_db;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


}
