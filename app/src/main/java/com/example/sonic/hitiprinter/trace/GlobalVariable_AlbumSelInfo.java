package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_AlbumSelInfo extends BaseGlobalVariable_Prinbiz {
    int m_iAlbumId;
    int m_iAlbumRoute;
    int m_iAlbumStoreageId;
    String m_strAlbumName;

    public GlobalVariable_AlbumSelInfo(Context context, boolean bBackIDCollage) {
        super(context);
        this.m_iAlbumId = -1;
        this.m_iAlbumStoreageId = -1;
        this.m_strAlbumName = null;
        this.m_iAlbumRoute = 0;
        String prefName = "pref_gv_sel_album_info";
        if (bBackIDCollage) {
            prefName = "pref_gv_sel_album_info_path_id";
        }
        this.m_sp = context.getSharedPreferences(prefName, 0);
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_iAlbumRoute = this.m_sp.getInt("GV_M_ALBUM_ROUTE", 0);
            if (this.m_iAlbumRoute == 1) {
                this.m_iAlbumId = this.m_sp.getInt("GV_M_SEL_ALBUM_ID", 0);
                this.m_strAlbumName = this.m_sp.getString("GV_M_SEL_ALBUM_NAME", XmlPullParser.NO_NAMESPACE);
            } else {
                this.m_iAlbumId = this.m_sp.getInt("GV_M_SD_SEL_ALBUM_ID", 0);
                this.m_iAlbumStoreageId = this.m_sp.getInt("GV_M_SEL_ALBUM_STORAGE_ID", 0);
                this.m_strAlbumName = this.m_sp.getString("GV_M_SEL_ALBUM_NAME", XmlPullParser.NO_NAMESPACE);
            }
            SetEdit(false);
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_PrinterInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_sp.edit();
                if (this.m_iAlbumRoute != 0) {
                    spe.putInt("GV_M_ALBUM_ROUTE", this.m_iAlbumRoute);
                    this.m_iAlbumRoute = 0;
                    spe.commit();
                    SetEdit(false);
                    return;
                }
                this.m_iAlbumRoute = this.m_sp.getInt("GV_M_ALBUM_ROUTE", 0);
                if (this.m_iAlbumRoute == 1) {
                    spe.putInt("GV_M_SEL_ALBUM_ID", this.m_iAlbumId);
                    spe.putString("GV_M_SEL_ALBUM_NAME", this.m_strAlbumName);
                } else {
                    spe.putInt("GV_M_SD_SEL_ALBUM_ID", this.m_iAlbumId);
                    spe.putInt("GV_M_SEL_ALBUM_STORAGE_ID", this.m_iAlbumStoreageId);
                    spe.putString("GV_M_SEL_ALBUM_NAME", this.m_strAlbumName);
                }
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_AlbumSelInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_AlbumSelInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_AlbumSelInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public int GetAlbumId() {
        return this.m_iAlbumId;
    }

    public void SetAlbumId(int iAlbumId) {
        this.m_iAlbumId = iAlbumId;
        SetEdit(true);
    }

    public int GetMobileAlbumId() {
        return this.m_iAlbumId;
    }

    public void SetMobileAlbumId(int iAlbumId) {
        this.m_iAlbumId = iAlbumId;
        SetEdit(true);
    }

    public int GetAlbumStorageId() {
        return this.m_iAlbumStoreageId;
    }

    public void SetAlbumStorageId(int iAlbumStoreageId) {
        this.m_iAlbumStoreageId = iAlbumStoreageId;
        SetEdit(true);
    }

    public String GetAlbumName() {
        return this.m_strAlbumName;
    }

    public void SetAlbumName(String iAlbumName) {
        this.m_strAlbumName = iAlbumName;
        SetEdit(true);
    }

    public int GetAlbumRoute() {
        return this.m_iAlbumRoute;
    }

    public void SetAlbumRoute(int iAlbumRoute) {
        this.LOG.m384e("SetAlbumRoute!!!", String.valueOf(iAlbumRoute));
        this.m_iAlbumRoute = iAlbumRoute;
        SetEdit(true);
    }

    public static void SaveAlbumInfo(GlobalVariable_AlbumSelInfo prefAlbumInfo, String strAlbumName, int iAlbumID, int iSourceRoute) {
        prefAlbumInfo.ClearGlobalVariable();
        prefAlbumInfo.SetAlbumRoute(iSourceRoute);
        prefAlbumInfo.SaveGlobalVariable();
        prefAlbumInfo.SetMobileAlbumId(iAlbumID);
        prefAlbumInfo.SetAlbumName(strAlbumName);
        prefAlbumInfo.SaveGlobalVariable();
    }
}
