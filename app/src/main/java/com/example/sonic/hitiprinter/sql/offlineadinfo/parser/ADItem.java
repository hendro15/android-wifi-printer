package com.example.sonic.hitiprinter.sql.offlineadinfo.parser;

import org.xmlpull.v1.XmlPullParser;

public class ADItem {
    private boolean m_boInDB;
    private String m_strAD_Ending_Time;
    private String m_strAD_Item_ID;
    private String m_strAD_Photo_Name;
    private String m_strAD_Photo_Path;
    private String m_strAD_Photo_Size;
    private String m_strAD_Photo_Time;
    private String m_strAD_Priority;
    private String m_strAD_Starting_Time;
    private String m_strAD_Video_Name;
    private String m_strAD_Video_Path;
    private String m_strAD_Video_Size;
    private String m_strAD_Video_Time;

    public ADItem() {
        this.m_strAD_Item_ID = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Priority = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Starting_Time = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Ending_Time = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Video_Name = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Video_Path = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Video_Size = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Video_Time = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Photo_Name = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Photo_Path = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Photo_Size = XmlPullParser.NO_NAMESPACE;
        this.m_strAD_Photo_Time = XmlPullParser.NO_NAMESPACE;
        this.m_boInDB = false;
    }

    public String GetADItemID() {
        return this.m_strAD_Item_ID;
    }

    public void SetADItemID(String strAD_Item_ID) {
        this.m_strAD_Item_ID = strAD_Item_ID;
    }

    public String GetADPriority() {
        return this.m_strAD_Priority;
    }

    public void SetADPriority(String strAD_Priority) {
        this.m_strAD_Priority = strAD_Priority;
    }

    public String GetADStartingTime() {
        return this.m_strAD_Starting_Time;
    }

    public void SetADStartingTime(String strAD_Starting_Time) {
        this.m_strAD_Starting_Time = strAD_Starting_Time;
    }

    public String GetADEndingTime() {
        return this.m_strAD_Ending_Time;
    }

    public void SetADEndingTime(String strAD_Ending_Time) {
        this.m_strAD_Ending_Time = strAD_Ending_Time;
    }

    public String GetADVideoName() {
        return this.m_strAD_Video_Name;
    }

    public void SetADVideoName(String strAD_Video_Name) {
        this.m_strAD_Video_Name = strAD_Video_Name;
    }

    public String GetADVideoPath() {
        return this.m_strAD_Video_Path;
    }

    public void SetADVideoPath(String strAD_Video_Path) {
        this.m_strAD_Video_Path = strAD_Video_Path;
    }

    public String GetADVideoSize() {
        return this.m_strAD_Video_Size;
    }

    public void SetADVideoSize(String strAD_Video_Size) {
        this.m_strAD_Video_Size = strAD_Video_Size;
    }

    public String GetADVideoTime() {
        return this.m_strAD_Video_Time;
    }

    public void SetADVideoTime(String strAD_Video_Time) {
        this.m_strAD_Video_Time = strAD_Video_Time;
    }

    public String GetADPhotoName() {
        return this.m_strAD_Photo_Name;
    }

    public void SetADPhotoName(String strAD_Photo_Name) {
        this.m_strAD_Photo_Name = strAD_Photo_Name;
    }

    public String GetADPhotoPath() {
        return this.m_strAD_Photo_Path;
    }

    public void SetADPhotoPath(String strAD_Photo_Path) {
        this.m_strAD_Photo_Path = strAD_Photo_Path;
    }

    public String GetADPhotoSize() {
        return this.m_strAD_Photo_Size;
    }

    public void SetADPhotoSize(String strAD_Photo_Size) {
        this.m_strAD_Photo_Size = strAD_Photo_Size;
    }

    public String GetADPhotoTime() {
        return this.m_strAD_Photo_Time;
    }

    public void SetADPhotoTime(String strAD_Photo_Time) {
        this.m_strAD_Photo_Time = strAD_Photo_Time;
    }

    public boolean GetInDB() {
        return this.m_boInDB;
    }

    public void SetInDB(boolean boInDB) {
        this.m_boInDB = boInDB;
    }
}
