package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_SDFWInfo extends BaseGlobalVariable {
    private String m_SD_FW_Version_P231;
    private String m_SD_FW_Version_P232;
    private String m_SD_FW_Version_P310W;
    private String m_SD_FW_Version_P461;
    private String m_SD_FW_Version_P520L;
    private String m_SD_FW_Version_P530D;
    private String m_SD_FW_Version_P750L;

    public GlobalVariable_SDFWInfo(Context context) {
        super(context);
        this.m_SD_FW_Version_P231 = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P232 = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P520L = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P750L = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P310W = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P461 = XmlPullParser.NO_NAMESPACE;
        this.m_SD_FW_Version_P530D = XmlPullParser.NO_NAMESPACE;
        this.m_fsp = context.getSharedPreferences("pref_fgv_sd_fw_info", 0);
        this.LOG.m385i("GlobalVariable_SDFWInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_SD_FW_Version_P231 = this.m_fsp.getString("GV_M_SD_FW_VERSION_P231", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P231", this.m_SD_FW_Version_P231);
            this.m_SD_FW_Version_P232 = this.m_fsp.getString("GV_M_SD_FW_VERSION_P232", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P232", this.m_SD_FW_Version_P232);
            this.m_SD_FW_Version_P520L = this.m_fsp.getString("GV_M_SD_FW_VERSION_P520L", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P520L", this.m_SD_FW_Version_P520L);
            this.m_SD_FW_Version_P750L = this.m_fsp.getString("GV_M_SD_FW_VERSION_P750L", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P750L", this.m_SD_FW_Version_P750L);
            this.m_SD_FW_Version_P310W = this.m_fsp.getString("GV_M_SD_FW_VERSION_P310W", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P310W", this.m_SD_FW_Version_P310W);
            this.m_SD_FW_Version_P461 = this.m_fsp.getString("GV_M_SD_FW_VERSION_P461", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P461", this.m_SD_FW_Version_P461);
            this.m_SD_FW_Version_P530D = this.m_fsp.getString("GV_M_SD_FW_VERSION_P530D", XmlPullParser.NO_NAMESPACE);
            this.LOG.m385i("m_SD_FW_Version_P530D", this.m_SD_FW_Version_P530D);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_SDFWInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_SDFWInfo", "RestoreGlobalVariable Fail");
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
                spe.putString("GV_M_SD_FW_VERSION_P231", this.m_SD_FW_Version_P231);
                spe.putString("GV_M_SD_FW_VERSION_P232", this.m_SD_FW_Version_P232);
                spe.putString("GV_M_SD_FW_VERSION_P520L", this.m_SD_FW_Version_P520L);
                spe.putString("GV_M_SD_FW_VERSION_P750L", this.m_SD_FW_Version_P750L);
                spe.putString("GV_M_SD_FW_VERSION_P310W", this.m_SD_FW_Version_P310W);
                spe.putString("GV_M_SD_FW_VERSION_P461", this.m_SD_FW_Version_P461);
                spe.putString("GV_M_SD_FW_VERSION_P530D", this.m_SD_FW_Version_P530D);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_SDFWInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_SDFWInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_SDFWInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetSDFWVersion(int ElementID) {
        if (5 == ElementID) {
            return this.m_SD_FW_Version_P232;
        }
        if (7 == ElementID) {
            return this.m_SD_FW_Version_P520L;
        }
        if (8 == ElementID) {
            return this.m_SD_FW_Version_P750L;
        }
        if (9 == ElementID) {
            return this.m_SD_FW_Version_P310W;
        }
        if (10 == ElementID) {
            return this.m_SD_FW_Version_P461;
        }
        return this.m_SD_FW_Version_P231;
    }

    public void SetSDFWVersion(int ElementID, String strSDFWVersion) {
        if (4 == ElementID) {
            this.m_SD_FW_Version_P231 = strSDFWVersion;
        }
        if (5 == ElementID) {
            this.m_SD_FW_Version_P232 = strSDFWVersion;
        }
        if (7 == ElementID) {
            this.m_SD_FW_Version_P520L = strSDFWVersion;
        }
        if (8 == ElementID) {
            this.m_SD_FW_Version_P750L = strSDFWVersion;
        }
        if (9 == ElementID) {
            this.m_SD_FW_Version_P310W = strSDFWVersion;
        }
        if (10 == ElementID) {
            this.m_SD_FW_Version_P461 = strSDFWVersion;
        }
        SetEdit(true);
    }
}
