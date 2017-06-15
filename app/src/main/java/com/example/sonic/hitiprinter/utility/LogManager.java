package com.example.sonic.hitiprinter.utility;

import android.util.Log;

public class LogManager {
    private int m_iEnble;

    public LogManager(int iEnable) {
        this.m_iEnble = 0;
        SetEnable(iEnable);
    }

    public void SetEnable(int iEnable) {
        this.m_iEnble = iEnable;
    }

    public void m385i(String strTag, String strMsg) {
        if (this.m_iEnble == 1) {
            Log.i(strTag, String.valueOf(strMsg));
        }
    }

    public void m386v(String strTag, String strMsg) {
        if (this.m_iEnble == 1) {
            Log.v(strTag, String.valueOf(strMsg));
        }
    }

    public void m384e(String strTag, String strMsg) {
        if (this.m_iEnble == 1) {
            Log.e(strTag, String.valueOf(strMsg));
        }
    }

    public void m383d(String strTag, String strMsg) {
        if (this.m_iEnble == 1) {
            Log.e(strTag, String.valueOf(strMsg));
        }
    }
}
