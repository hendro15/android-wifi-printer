package com.example.sonic.hitiprinter.sql.oadc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OADCItemOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "oadc.db3";
    private static final String TAG = "Sample5-10";

    public OADCItemOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
        Log.d(TAG, "OADCItemOpenHelper");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Begin execute onCreate, cretating database table......");
        try {
            db.beginTransaction();
            String sql = "CREATE TABLE TBL_OADC(_ID INTEGER PRIMARY KEY, _OADC TEXT NOT NULL)";
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
    }
}
