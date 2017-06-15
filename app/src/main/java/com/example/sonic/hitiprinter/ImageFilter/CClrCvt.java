package com.example.sonic.hitiprinter.ImageFilter;

import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.telnet.TelnetOption;

public class CClrCvt {
    int[] DstRGB;
    DEFRGBK P1_Pos;
    DEFRGBK P1_Val;
    DEFRGBK P2_Pos;
    DEFRGBK P2_Val;
    DEFRGBK P3_Pos;
    DEFRGBK P3_Val;
    DEFRGBK P4_Pos;
    DEFRGBK P4_Val;
    int[] buffer;
    int[] m_pTB1089;
    int[] m_pTB33;
    int[] m_pTB_256_8;

    public CClrCvt() {
        int i;
        this.buffer = new int[14739];
        this.m_pTB1089 = new int[17];
        this.m_pTB33 = new int[17];
        this.m_pTB_256_8 = new int[4352];
        this.DstRGB = new int[3];
        this.P1_Pos = new DEFRGBK();
        this.P2_Pos = new DEFRGBK();
        this.P3_Pos = new DEFRGBK();
        this.P4_Pos = new DEFRGBK();
        this.P1_Val = new DEFRGBK();
        this.P2_Val = new DEFRGBK();
        this.P3_Val = new DEFRGBK();
        this.P4_Val = new DEFRGBK();
        int X = 0;
        int X_256 = DNSConstants.FLAGS_RD;
        for (i = 0; i < 17; i++) {
            this.m_pTB1089[i] = i * 289;
            this.m_pTB33[i] = i * 17;
        }
        for (i = 1; i < 17; i++) {
            for (int j = 0; j < DNSConstants.FLAGS_RD; j++) {
                this.m_pTB_256_8[j + X_256] = this.m_pTB_256_8[j + X] + j;
            }
            X += DNSConstants.FLAGS_RD;
            X_256 += DNSConstants.FLAGS_RD;
        }
    }

