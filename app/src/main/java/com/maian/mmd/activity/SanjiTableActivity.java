package com.maian.mmd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.maian.mmd.R;
import com.maian.mmd.adapter.ErJiListViewAdapter;
import com.maian.mmd.adapter.SanjiListViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SanjiTableActivity extends BaseActivity {
    ErJiLiebiao erjiEntity;
    List<ErJiLiebiao> list;
    SanjiListViewAdapter adapter;
    PullToRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanji_table);
        init();
        initView();
    }

    private void initView() {
        TextView textView_back = (TextView) findViewById(R.id.textView_goBack);
        textView_back.setClickable(true);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*Intent intent = new Intent(getBaseContext(),ErJiTableActivity.class);
                startActivity(intent);*/
            }
        });
        TextView textView_title = (TextView) findViewById(R.id.textView_head_title);
        textView_title.setText(erjiEntity.name);

        listView = (PullToRefreshListView) findViewById(R.id.listView_sanji);
        adapter = new SanjiListViewAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("----点击位置:"+position);
                Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                intent.putExtra("web", list.get(position-1));
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });


    }

    private void init() {
        list = new ArrayList<>();
        Intent intent = getIntent();
        erjiEntity = (ErJiLiebiao) intent.getSerializableExtra("Erjitable");
        System.out.println("----二级列表;" + erjiEntity.id);
        getData();

    }

    private void getData() {
        Login.tongZhiPhone();
        if (Login.isLogin(MMDApplication.user.name)){
            getLieBiao();
        }else {
            x.http().post(Login.loginParms(MMDApplication.user.name,MMDApplication.user.pwd),
                    new xutilsCallBack<String>(){
                        @Override
                        public void onSuccess(String result) {
                            getLieBiao();
                        }
                    });
        }
    }

    private void getLieBiao() {
        RequestParams params = new RequestParams(Contact.serviceUrl);
        params.addBodyParameter("className", "CatalogService");
        params.addBodyParameter("methodName", "getChildElements");
        params.addBodyParameter("params", "[\"" + erjiEntity.id + "\"]");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                //System.out.println("----san级列表数据:" + result);
                JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ErJiLiebiao n = JSON.parseObject(arr.get(i).toString(), ErJiLiebiao.class);
                    System.out.println("----json:" + n.name);
                    if(n.showOnPhone.equals("true")){
                        list.add(n);
                    }
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                listView.onRefreshComplete();
            }
        });
    }


}

