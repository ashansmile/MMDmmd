package com.maian.mmd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ashan on 2017/1/23.
 */

public class SharedpreferencesUtil {

    public static void  saveIsHomeWeb(Activity activity,boolean flag){
        SharedPreferences preferences=activity.getSharedPreferences("isHomeWeb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("isHomeWeb", flag);
        editor.commit();
    }

    public static void saveIsFromNet(Activity activity,boolean f){
        SharedPreferences sp = activity.getSharedPreferences("isFromNet", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isFromNet", f).commit();
    }

    public static void saveIsGrid(Activity activity,boolean f){
        SharedPreferences sp = activity.getSharedPreferences("isGrid", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isGrid", f).commit();

    }


}
