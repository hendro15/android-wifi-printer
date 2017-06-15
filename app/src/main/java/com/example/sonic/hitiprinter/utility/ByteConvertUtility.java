package com.example.sonic.hitiprinter.utility;

import android.util.Log;
import org.apache.commons.net.telnet.TelnetOption;

public class ByteConvertUtility {
    public static int ByteToInt(byte[] array, int iStart, int iLength) {
        int iRet = 0;
        for (int i = 0; i < iLength; i++) {
            iRet |= (array[i + iStart] & TelnetOption.MAX_OPTION_VALUE) << (24 - (i * 8));
        }
        Log.e("ByteToInt", Integer.toHexString(iRet));
        return iRet;
    }

    public static byte[] IntToByte(int iValue) {
        byte[] array = new byte[4];
        for (int i = 0; i < 4; i++) {
            array[i] = (byte) ((iValue >> (24 - (i * 8))) & TelnetOption.MAX_OPTION_VALUE);
        }
        return array;
    }

    public static long ByteToLong(byte[] array, int iStart, int iLength) {
        long lRet = 0;
        for (int i = 0; i < iLength; i++) {
            lRet |= ((long) (array[i + iStart] & TelnetOption.MAX_OPTION_VALUE)) << (24 - (i * 8));
        }
        return lRet;
    }

    public static int String10ToInt16(String str) {
        byte[] b = new byte[4];
        for (int i = 0; i < str.length(); i += 2) {
            String s = str.substring(i, i + 2);
            Log.e("s", s);
            b[i / 2] = (byte) (Integer.parseInt(s, 16) & TelnetOption.MAX_OPTION_VALUE);
        }
        return ByteToInt(b, 0, b.length);
    }

    public static String ByteToString(byte[] byteBuf, int iStart, int iSize) {
        char[] str = new char[iSize];
        for (int i = 0; i < iSize; i++) {
            str[i] = (char) byteBuf[i];
        }
        return String.valueOf(str);
    }

    public static byte CheckBit(byte bCheck, byte bMask) {
        return (byte) (bCheck & bMask);
    }

    public static void EdienChange(byte[] array, int iStart, int iLength, byte[] byName) {
        for (int i = 0; i < iLength; i++) {
            if (i % 2 == 0) {
                byName[i] = array[(iStart + i) + 1];
            } else {
                byName[i] = array[(iStart + i) - 1];
            }
        }
    }

    public static short ByteToShort(byte[] array, int iStart, int iLength) {
        short iRet = (short) 0;
        for (int i = 0; i < iLength; i++) {
            iRet = (short) ((((short) (array[i + iStart] & TelnetOption.MAX_OPTION_VALUE)) << (8 - (i * 8))) | iRet);
        }
        Log.e("ByteToInt", Integer.toHexString(iRet));
        return iRet;
    }

    public static byte[] Short2Byte(int i) {
        return new byte[]{(byte) ((i >> 8) & TelnetOption.MAX_OPTION_VALUE), (byte) (i & TelnetOption.MAX_OPTION_VALUE)};
    }
}
