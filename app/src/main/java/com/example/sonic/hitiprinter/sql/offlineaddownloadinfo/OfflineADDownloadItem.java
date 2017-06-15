package com.example.sonic.hitiprinter.sql.offlineaddownloadinfo;

import java.io.Serializable;

public class OfflineADDownloadItem implements Serializable {
    public static final int DOWNLOAD_STATUS_GET_FILE = 1;
    public static final int DOWNLOAD_STATUS_NON = 0;
    public static final String FIELD_AD_DOWNLOAD_STATUS = "_AD_DOWNLOAD_STATUS";
    public static final String FIELD_AD_ENDING_TIME = "_AD_ENDING_TIME";
    public static final String FIELD_AD_ITEM_ID = "_AD_ITEM_ID";
    public static final String FIELD_AD_PHOTO_FILE_PATH = "_AD_PHOTO_FILE_PATH";
    public static final String FIELD_AD_PHOTO_NAME = "_AD_PHOTO_NAME";
    public static final String FIELD_AD_PHOTO_PATH = "_AD_PHOTO_PATH";
    public static final String FIELD_AD_PHOTO_SIZE = "_AD_PHOTO_SIZE";
    public static final String FIELD_AD_PHOTO_TIME = "_AD_PHOTO_TIME";
    public static final String FIELD_AD_PRIORITY = "_AD_PRIORITY";
    public static final String FIELD_AD_SHOW_COUNT = "_AD_SHOW_COUNT";
    public static final String FIELD_AD_STARTING_TIME = "_AD_STARTING_TIME";
    public static final String FIELD_AD_VIDEO_FILE_PATH = "_AD_VIDEO_FILE_PATH";
    public static final String FIELD_AD_VIDEO_NAME = "_AD_VIDEO_NAME";
    public static final String FIELD_AD_VIDEO_PATH = "_AD_VIDEO_PATH";
    public static final String FIELD_AD_VIDEO_SIZE = "_AD_VIDEO_SIZE";
    public static final String FIELD_AD_VIDEO_TIME = "_AD_VIDEO_TIME";
    public static final String FIELD_ID = "_ID";
    public static final String TABLE_NAME = "TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO";
    private static final long serialVersionUID = 3951632261980474203L;
    private int m_iAD_Show_Count;
    private int m_iID;
    private String m_strAD_DownloadStatus;
    private String m_strAD_Ending_Time;
    private String m_strAD_Item_ID;
    private String m_strAD_Photo_File_Path;
    private String m_strAD_Photo_Name;
    private String m_strAD_Photo_Path;
    private String m_strAD_Photo_Size;
    private String m_strAD_Photo_Time;
    private String m_strAD_Priority;
    private String m_strAD_Starting_Time;
    private String m_strAD_Video_File_Path;
    private String m_strAD_Video_Name;
    private String m_strAD_Video_Path;
    private String m_strAD_Video_Size;
    private String m_strAD_Video_Time;

    public OfflineADDownloadItem() {
        this.m_iID = -1;
        this.m_strAD_Item_ID = null;
        this.m_strAD_Priority = null;
        this.m_strAD_Starting_Time = null;
        this.m_strAD_Ending_Time = null;
        this.m_strAD_Video_Name = null;
        this.m_strAD_Video_Path = null;
        this.m_strAD_Video_Size = null;
        this.m_strAD_Video_Time = null;
        this.m_strAD_Photo_Name = null;
        this.m_strAD_Photo_Path = null;
        this.m_strAD_Photo_Size = null;
        this.m_strAD_Photo_Time = null;
        this.m_iAD_Show_Count = DOWNLOAD_STATUS_NON;
        this.m_strAD_DownloadStatus = String.valueOf(DOWNLOAD_STATUS_NON);
    }

    public int GetID() {
        return this.m_iID;
    }

    public void SetID(int iID) {
        this.m_iID = iID;
    }

