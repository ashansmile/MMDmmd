package com.maian.mmd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.alibaba.fastjson.JSON;
import com.maian.mmd.R;
import com.maian.mmd.activity.PushDetialActivity;
import com.maian.mmd.entity.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by admin on 2017/1/3.
 */

public class MessageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //text();
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("----传到这儿了");
        //notify("https://www.baidu.com",1);
        //notifyMessage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                text();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void text() {
        try {
            System.out.println("----creat Socket");
            //1、创建客户端Socket，指定服务器端口号和地址
            Socket socket = new Socket("192.168.1.34", 8801);
            System.out.println("----"+socket.getInetAddress().toString());
            //2、获取输出流,向服务器发送信息
            OutputStream os = socket.getOutputStream(); //字节输出流
            PrintWriter pw = new PrintWriter(os); //将输出流包装为打印流
            pw.flush();
            socket.shutdownOutput(); //关闭输出流

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String info = null;

            //循环读取
            while ((info = br.readLine()) != null) {
                System.out.println("----服务器:" + info);
                    notifyMessage(info);
            }

            br.close();
            is.close();
            isr.close();


            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----"+e.toString());
            text();

        }
    }

    private void notifyMessage(String str) {
        try {
            str = new String(str.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Message message = JSON.parseObject(str, Message.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // 必需的通知内容
        builder.setContentTitle(message.title)
                .setContentText(message.description)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
        ;


        Intent notifyIntent = new Intent(this, PushDetialActivity.class);
        notifyIntent.putExtra("url_push", message.url);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // 点击清除按钮或点击通知后会自动消失

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);

       // startForeground(1, notification);
    }

    private void judgeMessage(){

    }
}

