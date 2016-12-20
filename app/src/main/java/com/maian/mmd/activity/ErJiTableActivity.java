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
import com.maian.mmd.adapter.ErJiListViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
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
        listErji = new ArrayList<>();
        Intent intent = getIntent();
        result = (ResultCode) intent.getSerializableExtra("resultCode");
        judgeNet();
        initView();
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
        if (Login.isLogin(MMDApplication.user.name)) {
            getLieBiao();
        } else {
            x.http().post(Login.loginParms(MMDApplication.user.name, MMDApplication.user.pwd),
                    new xutilsCallBack<String>() {
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
                JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                System.out.println("----result:"+root);
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ErJiLiebiao n = JSON.parseObject(arr.get(i).toString(), ErJiLiebiao.class);
                    if (n.showOnPhone.equals("true")) {
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
        //空视图
        ViewGroup emptyView = (ViewGroup) View.inflate(this, R.layout.item_empity, null);
        emptyView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listViewErji.getParent()).addView(emptyView);
        listViewErji.setEmptyView(emptyView);


        adapter = new ErJiListViewAdapter(listErji,this);
        listViewErji.setAdapter(adapter);

        listViewErji.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                listErji.clear();
               judgeNet();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        listViewErji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listErji.get(position - 1).hasChild == true) {
                    Intent intent = new Intent(getBaseContext(), SanjiTableActivity.class);
                    intent.putExtra("Erjitable", listErji.get(position - 1));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                    intent.putExtra("web", listErji.get(position - 1));
                    startActivity(intent);
                }
            }
        });

    }

    public void goBack(View view) {
        finish();
//        Intent intent = new Intent(this, WorkeActivity.class);
//        startActivity(intent);
    }
}
