package com.example.sonic.hitiprinter.sql.offlineaddownloadinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.sql.offlineadinfo.parser.ADItem;
import com.example.sonic.hitiprinter.sql.offlineadinfo.parser.OfflineADInfo;
import com.example.sonic.hitiprinter.sql.printerInfo.PrintingInfo;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.LogManager;

public class OfflineADDownloadItemUtility {
    private LogManager LOG;
    private OfflineADDownloadItemOpenHelper oADDIOpenHelper;
    private SQLiteDatabase sqliteDatabase;

    public OfflineADDownloadItemUtility(Context ctx) {
        this.LOG = null;
        this.LOG = new LogManager(0);
        this.oADDIOpenHelper = new OfflineADDownloadItemOpenHelper(ctx, 1);
        this.sqliteDatabase = this.oADDIOpenHelper.getWritableDatabase();
    }

    public void Close() {
        this.oADDIOpenHelper.close();
    }

    public ContentValues CreateContentValues(ADItem adItem) {
        ContentValues value = new ContentValues();
        try {
            value.put(OfflineADDownloadItem.FIELD_AD_ITEM_ID, adItem.GetADItemID());
            String GetADPriority = "NULL";
            if (adItem.GetADPriority().length() > 0) {
                GetADPriority = adItem.GetADPriority();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_PRIORITY, GetADPriority);
            String GetADStartingTime = "NULL";
            if (adItem.GetADStartingTime().length() > 0) {
                GetADStartingTime = adItem.GetADStartingTime();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_STARTING_TIME, GetADStartingTime);
            String GetADEndingTime = "NULL";
            if (adItem.GetADEndingTime().length() > 0) {
                GetADEndingTime = adItem.GetADEndingTime();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_ENDING_TIME, GetADEndingTime);
            String GetADVideoName = "NULL";
            if (adItem.GetADVideoName().length() > 0) {
                GetADVideoName = adItem.GetADVideoName();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_NAME, GetADVideoName);
            String GetADVideoPath = "NULL";
            if (adItem.GetADVideoPath().length() > 0) {
                GetADVideoPath = adItem.GetADVideoPath();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_PATH, GetADVideoPath);
            String GetADVideoSize = "NULL";
            if (adItem.GetADVideoSize().length() > 0) {
                GetADVideoSize = adItem.GetADVideoSize();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_SIZE, GetADVideoSize);
            String GetADVideoTime = "NULL";
            if (adItem.GetADVideoTime().length() > 0) {
                GetADVideoTime = adItem.GetADVideoTime();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_TIME, GetADVideoTime);
            String GetADPhotoName = "NULL";
            if (adItem.GetADPhotoName().length() > 0) {
                GetADPhotoName = adItem.GetADPhotoName();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_NAME, GetADPhotoName);
            String GetADPhotoPath = "NULL";
            if (adItem.GetADPhotoPath().length() > 0) {
                GetADPhotoPath = adItem.GetADPhotoPath();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_PATH, GetADPhotoPath);
            String GetADPhotoSize = "NULL";
            if (adItem.GetADPhotoSize().length() > 0) {
                GetADPhotoSize = adItem.GetADPhotoSize();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_SIZE, GetADPhotoSize);
            String GetADPhotoTime = "NULL";
            if (adItem.GetADPhotoTime().length() > 0) {
                GetADPhotoTime = adItem.GetADPhotoTime();
            }
            value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_TIME, GetADPhotoTime);
            value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_FILE_PATH, "NULL");
            value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_FILE_PATH, "NULL");
            value.put(OfflineADDownloadItem.FIELD_AD_SHOW_COUNT, String.valueOf(0));
            value.put(OfflineADDownloadItem.FIELD_AD_DOWNLOAD_STATUS, String.valueOf(0));
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean ReflashDBByXML(OfflineADInfo offlineADInfo) {
        int i;
        ADItem adi;
        Cursor c = GetOADDICursor();
        if (c.moveToFirst()) {
            do {
                boolean boExclusive = false;
                OfflineADDownloadItem oaddi = GetOADDIFromCursor(c);
                for (i = 0; i < offlineADInfo.GetNumber(); i++) {
                    adi = offlineADInfo.GetADItem(i);
                    if (adi.GetADItemID().equals(oaddi.GetAD_Item_ID())) {
                        adi.SetInDB(true);
                        boExclusive = true;
                        oaddi.SetAD_Priority(adi.GetADPriority());
                        oaddi.SetAD_Show_Count(0);
                        UpdateOADDI(oaddi);
                    }
                }
                if (!boExclusive) {
                    DeleteOADDIRescouces(oaddi);
                    DeleteOADDIByID((long) oaddi.GetID());
                }
            } while (c.moveToNext());
        }
        c.close();
        for (i = 0; i < offlineADInfo.GetNumber(); i++) {
            adi = offlineADInfo.GetADItem(i);
            if (adi.GetInDB()) {
                this.LOG.m384e("ReflashDBByXML", "HAVE");
            } else {
                InsertOADDI(CreateContentValues(adi));
                adi.SetInDB(true);
            }
        }
        return true;
    }

    private void SetOADDIFromCursor(Cursor c, OfflineADDownloadItem oADDI) {
        oADDI.SetID(c.getInt(c.getColumnIndex(PrintingInfo.FIELD_ID)));
        oADDI.SetAD_Item_ID(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ITEM_ID)));
        oADDI.SetAD_Priority(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PRIORITY)));
        oADDI.SetAD_Starting_Time(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_STARTING_TIME)));
        oADDI.SetAD_Ending_Time(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ENDING_TIME)));
        oADDI.SetAD_Video_Name(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_NAME)));
        oADDI.SetAD_Video_Path(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_PATH)));
        oADDI.SetAD_Video_File_Path(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_FILE_PATH)));
        oADDI.SetAD_Video_Size(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_SIZE)));
        oADDI.SetAD_Video_Time(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_TIME)));
        oADDI.SetAD_Photo_Name(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_NAME)));
        oADDI.SetAD_Photo_Path(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_PATH)));
        oADDI.SetAD_Photo_File_Path(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_FILE_PATH)));
        oADDI.SetAD_Photo_Size(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_SIZE)));
        oADDI.SetAD_Photo_Time(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_TIME)));
        oADDI.SetAD_Show_Count(c.getInt(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_SHOW_COUNT)));
        oADDI.SetAD_DownloadStatus(c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_DOWNLOAD_STATUS)));
    }

    public Cursor GetOADDICursor() {
        return this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
    }

    public Cursor GetOADDICursor(String strOrder) {
        return this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, null, null, null, null, strOrder);
    }

    public Cursor GetShowOADDICursor(String strTimeStamp) {
        if (strTimeStamp == null) {
            return null;
        }
        return this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, "_AD_DOWNLOAD_STATUS=? AND _AD_STARTING_TIME<=? AND _AD_ENDING_TIME>=?", new String[]{String.valueOf(1), strTimeStamp, strTimeStamp}, null, null, OfflineADDownloadItem.FIELD_AD_SHOW_COUNT);
    }

    public OfflineADDownloadItem GetOADDIById(long id) {
        Cursor c = this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, "_ID=?", new String[]{String.valueOf(id)}, null, null, null);
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c.moveToFirst()) {
            SetOADDIFromCursor(c, oADDI);
        }
        c.close();
        return oADDI;
    }

    public OfflineADDownloadItem GetOADDIByAD_Item_ID(long AD_Item_ID) {
        Cursor c = this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, "_AD_ITEM_ID=?", new String[]{String.valueOf(AD_Item_ID)}, null, null, null);
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c.moveToFirst()) {
            SetOADDIFromCursor(c, oADDI);
        }
        c.close();
        return oADDI;
    }

    public OfflineADDownloadItem GetNextNotDownloadOADDI() {
        Cursor c = this.sqliteDatabase.rawQuery("SELECT * FROM TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO WHERE _AD_DOWNLOAD_STATUS = ?ORDER BY _AD_PRIORITY ASC", new String[]{String.valueOf(0)});
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c.moveToFirst()) {
            this.LOG.m384e("GetNextNotDownloadOADDI", "Get");
            SetOADDIFromCursor(c, oADDI);
        }
        c.close();
        return oADDI;
    }

    private OfflineADDownloadItem GetNextShowOADDI() {
        Cursor c = this.sqliteDatabase.rawQuery("SELECT * FROM TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO WHERE _AD_DOWNLOAD_STATUS = ? ORDER BY _AD_SHOW_COUNT ASC", new String[]{String.valueOf(1)});
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c.moveToFirst()) {
            SetOADDIFromCursor(c, oADDI);
            this.LOG.m384e("GetNextShowOADDI", String.valueOf(oADDI.GetID()));
        } else {
            this.LOG.m384e("GetNextShowOADDI", "No");
        }
        c.close();
        return oADDI;
    }

    public OfflineADDownloadItem GetOADDIFirst() {
        Cursor c = this.sqliteDatabase.query(OfflineADDownloadItem.TABLE_NAME, null, null, null, null, null, PrintingInfo.FIELD_ID);
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c.moveToFirst()) {
            SetOADDIFromCursor(c, oADDI);
        }
        c.close();
        return oADDI;
    }

    public OfflineADDownloadItem GetOADDIFromCursor(Cursor c) {
        OfflineADDownloadItem oADDI = new OfflineADDownloadItem();
        if (c != null) {
            SetOADDIFromCursor(c, oADDI);
        }
        return oADDI;
    }

    public void PrintOADDI() {
        Cursor c = GetOADDICursor();
        if (c.moveToFirst()) {
            do {
                this.LOG.m384e("PrintOADDI Current Record", "01.ID: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_ID)) + "\n" + "02.AD Item ID: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ITEM_ID)) + "\n" + "03.AD Priority: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PRIORITY)) + "\n" + "04.AD Starting Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_STARTING_TIME)) + "\n" + "05.AD Ending Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ENDING_TIME)) + "\n" + "06.AD Video Name: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_NAME)) + "\n" + "07.AD Video Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_PATH)) + "\n" + "08.AD Video File Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_FILE_PATH)) + "\n" + "09.AD Video Size: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_SIZE)) + "\n" + "10.AD Video Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_TIME)) + "\n" + "11.AD Photo Name: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_NAME)) + "\n" + "12.AD Photo Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_PATH)) + "\n" + "13.AD Photo File Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_FILE_PATH)) + "\n" + "14.AD Photo Size: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_SIZE)) + "\n" + "15.AD Photo Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_TIME)) + "\n" + "16.AD Show Count: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_SHOW_COUNT)) + "\n" + "17.AD Download Status: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_DOWNLOAD_STATUS)));
            } while (c.moveToNext());
        } else {
            this.LOG.m384e("PrintOADDI Current Record", OADCItem.WATCH_TYPE_NON);
        }
        c.close();
    }

    public void PrintOADDI(String strOrder) {
        Cursor c = GetOADDICursor(strOrder);
        if (c.moveToFirst()) {
            do {
                this.LOG.m384e("PrintOADDI Current Record" + strOrder, "01.ID: " + c.getString(c.getColumnIndex(PrintingInfo.FIELD_ID)) + "\n" + "02.AD Item ID: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ITEM_ID)) + "\n" + "03.AD Priority: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PRIORITY)) + "\n" + "04.AD Starting Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_STARTING_TIME)) + "\n" + "05.AD Ending Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_ENDING_TIME)) + "\n" + "06.AD Video Name: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_NAME)) + "\n" + "07.AD Video Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_PATH)) + "\n" + "08.AD Video File Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_FILE_PATH)) + "\n" + "09.AD Video Size: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_SIZE)) + "\n" + "10.AD Video Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_VIDEO_TIME)) + "\n" + "11.AD Photo Name: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_NAME)) + "\n" + "12.AD Photo Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_PATH)) + "\n" + "13.AD Photo File Path: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_FILE_PATH)) + "\n" + "14.AD Photo Size: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_SIZE)) + "\n" + "15.AD Photo Time: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_PHOTO_TIME)) + "\n" + "16.AD Show Count: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_SHOW_COUNT)) + "\n" + "17.AD Download Status: " + c.getString(c.getColumnIndex(OfflineADDownloadItem.FIELD_AD_DOWNLOAD_STATUS)));
            } while (c.moveToNext());
        } else {
            this.LOG.m384e("PrintOADDI Current Record " + strOrder, OADCItem.WATCH_TYPE_NON);
        }
        c.close();
    }

    public int GetShowOADDISize(String strTimeStamp) {
        Cursor c = this.sqliteDatabase.rawQuery("SELECT COUNT(*) FROM TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO WHERE _AD_DOWNLOAD_STATUS = ? AND _AD_STARTING_TIME <= ? AND _AD_ENDING_TIME >= ?", new String[]{String.valueOf(1), strTimeStamp, strTimeStamp});
        if (c == null) {
            return 0;
        }
        if (c.moveToFirst()) {
            int iCount = c.getInt(0);
            c.close();
            return iCount;
        }
        c.close();
        return 0;
    }

    public long InsertOADDI(String strAD_Item_ID, String strAD_Priority, String strAD_Starting_Time, String strAD_Ending_Time, String strAD_Video_Name, String strAD_Video_Path, String strAD_Video_File_Path, String strAD_Video_Size, String strAD_Video_Time, String strAD_Photo_Name, String strAD_Photo_Path, String strAD_Photo_File_Path, String strAD_Photo_Size, String strAD_Photo_Time, String strAD_Show_Count, String strAD_DownloadStatus) {
        if (strAD_Item_ID == null) {
            strAD_Item_ID = "NULL";
        }
        if (strAD_Priority == null) {
            strAD_Priority = "NULL";
        }
        if (strAD_Starting_Time == null) {
            strAD_Starting_Time = "NULL";
        }
        if (strAD_Ending_Time == null) {
            strAD_Ending_Time = "NULL";
        }
        if (strAD_Video_Name == null) {
            strAD_Video_Name = "NULL";
        }
        if (strAD_Video_Path == null) {
            strAD_Video_Path = "NULL";
        }
        if (strAD_Video_File_Path == null) {
            strAD_Video_File_Path = "NULL";
        }
        if (strAD_Video_Size == null) {
            strAD_Video_Size = "NULL";
        }
        if (strAD_Video_Time == null) {
            strAD_Video_Time = "NULL";
        }
        if (strAD_Photo_Name == null) {
            strAD_Photo_Name = "NULL";
        }
        if (strAD_Photo_Path == null) {
            strAD_Photo_Path = "NULL";
        }
        if (strAD_Photo_File_Path == null) {
            strAD_Photo_File_Path = "NULL";
        }
        if (strAD_Photo_Size == null) {
            strAD_Photo_Size = "NULL";
        }
        if (strAD_Photo_Time == null) {
            strAD_Photo_Time = "NULL";
        }
        if (strAD_Show_Count == null) {
            strAD_Show_Count = String.valueOf(0);
        }
        if (strAD_DownloadStatus == null) {
            strAD_DownloadStatus = String.valueOf(0);
        }
        ContentValues value = new ContentValues();
        value.put(OfflineADDownloadItem.FIELD_AD_ITEM_ID, strAD_Item_ID);
        value.put(OfflineADDownloadItem.FIELD_AD_PRIORITY, strAD_Priority);
        value.put(OfflineADDownloadItem.FIELD_AD_STARTING_TIME, strAD_Starting_Time);
        value.put(OfflineADDownloadItem.FIELD_AD_ENDING_TIME, strAD_Ending_Time);
        value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_NAME, strAD_Video_Name);
        value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_PATH, strAD_Video_Path);
        value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_FILE_PATH, strAD_Video_File_Path);
        value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_SIZE, strAD_Video_Size);
        value.put(OfflineADDownloadItem.FIELD_AD_VIDEO_TIME, strAD_Video_Time);
        value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_NAME, strAD_Photo_Name);
        value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_PATH, strAD_Photo_Path);
        value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_FILE_PATH, strAD_Photo_File_Path);
        value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_SIZE, strAD_Photo_Size);
        value.put(OfflineADDownloadItem.FIELD_AD_PHOTO_TIME, strAD_Photo_Time);
        value.put(OfflineADDownloadItem.FIELD_AD_SHOW_COUNT, strAD_Show_Count);
        value.put(OfflineADDownloadItem.FIELD_AD_DOWNLOAD_STATUS, strAD_DownloadStatus);
        return InsertOADDI(value);
    }

    public long InsertOADDI(ContentValues value) {
        if (value == null) {
            return -1;
        }
        long result = this.sqliteDatabase.insert(OfflineADDownloadItem.TABLE_NAME, PrintingInfo.FIELD_ID, value);
        this.LOG.m384e("Insert", String.valueOf(result));
        return result;
    }

    public boolean UpdateOADDI(int iID, String strAD_Item_ID, String strAD_Priority, String strAD_Starting_Time, String strAD_Ending_Time, String strAD_Video_Name, String strAD_Video_Path, String strAD_Video_File_Path, String strAD_Video_Size, String strAD_Video_Time, String strAD_Photo_Name, String strAD_Photo_Path, String strAD_Photo_File_Path, String strAD_Photo_Size, String strAD_Photo_Time, String strAD_Show_Count, String strAD_DownloadStatus) {
        OfflineADDownloadItem value = GetOADDIById((long) iID);
        value.SetID(iID);
        if (strAD_Item_ID != null) {
            value.SetAD_Item_ID(strAD_Item_ID);
        }
        if (strAD_Priority != null) {
            value.SetAD_Priority(strAD_Priority);
        }
        if (strAD_Starting_Time != null) {
            value.SetAD_Starting_Time(strAD_Starting_Time);
        }
        if (strAD_Ending_Time != null) {
            value.SetAD_Ending_Time(strAD_Ending_Time);
        }
        if (strAD_Video_Name != null) {
            value.SetAD_Video_Name(strAD_Video_Name);
        }
        if (strAD_Video_Path != null) {
            value.SetAD_Video_Path(strAD_Video_Path);
        }
        if (strAD_Video_File_Path != null) {
            value.SetAD_Video_File_Path(strAD_Video_File_Path);
        }
        if (strAD_Video_Size != null) {
            value.SetAD_Video_Size(strAD_Video_Size);
        }
        if (strAD_Video_Time != null) {
            value.SetAD_Video_Time(strAD_Video_Time);
        }
        if (strAD_Photo_Name != null) {
            value.SetAD_Photo_Name(strAD_Photo_Name);
        }
        if (strAD_Photo_Path != null) {
            value.SetAD_Photo_Path(strAD_Photo_Path);
        }
        if (strAD_Photo_File_Path != null) {
            value.SetAD_Photo_File_Path(strAD_Photo_File_Path);
        }
        if (strAD_Photo_Size != null) {
            value.SetAD_Photo_Size(strAD_Photo_Size);
        }
        if (strAD_Photo_Time != null) {
            value.SetAD_Photo_Time(strAD_Photo_Time);
        }
        if (strAD_Show_Count != null) {
            value.SetAD_Show_Count(Integer.valueOf(strAD_Show_Count).intValue());
        }
        if (strAD_DownloadStatus != null) {
            value.SetAD_DownloadStatus(strAD_DownloadStatus);
        }
        return UpdateOADDI(value);
    }

    public boolean UpdateOADDI(OfflineADDownloadItem value) {
        boolean result = false;
        try {
            this.sqliteDatabase.execSQL("update TBL_OFFLINE_AD_DOWNLOAD_INFO_INFO set _AD_ITEM_ID=?, _AD_PRIORITY=?, _AD_STARTING_TIME=?, _AD_ENDING_TIME=?, _AD_VIDEO_NAME=?, _AD_VIDEO_PATH=?, _AD_VIDEO_FILE_PATH=?, _AD_VIDEO_SIZE=?, _AD_VIDEO_TIME=?, _AD_PHOTO_NAME=?, _AD_PHOTO_PATH=?, _AD_PHOTO_FILE_PATH=?, _AD_PHOTO_SIZE=?, _AD_PHOTO_TIME=?, _AD_SHOW_COUNT=?, _AD_DOWNLOAD_STATUS=? where _ID=?", new String[]{value.GetAD_Item_ID(), value.GetAD_Priority(), value.GetAD_Starting_Time(), value.GetAD_Ending_Time(), value.GetAD_Video_Name(), value.GetAD_Video_Path(), value.GetAD_Video_File_Path(), value.GetAD_Video_Size(), value.GetAD_Video_Time(), value.GetAD_Photo_Name(), value.GetAD_Photo_Path(), value.GetAD_Photo_File_Path(), value.GetAD_Photo_Size(), value.GetAD_Photo_Time(), String.valueOf(value.GetAD_Show_Count()), value.GetAD_DownloadStatus(), String.valueOf(value.GetID())});
            result = true;
            this.LOG.m384e("UpdateOADDI", String.valueOf(value.GetID()));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return result;
        }
    }

    public int DeleteOADDIByID(long id) {
        this.LOG.m384e("Delete", String.valueOf(id));
        return this.sqliteDatabase.delete(OfflineADDownloadItem.TABLE_NAME, "_ID=?", new String[]{String.valueOf(id)});
    }

    public int DeleteAll() {
        this.LOG.m384e("Delete", "All");
        return this.sqliteDatabase.delete(OfflineADDownloadItem.TABLE_NAME, null, null);
    }

    public void DeleteOADDIRescouces(OfflineADDownloadItem oaddi) {
        if (!oaddi.GetAD_Video_File_Path().equals("NULL")) {
            FileUtility.DeleteFile(oaddi.GetAD_Video_File_Path());
        }
        if (!oaddi.GetAD_Photo_File_Path().equals("NULL")) {
            FileUtility.DeleteFile(oaddi.GetAD_Photo_File_Path());
        }
    }

    public void TestUpdate() {
        Cursor c = GetOADDICursor();
        if (c.moveToFirst()) {
            do {
                UpdateOADDI(GetOADDIFromCursor(c).GetID(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
            } while (c.moveToNext());
        }
        c.close();
    }
}
