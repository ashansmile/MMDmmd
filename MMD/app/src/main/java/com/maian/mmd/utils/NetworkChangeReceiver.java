package com.maian.mmd.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ashan on 2016/10/17.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean avaiable = NetworkUtil.isConnected(context);

        NetworkMonitor.setNetworkStatus(avaiable);

    }
}

