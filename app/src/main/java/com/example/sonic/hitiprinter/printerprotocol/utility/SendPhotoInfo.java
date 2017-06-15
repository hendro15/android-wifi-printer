package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.graphics.Bitmap;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class SendPhotoInfo {
    ArrayList<Bitmap> mBmpList;
    ArrayList<String> mPrintPhotoBufferList;
    String mPrinterModel;
    boolean mbDuplex;
    boolean mbSelTexture;
    byte mbyMediaSize;
    byte mbyPrintQTY;
    byte mbyPrintout;
    byte mbySharepen;
    int miPaperType;

    public SendPhotoInfo(String mPrinterModel) {
        this.mPrinterModel = null;
        this.mbSelTexture = false;
        this.mbDuplex = false;
        this.mbyMediaSize = (byte) -1;
        this.mbyPrintout = (byte) -1;
        this.mbyPrintQTY = (byte) -1;
        this.mbySharepen = (byte) -1;
        this.miPaperType = 0;
        this.mPrintPhotoBufferList = null;
        this.mBmpList = null;
        this.mPrinterModel = mPrinterModel;
        this.mPrintPhotoBufferList = new ArrayList();
    }

    public void SetPhotoFace(boolean mbSelTexture, byte mbyPrintQTY, byte mbySharepen) {
        this.mbSelTexture = mbSelTexture;
        this.mbyPrintQTY = mbyPrintQTY;
        this.mbySharepen = mbySharepen;
    }

    public void SetPhotoSize(byte mbyMediaSize, byte mbyPrintout, boolean mbDuplex, int miPaperType) {
        this.mbyMediaSize = mbyMediaSize;
        this.mbyPrintout = mbyPrintout;
        this.mbDuplex = mbDuplex;
        this.miPaperType = miPaperType;
    }

    public boolean GetTexture() {
        return this.mbSelTexture;
    }

    public boolean GetDuplex() {
        return this.mbDuplex;
    }

    public byte GetMediaSize() {
        return this.mbyMediaSize;
    }

    public byte GetPrintout() {
        return this.mbyPrintout;
    }

    public byte GetQuality() {
        return this.mbyPrintQTY;
    }

    public byte GetSharpen() {
        return this.mbySharepen;
    }

    public int GetPaperType() {
        return this.miPaperType;
    }

    public String GetPrinterModel() {
        return this.mPrinterModel;
    }

    public int GetNumber() {
        return this.mbDuplex ? this.miPaperType == 6 ? 4 : 2 : this.miPaperType != 6 ? 1 : 2;
    }

    public String[] GetPrintPhotoPath(ArrayList<String> strPhotoPathList, int iQueueSize) {
        if (this.mPrinterModel.equals(WirelessType.TYPE_P530D)) {
            return AddPhotoToBuffer(strPhotoPathList, iQueueSize);
        }
        return new String[]{(String) strPhotoPathList.get(iQueueSize)};
    }

    private String[] AddPhotoToBuffer(ArrayList<String> strPhotoPathList, int iQueueSize) {
        int i;
        int size = GetNumber();
        int length = strPhotoPathList.size();
        if (length < size) {
            for (i = length; i < size; i++) {
                strPhotoPathList.add(XmlPullParser.NO_NAMESPACE);
            }
        }
        if (size == 4) {
            int j;
            String[][] tmp = (String[][]) Array.newInstance(String.class, new int[]{2, 2});
            for (i = 0; i < 2; i++) {
                for (j = 0; j < 2; j++) {
                    tmp[i][j] = (String) strPhotoPathList.get(0);
                    strPhotoPathList.remove(0);
                }
            }
            for (j = 0; j < 2; j++) {
                for (i = 0; i < 2; i++) {
                    this.mPrintPhotoBufferList.add(tmp[i][j]);
                }
            }
        } else if (size == 2) {
            for (i = 0; i < size; i++) {
                this.mPrintPhotoBufferList.add(strPhotoPathList.get(0));
                strPhotoPathList.remove(0);
            }
        } else if (size == 1) {
            this.mPrintPhotoBufferList.add(strPhotoPathList.get(0));
            strPhotoPathList.remove(0);
        }
        return GetBuffer(size, iQueueSize);
    }

    public void RemoveBuffer() {
        for (int i = 0; i < GetNumber(); i++) {
            this.mPrintPhotoBufferList.remove(0);
        }
    }

    public void Remove(int size) {
        for (int i = 0; i < size; i++) {
            this.mPrintPhotoBufferList.remove(0);
        }
    }

    public boolean IsEmpty() {
        return this.mPrintPhotoBufferList.isEmpty();
    }

    public void Copy(String[] list, boolean duplex) {
        this.mPrintPhotoBufferList.clear();
        this.mBmpList = new ArrayList();
        for (String path : list) {
            this.mPrintPhotoBufferList.add(path);
        }
        this.mbDuplex = duplex;
    }

    public String[] GetBuffer() {
        int size = this.mPrintPhotoBufferList.size();
        String[] pathList = new String[size];
        for (int i = 0; i < size; i++) {
            pathList[i] = (String) this.mPrintPhotoBufferList.get(i);
        }
        return pathList;
    }

    public String[] GetBuffer(int size, int iQueueSize) {
        int iStart = size * iQueueSize;
        if (size > this.mPrintPhotoBufferList.size()) {
            return null;
        }
        String[] strArr = new String[size];
        int i = iStart;
        int k = 0;
        while (i < size + iStart) {
            int k2 = k + 1;
            strArr[k] = (String) this.mPrintPhotoBufferList.get(i);
            i++;
            k = k2;
        }
        return strArr;
    }

    public void AddBitmap(Bitmap bmp) {
        this.mBmpList.add(bmp);
    }

    public Bitmap GetBitmap(int id) {
        if (id >= this.mBmpList.size()) {
            return null;
        }
        return (Bitmap) this.mBmpList.get(id);
    }
}
