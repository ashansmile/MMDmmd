package com.maian.mmd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.adapter.GridViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.DataCleanManager;
import com.maian.mmd.view.CarouselView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class WorkeActivity extends BaseActivity {

    private LinearLayout leftLinearLayout;
    private List<String> grildList;
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worke);
        initHead();
        initViewPager();
        initLeft();
        initGrildViewDatas();
        initGrildView();
    }

    private void initHead() {
        leftLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_left);
        ImageView imgShouchang = (ImageView) findViewById(R.id.img_shouchang);
        imgShouchang.setClickable(true);
        imgShouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ErJiTableActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initGrildViewDatas() {
        grildList = new ArrayList<>();
        //textDatas
        grildList.add("测试");
        grildList.add("测试");
        grildList.add("测试");
        grildList.add("测试");

    }

    private void initGrildView() {
        gridView = (GridView) findViewById(R.id.gridView);
        GridViewAdapter adapter = new GridViewAdapter(grildList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "点击了", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getBaseContext(), ErJiTableActivity.class);
                startActivity(intent);

            }
        });
    }


    private void initLeft() {

        TextView textView_name = (TextView) findViewById(R.id.textView_user_name) ;
        if (MMDApplication.User != null){
            textView_name.setText(MMDApplication.User);
        }else{
            textView_name.setText("未登陆");
        }


        //服务器
        TextView textView_service = (TextView) findViewById(R.id.textView_service);
        textView_service.setClickable(true);
        textView_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),FuwuqiLiebiaoActivity.class);
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
                    String cacheSize =  DataCleanManager.getTotalCacheSize(getBaseContext());
                    Toast.makeText(WorkeActivity.this, "你已清除"+cacheSize, Toast.LENGTH_SHORT).show();
                    DataCleanManager.clearAllCache(getBaseContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        //关于
        TextView textView_guanyu= (TextView) findViewById(R.id.textView_guanyu);
        textView_guanyu.setClickable(true);
        textView_guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),GuanyuActivity.class);
                startActivity(intent);
            }
        });
        //注销
        TextView textView_zhuxiao= (TextView) findViewById(R.id.textView_zhuxiao);
        textView_zhuxiao.setClickable(true);
        textView_zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMDApplication.User = null;
                Intent intent = new Intent(getBaseContext(),Login.class);
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
                    int tem = position;

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
}
