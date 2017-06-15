package com.example.sonic.hitiprinter.sql.offlineaddownloadinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OfflineADDownloadItemOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "offlineaddownloadinfo.db3";
    private static final String TAG = "Sample5-10";

    public OfflineADDownloadItemOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
        Log.d(TAG, "OfflineADDownloadInfoOpenHelper");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Begin execute onCreate, cretating database table......");
        try {
            db.beginTransaction();
            String sql = "CREATE TABLE TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO(_ID INTEGER PRIMARY KEY, _AD_ITEM_ID TEXT NOT NULL, _AD_PRIORITY TEXT NOT NULL, _AD_STARTING_TIME TEXT NOT NULL, _AD_ENDING_TIME TEXT NOT NULL, _AD_VIDEO_NAME TEXT NOT NULL, _AD_VIDEO_PATH TEXT NOT NULL, _AD_VIDEO_FILE_PATH TEXT NOT NULL, _AD_VIDEO_SIZE TEXT NOT NULL, _AD_VIDEO_TIME TEXT NOT NULL, _AD_PHOTO_NAME TEXT NOT NULL, _AD_PHOTO_PATH TEXT NOT NULL, _AD_PHOTO_FILE_PATH TEXT NOT NULL, _AD_PHOTO_SIZE TEXT NOT NULL, _AD_PHOTO_TIME TEXT NOT NULL, _AD_SHOW_COUNT INT  NOT NULL, _AD_DOWNLOAD_STATUS TEXT NOT NULL)";
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
