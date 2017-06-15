package com.example.sonic.hitiprinter.sql.printerInfo;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PrinterInfoOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
    public static final String DB_NAME = "printinginfo.db3";
    private static final String TAG = "Sample5-10";

    public PrinterInfoOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
        Log.d(TAG, "PrinterInfoOpenHelper");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Begin execute onCreate!!!, cretating database table v.5");
        try {
            db.beginTransaction();
            String sql = ("CREATE TABLE TBL_PRINTING_INFO(_ID INTEGER PRIMARY KEY, _SERIAL_NUMBER TEXT NOT NULL, _PRINTING_TIME TEXT NOT NULL, _PRINTING_UPLOADER TEXT NOT NULL, _MASK_COLOR INT  NOT NULL, _PAPER_SIZE INT  NOT NULL, _COPYS TEXT NOT NULL, _REAL_COUNT TEXT NOT NULL, _FIELD_FLASH_CARD INT  NOT NULL, _TYPE INT  NOT NULL, _TOTAL_FRAME INT  NOT NULL" + ", _SNAP_PRINT INT  NOT NULL") + ")";
            Log.d(TAG, "Create Table " + sql);
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        Log.d(TAG, "Execute onCreate completed. Database created success.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("!!!!onUpgrade~~~", "oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        if (newVersion > oldVersion) {
            db.beginTransaction();
            if (oldVersion < 4) {
                try {
                    AlterTableToAddColumn(db, PrintingInfo.FIELD_TOTAL_FRAME);
                } catch (SQLException e) {
                    Log.d("onUpgrade sql error_v=" + oldVersion, e.toString());
                    e.printStackTrace();
                }
            }
            if (oldVersion < DATABASE_VERSION) {
                AlterTableToAddColumn(db, PrintingInfo.FIELD_SNAP_PRINT);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            return;
        }
        onCreate(db);
    }

    private void AlterTableToAddColumn(SQLiteDatabase db, String strColumnName) {
        int iColumnValue = db.rawQuery("SELECT * FROM TBL_PRINTING_INFO", null).getColumnIndex(String.valueOf(strColumnName));
        if (iColumnValue == -1) {
            db.execSQL("ALTER TABLE TBL_PRINTING_INFO ADD " + String.valueOf(strColumnName) + " INT DEFAULT 0");
        }
        Log.d("Add Collumn:" + String.valueOf(strColumnName), ":" + iColumnValue);
    }
}
