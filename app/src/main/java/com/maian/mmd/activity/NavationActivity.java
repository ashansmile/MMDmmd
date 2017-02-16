package com.maian.mmd.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;

import java.util.ArrayList;

public class NavationActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ArrayList<View> list = new ArrayList<View>();
    private ImageView imag[];
    private ImageView button;
    private NavationPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navation);
        initdata();
        initpoints();
        initview();

    }
    private void initview() {
        button = (ImageView) findViewById(R.id.button);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setOnPageChangeListener(this);
        adapter = new NavationPagerAdapter(list);
        viewpager.setAdapter(adapter);

    }

    //小圆点的设置
    private void initpoints() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearlayout);
        int count = ll.getChildCount();
        imag = new ImageView[count];
        for (int i = 0; i < count; i++) {
            ImageView x = (ImageView) ll.getChildAt(i);
            imag[i] = x;

        }

    }

    private void initdata() {
        ImageView image1 = new ImageView(this);
        image1.setScaleType(ImageView.ScaleType.FIT_XY);
        image1.setImageResource(R.mipmap.guide1);
        ImageView image2 = new ImageView(this);
        image2.setScaleType(ImageView.ScaleType.FIT_XY);
        image2.setImageResource(R.mipmap.guide2);
        ImageView image3 = new ImageView(this);
        image3.setScaleType(ImageView.ScaleType.FIT_XY);
        image3.setImageResource(R.mipmap.guide3);
        list.add(image1);
        list.add(image2);
        list.add(image3);
    }

    int current = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        imag[position].setScaleType(ImageView.ScaleType.FIT_XY);
        if (position == current) {
            imag[position].setImageResource(android.R.drawable.presence_online);
        } else {
            imag[position].setImageResource(android.R.drawable.presence_online);
            imag[current].setImageResource(android.R.drawable.presence_invisible);
            current = position;
        }
        if (position == list.size() - 1) {
            SharedPreferences sp = getSharedPreferences("args", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("isGuid", true).commit();
        }

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void click(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}

