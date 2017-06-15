package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_AppInfo extends BaseGlobalVariable {
    private int m_AppMode;
    private String m_AppVersion;
    private String m_CoutryCode;

    public GlobalVariable_AppInfo(Context context) {
        super(context);
        this.m_AppVersion = XmlPullParser.NO_NAMESPACE;
        this.m_AppMode = 2;
        this.m_CoutryCode = XmlPullParser.NO_NAMESPACE;
        this.m_fsp = context.getSharedPreferences("pref_fgv_app_info", 0);
        this.LOG.m385i("GlobalVariable_AppInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_AppVersion = this.m_fsp.getString("GV_M_APP_VERSION", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_AppVersion", this.m_AppVersion);
            this.m_AppMode = this.m_fsp.getInt("GV_M_APP_MODE", 2);
            this.LOG.m385i("m_AppMode", String.valueOf(this.m_AppMode));
            this.m_CoutryCode = this.m_fsp.getString("GV_M_APP_COUTRYCODE", XmlPullParser.NO_NAMESPACE);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_AppInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_AppInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_fsp.edit();
                spe.putString("GV_M_APP_VERSION", this.m_AppVersion);
                spe.putInt("GV_M_APP_MODE", this.m_AppMode);
                spe.putString("GV_M_APP_COUTRYCODE", this.m_CoutryCode);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_AppInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_AppInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_AppInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetAppVersion() {
        return this.m_AppVersion;
    }

    public void SetAppVersion(String appVersion) {
        this.m_AppVersion = appVersion;
        SetEdit(true);
    }

    public int GetAppMode() {
        return this.m_AppMode;
    }

    public void SetAppMode(int iAppMode) {
        this.m_AppMode = iAppMode;
        SetEdit(true);
    }

    public String GetAppCountryCode() {
        return this.m_CoutryCode;
    }

    public void SetAppCountryCode(String appCountryCode) {
        this.m_CoutryCode = appCountryCode;
        SetEdit(true);
    }
}
