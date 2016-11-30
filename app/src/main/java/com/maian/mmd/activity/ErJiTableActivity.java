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
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ErJiTableActivity extends BaseActivity {
    List<ErJiLiebiao> listErji;
    ResultCode result;
    ErJiListViewAdapter adapter;
    PullToRefreshListView listViewErji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_ji);
        initDatas();
        initView();
    }

    private void initDatas() {
        listErji = new ArrayList<>();
        Intent intent = getIntent();
        result = (ResultCode) intent.getSerializableExtra("resultCode");
        // System.out.println("----传递过来:" + result.catName + "------" + result.catId);
        //获取json
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
        params.addBodyParameter("params", "[\"" + result.catId + "\"]");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("----二级列表数据:" + result);
                JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ErJiLiebiao n = JSON.parseObject(arr.get(i).toString(), ErJiLiebiao.class);
                    System.out.println("----json:" + n.name);
                    if(n.showOnPhone.equals("true")){
                        listErji.add(n);
                    }

                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                listViewErji.onRefreshComplete();

            }

        });
    }

    private void initView() {
        TextView textView_title = (TextView) findViewById(R.id.textView_title);
        textView_title.setText(result.catName);
        listViewErji = (PullToRefreshListView) findViewById(R.id.listView_erji);
        adapter = new ErJiListViewAdapter(listErji);
        listViewErji.setAdapter(adapter);
        listViewErji.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                listErji.clear();
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listViewErji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("------二级点击位置:"+position);
                if (listErji.get(position-1).hasChild == true) {
                    Intent intent = new Intent(getBaseContext(), SanjiTableActivity.class);
                    intent.putExtra("Erjitable", listErji.get(position-1));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                    intent.putExtra("web", listErji.get(position-1));
                    startActivity(intent);
                }
            }
        });

    }

    public void goBack(View view) {
        Intent intent = new Intent(this, WorkeActivity.class);
        startActivity(intent);
    }
}
