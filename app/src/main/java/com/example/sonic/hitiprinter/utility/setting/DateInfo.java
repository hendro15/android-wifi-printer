package com.example.sonic.hitiprinter.utility.setting;

import android.content.Context;
import com.example.sonic.hitiprinter.trace.GlobalVariable_OtherSetting;
import com.example.sonic.hitiprinter.utility.LogManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;

public class DateInfo {
    LogManager LOG;
    String TAG;
    int mColor;
    ArrayList<Integer> mColorList;
    String mDateFormat;
    String mDateTime;
    ArrayList<String> mFormatList;
    Map<DateType, Integer> mItemIdMap;
    int mPaddingX;
    int mPaddingY;
    long mPhotoId;
    String mPhotoPath;
    ArrayList<Position> mPositionList;

    public enum Color {
        White,
        Black,
        Orange,
        Yellow
    }

    public enum DateType {
        Format,
        Position,
        Color
    }

    public enum Position {
        Left_buttom,
        Right_buttom
    }

    public DateInfo(Context context) {
        this.mDateFormat = null;
        this.mPhotoId = 0;
        this.mPhotoPath = XmlPullParser.NO_NAMESPACE;
        this.mDateTime = null;
        this.mColor = 0;
        this.mItemIdMap = null;
        this.mFormatList = null;
        this.mPositionList = null;
        this.mColorList = null;
        this.mPaddingX = 30;
        this.mPaddingY = 10;
        this.LOG = null;
        this.TAG = null;
        this.LOG = new LogManager(0);
        this.TAG = "DateInfo";
        this.mFormatList = new ArrayList();
        this.mPositionList = new ArrayList();
        this.mColorList = new ArrayList();
        this.mPositionList.add(Position.Left_buttom);
        this.mPositionList.add(Position.Right_buttom);
        RestoreDateItemId(context);
    }

    public void AddFormatList(String[] formatList) {
        for (String format : formatList) {
            this.mFormatList.add(format.replace("mm", "MM"));
        }
    }

    public void AddColorList(Integer[] colorList) {
        for (Integer color : colorList) {
            this.mColorList.add(color);
        }
    }

    public void SetItemId(DateType type, int itemId) {
        this.mItemIdMap.put(type, Integer.valueOf(itemId));
    }

    public void SaveData(Context context) {
        GlobalVariable_OtherSetting mPrefernce = new GlobalVariable_OtherSetting(context);
        mPrefernce.RestoreGlobalVariable();
        for (Entry<DateType, Integer> item : this.mItemIdMap.entrySet()) {
            mPrefernce.SetAddDateItemId((DateType) item.getKey(), ((Integer) this.mItemIdMap.get(item.getKey())).intValue());
        }
        mPrefernce.SaveGlobalVariable();
    }

    public void SetPhotoId(long photoId) {
        this.mPhotoId = photoId;
    }

    public long GetPhotoId() {
        return this.mPhotoId;
    }

    public void SetPhotoPath(String path) {
        this.mPhotoPath = path;
    }

    public String GetPhotoPath() {
        return this.mPhotoPath;
    }

    public void SetDate(String time) {
        if (time == null) {
            time = XmlPullParser.NO_NAMESPACE;
        }
        this.mDateTime = time;
    }

    public String GetDate() {
        return this.mDateTime;
    }

    private void RestoreDateItemId(Context context) {
        int i = 0;
        GlobalVariable_OtherSetting mPrefernce = new GlobalVariable_OtherSetting(context);
        mPrefernce.RestoreGlobalVariable();
        this.mItemIdMap = new HashMap();
        this.mItemIdMap.put(DateType.Format, Integer.valueOf(mPrefernce.GetAddDateItemId(DateType.Format)));
        DateType[] list = new DateType[]{DateType.Format, DateType.Position, DateType.Color};
        int length = list.length;
        while (i < length) {
            DateType key = list[i];
            this.mItemIdMap.put(key, Integer.valueOf(mPrefernce.GetAddDateItemId(key)));
            i++;
        }
        this.LOG.m386v(this.TAG, "RestoreDateItemId map: " + this.mItemIdMap);
    }

    public String GetFormat() {
        String format = (String) this.mFormatList.get(((Integer) this.mItemIdMap.get(DateType.Format)).intValue());
        this.LOG.m386v(this.TAG, "GetFormat: " + format);
        return format;
    }

    public Position GetPosition() {
        return (Position) this.mPositionList.get(((Integer) this.mItemIdMap.get(DateType.Position)).intValue());
    }

    public int GetColor() {
        return ((Integer) this.mColorList.get(((Integer) this.mItemIdMap.get(DateType.Color)).intValue())).intValue();
    }
}
