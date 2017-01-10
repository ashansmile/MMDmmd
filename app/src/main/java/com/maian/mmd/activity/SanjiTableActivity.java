package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.maian.mmd.R;
import com.maian.mmd.adapter.SanjiListViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SanjiTableActivity extends BaseActivity {
    private ErJiLiebiao erjiEntity;
    private List<ErJiLiebiao> list;
    private SanjiListViewAdapter adapter;
    private PullToRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanji_table);
        list = new ArrayList<>();
        Intent intent = getIntent();
        erjiEntity = (ErJiLiebiao) intent.getSerializableExtra("Erjitable");
        judgeNet();
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


        //空视图
        ViewGroup emptyView=(ViewGroup) View.inflate(this, R.layout.item_empity, null);
        emptyView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);


        adapter = new SanjiListViewAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                intent.putExtra("web", list.get(position-1));
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                judgeNet();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });


    }

    private void judgeNet() {

        if(NetworkMonitor.isNetworkAvailable(this)){
            //获取json
            getData();
        }else {
            Toast.makeText(this, "当前网络未连接", Toast.LENGTH_SHORT).show();
        }

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
                JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ErJiLiebiao n = JSON.parseObject(arr.get(i).toString(), ErJiLiebiao.class);
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

