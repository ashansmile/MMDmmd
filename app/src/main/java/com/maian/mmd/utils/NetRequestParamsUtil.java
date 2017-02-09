package com.maian.mmd.utils;

import org.xutils.http.RequestParams;

/**
 * Created by admin on 2017/2/9.
 */

public class NetRequestParamsUtil {


    public static RequestParams getLoginParms(String url,String username,String password){
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("className", "UserService");
        requestParams.addBodyParameter("methodName", "login");
        requestParams.addBodyParameter("params", "[\"" + username + "\",\"" + password + "\"]");
        return requestParams;
    }

    public static RequestParams getMenuParms(String url ,String id){
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("className", "CatalogService");
        requestParams.addBodyParameter("methodName", "getChildElements");
        requestParams.addBodyParameter("params", "[\"" + id + "\"]");
        return requestParams;
    }

    public static RequestParams getphoneParms(String url ,String id){
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("className", "IPadPortalModule");
        requestParams.addBodyParameter("methodName", "canAccessIPadModule");
        requestParams.addBodyParameter("params", "[\"" + id + "\"]");
        return requestParams;
    }
}
