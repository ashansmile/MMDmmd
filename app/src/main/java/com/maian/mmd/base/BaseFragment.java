package com.maian.mmd.base;

import android.support.v4.app.Fragment;

import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.NetRequestParamsUtil;
import com.maian.mmd.utils.xutilsCallBack;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/14.
 */
public class BaseFragment extends Fragment{
    public void handleOnLine(String username,String password){
        x.http().post(NetRequestParamsUtil.getLoginParms(Contact.serviceUrl,username,password), new xutilsCallBack<String>() {
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
