package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_MultiSelContainer extends BaseGlobalVariable_Prinbiz {
    boolean m_bIsNoData;
    ArrayList<Integer> m_iMultiSelCopiesList;
    ArrayList<Integer> m_iMultiSelIdList;
    ArrayList<Integer> m_iMultiSelSIDList;
    int m_iType;
    ArrayList<Long> m_lMultiSelIdList;
    ArrayList<String> m_strMultiSelNameList;
    ArrayList<String> m_strMultiSelPathList;

    public GlobalVariable_MultiSelContainer(Context context, int type) {
        super(context);
        this.m_strMultiSelPathList = null;
        this.m_strMultiSelNameList = null;
        this.m_iMultiSelIdList = null;
        this.m_iMultiSelSIDList = null;
        this.m_iMultiSelCopiesList = null;
        this.m_lMultiSelIdList = null;
        this.m_bIsNoData = false;
        this.m_iType = -1;
        this.m_sp = context.getSharedPreferences("pref_gv_multi_sel_container", 0);
        this.m_iType = type;
        this.m_strMultiSelPathList = new ArrayList();
        if (type == 2) {
            this.m_iMultiSelIdList = new ArrayList();
            this.m_iMultiSelSIDList = new ArrayList();
            this.m_strMultiSelNameList = new ArrayList();
        } else {
            this.m_lMultiSelIdList = new ArrayList();
        }
        this.m_iMultiSelCopiesList = new ArrayList();
        this.LOG.m385i("GlobalVariable_MultiSelContainer", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            int iCopiesLen = this.m_sp.getInt("GV_M_COPIES_LEN", 0);
            if (iCopiesLen == 0) {
                this.m_bIsNoData = true;
                return;
            }
            int i;
            this.m_iMultiSelCopiesList.clear();
            for (i = 0; i < iCopiesLen; i++) {
                this.m_iMultiSelCopiesList.add(Integer.valueOf(this.m_sp.getInt("GV_M_PHOTO_COPIES" + i, 1)));
            }
            int pathLen;
            int idLen;
            String path;
            if (this.m_iType == 1) {
                pathLen = this.m_sp.getInt("GV_M_PATH_LEN", 0);
                idLen = this.m_sp.getInt("GV_M_ID_LEN", 0);
                this.m_strMultiSelPathList.clear();
                this.m_lMultiSelIdList.clear();
                if (pathLen == 0 && idLen == 0) {
                    this.m_bIsNoData = true;
                    return;
                }
                if (pathLen != 0) {
                    for (i = 0; i < pathLen; i++) {
                        path = this.m_sp.getString("GV_M_PHOTO_PATH" + i, XmlPullParser.NO_NAMESPACE);
                        this.m_strMultiSelPathList.add(path);
                        this.LOG.m385i("path" + i, path);
                    }
                }
                if (idLen != 0) {
                    for (i = 0; i < idLen; i++) {
                        long id = this.m_sp.getLong("GV_M_PHOTO_ID" + i, 0);
                        this.m_lMultiSelIdList.add(Long.valueOf(id));
                        this.LOG.m385i("restore ID" + i, String.valueOf(id));
                    }
                }
                SetEdit(false);
                this.m_bIsNoData = false;
                return;
            }
            pathLen = this.m_sp.getInt("GV_M_PATH_LEN", 0);
            idLen = this.m_sp.getInt("GV_M_ID_LEN", 0);
            int sidLen = this.m_sp.getInt("GV_M_SIZE_LEN", 0);
            int nameLen = this.m_sp.getInt("GV_M_NAME_LEN", 0);
            this.m_strMultiSelPathList.clear();
            this.m_iMultiSelIdList.clear();
            this.m_iMultiSelSIDList.clear();
            this.m_strMultiSelNameList.clear();
            if (pathLen == 0 && idLen == 0) {
                this.m_bIsNoData = true;
                return;
            }
            if (pathLen != 0) {
                for (i = 0; i < pathLen; i++) {
                    path = this.m_sp.getString("GV_M_PHOTO_PATH" + i, XmlPullParser.NO_NAMESPACE);
                    this.m_strMultiSelPathList.add(path);
                    this.LOG.m385i("path" + i, path);
                }
            }
            if (idLen != 0) {
                for (i = 0; i < idLen; i++) {
                    this.m_iMultiSelIdList.add(Integer.valueOf(this.m_sp.getInt("GV_M_PHOTO_ID" + i, 0)));
                }
            }
            if (sidLen != 0) {
                for (i = 0; i < sidLen; i++) {
                    this.m_iMultiSelSIDList.add(Integer.valueOf(this.m_sp.getInt("GV_M_PHOTO_SID" + i, 0)));
                }
            }
            if (nameLen != 0) {
                for (i = 0; i < nameLen; i++) {
                    path = this.m_sp.getString("GV_M_PHOTO_NAME" + i, XmlPullParser.NO_NAMESPACE);
                    this.m_strMultiSelNameList.add(path);
                    this.LOG.m385i("path" + i, path);
                }
            }
            this.m_bIsNoData = false;
            SetEdit(false);
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_WifiAutoConnectInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public boolean IsNoData() {
        if (this.m_bIsNoData) {
            return true;
        }
        return false;
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                int i;
                Editor spe = this.m_sp.edit();
                for (i = 0; i < this.m_iMultiSelCopiesList.size(); i++) {
                    spe.putInt("GV_M_PHOTO_COPIES" + i, ((Integer) this.m_iMultiSelCopiesList.get(i)).intValue());
                }
                spe.putInt("GV_M_COPIES_LEN", this.m_iMultiSelCopiesList.size());
                if (this.m_iType == 1) {
                    for (i = 0; i < this.m_strMultiSelPathList.size(); i++) {
                        spe.putString("GV_M_PHOTO_PATH" + i, (String) this.m_strMultiSelPathList.get(i));
                    }
                    for (i = 0; i < this.m_lMultiSelIdList.size(); i++) {
                        spe.putLong("GV_M_PHOTO_ID" + i, ((Long) this.m_lMultiSelIdList.get(i)).longValue());
                    }
                    spe.putInt("GV_M_PATH_LEN", this.m_strMultiSelPathList.size());
                    spe.putInt("GV_M_ID_LEN", this.m_lMultiSelIdList.size());
                } else {
                    for (i = 0; i < this.m_strMultiSelPathList.size(); i++) {
                        spe.putString("GV_M_PHOTO_PATH" + i, (String) this.m_strMultiSelPathList.get(i));
                    }
                    for (i = 0; i < this.m_iMultiSelIdList.size(); i++) {
                        spe.putInt("GV_M_PHOTO_ID" + i, ((Integer) this.m_iMultiSelIdList.get(i)).intValue());
                        Log.e("save_id", XmlPullParser.NO_NAMESPACE + this.m_iMultiSelIdList.get(i));
                    }
                    for (i = 0; i < this.m_iMultiSelSIDList.size(); i++) {
                        spe.putInt("GV_M_PHOTO_SID" + i, ((Integer) this.m_iMultiSelSIDList.get(i)).intValue());
                    }
                    for (i = 0; i < this.m_strMultiSelNameList.size(); i++) {
                        spe.putString("GV_M_PHOTO_NAME" + i, (String) this.m_strMultiSelNameList.get(i));
                    }
                    spe.putInt("GV_M_PATH_LEN", this.m_strMultiSelPathList.size());
                    spe.putInt("GV_M_ID_LEN", this.m_iMultiSelIdList.size());
                    spe.putInt("GV_M_SIZE_LEN", this.m_iMultiSelSIDList.size());
                    spe.putInt("GV_M_NAME_LEN", this.m_strMultiSelNameList.size());
                }
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_MultiSelContainer", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_MultiSelContainer", "SaveGlobalVariable");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_MultiSelContainer", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<Integer> GetSDcardIDList() {
        return this.m_iMultiSelIdList;
    }

    public ArrayList<Integer> GetSDcardSIDList() {
        return this.m_iMultiSelSIDList;
    }

    public ArrayList<String> GetThumbPathList() {
        return this.m_strMultiSelPathList;
    }

    public ArrayList<String> GetPhotoNameList() {
        return this.m_strMultiSelNameList;
    }

    public ArrayList<String> GetMobilePathList() {
        this.LOG.m384e("GetMobilePathList", "=" + this.m_strMultiSelPathList);
        return this.m_strMultiSelPathList;
    }

    public ArrayList<Long> GetMobileIDList() {
        return this.m_lMultiSelIdList;
    }

    public ArrayList<Integer> GetPhotoCopiesList() {
        return this.m_iMultiSelCopiesList;
    }

    public void SetPhotoIdAndStorageIdList(ArrayList<Integer> iMultiSelIdList, ArrayList<Integer> iMultiSelSIDList) {
        Iterator it;
        this.m_iMultiSelIdList.clear();
        this.m_iMultiSelSIDList.clear();
        if (iMultiSelIdList != null) {
            it = iMultiSelIdList.iterator();
            while (it.hasNext()) {
                this.m_iMultiSelIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (iMultiSelSIDList != null) {
            it = iMultiSelSIDList.iterator();
            while (it.hasNext()) {
                this.m_iMultiSelSIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        SetEdit(true);
    }

    public void SetThumbPathAndNameList(ArrayList<String> strMultiSelPathList, ArrayList<String> strMultiSelNameList) {
        Iterator it;
        this.m_strMultiSelPathList.clear();
        this.m_strMultiSelNameList.clear();
        if (strMultiSelPathList != null) {
            it = strMultiSelPathList.iterator();
            while (it.hasNext()) {
                this.m_strMultiSelPathList.add((String) it.next());
            }
        }
        if (strMultiSelNameList != null) {
            it = strMultiSelNameList.iterator();
            while (it.hasNext()) {
                this.m_strMultiSelNameList.add((String) it.next());
            }
        }
        SetEdit(true);
    }

    public void SetMobilePhotoPathAndId(ArrayList<String> strMultiSelPathList, ArrayList<Long> lMultiSelIdList) {
        this.m_strMultiSelPathList.clear();
        this.m_lMultiSelIdList.clear();
        if (strMultiSelPathList != null) {
            Iterator it = strMultiSelPathList.iterator();
            while (it.hasNext()) {
                this.m_strMultiSelPathList.add((String) it.next());
            }
        }
        if (lMultiSelIdList != null) {
            Iterator it2 = lMultiSelIdList.iterator();
            while (it2.hasNext()) {
                this.m_lMultiSelIdList.add(Long.valueOf(((Long) it2.next()).longValue()));
            }
        }
        SetEdit(true);
    }

    public void SetPhotoCopiesList(ArrayList<Integer> iMultiSelCopiesList) {
        this.m_iMultiSelCopiesList.clear();
        if (iMultiSelCopiesList != null) {
            Iterator it = iMultiSelCopiesList.iterator();
            while (it.hasNext()) {
                this.m_iMultiSelCopiesList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        SetEdit(true);
    }
}
