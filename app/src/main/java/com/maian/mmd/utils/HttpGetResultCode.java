package com.maian.mmd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.maian.mmd.entity.ResultCode;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/11/22.
 */

public class HttpGetResultCode {
    public static List<ResultCode> list;
    public static List<ResultCode> getresultCode(String url){
        list= new ArrayList<>();
        System.out.println("----url:"+url);
        RequestParams params = new RequestParams(url);
        x.http().post(params,new xutilsCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                System.out.println("----resultCode:"+result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String root = jsonObject.getString("result");
                    JSONArray jsonArray = JSON.parseArray(root);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        ResultCode n = JSON.parseObject(jsonArray.get(i).toString(), ResultCode.class);
                        list.add(n);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);

            }
        });
        return list;
    }
}
