package com.maian.mmd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;

public class GuanyuActivity extends BaseActivity {

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
                /*Intent intent = new Intent(getBaseContext(),WorkeActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
