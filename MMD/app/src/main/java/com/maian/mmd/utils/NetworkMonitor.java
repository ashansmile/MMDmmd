package com.maian.mmd.utils;

/**
 * Created by ashan on 2016/10/17.
 */

public class NetworkMonitor {
    private boolean networkAvailable;

    private NetworkMonitor(){}

    private static NetworkMonitor instance = new NetworkMonitor();

    public static void setNetworkStatus(boolean networkStatus){
        instance.networkAvailable = networkStatus;
    }

    public static boolean isNetworkAvailable(){
        return instance.networkAvailable;
    }
}
