package com.example.sonic.hitiprinter.utility;

import android.content.Context;
import android.support.v4.view.PointerIconCompat;
import android.util.Pair;
import com.example.sonic.hitiprinter.AppInfo.AppInfo;
import com.example.sonic.hitiprinter.AppInfo.AppInfo.APP_MODE;
import com.example.sonic.hitiprinter.jni.hello.Hello;
import com.example.sonic.hitiprinter.jscommand.JSCommand;
import com.example.sonic.hitiprinter.printerprotocol.utility.BurnFWUtility;
import com.example.sonic.hitiprinter.trace.GlobalVariable_AppInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UploadInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UserInfo;
import com.example.sonic.hitiprinter.web.HitiWebPath;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public class UserInfo {

    /* renamed from: com.hiti.utility.UserInfo.1 */
    static /* synthetic */ class C04581 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE;

        static {
            $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE = new int[APP_MODE.values().length];
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINHOME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINGO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[APP_MODE.PRINBIZ.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static String ReEncryptEUploader(Context context, String strRet, String strCIV, String strCKey) {
        String strVerifyFlag = strRet.substring(0, 1);
        return strVerifyFlag + EncryptAndDecryptAES.EncryptStrNoPadding(EncryptAndDecryptAES.DecryptStrNoPadding(strRet.substring(1), strCIV, strCKey), Hello.SayGoodBye(context, 7894), Hello.SayHello(context, 7894));
    }

    public static String GetUploader(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        return GVUploadInfo.GetUploader();
    }

    public static String GetUser(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        Pair<String, String> pair = GetUP(context, GVUploadInfo.GetUploader());
        if (pair == null) {
            return null;
        }
        return (String) pair.first;
    }

    public static Pair<String, String> GetUP(Context context, String strEUploader) {
        if (!HaveUpLoader(strEUploader)) {
            return null;
        }
        String strUploader = XmlPullParser.NO_NAMESPACE;
        strEUploader = EncryptAndDecryptAES.DecryptStr(strEUploader, Hello.SayGoodBye(context, 7895), Hello.SayHello(context, 7895));
        if (strEUploader == null) {
            return null;
        }
        strUploader = strEUploader.replace(MobileInfo.GetIMEI(context), XmlPullParser.NO_NAMESPACE);
        if (strEUploader.length() == strUploader.length()) {
            return null;
        }
        JSCommand jsCommand = new JSCommand(context);
        String strUP = jsCommand.ParseUP(strUploader, Hello.SayGoodBye(context, 7894), Hello.SayHello(context, 7894));
        String strU = jsCommand.GetU(strUP);
        String strP = jsCommand.GetP(strUP);
        if (HaveUP(strU, strP)) {
            return new Pair(strU, strP);
        }
        return null;
    }

    public static boolean HaveLogin(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        if (GVUserInfo.GetVerify() == 1 && HaveUpLoader(GVUploadInfo.GetUploader())) {
            return true;
        }
        return false;
    }

    public static boolean HaveUpLoader(String strEUploader) {
        if (strEUploader == null || strEUploader.length() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean HaveUP(String strU, String strP) {
        boolean boRet = false;
        if (strU == null || strP == null) {
            return boRet;
        }
        if (!(strU.length() == 0 || strP.length() == 0)) {
            boRet = true;
        }
        return boRet;
    }

    public static String MakeAutoLoginString(Context context, String strIV, String strKey) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        boolean boAutoLogin = false;
        if (GVUserInfo.GetVerify() == 1) {
            boAutoLogin = true;
        }
        String strEUploader = GVUploadInfo.GetUploader();
        String strAutoLoginString = XmlPullParser.NO_NAMESPACE;
        String strU = XmlPullParser.NO_NAMESPACE;
        String strP = XmlPullParser.NO_NAMESPACE;
        Pair<String, String> pair = GetUP(context, strEUploader);
        if (pair == null) {
            return strAutoLoginString;
        }
        if (pair.first != null && ((String) pair.first).length() > 0) {
            strU = pair.first;
        }
        if (pair.second != null && ((String) pair.second).length() > 0) {
            strP = pair.second;
        }
        if (strU.length() <= 0) {
            return strAutoLoginString;
        }
        int i = 0;
        if (boAutoLogin) {
            i = 1;
        }
        return EncryptAndDecryptAES.SendToURL(EncryptAndDecryptAES.EncryptStr(strU + JSCommand.UP + strP + JSCommand.UP + String.valueOf(i), strIV, strKey));
    }

    public static String MakeAutoLoginString(Context context, String strIV, String strKey, boolean boAutoLogin) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        String strEUploader = GVUploadInfo.GetUploader();
        String strAutoLoginString = XmlPullParser.NO_NAMESPACE;
        String strU = XmlPullParser.NO_NAMESPACE;
        String strP = XmlPullParser.NO_NAMESPACE;
        Pair<String, String> pair = GetUP(context, strEUploader);
        if (pair == null) {
            return strAutoLoginString;
        }
        if (pair.first != null && ((String) pair.first).length() > 0) {
            strU = pair.first;
        }
        if (pair.second != null && ((String) pair.second).length() > 0) {
            strP = pair.second;
        }
        if (strU.length() <= 0) {
            return strAutoLoginString;
        }
        int i = 0;
        if (boAutoLogin) {
            i = 1;
        }
        return EncryptAndDecryptAES.SendToURL(EncryptAndDecryptAES.EncryptStr(strU + JSCommand.UP + strP + JSCommand.UP + String.valueOf(i), strIV, strKey));
    }

    public static void UserLogin(Context context, String strUploader) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GVUploadInfo.SetUploader(strUploader);
        GVUploadInfo.SetUploadE03(0);
        GVUploadInfo.SaveGlobalVariableForever();
        GVUserInfo.RestoreGlobalVariable();
        GVUserInfo.SetVerify(1);
        GVUserInfo.SaveGlobalVariableForever();
    }

    public static void UserLogin(Context context, String strRet, String strIV, String strKey) {
        if (JSCommand.GetVerify(strRet)) {
            strRet = ReEncryptEUploader(context, strRet, strIV, strKey);
            String strUploader = XmlPullParser.NO_NAMESPACE;
            strUploader = EncryptAndDecryptAES.EncryptStr(strRet + MobileInfo.GetIMEI(context), Hello.SayGoodBye(context, 7895), Hello.SayHello(context, 7895));
            GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
            GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
            GVUploadInfo.RestoreGlobalVariable();
            GVUploadInfo.SetUploader(strUploader);
            GVUploadInfo.SetUploadE03(0);
            GVUploadInfo.SaveGlobalVariableForever();
            GVUserInfo.RestoreGlobalVariable();
            GVUserInfo.SetVerify(1);
            GVUserInfo.SaveGlobalVariableForever();
        }
    }

    public static void FakeUserLogin(Context context, String strU, String strP) {
        String strRet = 1 + EncryptAndDecryptAES.EncryptStrNoPadding(strU + JSCommand.UP + strP, Hello.SayGoodBye(context, 7894), Hello.SayHello(context, 7894));
        String strUploader = XmlPullParser.NO_NAMESPACE;
        strUploader = EncryptAndDecryptAES.EncryptStr(strRet + MobileInfo.GetIMEI(context), Hello.SayGoodBye(context, 7895), Hello.SayHello(context, 7895));
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GVUploadInfo.SetUploader(strUploader);
        GVUploadInfo.SetUploadE03(0);
        GVUploadInfo.SaveGlobalVariableForever();
        GVUserInfo.RestoreGlobalVariable();
        GVUserInfo.SetVerify(1);
        GVUserInfo.SaveGlobalVariableForever();
    }

    public static void ChangeUserDueToOtherApp(Context context, String strUploader) {
        if (strUploader != null && strUploader.length() > 0) {
            UserLogin(context, strUploader);
        }
    }

    public static void UserLogout(Context context) {
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        GVUploadInfo.SetUploadE03(0);
        GVUploadInfo.SaveGlobalVariableForever();
        GVUserInfo.RestoreGlobalVariable();
        GVUserInfo.SetVerify(0);
        GVUserInfo.SaveGlobalVariableForever();
        BurnFWUtility.DeleteAllSDFW(context);
    }

    public static Pair<String, String> GetPublicPrintUP(Context context) {
        String strU = null;
        String strP = HitiWebPath.snapPassword;
        GlobalVariable_AppInfo GVAppInfo = new GlobalVariable_AppInfo(context);
        GVAppInfo.RestoreGlobalVariable();
        switch (C04581.$SwitchMap$com$hiti$AppInfo$AppInfo$APP_MODE[AppInfo.GetAppModeFromNumber(GVAppInfo.GetAppMode()).ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                strU = Hello.SayHello(context, 1104);
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                strU = Hello.SayHello(context, PointerIconCompat.TYPE_GRABBING);
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                strU = Hello.SayHello(context, PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW);
                break;
        }
        if (!(strU == null || strU.contains("@hiti"))) {
            strU = "AppTest@hiti.com";
        }
        if (HaveUP(strU, strP)) {
            return new Pair(strU, strP);
        }
        return null;
    }

    public static void SetCloudAlbumSortType(Context context, String strSortType) {
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        GVUserInfo.SetCloudAlbumSortType(strSortType);
        GVUserInfo.SaveGlobalVariable();
    }

    public static String GetCloudAlbumSortType(Context context) {
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        return GVUserInfo.GetCloudAlbumSortType();
    }
}
