package com.example.sonic.hitiprinter.HitiChunk;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.telnet.TelnetOption;

public class HitiChunk {
    private final int HITI_CHUNK_JPG_MARKER;
    private final String HITI_CHUNK_JPG_RESERVE;
    private final String HITI_CHUNK_JPG_SIGNATURE;

    public enum ChunkType {
        PNG,
        JPG
    }

    public HitiChunk() {
        this.HITI_CHUNK_JPG_SIGNATURE = "HitI_cHUNk_29126268";
        this.HITI_CHUNK_JPG_RESERVE = "000000000000000000000000";
        this.HITI_CHUNK_JPG_MARKER = 65505;
    }

    public boolean SetHitiChunk(String strPath, byte[] appendByte, ChunkType chunkType, int iWidth, int iHeight) {
        boolean boRet = false;
        byte[] byteArray = File2Bytes(strPath);
        if (byteArray == null) {
            return boRet;
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(strPath)));
            if (chunkType == ChunkType.PNG) {
                bos.write(byteArray, 0, byteArray.length - 12);
                boRet = SetHitiChunkPNG(bos, appendByte);
            } else if (chunkType == ChunkType.JPG) {
                boRet = SetHitiChunkJPG(bos, appendByte, iWidth, iHeight);
                bos.write(byteArray, 2, byteArray.length - 2);
            }
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boRet;
    }

    private boolean SetHitiChunkPNG(BufferedOutputStream bos, byte[] appendByte) {
        try {
            byte[] temp = new byte[]{(byte) (appendByte.length >> 24), (byte) (appendByte.length >> 16), (byte) (appendByte.length >> 8), (byte) (appendByte.length & TelnetOption.MAX_OPTION_VALUE)};
            bos.write(temp);
            temp[0] = (byte) 104;
            temp[1] = (byte) 105;
            temp[2] = (byte) 84;
            temp[3] = (byte) 73;
            bos.write(temp);
            bos.write(appendByte);
            int iCrc32 = CalculateCrc32(appendByte);
            temp[0] = (byte) (iCrc32 >> 24);
            temp[1] = (byte) (iCrc32 >> 16);
            temp[2] = (byte) (iCrc32 >> 8);
            temp[3] = (byte) (iCrc32 & TelnetOption.MAX_OPTION_VALUE);
            bos.write(temp);
            temp[0] = (byte) 0;
            temp[1] = (byte) 0;
            temp[2] = (byte) 0;
            temp[3] = (byte) 0;
            bos.write(temp);
            temp[0] = (byte) 73;
            temp[1] = (byte) 69;
            temp[2] = (byte) 78;
            temp[3] = (byte) 68;
            bos.write(temp);
            temp[0] = (byte) -82;
            temp[1] = (byte) 66;
            temp[2] = (byte) 96;
            temp[3] = (byte) -126;
            bos.write(temp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean SetHitiChunkJPG(BufferedOutputStream bos, byte[] appendByte, int iWidth, int iHeight) {
        int iBasicMetaSize = (((((("HitI_cHUNk_29126268".length() + 2) + 1) + 1) + 1) + 4) + 4) + 24;
        int iMaxDataSize = SupportMenu.USER_MASK - iBasicMetaSize;
        int iChunkCount = appendByte.length / iMaxDataSize;
        if (appendByte.length % iMaxDataSize > 0) {
            iChunkCount++;
        }
        try {
            byte[] iSize = new byte[4];
            byte[] temp = new byte[]{(byte) -1, (byte) -40};
            bos.write(temp);
            for (int i = 0; i < iChunkCount; i++) {
                temp[0] = (byte) -1;
                temp[1] = (byte) -31;
                bos.write(temp);
                int iDataSize = iMaxDataSize;
                if ((i + 1) * iMaxDataSize > appendByte.length) {
                    iDataSize = appendByte.length % iMaxDataSize;
                }
                iDataSize += iBasicMetaSize;
                temp[0] = (byte) (iDataSize >> 8);
                temp[1] = (byte) (iDataSize & TelnetOption.MAX_OPTION_VALUE);
                bos.write(temp);
                bos.write("HitI_cHUNk_29126268".getBytes());
                bos.write(1);
                bos.write(1);
                bos.write(1);
                iSize[0] = (byte) ((iWidth >> 24) & TelnetOption.MAX_OPTION_VALUE);
                iSize[1] = (byte) ((iWidth >> 16) & TelnetOption.MAX_OPTION_VALUE);
                iSize[2] = (byte) ((iWidth >> 8) & TelnetOption.MAX_OPTION_VALUE);
                iSize[3] = (byte) (iWidth & TelnetOption.MAX_OPTION_VALUE);
                bos.write(iSize);
                iSize[0] = (byte) ((iHeight >> 24) & TelnetOption.MAX_OPTION_VALUE);
                iSize[1] = (byte) ((iHeight >> 16) & TelnetOption.MAX_OPTION_VALUE);
                iSize[2] = (byte) ((iHeight >> 8) & TelnetOption.MAX_OPTION_VALUE);
                iSize[3] = (byte) (iHeight & TelnetOption.MAX_OPTION_VALUE);
                bos.write(iSize);
                Log.e("Set iWidth", String.valueOf(iWidth));
                Log.e("Set iHeight", String.valueOf(iHeight));
                bos.write("000000000000000000000000".getBytes());
                bos.write(appendByte, i * iMaxDataSize, iDataSize - iBasicMetaSize);
                Log.i("appendByte.length", String.valueOf(appendByte.length));
                Log.i("iChunkCount", String.valueOf(iChunkCount));
                Log.i("iDataSize", String.valueOf(iDataSize));
                Log.i("i*iMaxDataSize", String.valueOf(i * iMaxDataSize));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] GetHitiChunk(String strPath, ChunkType chunkType) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(new File(strPath)));
            InputStream inputStream;
            if (chunkType == ChunkType.PNG) {
                inputStream = is;
                return GetHitiChunkPNG(is);
            } else if (chunkType == ChunkType.JPG) {
                inputStream = is;
                return GetHitiChunkJPG(is);
            } else {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                inputStream = is;
                return null;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public byte[] GetHitiChunkPNG(InputStream is) {
        byte[] retByteArray = null;
        try {
            byte[] byteArray = InputStream2Bytes(is);
            Log.i("byteArray", String.valueOf(byteArray.length));
            if (IsPNG(byteArray)) {
                Log.i("IsPNG(byteArray)", "IsPNG(byteArray)");
                int iIndex = 8;
                int iMax = byteArray.length;
                while (iIndex < iMax) {
                    Log.i("lIndex", String.format("%x", new Object[]{Integer.valueOf(iIndex)}));
                    int iLength = GetPNGChunkLength(byteArray, iIndex);
                    Log.i("lLength", String.format("%x", new Object[]{Integer.valueOf(iLength)}));
                    iIndex += 4;
                    Log.i("iType", String.format("%x", new Object[]{Integer.valueOf(GetPNGChunkType(byteArray, iIndex))}));
                    iIndex += 4;
                    if (Integer.valueOf(GetPNGChunkType(byteArray, iIndex)).intValue() == 1751733321) {
                        if (iLength != 0) {
                            retByteArray = GetChnukData(byteArray, iIndex, iLength);
                        }
                        Log.i("Get chunk", "Get chunk");
                        return retByteArray;
                    }
                    iIndex = (iIndex + iLength) + 4;
                }
                return retByteArray;
            }
            Log.i("!IsPNG(byteArray)", "!IsPNG(byteArray)");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] GetHitiChunkJPG(InputStream is) {
        byte[] retByteArray = null;
        try {
            byte[] byteArray = InputStream2Bytes(is);
            Log.i("byteArray", String.valueOf(byteArray.length));
            if (IsJPG(byteArray)) {
                Log.i("IsJPG(byteArray)", "IsJPG(byteArray)");
                int iIndex = 0;
                int iMax = byteArray.length;
                int iBasicMetaSize = (((((("HitI_cHUNk_29126268".length() + 2) + 1) + 1) + 1) + 4) + 4) + "000000000000000000000000".length();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] iSize = new byte[4];
                while (iIndex < iMax) {
                    iIndex = SearchJPGChunk(byteArray, iIndex);
                    if (iIndex == -1) {
                        break;
                    }
                    int i = byteArray[iIndex] & TelnetOption.MAX_OPTION_VALUE;
                    int iLength = (i << 8) | (byteArray[iIndex + 1] & TelnetOption.MAX_OPTION_VALUE);
                    iIndex = ((iIndex + 1) + 1) + "HitI_cHUNk_29126268".length();
                    byte b = byteArray[iIndex];
                    iIndex++;
                    Log.i("iVersion", String.valueOf(b & TelnetOption.MAX_OPTION_VALUE));
                    b = byteArray[iIndex];
                    iIndex++;
                    Log.i("iType", String.valueOf(b & TelnetOption.MAX_OPTION_VALUE));
                    b = byteArray[iIndex];
                    iIndex++;
                    Log.i("iID", String.valueOf(b & TelnetOption.MAX_OPTION_VALUE));
                    int i2 = 0;
                    int iIndex2 = iIndex;
                    while (i2 < 4) {
                        iIndex = iIndex2 + 1;
                        iSize[i2] = byteArray[iIndex2];
                        i2++;
                        iIndex2 = iIndex;
                    }
                    int iWidth = ByteToInt(iSize, 0, 4);
                    i2 = 0;
                    while (i2 < 4) {
                        iIndex = iIndex2 + 1;
                        iSize[i2] = byteArray[iIndex2];
                        i2++;
                        iIndex2 = iIndex;
                    }
                    int iHeight = ByteToInt(iSize, 0, 4);
                    Log.e("Get iWidth", String.valueOf(iWidth));
                    Log.e("Get iHeight", String.valueOf(iHeight));
                    iIndex = iIndex2 + "000000000000000000000000".length();
                    if (iLength != 0) {
                        retByteArray = GetChnukData(byteArray, iIndex, iLength - iBasicMetaSize);
                    }
                    iIndex = (iIndex + iLength) - iBasicMetaSize;
                    try {
                        outputStream.write(retByteArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (retByteArray != null) {
                    retByteArray = outputStream.toByteArray();
                }
                return retByteArray;
            }
            Log.i("!IsJPG(byteArray)", "!IsJPG(byteArray)");
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean IsPNG(byte[] byteArray) {
        if (byteArray[0] == -119 && byteArray[1] == 80 && byteArray[2] == 78 && byteArray[3] == 71 && byteArray[4] == 13 && byteArray[5] == (byte) 10 && byteArray[6] == 26 && byteArray[7] == (byte) 10) {
            return true;
        }
        return false;
    }

    public boolean IsJPG(byte[] byteArray) {
        if (byteArray[0] == -1 && byteArray[1] == -40) {
            return true;
        }
        return false;
    }

    public boolean IsHitiJPGMarker(byte[] byteArray, int iStart) {
        int iSignatureLength = "HitI_cHUNk_29126268".length();
        byte[] SignatureByteArray = "HitI_cHUNk_29126268".getBytes();
        for (int i = 0; i < iSignatureLength; i++) {
            if (byteArray[iStart + i] != SignatureByteArray[i]) {
                return false;
            }
            Log.e("IsHitiJPGMarker", String.valueOf(byteArray[iStart + i]));
        }
        return true;
    }

    public int GetPNGChunkLength(byte[] byteArray, int iIndex) {
        return ((((byteArray[iIndex] & TelnetOption.MAX_OPTION_VALUE) << 24) | ((byteArray[iIndex + 1] & TelnetOption.MAX_OPTION_VALUE) << 16)) | ((byteArray[iIndex + 2] & TelnetOption.MAX_OPTION_VALUE) << 8)) | (byteArray[iIndex + 3] & TelnetOption.MAX_OPTION_VALUE);
    }

    public int GetPNGChunkType(byte[] byteArray, int iIndex) {
        return ((((byteArray[iIndex] & TelnetOption.MAX_OPTION_VALUE) << 24) | ((byteArray[iIndex + 1] & TelnetOption.MAX_OPTION_VALUE) << 16)) | ((byteArray[iIndex + 2] & TelnetOption.MAX_OPTION_VALUE) << 8)) | (byteArray[iIndex + 3] & TelnetOption.MAX_OPTION_VALUE);
    }

    public int SearchJPGChunk(byte[] byteArray, int iIndex) {
        while (iIndex < byteArray.length - 1) {
            Log.i("iIndex", String.format("%x", new Object[]{Integer.valueOf(iIndex)}));
            int iMarker = ((byteArray[iIndex] & TelnetOption.MAX_OPTION_VALUE) << 8) | (byteArray[iIndex + 1] & TelnetOption.MAX_OPTION_VALUE);
            iIndex = (iIndex + 1) + 1;
            Log.i("iMarker", String.format("%x", new Object[]{Integer.valueOf(iMarker)}));
            if (iMarker == 65498 || iMarker == 65497) {
                return -1;
            }
            int iLength;
            if (iMarker == 65496 || iMarker == 65281 || (iMarker >= 65488 && iMarker <= 65495)) {
                iLength = 0;
            } else if (iMarker == SupportMenu.USER_MASK) {
                iIndex -= 2;
                iLength = 1;
            } else {
                iLength = ((byteArray[iIndex] & TelnetOption.MAX_OPTION_VALUE) << 8) | (byteArray[iIndex + 1] & TelnetOption.MAX_OPTION_VALUE);
            }
            Log.i("iLength", String.format("%x", new Object[]{Integer.valueOf(iLength)}));
            if (iMarker == 65505) {
                int i = iIndex + 2;
                if ("HitI_cHUNk_29126268".length() + i > byteArray.length) {
                    return -1;
                }
                if (IsHitiJPGMarker(byteArray, i)) {
                    Log.i("IsHitiJPGMarker", "IsHitiJPGMarker");
                    return iIndex;
                }
                iIndex += iLength;
            } else {
                iIndex += iLength;
            }
        }
        return -1;
    }

    public byte[] GetChnukData(byte[] b, int offset, int length) {
        byte[] sub = new byte[length];
        for (int i = offset; i < offset + length; i++) {
            sub[i - offset] = b[i];
        }
        return sub;
    }

    public byte[] InputStream2Bytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[Util.DEFAULT_COPY_BUFFER_SIZE];
        while (true) {
            int len = is.read(buffer);
            if (len == -1) {
                return byteBuffer.toByteArray();
            }
            byteBuffer.write(buffer, 0, len);
        }
    }

    public byte[] Bitmap2Bytes(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public byte[] File2Bytes(String strPath) {
        byte[] byteArray;
        InputStream inputStream;
        IOException e;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(new File(strPath)));
            try {
                byteArray = InputStream2Bytes(is);
                inputStream = is;
            } catch (IOException e2) {
                e = e2;
                inputStream = is;
                byteArray = null;
                e.printStackTrace();
                return byteArray;
            }
        } catch (IOException e3) {
            e = e3;
            byteArray = null;
            e.printStackTrace();
            return byteArray;
        }
        return byteArray;
    }

    public boolean Bytes2File(String strPath, byte[] byteArray) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(strPath)));
            bos.write(byteArray);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int CalculateCrc32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return (int) crc.getValue();
    }

    public int ByteToInt(byte[] array, int iStart, int iLength) {
        int iRet = 0;
        for (int i = 0; i < iLength; i++) {
            iRet |= (array[i + iStart] & TelnetOption.MAX_OPTION_VALUE) << (24 - (i * 8));
        }
        return iRet;
    }
}
