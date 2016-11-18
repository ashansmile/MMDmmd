package com.maian.mmd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;

public class FuwuqiLiebiaoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuwuqi_liebiao);
        initHead();
    }
    private void initHead(){
        ImageView imageViewMore = (ImageView) findViewById(R.id.img_more);
        imageViewMore.setClickable(true);
        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView textView_back = (TextView) findViewById(R.id.textView_back);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),WorkeActivity.class);
                startActivity(intent);
            }
        });
    }

}
