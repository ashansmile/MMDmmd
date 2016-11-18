package com.maian.mmd.view;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maian.mmd.R;

/**
 * Created by Administrator on 2016/11/16.
 */
public class LoginEditText extends LinearLayout {
    private ImageView imageView = null;
    private EditText editText = null;

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_edittext, this);//设置布局文件
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void setHint(String s) {
        editText.setHint(s);
    }

    public void setBackground(int resId) {
        imageView.setImageResource(resId);
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setInputType(int a) {
        editText.setInputType(a);
    }

    public void setText(String s) {
        editText.setText(s);
    }
}
