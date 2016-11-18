package com.maian.mmd.utils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class Contact {
    public static String[] img_lunbo = {
            "http://www.uimaker.com/uploads/allimg/20141222/1419212297683731.png",
            "http://www.36dsj.com/wp-content/uploads/2014/10/436.jpg",
            "http://www.ppvke.com/js/attached/image/20131017/20131017163634_40548.jpg",
            "http://pic31.nipic.com/20130804/2531170_194351643000_2.jpg",
            "http://pic.58pic.com/58pic/14/14/84/00u58PICxT4_1024.jpg"
    };


    //服务器地址
    public static String serviceUrl="http://gzmian.com:5465/mmd/vision/RMIServlet";

    public static String getLoginUrl(String userName,String password){
        return serviceUrl+"?UserName="+userName+"&Password="+password;
    }

}
