package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.trace.GlobalVariable_SDFWInfo;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.PringoConvenientConst;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher.RS_TYPE;
import org.xmlpull.v1.XmlPullParser;

public class BurnFWUtility {
    public static final int FROM_ASSET = 0;
    public static final int FROM_SD = 1;

    public static String GetAppFWVersion(Context context, String strWirelessType) {
        int R_STRING_version = ResourceSearcher.getId(context, RS_TYPE.STRING, "version");
        int R_STRING_version_p232 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p232");
        int R_STRING_version_p520l = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p520l");
        int R_STRING_version_p750l = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p750l");
        int R_STRING_version_p310w = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p310w");
        int R_STRING_version_p461 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p461");
        int R_STRING_version_p530D = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p530D");
        String strUpdateVersion = context.getString(R_STRING_version).replace(".", XmlPullParser.NO_NAMESPACE);
        if (strWirelessType.equals(WirelessType.TYPE_P232)) {
            strUpdateVersion = context.getString(R_STRING_version_p232).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        if (strWirelessType.equals(WirelessType.TYPE_P520L)) {
            strUpdateVersion = context.getString(R_STRING_version_p520l).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        if (strWirelessType.equals(WirelessType.TYPE_P750L)) {
            strUpdateVersion = context.getString(R_STRING_version_p750l).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        if (strWirelessType.equals(WirelessType.TYPE_P310W)) {
            strUpdateVersion = context.getString(R_STRING_version_p310w).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        if (strWirelessType.equals(WirelessType.TYPE_P461)) {
            strUpdateVersion = context.getString(R_STRING_version_p461).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        if (strWirelessType.equals(WirelessType.TYPE_P530D)) {
            strUpdateVersion = context.getString(R_STRING_version_p530D).replace(".", XmlPullParser.NO_NAMESPACE);
        }
        return strUpdateVersion.substring(FROM_ASSET, 4);
    }

    public static String GetAppFWVersion(Context context, int ElementID, boolean boNumberFormat) {
        int R_STRING_version = ResourceSearcher.getId(context, RS_TYPE.STRING, "version");
        int R_STRING_version_p232 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p232");
        int R_STRING_version_p520l = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p520l");
        int R_STRING_version_p750l = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p750l");
        int R_STRING_version_p310w = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p310w");
        int R_STRING_version_p461 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p461");
        int R_STRING_version_p530D = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p530D");
        String strUpdateVersion = context.getString(R_STRING_version);
        if (ElementID == 5) {
            strUpdateVersion = context.getString(R_STRING_version_p232);
        }
        if (ElementID == 7) {
            strUpdateVersion = context.getString(R_STRING_version_p520l);
        }
        if (ElementID == 8) {
            strUpdateVersion = context.getString(R_STRING_version_p750l);
        }
        if (ElementID == 9) {
            strUpdateVersion = context.getString(R_STRING_version_p310w);
        }
        if (ElementID == 10) {
            strUpdateVersion = context.getString(R_STRING_version_p461);
        }
        if (ElementID == 11) {
            strUpdateVersion = context.getString(R_STRING_version_p530D);
        }
        if (boNumberFormat) {
            return RemoveFWFormat(strUpdateVersion);
        }
        return strUpdateVersion;
    }

    public static String RemoveFWFormat(String strFWVersion) {
        strFWVersion = strFWVersion.replace(".", XmlPullParser.NO_NAMESPACE);
        if (strFWVersion.length() >= 4) {
            return strFWVersion.substring(FROM_ASSET, 4);
        }
        return strFWVersion;
    }

    public static Pair<Integer, String> GetTheNewestFWVersion(Context context, int ElementID, boolean boNumberFormat) {
        String strAppFWVersion = GetAppFWVersion(context, ElementID, true);
        String strCurrentFWVersion = strAppFWVersion;
        int iCurrentFWFrom = FROM_ASSET;
        String strSDFWVersion = OADCItem.WATCH_TYPE_NON;
        GlobalVariable_SDFWInfo GVSDFWInfo = new GlobalVariable_SDFWInfo(context);
        GVSDFWInfo.RestoreGlobalVariable();
        if (GVSDFWInfo.GetSDFWVersion(ElementID).length() > 0) {
            strSDFWVersion = RemoveFWFormat(GVSDFWInfo.GetSDFWVersion(ElementID));
        }
        String strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH;
        if (ElementID == 5) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P232;
        }
        if (ElementID == 7) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P520L;
        }
        if (ElementID == 8) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P750L;
        }
        if (ElementID == 9) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P310W;
        }
        if (ElementID == 10) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P461;
        }
        if (ElementID == 11) {
            strSDFWPath = FileUtility.GetSDAppRootPath(context) + "/" + PringoConvenientConst.FIRMWARE_PATH_P530D;
        }
        if (FileUtility.GetFileSize(strSDFWPath) != 8388608) {
            strSDFWVersion = OADCItem.WATCH_TYPE_NON;
            GVSDFWInfo.SetSDFWVersion(ElementID, XmlPullParser.NO_NAMESPACE);
            GVSDFWInfo.SaveGlobalVariableForever();
        }
        if (Integer.parseInt(strSDFWVersion) > Integer.parseInt(strAppFWVersion)) {
            strCurrentFWVersion = strSDFWVersion;
            iCurrentFWFrom = FROM_SD;
        }
        if (!boNumberFormat) {
            if (!strSDFWVersion.equals(OADCItem.WATCH_TYPE_NON)) {
                GVSDFWInfo.RestoreGlobalVariable();
                strSDFWVersion = GVSDFWInfo.GetSDFWVersion(ElementID);
            }
            strAppFWVersion = GetAppFWVersion(context, ElementID, false);
            if (iCurrentFWFrom == FROM_SD) {
                strCurrentFWVersion = strSDFWVersion;
            } else {
                strCurrentFWVersion = strAppFWVersion;
            }
        }
        return new Pair(Integer.valueOf(iCurrentFWFrom), strCurrentFWVersion);
    }

