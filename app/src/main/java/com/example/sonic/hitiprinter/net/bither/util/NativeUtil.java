package com.example.sonic.hitiprinter.net.bither.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class NativeUtil {
    private static int DEFAULT_QUALITY;

    private static native String compressBitmap(Bitmap bitmap, int i, int i2, int i3, byte[] bArr, boolean z);

    static {
        System.loadLibrary("jpegbither");
        System.loadLibrary("bitherjni");
        DEFAULT_QUALITY = 90;
    }

    public static void compressBitmap(Bitmap bit, String fileName, boolean optimize) {
        compressBitmap(bit, DEFAULT_QUALITY, fileName, optimize);
    }

    public static String compressBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        Log.d("native", "compress of native");
        String strRet = XmlPullParser.NO_NAMESPACE;
        if (bit.getConfig() == Config.ARGB_8888) {
            return saveBitmap(bit, quality, fileName, optimize);
        }
        Bitmap result = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bit, 0.0f, 0.0f, paint);
        strRet = saveBitmap(result, quality, fileName, optimize);
        result.recycle();
        return strRet;
    }

    private static String saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        return compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
    }
}
