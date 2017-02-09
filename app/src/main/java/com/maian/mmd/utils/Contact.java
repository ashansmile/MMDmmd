package com.maian.mmd.utils;

/**
 * Created by Administrator on 2016/11/17.
 */
public class Contact {
    public static String SP_NAME = "COOKIE";
    public static String[] img_lunbo = {
            "http://bpic.588ku.com/back_pic/00/01/40/4956016d7259968.jpg",
            "http://bpic.588ku.com/back_pic/00/01/40/5356016d77af06a.jpg",
            "http://bpic.588ku.com/back_pic/00/01/40/5756016e606b142.jpg",
            "http://bpic.588ku.com/back_pic/03/80/50/8257c3fe9779221.jpg",
            "http://bpic.588ku.com/back_pic/03/80/50/6057c3fe3221823.jpg"
    };


    //服务器地址
    public static String serviceUrl = "http://gzmian.com:5465/mmd/vision/RMIServlet";
    public static String getwebUrl(String id, String userName, String password) {
        String s = StringHelper.jiQuString(serviceUrl) + "openresource.jsp?iPad=true&refresh=true&showtoolbar=false&showPath=false&resid=" + id + "&user=" + userName + "&password=" + password;
        return s;
    }

    public static String getImgUrl(String id) {
        String s = "http://gzmian.com:5465/mmd/vision/UploadImageServlet?type=view&downloadId="+id;
        return s;
    }

}
