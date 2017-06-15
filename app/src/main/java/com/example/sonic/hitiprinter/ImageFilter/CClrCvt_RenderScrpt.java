package com.example.sonic.hitiprinter.ImageFilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.Matrix4f;
import android.renderscript.RenderScript;
import android.renderscript.ScriptGroup;
import android.renderscript.ScriptIntrinsic3DLUT;
import android.renderscript.ScriptIntrinsicColorMatrix;
import android.renderscript.Type;
import android.renderscript.Type.Builder;
import android.support.v4.view.ViewCompat;
import java.io.InputStream;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.telnet.TelnetOption;

class CClrCvt_RenderScrpt {
    static int bDimX;
    static int bDimY;
    static int bDimZ;
    int[] buffer;
    int[] mBCTable;
    Allocation mCube;
    Matrix4f mMaxtrixSat;
    RenderScript mRS;

    static {
        bDimX = 17;
        bDimY = 17;
        bDimZ = 17;
    }

    @SuppressLint({"NewApi"})
    public CClrCvt_RenderScrpt(Context context) {
        this.buffer = new int[14739];
        this.mBCTable = new int[DNSConstants.FLAGS_RD];
        this.mMaxtrixSat = new Matrix4f();
        this.mRS = RenderScript.create(context);
        Builder tb = new Builder(this.mRS, Element.U8_4(this.mRS));
        tb.setX(bDimX);
        tb.setY(bDimY);
        tb.setZ(bDimZ);
        Type t = tb.create();
        this.mCube = Allocation.createTyped(this.mRS, t);
        this.mMaxtrixSat.loadIdentity();
        t.destroy();
    }

    public void clean() {
        this.buffer = null;
        this.mBCTable = null;
        this.mRS.finish();
        this.mCube.destroy();
        this.mMaxtrixSat = null;
    }

    protected boolean Get_BC_adj_Table(int tbsize, float dBrightness, float dContrast) {
        double dbLgamma;
        double dbLweight;
        double dbCgamma;
        double dbCweight;
        dBrightness = Math.min(100.0f, Math.max(-100.0f, dBrightness));
        dContrast = Math.min(100.0f, Math.max(-100.0f, dContrast));
        if (dBrightness > 0.0f) {
            dbLgamma = 2.1d;
            dbLweight = ((double) dBrightness) / 100.0d;
        } else {
            dbLgamma = 0.47619047619047616d;
            dbLweight = (-((double) dBrightness)) / 100.0d;
        }
        if (dContrast > 0.0f) {
            dbCgamma = 2.0d;
            dbCweight = ((double) dContrast) / 100.0d;
        } else {
            dbCgamma = 0.6666666666666666d;
            dbCweight = (-((double) dContrast)) / 100.0d;
        }
        this.mBCTable[0] = 0;
        this.mBCTable[tbsize - 1] = TelnetOption.MAX_OPTION_VALUE;
        for (int val = 1; val < tbsize - 1; val++) {
            double dbval = ((double) val) / ((double) (tbsize - 1));
            dbval += ((1.0d - dbval) - Math.pow(1.0d - dbval, dbLgamma)) * dbLweight;
            if (dbval > 0.5d) {
                dbval += ((1.0d - dbval) - (0.5d * Math.pow(2.0d * (1.0d - dbval), dbCgamma))) * dbCweight;
            } else {
                dbval += ((0.5d * Math.pow(2.0d * dbval, dbCgamma)) - dbval) * dbCweight;
            }
            this.mBCTable[val] = (int) Math.min(255.0d, Math.max(0.0d, dbval * 255.0d));
        }
        return true;
    }

