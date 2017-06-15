package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_PoolEditMeta extends BaseGlobalVariable_Prinbiz {
    ArrayList<Boolean> m_bIsEditList;
    boolean m_bIsNoData;
    ArrayList<Boolean> m_bIsVerticalList;
    ArrayList<Integer> m_iBorderPosList;
    ArrayList<Integer> m_iFilterPosList;
    int m_iType;
    ArrayList<String> m_strCollageList;
    ArrayList<String> m_strXMLList;

    public GlobalVariable_PoolEditMeta(Context context) {
        super(context);
        this.m_strXMLList = null;
        this.m_strCollageList = null;
        this.m_iBorderPosList = null;
        this.m_iFilterPosList = null;
        this.m_bIsVerticalList = null;
        this.m_bIsEditList = null;
        this.m_bIsNoData = false;
        this.m_iType = -1;
        this.m_sp = context.getSharedPreferences("PREF_GV_POOL_EDIT_META", 0);
        this.m_iBorderPosList = new ArrayList();
        this.m_iFilterPosList = new ArrayList();
        this.m_strXMLList = new ArrayList();
        this.m_bIsVerticalList = new ArrayList();
        this.m_bIsEditList = new ArrayList();
        this.m_strCollageList = new ArrayList();
    }

    public void RestoreGlobalVariable() {
        try {
            int i;
            this.m_strCollageList.clear();
            this.m_strXMLList.clear();
            this.m_bIsVerticalList.clear();
            this.m_bIsEditList.clear();
            this.m_iBorderPosList.clear();
            this.m_iFilterPosList.clear();
            int iCollageLen = this.m_sp.getInt("GV_M_EDIT_COLLAGE_LEN", 0);
            for (i = 0; i < iCollageLen; i++) {
                String collage = this.m_sp.getString("GV_M_EDIT_COLLAGE_META" + i, XmlPullParser.NO_NAMESPACE);
                this.m_strCollageList.add(collage);
                this.LOG.m385i("Add", "collage=" + collage);
            }
            int iXMLLen = this.m_sp.getInt("GV_M_EDIT_XML_LEN", 0);
            for (i = 0; i < iXMLLen; i++) {
                this.m_strXMLList.add(this.m_sp.getString("GV_M_EDIT_XML_META" + i, XmlPullParser.NO_NAMESPACE));
            }
            int iVLen = this.m_sp.getInt("GV_M_EDIT_VERTICAL_LEN", 0);
            for (i = 0; i < iVLen; i++) {
                this.m_bIsVerticalList.add(Boolean.valueOf(this.m_sp.getBoolean("GV_M_EDIT_VERTICAL" + i, false)));
            }
            int iELen = this.m_sp.getInt("GV_M_EDIT_ISEDIT_LEN", 0);
            for (i = 0; i < iELen; i++) {
                this.m_bIsEditList.add(Boolean.valueOf(this.m_sp.getBoolean("GV_M_EDIT_ISEDIT" + i, false)));
            }
            int iBorderLen = this.m_sp.getInt("GV_M_EDIT_BORDER_LEN", 0);
            for (i = 0; i < iBorderLen; i++) {
                this.m_iBorderPosList.add(Integer.valueOf(this.m_sp.getInt("GV_M_EDIT_BORDER_POS" + i, 0)));
            }
            int iFilterLen = this.m_sp.getInt("GV_M_EDIT_FILTER_LEN", 0);
            for (i = 0; i < iFilterLen; i++) {
                this.m_iFilterPosList.add(Integer.valueOf(this.m_sp.getInt("GV_M_EDIT_FILTER_POS" + i, 0)));
            }
            this.m_bIsNoData = false;
            SetEdit(false);
        } catch (Exception e) {
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
                for (i = 0; i < this.m_strCollageList.size(); i++) {
                    spe.putString("GV_M_EDIT_COLLAGE_META" + i, (String) this.m_strCollageList.get(i));
                }
                spe.putInt("GV_M_EDIT_COLLAGE_LEN", this.m_strCollageList.size());
                for (i = 0; i < this.m_strXMLList.size(); i++) {
                    spe.putString("GV_M_EDIT_XML_META" + i, (String) this.m_strXMLList.get(i));
                    this.LOG.m384e("save-m_strXMLList-" + i, (String) this.m_strXMLList.get(i));
                }
                spe.putInt("GV_M_EDIT_XML_LEN", this.m_strXMLList.size());
                for (i = 0; i < this.m_bIsVerticalList.size(); i++) {
                    spe.putBoolean("GV_M_EDIT_VERTICAL" + i, ((Boolean) this.m_bIsVerticalList.get(i)).booleanValue());
                }
                spe.putInt("GV_M_EDIT_VERTICAL_LEN", this.m_bIsVerticalList.size());
                for (i = 0; i < this.m_bIsEditList.size(); i++) {
                    spe.putBoolean("GV_M_EDIT_ISEDIT" + i, ((Boolean) this.m_bIsEditList.get(i)).booleanValue());
                    this.LOG.m384e("save-m_bIsEditList-" + i, XmlPullParser.NO_NAMESPACE + this.m_bIsEditList.get(i));
                }
                spe.putInt("GV_M_EDIT_ISEDIT_LEN", this.m_bIsEditList.size());
                for (i = 0; i < this.m_iBorderPosList.size(); i++) {
                    spe.putInt("GV_M_EDIT_BORDER_POS" + i, ((Integer) this.m_iBorderPosList.get(i)).intValue());
                }
                spe.putInt("GV_M_EDIT_BORDER_LEN", this.m_iBorderPosList.size());
                for (i = 0; i < this.m_iFilterPosList.size(); i++) {
                    spe.putInt("GV_M_EDIT_FILTER_POS" + i, ((Integer) this.m_iFilterPosList.get(i)).intValue());
                }
                spe.putInt("GV_M_EDIT_FILTER_LEN", this.m_iFilterPosList.size());
                if (!spe.commit()) {
                    this.LOG.m384e("!!!!GlobalVariable_MultiSelContainer", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_MultiSelContainer", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<String> GetXMLpathList() {
        Log.v("va Get m_bXMLpathList", "=" + String.valueOf(this.m_strXMLList));
        return this.m_strXMLList;
    }

    public ArrayList<Boolean> GetIsVerticalList() {
        return this.m_bIsVerticalList;
    }

    public ArrayList<Boolean> GetIsEditList() {
        Log.v("va  GetIsEditList", "=" + String.valueOf(this.m_bIsEditList));
        return this.m_bIsEditList;
    }

    public ArrayList<Integer> GetBorderPosList() {
        return this.m_iBorderPosList;
    }

    public ArrayList<Integer> GetFilterPosList() {
        return this.m_iFilterPosList;
    }

    public ArrayList<String> GetCollagePathList() {
        Log.i("GetCollagePathList", XmlPullParser.NO_NAMESPACE + this.m_strCollageList);
        return this.m_strCollageList;
    }

    public void SetXMLandVerticalList(ArrayList<String> strXMLList, ArrayList<Boolean> bIsVerticalList, ArrayList<Boolean> bIsEditList) {
        Iterator it;
        this.m_bIsEditList.clear();
        this.m_bIsVerticalList.clear();
        this.m_strXMLList.clear();
        if (strXMLList != null) {
            Iterator it2 = strXMLList.iterator();
            while (it2.hasNext()) {
                this.m_strXMLList.add((String) it2.next());
            }
        }
        if (bIsVerticalList != null) {
            it = bIsVerticalList.iterator();
            while (it.hasNext()) {
                this.m_bIsVerticalList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
        if (bIsEditList != null) {
            it = bIsEditList.iterator();
            while (it.hasNext()) {
                this.m_bIsEditList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
        }
        SetEdit(true);
    }

    public void SetBorderAndFilterList(ArrayList<Integer> iBorderPosList, ArrayList<Integer> iFilterList) {
        Iterator it;
        this.m_iBorderPosList.clear();
        this.m_iFilterPosList.clear();
        if (iBorderPosList != null) {
            it = iBorderPosList.iterator();
            while (it.hasNext()) {
                this.m_iBorderPosList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (iFilterList != null) {
            it = iFilterList.iterator();
            while (it.hasNext()) {
                this.m_iFilterPosList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        SetEdit(true);
    }

    public void SetCollagePathList(ArrayList<String> strCollageList) {
        this.m_strCollageList.clear();
        if (strCollageList != null) {
            Iterator it = strCollageList.iterator();
            while (it.hasNext()) {
                this.m_strCollageList.add((String) it.next());
            }
        }
        SetEdit(true);
    }
}
