package com.maian.mmd.utils;

import android.app.Activity;
import android.widget.Toast;

import com.maian.mmd.entity.ResultCode;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.List;

import static com.maian.mmd.utils.Contact.serviceUrl;

/**
 * Created by admin on 2016/11/21.
 */

public class Login {
    public static List<ResultCode> list;
    public static Boolean isOnline = false;


    public static boolean isLogin(String name) {
        isOnline = false;
        RequestParams params = new RequestParams(serviceUrl);
        params.addBodyParameter("className", "UserService");
        params.addBodyParameter("params", "[\"" + name + "\"]");
        params.addBodyParameter("methodName", "isLoginAs");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonLogin = null;
                try {
                    jsonLogin = new JSONObject(result);
                    String loginResult = jsonLogin.getString("result");
                    if ("true".equals(loginResult)) {
                        isOnline = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return isOnline;
    }

    public static void tongZhiPhone() {
        RequestParams params = new RequestParams(serviceUrl);
        params.addBodyParameter("className", "StateService");
        params.addBodyParameter("params", "[\"mobileClientType\",\"iphone\"]");
        params.addBodyParameter("methodName", "setSessionAttribute");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
            }
        });
    }



}
