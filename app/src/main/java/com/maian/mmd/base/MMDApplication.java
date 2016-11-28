package com.maian.mmd.base;

import android.app.Activity;
import android.app.Application;

import com.maian.mmd.entity.PersonService;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.entity.User;

import org.xutils.x;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MMDApplication extends Application {
    public static String cookie=null;
    public static com.maian.mmd.entity.User user;
    public static PersonService service = null;
    public static List<ResultCode> list_zhuye ;
    public static List<Activity> activityManagers;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        list_zhuye = new ArrayList<>();
        activityManagers = new ArrayList<>();

    }
}