    protected boolean LoadColorTable(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            if (in.available() < 14739) {
                return false;
            }
            byte[] buffer = new byte[14739];
            if (in.read(buffer) == -1) {
                return false;
            }
            in.close();
            int bDimXY = bDimX * bDimY;
            int[] dat = new int[((bDimX * bDimY) * bDimZ)];
            int i3 = 0;
            for (int x = 0; x < bDimX; x++) {
                for (int y = 0; y < bDimY; y++) {
                    int index = x + (bDimX * y);
                    for (int z = 0; z < bDimZ; z++) {
                        dat[index] = ((ViewCompat.MEASURED_STATE_MASK | (buffer[i3 + 2] & TelnetOption.MAX_OPTION_VALUE)) | ((buffer[i3 + 1] & TelnetOption.MAX_OPTION_VALUE) << 8)) | ((buffer[i3] & TelnetOption.MAX_OPTION_VALUE) << 16);
                        i3 += 3;
                        index += bDimXY;
                    }
                }
            }
            this.mCube.copyFromUnchecked(dat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    protected boolean LoadColorTableAndDoBCS(Context context, String fileName, float brightness, float contrast, float saturation) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            if (in.available() < 14739) {
                return false;
            }
            byte[] buffer = new byte[14739];
            if (in.read(buffer) == -1) {
                return false;
            }
            in.close();
            Get_BC_adj_Table(DNSConstants.FLAGS_RD, brightness, contrast);
            int bDimXY = bDimX * bDimY;
            int[] dat = new int[((bDimX * bDimY) * bDimZ)];
            int i3 = 0;
            for (int x = 0; x < bDimX; x++) {
                for (int y = 0; y < bDimY; y++) {
                    int index = x + (bDimX * y);
                    for (int z = 0; z < bDimZ; z++) {
                        dat[index] = ((ViewCompat.MEASURED_STATE_MASK | this.mBCTable[buffer[i3 + 2] & TelnetOption.MAX_OPTION_VALUE]) | (this.mBCTable[buffer[i3 + 1] & TelnetOption.MAX_OPTION_VALUE] << 8)) | (this.mBCTable[buffer[i3] & TelnetOption.MAX_OPTION_VALUE] << 16);
                        i3 += 3;
                        index += bDimXY;
                    }
                }
            }
            this.mCube.copyFromUnchecked(dat);
            saturation = Math.min(100.0f, Math.max(-100.0f, saturation));
            if (((int) saturation) == 0) {
                this.mMaxtrixSat.loadIdentity();
                return true;
            }
            float x2 = 1.0f + (saturation > 0.0f ? (3.0f * saturation) / 100.0f : saturation / 100.0f);
            float lumR = 0.3086f * (1.0f - x2);
            float lumG = 0.6094f * (1.0f - x2);
            float lumB = 0.082f * (1.0f - x2);
            this.mMaxtrixSat.loadIdentity();
            this.mMaxtrixSat.set(0, 0, lumR + x2);
            this.mMaxtrixSat.set(1, 0, lumG);
            this.mMaxtrixSat.set(2, 0, lumB);
            this.mMaxtrixSat.set(0, 1, lumR);
            this.mMaxtrixSat.set(1, 1, lumG + x2);
            this.mMaxtrixSat.set(2, 1, lumB);
            this.mMaxtrixSat.set(0, 2, lumR);
            this.mMaxtrixSat.set(1, 2, lumG);
            this.mMaxtrixSat.set(2, 2, lumB + x2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean SetBCSTable(float brightness, float contrast, float saturation) {
        Get_BC_adj_Table(bDimX, brightness, contrast);
        int[] dat = new int[((bDimX * bDimY) * bDimZ)];
        int i = 0;
        for (int z = 0; z < bDimZ; z++) {
            int r = this.mBCTable[z];
            for (int y = 0; y < bDimY; y++) {
                int g = this.mBCTable[y];
                int x = 0;
                while (x < bDimX) {
                    int i2 = i + 1;
                    dat[i] = ((ViewCompat.MEASURED_STATE_MASK | this.mBCTable[x]) | (g << 8)) | (r << 16);
                    x++;
                    i = i2;
                }
            }
        }
        saturation = Math.min(100.0f, Math.max(-100.0f, saturation));
        if (((int) saturation) == 0) {
            this.mMaxtrixSat.loadIdentity();
        } else {
            float x2 = 1.0f + (saturation > 0.0f ? (3.0f * saturation) / 100.0f : saturation / 100.0f);
            float lumR = 0.3086f * (1.0f - x2);
            float lumG = 0.6094f * (1.0f - x2);
            float lumB = 0.082f * (1.0f - x2);
            this.mMaxtrixSat.loadIdentity();
            this.mMaxtrixSat.set(0, 0, lumR + x2);
            this.mMaxtrixSat.set(1, 0, lumG);
            this.mMaxtrixSat.set(2, 0, lumB);
            this.mMaxtrixSat.set(0, 1, lumR);
            this.mMaxtrixSat.set(1, 1, lumG + x2);
            this.mMaxtrixSat.set(2, 1, lumB);
            this.mMaxtrixSat.set(0, 2, lumR);
            this.mMaxtrixSat.set(1, 2, lumG);
            this.mMaxtrixSat.set(2, 2, lumB + x2);
        }
        this.mCube.copyFromUnchecked(dat);
        this.buffer = null;
        return true;
    }

    @SuppressLint({"NewApi"})
    protected boolean Convert_Filter_and_BCS(Bitmap bitmapIn) {
        int nWidth = bitmapIn.getWidth();
        int nHeight = bitmapIn.getHeight();
        Allocation allocIn = Allocation.createFromBitmap(this.mRS, bitmapIn);
        Allocation allocOut = Allocation.createFromBitmap(this.mRS, bitmapIn);
        ScriptIntrinsic3DLUT Intrinsic3DLut = ScriptIntrinsic3DLUT.create(this.mRS, Element.U8_4(this.mRS));
        Intrinsic3DLut.setLUT(this.mCube);
        ScriptIntrinsicColorMatrix saturation = ScriptIntrinsicColorMatrix.create(this.mRS, Element.U8_4(this.mRS));
        saturation.setColorMatrix(this.mMaxtrixSat);
        ScriptGroup.Builder sBuilder = new ScriptGroup.Builder(this.mRS);
        Type connect = new Builder(this.mRS, Element.U8_4(this.mRS)).setX(nWidth).setY(nHeight).create();
        sBuilder = new ScriptGroup.Builder(this.mRS);
        sBuilder.addKernel(Intrinsic3DLut.getKernelID());
        sBuilder.addKernel(saturation.getKernelID());
        sBuilder.addConnection(connect, Intrinsic3DLut.getKernelID(), saturation.getKernelID());
        ScriptGroup sGroup = sBuilder.create();
        sGroup.setInput(Intrinsic3DLut.getKernelID(), allocIn);
        sGroup.setOutput(saturation.getKernelID(), allocOut);
        sGroup.execute();
        allocOut.copyTo(bitmapIn);
        connect.destroy();
        sGroup.destroy();
        Intrinsic3DLut.destroy();
        saturation.destroy();
        allocIn.destroy();
        allocOut.destroy();
        return true;
    }

    @SuppressLint({"NewApi"})
    protected boolean Convert_Filter(Bitmap bitmapIn) {
        Allocation allocIn = Allocation.createFromBitmap(this.mRS, bitmapIn);
        Allocation allocOut = Allocation.createFromBitmap(this.mRS, bitmapIn);
        ScriptIntrinsic3DLUT Intrinsic3DLut = ScriptIntrinsic3DLUT.create(this.mRS, Element.U8_4(this.mRS));
        Intrinsic3DLut.setLUT(this.mCube);
        Intrinsic3DLut.forEach(allocIn, allocOut);
        allocOut.copyTo(bitmapIn);
        allocIn.destroy();
        allocOut.destroy();
        Intrinsic3DLut.destroy();
        return true;
    }

    @SuppressLint({"NewApi"})
    protected boolean Convert_Matrix(Bitmap bitmapIn) {
        Allocation allocIn = Allocation.createFromBitmap(this.mRS, bitmapIn);
        Allocation allocOut = Allocation.createFromBitmap(this.mRS, bitmapIn);
        ScriptIntrinsicColorMatrix saturation = ScriptIntrinsicColorMatrix.create(this.mRS, Element.U8_4(this.mRS));
        saturation.setColorMatrix(this.mMaxtrixSat);
        saturation.forEach(allocIn, allocOut);
        allocOut.copyTo(bitmapIn);
        allocIn.destroy();
        allocOut.destroy();
        saturation.destroy();
        return true;
    }
}
