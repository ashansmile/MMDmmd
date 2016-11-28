package com.maian.mmd.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.maian.mmd.base.MMDApplication;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by admin on 2016/11/22.
 */

public class NetParams extends RequestParams {

    public NetParams(String url, JSONObject params, int timeOut) {
        super(url);
        setConnectTimeout(timeOut == 0 ? 30 * 1000 : timeOut);
        if (params != null)
            setBodyContent(params.toString());


        //登陆接口 和不需要CooKie的接口
        if (!"/auth/getDate".equals(url) && !"/api/auth".equals(url)) {
            SharedPreferences sharedPreferences = x.app().getSharedPreferences(Contact.SP_NAME, Context.MODE_PRIVATE);

            MMDApplication.cookie = sharedPreferences.getString("Cookie", "");
            addHeader("Cookie", "JSESSIONID=" + MMDApplication.cookie);
            setUseCookie(false);
            addHeader("Content-Type", "application/json;charset=UTF-8");

        } else {
            setUseCookie(true);
        }


    }

}