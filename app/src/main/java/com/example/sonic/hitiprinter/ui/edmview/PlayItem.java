package com.example.sonic.hitiprinter.ui.edmview;

public class PlayItem {
    private String m_ADItemID;
    private String m_OADCIID;
    private int m_PhotoPlayTime;
    private int m_PlayID;
    private int m_ShowCount;
    private int m_VideoPlayTime;
    private boolean m_boPhotoPlay;
    private boolean m_boVideoPlay;
    private String m_strPhotoPath;
    private String m_strTimeStamp;
    private String m_strVideoPath;

    PlayItem(int PlayID, String ADItemID, String OADCIID, String strTimeStamp, String strVideoPath, boolean boVideoPlay, int VideoPlayTime, String strPhotoPath, boolean boPhotoPlay, int PhotoPlayTime, int ShowCount) {
        this.m_PlayID = PlayID;
        this.m_ADItemID = ADItemID;
        this.m_OADCIID = OADCIID;
        this.m_strTimeStamp = strTimeStamp;
        this.m_strVideoPath = strVideoPath;
        this.m_boVideoPlay = boVideoPlay;
        this.m_VideoPlayTime = VideoPlayTime;
        this.m_strPhotoPath = strPhotoPath;
        this.m_boPhotoPlay = boPhotoPlay;
        this.m_PhotoPlayTime = PhotoPlayTime;
        this.m_ShowCount = ShowCount;
    }

    public int GetPlayID() {
        return this.m_PlayID;
    }

    public void SetPlayID(int PlayID) {
        this.m_PlayID = PlayID;
    }

    public String GetADItemID() {
        return this.m_ADItemID;
    }

    public void SetADItemID(String ADItemID) {
        this.m_ADItemID = ADItemID;
    }

    public String GetOADCIID() {
        return this.m_OADCIID;
    }

    public void SetOADCIID(String OADCIID) {
        this.m_OADCIID = OADCIID;
    }

    public String GetTimeStamp() {
        return this.m_strTimeStamp;
    }

    public void SetTimeStamp(String strTimeStamp) {
        this.m_strTimeStamp = strTimeStamp;
    }

    public String GetVideoPath() {
        return this.m_strVideoPath;
    }

    public void SetVideoPath(String strVideoPath) {
        this.m_strVideoPath = strVideoPath;
    }

    public boolean GetVideoPlay() {
        return this.m_boVideoPlay;
    }

    public void SetVideoPlay(boolean boVideoPlay) {
        this.m_boVideoPlay = boVideoPlay;
    }

    public int GetVideoPlayTime() {
        return this.m_VideoPlayTime;
    }

    public void SetVideoPlayTime(int VideoPlayTime) {
        this.m_VideoPlayTime = VideoPlayTime;
    }

    public String GetPhotoPath() {
        return this.m_strPhotoPath;
    }

    public void SetPhotoPath(String strPhotoPath) {
        this.m_strPhotoPath = strPhotoPath;
    }

    public boolean GetPhotoPlay() {
        return this.m_boPhotoPlay;
    }

    public void SetPhotoPlay(boolean boPhotoPlay) {
        this.m_boPhotoPlay = boPhotoPlay;
    }

    public int GetPhotoPlayTime() {
        return this.m_PhotoPlayTime;
    }

    public void SetPhotoPlayTime(int PhotoPlayTime) {
        this.m_PhotoPlayTime = PhotoPlayTime;
    }

    public int GetShowCount() {
        return this.m_ShowCount;
    }

    public void SetShowCount(int ShowCount) {
        this.m_ShowCount = ShowCount;
    }
}
