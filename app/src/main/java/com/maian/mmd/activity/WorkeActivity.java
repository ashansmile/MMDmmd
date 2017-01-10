package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.maian.mmd.R;
import com.maian.mmd.adapter.GridViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.DataCleanManager;
import com.maian.mmd.utils.HDbManager;
import com.maian.mmd.utils.Login;
import com.maian.mmd.utils.xutilsCallBack;
import com.maian.mmd.view.CarouselView;

import org.xutils.DbManager;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.maian.mmd.utils.Contact.serviceUrl;

public class WorkeActivity extends BaseActivity {

    private LinearLayout leftLinearLayout;
    private List<ResultCode> grildList;
    private PullToRefreshGridView gridView;
    private GridViewAdapter grildAdapter;
    private TextView textView_name;
    private TextView textView_marquee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worke);
        initgridlViewdata();
        initHead();
        initViewPager();
        initLeft();
        initGrildView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        initUserName();
    }

    private void initgridlViewdata() {
        grildList = new ArrayList<>();
        Login.tongZhiPhone();
        getData();
    }

    private void getData() {

        if (!Login.isLogin(MMDApplication.user.name)) {
            x.http().post(Login.loginParms(MMDApplication.user.name, MMDApplication.user.pwd), new xutilsCallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            getmainXinxi();
                        }
                    }
            );
        } else {
            getmainXinxi();
        }
    }

    private void addDatasFromDB() {
        DbManager db = x.getDb(HDbManager.getZhuYeDb());
        try {
            grildList = db.selector(ResultCode.class).findAll();
            if (grildList == null) {
                grildList = new ArrayList<>();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getmainXinxi() {
        RequestParams params = new RequestParams(serviceUrl);
        params.addBodyParameter("className", "CatalogPublishService");
        params.addBodyParameter("params", "[]");
        params.addBodyParameter("methodName", "getPublishCatalogsOfCurrentUser");
        x.http().post(params, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                com.alibaba.fastjson.JSONObject data = JSON.parseObject(result);
                String root = data.getString("result");
                JSONArray arr = JSON.parseArray(root);
                for (int i = 0; i < arr.size(); i++) {
                    ResultCode n = JSON.parseObject(arr.get(i).toString(), ResultCode.class);
                    grildList.add(n);
                }

                if (HDbManager.SAVEDBBIAOZHI == 0) {
                    saveSouyeDB();
                    HDbManager.SAVEDBBIAOZHI = 1;
                }
                if (grildList.size() == 0) {
                    addDatasFromDB();
                }
                if (grildAdapter != null) {
                    grildAdapter.notifyDataSetChanged();
                }
                gridView.onRefreshComplete();
            }
        });
    }

    private void initUserName() {
        textView_name.setText("用户名："+MMDApplication.user.name);
    }

    private void initHead() {
        leftLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_left);
        ImageView imgShouchang = (ImageView) findViewById(R.id.img_shouchang);
        imgShouchang.setClickable(true);
        imgShouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ShouChangActivity.class);
                startActivity(intent);
            }
        });

        textView_marquee = (TextView) findViewById(R.id.textView_marquee);
        getMarquee();

    }


    private void initGrildView() {
        gridView = (PullToRefreshGridView) findViewById(R.id.refreshGridView);
        //空视图
        ViewGroup emptyView=(ViewGroup) View.inflate(this, R.layout.item_empity, null);
        emptyView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)gridView.getParent()).addView(emptyView);
        gridView.setEmptyView(emptyView);


        grildAdapter = new GridViewAdapter(grildList,this);
        gridView.setAdapter(grildAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), ErJiTableActivity.class);
                intent.putExtra("resultCode", grildList.get(position));
                startActivity(intent);

            }
        });
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<GridView> refreshView) {
                grildList.clear();
                getData();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<GridView> refreshView) {

            }
        });
    }


    private void initLeft() {

        textView_name = (TextView) findViewById(R.id.textView_user_name);


        //服务器
        TextView textView_service = (TextView) findViewById(R.id.textView_service);
        textView_service.setClickable(true);
        textView_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FuwuqiLiebiaoActivity.class);
                startActivity(intent);
            }
        });
        //清除缓存
        TextView textView_clearData = (TextView) findViewById(R.id.textView_clearData);
        textView_clearData.setClickable(true);
        textView_clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String cacheSize = DataCleanManager.getTotalCacheSize(getBaseContext());
                    Toast.makeText(WorkeActivity.this, "你已清除" + cacheSize, Toast.LENGTH_SHORT).show();
                    DataCleanManager.clearAllCache(getBaseContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        //关于
        TextView textView_guanyu = (TextView) findViewById(R.id.textView_guanyu);
        textView_guanyu.setClickable(true);
        textView_guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GuanyuActivity.class);
                startActivity(intent);
            }
        });
        //注销
        TextView textView_zhuxiao = (TextView) findViewById(R.id.textView_zhuxiao);
        textView_zhuxiao.setClickable(true);
        textView_zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MMDApplication.user = null;
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    //视图的轮播
    private void initViewPager() {
        CarouselView carouselView = (CarouselView) findViewById(R.id.lunbo);
        carouselView.setAdapter(new CarouselView.Adapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(final int position) {
                View view = View.inflate(getBaseContext(), R.layout.item, null);

                ImageView imageView = (ImageView) view.findViewById(R.id.image);

                x.image().bind(imageView, Contact.img_lunbo[position]);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                return view;
            }

            @Override
            public int getCount() {
                return Contact.img_lunbo.length;
            }
        });
    }

    private void getMarquee(){
        String marquee = "效益，因管理而改变！管理，因我们（MMD）而改变！";
        textView_marquee.setText(marquee);
        /*RequestParams params = new RequestParams(serviceUrl);
        params.addBodyParameter("className", "mobilePortalModule");
        params.addBodyParameter("params", "[]");
        params.addBodyParameter("methodName", "getMarquee");
        x.http().post(params,new xutilsCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                com.alibaba.fastjson.JSONObject root = JSON.parseObject(result);
                String marquee = root.getString("result");
                System.out.println("----"+marquee);
                marquee = "效益，因管理而改变！管理，因我们（MMD）而改变！";
                textView_marquee.setText(marquee);
            }
        });*/
    }
    private void saveSouyeDB() {
        DbManager db = x.getDb(HDbManager.getZhuYeDb());
        if (grildList != null) {

            for (int i = 0; i < grildList.size(); i++) {
                try {
                    db.save(grildList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
