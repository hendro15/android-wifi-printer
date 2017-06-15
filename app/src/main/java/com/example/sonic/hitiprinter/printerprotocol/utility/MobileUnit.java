package com.example.sonic.hitiprinter.printerprotocol.utility;

import java.util.ArrayList;
import java.util.Iterator;

public class MobileUnit {
    ArrayList<Integer> m_iCopiesList;
    int m_iLastCopies;
    ArrayList<Integer> m_iPrintedJobIdList;
    ArrayList<Integer> m_iQueueJobIdList;
    ArrayList<String> m_strPhotoPathList;
    String m_strProductId;

    public MobileUnit() {
        this.m_strPhotoPathList = null;
        this.m_iCopiesList = null;
        this.m_iPrintedJobIdList = null;
        this.m_iQueueJobIdList = null;
        this.m_iLastCopies = 0;
        this.m_strProductId = null;
        this.m_strPhotoPathList = new ArrayList();
        this.m_iCopiesList = new ArrayList();
        this.m_iPrintedJobIdList = new ArrayList();
        this.m_iQueueJobIdList = new ArrayList();
    }

    public void SetProdutId(String strProductId) {
        this.m_strProductId = strProductId;
    }

    public String GetProductId() {
        return this.m_strProductId;
    }

    public void SetLastCopies(int iLastCopies) {
        this.m_iLastCopies = iLastCopies;
    }

    public int GetLastCopies() {
        return this.m_iLastCopies;
    }

    public void SetPhotoList(ArrayList<String> strPhotoPathList, ArrayList<Integer> iCopiesList) {
        if (strPhotoPathList != null && iCopiesList != null) {
            Iterator it = strPhotoPathList.iterator();
            while (it.hasNext()) {
                this.m_strPhotoPathList.add((String) it.next());
            }
            Iterator it2 = iCopiesList.iterator();
            while (it2.hasNext()) {
                this.m_iCopiesList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
            }
        }
    }

    public void GetPhotoList(ArrayList<String> strPhotoPathList, ArrayList<Integer> iCopiesList) {
        if (strPhotoPathList != null && iCopiesList != null) {
            Iterator it = this.m_strPhotoPathList.iterator();
            while (it.hasNext()) {
                strPhotoPathList.add((String) it.next());
            }
            Iterator it2 = this.m_iCopiesList.iterator();
            while (it2.hasNext()) {
                iCopiesList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
            }
        }
    }

    public void SetJobIdList(ArrayList<Integer> iPrintedJobIdList, ArrayList<Integer> iQueueJobIdList) {
        if (iPrintedJobIdList != null && iQueueJobIdList != null) {
            Iterator it = iPrintedJobIdList.iterator();
            while (it.hasNext()) {
                this.m_iPrintedJobIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
            it = iQueueJobIdList.iterator();
            while (it.hasNext()) {
                this.m_iQueueJobIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }

    public void GetJobIdList(ArrayList<Integer> iPrintedJobIdList, ArrayList<Integer> iQueueJobIdList) {
        if (iPrintedJobIdList != null && iQueueJobIdList != null) {
            Iterator it = this.m_iPrintedJobIdList.iterator();
            while (it.hasNext()) {
                iPrintedJobIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
            it = this.m_iQueueJobIdList.iterator();
            while (it.hasNext()) {
                iQueueJobIdList.add(Integer.valueOf(((Integer) it.next()).intValue()));
            }
        }
    }
}
