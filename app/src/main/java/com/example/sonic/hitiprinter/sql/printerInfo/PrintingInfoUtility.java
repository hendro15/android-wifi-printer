package com.example.sonic.hitiprinter.sql.printerInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.utility.LogManager;

public class PrintingInfoUtility {
    public static final int SERVER_VALUE_FLASH_CARD = 1;
    public static final int SERVER_VALUE_FLASH_CARD_NONE = 0;
    public static final int SERVER_VALUE_MACHINE_TYPE_P231 = 1;
    public static final int SERVER_VALUE_MACHINE_TYPE_P232 = 2;
    public static final int SERVER_VALUE_MACHINE_TYPE_P233 = 6;
    public static final int SERVER_VALUE_MACHINE_TYPE_P235 = 9;
    public static final int SERVER_VALUE_MACHINE_TYPE_P310W = 4;
    public static final int SERVER_VALUE_MACHINE_TYPE_P461 = 7;
    public static final int SERVER_VALUE_MACHINE_TYPE_P520L = 3;
    public static final int SERVER_VALUE_MACHINE_TYPE_P525L = 10;
    public static final int SERVER_VALUE_MACHINE_TYPE_P530D = 8;
    public static final int SERVER_VALUE_MACHINE_TYPE_P750L = 5;
    public static final int SERVER_VALUE_PAPER_TYPE_2X3 = 1;
    public static final int SERVER_VALUE_PAPER_TYPE_4X6 = 2;
    public static final int SERVER_VALUE_PAPER_TYPE_5X7 = 3;
    public static final int SERVER_VALUE_PAPER_TYPE_6X6 = 8;
    public static final int SERVER_VALUE_PAPER_TYPE_6X6_x2 = 9;
    public static final int SERVER_VALUE_PAPER_TYPE_6X8 = 4;
    public static final int SERVER_VALUE_PAPER_TYPE_6X8_2up = 6;
    public static final int SERVER_VALUE_PAPER_TYPE_6X8_2up_x2 = 7;
    public static final int SERVER_VALUE_PAPER_TYPE_6X8_x2 = 5;
    public static final int SERVER_VALUE_RESIN_TYPE_GOLD = 2;
    public static final int SERVER_VALUE_RESIN_TYPE_NONE = 0;
    public static final int SERVER_VALUE_RESIN_TYPE_SILVER = 1;
    private LogManager LOG;
    private PrinterInfoOpenHelper printerInfoOpenHelper;
    private SQLiteDatabase sqliteDatabase;

    public PrintingInfoUtility(Context ctx) {
        this.LOG = null;
        this.printerInfoOpenHelper = new PrinterInfoOpenHelper(ctx, SERVER_VALUE_PAPER_TYPE_6X8_x2);
        this.sqliteDatabase = this.printerInfoOpenHelper.getWritableDatabase();
        this.LOG = new LogManager(SERVER_VALUE_RESIN_TYPE_NONE);
    }

    public void Close() {
        this.printerInfoOpenHelper.close();
    }

