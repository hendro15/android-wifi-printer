package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_OfflineADDownloadInfo extends BaseGlobalVariable {
    private String m_OADDI_Country;
    private String m_OADDI_Info_ID;
    private int m_OADDI_Number;
    private int m_OADDI_Type;
    private int m_OADDI_Version;

    public GlobalVariable_OfflineADDownloadInfo(Context context) {
        super(context);
        this.m_OADDI_Info_ID = XmlPullParser.NO_NAMESPACE;
        this.m_OADDI_Version = -1;
        this.m_OADDI_Type = -1;
        this.m_OADDI_Number = -1;
        this.m_OADDI_Country = XmlPullParser.NO_NAMESPACE;
        this.m_fsp = context.getSharedPreferences("pref_fgv_offline_ad_download_info", 0);
        this.LOG.m385i("GlobalVariable_OfflineADDownloadInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_OADDI_Info_ID = this.m_fsp.getString("GV_M_OADDI_INFO_ID", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_OADDI_Info_ID", this.m_OADDI_Info_ID);
            this.m_OADDI_Version = this.m_fsp.getInt("GV_M_OADDI_VERIOSN", -1);
            this.LOG.m385i("m_OADDI_Version", String.valueOf(this.m_OADDI_Version));
            this.m_OADDI_Type = this.m_fsp.getInt("GV_M_OADDI_TYPE", -1);
            this.LOG.m385i("m_OADDI_Type", String.valueOf(this.m_OADDI_Type));
            this.m_OADDI_Number = this.m_fsp.getInt("GV_M_OADDI_NUMBER", -1);
            this.LOG.m385i("m_OADDI_Number", String.valueOf(this.m_OADDI_Number));
            this.m_OADDI_Country = this.m_fsp.getString("GV_M_OADDI_COUNTRY", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_OADDI_Country", this.m_OADDI_Country);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_OfflineADDownloadInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_OfflineADDownloadInfo", "RestoreGlobalVariable Fail");
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
                spe.putString("GV_M_OADDI_INFO_ID", this.m_OADDI_Info_ID);
                spe.putInt("GV_M_OADDI_VERIOSN", this.m_OADDI_Version);
                spe.putInt("GV_M_OADDI_TYPE", this.m_OADDI_Type);
                spe.putInt("GV_M_OADDI_NUMBER", this.m_OADDI_Number);
                spe.putString("GV_M_OADDI_COUNTRY", this.m_OADDI_Country);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_OfflineADDownloadInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_OfflineADDownloadInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_OfflineADDownloadInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetOADDI_Info_ID() {
        return this.m_OADDI_Info_ID;
    }

    public void SetOADDI_Info_ID(String OADDI_Info_ID) {
        this.m_OADDI_Info_ID = OADDI_Info_ID;
        SetEdit(true);
    }

    public int GetOADDI_Version() {
        return this.m_OADDI_Version;
    }

    public void SetOADDI_Version(int OADDI_Version) {
        this.m_OADDI_Version = OADDI_Version;
        SetEdit(true);
    }

    public int GetOADDI_Type() {
        return this.m_OADDI_Type;
    }

    public void SetOADDI_Type(int OADDI_Type) {
        this.m_OADDI_Type = OADDI_Type;
        SetEdit(true);
    }

    public int GetOADDI_Number() {
        return this.m_OADDI_Number;
    }

    public void SetOADDI_Number(int OADDI_Number) {
        this.m_OADDI_Number = OADDI_Number;
        SetEdit(true);
    }

    public String GetOADDI_Country() {
        return this.m_OADDI_Country;
    }

    public void SetOADDI_Country(String OADDI_Country) {
        this.m_OADDI_Country = OADDI_Country;
        SetEdit(true);
    }
}
