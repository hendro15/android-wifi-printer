package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.utility.resource.ResourceId;
import com.example.sonic.hitiprinter.utility.resource.ResourceId.Page;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class PrinterInfo {
    public static final int PRINTER_FRAME_TYPE_2x3 = 1;
    public static final int PRINTER_FRAME_TYPE_4x6 = 2;
    public static final int PRINTER_FRAME_TYPE_5x7 = 3;
    public static final int PRINTER_FRAME_TYPE_6x6 = 6;
    public static final int PRINTER_FRAME_TYPE_6x8 = 4;
    public static final int PRINTER_FRAME_TYPE_6x8_2up = 5;

    public interface Callback {
        String setModelTextP310W();

        String setModelTextP520L();

        String setModelTextP530D();

        String setModelTextP750L();
    }

    public static ArrayList<Byte> SetFrameType(String strProductID) {
        ArrayList<Byte> mFrameTypeList = new ArrayList();
        if (strProductID.equals(WirelessType.TYPE_P231) || strProductID.equals(WirelessType.TYPE_P232)) {
            mFrameTypeList.add(Byte.valueOf((byte) 1));
        } else if (strProductID.equals(WirelessType.TYPE_P310W) || strProductID.equals(WirelessType.TYPE_P461)) {
            mFrameTypeList.add(Byte.valueOf((byte) 2));
        } else if (strProductID.equals(WirelessType.TYPE_P520L) || strProductID.equals(WirelessType.TYPE_P750L)) {
            mFrameTypeList.add(Byte.valueOf((byte) 2));
            mFrameTypeList.add(Byte.valueOf((byte) 3));
            mFrameTypeList.add(Byte.valueOf((byte) 4));
        } else if (strProductID.equals(WirelessType.TYPE_P530D)) {
            mFrameTypeList.add(Byte.valueOf((byte) 5));
            mFrameTypeList.add(Byte.valueOf((byte) 6));
            mFrameTypeList.add(Byte.valueOf((byte) 4));
        }
        return mFrameTypeList;
    }

    public static int ChangeProductIDValueForServer(String strProductID) {
        if (strProductID.equals(WirelessType.TYPE_P232)) {
            return PRINTER_FRAME_TYPE_4x6;
        }
        if (strProductID.equals(WirelessType.TYPE_P520L)) {
            return PRINTER_FRAME_TYPE_5x7;
        }
        if (strProductID.equals(WirelessType.TYPE_P310W)) {
            return PRINTER_FRAME_TYPE_6x8;
        }
        if (strProductID.equals(WirelessType.TYPE_P750L)) {
            return PRINTER_FRAME_TYPE_6x8_2up;
        }
        if (strProductID.equals(WirelessType.TYPE_P461)) {
            return 7;
        }
        if (strProductID.equals(WirelessType.TYPE_P530D)) {
            return 8;
        }
        if (strProductID.equals(WirelessType.TYPE_P525L)) {
            return 10;
        }
        return PRINTER_FRAME_TYPE_2x3;
    }

    public static byte GetMediaSize(int iPaperType) {
        switch (iPaperType) {
            case PRINTER_FRAME_TYPE_2x3 /*1*/:
                return (byte) 5;
            case PRINTER_FRAME_TYPE_4x6 /*2*/:
                return (byte) 1;
            case PRINTER_FRAME_TYPE_5x7 /*3*/:
                return (byte) 2;
            case PRINTER_FRAME_TYPE_6x8 /*4*/:
            case PRINTER_FRAME_TYPE_6x6 /*6*/:
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return (byte) 3;
            default:
                return (byte) -1;
        }
    }

    public static byte GetPrintLayout(int iPaperType) {
        switch (iPaperType) {
            case PRINTER_FRAME_TYPE_2x3 /*1*/:
                return (byte) 5;
            case PRINTER_FRAME_TYPE_4x6 /*2*/:
                return (byte) 1;
            case PRINTER_FRAME_TYPE_5x7 /*3*/:
                return (byte) 2;
            case PRINTER_FRAME_TYPE_6x8 /*4*/:
                return (byte) 3;
            case PRINTER_FRAME_TYPE_6x6 /*6*/:
                return (byte) 19;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return (byte) 8;
            default:
                return (byte) -1;
        }
    }

    public static String GetPrintoutItem(Context context, int iPaperType) {
        ResourceId mRID = new ResourceId(context, Page.MobileUtility);
        switch (iPaperType) {
            case PRINTER_FRAME_TYPE_2x3 /*1*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_2x3);
            case PRINTER_FRAME_TYPE_4x6 /*2*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_4x6);
            case PRINTER_FRAME_TYPE_5x7 /*3*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_5x7);
            case PRINTER_FRAME_TYPE_6x8 /*4*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x8);
            case PRINTER_FRAME_TYPE_6x6 /*6*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x8_2up);
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x6);
            default:
                return XmlPullParser.NO_NAMESPACE;
        }
    }

    public static int AdjustPaperType(int iPaperType, boolean bDuplex) {
        if (!bDuplex) {
            return iPaperType;
        }
        switch (iPaperType) {
            case PRINTER_FRAME_TYPE_6x8 /*4*/:
                return PRINTER_FRAME_TYPE_6x8_2up;
            case PRINTER_FRAME_TYPE_6x6 /*6*/:
                return 7;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return 9;
            default:
                return iPaperType;
        }
    }

    public static String GetFrameName(Context context, byte iFrameType) {
        ResourceId mRID = new ResourceId(context, Page.MobileUtility);
        switch (iFrameType) {
            case PRINTER_FRAME_TYPE_2x3 /*1*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_2x3);
            case PRINTER_FRAME_TYPE_4x6 /*2*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_4x6);
            case PRINTER_FRAME_TYPE_5x7 /*3*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_5x7);
            case PRINTER_FRAME_TYPE_6x8 /*4*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x8);
            case PRINTER_FRAME_TYPE_6x8_2up /*5*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x8_2up);
            case PRINTER_FRAME_TYPE_6x6 /*6*/:
                return context.getString(mRID.R_STRING_PRINT_OUT_6x6);
            default:
                return XmlPullParser.NO_NAMESPACE;
        }
    }

    public static int GetPaperTypeByName(String strTypeName) {
        if (strTypeName.equals("2x3")) {
            return PRINTER_FRAME_TYPE_2x3;
        }
        if (strTypeName.equals("4x6")) {
            return PRINTER_FRAME_TYPE_4x6;
        }
        if (strTypeName.equals("5x7")) {
            return PRINTER_FRAME_TYPE_5x7;
        }
        if (strTypeName.equals("6x8")) {
            return PRINTER_FRAME_TYPE_6x8;
        }
        if (strTypeName.equals("6x6")) {
            return 8;
        }
        if (strTypeName.equals("6x8 2up")) {
            return PRINTER_FRAME_TYPE_6x6;
        }
        return 0;
    }

    public static String getPrinterModelCode(Context context) {
        return new JumpPreferenceKey(context).GetModelPreference();
    }

    public static String getPrinterModelName(Context context, Callback callback) {
        String strModel = getPrinterModelCode(context);
        if (strModel.equals(WirelessType.TYPE_P310W)) {
            return callback.setModelTextP310W();
        }
        if (strModel.equals(WirelessType.TYPE_P520L)) {
            return callback.setModelTextP520L();
        }
        if (strModel.equals(WirelessType.TYPE_P750L)) {
            return callback.setModelTextP750L();
        }
        if (strModel.equals(WirelessType.TYPE_P530D)) {
            return callback.setModelTextP530D();
        }
        return XmlPullParser.NO_NAMESPACE;
    }
}
