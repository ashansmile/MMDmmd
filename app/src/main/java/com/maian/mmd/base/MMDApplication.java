package com.maian.mmd.base;

import android.app.Application;

import com.maian.mmd.entity.PersonService;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MMDApplication extends Application {
    public static String User = "admin";
    public static String service = "服务器１";
    public static List<PersonService> list_service;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        list_service = new ArrayList<>();
        list_service.add(new PersonService("服务器1",""));
        list_service.add(new PersonService("服务器2",""));
    }
}
