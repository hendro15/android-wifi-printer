package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_EditMeta extends BaseGlobalVariable_Prinbiz {
    boolean m_bIsNoData;
    ArrayList<Integer> m_iEditSelPosList;
    int m_iType;
    ArrayList<String> m_strFetchImgList;
    ArrayList<String> m_strSaveEditImgList;

    public GlobalVariable_EditMeta(Context context, int type) {
        super(context);
        this.m_strFetchImgList = null;
        this.m_iEditSelPosList = null;
        this.m_strSaveEditImgList = null;
        this.m_bIsNoData = false;
        this.m_iType = -1;
        this.m_sp = context.getSharedPreferences("pref_gv_multi_sel_edit_meta", 0);
        this.m_iType = type;
        this.m_strFetchImgList = new ArrayList();
        this.m_iEditSelPosList = new ArrayList();
        this.m_strSaveEditImgList = new ArrayList();
    }

    public void RestoreGlobalVariable() {
        try {
            int i;
            this.m_strFetchImgList.clear();
            this.m_iEditSelPosList.clear();
            this.m_strSaveEditImgList.clear();
            int iPosLen = this.m_sp.getInt("GV_M_INDEX_LEN", 0);
            if (iPosLen != 0) {
                for (i = 0; i < iPosLen; i++) {
                    this.m_iEditSelPosList.add(Integer.valueOf(this.m_sp.getInt("GV_M_EDIT_INDEX" + i, 0)));
                }
            }
            int fetchLen = this.m_sp.getInt("GV_M_FETCH_LEN", 0);
            if (fetchLen != 0) {
                for (i = 0; i < fetchLen; i++) {
                    this.m_strFetchImgList.add(this.m_sp.getString("GV_M_FETCH_PATH" + i, XmlPullParser.NO_NAMESPACE));
                }
            }
            int imgLen = this.m_sp.getInt("GV_M_SAVE_IMG_LEN", 0);
            if (imgLen != 0) {
                for (i = 0; i < imgLen; i++) {
                    this.m_strSaveEditImgList.add(this.m_sp.getString("GV_M_SAVE_IMG_PATH" + i, XmlPullParser.NO_NAMESPACE));
                }
            }
            SetEdit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                int i;
                Editor spe = this.m_sp.edit();
                if (this.m_strFetchImgList != null) {
                    int iLen = this.m_strFetchImgList.size();
                    if (iLen != 0) {
                        for (i = 0; i < iLen; i++) {
                            spe.putString("GV_M_FETCH_PATH" + i, (String) this.m_strFetchImgList.get(i));
                        }
                        spe.putInt("GV_M_FETCH_LEN", iLen);
                    }
                }
                if (this.m_iEditSelPosList != null) {
                    for (i = 0; i < this.m_iEditSelPosList.size(); i++) {
                        spe.putInt("GV_M_EDIT_INDEX" + i, ((Integer) this.m_iEditSelPosList.get(i)).intValue());
                    }
                    spe.putInt("GV_M_INDEX_LEN", this.m_iEditSelPosList.size());
                }
                if (this.m_strSaveEditImgList != null) {
                    for (i = 0; i < this.m_strSaveEditImgList.size(); i++) {
                        spe.putString("GV_M_SAVE_IMG_PATH" + i, (String) this.m_strSaveEditImgList.get(i));
                    }
                    spe.putInt("GV_M_SAVE_IMG_LEN", this.m_strSaveEditImgList.size());
                }
                spe.commit();
                SetEdit(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<String> GetFetchPathList() {
        return this.m_strFetchImgList;
    }

    public ArrayList<Integer> GetEditSelPosList() {
        return this.m_iEditSelPosList;
    }

    public ArrayList<String> GetSaveEditPathList() {
        return this.m_strSaveEditImgList;
    }

    public void SetEditSelPos(ArrayList<Integer> iSelPosList) {
        if (iSelPosList != null) {
            this.m_iEditSelPosList.clear();
            Iterator it = iSelPosList.iterator();
            while (it.hasNext()) {
                this.m_iEditSelPosList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        SetEdit(true);
    }

    public void SetFetchImgPath(ArrayList<String> strFetchImagePathList) {
        if (strFetchImagePathList != null) {
            this.m_strFetchImgList.clear();
            Iterator it = strFetchImagePathList.iterator();
            while (it.hasNext()) {
                this.m_strFetchImgList.add((String) it.next());
            }
        }
        SetEdit(true);
    }

    public void SetSaveEditImgPath(ArrayList<String> strSaveImagePathList) {
        if (strSaveImagePathList != null) {
            this.m_strSaveEditImgList.clear();
            Iterator it = strSaveImagePathList.iterator();
            while (it.hasNext()) {
                this.m_strSaveEditImgList.add((String) it.next());
            }
        }
        SetEdit(true);
    }
}
