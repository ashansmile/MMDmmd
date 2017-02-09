package com.maian.mmd.utils;

import org.xutils.common.Callback;

/**
 * Created by ashan on 2016/10/12.
 */

public class xutilsCallBack <String>implements Callback.CommonCallback<String> {
    @Override
    public void onSuccess(String result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

}
