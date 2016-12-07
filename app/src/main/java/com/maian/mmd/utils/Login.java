package com.maian.mmd.utils;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ResultCode;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import static com.maian.mmd.utils.Contact.serviceUrl;

/**
 * Created by admin on 2016/11/21.
 */

public class Login {
    public static List<ResultCode> list;
    public static Boolean isLoginSucess = false;
    public static Boolean isOnline = false;

    //登录
    public static Boolean login(final String username, final String password, final Activity activity) {
        isLoginSucess = true;
        System.out.println("----访问地址:" + Contact.serviceUrl);
        RequestParams requestParams = new RequestParams(Contact.serviceUrl);
        requestParams.addBodyParameter("className", "UserService");
        requestParams.addBodyParameter("methodName", "login");
        requestParams.addBodyParameter("params", "[\"" + username + "\",\"" + password + "\"]");
        x.http().post(requestParams, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonLogin = new JSONObject(result);
                    String loginResult = jsonLogin.getString("result");
                    if (loginResult.equals("true")) {
                        DbCookieStore instance = DbCookieStore.INSTANCE;
                        List<HttpCookie> cookies = instance.getCookies();
                        System.out.println("----cookie:" + cookies.size());
                        for (HttpCookie cookie : cookies) {

                            String name = cookie.getName();
                            String value = cookie.getValue();
                            System.out.println("----name:" + name + ";value:" + value);
                        }

                        isLoginSucess = true;
                    } else if ("false".equals(loginResult)) {
                        isLoginSucess = false;
                        Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        isLoginSucess = false;
                        Toast.makeText(activity, "服务器配置出错,", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return isLoginSucess;
    }

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
        params.addBodyParameter("params", "[\"mobileClientType\",\"iPhone\"]");
        params.addBodyParameter("methodName", "setSessionAttribute");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
            }
        });
    }

    public static RequestParams loginParms(final String username, final String password) {
        RequestParams requestParams = new RequestParams(Contact.serviceUrl);
        requestParams.addBodyParameter("className", "UserService");
        requestParams.addBodyParameter("methodName", "login");
        requestParams.addBodyParameter("params", "[\"" + username + "\",\"" + password + "\"]");
        return requestParams;
    }

}
