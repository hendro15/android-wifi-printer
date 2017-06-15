package com.example.sonic.hitiprinter.sql.offlineadinfo.parser;

import android.content.Context;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class OfflineADInfo {
    public static final int TYPE_EDM = 0;
    private ArrayList<ADItem> m_ADItemManager;
    private Context m_Context;
    private int m_iNumber;
    private int m_iType;
    private int m_iVersion;
    private String m_strCountry;
    private String m_strInfo_ID;
    private String m_strOfflineADInfoXMLPath;

    public OfflineADInfo(Context context) {
        this.m_Context = null;
        this.m_strOfflineADInfoXMLPath = XmlPullParser.NO_NAMESPACE;
        this.m_strInfo_ID = XmlPullParser.NO_NAMESPACE;
        this.m_iVersion = 0;
        this.m_iType = 0;
        this.m_iNumber = 0;
        this.m_strCountry = XmlPullParser.NO_NAMESPACE;
        this.m_ADItemManager = null;
        this.m_Context = context;
        this.m_ADItemManager = new ArrayList();
    }

    public String GetOfflineADInfoXMLPath() {
        return this.m_strOfflineADInfoXMLPath;
    }

    public void SetOfflineADInfoXMLPath(String strOfflineADInfoXMLPath) {
        this.m_strOfflineADInfoXMLPath = strOfflineADInfoXMLPath;
    }

    public String GetInfoID() {
        return this.m_strInfo_ID;
    }

    public void SetInfoID(String strInfoID) {
        this.m_strInfo_ID = strInfoID;
    }

    public int GetVersion() {
        return this.m_iVersion;
    }

    public void SetVersion(int iVersion) {
        this.m_iVersion = iVersion;
    }

    public int GetType() {
        return this.m_iType;
    }

    public void SetType(int iType) {
        this.m_iType = iType;
    }

    public int GetNumber() {
        return this.m_iNumber;
    }

    public void SetNumber(int iNumber) {
        this.m_iNumber = iNumber;
    }

    public String GetCountry() {
        return this.m_strCountry;
    }

    public void SetCountry(String strCountry) {
        this.m_strCountry = strCountry;
    }

    public ADItem GetADItem(int i) {
        if (i < this.m_ADItemManager.size()) {
            return (ADItem) this.m_ADItemManager.get(i);
        }
        return null;
    }

    public void AddADItem(ADItem adItem) {
        this.m_ADItemManager.add(adItem);
    }

    public void Clear() {
        this.m_ADItemManager.clear();
    }
}
