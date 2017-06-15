package com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

public class EditMeta {
    ArrayList<Boolean> m_bIsEditList;
    ArrayList<Boolean> m_bIsVerticalList;
    ArrayList<Integer> m_iBorderPosList;
    ArrayList<Integer> m_iFilterPosList;
    ArrayList<Integer> m_iPhotoIDList;
    ArrayList<Integer> m_iPhotoSIDList;
    ArrayList<Integer> m_iSelCopiesList;
    ArrayList<Integer> m_iSelIndexList;
    int m_iSrcRoute;
    ArrayList<Long> m_lmobileIdList;
    ArrayList<String> m_strCollagePathList;
    ArrayList<String> m_strFetchPathList;
    ArrayList<String> m_strMobilePathList;
    ArrayList<String> m_strPhotoNameList;
    ArrayList<String> m_strThumbPathList;
    ArrayList<String> m_strXMLpathList;

    public enum EditMode {
        Back,
        Edit,
        Print
    }

    public EditMeta(int iRoute) {
        this.m_strMobilePathList = null;
        this.m_strThumbPathList = null;
        this.m_strPhotoNameList = null;
        this.m_strFetchPathList = null;
        this.m_strCollagePathList = null;
        this.m_strXMLpathList = null;
        this.m_iPhotoIDList = null;
        this.m_iPhotoSIDList = null;
        this.m_iSelCopiesList = null;
        this.m_lmobileIdList = null;
        this.m_bIsVerticalList = null;
        this.m_bIsEditList = null;
        this.m_iBorderPosList = null;
        this.m_iFilterPosList = null;
        this.m_iSelIndexList = null;
        this.m_iSrcRoute = 0;
        this.m_iSrcRoute = iRoute;
        this.m_iSelCopiesList = new ArrayList();
        if (iRoute == 1) {
            this.m_strMobilePathList = new ArrayList();
            this.m_lmobileIdList = new ArrayList();
        } else {
            this.m_iPhotoIDList = new ArrayList();
            this.m_iPhotoSIDList = new ArrayList();
            this.m_strThumbPathList = new ArrayList();
            this.m_strPhotoNameList = new ArrayList();
            this.m_strFetchPathList = new ArrayList();
        }
        this.m_bIsVerticalList = new ArrayList();
        this.m_bIsEditList = new ArrayList();
        this.m_strXMLpathList = new ArrayList();
        this.m_iBorderPosList = new ArrayList();
        this.m_iFilterPosList = new ArrayList();
        this.m_iSelIndexList = new ArrayList();
        this.m_strCollagePathList = new ArrayList();
    }

    public boolean IsEmpty() {
        if (this.m_iPhotoIDList.isEmpty()) {
            return true;
        }
        return false;
    }

