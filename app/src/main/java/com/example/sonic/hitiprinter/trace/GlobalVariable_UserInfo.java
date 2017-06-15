package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_UserInfo extends BaseGlobalVariable {
    private String m_CloudAlbumSortType;
    private int m_ShowEditHint;
    private int m_ShowSnowGlobeHint;
    private int m_Verify;
    private int m_VerifyPrintCount;

    public GlobalVariable_UserInfo(Context context) {
        super(context);
        this.m_Verify = 0;
        this.m_VerifyPrintCount = 0;
        this.m_ShowEditHint = 0;
        this.m_ShowSnowGlobeHint = 0;
        this.m_CloudAlbumSortType = XmlPullParser.NO_NAMESPACE;
        this.m_fsp = context.getSharedPreferences("pref_fgv_user_info", 0);
        this.LOG.m385i("GlobalVariable_UserInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_Verify = this.m_fsp.getInt("GV_M_VERIFY", 0);
            this.LOG.m385i("m_Verify", String.valueOf(this.m_Verify));
            this.m_VerifyPrintCount = this.m_fsp.getInt("GV_M_VERIFY_PRINT_COUNT", 0);
            this.LOG.m385i("m_VerifyPrintCount", String.valueOf(this.m_VerifyPrintCount));
            this.m_ShowEditHint = this.m_fsp.getInt("GV_M_SHOW_EDIT_HINT", 0);
            this.LOG.m385i("m_ShowEditHint", String.valueOf(this.m_ShowEditHint));
            this.m_ShowSnowGlobeHint = this.m_fsp.getInt("GV_M_SHOW_SNOW_GLOBE_HINT", 0);
            this.LOG.m385i("m_ShowSnowGlobeHint", String.valueOf(this.m_ShowSnowGlobeHint));
            this.m_CloudAlbumSortType = this.m_fsp.getString("GV_M_CLOUD_ALBUM_SORT_TYPE", XmlPullParser.NO_NAMESPACE);
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_UserInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_UserInfo", "RestoreGlobalVariable Fail");
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
                spe.putInt("GV_M_VERIFY", this.m_Verify);
                spe.putInt("GV_M_VERIFY_PRINT_COUNT", this.m_VerifyPrintCount);
                spe.putInt("GV_M_SHOW_EDIT_HINT", this.m_ShowEditHint);
                spe.putInt("GV_M_SHOW_SNOW_GLOBE_HINT", this.m_ShowSnowGlobeHint);
                spe.putString("GV_M_CLOUD_ALBUM_SORT_TYPE", this.m_CloudAlbumSortType);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_UserInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_UserInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_UserInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public int GetVerify() {
        return this.m_Verify;
    }

    public void SetVerify(int Verify) {
        this.m_Verify = Verify;
        SetEdit(true);
    }

    public int GetVerifyPrintCount() {
        return this.m_VerifyPrintCount;
    }

    public void SetVerifyPrintCount(int VerifyPrintCount) {
        this.m_VerifyPrintCount = VerifyPrintCount;
        SetEdit(true);
    }

    public int GetShowEditHint() {
        return this.m_ShowEditHint;
    }

    public void SetShowEditHint(int m_ShowEditHint) {
        this.m_ShowEditHint = m_ShowEditHint;
        SetEdit(true);
    }

    public int GetShowSnowGlobeHint() {
        return this.m_ShowSnowGlobeHint;
    }

    public void SetShowSnowGlobeHint(int showSnowGlobeHint) {
        this.m_ShowSnowGlobeHint = showSnowGlobeHint;
        SetEdit(true);
    }

    public String GetCloudAlbumSortType() {
        return this.m_CloudAlbumSortType;
    }

    public void SetCloudAlbumSortType(String mCloudAlbumSortType) {
        this.m_CloudAlbumSortType = mCloudAlbumSortType;
        SetEdit(true);
    }
}
