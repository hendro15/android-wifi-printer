package com.example.sonic.hitiprinter.printerprotocol;

import android.os.Handler;

public class MSGHandler extends Handler {
    public static final String MSG = "MSG";
    private boolean m_boStop;

    public MSGHandler() {
        this.m_boStop = false;
    }

    public void SetStop(boolean boStop) {
        this.m_boStop = boStop;
    }

    public boolean IsStop() {
        return this.m_boStop;
    }
}
