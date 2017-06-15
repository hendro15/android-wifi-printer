package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_TotalPrintedRecord extends BaseGlobalVariable {
    private HashMap<String, Integer> m_PrintOutList;
    private String m_SerialNumber;
    private int m_iPrintedRecord;
    private String m_strProductId;

    public GlobalVariable_TotalPrintedRecord(Context context, String strProductionID) {
        super(context);
        this.m_strProductId = XmlPullParser.NO_NAMESPACE;
        this.m_SerialNumber = XmlPullParser.NO_NAMESPACE;
        this.m_PrintOutList = null;
        this.m_iPrintedRecord = -1;
        this.m_sp = context.getSharedPreferences("PREF_GV_TOTAL_PRINTED_RECORD" + strProductionID, 0);
        this.m_strProductId = strProductionID;
        this.m_PrintOutList = new HashMap();
        this.LOG.m385i("GlobalVariable_TotalPrintedRecord", "Create: " + strProductionID);
    }

    public boolean IsEmpty() {
        this.LOG.m385i("TotalPrintedRecord_IsEmpty", String.valueOf(this.m_PrintOutList.isEmpty()));
        return this.m_PrintOutList.isEmpty();
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_SerialNumber = this.m_sp.getString("GV_M_SERIAL_NUMBER", XmlPullParser.NO_NAMESPACE);
            this.m_PrintOutList.clear();
            AddPrintOutValue("2x3");
            AddPrintOutValue("4x6");
            AddPrintOutValue("5x7");
            AddPrintOutValue("6x8");
            this.LOG.m385i("m_ProductId", this.m_strProductId);
            this.LOG.m385i("m_PrintOutList", String.valueOf(this.m_PrintOutList));
            SetEdit(false);
            this.LOG.m385i("TotalPrintedRecord", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_TotalPrintedRecord", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    private void AddPrintOutValue(String printoutType) {
        this.m_PrintOutList.put(printoutType, Integer.valueOf(this.m_sp.getInt("GV_M_PRINTER_PRINTOUT" + printoutType, 0)));
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            this.LOG.m385i("SaveGlobalVariableForever", "m_PrintOutList: " + this.m_PrintOutList);
            try {
                Editor spe = this.m_sp.edit();
                spe.clear();
                spe.putString("GV_M_SERIAL_NUMBER", this.m_SerialNumber);
                spe.putInt("GV_M_TOTAL_PRINTED_RECORD", this.m_iPrintedRecord);
                for (String Key : this.m_PrintOutList.keySet()) {
                    spe.putInt("GV_M_PRINTER_PRINTOUT" + Key, ((Integer) this.m_PrintOutList.get(Key)).intValue());
                    this.LOG.m385i("GV_M_PRINTER_PRINTOUT" + Key, XmlPullParser.NO_NAMESPACE + this.m_PrintOutList.get(Key));
                }
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_UploadInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_UploadInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_UploadInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetSerialNumber() {
        return this.m_SerialNumber;
    }

    public void SetSerialNumber(String SerialNumber) {
        this.m_SerialNumber = SerialNumber;
        SetEdit(true);
    }

    public String GetProductID() {
        return this.m_strProductId;
    }

    public HashMap<String, Integer> GetPrintFrameList() {
        return this.m_PrintOutList;
    }

    public void SetPrintOutList(HashMap<String, Integer> mPrintOutList) {
        for (String count : mPrintOutList.keySet()) {
            this.m_PrintOutList.put(count, mPrintOutList.get(count));
        }
        SetEdit(true);
    }

    public void SetTotalPrintedRecord(int iPrintedRecord) {
        this.m_iPrintedRecord = iPrintedRecord;
        SetEdit(true);
    }
}
