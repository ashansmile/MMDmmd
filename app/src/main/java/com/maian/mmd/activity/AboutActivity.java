package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.entity.ChildResult;
import com.maian.mmd.utils.Constant;

public class  AboutActivity extends BaseActivity {
    
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanyu);
        initView();
    }



    private void initView() {
        TextView text_back = (TextView) findViewById(R.id.textView_back_guanyu);
        text_back.setClickable(true);
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        intent = new Intent(this,WebViewActivity.class);
    }


    public void clickAbout(View view){
        ChildResult entity = null;
        switch (view.getId()){

            case R.id.textView_about_1:
                entity = new ChildResult(Constant.ABOUT_MMD_ID,"迈安MMD介绍");
                break;
            case R.id.textView_about_2:
                entity = new ChildResult(Constant.ABOUT_PRODUCT_ID,"产品体系介绍");
                break;
            case R.id.textView_about_3:
                entity = new ChildResult(Constant.ABOUT_FORM_ID,"复杂报表介绍");
                break;
            case R.id.textView_about_4:
                entity = new ChildResult(Constant.ABOUT_DATAREDUCTION_ID,"数据整理介绍");
                break;
            case R.id.textView_about_5:
                entity = new ChildResult(Constant.ABOUT_METADATA_ID,"元数据平台介绍");
                break;
        }
        intent.putExtra("web",entity);
        startActivity(intent);

    }




}
