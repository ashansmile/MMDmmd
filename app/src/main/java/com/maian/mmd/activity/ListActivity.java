package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.maian.mmd.R;
import com.maian.mmd.adapter.LevelGridAdapter;
import com.maian.mmd.adapter.LevelListAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.BaseLevel;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ChildResult;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.main.SplashActivity;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.HDbManager;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.MyRefreshListener;
import com.maian.mmd.utils.NetRequestParamsUtil;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.ShareUtil;
import com.maian.mmd.utils.SharedpreferencesUtil;
import com.maian.mmd.utils.xutilsCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {

    private List<ChildResult> list_data = new ArrayList<>();
    private ResultCode result;
    private BaseAdapter adapter1, adapter2;
    private PullToRefreshListView refreshListView;
    private PullToRefreshGridView refreshgridView;
    private PullToRefreshBase baserefresh;
    private AdapterView.AdapterContextMenuInfo lm;
    private boolean isGrid;
    private ImageView imgGrid;
    private ViewGroup emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_level);
        initData();
        initView();
    }

    private void initData() {

        Intent intent = getIntent();
        try {
            result = (ResultCode) intent.getSerializableExtra("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        judgeNet();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // 加载xml中的上下文菜单
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share, menu);
        lm = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareMessage();
                break;

            case R.id.collect:
                collectForm();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void collectForm() {
        ChildResult child = null;
        if (isGrid) {

            child = list_data.get(lm.position);
        } else {

            child = list_data.get(lm.position - 1);
        }
        HDbManager.collectForm(child, this, child.id);
       /* if (child.hasChild == true){
            HDbManager.collectForm(child,this,child.id);
        }else {
            Toast.makeText(this, "该文件是目录不能收藏", Toast.LENGTH_SHORT).show();
        }*/

    }

    private void shareMessage() {
        if (isGrid) {
            //网格分享
            new ShareUtil(this).shareMessageURL(list_data.get(lm.position));
        } else {
            //列表分享
            new ShareUtil(this).shareMessageURL(list_data.get(lm.position - 1));
        }
    }

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
            handleOnLine(MMDApplication.user.name, MMDApplication.user.pwd);
        }
    }


    private void getLieBiao() {
        x.http().post(NetRequestParamsUtil.getMenuParms(Contact.serviceUrl, result.catId), new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                //  System.out.println("----"+result);
                JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                //System.out.println("----result:" + root);
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ChildResult n = JSON.parseObject(arr.get(i).toString(), ChildResult.class);
                    if (n.showOnPhone == true) {
                        list_data.add(n);
                    }

                }
                if (isGrid) {
                    if (adapter2 != null) {
                        adapter2.notifyDataSetChanged();
                    }
                } else {
                    if (adapter1 != null) {
                        adapter1.notifyDataSetChanged();
                    }
                }

                baserefresh.onRefreshComplete();

            }

        });
    }

    private void initView() {
        TextView title = (TextView) findViewById(R.id.textView_head_title);
        title.setText(result.catName);
        imgGrid = (ImageView) findViewById(R.id.imageView_change);
        refreshListView = (PullToRefreshListView) findViewById(R.id.listView_baseleve);
        refreshgridView = (PullToRefreshGridView) findViewById(R.id.gridView_baseleve);
        //空视图
        emptyView = (ViewGroup) View.inflate(this, R.layout.item_empity, null);
        emptyView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        isGrid = SplashActivity.isGrid;
        if (isGrid) {
            imgGrid.setImageResource(R.drawable.top_list);
            baserefresh = refreshgridView;
            refreshListView.setVisibility(View.GONE);
            refreshgridView.setVisibility(View.VISIBLE);
        } else {

            imgGrid.setImageResource(R.drawable.top_grid);
            baserefresh = refreshListView;
            refreshgridView.setVisibility(View.GONE);
            refreshListView.setVisibility(View.VISIBLE);
        }
        ((ViewGroup) baserefresh.getParent()).addView(emptyView);
        isGrid();
        isList();

        //注册上下文
        registerForContextMenu(refreshListView.getRefreshableView());
        registerForContextMenu(refreshgridView.getRefreshableView());

        baserefresh.setOnRefreshListener(new MyRefreshListener() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                list_data.clear();
                judgeNet();
            }
        });


    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void handleOnLine(String username, String password) {
        super.handleOnLine(username, password);
    }

    @Override
    public void handleThing() {
        list_data.clear();
        getLieBiao();
    }


    private void isGrid() {

        refreshgridView.setEmptyView(emptyView);
        adapter2 = new LevelGridAdapter(list_data, this);
        refreshgridView.setAdapter(adapter2);
        refreshgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list_data.get(position).hasChild == true) {
                    Intent intent = new Intent(getBaseContext(), BaseLevel.class);
                    intent.putExtra("list", list_data.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                    intent.putExtra("web", list_data.get(position));
                    startActivity(intent);
                }
            }
        });

    }


    private void isList() {

        refreshListView.setEmptyView(emptyView);
        adapter1 = new LevelListAdapter(list_data, this);
        refreshListView.setAdapter(adapter1);

        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list_data.get(position - 1).hasChild == true) {
                    Intent intent = new Intent(getBaseContext(), BaseLevel.class);
                    intent.putExtra("list", list_data.get(position - 1));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), WebViewActivity.class);
                    intent.putExtra("web", list_data.get(position - 1));
                    startActivity(intent);
                }
            }
        });

    }


    //九宫格切换
    public void changeGrid(View view) {
        if (isGrid) {
            isGrid = false;
            imgGrid.setImageResource(R.drawable.top_grid);
            baserefresh = refreshgridView;
            refreshgridView.setVisibility(View.GONE);
            refreshListView.setVisibility(View.VISIBLE);
            adapter1.notifyDataSetChanged();
            SharedpreferencesUtil.saveIsGrid(this, isGrid);
        } else {
            isGrid = true;
            imgGrid.setImageResource(R.drawable.top_list);
            baserefresh = refreshgridView;
            refreshListView.setVisibility(View.GONE);
            refreshgridView.setVisibility(View.VISIBLE);
            adapter2.notifyDataSetChanged();
            SharedpreferencesUtil.saveIsGrid(this, isGrid);
        }
        SplashActivity.isGrid = isGrid;
    }
}
