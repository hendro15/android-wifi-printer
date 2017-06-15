package com.example.sonic.hitiprinter.sql.oadc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sonic.hitiprinter.jni.hello.Hello;
import com.example.sonic.hitiprinter.sql.printerInfo.PrintingInfo;
import com.example.sonic.hitiprinter.utility.EncryptAndDecryptAES;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.PringoConvenientConst;

public class OADCItemUtility {
    private LogManager LOG;
    private OADCItemOpenHelper oadcItemOpenHelper;
    private SQLiteDatabase sqliteDatabase;

    public OADCItemUtility(Context ctx) {
        this.LOG = null;
        this.LOG = new LogManager(0);
        this.oadcItemOpenHelper = new OADCItemOpenHelper(ctx, 1);
        this.sqliteDatabase = this.oadcItemOpenHelper.getWritableDatabase();
    }

    public void Close() {
        this.oadcItemOpenHelper.close();
    }

    private void SetOADCFromCursor(Cursor c, OADCItem oadc) {
        oadc.SetID(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID)));
        oadc.SetOADC(c.getString(c.getColumnIndex(OADCItem.FIELD_OADC)));
    }

    public Cursor GetOADCCursor() {
        return this.sqliteDatabase.query(OADCItem.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
    }

    public Cursor GetOADCCursor(String strOrder) {
        return this.sqliteDatabase.query(OADCItem.TABLE_NAME, null, null, null, null, null, strOrder);
    }

    public OADCItem GetOADCById(long id) {
        Cursor c = this.sqliteDatabase.query(OADCItem.TABLE_NAME, null, "_ID=?", new String[]{String.valueOf(id)}, null, null, null);
        OADCItem oadc = new OADCItem();
        if (c.moveToFirst()) {
            SetOADCFromCursor(c, oadc);
        }
        c.close();
        return oadc;
    }

    public OADCItem GetOADCFirst() {
        Cursor c = this.sqliteDatabase.query(OADCItem.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
        OADCItem oadc = new OADCItem();
        if (c.moveToFirst()) {
            SetOADCFromCursor(c, oadc);
        }
        c.close();
        return oadc;
    }

    public OADCItem GetOADCFromCursor(Cursor c) {
        OADCItem oadc = new OADCItem();
        if (c != null) {
            SetOADCFromCursor(c, oadc);
        }
        return oadc;
    }

    public void PrintOADC() {
        Cursor c = GetOADCCursor();
        if (c.moveToFirst()) {
            do {
                this.LOG.m384e("OADC Current Record", "01.ID: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_ID)) + "\n" + "02.OADC: " + c.getString(c.getColumnIndex(OADCItem.FIELD_OADC)) + "\n");
            } while (c.moveToNext());
        } else {
            this.LOG.m384e("OADC Current Record", OADCItem.WATCH_TYPE_NON);
        }
        c.close();
    }

    public void PrintOADC(String strOrder) {
        Cursor c = GetOADCCursor(strOrder);
        if (c.moveToFirst()) {
            do {
                this.LOG.m384e("PrintOADDI Current Record" + strOrder, "01.ID: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_ID)) + "\n" + "02.OADC: " + c.getString(c.getColumnIndex(OADCItem.FIELD_OADC)) + "\n");
            } while (c.moveToNext());
        } else {
            this.LOG.m384e("PrintOADDI Current Record " + strOrder, OADCItem.WATCH_TYPE_NON);
        }
        c.close();
    }

    public int GetOADCSize() {
        Cursor c = this.sqliteDatabase.rawQuery("SELECT COUNT(*) FROM TBL_OADC", null);
        if (c == null) {
            this.LOG.m384e("GetOADCSize", OADCItem.WATCH_TYPE_NON);
            return 0;
        } else if (c.moveToFirst()) {
            int iCount = c.getInt(0);
            c.close();
            this.LOG.m384e("GetOADCSize", String.valueOf(iCount));
            return iCount;
        } else {
            this.LOG.m384e("GetOADCSize", OADCItem.WATCH_TYPE_NON);
            c.close();
            return 0;
        }
    }

    public long InsertOADC(String strOADC) {
        if (strOADC == null) {
            strOADC = "NULL";
        }
        ContentValues value = new ContentValues();
        value.put(OADCItem.FIELD_OADC, strOADC);
        return InsertOADC(value);
    }

    public long InsertOADC(ContentValues value) {
        long result = this.sqliteDatabase.insert(OADCItem.TABLE_NAME, PrintingInfo.FIELD_ID, value);
        this.LOG.m384e("Insert", String.valueOf(result));
        return result;
    }

    public boolean UpdateOADC(int iID, String strOADC) {
        OADCItem value = GetOADCById((long) iID);
        value.SetID(iID);
        if (strOADC != null) {
            value.SetOADC(strOADC);
        }
        return UpdateOADC(value);
    }

    public boolean UpdateOADC(OADCItem value) {
        boolean result = false;
        try {
            this.sqliteDatabase.execSQL("update TBL_OADC set _OADC=? where _ID=?", new String[]{value.GetOADC(), String.valueOf(value.GetID())});
            result = true;
            this.LOG.m384e("Update", String.valueOf(value.GetID()));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }
    }

    public int DeleteOADCByID(long id) {
        this.LOG.m384e("Delete", String.valueOf(id));
        return this.sqliteDatabase.delete(OADCItem.TABLE_NAME, "_ID=?", new String[]{String.valueOf(id)});
    }

    public int DeleteAll() {
        this.LOG.m384e("Delete", "All");
        return this.sqliteDatabase.delete(OADCItem.TABLE_NAME, null, null);
    }

    public Long InsertOADC(Context context, String strADID, String strTimeStamp, String strUser, String strWatchStatus, String strCountry) {
        return Long.valueOf(InsertOADC(EncryptAndDecryptAES.EncryptStr(strADID + PringoConvenientConst.DATE_TO_DATE + strTimeStamp + PringoConvenientConst.DATE_TO_DATE + strUser + PringoConvenientConst.DATE_TO_DATE + strWatchStatus + PringoConvenientConst.DATE_TO_DATE + strCountry + PringoConvenientConst.DATE_TO_DATE, Hello.SayGoodBye(context, 3331), Hello.SayHello(context, 3331))));
    }

    public boolean UpdateOADC(Context context, int id, String strADID, String strTimeStamp, String strUser, String strWatchStatus, String strCountry) {
        return UpdateOADC(id, EncryptAndDecryptAES.EncryptStr(strADID + PringoConvenientConst.DATE_TO_DATE + strTimeStamp + PringoConvenientConst.DATE_TO_DATE + strUser + PringoConvenientConst.DATE_TO_DATE + strWatchStatus + PringoConvenientConst.DATE_TO_DATE + strCountry + PringoConvenientConst.DATE_TO_DATE, Hello.SayGoodBye(context, 3331), Hello.SayHello(context, 3331)));
    }
}
