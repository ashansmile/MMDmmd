package com.maian.mmd.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.maian.mmd.activity.WorkeActivity;
import com.maian.mmd.utils.HDbManager;

import org.xutils.DbManager;
import org.xutils.x;

public class BaseActivity extends AppCompatActivity {
    BaseActivity mActivity;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mActivity instanceof WorkeActivity) {

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
}
