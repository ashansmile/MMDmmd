package com.maian.mmd.utils;

/**
 * Created by admin on 2016/11/29.
 */

public class StringHelper {

    //截取最后一个"/"
    public static String jiQuString(String str){
        String url = str;
        int index = url.lastIndexOf("/");
        String result = url.substring(0,index + 1);
        return result;
    }
}
