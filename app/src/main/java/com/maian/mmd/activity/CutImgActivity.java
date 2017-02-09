package com.maian.mmd.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.utils.BitmapHandlerUtil;
import com.maian.mmd.utils.ShareUtil;
import com.maian.mmd.view.HandWrite;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.weixin.handler.UmengWXHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class CutImgActivity extends BaseActivity {
    private HandWrite handWrite = null;
    private int whichColor = 0;
    private int whichStrokeWidth = 0;
    private String imgPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_draw);
        init();
        getBitmap();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void init(){
        handWrite = (HandWrite) findViewById(R.id.handwriteview);
        ImageView img_close = (ImageView) findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getBitmap() {
        Intent intent = getIntent();
        imgPath = intent.getStringExtra("img");
        handWrite.setImgPath(imgPath);

    }


    public void clickSelect(View view) {
        switch (view.getId()) {
            case R.id.textView_clear:
                handWrite.clear();
                break;
            case R.id.textView_black:
                handWrite.color = Color.BLACK;
                whichColor = 0;
                break;
            case R.id.textView_blue:
                handWrite.color = Color.BLUE;
                whichColor = 1;
                break;
            case R.id.textView_red:
                handWrite.color = Color.RED;
                whichColor = 2;
                break;

            case R.id.textViewtView_share:
                File f = new File(imgPath);
                try {

                    saveMyBitmap(f, handWrite.new1Bitmap);
                   // share();
                    shareSelect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    private void shareSelect() {
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        bitmap = BitmapHandlerUtil.imageZoom(bitmap);
        new ShareUtil(this).shareMessageImg(bitmap);

       /* //图片内存处理
        // 先判断是否已经回收
        if(bitmap != null && !bitmap.isRecycled()){
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();*/
    }


    public void saveMyBitmap(File f, Bitmap mBitmap) throws IOException {
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            /*//图片内存处理
            // 先判断是否已经回收
            if(mBitmap != null && !mBitmap.isRecycled()){
                // 回收并且置为null
                mBitmap.recycle();
                mBitmap = null;
            }
            System.gc();*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}