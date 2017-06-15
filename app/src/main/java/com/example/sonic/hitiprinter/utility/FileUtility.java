package com.example.sonic.hitiprinter.utility;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.sonic.hitiprinter.net.bither.util.NativeUtil;
import org.apache.commons.net.io.Util;
import org.xmlpull.v1.XmlPullParser;

public class FileUtility {

    /* renamed from: com.hiti.utility.FileUtility.1 */
    static class C04481 implements OnScanCompletedListener {
        C04481() {
        }

        public void onScanCompleted(String path, Uri uri) {
            Log.i("SavePhoto", "Scanned " + path + ":");
            Log.i("SavePhoto", "-> uri=" + uri);
        }
    }

    public static boolean SDCardState() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static String GetFileName(String strPath) {
        strPath = strPath.replace("\\", "/");
        return strPath.substring(strPath.lastIndexOf("/") + 1);
    }

    public static String GetFileNameWithoutExt(String strPath) {
        strPath = strPath.replace("\\", "/");
        String strName = strPath.substring(strPath.lastIndexOf("/") + 1);
        return strName.substring(0, strName.lastIndexOf("."));
    }

    public static String GetFileExt(String strFileName) {
        int iStart = strFileName.lastIndexOf(".");
        String ExtString = PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG;
        if (iStart != -1) {
            return strFileName.substring(iStart, strFileName.length());
        }
        return ExtString;
    }

