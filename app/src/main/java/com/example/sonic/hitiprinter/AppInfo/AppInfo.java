package com.example.sonic.hitiprinter.AppInfo;

import android.content.Context;
import com.example.sonic.hitiprinter.trace.GlobalVariable_AppInfo;

public class AppInfo {
    private APP_MODE m_APPMode;

    public enum APP_MODE {
        LIKODA,
        PRINGO,
        PRINSNAP,
        PRINBIZ,
        PRINHOME,
        FOR_WECHAT
    }

    public AppInfo(Context context) {
        this.m_APPMode = APP_MODE.PRINGO;
        this.m_APPMode = APP_MODE.PRINGO;
    }

    public void SetAppMode(APP_MODE appMode) {
        this.m_APPMode = appMode;
    }

    public APP_MODE GetAppMode() {
        return this.m_APPMode;
    }

    public int GetAppModeNumber() {
        if (this.m_APPMode == APP_MODE.LIKODA) {
            return 1;
        }
        if (this.m_APPMode == APP_MODE.PRINSNAP) {
            return 3;
        }
        if (this.m_APPMode == APP_MODE.PRINBIZ) {
            return 4;
        }
        if (this.m_APPMode == APP_MODE.PRINHOME) {
            return 5;
        }
        if (this.m_APPMode == APP_MODE.FOR_WECHAT) {
            return 6;
        }
        return 2;
    }

    public static int GetAppModeNumber(APP_MODE appMode) {
        if (appMode == APP_MODE.LIKODA) {
            return 1;
        }
        if (appMode == APP_MODE.PRINSNAP) {
            return 3;
        }
        if (appMode == APP_MODE.PRINBIZ) {
            return 4;
        }
        if (appMode == APP_MODE.PRINHOME) {
            return 5;
        }
        if (appMode == APP_MODE.FOR_WECHAT) {
            return 6;
        }
        return 2;
    }

    public static APP_MODE GetAppModeFromNumber(int iAppMode) {
        if (iAppMode == 1) {
            return APP_MODE.LIKODA;
        }
        if (iAppMode == 3) {
            return APP_MODE.PRINSNAP;
        }
        if (iAppMode == 4) {
            return APP_MODE.PRINBIZ;
        }
        if (iAppMode == 5) {
            return APP_MODE.PRINHOME;
        }
        if (iAppMode == 6) {
            return APP_MODE.FOR_WECHAT;
        }
        return APP_MODE.PRINGO;
    }

    public static String GetAPPVersion(Context context) {
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(context);
        GVAppInfo.RestoreGlobalVariable();
        return GVAppInfo.GetAppVersion();
    }

    public static void SetAPPVersion(Context context, String strAppVersion) {
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(context);
        GVAppInfo.RestoreGlobalVariable();
        GVAppInfo.SetAppVersion(strAppVersion);
        GVAppInfo.SaveGlobalVariableForever();
    }
}
