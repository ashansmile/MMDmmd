package com.maian.mmd.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by admin on 2016/12/14.
 */

public class SdcardUtils {

    public static String imgPath;
    /**
     * 获取SDCard的目录路径功能
     * @return
     */
    public static String getSDCardPath(){
        File sdcardDir = null;
        //判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdcardExist){
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    public static String saveBitmap(Bitmap bmp){

        String SavePath = getSDCardPath()+"/MMD_ScreenImage";

        //3.保存Bitmap
        try {
            File path = new File(SavePath);
            UUID uuid = UUID.randomUUID();
            //文件
            imgPath = SavePath + "/"+uuid.toString()+".png";
            File file = new File(imgPath);
            if(!path.exists()){
                path.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgPath;
    }
}