    public void clean() {
        this.buffer = null;
        this.m_pTB1089 = null;
        this.m_pTB33 = null;
        this.m_pTB_256_8 = null;
        this.DstRGB = null;
        this.P1_Pos = null;
        this.P2_Pos = null;
        this.P3_Pos = null;
        this.P4_Pos = null;
        this.P1_Val = null;
        this.P2_Val = null;
        this.P3_Val = null;
        this.P4_Val = null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected String LoadColorTable(android.content.Context r8, String r9) {
        /*
        r7 = this;
        r4 = "";
        r1 = 0;
        r5 = 0;
        r6 = r8.getResources();	 Catch:{ Exception -> 0x0022 }
        r6 = r6.getAssets();	 Catch:{ Exception -> 0x0022 }
        r3 = r6.open(r9);	 Catch:{ Exception -> 0x0022 }
        r2 = r1;
    L_0x0011:
        r5 = r3.read();	 Catch:{ Exception -> 0x0027 }
        r6 = -1;
        if (r5 != r6) goto L_0x001a;
    L_0x0018:
        r1 = r2;
    L_0x0019:
        return r4;
    L_0x001a:
        r6 = r7.buffer;	 Catch:{ Exception -> 0x0027 }
        r1 = r2 + 1;
        r6[r2] = r5;	 Catch:{ Exception -> 0x0022 }
        r2 = r1;
        goto L_0x0011;
    L_0x0022:
        r0 = move-exception;
    L_0x0023:
        r0.printStackTrace();
        goto L_0x0019;
    L_0x0027:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hiti.ImageFilter.CClrCvt.LoadColorTable(android.content.Context, java.lang.String):java.lang.String");
    }

    protected boolean Convert(Bitmap src) {
        boolean boRet = true;
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] DstRGB = null;
        int[] OldRGB = null;
        int[] newBitmap = null;
        try {
            DstRGB = new int[3];
            OldRGB = new int[3];
            newBitmap = new int[bmWidth];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (boRet) {
            OldRGB[2] = TelnetOption.MAX_OPTION_VALUE;
            OldRGB[1] = TelnetOption.MAX_OPTION_VALUE;
            OldRGB[0] = TelnetOption.MAX_OPTION_VALUE;
            DstRGB[2] = 0;
            DstRGB[1] = 0;
            DstRGB[0] = 0;
            DstRGB = Interp33To256C3(OldRGB);
            for (int h = 0; h < bmHeight; h++) {
                src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
                for (int w = 0; w < bmWidth; w++) {
                    int r = (newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE;
                    int g = (newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE;
                    int b = newBitmap[w] & TelnetOption.MAX_OPTION_VALUE;
                    if (r != OldRGB[0] || g != OldRGB[1] || b != OldRGB[2]) {
                        OldRGB[0] = r;
                        OldRGB[1] = g;
                        OldRGB[2] = b;
                        DstRGB = Interp33To256C3_improve(DstRGB, OldRGB);
                    }
                    newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (DstRGB[0] << 16)) | (DstRGB[1] << 8)) | DstRGB[2];
                }
                src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            }
        }
        return boRet;
    }

    protected int[] Interp33To256C3(int[] SrcRGB) {
        int W1;
        int W2;
        int W3;
        int W4;
        DEFRGBK P1_Pos = new DEFRGBK();
        DEFRGBK P2_Pos = new DEFRGBK();
        DEFRGBK P3_Pos = new DEFRGBK();
        DEFRGBK P4_Pos = new DEFRGBK();
        DEFRGBK P1_Val = new DEFRGBK();
        DEFRGBK P2_Val = new DEFRGBK();
        DEFRGBK P3_Val = new DEFRGBK();
        DEFRGBK P4_Val = new DEFRGBK();
        P1_Pos.rgbRed = SrcRGB[0] >> 4;
        P1_Pos.rgbGreen = SrcRGB[1] >> 4;
        P1_Pos.rgbBlue = SrcRGB[2] >> 4;
        P4_Pos.rgbRed = P1_Pos.rgbRed + 1;
        P4_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
        P4_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
        int R_Weight = SrcRGB[0] & 15;
        int G_Weight = SrcRGB[1] & 15;
        int B_Weight = SrcRGB[2] & 15;
        if (SrcRGB[0] == 255) {
            R_Weight = 16;
        }
        if (SrcRGB[1] == 255) {
            G_Weight = 16;
        }
        if (SrcRGB[2] == 255) {
            B_Weight = 16;
        }
        if (R_Weight >= G_Weight) {
            if (G_Weight >= B_Weight) {
                W1 = (16 - R_Weight) << 8;
                W2 = (R_Weight - G_Weight) << 8;
                W3 = (G_Weight - B_Weight) << 8;
                W4 = B_Weight << 8;
                P2_Pos.rgbRed = P1_Pos.rgbRed + 1;
                P2_Pos.rgbGreen = P1_Pos.rgbGreen;
                P2_Pos.rgbBlue = P1_Pos.rgbBlue;
                P3_Pos.rgbRed = P1_Pos.rgbRed + 1;
                P3_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
                P3_Pos.rgbBlue = P1_Pos.rgbBlue;
            } else if (R_Weight >= B_Weight) {
                W1 = (16 - R_Weight) << 8;
                W2 = (R_Weight - B_Weight) << 8;
                W3 = (B_Weight - G_Weight) << 8;
                W4 = G_Weight << 8;
                P2_Pos.rgbRed = P1_Pos.rgbRed + 1;
                P2_Pos.rgbGreen = P1_Pos.rgbGreen;
                P2_Pos.rgbBlue = P1_Pos.rgbBlue;
                P3_Pos.rgbRed = P1_Pos.rgbRed + 1;
                P3_Pos.rgbGreen = P1_Pos.rgbGreen;
                P3_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
            } else {
                W1 = (16 - B_Weight) << 8;
                W2 = (B_Weight - R_Weight) << 8;
                W3 = (R_Weight - G_Weight) << 8;
                W4 = G_Weight << 8;
                P2_Pos.rgbRed = P1_Pos.rgbRed;
                P2_Pos.rgbGreen = P1_Pos.rgbGreen;
                P2_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
                P3_Pos.rgbRed = P1_Pos.rgbRed + 1;
                P3_Pos.rgbGreen = P1_Pos.rgbGreen;
                P3_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
            }
        } else if (R_Weight >= B_Weight) {
            W1 = (16 - G_Weight) << 8;
            W2 = (G_Weight - R_Weight) << 8;
            W3 = (R_Weight - B_Weight) << 8;
            W4 = B_Weight << 8;
            P2_Pos.rgbRed = P1_Pos.rgbRed;
            P2_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
            P2_Pos.rgbBlue = P1_Pos.rgbBlue;
            P3_Pos.rgbRed = P1_Pos.rgbRed + 1;
            P3_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
            P3_Pos.rgbBlue = P1_Pos.rgbBlue;
        } else if (G_Weight >= B_Weight) {
            W1 = (16 - G_Weight) << 8;
            W2 = (G_Weight - B_Weight) << 8;
            W3 = (B_Weight - R_Weight) << 8;
            W4 = R_Weight << 8;
            P2_Pos.rgbRed = P1_Pos.rgbRed;
            P2_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
            P2_Pos.rgbBlue = P1_Pos.rgbBlue;
            P3_Pos.rgbRed = P1_Pos.rgbRed;
            P3_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
            P3_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
        } else {
            W1 = (16 - B_Weight) << 8;
            W2 = (B_Weight - G_Weight) << 8;
            W3 = (G_Weight - R_Weight) << 8;
            W4 = R_Weight << 8;
            P2_Pos.rgbRed = P1_Pos.rgbRed;
            P2_Pos.rgbGreen = P1_Pos.rgbGreen;
            P2_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
            P3_Pos.rgbRed = P1_Pos.rgbRed;
            P3_Pos.rgbGreen = P1_Pos.rgbGreen + 1;
            P3_Pos.rgbBlue = P1_Pos.rgbBlue + 1;
        }
        int[] iArr = this.m_pTB1089;
        int i = P1_Pos.rgbBlue;
        int[] iArr2 = this.m_pTB33;
        int i2 = P1_Pos.rgbGreen;
        int Pos = ((iArr[i] + iArr2[i]) + P1_Pos.rgbRed) * 3;
        P1_Val.rgbRed = this.buffer[Pos] & TelnetOption.MAX_OPTION_VALUE;
        P1_Val.rgbGreen = this.buffer[Pos + 1] & TelnetOption.MAX_OPTION_VALUE;
        P1_Val.rgbBlue = this.buffer[Pos + 2] & TelnetOption.MAX_OPTION_VALUE;
        iArr = this.m_pTB1089;
        i = P2_Pos.rgbBlue;
        iArr2 = this.m_pTB33;
        i2 = P2_Pos.rgbGreen;
        Pos = ((iArr[i] + iArr2[i2]) + P2_Pos.rgbRed) * 3;
        P2_Val.rgbRed = this.buffer[Pos] & TelnetOption.MAX_OPTION_VALUE;
        P2_Val.rgbGreen = this.buffer[Pos + 1] & TelnetOption.MAX_OPTION_VALUE;
        P2_Val.rgbBlue = this.buffer[Pos + 2] & TelnetOption.MAX_OPTION_VALUE;
        iArr = this.m_pTB1089;
        i = P3_Pos.rgbBlue;
        iArr2 = this.m_pTB33;
        i2 = P3_Pos.rgbGreen;
        Pos = ((iArr[i] + iArr2[i2]) + P3_Pos.rgbRed) * 3;
        P3_Val.rgbRed = this.buffer[Pos] & TelnetOption.MAX_OPTION_VALUE;
        P3_Val.rgbGreen = this.buffer[Pos + 1] & TelnetOption.MAX_OPTION_VALUE;
        P3_Val.rgbBlue = this.buffer[Pos + 2] & TelnetOption.MAX_OPTION_VALUE;
        iArr = this.m_pTB1089;
        i = P4_Pos.rgbBlue;
        iArr2 = this.m_pTB33;
        i2 = P4_Pos.rgbGreen;
        Pos = ((iArr[i] + iArr2[i2]) + P4_Pos.rgbRed) * 3;
        P4_Val.rgbRed = this.buffer[Pos] & TelnetOption.MAX_OPTION_VALUE;
        P4_Val.rgbGreen = this.buffer[Pos + 1] & TelnetOption.MAX_OPTION_VALUE;
        P4_Val.rgbBlue = this.buffer[Pos + 2] & TelnetOption.MAX_OPTION_VALUE;
        int[] DstRGB = new int[3];
        DstRGB[0] = (((this.m_pTB_256_8[P1_Val.rgbRed + W1] + this.m_pTB_256_8[P2_Val.rgbRed + W2]) + this.m_pTB_256_8[P3_Val.rgbRed + W3]) + this.m_pTB_256_8[P4_Val.rgbRed + W4]) >> 4;
        DstRGB[1] = (((this.m_pTB_256_8[P1_Val.rgbGreen + W1] + this.m_pTB_256_8[P2_Val.rgbGreen + W2]) + this.m_pTB_256_8[P3_Val.rgbGreen + W3]) + this.m_pTB_256_8[P4_Val.rgbGreen + W4]) >> 4;
        DstRGB[2] = (((this.m_pTB_256_8[P1_Val.rgbBlue + W1] + this.m_pTB_256_8[P2_Val.rgbBlue + W2]) + this.m_pTB_256_8[P3_Val.rgbBlue + W3]) + this.m_pTB_256_8[P4_Val.rgbBlue + W4]) >> 4;
        return DstRGB;
    }

    protected int[] Interp33To256C3_improve(int[] DstRGB, int[] SrcRGB) {
        int W1;
        int W2;
        int W3;
        int W4;
        this.P1_Pos.rgbRed = SrcRGB[0] >> 4;
        this.P1_Pos.rgbGreen = SrcRGB[1] >> 4;
        this.P1_Pos.rgbBlue = SrcRGB[2] >> 4;
        this.P4_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
        this.P4_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
        this.P4_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
        int R_Weight = SrcRGB[0] & 15;
        int G_Weight = SrcRGB[1] & 15;
        int B_Weight = SrcRGB[2] & 15;
        if (SrcRGB[0] == TelnetOption.MAX_OPTION_VALUE) {
            R_Weight = 16;
        }
        if (SrcRGB[1] == TelnetOption.MAX_OPTION_VALUE) {
            G_Weight = 16;
        }
        if (SrcRGB[2] == TelnetOption.MAX_OPTION_VALUE) {
            B_Weight = 16;
        }
        if (R_Weight >= G_Weight) {
            if (G_Weight >= B_Weight) {
                W1 = (16 - R_Weight) << 8;
                W2 = (R_Weight - G_Weight) << 8;
                W3 = (G_Weight - B_Weight) << 8;
                W4 = B_Weight << 8;
                this.P2_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
                this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen;
                this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue;
                this.P3_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
                this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
                this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue;
            } else if (R_Weight >= B_Weight) {
                W1 = (16 - R_Weight) << 8;
                W2 = (R_Weight - B_Weight) << 8;
                W3 = (B_Weight - G_Weight) << 8;
                W4 = G_Weight << 8;
                this.P2_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
                this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen;
                this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue;
                this.P3_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
                this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen;
                this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
            } else {
                W1 = (16 - B_Weight) << 8;
                W2 = (B_Weight - R_Weight) << 8;
                W3 = (R_Weight - G_Weight) << 8;
                W4 = G_Weight << 8;
                this.P2_Pos.rgbRed = this.P1_Pos.rgbRed;
                this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen;
                this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
                this.P3_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
                this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen;
                this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
            }
        } else if (R_Weight >= B_Weight) {
            W1 = (16 - G_Weight) << 8;
            W2 = (G_Weight - R_Weight) << 8;
            W3 = (R_Weight - B_Weight) << 8;
            W4 = B_Weight << 8;
            this.P2_Pos.rgbRed = this.P1_Pos.rgbRed;
            this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
            this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue;
            this.P3_Pos.rgbRed = this.P1_Pos.rgbRed + 1;
            this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
            this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue;
        } else if (G_Weight >= B_Weight) {
            W1 = (16 - G_Weight) << 8;
            W2 = (G_Weight - B_Weight) << 8;
            W3 = (B_Weight - R_Weight) << 8;
            W4 = R_Weight << 8;
            this.P2_Pos.rgbRed = this.P1_Pos.rgbRed;
            this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
            this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue;
            this.P3_Pos.rgbRed = this.P1_Pos.rgbRed;
            this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
            this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
        } else {
            W1 = (16 - B_Weight) << 8;
            W2 = (B_Weight - G_Weight) << 8;
            W3 = (G_Weight - R_Weight) << 8;
            W4 = R_Weight << 8;
            this.P2_Pos.rgbRed = this.P1_Pos.rgbRed;
            this.P2_Pos.rgbGreen = this.P1_Pos.rgbGreen;
            this.P2_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
            this.P3_Pos.rgbRed = this.P1_Pos.rgbRed;
            this.P3_Pos.rgbGreen = this.P1_Pos.rgbGreen + 1;
            this.P3_Pos.rgbBlue = this.P1_Pos.rgbBlue + 1;
        }
        int Pos = ((this.m_pTB1089[this.P1_Pos.rgbBlue] + this.m_pTB33[this.P1_Pos.rgbGreen]) + this.P1_Pos.rgbRed) * 3;
        this.P1_Val.rgbRed = this.buffer[Pos];
        this.P1_Val.rgbGreen = this.buffer[Pos + 1];
        this.P1_Val.rgbBlue = this.buffer[Pos + 2];
        Pos = ((this.m_pTB1089[this.P2_Pos.rgbBlue] + this.m_pTB33[this.P2_Pos.rgbGreen]) + this.P2_Pos.rgbRed) * 3;
        this.P2_Val.rgbRed = this.buffer[Pos];
        this.P2_Val.rgbGreen = this.buffer[Pos + 1];
        this.P2_Val.rgbBlue = this.buffer[Pos + 2];
        Pos = ((this.m_pTB1089[this.P3_Pos.rgbBlue] + this.m_pTB33[this.P3_Pos.rgbGreen]) + this.P3_Pos.rgbRed) * 3;
        this.P3_Val.rgbRed = this.buffer[Pos];
        this.P3_Val.rgbGreen = this.buffer[Pos + 1];
        this.P3_Val.rgbBlue = this.buffer[Pos + 2];
        Pos = ((this.m_pTB1089[this.P4_Pos.rgbBlue] + this.m_pTB33[this.P4_Pos.rgbGreen]) + this.P4_Pos.rgbRed) * 3;
        this.P4_Val.rgbRed = this.buffer[Pos];
        this.P4_Val.rgbGreen = this.buffer[Pos + 1];
        this.P4_Val.rgbBlue = this.buffer[Pos + 2];
        DstRGB[0] = (((this.m_pTB_256_8[this.P1_Val.rgbRed + W1] + this.m_pTB_256_8[this.P2_Val.rgbRed + W2]) + this.m_pTB_256_8[this.P3_Val.rgbRed + W3]) + this.m_pTB_256_8[this.P4_Val.rgbRed + W4]) >> 4;
        DstRGB[1] = (((this.m_pTB_256_8[this.P1_Val.rgbGreen + W1] + this.m_pTB_256_8[this.P2_Val.rgbGreen + W2]) + this.m_pTB_256_8[this.P3_Val.rgbGreen + W3]) + this.m_pTB_256_8[this.P4_Val.rgbGreen + W4]) >> 4;
        DstRGB[2] = (((this.m_pTB_256_8[this.P1_Val.rgbBlue + W1] + this.m_pTB_256_8[this.P2_Val.rgbBlue + W2]) + this.m_pTB_256_8[this.P3_Val.rgbBlue + W3]) + this.m_pTB_256_8[this.P4_Val.rgbBlue + W4]) >> 4;
        return DstRGB;
    }
}
