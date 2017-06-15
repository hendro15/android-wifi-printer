package com.example.sonic.hitiprinter.utility.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import org.xmlpull.v1.XmlPullParser;

public class WifiSetting {
    public static String GetSSID(Context context) {
        WifiInfo wInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        if (wInfo.getSSID() == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return wInfo.getSSID();
    }

    public static boolean IsWifiConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == 1) {
            return networkInfo.isConnected();
        }
        return false;
    }

    public static void ShowWifiPage(Activity activity) {
        activity.startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }
}
