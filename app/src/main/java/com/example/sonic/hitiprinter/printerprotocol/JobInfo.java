package com.example.sonic.hitiprinter.printerprotocol;

public class JobInfo {
    public byte bFrmt;
    public byte bMSize;
    public byte bPrintLayout;
    public byte bQlty;
    public byte bSharpen;
    public byte bTxtr;
    public byte bType;
    public byte boDuplex;
    public byte boMaskColor;
    public int iDuplexSize;
    public int iMaskSize;
    public int iTotal;
    public int iTotalSize;
    public long lSize;
    public short shCopies;
    public int shJobId;

    public JobInfo() {
        this.shJobId = 0;
        this.iTotal = 1;
        this.bFrmt = (byte) 0;
        this.shCopies = (short) 0;
        this.lSize = 0;
        this.bQlty = (byte) 0;
        this.bType = (byte) 0;
        this.bMSize = (byte) 0;
        this.bTxtr = (byte) 0;
        this.iMaskSize = 0;
        this.iTotalSize = 0;
        this.boMaskColor = (byte) 0;
        this.bSharpen = (byte) 0;
        this.bPrintLayout = (byte) 0;
        this.boDuplex = (byte) 0;
        this.iDuplexSize = 0;
    }
}
