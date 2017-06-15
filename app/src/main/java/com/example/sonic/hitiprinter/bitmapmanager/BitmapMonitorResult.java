package com.example.sonic.hitiprinter.bitmapmanager;

import android.content.Context;
import android.graphics.Bitmap;
import com.hiti.utility.resource.ResourceSearcher;
import com.hiti.utility.resource.ResourceSearcher.RS_TYPE;
import org.xmlpull.v1.XmlPullParser;

public class BitmapMonitorResult {
    public static final int ERROR_FILE_FORMAT = 96;
    public static final int ERROR_OUT_OF_MEMORY = 99;
    public static final int ERROR_PHOTO_RATIO = 94;
    public static final int ERROR_SD_CARD_MAYBE_FULL = 93;
    public static final int ERROR_SD_CARD_NOT_EXIST = 95;
    public static final int ERROR_SIZE_IS_ZERO = 98;
    public static final int ERROR_SOURCE_NOT_FOUND = 97;
    public static final int ERROR_UNKNOWN = 100;
    public static final int SUCCESS = 0;
    public static final int WARNING = -1;
    private Bitmap m_Bmp;
    private double m_dScale;
    private int m_iResult;
    private int m_iSampleSize;

    public BitmapMonitorResult() {
        this.m_dScale = 0.0d;
        this.m_iSampleSize = SUCCESS;
        this.m_Bmp = null;
        this.m_iResult = WARNING;
    }

    public void SetBitmap(Bitmap bmp) {
        this.m_Bmp = bmp;
    }

    public void SetResult(int iResult) {
        this.m_iResult = iResult;
        if (this.m_iResult != 0 && this.m_Bmp != null) {
            this.m_Bmp.recycle();
            this.m_Bmp = null;
        }
    }

    public Bitmap GetBitmap() {
        if (this.m_iResult == 0) {
            return this.m_Bmp;
        }
        return null;
    }

    public int GetResult() {
        return this.m_iResult;
    }

    public String GetError(Context context) {
        return GetError(context, this.m_iResult);
    }

    public static String GetError(Context context, int iResult) {
        if (iResult == WARNING || iResult == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        if (iResult == ERROR_OUT_OF_MEMORY) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_OUT_OF_MEMORY"));
        }
        if (iResult == ERROR_SIZE_IS_ZERO) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_SIZE_IS_ZERO"));
        }
        if (iResult == ERROR_SOURCE_NOT_FOUND) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_SOURCE_NOT_FOUND"));
        }
        if (iResult == ERROR_FILE_FORMAT) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_FILE_FORMAT"));
        }
        if (iResult == ERROR_SD_CARD_NOT_EXIST) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_SD_CARD_NOT_EXIST"));
        }
        if (iResult == ERROR_PHOTO_RATIO) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_ERROR_PHOTO_RATIO"));
        }
        if (iResult == ERROR_SD_CARD_MAYBE_FULL) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_SD_CARD_MAYBE_FULL"));
        }
        if (iResult == ERROR_UNKNOWN) {
            return context.getString(ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_ERROR_UNKNOWN"));
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    public boolean IsSuccess() {
        if (this.m_iResult == 0) {
            return true;
        }
        return false;
    }

    public void SetPixelWarning(double scale, int sampleSize) {
        if (scale > 1.0d) {
            SetScale(scale);
            SetSampleSize(sampleSize);
        }
    }

    public void SetScale(double scale) {
        this.m_dScale = scale;
    }

    public void SetSampleSize(int sampleSize) {
        this.m_iSampleSize = sampleSize;
    }

    public double GetScale() {
        return this.m_dScale;
    }

    public int GetSampleSize() {
        return this.m_iSampleSize;
    }
}
