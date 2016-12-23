package com.maian.mmd.base;

import android.app.Activity;
import android.app.Application;

import com.maian.mmd.entity.PersonService;
import com.maian.mmd.entity.ResultCode;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MMDApplication extends Application {
    public static int ISFIRSTUSE = 0;
    public static String cookie = null;
    public static com.maian.mmd.entity.User user;
    public static PersonService service = null;
    public static List<ResultCode> list_zhuye;
    public static List<Activity> activityManagers;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        list_zhuye = new ArrayList<>();
        activityManagers = new ArrayList<>();


        initUmeng();
    }

    private void initUmeng() {
        PlatformConfig.setWeixin("wxb6514a83a5cf0d42", "1f584bf3e5d3cad9428a536782730c3a");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }
}
