package com.maian.mmd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.maian.mmd.R;
import com.maian.mmd.view.HandWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CanvasDrawActivity extends Activity {
    private HandWrite handWrite = null;
    private int whichColor = 0;
    private int whichStrokeWidth = 0;
    private String imgPath;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_draw);

        handWrite = (HandWrite)findViewById(R.id.handwriteview);
        getBitmap();
        //init();

    }

    private void getBitmap() {
        Intent intent = getIntent();
        imgPath = intent.getStringExtra("img");
        handWrite.setImgPath(imgPath);

    }


    public void clickSelect(View view){
        switch (view.getId()){
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


        }
    }


    private void setPaint() {
        Dialog mDialog = new AlertDialog.Builder(CanvasDrawActivity.this)
                .setTitle("画笔设置")
                .setSingleChoiceItems(new String[]{"细","中","粗"}, whichStrokeWidth,new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        switch(which)
                        {
                            case 0:
                            {
                                handWrite.strokeWidth = 3.0f;
                                whichStrokeWidth = 0;
                                break;
                            }
                            case 1:
                            {
                                handWrite.strokeWidth = 6.0f;
                                whichStrokeWidth = 1;
                                break;
                            }
                            case 2:
                            {
                                handWrite.strokeWidth = 3.0f;
                                whichStrokeWidth = 2;
                                break;
                            }
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).create();
        mDialog.show();
    }

    private void initPaint() {
        Dialog mDialog = new AlertDialog.Builder(CanvasDrawActivity.this)
                .setTitle("颜色设置")
                .setSingleChoiceItems(new String[]{"白色","绿色","红色"}, whichColor, new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        switch(which)
                        {
                            case 0:
                            {
                                //画笔的颜色
                                handWrite.color = Color.WHITE;
                                whichColor = 0;
                                break;
                            }
                            case 1:
                            {
                                //画笔的颜色
                                handWrite.color = Color.GREEN;
                                whichColor = 1;
                                break;
                            }
                            case 2:
                            {
                                //画笔的颜色
                                handWrite.color = Color.RED;
                                whichColor = 2;
                                break;
                            }
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                })
                .create();
        mDialog.show();
    }


    public void saveMyBitmap(File f, Bitmap mBitmap) throws IOException {
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}