    public static Pair<Integer, String> GetTheNewestFWVersion(Context context, String strWirelessType, boolean boNumberFormat) {
        int ElementID = 4;
        if (strWirelessType.equals(WirelessType.TYPE_P232)) {
            ElementID = 5;
        }
        if (strWirelessType.equals(WirelessType.TYPE_P520L)) {
            ElementID = 7;
        }
        if (strWirelessType.equals(WirelessType.TYPE_P750L)) {
            ElementID = 8;
        }
        if (strWirelessType.equals(WirelessType.TYPE_P310W)) {
            ElementID = 9;
        }
        if (strWirelessType.equals(WirelessType.TYPE_P461)) {
            ElementID = 10;
        }
        if (strWirelessType.equals(WirelessType.TYPE_P530D)) {
            ElementID = 11;
        }
        return GetTheNewestFWVersion(context, ElementID, boNumberFormat);
    }

    public static void DeleteSDFW(Context context, int ElementID) {
        GlobalVariable_SDFWInfo GVSDFWInfo = new GlobalVariable_SDFWInfo(context);
        GVSDFWInfo.RestoreGlobalVariable();
        GVSDFWInfo.SetSDFWVersion(ElementID, XmlPullParser.NO_NAMESPACE);
        GVSDFWInfo.SaveGlobalVariableForever();
        String strSDFWName = PringoConvenientConst.FIRMWARE_NAME;
        if (ElementID == 5) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P232;
        }
        if (ElementID == 7) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P520L;
        }
        if (ElementID == 8) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P750L;
        }
        if (ElementID == 9) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P310W;
        }
        if (ElementID == 10) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P461;
        }
        if (ElementID == 11) {
            strSDFWName = PringoConvenientConst.FIRMWARE_NAME_P530D;
        }
        String strSDAppRootPath = FileUtility.GetSDAppRootPath(context);
        if (strSDAppRootPath == null) {
            Log.e("HAHAHA strSDAppRootPath", "NULL");
        } else if (strSDAppRootPath.length() <= 0) {
            Log.e("HAHAHA strSDAppRootPath", "<= 0");
        } else {
            FileUtility.DeleteFile(strSDAppRootPath + "/" + PringoConvenientConst.ROOT_FIRMWARE + "/" + strSDFWName);
        }
    }

    public static void DeleteAllSDFW(Context context) {
        DeleteSDFW(context, 4);
        DeleteSDFW(context, 5);
        DeleteSDFW(context, 7);
        DeleteSDFW(context, 8);
        DeleteSDFW(context, 9);
        DeleteSDFW(context, 10);
        DeleteSDFW(context, 11);
    }
}
