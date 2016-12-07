package com.maian.mmd.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.utils.Login;

import java.util.ArrayList;

public class NavationActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    ArrayList<View> list = new ArrayList<View>();
    ImageView imag[];
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
        image1.setImageResource(R.drawable.guide0);
        ImageView image2 = new ImageView(this);
        image2.setImageResource(R.drawable.guide1);
        ImageView image3 = new ImageView(this);
        image3.setImageResource(R.drawable.guide2);
        ImageView image4 = new ImageView(this);
        image4.setImageResource(R.drawable.guide3);
        list.add(image1);
        list.add(image2);
        list.add(image3);
        list.add(image4);

    }

    int current = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
        if (position == 3) {
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

