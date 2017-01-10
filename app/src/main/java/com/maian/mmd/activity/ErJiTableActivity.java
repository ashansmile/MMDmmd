package com.maian.mmd.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.maian.mmd.utils.BitmapHandlerUtil;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.weixin.handler.UmengWXHandler;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErJiTableActivity extends BaseActivity {
    private List<ErJiLiebiao> listErji;
    private ResultCode result;
    private ErJiListViewAdapter adapter;
    private PullToRefreshListView listViewErji;
    private AdapterView.AdapterContextMenuInfo lm;
    int item_position;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // 加载xml中的上下文菜单
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share, menu);
        lm = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //System.out.println("----第" + lm.id + "项,弹出菜单注册成功");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareMessage();

                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    //分享链接
    private void shareMessage(){
        ErJiLiebiao entity=listErji.get(lm.position-1);
        new ShareAction(this)
                .withTitle("报表"+entity.alias)
                .withTargetUrl(Contact.getwebUrl(entity.id,"demo","demo"))
                .setDisplayList( SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                .setCallback(umShareListener).open();

    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            System.out.println("----plat"+platform);

            Toast.makeText(getBaseContext(), " 分享成功", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            // Toast.makeText(getBaseContext(), platform.toString()+t.toString() + " 分享失败啦", Toast.LENGTH_LONG).show();
            if (t != null) {
                System.out.println("----platform"+platform);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(), " 你取消了分享", Toast.LENGTH_SHORT).show();
        }
    };

    private void judgeNet() {

        if (NetworkMonitor.isNetworkAvailable(this)) {
            //获取json
            getData();
        } else {
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
                System.out.println("----result:" + root);
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


        adapter = new ErJiListViewAdapter(listErji, this);
        listViewErji.setAdapter(adapter);
        //注册上下文
        registerForContextMenu(listViewErji.getRefreshableView());


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
