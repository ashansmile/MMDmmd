package com.maian.mmd.utils;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by admin on 2017/1/22.
 */

public class xutilsHelper {

    public static  RequestParams getReqParams(String url,String className,String methodName,String username, String password) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("className", className);
        requestParams.addBodyParameter("methodName", methodName);
        //requestParams.addBodyParameter("params", "[\"" + username + "\",\"" + password + "\"]");
        requestParams.addBodyParameter("params", "[]");
        return requestParams;
    }
}