    public String GetAD_Item_ID() {
        return this.m_strAD_Item_ID;
    }

    public void SetAD_Item_ID(String strAD_Item_ID) {
        this.m_strAD_Item_ID = strAD_Item_ID;
    }

    public String GetAD_Priority() {
        return this.m_strAD_Priority;
    }

    public void SetAD_Priority(String strAD_Priority) {
        this.m_strAD_Priority = strAD_Priority;
    }

    public String GetAD_Starting_Time() {
        return this.m_strAD_Starting_Time;
    }

    public void SetAD_Starting_Time(String strAD_Starting_Time) {
        this.m_strAD_Starting_Time = strAD_Starting_Time;
    }

    public String GetAD_Ending_Time() {
        return this.m_strAD_Ending_Time;
    }

    public void SetAD_Ending_Time(String strAD_Ending_Time) {
        this.m_strAD_Ending_Time = strAD_Ending_Time;
    }

    public String GetAD_Video_Name() {
        return this.m_strAD_Video_Name;
    }

    public void SetAD_Video_Name(String strAD_Video_Name) {
        this.m_strAD_Video_Name = strAD_Video_Name;
    }

    public String GetAD_Video_Path() {
        return this.m_strAD_Video_Path;
    }

    public void SetAD_Video_Path(String strAD_Video_Path) {
        this.m_strAD_Video_Path = strAD_Video_Path;
    }

    public String GetAD_Video_Size() {
        return this.m_strAD_Video_Size;
    }

    public void SetAD_Video_Size(String iAD_Video_Size) {
        this.m_strAD_Video_Size = iAD_Video_Size;
    }

    public String GetAD_Video_Time() {
        return this.m_strAD_Video_Time;
    }

    public void SetAD_Video_Time(String iAD_Video_Time) {
        this.m_strAD_Video_Time = iAD_Video_Time;
    }

    public String GetAD_Photo_Name() {
        return this.m_strAD_Photo_Name;
    }

    public void SetAD_Photo_Name(String strAD_Photo_Name) {
        this.m_strAD_Photo_Name = strAD_Photo_Name;
    }

    public String GetAD_Photo_Path() {
        return this.m_strAD_Photo_Path;
    }

    public void SetAD_Photo_Path(String strAD_Photo_Path) {
        this.m_strAD_Photo_Path = strAD_Photo_Path;
    }

    public String GetAD_Photo_Size() {
        return this.m_strAD_Photo_Size;
    }

    public void SetAD_Photo_Size(String strAD_Photo_Size) {
        this.m_strAD_Photo_Size = strAD_Photo_Size;
    }

    public String GetAD_Photo_Time() {
        return this.m_strAD_Photo_Time;
    }

    public void SetAD_Photo_Time(String strAD_Photo_Time) {
        this.m_strAD_Photo_Time = strAD_Photo_Time;
    }

    public int GetAD_Show_Count() {
        return this.m_iAD_Show_Count;
    }

    public void SetAD_Show_Count(int iAD_Show_Count) {
        this.m_iAD_Show_Count = iAD_Show_Count;
    }

    public String GetAD_DownloadStatus() {
        return this.m_strAD_DownloadStatus;
    }

    public void SetAD_DownloadStatus(String strAD_DownloadStatus) {
        this.m_strAD_DownloadStatus = strAD_DownloadStatus;
    }

    public String GetAD_Video_File_Path() {
        return this.m_strAD_Video_File_Path;
    }

    public void SetAD_Video_File_Path(String strAD_Video_File_Path) {
        this.m_strAD_Video_File_Path = strAD_Video_File_Path;
    }

    public String GetAD_Photo_File_Path() {
        return this.m_strAD_Photo_File_Path;
    }

    public void SetAD_Photo_File_Path(String strAD_Photo_File_Path) {
        this.m_strAD_Photo_File_Path = strAD_Photo_File_Path;
    }
}
