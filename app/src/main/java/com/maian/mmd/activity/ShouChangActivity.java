package com.maian.mmd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;

public class ShouChangActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_chang);
        initHead();
    }

    private void initHead() {
        TextView textView_back = (TextView) findViewById(R.id.textView_goBack);
        TextView textView_biaoti = (TextView) findViewById(R.id.textView_head_title);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(getBaseContext(),WorkeActivity.class);
//                startActivity(intent);
            }
        });
        textView_biaoti.setText("收藏");

    }
}
