package com.example.sonic.hitiprinter.sql.oadc;

import java.io.Serializable;

public class OADCItem implements Serializable {
    public static final String FIELD_ID = "_ID";
    public static final String FIELD_OADC = "_OADC";
    public static final String TABLE_NAME = "TBL_OADC";
    public static final String WATCH_TYPE_NON = "0";
    public static final String WATCH_TYPE_WATCH = "1";
    private static final long serialVersionUID = 3951632261980474986L;
    private int m_iID;
    private String m_strOADC;

    public OADCItem() {
        this.m_iID = -1;
        this.m_strOADC = null;
    }

    public int GetID() {
        return this.m_iID;
    }

    public void SetID(int iID) {
        this.m_iID = iID;
    }

    public String GetOADC() {
        return this.m_strOADC;
    }

    public void SetOADC(String strOADC) {
        this.m_strOADC = strOADC;
    }
}
