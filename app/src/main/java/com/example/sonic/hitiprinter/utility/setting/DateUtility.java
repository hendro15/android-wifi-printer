package com.example.sonic.hitiprinter.utility.setting;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Pair;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.sonic.hitiprinter.trace.GlobalVariable_OtherSetting;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.setting.DateInfo.DateType;
import com.example.sonic.hitiprinter.utility.setting.DateInfo.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.net.ftp.FTPClient;

public class DateUtility {
    LogManager LOG;
    String TAG;
    Activity mActivity;
    OnItemSelectedListener mListener;
    Map<DateType, Pair<Integer, Integer>> mSpinnerMap;

    /* renamed from: com.hiti.utility.setting.DateUtility.1 */
    static /* synthetic */ class C04841 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$setting$DateInfo$Position;

        static {
            $SwitchMap$com$hiti$utility$setting$DateInfo$Position = new int[Position.values().length];
            try {
                $SwitchMap$com$hiti$utility$setting$DateInfo$Position[Position.Left_buttom.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$setting$DateInfo$Position[Position.Right_buttom.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public DateUtility(Activity activity, OnItemSelectedListener listener) {
        this.mSpinnerMap = null;
        this.mActivity = null;
        this.mListener = null;
        this.LOG = null;
        this.TAG = null;
        this.mActivity = activity;
        this.mListener = listener;
        this.mSpinnerMap = new HashMap();
        this.LOG = new LogManager(0);
        this.TAG = "DateUtility";
    }

    public void SetSpinnerMap(DateType key, int spinnerId, int arrayId) {
        this.mSpinnerMap.put(key, new Pair(Integer.valueOf(spinnerId), Integer.valueOf(arrayId)));
    }

    public Map<DateType, Pair<Integer, Integer>> GetSpinnerMap() {
        return this.mSpinnerMap;
    }

    public void SetSpinnerAdapter(Map<DateType, Pair<Integer, Integer>> spinnerMap, int iAdpaterId, int iDropDownId) {
        GlobalVariable_OtherSetting preference = new GlobalVariable_OtherSetting(this.mActivity);
        preference.RestoreGlobalVariable();
        for (Entry<DateType, Pair<Integer, Integer>> map : spinnerMap.entrySet()) {
            Spinner mSpinner = (Spinner) this.mActivity.findViewById(((Integer) ((Pair) map.getValue()).first).intValue());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.mActivity, ((Integer) ((Pair) map.getValue()).second).intValue(), iAdpaterId);
            adapter.setDropDownViewResource(iDropDownId);
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this.mListener);
            mSpinner.setTag(map.getKey());
            int itemId = preference.GetAddDateItemId((DateType) map.getKey());
            mSpinner.setSelection(itemId);
            this.LOG.m386v(this.TAG, "SetSpinnerAdapter key: " + String.valueOf(map.getKey()));
            this.LOG.m386v(this.TAG, "SetSpinnerAdapter itemId: " + itemId);
        }
    }

    public static void AddDateOnPhoto(Bitmap photo, DateInfo photoDate) {
        boolean bVertical = true;
        Canvas canvas = new Canvas(photo);
        Paint paint = new Paint(1);
        paint.setColor(photoDate.GetColor());
        paint.setTextSize(40.0f);
        paint.setStyle(Style.FILL);
        Rect rect = new Rect();
        paint.getTextBounds(photoDate.GetDate(), 0, photoDate.GetDate().length(), rect);
        if (photo.getWidth() >= photo.getHeight()) {
            bVertical = false;
        }
        canvas.drawText(photoDate.GetDate(), GetRecX(photoDate.GetPosition(), rect.width(), bVertical), GetRecY(photoDate.GetPosition(), bVertical), paint);
    }

    static float GetRecX(Position type, int textLength, boolean bVertical) {
        int padding = bVertical ? 95 : 64;
        int iWidth = bVertical ? 1280 : 1818;
        switch (C04841.$SwitchMap$com$hiti$utility$setting$DateInfo$Position[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return (float) padding;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return (float) ((iWidth - padding) - textLength);
            default:
                return (float) ((iWidth - padding) - textLength);
        }
    }

    static float GetRecY(Position type, boolean bVertical) {
        int padding = bVertical ? 41 : 76;
        int iHeight = bVertical ? 1818 : 1280;
        switch (C04841.$SwitchMap$com$hiti$utility$setting$DateInfo$Position[type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return (float) (iHeight - padding);
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return (float) (iHeight - padding);
            default:
                return (float) (iHeight - padding);
        }
    }
}
