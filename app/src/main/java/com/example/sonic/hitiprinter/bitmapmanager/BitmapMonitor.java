package com.example.sonic.hitiprinter.bitmapmanager;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.utility.FileUtility;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class BitmapMonitor {
    private static final float LEGAL_PHOTO_RATIO = 5.0f;

    public static BitmapMonitorResult Copy(Bitmap SourceBitmap, Config config, boolean isMutable) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        try {
            Bitmap bmp = SourceBitmap.copy(config, isMutable);
            if (bmp == null) {
                bmr.SetResult(96);
            } else if (bmp.getWidth() > 0 || bmp.getHeight() > 0) {
                bmr.SetBitmap(bmp);
                bmr.SetResult(0);
            } else {
                bmr.SetResult(98);
            }
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        } catch (Exception e2) {
            bmr.SetResult(100);
            e2.printStackTrace();
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateBitmap(int width, int height, Config config) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (config == null) {
            config = Config.ARGB_8888;
        }
        try {
            Bitmap bmp = Bitmap.createBitmap(width, height, config);
            if (bmp == null) {
                bmr.SetResult(96);
            } else if (bmp.getWidth() > 0 || bmp.getHeight() > 0) {
                bmr.SetResult(0);
                bmr.SetBitmap(bmp);
            } else {
                bmr.SetResult(98);
            }
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            bmr.SetResult(98);
            e2.printStackTrace();
        } catch (Exception e3) {
            bmr.SetResult(100);
            e3.printStackTrace();
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateBitmap(InputStream is, boolean boMutable) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (is == null) {
            bmr.SetResult(97);
            return bmr;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp == null) {
                bmr.SetResult(96);
                return bmr;
            } else if (bmp.getWidth() <= 0 && bmp.getHeight() <= 0) {
                bmr.SetResult(98);
                return bmr;
            } else if (boMutable) {
                return ConvertToMutable(bmp);
            } else {
                bmr.SetResult(0);
                bmr.SetBitmap(bmp);
                return bmr;
            }
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        } catch (Exception e2) {
            bmr.SetResult(100);
            e2.printStackTrace();
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateBitmap(InputStream is, Rect outPadding, Options opts, boolean boMutable) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (is == null) {
            bmr.SetResult(97);
            return bmr;
        }
        try {
            Bitmap bmp = BitmapFactory.decodeStream(is, outPadding, opts);
            if (opts.inJustDecodeBounds) {
                bmr.SetResult(0);
                return bmr;
            } else if (bmp == null) {
                bmr.SetResult(96);
                return bmr;
            } else if (bmp.getWidth() <= 0 && bmp.getHeight() <= 0) {
                bmr.SetResult(98);
                return bmr;
            } else if (boMutable) {
                return ConvertToMutable(bmp);
            } else {
                bmr.SetResult(0);
                bmr.SetBitmap(bmp);
                return bmr;
            }
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        } catch (Exception e2) {
            bmr.SetResult(100);
            e2.printStackTrace();
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateBitmap(String pathName, boolean boMutable) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (!FileUtility.SDCardState()) {
            bmr.SetResult(95);
            return bmr;
        } else if (FileUtility.FileExist(pathName)) {
            try {
                Bitmap bmp = BitmapFactory.decodeFile(pathName);
                if (bmp == null) {
                    bmr.SetResult(96);
                    return bmr;
                } else if (bmp.getWidth() <= 0 && bmp.getHeight() <= 0) {
                    bmr.SetResult(98);
                    return bmr;
                } else if (boMutable) {
                    return ConvertToMutable(bmp);
                } else {
                    bmr.SetResult(0);
                    bmr.SetBitmap(bmp);
                    return bmr;
                }
            } catch (OutOfMemoryError e) {
                bmr.SetResult(99);
                e.printStackTrace();
            } catch (Exception e2) {
                bmr.SetResult(100);
                e2.printStackTrace();
            }
        } else {
            bmr.SetResult(97);
            return bmr;
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        try {
            Bitmap bmp = Bitmap.createBitmap(source, x, y, width, height, m, filter);
            if (bmp == null) {
                bmr.SetResult(96);
            } else if (bmp.getWidth() > 0 || bmp.getHeight() > 0) {
                bmr.SetResult(0);
                bmr.SetBitmap(bmp);
            } else {
                bmr.SetResult(98);
            }
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        } catch (Exception e2) {
            bmr.SetResult(100);
            e2.printStackTrace();
        }
        return bmr;
    }

    public static BitmapMonitorResult CreateScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (src == null) {
            bmr.SetResult(97);
        } else if (dstWidth > 0 || dstHeight > 0) {
            try {
                Bitmap bmp = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, filter);
                if (bmp == null) {
                    bmr.SetResult(96);
                } else if (bmp.getWidth() > 0 || bmp.getHeight() > 0) {
                    bmr.SetResult(0);
                    bmr.SetBitmap(bmp);
                } else {
                    bmr.SetResult(98);
                }
            } catch (OutOfMemoryError e) {
                bmr.SetResult(99);
                e.printStackTrace();
            } catch (Exception e2) {
                bmr.SetResult(100);
                e2.printStackTrace();
            }
        } else {
            bmr.SetResult(98);
        }
        return bmr;
    }

    public static void TrySystemGC() {
        System.gc();
    }

    private static int CalculateInSampleSize(int iOrgWidth, int iOrgHeight, int iLimitWidth, int iLimitHeight) {
        int inSampleSize = 1;
        if (iOrgWidth > 0 && iOrgHeight > 0) {
            while (true) {
                if (iOrgHeight > 0 && iOrgWidth / inSampleSize <= iLimitWidth && iOrgWidth > 0 && iOrgHeight / inSampleSize <= iLimitHeight) {
                    break;
                }
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static BitmapMonitorResult ConvertToMutable(Bitmap bmp) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        if (bmp == null) {
            bmr.SetResult(97);
            return bmr;
        }
        try {
            if (!FileUtility.SDCardState()) {
                bmr.SetResult(95);
                return bmr;
            }
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            Config type = bmp.getConfig();
            if (type == null) {
                type = Config.ARGB_8888;
            }
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, (long) (bmp.getRowBytes() * height));
            bmp.copyPixelsToBuffer(map);
            bmp.recycle();
            System.gc();
            bmr = CreateBitmap(width, height, type);
            if (bmr.IsSuccess()) {
                bmp = bmr.GetBitmap();
                map.position(0);
                bmp.copyPixelsFromBuffer(map);
            }
            channel.close();
            randomAccessFile.close();
            file.delete();
            return bmr;
        } catch (FileNotFoundException e) {
            bmr.SetResult(97);
            e.printStackTrace();
        } catch (IOException e2) {
            bmr.SetResult(93);
            e2.printStackTrace();
        } catch (RuntimeException e3) {
            bmr.SetResult(96);
            e3.printStackTrace();
        }
        return bmr;
    }

    public static boolean BitmapExist(Context context, String strPath) {
        boolean boRet = FileUtility.FileExist(strPath);
        if (!boRet) {
            return boRet;
        }
        Options options = new Options();
        try {
            options.inJustDecodeBounds = true;
            CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(Uri.parse("file://" + strPath))), null, options, false);
            int iOrgWidth = options.outWidth;
            int iOrgHeight = options.outHeight;
            if (iOrgWidth <= 0 || iOrgHeight <= 0) {
                return false;
            }
            return boRet;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return boRet;
        }
    }

    public static BitmapMonitorResult GetBitmapFromFile(Context context, String strPath, boolean boMutable) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Options options = new Options();
        options.inJustDecodeBounds = false;
        if (!FileUtility.SDCardState()) {
            bmr.SetResult(95);
            return bmr;
        } else if (FileUtility.FileExist(strPath)) {
            Uri uri;
            if (strPath.contains("file://")) {
                uri = Uri.parse(strPath);
            } else {
                uri = Uri.parse("file://" + strPath);
            }
            try {
                bmr = CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, boMutable);
            } catch (FileNotFoundException e) {
                bmr.SetResult(97);
                e.printStackTrace();
            }
            return bmr;
        } else {
            bmr.SetResult(97);
            return bmr;
        }
    }

    public static BitmapMonitorResult IsLegalRatio(Context context, Uri uri) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Options options = new Options();
        options.inJustDecodeBounds = true;
        if (new File(uri.getPath()).exists()) {
            try {
                BitmapFactory.decodeStream(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options);
                int iOrgWidth = options.outWidth;
                int iOrgHeight = options.outHeight;
                if (iOrgWidth <= 0 || iOrgHeight <= 0) {
                    bmr.SetResult(96);
                } else if (iOrgHeight > iOrgWidth && ((float) iOrgHeight) / ((float) iOrgWidth) > LEGAL_PHOTO_RATIO) {
                    bmr.SetResult(94);
                } else if (iOrgWidth <= iOrgHeight || ((float) iOrgWidth) / ((float) iOrgHeight) <= LEGAL_PHOTO_RATIO) {
                    bmr.SetResult(0);
                } else {
                    bmr.SetResult(94);
                }
            } catch (FileNotFoundException e) {
                bmr.SetResult(97);
                e.printStackTrace();
            }
        } else {
            bmr.SetResult(97);
        }
        return bmr;
    }

    public static BitmapMonitorResult DecodeImage(String imageData) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Bitmap bmp = null;
        if (!(imageData == null || imageData.equals(XmlPullParser.NO_NAMESPACE))) {
            try {
                byte[] decodedImage = Base64.decode(imageData, 0);
                if (decodedImage != null) {
                    bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                }
                if (bmp == null) {
                    bmr.SetResult(96);
                } else {
                    bmr.SetResult(0);
                }
                bmr.SetBitmap(bmp);
            } catch (IllegalArgumentException e) {
                bmr.SetResult(96);
                e.printStackTrace();
            } catch (OutOfMemoryError e2) {
                bmr.SetResult(99);
                e2.printStackTrace();
            }
        }
        return bmr;
    }

    @SuppressLint({"NewApi"})
    public static void ShowHeapSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        if (VERSION.SDK_INT >= 11) {
            int memory = am.getMemoryClass();
            am.getLargeMemoryClass();
        }
    }

    public static boolean IsPhotoLowQuality(Context context, Uri uri, int iLimitWidth, int iLimitHeight) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Options options = new Options();
        try {
            options.inJustDecodeBounds = true;
            bmr = CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, false);
            if (!bmr.IsSuccess()) {
                return false;
            }
            int iOrgWidth = options.outWidth;
            int iOrgHeight = options.outHeight;
            double dLimitMaxT = (double) iLimitWidth;
            if (((double) iLimitHeight) > dLimitMaxT) {
                dLimitMaxT = (double) iLimitHeight;
            }
            double dOriMinS = (double) iOrgWidth;
            if (((double) iOrgHeight) < dOriMinS) {
                dOriMinS = (double) iOrgHeight;
            }
            if (dLimitMaxT / 2.0d > dOriMinS) {
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            bmr.SetResult(97);
            e.printStackTrace();
            return false;
        }
    }

    public static void CopyPixelsToFile(String strSavePath, Bitmap bmp) {
        IOException e;
        File file = new File(strSavePath);
        if (file.exists()) {
            file.delete();
        }
        file = new File(strSavePath);
        try {
            ByteBuffer buffer = ByteBuffer.allocate(bmp.getRowBytes() * bmp.getHeight());
            bmp.copyPixelsToBuffer(buffer);
            FileOutputStream fos = new FileOutputStream(file.getPath());
            FileOutputStream fileOutputStream;
            try {
                fos.write(buffer.array());
                fos.close();
                fileOutputStream = fos;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = fos;
                e.printStackTrace();
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public static void CopyPixelsToFile(String strSavePath, byte[] bytes) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(strSavePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(strSavePath).getPath());
            try {
                fos.write(bytes);
                fos.close();
                fileOutputStream = fos;
            } catch (IOException e2) {
                e = e2;
                fileOutputStream = fos;
                e.printStackTrace();
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
        }
    }

    public static int ResolveBitmapOrientation(Uri uri) {
        if (uri.getPath() == null) {
            return -1;
        }
        return ResolveBitmapOrientation(new File(uri.getPath()));
    }

    public static int ResolveBitmapOrientation(File file) {
        try {
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            ExifInterface exifInterface = exif;
            return exif.getAttributeInt("Orientation", 1);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static BitmapMonitorResult GetExifOrientationBitmap(Bitmap bitmap, int orientation) {
        int rotate;
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        switch (orientation) {
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                rotate = 180;
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                rotate = 90;
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                rotate = 270;
                break;
            default:
                bmr.SetBitmap(bitmap);
                bmr.SetResult(0);
                return bmr;
        }
        Matrix mtx = new Matrix();
        mtx.postRotate((float) rotate);
        return CreateBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);
    }

    public static boolean IsVertical(Context context, Uri uri) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, false);
            int iOrgWidth = options.outWidth;
            int iOrgHeight = options.outHeight;
            if (IsHappenExifInterfaceOrientationVHChange(ResolveBitmapOrientation(uri))) {
                int iTemp = iOrgWidth;
                iOrgWidth = iOrgHeight;
                iOrgHeight = iTemp;
            }
            if (iOrgWidth > iOrgHeight) {
                return false;
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean IsHappenExifInterfaceOrientationVHChange(int iExifInterfaceOrientation) {
        boolean boChange = false;
        if (iExifInterfaceOrientation == -1) {
            return boChange;
        }
        switch (iExifInterfaceOrientation) {
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                boChange = true;
                break;
        }
        return boChange;
    }

    public static BitmapMonitorResult CreateCroppedBitmap(Context context, Uri uri, int iLimitWidth, int iLimitHeight) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        Options options = new Options();
        int iLimitSimpleWidth = (int) (((((float) iLimitWidth) * 80.0f) / 100.0f) * 2.0f);
        int iLimitSimpleHeight = (int) (((((float) iLimitHeight) * 80.0f) / 100.0f) * 2.0f);
        if (!FileUtility.SDCardState()) {
            bmr.SetResult(95);
            return bmr;
        } else if (iLimitWidth > 0 || iLimitHeight > 0) {
            try {
                options.inJustDecodeBounds = true;
                bmr = CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, false);
                if (!bmr.IsSuccess()) {
                    return bmr;
                }
                Bitmap OrgBitmap = bmr.GetBitmap();
                int iOrgWidth = options.outWidth;
                int iOrgHeight = options.outHeight;
                int iExifInterfaceOrientation = ResolveBitmapOrientation(uri);
                if (IsHappenExifInterfaceOrientationVHChange(iExifInterfaceOrientation)) {
                    int iTemp = iOrgWidth;
                    iOrgWidth = iOrgHeight;
                    iOrgHeight = iTemp;
                }
                TrySystemGC();
                int iSampleSize = CalculateInSampleSize(iOrgWidth, iOrgHeight, iLimitSimpleWidth, iLimitSimpleHeight);
                options.inJustDecodeBounds = false;
                options.inSampleSize = iSampleSize;
                try {
                    bmr = CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, true);
                    if (!bmr.IsSuccess()) {
                        return bmr;
                    }
                    if (iExifInterfaceOrientation != -1) {
                        BitmapMonitorResult exifBmr = GetExifOrientationBitmap(bmr.GetBitmap(), iExifInterfaceOrientation);
                        if (exifBmr.IsSuccess() && bmr.GetBitmap() != exifBmr.GetBitmap()) {
                            bmr.GetBitmap().recycle();
                            bmr = exifBmr;
                        }
                    }
                    OrgBitmap = bmr.GetBitmap();
                    iOrgWidth = bmr.GetBitmap().getWidth();
                    iOrgHeight = bmr.GetBitmap().getHeight();
                    if (iOrgWidth == iLimitWidth && iOrgHeight == iLimitHeight) {
                        return bmr;
                    }
                    double dCompareS = (double) iOrgWidth;
                    double dCompareT = (double) iLimitWidth;
                    if (dCompareS > ((double) iOrgHeight)) {
                        dCompareS = (double) iOrgHeight;
                        dCompareT = (double) iLimitHeight;
                    }
                    double dScale = dCompareT / dCompareS;
                    int iWidth = (int) (((double) iOrgWidth) * dScale);
                    int iHeight = (int) (((double) iOrgHeight) * dScale);
                    bmr = CreateScaledBitmap(OrgBitmap, iWidth, iHeight, true);
                    bmr.SetPixelWarning(dScale, iSampleSize);
                    if (!bmr.IsSuccess()) {
                        return bmr;
                    }
                    Bitmap CroppedBitmap = bmr.GetBitmap();
                    if (OrgBitmap == CroppedBitmap) {
                        return bmr;
                    }
                    if (!OrgBitmap.isRecycled()) {
                        OrgBitmap.recycle();
                    }
                    dCompareS = (double) iWidth;
                    dCompareT = (double) iLimitWidth;
                    if (Math.abs(dCompareS - ((double) iLimitWidth)) <= 1.0d) {
                        dCompareS = (double) iHeight;
                        dCompareT = (double) iLimitHeight;
                    }
                    if (dCompareS < dCompareT) {
                        dScale = dCompareT / dCompareS;
                        bmr = CreateScaledBitmap(CroppedBitmap, (int) (((double) iWidth) * dScale), (int) (((double) iHeight) * dScale), true);
                    }
                    bmr.SetPixelWarning(dScale, iSampleSize);
                    return bmr;
                } catch (FileNotFoundException e) {
                    bmr.SetResult(97);
                    e.printStackTrace();
                    return bmr;
                }
            } catch (FileNotFoundException e2) {
                bmr.SetResult(97);
                e2.printStackTrace();
                return bmr;
            }
        } else {
            bmr.SetResult(98);
            return bmr;
        }
    }

    public static BitmapMonitorResult CreateCroppedBitmapNew(Context context, Uri uri, int iLimitWidth, int iLimitHeight) {
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        int iSampleSize = 1;
        Options options = new Options();
        int iLimitSimpleWidth = (int) (((((float) iLimitWidth) * 80.0f) / 100.0f) * 2.0f);
        int iLimitSimpleHeight = (int) (((((float) iLimitHeight) * 80.0f) / 100.0f) * 2.0f);
        if (!FileUtility.SDCardState()) {
            bmr.SetResult(95);
            return bmr;
        } else if (iLimitWidth > 0 || iLimitHeight > 0) {
            try {
                options.inJustDecodeBounds = true;
                bmr = CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, false);
                if (!bmr.IsSuccess()) {
                    return bmr;
                }
                Bitmap OrgBitmap = bmr.GetBitmap();
                int iOrgWidth = options.outWidth;
                int iOrgHeight = options.outHeight;
                int iExifInterfaceOrientation = ResolveBitmapOrientation(uri);
                if (IsHappenExifInterfaceOrientationVHChange(iExifInterfaceOrientation) && iOrgWidth > iOrgHeight) {
                    int iTemp = iOrgWidth;
                    iOrgWidth = iOrgHeight;
                    iOrgHeight = iTemp;
                }
                try {
                    InputStream bufferedInputStream = new BufferedInputStream(context.getContentResolver().openInputStream(uri));
                    TrySystemGC();
                    options.inJustDecodeBounds = false;
                    if (iOrgWidth == iLimitWidth && iOrgHeight == iLimitHeight) {
                        options.inSampleSize = 1;
                        bmr = CreateBitmap(bufferedInputStream, true);
                    } else {
                        iSampleSize = CalculateInSampleSize(iOrgWidth, iOrgHeight, iLimitSimpleWidth, iLimitSimpleHeight);
                        options.inSampleSize = iSampleSize;
                        bmr = CreateBitmap(bufferedInputStream, null, options, true);
                    }
                    if (!bmr.IsSuccess()) {
                        return bmr;
                    }
                    if (iExifInterfaceOrientation != -1) {
                        BitmapMonitorResult exifBmr = GetExifOrientationBitmap(bmr.GetBitmap(), iExifInterfaceOrientation);
                        if (exifBmr.IsSuccess() && bmr.GetBitmap() != exifBmr.GetBitmap()) {
                            bmr.GetBitmap().recycle();
                            bmr = exifBmr;
                        }
                    }
                    if (iOrgWidth == iLimitWidth && iOrgHeight == iLimitHeight) {
                        bmr.SetSampleSize(1);
                        bmr.SetScale(1.0d);
                        return bmr;
                    }
                    double dScale;
                    OrgBitmap = bmr.GetBitmap();
                    iOrgWidth = bmr.GetBitmap().getWidth();
                    iOrgHeight = bmr.GetBitmap().getHeight();
                    double dWidthRatio = ((double) iLimitWidth) / ((double) iOrgWidth);
                    double dHeightRatio = ((double) iLimitHeight) / ((double) iOrgHeight);
                    if (dHeightRatio > dWidthRatio) {
                        dScale = dHeightRatio;
                    } else {
                        dScale = dWidthRatio;
                    }
                    bmr.SetPixelWarning(dScale, iSampleSize);
                    if (dScale == 1.0d) {
                        return bmr;
                    }
                    bmr = CreateScaledBitmap(OrgBitmap, (int) (((double) iOrgWidth) * dScale), (int) (((double) iOrgHeight) * dScale), true);
                    if (!bmr.IsSuccess()) {
                        return bmr;
                    }
                    if (OrgBitmap == bmr.GetBitmap()) {
                        Log.i("Really!?", String.valueOf(dScale));
                        return bmr;
                    }
                    if (!OrgBitmap.isRecycled()) {
                        OrgBitmap.recycle();
                    }
                    return bmr;
                } catch (FileNotFoundException e) {
                    bmr.SetResult(97);
                    e.printStackTrace();
                    return bmr;
                }
            } catch (FileNotFoundException e2) {
                bmr.SetResult(97);
                e2.printStackTrace();
                return bmr;
            }
        } else {
            bmr.SetResult(98);
            return bmr;
        }
    }

    private static BitmapMonitorResult DownSampleSize(String sourcePath, int iMaxLengthLimit) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapMonitorResult bmr = new BitmapMonitorResult();
        try {
            bmr.SetBitmap(BitmapFactory.decodeFile(sourcePath, options));
            bmr.SetResult(0);
            options.inSampleSize = (options.outWidth > options.outHeight ? options.outWidth : options.outHeight) / iMaxLengthLimit;
        } catch (OutOfMemoryError e) {
            bmr.SetResult(99);
            e.printStackTrace();
        }
        if (bmr.IsSuccess()) {
            options.inJustDecodeBounds = false;
            TrySystemGC();
            try {
                bmr.SetBitmap(BitmapFactory.decodeFile(sourcePath, options));
                bmr.SetResult(0);
            } catch (OutOfMemoryError e2) {
                bmr.SetResult(99);
                e2.printStackTrace();
                Log.i("BitmapDownSampleSize", "ERROR_OUT_OF_MEMORY");
            }
        }
        return bmr;
    }

    public static BitmapMonitorResult DownSizeBitmap(String sourcePath, String tagetPath, int iMaxWidthLimit) {
        BitmapMonitorResult bmr = DownSampleSize(sourcePath, iMaxWidthLimit);
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        int iWidth = bmr.GetBitmap().getWidth();
        int iHeight = bmr.GetBitmap().getHeight();
        int iShortSide = (int) (((float) Math.min(iWidth, iHeight)) * (((float) iMaxWidthLimit) / ((float) Math.max(iWidth, iHeight))));
        int iLongSide = iMaxWidthLimit;
        if (iWidth > iHeight) {
            iWidth = iLongSide;
        } else {
            iWidth = iShortSide;
        }
        if (iWidth > iHeight) {
            iHeight = iShortSide;
        } else {
            iHeight = iLongSide;
        }
        bmr = CreateScaledBitmap(bmr.GetBitmap(), iWidth, iHeight, false);
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        FileUtility.SaveBitmap(tagetPath, bmr.GetBitmap(), CompressFormat.JPEG);
        return bmr;
    }

    public static Pair<Integer, Integer> GetPhotoSides(Context context, Uri uri) {
        int iOrgWidth = 0;
        int iOrgHeight = 0;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            CreateBitmap(new BufferedInputStream(context.getContentResolver().openInputStream(uri)), null, options, false);
            iOrgWidth = options.outWidth;
            iOrgHeight = options.outHeight;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Pair.create(Integer.valueOf(iOrgWidth), Integer.valueOf(iOrgHeight));
    }
}