    public void SetSDcardMeta(ArrayList<Integer> iPhotoIDList, ArrayList<Integer> iPhotoSIDList) {
        Iterator it;
        if (iPhotoIDList != null) {
            this.m_iPhotoIDList.clear();
            it = iPhotoIDList.iterator();
            while (it.hasNext()) {
                this.m_iPhotoIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
        if (iPhotoSIDList != null) {
            this.m_iPhotoSIDList.clear();
            it = iPhotoSIDList.iterator();
            while (it.hasNext()) {
                this.m_iPhotoSIDList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void SetMobilePathAndID(ArrayList<String> strMobilePathList, ArrayList<Long> lmobileIdList) {
        if (strMobilePathList != null) {
            this.m_strMobilePathList.clear();
            Iterator it = strMobilePathList.iterator();
            while (it.hasNext()) {
                this.m_strMobilePathList.add((String) it.next());
            }
        }
        if (this.m_lmobileIdList != null && lmobileIdList != null) {
            this.m_lmobileIdList.clear();
            Iterator it2 = lmobileIdList.iterator();
            while (it2.hasNext()) {
                this.m_lmobileIdList.add(Long.valueOf(((Long) it2.next()).longValue()));
            }
        }
    }

    public void SetCopies(ArrayList<Integer> iCopiesList) {
        if (iCopiesList != null) {
            this.m_iSelCopiesList.clear();
            Iterator it = iCopiesList.iterator();
            while (it.hasNext()) {
                this.m_iSelCopiesList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void SetEditSelPos(ArrayList<Integer> iSelPosList) {
        if (iSelPosList != null) {
            Iterator it = iSelPosList.iterator();
            while (it.hasNext()) {
                this.m_iSelIndexList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void SetFetchPath(ArrayList<String> strFetchPathList) {
        if (strFetchPathList != null) {
            this.m_strFetchPathList.clear();
            Iterator it = strFetchPathList.iterator();
            while (it.hasNext()) {
                this.m_strFetchPathList.add((String) it.next());
            }
        }
    }

    public void SetThumbPathAndName(ArrayList<String> strThumbPathList, ArrayList<String> strPhotoNameList) {
        Iterator it;
        if (strThumbPathList != null) {
            this.m_strThumbPathList.clear();
            it = strThumbPathList.iterator();
            while (it.hasNext()) {
                this.m_strThumbPathList.add((String) it.next());
            }
        }
        if (strPhotoNameList != null) {
            this.m_strPhotoNameList.clear();
            it = strPhotoNameList.iterator();
            while (it.hasNext()) {
                this.m_strPhotoNameList.add((String) it.next());
            }
        }
    }

    public void SetXMLVerticalIsEdit(ArrayList<String> strXMLList, ArrayList<Boolean> bIsVerticalList, ArrayList<Boolean> bIsEditList) {
        if (strXMLList != null && bIsVerticalList != null && bIsEditList != null) {
            this.m_bIsVerticalList.clear();
            this.m_bIsEditList.clear();
            this.m_strXMLpathList.clear();
            Iterator it = bIsVerticalList.iterator();
            while (it.hasNext()) {
                this.m_bIsVerticalList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
            it = bIsEditList.iterator();
            while (it.hasNext()) {
                this.m_bIsEditList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
            }
            Iterator it2 = strXMLList.iterator();
            while (it2.hasNext()) {
                this.m_strXMLpathList.add((String) it2.next());
            }
        }
    }

    public void SetBorderAndFilterPos(ArrayList<Integer> iBorderPosList, ArrayList<Integer> iFilterPosList) {
        if (iBorderPosList != null && iFilterPosList != null) {
            this.m_iBorderPosList.clear();
            this.m_iFilterPosList.clear();
            Iterator it = iBorderPosList.iterator();
            while (it.hasNext()) {
                this.m_iBorderPosList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
            it = iFilterPosList.iterator();
            while (it.hasNext()) {
                this.m_iFilterPosList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void SetCollagePath(ArrayList<String> strCollagePathList) {
        if (strCollagePathList != null) {
            this.m_strCollagePathList.clear();
            Iterator it = strCollagePathList.iterator();
            while (it.hasNext()) {
                this.m_strCollagePathList.add((String) it.next());
            }
        }
    }

    public int GetSrcRoute() {
        return this.m_iSrcRoute;
    }

    public ArrayList<Integer> GetCopiesList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iSelCopiesList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<Integer> GetSDcardIDList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iPhotoIDList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<Integer> GetSDcardSIDList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iPhotoSIDList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<String> GetFetchPathList() {
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strFetchPathList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<String> GetThumbPathList() {
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strThumbPathList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<String> GetPhotoNameList() {
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strPhotoNameList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<String> GetCollagePathList() {
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strCollagePathList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<Integer> GetSelPosList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iSelIndexList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<String> GetXMLList() {
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strXMLpathList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<Boolean> GetIsVerticalList() {
        ArrayList<Boolean> tempList = new ArrayList();
        Iterator it = this.m_bIsVerticalList.iterator();
        while (it.hasNext()) {
            tempList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
        }
        return tempList;
    }

    public ArrayList<Boolean> GetIsEditList() {
        ArrayList<Boolean> tempList = new ArrayList();
        Iterator it = this.m_bIsEditList.iterator();
        while (it.hasNext()) {
            tempList.add(Boolean.valueOf(((Boolean) it.next()).booleanValue()));
        }
        return tempList;
    }

    public ArrayList<Integer> GetBorderPosList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iBorderPosList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<Integer> GetFilterPosList() {
        ArrayList<Integer> tempList = new ArrayList();
        Iterator it = this.m_iFilterPosList.iterator();
        while (it.hasNext()) {
            tempList.add(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        return tempList;
    }

    public ArrayList<String> GetMobilePathList() {
        Log.i("GetMobilePathList", XmlPullParser.NO_NAMESPACE + this.m_strMobilePathList);
        ArrayList<String> tempList = new ArrayList();
        Iterator it = this.m_strMobilePathList.iterator();
        while (it.hasNext()) {
            tempList.add((String) it.next());
        }
        return tempList;
    }

    public ArrayList<Long> GetMobileIDList() {
        ArrayList<Long> tempList = new ArrayList();
        Iterator it = this.m_lmobileIdList.iterator();
        while (it.hasNext()) {
            tempList.add(Long.valueOf(((Long) it.next()).longValue()));
        }
        return tempList;
    }

    public Boolean GetIsVertical(int iPos) {
        return (Boolean) this.m_bIsVerticalList.get(iPos);
    }
}
