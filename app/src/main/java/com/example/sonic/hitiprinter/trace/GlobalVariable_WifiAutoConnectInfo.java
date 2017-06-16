package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_WifiAutoConnectInfo extends BaseGlobalVariable {
    private String m_InfraPassword;
    private String m_InfraSSID;
    private String m_Password;
    private String m_SSID;

    public GlobalVariable_WifiAutoConnectInfo(Context context) {
        super(context);
        this.m_SSID = XmlPullParser.NO_NAMESPACE;
        this.m_Password = XmlPullParser.NO_NAMESPACE;
        this.m_InfraSSID = XmlPullParser.NO_NAMESPACE;
        this.m_InfraPassword = XmlPullParser.NO_NAMESPACE;
        this.m_fsp = context.getSharedPreferences("pref_fgv_wifi_auto_connect_info", 0);
        this.LOG.m385i("GlobalVariable_WifiAutoConnectInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_SSID = this.m_fsp.getString("GV_M_SSID", XmlPullParser.NO_NAMESPACE);
            this.m_InfraSSID = this.m_fsp.getString("GV_M_INFRA_SSID", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SSID", this.m_SSID);
            this.m_Password = this.m_fsp.getString("GV_M_PASSWORD", XmlPullParser.NO_NAMESPACE);
            this.m_InfraPassword = this.m_fsp.getString("GV_M_INFRA_PASSWORD", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_Password", this.m_Password);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_WifiAutoConnectInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_WifiAutoConnectInfo", "RestoreGlobalVariable Fail");
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
                spe.putString("GV_M_SSID", this.m_SSID);
                spe.putString("GV_M_INFRA_SSID", this.m_InfraSSID);
                spe.putString("GV_M_PASSWORD", this.m_Password);
                spe.putString("GV_M_INFRA_PASSWORD", this.m_InfraPassword);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_WifiAutoConnectInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_WifiAutoConnectInfo", "SaveGlobalVariable");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_WifiAutoConnectInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetSSID() {
        return this.m_SSID;
    }

    public String GetPassword() {
        return this.m_Password;
    }

    public String GetInfraSSID() {
        return this.m_InfraSSID;
    }

    public String GetInfraPassword() {
        return this.m_InfraPassword;
    }

    public void SetSSID(String SSID) {
        this.m_SSID = SSID;
        SetEdit(true);
    }

    public void SetPassword(String Password) {
        this.m_Password = Password;
        SetEdit(true);
    }

    public void SetInfraSSID(String infraSSID) {
        this.m_InfraSSID = infraSSID;
        SetEdit(true);
    }

    public void SetInfraPassword(String infraPassword) {
        this.m_InfraPassword = infraPassword;
        SetEdit(true);
    }
}
