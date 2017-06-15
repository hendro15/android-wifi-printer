package com.example.sonic.hitiprinter.utility;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.trace.GlobalVariable_AppInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;

public class MobileInfo {
    public static String MakeRandString(int iLength) {
        String strRet = XmlPullParser.NO_NAMESPACE;
        for (int i = 0; i < iLength; i++) {
            strRet = strRet + String.valueOf((int) (Math.random() * 10.0d));
        }
        return strRet;
    }

    public static String MakeRandStringNoZeroPrefix(int iLength) {
        String strRet = XmlPullParser.NO_NAMESPACE;
        for (int i = 0; i < iLength; i++) {
            strRet = strRet + String.valueOf((int) (Math.random() * 10.0d));
        }
        String strFirst = strRet.substring(0, 1);
        if (strFirst.equals(OADCItem.WATCH_TYPE_NON)) {
            strFirst = OADCItem.WATCH_TYPE_WATCH;
        }
        return strFirst + strRet.substring(1, iLength);
    }

    public static String GetTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String GetDateStamp() {
        return new SimpleDateFormat("yyyyMMdd", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String GetTimeReocrd() {
        return new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String GetHmsSStamp() {
        return new SimpleDateFormat("HHmmssSSS", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String GetIMEI(Context context) {
        String strIMEI = null;
        if (null == null) {
            strIMEI = Build.SERIAL;
        }
        if (strIMEI == null) {
            return "9999900000";
        }
        return strIMEI;
    }

    public static Location GetLocation(Context context, boolean boDisable) {
        LocationManager locationManager = null;
        String strBestProvider = null;
        Location location = null;
        if (null == null) {
            locationManager = (LocationManager) context.getSystemService("location");
        }
        if (locationManager != null && locationManager.isProviderEnabled("network")) {
            if (null == null) {
                strBestProvider = locationManager.getBestProvider(new Criteria(), true);
            }
            if (!(strBestProvider == null || ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                location = locationManager.getLastKnownLocation(strBestProvider);
            }
        }
        if (boDisable) {
            return null;
        }
        return location;
    }

    public static String GetMapAppServerLanguage() {
        String strLanguage = "en";
        if (Locale.getDefault().getLanguage().contains("zh")) {
            if (Locale.getDefault().getCountry().contains("CN")) {
                return "cn";
            }
            if (Locale.getDefault().getCountry().contains("TW")) {
                return "tw";
            }
            if (Locale.getDefault().getCountry().contains("HK")) {
                return "tw";
            }
            return strLanguage;
        } else if (Locale.getDefault().getLanguage().contains("en")) {
            return "en";
        } else {
            if (Locale.getDefault().getLanguage().contains("ja")) {
                return "ja";
            }
            if (Locale.getDefault().getLanguage().contains("ko")) {
                return "ko";
            }
            if (Locale.getDefault().getLanguage().contains("fr")) {
                return "fr";
            }
            if (Locale.getDefault().getLanguage().contains("es")) {
                return "es";
            }
            if (Locale.getDefault().getLanguage().contains("ru")) {
                return "ru";
            }
            if (Locale.getDefault().getLanguage().contains("pt")) {
                return "pt";
            }
            if (Locale.getDefault().getLanguage().contains("de")) {
                return "de";
            }
            if (Locale.getDefault().getLanguage().contains("it")) {
                return "it";
            }
            if (Locale.getDefault().getLanguage().contains("ar")) {
                return "ar";
            }
            return strLanguage;
        }
    }

    public static String GetMapAppServerCountry() {
        String strCountry = "en";
        if (Locale.getDefault().getCountry().contains("AU")) {
            return "en-au";
        }
        if (Locale.getDefault().getCountry().contains("CN")) {
            return "cn";
        }
        if (Locale.getDefault().getCountry().contains("TW")) {
            return "tw";
        }
        if (Locale.getDefault().getCountry().contains("HK")) {
            return "hk";
        }
        if (Locale.getDefault().getCountry().contains("JP")) {
            return "jp";
        }
        if (Locale.getDefault().getCountry().contains("RU")) {
            return "ru";
        }
        if (Locale.getDefault().getCountry().contains("KR")) {
            return "kr";
        }
        if (Locale.getDefault().getCountry().contains("US")) {
            return "en-us";
        }
        return strCountry;
    }

    public static void SetAppCountryCode(Context context, String strCountryCode) {
        GlobalVariable_AppInfo mGlobalVariable_AppInfo = new GlobalVariable_AppInfo(context);
        mGlobalVariable_AppInfo.RestoreGlobalVariable();
        mGlobalVariable_AppInfo.SetAppCountryCode(strCountryCode);
        mGlobalVariable_AppInfo.SaveGlobalVariable();
    }

    public static String GetAppCountryCode(Context context) {
        GlobalVariable_AppInfo mGlobalVariable_AppInfo = new GlobalVariable_AppInfo(context);
        mGlobalVariable_AppInfo.RestoreGlobalVariable();
        return mGlobalVariable_AppInfo.GetAppCountryCode();
    }
}
