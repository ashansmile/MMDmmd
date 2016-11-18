package com.maian.mmd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.maian.mmd.R;
import com.maian.mmd.adapter.ErJiListViewAdapter;
import com.maian.mmd.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ErJiTableActivity extends BaseActivity {
    List<String> listErji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_ji);
        initDatas();
        initView();
    }

    private void initDatas() {
        listErji = new ArrayList<>();
        listErji.add("测试1");
        listErji.add("测试2");
        listErji.add("测试3");
    }

    private void initView() {
        ListView listViewErji = (ListView) findViewById(R.id.listView_erji);
        ErJiListViewAdapter adapter = new ErJiListViewAdapter(listErji);
        listViewErji.setAdapter(adapter);

    }
    public void goBack(View view ){
        Intent intent = new Intent(this,WorkeActivity.class);
        startActivity(intent);
    }
}
