package com.maian.mmd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.PersonService;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;
import com.maian.mmd.view.LoginEditText;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Login extends BaseActivity {

    private LoginEditText username = null;
    private LoginEditText password = null;
    private Button log = null;
    private Button register = null;
    private CheckBox checkBox = null;
    TextView textView_service_name_dangqian;
    Spinner spinner_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        //initService();
        initEditText();



    }

    private void initService() {
//        textView_service_name_dangqian = (TextView) findViewById(R.id.textView_service_name_dangqian);
//        spinner_service = (Spinner) findViewById(R.id.spinner_service_name_dangqian);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setService();
        //textView_service_name_dangqian.setText("服务器：" + MMDApplication.service);
    }

    private void setService() {
      /*  List<String> items = new ArrayList<>();
        for (int i = 0; i < MMDApplication.list_service.size(); i++) {
            items.add(MMDApplication.list_service.get(i).serviceName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        spinner_service.setAdapter(adapter);*/
    }

    private void initEditText() {
        username = (LoginEditText) findViewById(R.id.username);
        password = (LoginEditText) findViewById(R.id.password);
        username.setBackground(R.mipmap.username);
        password.setBackground(R.mipmap.password);
        log = (Button) findViewById(R.id.log);
        // register = (Button) findViewById(R.id.register);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        username.setHint("请输入账号");
        password.setHint("请输入密码");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//将密码设置为password
        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username", ""));
        password.setText(sharedPreferences.getString("password", ""));
        if (sharedPreferences.getString("password", "") != "")
            checkBox.setChecked(true);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", "");
                    editor.apply();
                }
                judgeNet();
            }
        });
    }

    //判断网络
    private void judgeNet() {
        if (NetworkMonitor.isNetworkAvailable(getBaseContext())) {
            judgeTijiaoXinxi();
        } else {
            Toast.makeText(Login.this, "网络未连接无法登录 ", Toast.LENGTH_SHORT).show();
        }
    }

    //判断信息的合法性
    private void judgeTijiaoXinxi() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        if (userName != null && !"".equals(userName) && !" ".equals(userName) && passWord != null && !"".equals(passWord) && !" ".equals(passWord)) {
            login(userName,passWord);
        } else {
            Toast.makeText(Login.this, "信息不完整", Toast.LENGTH_SHORT).show();
        }
    }

    //登录
    private void login(String username,String password) {
        RequestParams requestParams = new RequestParams(Contact.getLoginUrl(username,password));
        /*requestParams.addBodyParameter("UserName",username);
        requestParams.addBodyParameter("Password",password);*/

        x.http().post(requestParams,new xutilsCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                System.out.println("------成功"+result);
                Intent intent = new Intent(getBaseContext(),WorkeActivity.class);
                startActivity(intent);
                super.onSuccess(result);
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                System.out.println("----"+ex.toString());
            }
        });

    }

}
