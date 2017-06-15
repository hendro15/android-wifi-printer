package com.example.sonic.hitiprinter.utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.util.Pair;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.GalleryViewHolder;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MediaUtil {

    /* renamed from: com.hiti.utility.MediaUtil.1 */
    static class C04491 implements OnScanCompletedListener {
        C04491() {
        }

        public void onScanCompleted(String path, Uri uri) {
            Log.d("onScanCompleted", "!!!!!! " + path);
        }
    }

    private static Uri MakeMedia(Context context, String strImagePath) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("_data", strImagePath);
        return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public static Pair<Uri, String> PrepareMedia(Context context, String strAlbumFolder) {
        String m_TmpFilePath = (Environment.getExternalStorageDirectory().getPath() + File.separator + strAlbumFolder) + File.separator + FileUtility.GetNewNameWithExt(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG, PringoConvenientConst.NEW_FILE_NAME_IMG);
        return new Pair(MakeMedia(context, m_TmpFilePath), m_TmpFilePath);
    }

    public static Intent OpenCameraIntent(Uri photoUri, String m_TmpFilePath) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", photoUri);
        return intent;
    }

    public static void UpdateMedia(Context context, String strImagePath, long ImageID) {
        ContentValues updateValues = new ContentValues();
        File updateFile = new File(strImagePath);
        updateValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        updateValues.put("_size", Long.valueOf(updateFile.length()));
        context.getContentResolver().update(Media.EXTERNAL_CONTENT_URI, updateValues, "_id=?", new String[]{String.valueOf(ImageID)});
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new C04491());
    }

    public static Uri SaveIntoMedia(Context context, String strImagePath) {
        ContentValues contentValues = new ContentValues();
        File imageFile = new File(strImagePath);
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        contentValues.put("_data", strImagePath);
        contentValues.put("_size", Long.valueOf(imageFile.length()));
        return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public static void DeleteMedia(Context context, String m_TmpFilePath, long m_TmpFileID) {
        if (m_TmpFilePath != null) {
            FileUtility.DeleteFile(m_TmpFilePath);
        }
        if (m_TmpFileID != 0) {
            context.getContentResolver().delete(Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{String.valueOf(m_TmpFileID)});
        }
    }

    public static String GetAlbumID(Context context, String strAlbumName) {
        String[] projection = new String[]{"bucket_id"};
        String[] where = new String[]{String.valueOf(strAlbumName)};
        Cursor cursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, "bucket_display_name = ? ", where, null);
        int bucketIDColumnIndex = -1;
        if (cursor.moveToFirst()) {
            bucketIDColumnIndex = cursor.getColumnIndex("bucket_id");
        }
        if (bucketIDColumnIndex != -1) {
            return cursor.getString(bucketIDColumnIndex);
        }
        return null;
    }

    public static String GetPhotoCreatedTime(Context context, long photoId, String strPath, String timeFormat) {
        String dateTime = null;
        long createdAtTime = 0;
        Log.v("GetPhotoCreatedTime", "photoId: " + photoId + "\n path: " + strPath);
        try {
            dateTime = new ExifInterface(strPath).getAttribute("DateTime");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("GetPhotoCreatedTime", "GetPhoto exif IOException: " + e.toString());
        }
        Log.v("GetPhotoCreatedTime", "GetPhoto exif dateTime: " + dateTime);
        if (dateTime == null) {
            if (photoId == 0) {
                return null;
            }
            String[] projection = new String[]{"datetaken"};
            String[] where = new String[]{String.valueOf(photoId)};
            Cursor cursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, projection, "_id = ? ", where, null);
            int createdAtTimeIndex = -1;
            if (cursor.moveToFirst()) {
                createdAtTimeIndex = cursor.getColumnIndex(projection[0]);
            }
            if (createdAtTimeIndex != -1) {
                createdAtTime = cursor.getLong(createdAtTimeIndex);
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat, Locale.US);
        if (dateTime != null) {
            try {
                String date = simpleDateFormat.format(new SimpleDateFormat("yyyy:MM:dd hh:mm:ss", Locale.US).parse(dateTime));
                Log.v("GetPhotoCreatedTime", "format exif dateTime: " + date);
                return date;
            } catch (ParseException e2) {
                e2.printStackTrace();
                return null;
            }
        } else if (createdAtTime <= 0) {
            return null;
        } else {
            return simpleDateFormat.format(new Date(createdAtTime));
        }
    }

    public static void ImageQualityCheck(Context context, GalleryViewHolder holder, ArrayList<Long> lIsLawQtyList, ArrayList<String> photoPathList, ArrayList<Long> photoIDList, int iMaxWidth, int iMaxHeight) {
        boolean bLow = BitmapMonitor.IsPhotoLowQuality(context, Uri.parse("file://" + ((String) photoPathList.get(holder.m_iID))), iMaxWidth, iMaxHeight);
        long lPhotoId = ((Long) photoIDList.get(holder.m_iID)).longValue();
        if (bLow && !lIsLawQtyList.contains(Long.valueOf(lPhotoId))) {
            lIsLawQtyList.add(Long.valueOf(lPhotoId));
            holder.m_QtyView.setVisibility(0);
        }
    }
}
