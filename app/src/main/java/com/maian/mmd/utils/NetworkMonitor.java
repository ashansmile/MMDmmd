package com.maian.mmd.utils;

import android.content.Context;

/**
 * Created by ashan on 2016/10/17.
 */

public class NetworkMonitor {
    private boolean networkAvailable;

    private NetworkMonitor() {
    }

    private static NetworkMonitor instance = new NetworkMonitor();

    public static void setNetworkStatus(boolean networkStatus) {
        NetworkUtil.netchangeCount++;
        instance.networkAvailable = networkStatus;
    }

    public static boolean isNetworkAvailable(Context context) {
        if (NetworkUtil.netchangeCount == 0) {
            return NetworkUtil.isConnected(context);
        }
        return instance.networkAvailable;
    }
}
