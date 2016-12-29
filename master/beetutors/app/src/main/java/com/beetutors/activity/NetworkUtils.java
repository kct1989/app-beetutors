package com.beetutors.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by TRUYENLT on 29/12/2016.
 */

public class NetworkUtils {
    Context context;

    public NetworkUtils(@NonNull Context context) {
        this.context = context;
    }

    public static boolean isWifiConnected(@NonNull Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * check api <5.0 and >5.0
     */

    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo != null && networkInfo.isConnected();
        } else {
            Network[] networks = connMgr.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connMgr.getNetworkInfo(mNetwork);
                if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    return true;
                }
            }
            return false;
        }
    }
}
