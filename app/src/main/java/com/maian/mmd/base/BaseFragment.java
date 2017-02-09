package com.maian.mmd.base;

import android.support.v4.app.Fragment;

import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.xutilsCallBack;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/14.
 */
public class BaseFragment extends Fragment{
    public void handleOnLine(String username,String password){
        final String url = Contact.serviceUrl;
        RequestParams requestParams = new RequestParams(url);
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
                        handleThing();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }
    //用于处理登录获取数据
    public void handleThing() {
    }
}
