package com.example.sonic.hitiprinter.HitiChunk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.example.sonic.hitiprinter.HitiChunk.HitiChunk.ChunkType;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.PackBits;
import com.example.sonic.hitiprinter.utility.PringoConvenientConst;
import java.io.File;
import org.apache.commons.net.telnet.TelnetOption;

public class HitiChunkUtility {
    public static boolean AddHiTiChunk(String strPath, Bitmap hitiChunkBitmap, ChunkType chunkType) {
        byte[] PackBitsByteArray = new PackBits().GetPackBitsMask(hitiChunkBitmap);
        if (PackBitsByteArray == null) {
            return false;
        }
        return new HitiChunk().SetHitiChunk(strPath, PackBitsByteArray, chunkType, hitiChunkBitmap.getWidth(), hitiChunkBitmap.getHeight());
    }

    public static byte[] GetHiTiChunk(String strPath) {
        String strFileExt = FileUtility.GetFileExt(strPath);
        if (strFileExt.equalsIgnoreCase(PringoConvenientConst.PRINBIZ_BORDER_EXT)) {
            return new HitiChunk().GetHitiChunk(strPath, ChunkType.PNG);
        }
        if (strFileExt.equalsIgnoreCase(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG) || strFileExt.equalsIgnoreCase(".jpeg")) {
            return new HitiChunk().GetHitiChunk(strPath, ChunkType.JPG);
        }
        return null;
    }

    public static boolean GetMaskFromFile(String strPath, Bitmap bmp, int iIdentifyColor) {
        byte[] byteArray = GetHiTiChunk(strPath);
        if (byteArray == null) {
            return false;
        }
        int i;
        int iSize = ((((byteArray[0] & TelnetOption.MAX_OPTION_VALUE) << 24) | ((byteArray[1] & TelnetOption.MAX_OPTION_VALUE) << 16)) | ((byteArray[2] & TelnetOption.MAX_OPTION_VALUE) << 8)) | (byteArray[3] & TelnetOption.MAX_OPTION_VALUE);
        byte[] packBitsByteArray = new byte[iSize];
        for (i = 4; i < byteArray.length; i++) {
            packBitsByteArray[i - 4] = byteArray[i];
        }
        byte[] alpha8Pixels = new PackBits().GetMaskFromPackBitsMask(packBitsByteArray, iSize);
        if (alpha8Pixels.length == 0) {
            return false;
        }
        int iWidth = bmp.getWidth();
        int iHeight = bmp.getHeight();
        int[] rowPixels = new int[bmp.getWidth()];
        for (i = 0; i < iHeight; i++) {
            for (int j = 0; j < iWidth; j++) {
                int iIndex = (i * iWidth) + j;
                if (alpha8Pixels[iIndex] != 0) {
                    rowPixels[j] = (alpha8Pixels[iIndex] << 24) | iIdentifyColor;
                } else {
                    rowPixels[j] = 0;
                }
            }
            bmp.setPixels(rowPixels, 0, iWidth, 0, i, iWidth, 1);
        }
        return true;
    }

    public static Uri SaveHitiBitmap2File(Context context, String strAlbumName, Bitmap bmp, Bitmap maskBmp) {
        if (!FileUtility.SDCardState()) {
            return null;
        }
        String strSaveName = FileUtility.GetNewName(PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG, PringoConvenientConst.NEW_FILE_NAME_EDIT);
        String strSavePath = Environment.getExternalStorageDirectory().getPath() + File.separator + strAlbumName;
        FileUtility.CreateFolder(strSavePath);
        strSavePath = strSavePath + "/" + strSaveName;
        BitmapMonitor.TrySystemGC();
        Uri retUri = FileUtility.SavePhoto(context, strSavePath, bmp, CompressFormat.JPEG);
        if (retUri == null) {
            return null;
        }
        if (maskBmp != null) {
            AddHiTiChunk(strSavePath, maskBmp, ChunkType.JPG);
        }
        Log.i("HitiChunkUtility", "save path " + strSavePath);
        return retUri;
    }
}
