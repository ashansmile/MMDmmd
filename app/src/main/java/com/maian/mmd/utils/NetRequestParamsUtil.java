package com.maian.mmd.utils;

import org.xutils.http.RequestParams;

/**
 * Created by admin on 2017/2/9.
 */

public class NetRequestParamsUtil {

    public static RequestParams getReqParams(String url, String className, String methodName, String params) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("className", className);
        requestParams.addBodyParameter("methodName", methodName);
        requestParams.addBodyParameter("params", params);
        return requestParams;
    }

    public static RequestParams getIsLoginParams(String url,String name){
        return getReqParams(url,"UserService","isLoginAs","[\"" + name + "\"]");
    }

    public static RequestParams getHomePageParms() {
        return getReqParams(Contact.serviceUrl,
                "mobilePortalModule", "getHomePage", "[\"demo\", \"demo\"]");
    }

    public static RequestParams getLoginParms(String url, String username, String password) {
        return getReqParams(url, "UserService", "login", "[\"" + username + "\",\"" + password + "\"]");
    }

    public static RequestParams getMenuParms(String url, String id) {
        return getReqParams(url, "CatalogService", "getChildElements", "[\"" + id + "\"]");
    }

    public static RequestParams getphoneParms(String url, String id) {
        return getReqParams(url, "IPadPortalModule", "canAccessIPadModule", "[\"" + id + "\"]");
    }

    public static RequestParams getMainPage(String url) {
        return getReqParams(url, "CatalogPublishService", "getPublishCatalogsOfCurrentUser", "[]");
    }
}
