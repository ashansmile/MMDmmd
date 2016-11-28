package com.maian.mmd.utils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class Contact {
    public static String SP_NAME = "COOKIE";
    public static String[] img_lunbo = {
            "http://www.uimaker.com/uploads/allimg/20141222/1419212297683731.png",
            "http://www.36dsj.com/wp-content/uploads/2014/10/436.jpg",
            //"http://www.ppvke.com/js/attached/image/20131017/20131017163634_40548.jpg",
            "http://pic31.nipic.com/20130804/2531170_194351643000_2.jpg",
            "http://pic.58pic.com/58pic/14/14/84/00u58PICxT4_1024.jpg"
    };


    //服务器地址
    public static String serviceUrl = "http://gzmian.com:5465/mmd/vision/RMIServlet";

    public static String getServiceUrl(String url){
        return url;
    }
    //http://gzmian.com:5465/mmd/vision/openresource.jsp?iPad=true&refresh=true&showtoolbar=false&showPath=false&resid=114f39db3220158534f534f29c9015853f6c7450886&user=admin&password=Mmd123
    public static String weburl = "http://gzmian.com:5465/mmd/vision/openresource.jsp";

    public static String getwebUrl(String id,String userName,String password){
       String s = "http://gzmian.com:5465/mmd/vision/openresource.jsp?iPad=true&refresh=true&showtoolbar=false&showPath=false&resid="+id+"&user="+userName+"&password="+password;
        return s;
    }


}
