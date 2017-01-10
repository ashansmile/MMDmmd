package com.maian.mmd.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.maian.mmd.activity.PermissionsActivity;
import com.maian.mmd.activity.WorkeActivity;
import com.maian.mmd.utils.PermissionUtil;

public class BaseActivity extends AppCompatActivity {
    private BaseActivity mActivity;
    private long mExitTime;

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private PermissionUtil mPermissionsChecker; // 权限检测器



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册权限
        mPermissionsChecker = new PermissionUtil(this);
        //统计应用启动数据
        //PushAgent.getInstance(this).onAppStart();
        mActivity = this;
    }
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mActivity instanceof WorkeActivity ) {

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

    //权限处理
    @Override protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

}