    public Cursor GetPrintingInfoCursor() {
        return this.sqliteDatabase.query(PrintingInfo.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
    }

    public PrintingInfo GetPrintingInfoById(long id) {
        String[] strArr = new String[SERVER_VALUE_RESIN_TYPE_SILVER];
        strArr[SERVER_VALUE_RESIN_TYPE_NONE] = String.valueOf(id);
        Cursor c = this.sqliteDatabase.query(PrintingInfo.TABLE_NAME, null, "_ID=?", strArr, null, null, null);
        PrintingInfo printingInfo = new PrintingInfo();
        if (c.moveToFirst()) {
            printingInfo.SetID(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID)));
            printingInfo.SetSerialNumber(c.getString(c.getColumnIndex(PrintingInfo.FIELD_SERIAL_NUMBER)));
            printingInfo.SetPrintingTime(c.getString(c.getColumnIndex(PrintingInfo.FIELD_PRINTING_TIME)));
            printingInfo.SetUpLoader(c.getString(c.getColumnIndex(PrintingInfo.FIELD_UPLOADER)));
            printingInfo.SetMaskColor(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_MASK_COLOR)));
            printingInfo.SetPaperSize(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_PAPER_SIZE)));
            printingInfo.SetCopys(c.getString(c.getColumnIndex(PrintingInfo.FIELD_COPYS)));
            printingInfo.SetRealCount(c.getString(c.getColumnIndex(PrintingInfo.FIELD_REAL_COUNT)));
            printingInfo.SetFlashCard(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_FLASH_CARD)));
            printingInfo.SetType(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TYPE)));
            printingInfo.SetTotalFrame(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TOTAL_FRAME)));
            printingInfo.SetSnapPrintFlag(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_SNAP_PRINT)));
        }
        c.close();
        return printingInfo;
    }

    public PrintingInfo GetPrintingInfoFirst() {
        Cursor c = this.sqliteDatabase.query(PrintingInfo.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
        PrintingInfo printingInfo = new PrintingInfo();
        if (c.moveToFirst()) {
            this.LOG.m384e("Get First", String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID))));
            printingInfo.SetID(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID)));
            printingInfo.SetSerialNumber(c.getString(c.getColumnIndex(PrintingInfo.FIELD_SERIAL_NUMBER)));
            printingInfo.SetPrintingTime(c.getString(c.getColumnIndex(PrintingInfo.FIELD_PRINTING_TIME)));
            printingInfo.SetUpLoader(c.getString(c.getColumnIndex(PrintingInfo.FIELD_UPLOADER)));
            printingInfo.SetMaskColor(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_MASK_COLOR)));
            printingInfo.SetPaperSize(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_PAPER_SIZE)));
            printingInfo.SetCopys(c.getString(c.getColumnIndex(PrintingInfo.FIELD_COPYS)));
            printingInfo.SetRealCount(c.getString(c.getColumnIndex(PrintingInfo.FIELD_REAL_COUNT)));
            printingInfo.SetFlashCard(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_FLASH_CARD)));
            printingInfo.SetType(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TYPE)));
            printingInfo.SetTotalFrame(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TOTAL_FRAME)));
            printingInfo.SetSnapPrintFlag(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_SNAP_PRINT)));
        }
        c.close();
        this.LOG.m384e("Get First", "ID=" + printingInfo.GetID());
        return printingInfo;
    }

    public PrintingInfo GetPrintingInfoFromCursor(Cursor c) {
        PrintingInfo printingInfo = new PrintingInfo();
        if (c != null) {
            printingInfo.SetID(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID)));
            printingInfo.SetSerialNumber(c.getString(c.getColumnIndex(PrintingInfo.FIELD_SERIAL_NUMBER)));
            printingInfo.SetPrintingTime(c.getString(c.getColumnIndex(PrintingInfo.FIELD_PRINTING_TIME)));
            printingInfo.SetUpLoader(c.getString(c.getColumnIndex(PrintingInfo.FIELD_UPLOADER)));
            printingInfo.SetMaskColor(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_MASK_COLOR)));
            printingInfo.SetPaperSize(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_PAPER_SIZE)));
            printingInfo.SetCopys(c.getString(c.getColumnIndex(PrintingInfo.FIELD_COPYS)));
            printingInfo.SetRealCount(c.getString(c.getColumnIndex(PrintingInfo.FIELD_REAL_COUNT)));
            printingInfo.SetFlashCard(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_FLASH_CARD)));
            printingInfo.SetType(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TYPE)));
            printingInfo.SetTotalFrame(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TOTAL_FRAME)));
            printingInfo.SetSnapPrintFlag(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_SNAP_PRINT)));
        }
        return printingInfo;
    }

    public void PrintPrintingInfo() {
        Cursor c = GetPrintingInfoCursor();
        if (c.moveToFirst()) {
            do {
                this.LOG.m385i("v.5_Count Record", ("01.ID: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID))) + "\n" + "02.Serial Number: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_SERIAL_NUMBER)) + "\n" + "03.Printing Time: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_PRINTING_TIME)) + "\n" + "04.Uploader: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_UPLOADER)) + "\n" + "05.MsakColor: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_MASK_COLOR))) + "\n" + "06.Paper Size: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_PAPER_SIZE))) + "\n" + "07.Copys: " + String.valueOf(c.getString(c.getColumnIndex(PrintingInfo.FIELD_COPYS))) + "\n" + "08.Real Printing Count: " + String.valueOf(c.getString(c.getColumnIndex(PrintingInfo.FIELD_REAL_COUNT))) + "\n" + "09.Flash Card: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_FLASH_CARD))) + "\n" + "10.Type: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TYPE))) + "\n" + "11.Total Frame: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_TOTAL_FRAME))) + "\n") + "12.Snap Print: " + String.valueOf(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_SNAP_PRINT))) + "\n");
            } while (c.moveToNext());
        } else {
            this.LOG.m384e("Print Current Record", OADCItem.WATCH_TYPE_NON);
        }
        c.close();
    }

    public long InsertPrintingInfo(String strSerialNumber, String strPrintingTime, String strUpLoader, int iMaskColor, int iPaperSize, String strCopys, String strRealCount, int iFlashCard, int iType, int iTotalFram, int iPrintMode) {
        if (strSerialNumber == null) {
            strSerialNumber = "NULL";
        }
        if (strPrintingTime == null) {
            strPrintingTime = "NULL";
        }
        if (strUpLoader == null) {
            strUpLoader = "NULL";
        }
        if (strCopys == null) {
            strCopys = "NULL";
        }
        if (strRealCount == null) {
            strRealCount = "NULL";
        }
        ContentValues value = new ContentValues();
        value.put(PrintingInfo.FIELD_SERIAL_NUMBER, strSerialNumber);
        value.put(PrintingInfo.FIELD_PRINTING_TIME, strPrintingTime);
        value.put(PrintingInfo.FIELD_UPLOADER, strUpLoader);
        value.put(PrintingInfo.FIELD_MASK_COLOR, Integer.valueOf(iMaskColor));
        value.put(PrintingInfo.FIELD_PAPER_SIZE, Integer.valueOf(iPaperSize));
        value.put(PrintingInfo.FIELD_COPYS, strCopys);
        value.put(PrintingInfo.FIELD_REAL_COUNT, strRealCount);
        value.put(PrintingInfo.FIELD_FLASH_CARD, Integer.valueOf(iFlashCard));
        value.put(PrintingInfo.FIELD_TYPE, Integer.valueOf(iType));
        value.put(PrintingInfo.FIELD_TOTAL_FRAME, Integer.valueOf(iTotalFram));
        value.put(PrintingInfo.FIELD_SNAP_PRINT, Integer.valueOf(iPrintMode));
        this.LOG.m384e("InsertPrintingInfo iTotalFram", String.valueOf(iTotalFram));
        this.LOG.m384e("InsertPrintingInfo iPrintMode", String.valueOf(iPrintMode));
        return InsertPrintingInfo(value);
    }

    public long InsertPrintingInfo(ContentValues value) {
        long result = this.sqliteDatabase.insert(PrintingInfo.TABLE_NAME, PrintingInfo.FIELD_ID, value);
        this.LOG.m384e("Insert ID", String.valueOf(result));
        return result;
    }

    public boolean UpdatePrintingInfo(int iID, String strSerialNumber, String strPrintingTime, String strUpLoader, String strMaskColor, String strPaperSize, String strCopys, String strRealCount, String strFlashCard, String strType, int iTotalFrame) {
        this.LOG.m386v("UpdatePrintingInfo: " + iID, "frame: " + iTotalFrame);
        PrintingInfo value = GetPrintingInfoById((long) iID);
        value.SetID(iID);
        if (strSerialNumber != null) {
            value.SetSerialNumber(strSerialNumber);
        }
        if (strPrintingTime != null) {
            value.SetPrintingTime(strPrintingTime);
        }
        if (strUpLoader != null) {
            value.SetUpLoader(strUpLoader);
        }
        if (strMaskColor != null) {
            value.SetMaskColor(Integer.valueOf(strMaskColor).intValue());
        }
        if (strPaperSize != null) {
            value.SetPaperSize(Integer.valueOf(strPaperSize).intValue());
        }
        if (strCopys != null) {
            value.SetCopys(strCopys);
        }
        if (strRealCount != null) {
            value.SetRealCount(strRealCount);
        }
        if (strFlashCard != null) {
            value.SetFlashCard(Integer.valueOf(strFlashCard).intValue());
        }
        if (strType != null) {
            value.SetType(Integer.valueOf(strType).intValue());
        }
        if (iTotalFrame != -1) {
            value.SetTotalFrame(iTotalFrame);
        }
        return UpdatePrintingInfo(value);
    }

    public boolean UpdatePrintingInfo(PrintingInfo value) {
        boolean result = false;
        try {
            String sql = ("update TBL_PRINTING_INFO set _SERIAL_NUMBER=?, _PRINTING_TIME=?, _PRINTING_UPLOADER=?, _MASK_COLOR=?, _PAPER_SIZE=?, _COPYS=?, _REAL_COUNT=?, _FIELD_FLASH_CARD=?, _TYPE=?, _TOTAL_FRAME=?" + ", _SNAP_PRINT=?") + " where _ID=? ";
            String[] updateValue = new String[]{value.GetSerialNumber(), value.GetPrintingTime(), value.GetUpLoader(), String.valueOf(value.GetMaskColor()), String.valueOf(value.GetPaperSize()), String.valueOf(value.GetCopys()), String.valueOf(value.GetRealCount()), String.valueOf(value.GetFlashCard()), String.valueOf(value.GetType()), String.valueOf(value.GetTotalFrame()), String.valueOf(value.GetSnapPrintFlag()), String.valueOf(value.GetID())};
            this.LOG.m385i("Update sql", "=" + sql);
            this.LOG.m385i("Update frame", "=" + value.GetTotalFrame());
            this.sqliteDatabase.execSQL(sql, updateValue);
            result = true;
            this.LOG.m384e("Update ID", String.valueOf(value.GetID()));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }
    }

    public int DeleteContactByID(long id) {
        this.LOG.m384e("Delete", String.valueOf(id));
        String[] strArr = new String[SERVER_VALUE_RESIN_TYPE_SILVER];
        strArr[SERVER_VALUE_RESIN_TYPE_NONE] = String.valueOf(id);
        return this.sqliteDatabase.delete(PrintingInfo.TABLE_NAME, "_ID=?", strArr);
    }

    public static int ChangeMaskValueForServer(int iMaskColorFromGVPI, boolean boMetalEnable) {
        if (!boMetalEnable) {
            return SERVER_VALUE_RESIN_TYPE_NONE;
        }
        if (iMaskColorFromGVPI == 0) {
            return SERVER_VALUE_RESIN_TYPE_SILVER;
        }
        if (iMaskColorFromGVPI == SERVER_VALUE_RESIN_TYPE_SILVER) {
            return SERVER_VALUE_RESIN_TYPE_GOLD;
        }
        return SERVER_VALUE_RESIN_TYPE_NONE;
    }

    public static int ChangeFlashCardValueForServer(boolean boFlashCardFromGVPI) {
        if (boFlashCardFromGVPI) {
            return SERVER_VALUE_RESIN_TYPE_SILVER;
        }
        return SERVER_VALUE_RESIN_TYPE_NONE;
    }
}
