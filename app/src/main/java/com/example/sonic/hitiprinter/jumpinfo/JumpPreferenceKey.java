package com.example.sonic.hitiprinter.jumpinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.FilterColorValue;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class JumpPreferenceKey {
    public static final String ANIMATION_DONE = "ANIMATION_DONE";
    public static final String EDM_SHOW = "EDM_SHOW";
    public static final String FROM_SETTING = "FROM_SETTING";
    public static final String ID_FROM_MAIN = "ID_FROM_MAIN";
    public static final String ID_FROM_SETTING = "ID_FROM_SETTING";
    public static final String LOW_QUALITY_SEL_ALL = "LOW_QUALITY_SEL_ALL";
    public static final String LOW_QUALITY_WILL = "LOW_QUALITY_WILL";
    public static final String MODEL_DEFAULT = "MODEL_DEFAULT";
    public static final String PATHSELECTED = "PATHSELECTED";
    public static final String PHOTO_MODE = "PHOTO_MODE";
    public static final String PREFFILE = "PRINBIZ";
    public static final String PRINTER_MODEL = "PRINTER_MODEL";
    public static final int ROUTE1_RAPID = 101;
    public static final int ROUTE2_GENERAL = 102;
    public static final int ROUTE3_ID = 103;
    public static final int ROUTE_MOBILE = 1;
    public static final int ROUTE_SDCARD = 2;
    public static final String SCALL_SEL_ALL = "SCALL_SEL_ALL";
    public static final String SCALL_SEL_WILL = "SCALL_SEL_WILL";
    public static final String SIZE_DOWN_SEL_ALL = "SIZE_DOWN_SEL_ALL";
    public static final String SIZE_DOWN_WILL = "SIZE_DOWN_WILL";
    private SharedPreferences sp;

    public enum PHOTO_MODE {
        MODE_OUTPHOTO,
        MODE_DEFAULT,
        MODE_CLOUDALBUM
    }

    public JumpPreferenceKey(Context context) {
        this.sp = context.getSharedPreferences(PREFFILE, 0);
    }

    public JumpPreferenceKey(Context context, String preName) {
        this.sp = context.getSharedPreferences(preName, 0);
    }

    public void SetPreference(String prefKey, boolean prefValue) {
        Editor ed = this.sp.edit();
        ed.putBoolean(prefKey, prefValue);
        ed.commit();
    }

    public void SetPreference(String prefKey, String prefValue) {
        Editor ed = this.sp.edit();
        ed.putString(prefKey, prefValue);
        ed.commit();
    }

    public void SetPreference(String prefKey, int prefValue) {
        Editor ed = this.sp.edit();
        ed.putInt(prefKey, prefValue);
        ed.commit();
    }

    public void SetPreference(String prefKey, float prefValue) {
        Editor ed = this.sp.edit();
        ed.putFloat(prefKey, prefValue);
        ed.commit();
    }

    public void SetFilterValue(String prefKey, ArrayList<FilterColorValue> fcvl) {
        Editor ed = this.sp.edit();
        if (fcvl != null) {
            for (int j = 0; j < fcvl.size(); j += ROUTE_MOBILE) {
                FilterColorValue fcv = (FilterColorValue) fcvl.get(j);
                if (fcv != null && (fcv.HaveModifyHSLCValue() || fcv.HaveModifyRGBValue())) {
                    ed.putFloat(prefKey + "_" + j + "_Hue", fcv.m_fHue);
                    ed.putFloat(prefKey + "_" + j + "_Saturation", fcv.m_fSaturation);
                    ed.putFloat(prefKey + "_" + j + "_Light", fcv.m_fLight);
                    ed.putFloat(prefKey + "_" + j + "_Contrast", fcv.m_fContrast);
                    ed.putFloat(prefKey + "_" + j + "_Red", fcv.m_fRed);
                    ed.putFloat(prefKey + "_" + j + "_Green", fcv.m_fGreen);
                    ed.putFloat(prefKey + "_" + j + "_Blue", fcv.m_fBlue);
                }
            }
        }
        ed.commit();
    }

    public float GetFilterValue(String prefKey) {
        return this.sp.getFloat(prefKey, 0.0f);
    }

    public boolean GetStatePreference(String state) {
        return this.sp.getBoolean(state, false);
    }

    public int GetPathSelectedPref() {
        return this.sp.getInt(PATHSELECTED, 0);
    }

    public String GetPathPreference(String prefKey) {
        return this.sp.getString(prefKey, XmlPullParser.NO_NAMESPACE);
    }

    public String GetModelPreference() {
        return this.sp.getString(PRINTER_MODEL, XmlPullParser.NO_NAMESPACE);
    }

    public int GetIntPreference(String prefKey) {
        return this.sp.getInt(prefKey, 0);
    }

    public void CleanPreference() {
        try {
            Editor ed = this.sp.edit();
            ed.clear();
            ed.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
