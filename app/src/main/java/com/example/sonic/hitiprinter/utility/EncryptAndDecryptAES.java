package com.example.sonic.hitiprinter.utility;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import com.example.sonic.hitiprinter.jni.hello.Hello;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UploadInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UserInfo;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.net.telnet.TelnetOption;
import org.xmlpull.v1.XmlPullParser;

public class EncryptAndDecryptAES {
    private static byte[] EncryptAES(byte[] iv, byte[] key, byte[] text, String strPadding) {
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance(strPadding);
            mCipher.init(1, mSecretKeySpec, mAlgorithmParameterSpec);
            return mCipher.doFinal(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static String EncryptStr(String str, String iv, String key) {
        String strEncryptByte = null;
        try {
            strEncryptByte = Base64.encodeToString(EncryptAES(iv.getBytes("UTF-8"), key.getBytes("UTF-8"), str.getBytes("UTF-8"), "AES/CBC/PKCS5Padding"), 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strEncryptByte;
    }

    private static byte[] DecryptAES(byte[] iv, byte[] key, byte[] text, String strPadding) {
        try {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance(strPadding);
            mCipher.init(2, mSecretKeySpec, mAlgorithmParameterSpec);
            return mCipher.doFinal(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static String DecryptStr(String strEncrypt, String iv, String key) {
        String strDecryptByte = null;
        try {
            strDecryptByte = new String(DecryptAES(iv.getBytes("UTF-8"), key.getBytes("UTF-8"), Base64.decode(strEncrypt.getBytes("UTF-8"), 0), "AES/CBC/PKCS5Padding"), "UTF-8");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
        return strDecryptByte;
    }

    public static String EncryptStrNoPadding(String str, String iv, String key) {
        Log.e("iv", "=" + iv);
        Log.e("key", "=" + key);
        String strEncryptByte = null;
        try {
            byte[] EncryptByte = EncryptAES(iv.getBytes("UTF-8"), key.getBytes("UTF-8"), PadString(str).getBytes("UTF-8"), "AES/CBC/NoPadding");
            Log.e("EncryptByte", "=" + EncryptByte);
            strEncryptByte = Base64.encodeToString(EncryptByte, 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strEncryptByte;
    }

    public static String DecryptStrNoPadding(String strEncrypt, String iv, String key) {
        IllegalArgumentException e;
        UnsupportedEncodingException e2;
        String strDecryptByte = null;
        try {
            byte[] DecryptByte = DecryptAES(iv.getBytes("UTF-8"), key.getBytes("UTF-8"), Base64.decode(strEncrypt.getBytes("UTF-8"), 0), "AES/CBC/NoPadding");
            String strDecryptByte2 = new String(DecryptByte, "UTF-8");
            int iResize = 0;
            int i = 0;
            while (i < DecryptByte.length && DecryptByte[i] != 0) {
                try {
                    iResize++;
                    i++;
                } catch (IllegalArgumentException e3) {
                    e = e3;
                    strDecryptByte = strDecryptByte2;
                }
            }
            strDecryptByte = strDecryptByte2.substring(0, iResize);
        } catch (IllegalArgumentException e5) {
            e = e5;
            e.printStackTrace();
            return strDecryptByte;
        } catch (UnsupportedEncodingException e6) {
            e2 = e6;
            e2.printStackTrace();
            return strDecryptByte;
        }
        return strDecryptByte;
    }

    public static String SendToServer(String strEncrypt) {
        String strRet = strEncrypt;
        return strEncrypt.replace("+", "%2b").replace("/", "%2f");
    }

    public static String SendToURL(String strEncrypt) {
        String strRet = strEncrypt;
        return strEncrypt.replace("+", "-").replace("/", "_");
    }

    public static String MakeIVFromIMEI(Context context) {
        String strIV = MobileInfo.GetIMEI(context);
        if (strIV.length() > 16) {
            strIV = strIV.substring(0, 16);
        }
        if (strIV.contains("\"")) {
            strIV.replace("\"", XmlPullParser.NO_NAMESPACE);
        }
        if (strIV.contains("\n")) {
            strIV.replace("\n", XmlPullParser.NO_NAMESPACE);
        }
        int iIMEILength = strIV.length();
        for (int i = 0; i < 16 - iIMEILength; i++) {
            strIV = strIV + OADCItem.WATCH_TYPE_NON;
        }
        return strIV;
    }

    public static String MakeIVFromUser(Context context) {
        GlobalVariable_UserInfo GVUserInfo = new GlobalVariable_UserInfo(context);
        GVUserInfo.RestoreGlobalVariable();
        if (GVUserInfo.GetVerify() == 0) {
            return "0000000000000000";
        }
        GlobalVariable_UploadInfo GVUploadInfo = new GlobalVariable_UploadInfo(context);
        GVUploadInfo.RestoreGlobalVariable();
        String strIV = Pattern.compile("[.,\"\\?!:'@]").matcher(UserInfo.GetUP(context, GVUploadInfo.GetUploader()).first).replaceAll(XmlPullParser.NO_NAMESPACE);
        if (strIV.length() > 16) {
            strIV = strIV.substring(0, 16);
        }
        if (strIV.contains("\"")) {
            strIV.replace("\"", XmlPullParser.NO_NAMESPACE);
        }
        if (strIV.contains("\n")) {
            strIV.replace("\n", XmlPullParser.NO_NAMESPACE);
        }
        int iIMEILength = strIV.length();
        for (int i = 0; i < 16 - iIMEILength; i++) {
            strIV = strIV + OADCItem.WATCH_TYPE_NON;
        }
        return strIV;
    }

    private static String PadString(String source) {
        int padLength = 16 - (source.length() % 16);
        for (int i = 0; i < padLength; i++) {
            source = source + '\u0000';
        }
        return source;
    }

    public static String ReEncryptStr(Context context, String strAES, String iv, String key, String iv2, String key2) {
        if (strAES == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        if (strAES.length() == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        strAES = DecryptStr(strAES, iv, key);
        if (strAES != null) {
            EncryptStr(strAES, iv2, key2);
        }
        return strAES == null ? XmlPullParser.NO_NAMESPACE : strAES;
    }

    public static String MakeAESCount(Context context, int iCount, String strTimeStamp) {
        String strAESCount = "NULL";
        return EncryptStr(MobileInfo.GetIMEI(context) + "#" + strTimeStamp + EncryptStr(String.valueOf(iCount), Hello.SayGoodBye(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR), Hello.SayHello(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR)), Hello.SayGoodBye(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR), Hello.SayHello(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR));
    }

    public static String OpenAESCount(Context context, String strAESCount, String strTimeStamp) {
        if (strAESCount.length() == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String strDecryptIMEI = XmlPullParser.NO_NAMESPACE;
        String strDecryptTimeStamp = XmlPullParser.NO_NAMESPACE;
        strAESCount = DecryptStr(strAESCount, Hello.SayGoodBye(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR), Hello.SayHello(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR));
        if (strAESCount == null) {
            return XmlPullParser.NO_NAMESPACE;
        }
        if (!strAESCount.substring(0, MobileInfo.GetIMEI(context).length()).equals(MobileInfo.GetIMEI(context))) {
            return XmlPullParser.NO_NAMESPACE;
        }
        strAESCount = strAESCount.replace(MobileInfo.GetIMEI(context) + "#", XmlPullParser.NO_NAMESPACE);
        strDecryptTimeStamp = strAESCount.substring(0, strTimeStamp.length());
        if (strDecryptTimeStamp.equals(strTimeStamp)) {
            return strAESCount.substring(strDecryptTimeStamp.length());
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    public static String OpenAESCount(Context context, String strAESCount, String strTimeStamp, String iv, String key) {
        if (strAESCount.length() == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String strDecryptIMEI = XmlPullParser.NO_NAMESPACE;
        String strDecryptTimeStamp = XmlPullParser.NO_NAMESPACE;
        strAESCount = DecryptStr(strAESCount, iv, key);
        if (!strAESCount.substring(0, MobileInfo.GetIMEI(context).length()).equals(MobileInfo.GetIMEI(context))) {
            return XmlPullParser.NO_NAMESPACE;
        }
        strAESCount = strAESCount.replace(MobileInfo.GetIMEI(context) + "#", XmlPullParser.NO_NAMESPACE);
        strDecryptTimeStamp = strAESCount.substring(0, strTimeStamp.length());
        if (strDecryptTimeStamp.equals(strTimeStamp)) {
            return strAESCount.substring(strDecryptTimeStamp.length());
        }
        return XmlPullParser.NO_NAMESPACE;
    }

    public static String OpenAESCount(Context context, String strAESCount, String iv, String key) {
        if (strAESCount.length() == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String strDecryptTimeStamp = XmlPullParser.NO_NAMESPACE;
        strAESCount = DecryptStr(strAESCount, iv, key).replace(MobileInfo.GetIMEI(context) + "#", XmlPullParser.NO_NAMESPACE);
        return strAESCount.substring(strAESCount.substring(0, MobileInfo.GetTimeStamp().length()).length());
    }

    public static String OpenAESCountGetTime(Context context, String strAESCount, String iv, String key) {
        if (strAESCount.length() == 0) {
            return XmlPullParser.NO_NAMESPACE;
        }
        String strDecryptTimeStamp = XmlPullParser.NO_NAMESPACE;
        return DecryptStr(strAESCount, iv, key).replace(MobileInfo.GetIMEI(context) + "#", XmlPullParser.NO_NAMESPACE).substring(0, MobileInfo.GetTimeStamp().length());
    }

    public static String OpenCount(Context context, String strAESCount) {
        if (strAESCount.length() == 0) {
            return OADCItem.WATCH_TYPE_NON;
        }
        String strCount = DecryptStr(strAESCount, Hello.SayGoodBye(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR), Hello.SayHello(context, RequestState.REQUEST_COMPLETE_DUETO_CHECK_SUM_ERROR));
        return strCount == null ? OADCItem.WATCH_TYPE_NON : strCount;
    }

    public static String OpenCount(Context context, String strAESCount, String iv, String key) {
        if (strAESCount.length() == 0) {
            return OADCItem.WATCH_TYPE_NON;
        }
        String strCount = DecryptStr(strAESCount, iv, key);
        return strCount == null ? OADCItem.WATCH_TYPE_NON : strCount;
    }

    public static String MakeGoodString(String str) {
        String strBase64 = "AQgwBRhxCSiyDTjzEUk0FVl1GWm2HXn3IYo4JZp5Kaq6Lbr7Mcs8Ndt9OeuPfv";
        int iBase64Max = strBase64.length();
        char c1 = str.charAt(0);
        String strGoodString = String.valueOf(c1);
        int iRand = (c1 % 4) + 1;
        for (int i = 0; i < iRand; i++) {
            strGoodString = strGoodString + String.valueOf(strBase64.charAt((int) (Math.random() * ((double) iBase64Max))));
        }
        return strGoodString + str.substring(1, str.length());
    }

    public static String MakeMD5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                int val = b & TelnetOption.MAX_OPTION_VALUE;
                if (val < 16) {
                    hexString.append(OADCItem.WATCH_TYPE_NON);
                }
                hexString.append(Integer.toHexString(val));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return XmlPullParser.NO_NAMESPACE;
        }
    }

    public static void getHashCode(Context context) {
        try {
            for (Signature signature : context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures) {
                MessageDigest.getInstance("SHA").update(signature.toByteArray());
                Log.v("KeyHash int:", XmlPullParser.NO_NAMESPACE + signature.hashCode());
            }
        } catch (NameNotFoundException e) {
            Log.v("name not found:", e.toString());
        } catch (NoSuchAlgorithmException e2) {
            Log.v("no such an algorithm:", e2.toString());
        }
    }
}