    public static String GetNewName(String strFileName, String strPostfix) {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(System.currentTimeMillis())) + strPostfix + GetFileExt(strFileName);
    }

    public static String GetNewNameWithExt(String strFileExt, String strPostfix) {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(System.currentTimeMillis())) + strPostfix + strFileExt;
    }

    public static String ChangeFileExt(String strFileName, String strfileExt) {
        return strFileName.replace(GetFileExt(strFileName), strfileExt);
    }

    public static boolean FileExist(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return new File(strPath).exists();
        }
        return false;
    }

    public static String GetFolderName(String strPath) {
        strPath = strPath.replace("\\", "/");
        String strRemoveNamePath = strPath.replace("/" + GetFileName(strPath), XmlPullParser.NO_NAMESPACE);
        return strRemoveNamePath.substring(strRemoveNamePath.lastIndexOf("/") + 1);
    }

    public static String GetFolderFullPath(String strPath) {
        strPath = strPath.replace("\\", "/");
        return strPath.replace("/" + GetFileName(strPath), XmlPullParser.NO_NAMESPACE);
    }

    public static void CreateFolder(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File dirFile = new File(strPath);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
        }
    }

    public static void CreateFolders(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File dirFile = new File(strPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        }
    }

    public static void DeleteALLFolder(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File dirFile = new File(strPath);
            if (dirFile.exists()) {
                deleteDir(dirFile);
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null) {
                return true;
            }
            for (String file : children) {
                if (!deleteDir(new File(dir, file))) {
                    return false;
                }
            }
        }
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);
        return to.delete();
    }

    public static String ReNameFile(String strOldPath, String strNewName) {
        new File(strOldPath).renameTo(new File(GetFolderFullPath(strOldPath) + "/" + strNewName));
        return GetFolderFullPath(strOldPath) + "/" + strNewName;
    }

    public static boolean ReFullPathFile(String strOldPath, String strNewName) {
        return new File(strOldPath).renameTo(new File(strNewName));
    }

    public static void DeleteFolder(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File dirFile = new File(strPath);
            if (dirFile != null && dirFile.exists()) {
                for (String strFile : dirFile.list()) {
                    new File(strPath + "/" + strFile).delete();
                }
            }
        }
    }

    public static void DeleteFile(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(strPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void CopyDirectory(String strSourceLocation, String strTargetLocation) throws IOException {
        CopyDirectory(new File(strSourceLocation), new File(strTargetLocation));
    }

    private static void CopyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                CopyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
            }
            return;
        }
        InputStream in = new FileInputStream(sourceLocation);
        OutputStream out = new FileOutputStream(targetLocation);
        byte[] buf = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public static void WriteFile(String strFilePath, String strContent) {
        FileWriter fileWriter;
        IOException e;
        try {
            FileWriter writer = new FileWriter(new File(strFilePath));
            try {
                writer.write(strContent);
                writer.close();
                fileWriter = writer;
            } catch (IOException e2) {
                e = e2;
                fileWriter = writer;
                e.printStackTrace();
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public static String ReadFile(String strFilePath) throws IOException {
        IOException e;
        File file = new File(strFilePath);
        String strLine = XmlPullParser.NO_NAMESPACE;
        StringBuilder text = new StringBuilder();
        FileReader fReader = null;
        BufferedReader bReader = null;
        try {
            FileReader fReader2 = new FileReader(file);
            BufferedReader bReader2 = new BufferedReader(fReader2);
            while (true) {
                try {
                    strLine = bReader2.readLine();
                    if (strLine == null) {
                        break;
                    }
                    text.append(strLine + "\n");
                } catch (IOException e2) {
                    e = e2;
                    bReader = bReader2;
                    fReader = fReader2;
                }
            }
            bReader = bReader2;
            fReader = fReader2;
        } catch (IOException e5) {
            e = e5;
            e.printStackTrace();
            if (bReader != null) {
                bReader.close();
            }
            if (fReader != null) {
                fReader.close();
            }
            return text.toString();
        }
        if (bReader != null) {
            bReader.close();
        }
        if (fReader != null) {
            fReader.close();
        }
        return text.toString();
    }

    public static boolean IsFromSDCard(Context context, String strPath) {
        String strSDAppRootPath = GetSDAppRootPath(context);
        if (strSDAppRootPath.length() <= 0 || !strPath.contains(strSDAppRootPath)) {
            return false;
        }
        return true;
    }

    public static long FolderSize(String strFolderPath) {
        return FolderSize(new File(strFolderPath));
    }

    private static long FolderSize(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return 0;
        }
        long result = 0;
        File[] fileList = dir.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                result += FolderSize(fileList[i]);
            } else {
                result += fileList[i].length();
            }
        }
        return result;
    }

    public static Uri SavePhoto(Context context, String strSavePath, Bitmap bmp, CompressFormat format) {
        boolean boRet = false;
        Log.i("SavePhoto", strSavePath);
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("_data", strSavePath);
        Uri editFileUri = context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        long lFileID = ContentUris.parseId(editFileUri);
        Log.i("SavePhoto", "URI:" + editFileUri.toString());
        try {
            if (format != CompressFormat.JPEG) {
                OutputStream stream = context.getContentResolver().openOutputStream(editFileUri);
                boRet = bmp.compress(format, 100, stream);
                stream.close();
            } else if (NativeUtil.compressBitmap(bmp, 90, strSavePath, true).equals(OADCItem.WATCH_TYPE_WATCH)) {
                boRet = true;
            }
            ContentValues updateValues = new ContentValues();
            File updateFile = new File(strSavePath);
            updateValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            updateValues.put("_size", Long.valueOf(updateFile.length()));
            context.getContentResolver().update(Media.EXTERNAL_CONTENT_URI, updateValues, "_id=?", new String[]{String.valueOf(lFileID)});
            context.getContentResolver().notifyChange(editFileUri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!boRet) {
            return null;
        }
        Context context2 = context;
        MediaScannerConnection.scanFile(context2, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new C04481());
        return editFileUri;
    }

    public static boolean SaveBitmap(String strPath, Bitmap bmp, CompressFormat format) {
        IOException e;
        File file = new File(strPath);
        if (file.exists()) {
            file.delete();
        }
        if (format != CompressFormat.JPEG) {
            file = new File(strPath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(format, 100, baos);
            try {
                FileOutputStream fos = new FileOutputStream(file.getPath());
                try {
                    fos.write(baos.toByteArray());
                    fos.close();
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    FileOutputStream fileOutputStream = fos;
                    e.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                return false;
            }
        } else if (NativeUtil.compressBitmap(bmp, 90, strPath, true).equals(OADCItem.WATCH_TYPE_WATCH)) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean SaveBitmapAndroidVersion(String strPath, Bitmap bmp, CompressFormat format) {
        IOException e;
        File file = new File(strPath);
        if (file.exists()) {
            file.delete();
        }
        file = new File(strPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(format, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file.getPath());
            FileOutputStream fileOutputStream;
            try {
                fos.write(baos.toByteArray());
                fos.close();
                fileOutputStream = fos;
                return true;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = fos;
                e.printStackTrace();
                return false;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] GetFileToByte(String strEditPath) {
        File file = new File(strEditPath);
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
            while (true) {
                int readNum = fis.read(buf);
                if (readNum == -1) {
                    return bos.toByteArray();
                }
                bos.write(buf, 0, readNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String GetRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(contentUri, new String[]{"_data"}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            String string = cursor.getString(column_index);
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String GetSDRootPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String GetSDAppRootPath(Context context) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return XmlPullParser.NO_NAMESPACE;
        }
        File file = context.getExternalFilesDir(null);
        if (file == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        return file.getAbsolutePath();
    }

    public static long GetFileSize(String strPath) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(strPath);
            if (file.exists()) {
                return file.length();
            }
        }
        return 0;
    }

    public static String SearchFolderParentPath(String targetName, String folderName) {
        File folder = new File(folderName);
        if (!folder.exists()) {
            return XmlPullParser.NO_NAMESPACE;
        }
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                if (file.getName().equalsIgnoreCase(targetName)) {
                    return file.getParent();
                }
                String parentPath = SearchFolderParentPath(targetName, file.getPath());
                if (!parentPath.isEmpty()) {
                    return parentPath;
                }
            }
        }
        return XmlPullParser.NO_NAMESPACE;
    }
}
