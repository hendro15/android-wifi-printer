package com.example.sonic.hitiprinter.sql.printerInfo;

import java.io.Serializable;

public class PrintingInfo implements Serializable {
    public static final String FIELD_COPYS = "_COPYS";
    public static final String FIELD_FLASH_CARD = "_FIELD_FLASH_CARD";
    public static final String FIELD_ID = "_ID";
    public static final String FIELD_MASK_COLOR = "_MASK_COLOR";
    public static final String FIELD_PAPER_SIZE = "_PAPER_SIZE";
    public static final String FIELD_PRINTING_TIME = "_PRINTING_TIME";
    public static final String FIELD_REAL_COUNT = "_REAL_COUNT";
    public static final String FIELD_SERIAL_NUMBER = "_SERIAL_NUMBER";
    public static final String FIELD_SNAP_PRINT = "_SNAP_PRINT";
    public static final String FIELD_TOTAL_FRAME = "_TOTAL_FRAME";
    public static final String FIELD_TYPE = "_TYPE";
    public static final String FIELD_UPLOADER = "_PRINTING_UPLOADER";
    public static final String TABLE_NAME = "TBL_PRINTING_INFO";
    private static final long serialVersionUID = 3951632261980474202L;
    private int m_iFlashCard;
    private int m_iID;
    private int m_iMaskColor;
    private int m_iPaperSize;
    private int m_iSnapPrintFlag;
    private int m_iTotalFrame;
    private int m_iType;
    private String m_strCopys;
    private String m_strPrintingTime;
    private String m_strRealCount;
    private String m_strSerialNumber;
    private String m_strUpLoader;

    public PrintingInfo() {
        this.m_iID = -1;
        this.m_strSerialNumber = null;
        this.m_strPrintingTime = null;
        this.m_strUpLoader = null;
        this.m_iMaskColor = -1;
        this.m_iPaperSize = -1;
        this.m_strCopys = null;
        this.m_strRealCount = null;
        this.m_iFlashCard = -1;
        this.m_iType = -1;
    }

    public int GetID() {
        return this.m_iID;
    }

    public void SetID(int iID) {
        this.m_iID = iID;
    }

    public String GetSerialNumber() {
        return this.m_strSerialNumber;
    }

    public void SetSerialNumber(String strSerialNumber) {
        this.m_strSerialNumber = strSerialNumber;
    }

    public String GetPrintingTime() {
        return this.m_strPrintingTime;
    }

    public void SetPrintingTime(String strPrintingTime) {
        this.m_strPrintingTime = strPrintingTime;
    }

    public String GetUpLoader() {
        return this.m_strUpLoader;
    }

    public void SetUpLoader(String strUpLoader) {
        this.m_strUpLoader = strUpLoader;
    }

    public int GetMaskColor() {
        return this.m_iMaskColor;
    }

    public void SetMaskColor(int iMaskColor) {
        this.m_iMaskColor = iMaskColor;
    }

    public int GetPaperSize() {
        return this.m_iPaperSize;
    }

    public void SetPaperSize(int iPaperSize) {
        this.m_iPaperSize = iPaperSize;
    }

    public String GetCopys() {
        return this.m_strCopys;
    }

    public void SetCopys(String strCopys) {
        this.m_strCopys = strCopys;
    }

    public String GetRealCount() {
        return this.m_strRealCount;
    }

    public void SetRealCount(String strRealCount) {
        this.m_strRealCount = strRealCount;
    }

    public int GetFlashCard() {
        return this.m_iFlashCard;
    }

    public void SetFlashCard(int iFlashCard) {
        this.m_iFlashCard = iFlashCard;
    }

    public int GetType() {
        return this.m_iType;
    }

    public void SetType(int iType) {
        this.m_iType = iType;
    }

    public int GetTotalFrame() {
        return this.m_iTotalFrame;
    }

    public void SetTotalFrame(int iTotalFrame) {
        this.m_iTotalFrame = iTotalFrame;
    }

    public void SetSnapPrintFlag(int iSnapPrintFlag) {
        this.m_iSnapPrintFlag = iSnapPrintFlag;
    }

    public int GetSnapPrintFlag() {
        return this.m_iSnapPrintFlag;
    }
}
