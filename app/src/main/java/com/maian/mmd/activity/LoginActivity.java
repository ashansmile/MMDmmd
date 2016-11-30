package com.maian.mmd.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maian.mmd.R;
import com.maian.mmd.base.BaseActivity;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.PersonService;
import com.maian.mmd.entity.User;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.HDbManager;
import com.maian.mmd.utils.NetworkMonitor;
import com.maian.mmd.utils.xutilsCallBack;
import com.maian.mmd.view.LoginEditText;
import com.maian.mmd.view.ServiceSetView;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private LoginEditText username = null;
    private LoginEditText password = null;
    private Activity loginActivity;
    private Button log = null;
    private CheckBox checkBox = null;
    private ProgressDialog dialog;
    private String name;
    private String pwd;
    private String serviceUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        initSet();
        initEditText();


    }

    private void initSet() {
        ServiceSetView imageView_set = (ServiceSetView) findViewById(R.id.serviceSetView);
        imageView_set.setClickable(true);
        imageView_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),FuwuqiLiebiaoActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initEditText() {
        username = (LoginEditText) findViewById(R.id.username);
        password = (LoginEditText) findViewById(R.id.password);
        username.setBackground(R.mipmap.username);
        password.setBackground(R.mipmap.password);
        log = (Button) findViewById(R.id.log);
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
                    String name = username.getText().toString();
                    String pwd = password.getText().toString();
                    String serviceUrl = Contact.serviceUrl;
                    insertDB(new User(name, pwd, serviceUrl));
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
            Toast.makeText(LoginActivity.this, "网络未连接无法登录 ", Toast.LENGTH_SHORT).show();
        }
    }

    //判断信息的合法性
    private void judgeTijiaoXinxi() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        if (userName != null && !"".equals(userName) && !" ".equals(userName) && passWord != null && !"".equals(passWord) && !" ".equals(passWord)) {
            dialog = ProgressDialog.show(LoginActivity.this, "登录提示", "正在登录，请稍等...", false);//创建ProgressDialog
            login(userName, passWord, this);

        } else {
            Toast.makeText(LoginActivity.this, "信息不完整", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(final String username, final String password, final Activity activity) {
        final String url = Contact.serviceUrl;
        RequestParams requestParams = new RequestParams(url);
        System.out.println("----" + Contact.serviceUrl);
        requestParams.addBodyParameter("className", "UserService");
        requestParams.addBodyParameter("methodName", "login");
        requestParams.addBodyParameter("params", "[\"" + username + "\",\"" + password + "\"]");
        x.http().post(requestParams, new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("------" + result);
                try {
                    JSONObject jsonLogin = new JSONObject(result);
                    String loginResult = jsonLogin.getString("result");
                    if (loginResult.equals("true")) {
                        User user = new User(username,password,url);
                        caoZhuoDB(username,url);
                        insertDB(user);
                        Intent intent = new Intent(loginActivity, WorkeActivity.class);
                        startActivity(intent);
                        MMDApplication.user = new User(username, password);
                        DbCookieStore instance = DbCookieStore.INSTANCE;
                        List<HttpCookie> cookies = instance.getCookies();
                        System.out.println("----cookie:" + cookies.size());
                        for (int i = 0; i < cookies.size(); i++) {
                            String domain = cookies.get(i).getDomain();
                            System.out.println("----domain:" + domain);
                        }


                    } else if ("false".equals(loginResult)) {
                        Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "服务器配置出错,", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dialog.dismiss();
                Toast.makeText(activity, "网络连接错误", Toast.LENGTH_SHORT).show();
                System.out.println("----登录出错了");
                super.onError(ex, isOnCallback);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void insertDB(User user) {
        DbManager db = x.getDb(HDbManager.getUserDB());
        try {
            db.save(user);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void caoZhuoDB(String name, String serviceUrl) {
        DbManager db = x.getDb(HDbManager.getUserDB());
        try {
            List<User> list_db = db.selector(User.class).findAll();
            if (list_db.size() > 3){
                db.delete(list_db.get(0));
            }
            for (int i = 0; i < list_db.size(); i++) {
                if (name.equals(list_db.get(i).name)
                        && serviceUrl.equals(list_db.get(i).inServiceUrl)){
                    db.delete(list_db.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
