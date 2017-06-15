package com.example.sonic.hitiprinter.ui.drawview.garnishitem;

import com.example.sonic.hitiprinter.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.example.sonic.hitiprinter.utility.MathUtility;

public class FilterColorValue {
    public IMAGE_FILTER_TYPE m_FilterType;
    public float m_fBlue;
    public float m_fContrast;
    public float m_fGreen;
    public float m_fHue;
    public float m_fLight;
    public float m_fRed;
    public float m_fSaturation;
    public int m_iFilterName;

    public FilterColorValue() {
        this.m_fHue = 0.0f;
        this.m_fSaturation = 0.0f;
        this.m_fLight = 0.0f;
        this.m_fContrast = 0.0f;
        this.m_fRed = 0.0f;
        this.m_fGreen = 0.0f;
        this.m_fBlue = 0.0f;
        this.m_iFilterName = -1;
        this.m_FilterType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_NON;
    }

    public boolean HaveModifyHSLCValue() {
        if (MathUtility.IsZero(this.m_fHue) && MathUtility.IsZero(this.m_fSaturation) && MathUtility.IsZero(this.m_fLight) && MathUtility.IsZero(this.m_fContrast)) {
            return false;
        }
        return true;
    }

    public boolean HaveModifyRGBValue() {
        if (MathUtility.IsZero(this.m_fRed) && MathUtility.IsZero(this.m_fGreen) && MathUtility.IsZero(this.m_fBlue)) {
            return false;
        }
        return true;
    }

    public static boolean HaveModifyHSLCValue(float fHue, float fSaturation, float fLight, float fContrast) {
        if (MathUtility.IsZero(fHue) && MathUtility.IsZero(fSaturation) && MathUtility.IsZero(fLight) && MathUtility.IsZero(fContrast)) {
            return false;
        }
        return true;
    }

    public static boolean HaveModifyRGBValue(float fRed, float fGreen, float fBlue) {
        if (MathUtility.IsZero(fRed) && MathUtility.IsZero(fGreen) && MathUtility.IsZero(fBlue)) {
            return false;
        }
        return true;
    }
}
