package com.maian.mmd.utils;

import com.maian.mmd.entity.ResultCode;

import org.json.JSONObject;
import org.xutils.x;

import java.util.List;

import static com.maian.mmd.utils.Contact.serviceUrl;

/**
 * Created by ashan on 2016/11/21.
 */

public class Login {
    public static List<ResultCode> list;
    public static Boolean isOnline = false;


    public static boolean isLogin(String name) {
        isOnline = false;
        x.http().post(NetRequestParamsUtil.getIsLoginParams(serviceUrl, name), new xutilsCallBack<String>() {
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
        x.http().post(NetRequestParamsUtil.getReqParams(serviceUrl,
                "StateService", "setSessionAttribute",
                "[\"mobileClientType\",\"iphone\"]"),
                new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
            }
        });
    }


}
