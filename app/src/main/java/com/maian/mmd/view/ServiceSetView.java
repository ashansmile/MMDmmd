package com.maian.mmd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.maian.mmd.R;

/**
 * Created by admin on 2016/11/29.
 */

public class ServiceSetView extends LinearLayout {
    public ServiceSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.service_set, this);//设置布局文件
    }


}
