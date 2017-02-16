package com.maian.mmd.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.maian.mmd.activity.HomeActivity;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.NetRequestParamsUtil;
import com.maian.mmd.utils.PermissionUtil;
import com.maian.mmd.utils.xutilsCallBack;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseActivity extends AppCompatActivity {
    private BaseActivity mActivity;
    private long mExitTime;

//    private static final int REQUEST_CODE = 0; // 请求码
//    private PermissionUtil mPermissionsChecker; // 权限检测器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册权限
       // mPermissionsChecker = new PermissionUtil(this);
        //统计应用启动数据
        //PushAgent.getInstance(this).onAppStart();
        mActivity = this;
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mActivity instanceof HomeActivity) {

            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                exit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            for (int i = 0; i < MMDApplication.activityManagers.size(); i++) {
                MMDApplication.activityManagers.get(i).finish();
            }
            System.exit(0);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        MMDApplication.activityManagers.add(mActivity);
    }


    public void handleOnLine(String username, String password) {
        final String url = Contact.serviceUrl;
        x.http().post(NetRequestParamsUtil.getLoginParms(url,username,password), new xutilsCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonLogin = new JSONObject(result);
                    String loginResult = jsonLogin.getString("result");
                    if (loginResult.equals("true")) {
                        handleThing();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    //用于处理登录获取数据
    public void handleThing() {
    }


}
