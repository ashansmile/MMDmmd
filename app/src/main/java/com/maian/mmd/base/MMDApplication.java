package com.maian.mmd.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.service.MessageService;
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
    public static List<ResultCode> list_zhuye;
    public static List<Activity> activityManagers;
    public static String url_push;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        list_zhuye = new ArrayList<>();
        activityManagers = new ArrayList<>();


        initUmeng();
        initService();
       // initUmengPush();
    }

    private void initService() {
        System.out.println("----do");
        Intent intent = new Intent(this, MessageService.class);
        startService(intent);
    }
/*

    UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
            Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            url_push = msg.custom;
            Intent intent = new Intent(context, PushDetialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }
    };
    private void initUmengPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(false);

        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
               // Toast.makeText(MMDApplication.this, "设备token："+deviceToken, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

    }
    */

    private void initUmeng() {
        PlatformConfig.setWeixin("wxb6514a83a5cf0d42", "1f584bf3e5d3cad9428a536782730c3a");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
    }
}
