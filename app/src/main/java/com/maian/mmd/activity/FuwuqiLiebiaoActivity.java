package com.maian.mmd.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.adapter.ServiceListViewAdapter;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.PersonService;

import java.util.ArrayList;
import java.util.List;

public class FuwuqiLiebiaoActivity extends BaseActivity {
    PopupWindow popupWindow;
    EditText editText_service_name;
    EditText editText_service_adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuwuqi_liebiao);
        initPopUpWindow();
        initHead();
        initListService();
    }

    private void initListService() {


        ListView listView_service = (ListView) findViewById(R.id.listView_service);
        ServiceListViewAdapter adapter = new ServiceListViewAdapter(MMDApplication.list_service);
        listView_service.setAdapter(adapter);
    }

    private void initPopUpWindow() {
        View popupWindow_service = View.inflate(getBaseContext(), R.layout.popupwindow_service, null);
        popupWindow = new PopupWindow(popupWindow_service);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style); // 设置动画
        //事件处理
        editText_service_name = (EditText) popupWindow_service.findViewById(R.id.editText_service_name);
        editText_service_adress = (EditText) popupWindow_service.findViewById(R.id.editText_service_adress);
        Button button_clear = (Button) popupWindow_service.findViewById(R.id.button_clear);
        Button button_submit = (Button) popupWindow_service.findViewById(R.id.button_submit);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_service_name.setText("");
                editText_service_adress.setText("");
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serviceName = editText_service_name.getText().toString();
                String serviceAdress =  editText_service_adress.getText().toString();
                if (serviceName != null && !"".equals(serviceName) && serviceAdress != null && !" ".equals(serviceAdress)){
                    popupWindow.dismiss();
                    MMDApplication.list_service.add(new PersonService(serviceName,serviceAdress));
                }else {
                    Toast.makeText(FuwuqiLiebiaoActivity.this, "请填写完整后提交", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void initHead() {
        ImageView imageViewMore = (ImageView) findViewById(R.id.img_more);
        imageViewMore.setClickable(true);
        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, WindowManager.LayoutParams.MATCH_PARENT, 200);
            }
        });

        TextView textView_back = (TextView) findViewById(R.id.textView_back);
        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WorkeActivity.class);
                startActivity(intent);
            }
        });
    }


}
