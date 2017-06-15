package com.example.sonic.hitiprinter.ImageFilter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.jumpinfo.JumpInfo;
//import com.hiti.prinbiz.BorderDelFragment;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.example.sonic.hitiprinter.utility.grid.ImageAdapter;
import java.lang.reflect.Array;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.smtp.SMTP;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.tftp.TFTPClient;
import org.apache.commons.net.time.TimeUDPClient;
import org.apache.commons.net.whois.WhoisClient;
import org.kxml2.wap.Wbxml;

public class ImageFilter {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE = null;
    private static double[] DELTA_INDEX = null;
    private static final String m_RAWPath = "RAW_PRINGO/";
    private Context mContext;
    Bitmap m_bmp;

    public enum IMAGE_FILTER_TYPE {
        IMAGE_FILTER_TYPE_NON,
        IMAGE_FILTER_TYPE_GaussianFilter,
        IMAGE_FILTER_TYPE_UnSharpMask,
        IMAGE_FILTER_TYPE_IP1,
        IMAGE_FILTER_TYPE_IP2,
        IMAGE_FILTER_TYPE_IP3,
        IMAGE_FILTER_TYPE_IP4,
        IMAGE_FILTER_TYPE_IP5,
        IMAGE_FILTER_TYPE_IP6,
        IMAGE_FILTER_TYPE_IP7,
        IMAGE_FILTER_TYPE_IP8,
        IMAGE_FILTER_TYPE_IP9,
        IMAGE_FILTER_TYPE_IP10,
        IMAGE_FILTER_TYPE_IP11,
        IMAGE_FILTER_TYPE_IP12,
        IMAGE_FILTER_TYPE_IP13,
        IMAGE_FILTER_TYPE_IP14,
        IMAGE_FILTER_TYPE_IP15,
        IMAGE_FILTER_TYPE_IP16,
        IMAGE_FILTER_TYPE_IP17,
        IMAGE_FILTER_TYPE_IP18,
        IMAGE_FILTER_TYPE_IP19,
        IMAGE_FILTER_TYPE_IP20,
        IMAGE_FILTER_TYPE_IP21,
        IMAGE_FILTER_TYPE_IP22,
        IMAGE_FILTER_TYPE_IP23,
        IMAGE_FILTER_TYPE_IP24,
        IMAGE_FILTER_TYPE_IP25,
        IMAGE_FILTER_TYPE_IP_Single_R,
        IMAGE_FILTER_TYPE_IP_Single_G,
        IMAGE_FILTER_TYPE_IP_Single_B,
        IMAGE_FILTER_TYPE_IP_Single_Y,
        IMAGE_FILTER_TYPE_IP_Single_M,
        IMAGE_FILTER_TYPE_GRAY1,
        IMAGE_FILTER_TYPE_GRAY2,
        IMAGE_FILTER_TYPE_OLD1,
        IMAGE_FILTER_TYPE_OLD2,
        IMAGE_FILTER_TYPE_OLD3,
        IMAGE_FILTER_TYPE_LOMO1,
        IMAGE_FILTER_TYPE_LOMO2,
        IMAGE_FILTER_TYPE_LOMO3,
        IMAGE_FILTER_TYPE_adjust_H_S_B,
        IMAGE_FILTER_TYPE_CircleBlur,
        IMAGE_FILTER_TYPE_IP_26,
        IMAGE_FILTER_TYPE_IP_27,
        IMAGE_FILTER_TYPE_IP_28,
        IMAGE_FILTER_TYPE_IP_29,
        IMAGE_FILTER_TYPE_IP_30,
        IMAGE_FILTER_TYPE_IP_31,
        IMAGE_FILTER_TYPE_IP_32,
        IMAGE_FILTER_TYPE_IP_33,
        IMAGE_FILTER_TYPE_IP_34,
        IMAGE_FILTER_TYPE_IP_35,
        IMAGE_FILTER_TYPE_IP_36,
        IMAGE_FILTER_TYPE_IP_37,
        IMAGE_FILTER_TYPE_IP_38,
        IMAGE_FILTER_TYPE_adjust_S_B_C_new,
        IMAGE_FILTER_TYPE_Test
    }

    public class POINTT {
        int f440x;
        int f441y;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE() {
        int[] iArr = $SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE;
        if (iArr == null) {
            iArr = new int[IMAGE_FILTER_TYPE.values().length];
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_CircleBlur.ordinal()] = 43;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GRAY1.ordinal()] = 34;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GRAY2.ordinal()] = 35;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GaussianFilter.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP1.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP10.ordinal()] = 13;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP11.ordinal()] = 14;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP12.ordinal()] = 15;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP13.ordinal()] = 16;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP14.ordinal()] = 17;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP15.ordinal()] = 18;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP16.ordinal()] = 19;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP17.ordinal()] = 20;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP18.ordinal()] = 21;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP19.ordinal()] = 22;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP2.ordinal()] = 5;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP20.ordinal()] = 23;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP21.ordinal()] = 24;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP22.ordinal()] = 25;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP23.ordinal()] = 26;
            } catch (NoSuchFieldError e20) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP24.ordinal()] = 27;
            } catch (NoSuchFieldError e21) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP25.ordinal()] = 28;
            } catch (NoSuchFieldError e22) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP3.ordinal()] = 6;
            } catch (NoSuchFieldError e23) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP4.ordinal()] = 7;
            } catch (NoSuchFieldError e24) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP5.ordinal()] = 8;
            } catch (NoSuchFieldError e25) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP6.ordinal()] = 9;
            } catch (NoSuchFieldError e26) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP7.ordinal()] = 10;
            } catch (NoSuchFieldError e27) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP8.ordinal()] = 11;
            } catch (NoSuchFieldError e28) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP9.ordinal()] = 12;
            } catch (NoSuchFieldError e29) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_26.ordinal()] = 44;
            } catch (NoSuchFieldError e30) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_27.ordinal()] = 45;
            } catch (NoSuchFieldError e31) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_28.ordinal()] = 46;
            } catch (NoSuchFieldError e32) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_29.ordinal()] = 47;
            } catch (NoSuchFieldError e33) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_30.ordinal()] = 48;
            } catch (NoSuchFieldError e34) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_31.ordinal()] = 49;
            } catch (NoSuchFieldError e35) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_32.ordinal()] = 50;
            } catch (NoSuchFieldError e36) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_33.ordinal()] = 51;
            } catch (NoSuchFieldError e37) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_34.ordinal()] = 52;
            } catch (NoSuchFieldError e38) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_35.ordinal()] = 53;
            } catch (NoSuchFieldError e39) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_36.ordinal()] = 54;
            } catch (NoSuchFieldError e40) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_37.ordinal()] = 55;
            } catch (NoSuchFieldError e41) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_38.ordinal()] = 56;
            } catch (NoSuchFieldError e42) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_B.ordinal()] = 31;
            } catch (NoSuchFieldError e43) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_G.ordinal()] = 30;
            } catch (NoSuchFieldError e44) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_M.ordinal()] = 33;
            } catch (NoSuchFieldError e45) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_R.ordinal()] = 29;
            } catch (NoSuchFieldError e46) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_Y.ordinal()] = 32;
            } catch (NoSuchFieldError e47) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO1.ordinal()] = 39;
            } catch (NoSuchFieldError e48) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO2.ordinal()] = 40;
            } catch (NoSuchFieldError e49) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO3.ordinal()] = 41;
            } catch (NoSuchFieldError e50) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_NON.ordinal()] = 1;
            } catch (NoSuchFieldError e51) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD1.ordinal()] = 36;
            } catch (NoSuchFieldError e52) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD2.ordinal()] = 37;
            } catch (NoSuchFieldError e53) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD3.ordinal()] = 38;
            } catch (NoSuchFieldError e54) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_Test.ordinal()] = 58;
            } catch (NoSuchFieldError e55) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_UnSharpMask.ordinal()] = 3;
            } catch (NoSuchFieldError e56) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_adjust_H_S_B.ordinal()] = 42;
            } catch (NoSuchFieldError e57) {
            }
            try {
                iArr[IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_adjust_S_B_C_new.ordinal()] = 57;
            } catch (NoSuchFieldError e58) {
            }
            $SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE = iArr;
        }
        return iArr;
    }

    static {
        DELTA_INDEX = new double[]{0.0d, 0.01d, 0.02d, 0.04d, 0.05d, 0.06d, 0.07d, 0.08d, 0.1d, 0.11d, 0.12d, 0.14d, 0.15d, 0.16d, 0.17d, 0.18d, 0.2d, 0.21d, 0.22d, 0.24d, 0.25d, 0.27d, 0.28d, 0.3d, 0.32d, 0.34d, 0.36d, 0.38d, 0.4d, 0.42d, 0.44d, 0.46d, 0.48d, 0.5d, 0.53d, 0.56d, 0.59d, 0.62d, 0.65d, 0.68d, 0.71d, 0.74d, 0.77d, 0.8d, 0.83d, 0.86d, 0.89d, 0.92d, 0.95d, 0.98d, 1.0d, 1.06d, 1.12d, 1.18d, 1.24d, 1.3d, 1.36d, 1.42d, 1.48d, 1.54d, 1.6d, 1.66d, 1.72d, 1.78d, 1.84d, 1.9d, 1.96d, 2.0d, 2.12d, 2.25d, 2.37d, 2.5d, 2.62d, 2.75d, 2.87d, 3.0d, 3.2d, 3.4d, 3.6d, 3.8d, 4.0d, 4.3d, 4.7d, 4.9d, 5.0d, 5.5d, 6.0d, 6.5d, 6.8d, 7.0d, 7.3d, 7.5d, 7.8d, 8.0d, 8.4d, 8.7d, 9.0d, 9.4d, 9.6d, 9.8d, 10.0d};
    }

    public ImageFilter(Context context) {
        this.m_bmp = null;
        this.mContext = context;
    }

    public IMAGE_FILTER_TYPE ValueOf(int iType) {
        IMAGE_FILTER_TYPE retType = IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_NON;
        switch (iType) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_NON;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP1;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP2;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP3;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP4;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP5;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP6;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP7;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP8;
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP9;
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP10;
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP11;
            case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP12;
            case ConnectionResult.CANCELED /*13*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP13;
            case ConnectionResult.TIMEOUT /*14*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP14;
            case ConnectionResult.INTERRUPTED /*15*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP15;
            case ConnectionResult.API_UNAVAILABLE /*16*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP16;
            case ConnectionResult.SIGN_IN_FAILED /*17*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP17;
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP18;
            case ConnectionResult.SERVICE_MISSING_PERMISSION /*19*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP19;
            case ConnectionResult.RESTRICTED_PROFILE /*20*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP20;
            case FTP.DEFAULT_PORT /*21*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP21;
            case TelnetOption.SUPDUP_OUTPUT /*22*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP22;
            case TelnetOption.SEND_LOCATION /*23*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP23;
            case TelnetOption.TERMINAL_TYPE /*24*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP24;
            case SMTP.DEFAULT_PORT /*25*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP25;
            case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_R;
            case TelnetOption.OUTPUT_MARKING /*27*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_G;
            case TelnetOption.TERMINAL_LOCATION_NUMBER /*28*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_B;
            case TelnetOption.REGIME_3270 /*29*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_Y;
            case TelnetOption.X3_PAD /*30*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_Single_M;
            case TelnetOption.WINDOW_SIZE /*31*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GRAY1;
//            case BorderDelFragment.PENDDING_SIZE /*32*/:
//                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_GRAY2;
            case TelnetOption.REMOTE_FLOW_CONTROL /*33*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD1;
            case TelnetOption.LINEMODE /*34*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD2;
            case TelnetOption.X_DISPLAY_LOCATION /*35*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_OLD3;
            case TelnetOption.OLD_ENVIRONMENT_VARIABLES /*36*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO1;
            case TimeUDPClient.DEFAULT_PORT /*37*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO2;
            case TelnetOption.ENCRYPTION /*38*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_LOMO3;
            case TelnetOption.NEW_ENVIRONMENT_VARIABLES /*39*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_28;
            case ImageAdapter.PENDDING_SIZE /*40*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_29;
            case JumpInfo.RESULT_UPLOAD_PHOTO_BACK_GALLERY /*41*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_30;
            case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_31;
            case WhoisClient.DEFAULT_PORT /*43*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_32;
            case JumpInfo.RESULT_SNAP_DONE /*44*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_33;
            case JumpInfo.REQUEST_SNAP_PRINT /*45*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_34;
            case JumpInfo.RESULT_CLOUD_ALBUM /*46*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_35;
            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_36;
            case 48:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_37;
            case 49:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP_38;
            default:
                return IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_IP1;
        }
    }

    public boolean ProcessImage(Bitmap bmp, IMAGE_FILTER_TYPE type) {
        switch ($SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE()[type.ordinal()]) {
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return GaussianFilter(bmp);
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return UnSharpMask(bmp);
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return Filter_IP("RAW_PRINGO/IP_1.raw", bmp);
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return Filter_IP2(bmp);
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return Filter_IP("RAW_PRINGO/IP_3.raw", bmp);
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return Filter_IP("RAW_PRINGO/IP_4.raw", bmp);
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return Filter_IP("RAW_PRINGO/IP_5.raw", bmp);
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return Filter_IP("RAW_PRINGO/IP_6.raw", bmp);
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return Filter_IP("RAW_PRINGO/IP_7.raw", bmp);
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return Filter_IP("RAW_PRINGO/IP_8.raw", bmp);
            case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                return Filter_IP("RAW_PRINGO/IP_9.raw", bmp);
            case ConnectionResult.CANCELED /*13*/:
                return Filter_IP("RAW_PRINGO/IP_10.raw", bmp);
            case ConnectionResult.TIMEOUT /*14*/:
                return Filter_IP("RAW_PRINGO/IP_11.raw", bmp);
            case ConnectionResult.INTERRUPTED /*15*/:
                return Filter_IP12(bmp);
            case ConnectionResult.API_UNAVAILABLE /*16*/:
                return Filter_IP("RAW_PRINGO/IP_13.raw", bmp);
            case ConnectionResult.SIGN_IN_FAILED /*17*/:
                return Filter_IP("RAW_PRINGO/IP_14.raw", bmp);
            case ConnectionResult.SERVICE_UPDATING /*18*/:
                return Filter_IP15(bmp);
            case ConnectionResult.SERVICE_MISSING_PERMISSION /*19*/:
                return Filter_IP("RAW_PRINGO/IP_16.raw", bmp);
            case ConnectionResult.RESTRICTED_PROFILE /*20*/:
                return Filter_IP("RAW_PRINGO/IP_17.raw", bmp);
            case FTP.DEFAULT_PORT /*21*/:
                return Filter_IP("RAW_PRINGO/IP_18.raw", bmp);
            case TelnetOption.SUPDUP_OUTPUT /*22*/:
                return Filter_IP("RAW_PRINGO/IP_19.raw", bmp);
            case TelnetOption.SEND_LOCATION /*23*/:
                return Filter_IP("RAW_PRINGO/IP_20.raw", bmp);
            case TelnetOption.TERMINAL_TYPE /*24*/:
                return Filter_IP("RAW_PRINGO/IP_21.raw", bmp);
            case SMTP.DEFAULT_PORT /*25*/:
                return Filter_IP("RAW_PRINGO/IP_22.raw", bmp);
            case TelnetOption.TACACS_USER_IDENTIFICATION /*26*/:
                return Filter_IP("RAW_PRINGO/IP_23.raw", bmp);
            case TelnetOption.OUTPUT_MARKING /*27*/:
                return Filter_IP("RAW_PRINGO/IP_24.raw", bmp);
            case TelnetOption.TERMINAL_LOCATION_NUMBER /*28*/:
                return Filter_IP("RAW_PRINGO/IP_25.raw", bmp);
            case TelnetOption.REGIME_3270 /*29*/:
                return Filter_IP("RAW_PRINGO/RGB_R.raw", bmp);
            case TelnetOption.X3_PAD /*30*/:
                return Filter_IP("RAW_PRINGO/RGB_G.raw", bmp);
            case TelnetOption.WINDOW_SIZE /*31*/:
                return Filter_IP("RAW_PRINGO/RGB_B.raw", bmp);
//            case BorderDelFragment.PENDDING_SIZE /*32*/:
//                return Filter_IP("RAW_PRINGO/RGB_Y.raw", bmp);
            case TelnetOption.REMOTE_FLOW_CONTROL /*33*/:
                return Filter_IP("RAW_PRINGO/RGB_M.raw", bmp);
            case TelnetOption.LINEMODE /*34*/:
                return Filter_GRAY1(bmp);
            case TelnetOption.X_DISPLAY_LOCATION /*35*/:
                return Filter_GRAY2(bmp);
            case TelnetOption.OLD_ENVIRONMENT_VARIABLES /*36*/:
                return Filter_OLD1(bmp);
            case TimeUDPClient.DEFAULT_PORT /*37*/:
                return Filter_OLD2(bmp);
            case TelnetOption.ENCRYPTION /*38*/:
                return Filter_OLD3(bmp);
            case TelnetOption.NEW_ENVIRONMENT_VARIABLES /*39*/:
                return Filter_LOMO1(bmp);
            case ImageAdapter.PENDDING_SIZE /*40*/:
                return Filter_LOMO2(bmp);
            case JumpInfo.RESULT_UPLOAD_PHOTO_BACK_GALLERY /*41*/:
                return Filter_LOMO3(bmp);
            case WhoisClient.DEFAULT_PORT /*43*/:
                return CircleBlur(bmp);
            case JumpInfo.REQUEST_SNAP_PRINT /*45*/:
                return Filter_IP("RAW_PRINGO/IP_27.raw", bmp);
            case JumpInfo.RESULT_CLOUD_ALBUM /*46*/:
                return Filter_IP("RAW_PRINGO/IP_28.raw", bmp);
            case MotionEventCompat.AXIS_GENERIC_16 /*47*/:
                return Filter_IP("RAW_PRINGO/IP_29.raw", bmp);
            case 48:
                return Filter_IP("RAW_PRINGO/IP_30.raw", bmp);
            case 49:
                return Filter_IP("RAW_PRINGO/IP_31.raw", bmp);
            case JumpInfo.RESULT_MAIN_ACTIVITY /*50*/:
                return Filter_IP("RAW_PRINGO/IP_32.raw", bmp);
            case JumpInfo.RESULT_SOURCE_ACTIVITY /*51*/:
                return Filter_IP("RAW_PRINGO/IP_33.raw", bmp);
            case 52:
                return Filter_IP("RAW_PRINGO/IP_34.raw", bmp);
            case DNSConstants.DNS_PORT /*53*/:
                return Filter_IP("RAW_PRINGO/IP_35.raw", bmp);
            case JumpInfo.RESULT_ALBUM_FROM_PRINTER_ACTIVITY /*54*/:
                return Filter_IP("RAW_PRINGO/IP_36.raw", bmp);
            case JumpInfo.RESULT_PHOTO_ACTIVITY /*55*/:
                return Filter_IP("RAW_PRINGO/IP_37.raw", bmp);
            case JumpInfo.RESULT_PHOTO_FROM_PRINTER_ACTIVITY /*56*/:
                return Filter_IP("RAW_PRINGO/IP_38.raw", bmp);
            case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                return Filter_TestImage1(bmp);
            default:
                return true;
        }
    }

    public boolean ProcessImage(Bitmap Srcbmp, Bitmap Dstbmp, IMAGE_FILTER_TYPE type) {
        double startTime = (double) System.currentTimeMillis();
        boolean boRet = true;
        switch ($SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE()[type.ordinal()]) {
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                boRet = GaussianFilter(Dstbmp);
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                boRet = UnSharpMask(Srcbmp, Dstbmp);
                break;
            case JumpInfo.RESULT_POOL_ID_ACTIVITY /*58*/:
                boRet = Filter_IP31(Srcbmp, Dstbmp);
                break;
        }
        System.out.println("Using Time: " + ((((double) System.currentTimeMillis()) - startTime) / 1000.0d) + " sec");
        return boRet;
    }

    public boolean ProcessImage_HSBC(Bitmap Srcbmp, IMAGE_FILTER_TYPE type, float hue, float saturation, float brightness, float contrast) {
        switch ($SWITCH_TABLE$com$hiti$ImageFilter$ImageFilter$IMAGE_FILTER_TYPE()[type.ordinal()]) {
            case JumpInfo.RESULT_FORCE_VERIFY_ACTIVITY /*42*/:
                return Filter_adjust_H_S_B_C(Srcbmp, hue, saturation, brightness, contrast);
            case JumpInfo.RESULT_POOL_ACTIVITY /*57*/:
                return Filter_adjust_S_B_C_new(Srcbmp, saturation, brightness, contrast);
            default:
                return true;
        }
    }

    private boolean GaussianFilter(Bitmap src) {
        BlurFastLessMemory(src, 32);
        return true;
    }

    private boolean UnSharpMask(Bitmap src) {
        boolean boRet = true;
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] blurPixels = null;
        int[] OrigPixels = null;
        try {
            blurPixels = new int[(bmWidth * bmHeight)];
            OrigPixels = new int[bmWidth];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (boRet) {
            src.getPixels(blurPixels, 0, bmWidth, 0, 0, bmWidth, bmHeight);
            BlurFast(blurPixels, bmWidth, bmHeight, 32);
            int index = 0;
            for (int h = 0; h < bmHeight; h++) {
                src.getPixels(OrigPixels, 0, bmWidth, 0, h, bmWidth, 1);
                for (int w = 0; w < bmWidth; w++) {
                    int origPixel = OrigPixels[w];
                    int blurredPixel = blurPixels[index];
                    int orgRed = (origPixel >> 16) & TelnetOption.MAX_OPTION_VALUE;
                    int orgGreen = (origPixel >> 8) & TelnetOption.MAX_OPTION_VALUE;
                    int orgBlue = origPixel & TelnetOption.MAX_OPTION_VALUE;
                    int blurredRed = (blurredPixel >> 16) & TelnetOption.MAX_OPTION_VALUE;
                    int blurredGreen = (blurredPixel >> 8) & TelnetOption.MAX_OPTION_VALUE;
                    int blurredBlue = blurredPixel & TelnetOption.MAX_OPTION_VALUE;
                    if (Math.abs(orgRed - blurredRed) >= 0) {
                        orgRed = (int) ((((float) (orgRed - blurredRed)) * 0.15f) + ((float) orgRed));
                        if (orgRed > 255) {
                            orgRed = TelnetOption.MAX_OPTION_VALUE;
                        } else if (orgRed < 0) {
                            orgRed = 0;
                        }
                    }
                    if (Math.abs(orgGreen - blurredGreen) >= 0) {
                        orgGreen = (int) ((((float) (orgGreen - blurredGreen)) * 0.15f) + ((float) orgGreen));
                        if (orgGreen > 255) {
                            orgGreen = TelnetOption.MAX_OPTION_VALUE;
                        } else if (orgGreen < 0) {
                            orgGreen = 0;
                        }
                    }
                    if (Math.abs(orgBlue - blurredBlue) >= 0) {
                        orgBlue = (int) ((((float) (orgBlue - blurredBlue)) * 0.15f) + ((float) orgBlue));
                        if (orgBlue > 255) {
                            orgBlue = TelnetOption.MAX_OPTION_VALUE;
                        } else if (orgBlue < 0) {
                            orgBlue = 0;
                        }
                    }
                    blurPixels[index] = ((ViewCompat.MEASURED_STATE_MASK | (orgRed << 16)) | (orgGreen << 8)) | orgBlue;
                    index++;
                }
            }
            src.setPixels(blurPixels, 0, bmWidth, 0, 0, bmWidth, bmHeight);
            Canvas canvas = new Canvas(src);
            Paint paint = new Paint(1);
            paint.setColorFilter(adjustSaturation(-5.0f));
            canvas.drawBitmap(src, 0.0f, 0.0f, paint);
        }
        return boRet;
    }

    private boolean UnSharpMask(Bitmap src, Bitmap dst) {
        if (src == null || dst == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        if (bmWidth != dst.getWidth() || bmHeight != dst.getHeight()) {
            return false;
        }
        boolean boRet = BlurFastLessMemory(dst, 32);
        if (!boRet) {
            return boRet;
        }
        int[] blurPixels = null;
        int[] OrigPixels = null;
        try {
            blurPixels = new int[bmWidth];
            OrigPixels = new int[bmWidth];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(OrigPixels, 0, bmWidth, 0, h, bmWidth, 1);
            dst.getPixels(blurPixels, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int origPixel = OrigPixels[w];
                int blurredPixel = blurPixels[w];
                int orgRed = (origPixel >> 16) & TelnetOption.MAX_OPTION_VALUE;
                int orgGreen = (origPixel >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int orgBlue = origPixel & TelnetOption.MAX_OPTION_VALUE;
                int blurredRed = (blurredPixel >> 16) & TelnetOption.MAX_OPTION_VALUE;
                int blurredGreen = (blurredPixel >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int blurredBlue = blurredPixel & TelnetOption.MAX_OPTION_VALUE;
                if (Math.abs(orgRed - blurredRed) >= 0) {
                    orgRed = (int) ((((float) (orgRed - blurredRed)) * 0.15f) + ((float) orgRed));
                    if (orgRed > 255) {
                        orgRed = TelnetOption.MAX_OPTION_VALUE;
                    } else if (orgRed < 0) {
                        orgRed = 0;
                    }
                }
                if (Math.abs(orgGreen - blurredGreen) >= 0) {
                    orgGreen = (int) ((((float) (orgGreen - blurredGreen)) * 0.15f) + ((float) orgGreen));
                    if (orgGreen > 255) {
                        orgGreen = TelnetOption.MAX_OPTION_VALUE;
                    } else if (orgGreen < 0) {
                        orgGreen = 0;
                    }
                }
                if (Math.abs(orgBlue - blurredBlue) >= 0) {
                    orgBlue = (int) ((((float) (orgBlue - blurredBlue)) * 0.15f) + ((float) orgBlue));
                    if (orgBlue > 255) {
                        orgBlue = TelnetOption.MAX_OPTION_VALUE;
                    } else if (orgBlue < 0) {
                        orgBlue = 0;
                    }
                }
                blurPixels[w] = ((ViewCompat.MEASURED_STATE_MASK | (orgRed << 16)) | (orgGreen << 8)) | orgBlue;
            }
            dst.setPixels(blurPixels, 0, bmWidth, 0, h, bmWidth, 1);
        }
        Canvas canvas = new Canvas(dst);
        Paint paint = new Paint(1);
        paint.setColorFilter(adjustSaturation(-10.0f));
        canvas.drawBitmap(dst, 0.0f, 0.0f, paint);
        return boRet;
    }

    private boolean Filter_GRAY1(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        int[] Rfilter = null;
        int[] Gfilter = null;
        int[] Bfilter = null;
        try {
            newBitmap = new int[bmWidth];
            Rfilter = new int[DNSConstants.FLAGS_RD];
            Gfilter = new int[DNSConstants.FLAGS_RD];
            Bfilter = new int[DNSConstants.FLAGS_RD];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        for (int i = 0; i < 256; i++) {
            Rfilter[i] = (int) (0.4f * ((float) i));
            Gfilter[i] = (int) (0.5f * ((float) i));
            Bfilter[i] = (int) (0.1f * ((float) i));
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int Gray = (Rfilter[(newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE] + Gfilter[(newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE]) + Bfilter[newBitmap[w] & TelnetOption.MAX_OPTION_VALUE];
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (Gray << 16)) | (Gray << 8)) | Gray;
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        Canvas canvas = new Canvas(src);
        Paint paint = new Paint(1);
        paint.setColorFilter(adjustContrast(10.0f));
        canvas.drawBitmap(src, 0.0f, 0.0f, paint);
        return true;
    }

    private boolean Filter_GRAY2(Bitmap src) {
        Canvas CanvasBMP = new Canvas(src);
        ColorMatrixColorFilter ColorFilter_GRAY = new ColorMatrixColorFilter(new float[]{0.3f, 0.58f, 0.12f, 0.0f, 0.0f, 0.3f, 0.58f, 0.12f, 0.0f, 0.0f, 0.3f, 0.58f, 0.12f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Paint paint = new Paint(1);
        paint.setColorFilter(ColorFilter_GRAY);
        CanvasBMP.drawBitmap(src, 0.0f, 0.0f, paint);
        return true;
    }

    private boolean Filter_OLD1(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        int[] Rfilter = null;
        int[] Gfilter = null;
        int[] Bfilter = null;
        double[] Gray_100P = null;
        try {
            newBitmap = new int[bmWidth];
            Rfilter = new int[DNSConstants.FLAGS_RD];
            Gfilter = new int[DNSConstants.FLAGS_RD];
            Bfilter = new int[DNSConstants.FLAGS_RD];
            Gray_100P = new double[766];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        int i;
        double temp = 0.0d;
        for (i = 0; i < 256; i++) {
            double k = (double) min(TelnetOption.MAX_OPTION_VALUE, max(60, i + 30));
            Rfilter[i] = (int) (k * 1.0d);
            Gfilter[i] = (int) (k * 0.9725490212440491d);
            Bfilter[i] = (int) (k * 0.843137264251709d);
        }
        i = 0;
        while (i < 766) {
            Gray_100P[i] = temp;
            i++;
            temp += 0.3333333432674408d;
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int i2 = ((newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE) + ((newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE);
                int Gray = (int) Gray_100P[i2 + (newBitmap[w] & TelnetOption.MAX_OPTION_VALUE)];
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (Rfilter[Gray] << 16)) | (Gfilter[Gray] << 8)) | Bfilter[Gray];
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        return true;
    }

    private boolean Filter_OLD2(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        int[] Rfilter = null;
        int[] Gfilter = null;
        int[] Bfilter = null;
        double[] Gray_60P = null;
        double[] RGB_40p = null;
        try {
            newBitmap = new int[bmWidth];
            Rfilter = new int[DNSConstants.FLAGS_RD];
            Gfilter = new int[DNSConstants.FLAGS_RD];
            Bfilter = new int[DNSConstants.FLAGS_RD];
            RGB_40p = new double[DNSConstants.FLAGS_RD];
            Gray_60P = new double[766];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        int i;
        double temp = 0.0d;
        for (i = 0; i < 256; i++) {
            double k = (double) max(50, min(TelnetOption.MAX_OPTION_VALUE, ((int) (128.0d - (((double) (128 - i)) * 1.5d))) + 25));
            Rfilter[i] = (int) (k * 0.9450980392156862d);
            Gfilter[i] = (int) (k * 0.8823529411764706d);
            Bfilter[i] = (int) (k * 0.6901960784313725d);
            RGB_40p[i] = ((double) i) * 0.4d;
        }
        i = 0;
        while (i < 766) {
            Gray_60P[i] = temp;
            i++;
            temp += 0.2d;
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int r = (newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE;
                int g = (newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int b = newBitmap[w] & TelnetOption.MAX_OPTION_VALUE;
                int Gray = (int) Gray_60P[(r + g) + b];
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (Rfilter[(int) (RGB_40p[r] + ((double) Gray))] << 16)) | (Gfilter[(int) (RGB_40p[g] + ((double) Gray))] << 8)) | Bfilter[(int) (RGB_40p[b] + ((double) Gray))];
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        return true;
    }

    private boolean Filter_OLD3(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        int[] Rfilter = null;
        int[] Gfilter = null;
        int[] Bfilter = null;
        double[] Gray_60P = null;
        double[] RGB_40p = null;
        try {
            newBitmap = new int[bmWidth];
            Rfilter = new int[DNSConstants.FLAGS_RD];
            Gfilter = new int[DNSConstants.FLAGS_RD];
            Bfilter = new int[DNSConstants.FLAGS_RD];
            RGB_40p = new double[DNSConstants.FLAGS_RD];
            Gray_60P = new double[766];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        int i;
        double temp = 0.0d;
        for (i = 0; i < 256; i++) {
            double k = (double) max(50, min(TelnetOption.MAX_OPTION_VALUE, ((int) (128.0d - (((double) (128 - i)) * 1.5d))) + 25));
            Rfilter[i] = (int) (k * 1.0d);
            Gfilter[i] = (int) (k * 0.9411764705882353d);
            Bfilter[i] = (int) (k * 0.7450980392156863d);
            RGB_40p[i] = ((double) i) * 0.8d;
        }
        i = 0;
        while (i < 766) {
            Gray_60P[i] = temp;
            i++;
            temp += 0.06666666666666667d;
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int r = (newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE;
                int g = (newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int b = newBitmap[w] & TelnetOption.MAX_OPTION_VALUE;
                int Gray = (int) Gray_60P[(r + g) + b];
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (Rfilter[(int) (RGB_40p[r] + ((double) Gray))] << 16)) | (Gfilter[(int) (RGB_40p[g] + ((double) Gray))] << 8)) | Bfilter[(int) (RGB_40p[b] + ((double) Gray))];
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        return true;
    }

    private boolean Filter_LOMO1(Bitmap src) {
        Canvas c = new Canvas(src);
        Paint paintContrast = new Paint(1);
        paintContrast.setColorFilter(adjustContrast(40.0f));
        c.drawBitmap(src, 0.0f, 0.0f, paintContrast);
        Canvas canvas = new Canvas(src);
        int leftX = (int) (0.25d * ((double) src.getWidth()));
        int leftY = (int) (0.25d * ((double) src.getHeight()));
        float f = (float) (leftX * 2);
        float f2 = (float) (leftY * 2);
        float max = (float) (max(leftX, leftY) * 3);
        int[] iArr = new int[2];
        iArr[1] = ViewCompat.MEASURED_STATE_MASK;
        RadialGradient mRadialGradient_sec = new RadialGradient(f, f2, max, iArr, new float[]{0.5f, 0.8f}, TileMode.CLAMP);
        Paint paint = new Paint(1);
        paint.setShader(mRadialGradient_sec);
        canvas.drawRect(0.0f, 0.0f, (float) src.getWidth(), (float) src.getHeight(), paint);
        return true;
    }

    private boolean Filter_LOMO2(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[][] m_pCurveTable = null;
        int[] iArr = null;
        try {
            iArr = new int[bmWidth];
            m_pCurveTable = (int[][]) Array.newInstance(Integer.TYPE, new int[]{4, DNSConstants.FLAGS_RD});
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        POINTT[] PP = new POINTT[]{new POINTT(), new POINTT(), new POINTT(), new POINTT(), new POINTT()};
        PP[0].f440x = 0;
        PP[0].f441y = 0;
        PP[1].f440x = 64;
        PP[1].f441y = 59;
        PP[2].f440x = ControllerState.PLAY_COUNT_STATE;
        PP[2].f441y = 160;
        PP[3].f440x = 183;
        PP[3].f441y = NNTPReply.ARTICLE_TRANSFERRED_OK;
        PP[4].f440x = TelnetOption.MAX_OPTION_VALUE;
        PP[4].f441y = TelnetOption.MAX_OPTION_VALUE;
        MakeCurveTable(5, PP, 0, m_pCurveTable);
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(iArr, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int g = (iArr[w] >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int b = iArr[w] & TelnetOption.MAX_OPTION_VALUE;
                int r = m_pCurveTable[0][(iArr[w] >> 16) & TelnetOption.MAX_OPTION_VALUE];
                g = m_pCurveTable[0][g];
                iArr[w] = ((ViewCompat.MEASURED_STATE_MASK | (r << 16)) | (g << 8)) | m_pCurveTable[0][b];
            }
            src.setPixels(iArr, 0, bmWidth, 0, h, bmWidth, 1);
        }
        Canvas canvas = new Canvas(src);
        int leftX = (int) (0.25d * ((double) src.getWidth()));
        int leftY = (int) (0.25d * ((double) src.getHeight()));
        float f = (float) (leftX * 2);
        float f2 = (float) (leftY * 2);
        float max = ((float) max(leftX, leftY)) * 2.8f;
        int[] iArr2 = new int[2];
        iArr2[1] = ViewCompat.MEASURED_STATE_MASK;
        RadialGradient mRadialGradient_sec = new RadialGradient(f, f2, max, iArr2, new float[]{0.5f, 0.8f}, TileMode.CLAMP);
        Paint paint = new Paint(1);
        paint.setShader(mRadialGradient_sec);
        canvas.drawRect(0.0f, 0.0f, (float) src.getWidth(), (float) src.getHeight(), paint);
        return boRet;
    }

    private boolean Filter_LOMO3(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[][] m_pCurveTable = null;
        int[] iArr = null;
        try {
            iArr = new int[bmWidth];
            m_pCurveTable = (int[][]) Array.newInstance(Integer.TYPE, new int[]{4, DNSConstants.FLAGS_RD});
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        POINTT[] PP = new POINTT[]{new POINTT(), new POINTT(), new POINTT(), new POINTT(), new POINTT()};
        PP[0].f440x = 0;
        PP[0].f441y = 45;
        PP[1].f440x = 64;
        PP[1].f441y = 113;
        PP[2].f440x = TransportMediator.FLAG_KEY_MEDIA_NEXT;
        PP[2].f441y = 208;
        PP[3].f440x = Wbxml.EXT_0;
        PP[3].f441y = TelnetCommand.AO;
        PP[4].f440x = TelnetOption.MAX_OPTION_VALUE;
        PP[4].f441y = TelnetOption.MAX_OPTION_VALUE;
        MakeCurveTable(5, PP, 0, m_pCurveTable);
        PP[0].f440x = 0;
        PP[0].f441y = 0;
        PP[1].f440x = 64;
        PP[1].f441y = 92;
        PP[2].f440x = TransportMediator.FLAG_KEY_MEDIA_NEXT;
        PP[2].f441y = 187;
        PP[3].f440x = Wbxml.EXT_0;
        PP[3].f441y = TelnetCommand.SUSP;
        PP[4].f440x = TelnetOption.MAX_OPTION_VALUE;
        PP[4].f441y = TelnetCommand.DONT;
        MakeCurveTable(5, PP, 1, m_pCurveTable);
        PP[0].f440x = 0;
        PP[0].f441y = 20;
        PP[1].f440x = 64;
        PP[1].f441y = TransportMediator.KEYCODE_MEDIA_RECORD;
        PP[2].f440x = TransportMediator.FLAG_KEY_MEDIA_NEXT;
        PP[2].f441y = 184;
        PP[3].f440x = Wbxml.EXT_0;
        PP[3].f441y = NNTPReply.ARTICLE_LIST_BY_MESSAGE_ID_FOLLOWS;
        PP[4].f440x = TelnetOption.MAX_OPTION_VALUE;
        PP[4].f441y = TelnetOption.MAX_OPTION_VALUE;
        MakeCurveTable(5, PP, 2, m_pCurveTable);
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(iArr, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int g = (iArr[w] >> 8) & TelnetOption.MAX_OPTION_VALUE;
                int b = iArr[w] & TelnetOption.MAX_OPTION_VALUE;
                int r = m_pCurveTable[0][(iArr[w] >> 16) & TelnetOption.MAX_OPTION_VALUE];
                g = m_pCurveTable[1][g];
                iArr[w] = ((ViewCompat.MEASURED_STATE_MASK | (r << 16)) | (g << 8)) | m_pCurveTable[2][b];
            }
            src.setPixels(iArr, 0, bmWidth, 0, h, bmWidth, 1);
        }
        Canvas canvas = new Canvas(src);
        int leftX = (int) (0.25d * ((double) src.getWidth()));
        int leftY = (int) (0.25d * ((double) src.getHeight()));
        float f = (float) (leftX * 2);
        float f2 = (float) (leftY * 2);
        float max = ((float) max(leftX, leftY)) * 2.5f;
        int[] iArr2 = new int[2];
        iArr2[1] = ViewCompat.MEASURED_STATE_MASK;
        RadialGradient mRadialGradient_sec = new RadialGradient(f, f2, max, iArr2, new float[]{0.45f, 0.9f}, TileMode.CLAMP);
        Paint paint = new Paint(1);
        paint.setShader(mRadialGradient_sec);
        canvas.drawRect(0.0f, 0.0f, (float) src.getWidth(), (float) src.getHeight(), paint);
        return boRet;
    }

    @SuppressLint({"NewApi"})
    private boolean Filter_IP(String fileName, Bitmap bmp) {
        if (VERSION.SDK_INT >= 21) {
            System.out.println(" Filter_RS_ColorMap ");
            return Filter_RS_ColorMap(fileName, bmp);
        }
        System.out.println("cClrCvt do table mapping ");
        CClrCvt cClrCvt = new CClrCvt();
        cClrCvt.LoadColorTable(this.mContext, fileName);
        boolean boRet = cClrCvt.Convert(bmp);
        cClrCvt.clean();
        return boRet;
    }

    private boolean Filter_IP2(Bitmap bmp) {
        boolean boRet = Filter_IP("RAW_PRINGO/IP_2.raw", bmp);
        Canvas canvas_BMP = new Canvas(bmp);
        int leftX = (int) (0.25d * ((double) bmp.getWidth()));
        LinearGradient mLinearGradient_sec = new LinearGradient(0.0f, 0.0f, 0.0f, (float) (((int) (0.25d * ((double) bmp.getHeight()))) * 2), new int[]{1088479456, 14737632}, new float[]{0.0f, 0.3f}, TileMode.MIRROR);
        LinearGradient mLinearGradient_sec2 = new LinearGradient(0.0f, 0.0f, (float) (leftX * 2), 0.0f, new int[]{1088479456, 14737632}, new float[]{0.0f, 0.3f}, TileMode.MIRROR);
        Paint paint = new Paint(1);
        paint.setShader(mLinearGradient_sec);
        canvas_BMP.drawRect(0.0f, 0.0f, (float) bmp.getWidth(), (float) bmp.getHeight(), paint);
        paint.setShader(mLinearGradient_sec2);
        canvas_BMP.drawRect(0.0f, 0.0f, (float) bmp.getWidth(), (float) bmp.getHeight(), paint);
        return boRet;
    }

    private boolean Filter_IP12(Bitmap bmp) {
        boolean boRet = Filter_IP("RAW_PRINGO/IP_12.raw", bmp);
        Canvas canvas = new Canvas(bmp);
        int leftX = (int) (0.25d * ((double) bmp.getWidth()));
        int leftY = (int) (0.25d * ((double) bmp.getHeight()));
        float f = (float) (leftX * 2);
        float f2 = (float) (leftY * 2);
        float max = (float) (max(leftX, leftY) * 3);
        int[] iArr = new int[2];
        iArr[1] = ViewCompat.MEASURED_STATE_MASK;
        RadialGradient mRadialGradient_sec = new RadialGradient(f, f2, max, iArr, new float[]{0.5f, 0.8f}, TileMode.CLAMP);
        Paint paint = new Paint(1);
        paint.setShader(mRadialGradient_sec);
        canvas.drawRect(0.0f, 0.0f, (float) bmp.getWidth(), (float) bmp.getHeight(), paint);
        return boRet;
    }

    private boolean Filter_IP15(Bitmap bmp) {
        boolean boRet = Filter_IP("RAW_PRINGO/IP_15.raw", bmp);
        Canvas canvas = new Canvas(bmp);
        float f = 0.0f;
        LinearGradient mLinearGradient_sec = new LinearGradient(0.0f, f, (float) (((int) (((double) bmp.getWidth()) * 0.25d)) * 2), (float) (((int) (((double) bmp.getHeight()) * 0.25d)) * 2), new int[]{-1, ViewCompat.MEASURED_SIZE_MASK}, new float[]{0.1f, 0.3f}, TileMode.MIRROR);
        Paint paint = new Paint(1);
        paint.setShader(mLinearGradient_sec);
        canvas.drawRect(0.0f, 0.0f, (float) bmp.getWidth(), (float) bmp.getHeight(), paint);
        return boRet;
    }

    private boolean Filter_IP31(Bitmap src, Bitmap dst) {
        BlurFastLessMemory(src, 4);
        Canvas canvas = new Canvas(src);
        Paint paint = new Paint(1);
        int leftX = (int) (0.25d * ((double) src.getWidth()));
        int leftY = (int) (0.25d * ((double) src.getHeight()));
        float f = (float) (leftX * 2);
        float f2 = (float) (leftY * 2);
        float max = (float) (max(leftX, leftY) * 2);
        int[] iArr = new int[2];
        iArr[1] = ViewCompat.MEASURED_STATE_MASK;
        paint.setShader(new RadialGradient(f, f2, max, iArr, new float[]{0.5f, 0.99f}, TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
        canvas.drawRect(0.0f, 0.0f, (float) src.getWidth(), (float) src.getHeight(), paint);
        Canvas canvas_Src = new Canvas(dst);
        Rect Rect1 = new Rect(0, 0, src.getWidth(), src.getHeight());
        canvas_Src.drawBitmap(src, Rect1, Rect1, null);
        boolean boRet = Filter_IP("RAW_PRINGO/IP_7.raw", dst);
        return true;
    }

    private boolean Filter_RS_ColorMap(String fileName, Bitmap Srcbmp) {
        CClrCvt_RenderScrpt cClrCvt_RS = new CClrCvt_RenderScrpt(this.mContext);
        cClrCvt_RS.LoadColorTable(this.mContext, fileName);
        cClrCvt_RS.Convert_Filter(Srcbmp);
        cClrCvt_RS.clean();
        return true;
    }

    private boolean Filter_RS_ColorMap_SBC(String fileName, Bitmap Srcbmp, float saturation, float brightness, float contrast) {
        CClrCvt_RenderScrpt cClrCvt_RS = new CClrCvt_RenderScrpt(this.mContext);
        cClrCvt_RS.LoadColorTableAndDoBCS(this.mContext, fileName, brightness, contrast, saturation);
        cClrCvt_RS.Convert_Filter_and_BCS(Srcbmp);
        cClrCvt_RS.clean();
        return true;
    }

    private boolean Filter_RS_SBC(Bitmap Srcbmp, float saturation, float brightness, float contrast) {
        CClrCvt_RenderScrpt cClrCvt_RS = new CClrCvt_RenderScrpt(this.mContext);
        cClrCvt_RS.SetBCSTable(brightness, contrast, saturation);
        cClrCvt_RS.Convert_Filter_and_BCS(Srcbmp);
        cClrCvt_RS.clean();
        return true;
    }

    private boolean Filter_TestImage1(Bitmap bmp) {
        this.m_bmp = Bitmap.createBitmap(bmp);
        GaussianFilter(this.m_bmp);
        Canvas canvas_m_bmp = new Canvas(this.m_bmp);
        RadialGradient mRadialGradient_sec = new RadialGradient((float) (((int) (((double) bmp.getWidth()) * 0.25d)) * 2), (float) (((int) (((double) bmp.getHeight()) * 0.25d)) * 2), 350.0f, Color.alpha(0), Color.alpha(TelnetOption.MAX_OPTION_VALUE), TileMode.CLAMP);
        Paint paint = new Paint(1);
        paint.setShader(mRadialGradient_sec);
        new Canvas(bmp).drawBitmap(this.m_bmp, 0.0f, 0.0f, paint);
        return true;
    }

    private boolean CircleBlur(Bitmap bmp) {
        this.m_bmp = Bitmap.createBitmap(bmp);
        GaussianFilter(this.m_bmp);
        Bitmap MASK_Bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Config.ARGB_8888);
        Canvas canvas_MASK = new Canvas(MASK_Bitmap);
        RadialGradient mRadialGradient_tr = new RadialGradient((float) (((int) (0.25d * ((double) bmp.getWidth()))) * 2), (float) (((int) (0.25d * ((double) bmp.getHeight()))) * 2), 300.0f, new int[]{ViewCompat.MEASURED_STATE_MASK, -1}, new float[]{0.3f, 0.6f}, TileMode.CLAMP);
        Paint paint_2 = new Paint(1);
        paint_2.setShader(mRadialGradient_tr);
        canvas_MASK.drawRect(0.0f, 0.0f, (float) bmp.getWidth(), (float) bmp.getHeight(), paint_2);
        applyAlpha(this.m_bmp, MASK_Bitmap);
        Rect Rect1 = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        new Canvas(bmp).drawBitmap(this.m_bmp, Rect1, Rect1, null);
        return true;
    }

    private boolean CircleBlur(Bitmap Srcbmp, Bitmap Dstbmp) {
        return true;
    }

    private boolean Filter_adjust_H_S_B_C(Bitmap Srcbmp, float hue, float saturation, float brightness, float contrast) {
        if (Srcbmp == null) {
            return false;
        }
        Canvas c = new Canvas(Srcbmp);
        Paint paint = new Paint(1);
        paint.setColorFilter(adjustColor(brightness, contrast, saturation, hue));
        c.drawBitmap(Srcbmp, 0.0f, 0.0f, paint);
        return true;
    }

    private boolean Filter_B_C(Bitmap src, float brightness, float contrast) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        brightness = (float) ((int) cleanValue(brightness, 100.0f));
        contrast = (float) ((int) cleanValue(contrast, 100.0f));
        if (brightness == 0.0f && contrast == 0.0f) {
            return true;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        int[] TableMap = null;
        try {
            newBitmap = new int[bmWidth];
            TableMap = new int[DNSConstants.FLAGS_RD];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        double dbLgamma;
        double dbLweight;
        double dbCgamma;
        double dbCweight;
        if (brightness > 0.0f) {
            dbLgamma = 2.1d;
            dbLweight = ((double) brightness) / 100.0d;
        } else {
            dbLgamma = 0.47619047619047616d;
            dbLweight = (-((double) brightness)) / 100.0d;
        }
        if (contrast > 0.0f) {
            dbCgamma = 2.0d;
            dbCweight = ((double) contrast) / 100.0d;
        } else {
            dbCgamma = 0.6666666666666666d;
            dbCweight = (-((double) contrast)) / 100.0d;
        }
        TableMap[0] = 0;
        TableMap[TelnetOption.MAX_OPTION_VALUE] = TelnetOption.MAX_OPTION_VALUE;
        for (int val = 1; val < 255; val++) {
            double dbval = ((double) val) / 255.0d;
            if (brightness != 0.0f) {
                dbval += ((1.0d - dbval) - Math.pow(1.0d - dbval, dbLgamma)) * dbLweight;
            }
            if (contrast != 0.0f) {
                if (dbval > 0.5d) {
                    dbval += ((1.0d - dbval) - (0.5d * Math.pow(2.0d * (1.0d - dbval), dbCgamma))) * dbCweight;
                } else {
                    dbval += ((0.5d * Math.pow(2.0d * dbval, dbCgamma)) - dbval) * dbCweight;
                }
            }
            TableMap[val] = (int) Math.min(255.0d, Math.max(0.0d, (255.0d * dbval) + 0.5d));
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (TableMap[(newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE] << 16)) | (TableMap[(newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE] << 8)) | TableMap[newBitmap[w] & TelnetOption.MAX_OPTION_VALUE];
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        return true;
    }

    private boolean Filter_adjust_S_B_C_new(Bitmap Srcbmp, float saturation, float brightness, float contrast) {
        if (Srcbmp == null) {
            return false;
        }
        boolean boRet = Filter_B_C(Srcbmp, brightness, contrast);
        if (!boRet) {
            return boRet;
        }
        Canvas c = new Canvas(Srcbmp);
        Paint paint = new Paint(1);
        paint.setColorFilter(adjustSaturation(saturation));
        c.drawBitmap(Srcbmp, 0.0f, 0.0f, paint);
        return boRet;
    }

    private void BlurFast_Original(Bitmap bmp, int radius) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int[] pix = new int[(w * h)];
        bmp.getPixels(pix, 0, w, 0, 0, w, h);
        for (int r = radius; r >= 1; r /= 2) {
            for (int i = r; i < h - r; i++) {
                for (int j = r; j < w - r; j++) {
                    int tl = pix[(((i - r) * w) + j) - r];
                    int tr = pix[(((i - r) * w) + j) + r];
                    int tc = pix[((i - r) * w) + j];
                    int bl = pix[(((i + r) * w) + j) - r];
                    int br = pix[(((i + r) * w) + j) + r];
                    int bc = pix[((i + r) * w) + j];
                    int cl = pix[((i * w) + j) - r];
                    int cr = pix[((i * w) + j) + r];
                    pix[(i * w) + j] = ((ViewCompat.MEASURED_STATE_MASK | ((((((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (bl & TelnetOption.MAX_OPTION_VALUE)) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) >> 3) & TelnetOption.MAX_OPTION_VALUE)) | ((((((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) >> 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((((((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) >> 3) & 16711680);
                }
            }
        }
        bmp.setPixels(pix, 0, w, 0, 0, w, h);
    }

    private void BlurFast_AllImage(Bitmap bmp, int radius) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int[] pix = new int[(w * h)];
        bmp.getPixels(pix, 0, w, 0, 0, w, h);
        int cl = 0;
        int bc = 0;
        int br = 0;
        int bl = 0;
        int tc = 0;
        int tr = 0;
        int tl = 0;
        for (int r = radius; r >= 1; r /= 2) {
            for (int y = 0; y < h; y++) {
                int x;
                int cr;
                if (y < r) {
                    for (x = 0; x < w; x++) {
                        if (x < r) {
                            cr = pix[((y * w) + x) + r];
                            br = pix[(((y + r) * w) + x) + r];
                            bc = pix[((y + r) * w) + x];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((br & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & br) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & br) + (16711680 & bc)) + (16711680 & cr)) / 3) & 16711680);
                        } else if (x >= w - r) {
                            cl = pix[((y * w) + x) - r];
                            bl = pix[(((y + r) * w) + x) - r];
                            bc = pix[((y + r) * w) + x];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((bl & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & bl) + (16711680 & bc)) + (16711680 & cl)) / 3) & 16711680);
                        } else {
                            bl = pix[(((y + r) * w) + x) - r];
                            br = pix[(((y + r) * w) + x) + r];
                            bc = pix[((y + r) * w) + x];
                            cl = pix[((y * w) + x) - r];
                            cr = pix[((y * w) + x) + r];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((bl & TelnetOption.MAX_OPTION_VALUE) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & bl) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                        }
                    }
                } else if (y >= h - r) {
                    for (x = 0; x < w; x++) {
                        if (x < r) {
                            tc = pix[((y - r) * w) + x];
                            tr = pix[(((y - r) * w) + x) + r];
                            cr = pix[((y * w) + x) + r];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((tc & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tc) + (16711680 & tr)) + (16711680 & cr)) / 3) & 16711680);
                        } else if (x >= w - r) {
                            cl = pix[((y * w) + x) - r];
                            tl = pix[(((y - r) * w) + x) - r];
                            tc = pix[((y - r) * w) + x];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tl) + (16711680 & tc)) + (16711680 & cl)) / 3) & 16711680);
                        } else {
                            tl = pix[(((y - r) * w) + x) - r];
                            tr = pix[(((y - r) * w) + x) + r];
                            tc = pix[((y - r) * w) + x];
                            cl = pix[((y * w) + x) - r];
                            cr = pix[((y * w) + x) + r];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                        }
                    }
                } else {
                    for (x = 0; x < w; x++) {
                        if (x < r) {
                            tc = pix[((y - r) * w) + x];
                            tr = pix[(((y - r) * w) + x) + r];
                            cr = pix[((y * w) + x) + r];
                            br = pix[(((y + r) * w) + x) + r];
                            bc = pix[((y + r) * w) + x];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tr & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tr) + (16711680 & tc)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cr)) / 5) & 16711680);
                        } else if (x >= w - r) {
                            tl = pix[(((y - r) * w) + x) - r];
                            tc = pix[((y - r) * w) + x];
                            bl = pix[(((y + r) * w) + x) - r];
                            bc = pix[((y + r) * w) + x];
                            cl = pix[((y * w) + x) - r];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (bl & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & bc)) + (16711680 & cl)) / 5) & 16711680);
                        } else {
                            tl = pix[(((y - r) * w) + x) - r];
                            tr = pix[(((y - r) * w) + x) + r];
                            tc = pix[((y - r) * w) + x];
                            bl = pix[(((y + r) * w) + x) - r];
                            br = pix[(((y + r) * w) + x) + r];
                            bc = pix[((y + r) * w) + x];
                            cl = pix[((y * w) + x) - r];
                            cr = pix[((y * w) + x) + r];
                            pix[(y * w) + x] = ((ViewCompat.MEASURED_STATE_MASK | ((((((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (bl & TelnetOption.MAX_OPTION_VALUE)) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) >> 3) & TelnetOption.MAX_OPTION_VALUE)) | ((((((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) >> 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((((((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) >> 3) & 16711680);
                        }
                    }
                }
            }
        }
        bmp.setPixels(pix, 0, w, 0, 0, w, h);
    }

    private void BlurFast(int[] blurPixels, int width, int height, int radius) {
        int w = width;
        int h = height;
        int cl = 0;
        int cr = 0;
        int bc = 0;
        int br = 0;
        int bl = 0;
        int tc = 0;
        int tr = 0;
        int tl = 0;
        int i = 0;
        int w_r = w - radius;
        int cur_line_idx;
        int nxt_line_idx;
        int pre_line_idx;
        int x;
        for (int r = radius; r >= 1; r /= 2) {
            int y;
            for (y = 0; y < r; y++) {
                cur_line_idx = y * w;
                nxt_line_idx = (y + r) * w;
                for (x = 0; x < r; x++) {
                    cr = blurPixels[cur_line_idx + r];
                    br = blurPixels[nxt_line_idx + r];
                    bc = blurPixels[nxt_line_idx];
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((br & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + r0) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & br) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & br) + (16711680 & bc)) + (16711680 & cr)) / 3) & 16711680);
                    cur_line_idx++;
                    nxt_line_idx++;
                }
                for (x = r; x < w_r; x++) {
                    bl = blurPixels[nxt_line_idx - r];
                    br = blurPixels[nxt_line_idx + r];
                    bc = blurPixels[nxt_line_idx];
                    cl = blurPixels[cur_line_idx - r];
                    cr = blurPixels[cur_line_idx + r];
                    i = bc & TelnetOption.MAX_OPTION_VALUE;
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((((bl & TelnetOption.MAX_OPTION_VALUE) + (br & TelnetOption.MAX_OPTION_VALUE)) + r0) + r0) + r0) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & bl) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                    cur_line_idx++;
                    nxt_line_idx++;
                }
                for (x = w_r; x < w; x++) {
                    cl = blurPixels[cur_line_idx - r];
                    bl = blurPixels[nxt_line_idx - r];
                    bc = blurPixels[nxt_line_idx];
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((bl & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + r0) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & bl) + (16711680 & bc)) + (16711680 & cl)) / 3) & 16711680);
                    cur_line_idx++;
                    nxt_line_idx++;
                }
            }
            for (y = r; y < h - r; y++) {
                pre_line_idx = (y - r) * w;
                cur_line_idx = y * w;
                nxt_line_idx = (y + r) * w;
                for (x = 0; x < r; x++) {
                    tc = blurPixels[pre_line_idx];
                    tr = blurPixels[pre_line_idx + r];
                    cr = blurPixels[cur_line_idx + r];
                    br = blurPixels[nxt_line_idx + r];
                    bc = blurPixels[nxt_line_idx];
                    i = br & TelnetOption.MAX_OPTION_VALUE;
                    i = bc & TelnetOption.MAX_OPTION_VALUE;
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tr & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + r0) + r0) + r0) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tr) + (16711680 & tc)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cr)) / 5) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                    nxt_line_idx++;
                }
                for (x = r; x < w_r; x++) {
                    tl = blurPixels[pre_line_idx - r];
                    tr = blurPixels[pre_line_idx + r];
                    tc = blurPixels[pre_line_idx];
                    bl = blurPixels[nxt_line_idx - r];
                    br = blurPixels[nxt_line_idx + r];
                    bc = blurPixels[nxt_line_idx];
                    cl = blurPixels[cur_line_idx - r];
                    cr = blurPixels[cur_line_idx + r];
                    i = tc & TelnetOption.MAX_OPTION_VALUE;
                    i = bl & TelnetOption.MAX_OPTION_VALUE;
                    i = br & TelnetOption.MAX_OPTION_VALUE;
                    i = bc & TelnetOption.MAX_OPTION_VALUE;
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | ((((((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + r0) + r0) + r0) + r0) + r0) + r0) >> 3) & TelnetOption.MAX_OPTION_VALUE)) | ((((((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) >> 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((((((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) >> 3) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                    nxt_line_idx++;
                }
                for (x = w_r; x < w; x++) {
                    tl = blurPixels[pre_line_idx - r];
                    tc = blurPixels[pre_line_idx];
                    bl = blurPixels[nxt_line_idx - r];
                    bc = blurPixels[nxt_line_idx];
                    cl = blurPixels[cur_line_idx - r];
                    i = bl & TelnetOption.MAX_OPTION_VALUE;
                    i = bc & TelnetOption.MAX_OPTION_VALUE;
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + r0) + r0) + r0) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & bc)) + (16711680 & cl)) / 5) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                    nxt_line_idx++;
                }
            }
            for (y = h - r; y < h; y++) {
                pre_line_idx = (y - r) * w;
                cur_line_idx = y * w;
                for (x = 0; x < r; x++) {
                    tc = blurPixels[pre_line_idx];
                    tr = blurPixels[pre_line_idx + r];
                    cr = blurPixels[cur_line_idx + r];
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((tc & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + r0) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tc) + (16711680 & tr)) + (16711680 & cr)) / 3) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                }
                for (x = r; x < w_r; x++) {
                    tl = blurPixels[pre_line_idx - r];
                    tr = blurPixels[pre_line_idx + r];
                    tc = blurPixels[pre_line_idx];
                    cl = blurPixels[cur_line_idx - r];
                    cr = blurPixels[cur_line_idx + r];
                    i = tc & TelnetOption.MAX_OPTION_VALUE;
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    i = cr & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + r0) + r0) + r0) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                }
                for (x = w_r; x < w; x++) {
                    cl = blurPixels[cur_line_idx - r];
                    tl = blurPixels[pre_line_idx - r];
                    tc = blurPixels[pre_line_idx];
                    i = cl & TelnetOption.MAX_OPTION_VALUE;
                    blurPixels[cur_line_idx] = ((ViewCompat.MEASURED_STATE_MASK | (((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + r0) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tl) + (16711680 & tc)) + (16711680 & cl)) / 3) & 16711680);
                    pre_line_idx++;
                    cur_line_idx++;
                }
            }
        }
    }

    private boolean BlurFastLessMemory(Bitmap bmp, int radius) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int cl = 0;
        int bc = 0;
        int br = 0;
        int bl = 0;
        int tc = 0;
        int tr = 0;
        int tl = 0;
        boolean boRet = true;
        int[] TopPixels = null;
        int[] NowPixels = null;
        int[] Dowpixels = null;
        try {
            TopPixels = new int[w];
            NowPixels = new int[w];
            Dowpixels = new int[w];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (boRet) {
            int w_r = w - radius;
            for (int r = radius; r >= 1; r /= 2) {
                int y;
                int x;
                int cr;
                for (y = 0; y < r; y++) {
                    bmp.getPixels(NowPixels, 0, w, 0, y, w, 1);
                    bmp.getPixels(Dowpixels, 0, w, 0, y + r, w, 1);
                    for (x = 0; x < r; x++) {
                        cr = NowPixels[x + r];
                        br = Dowpixels[x + r];
                        bc = Dowpixels[x];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((br & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & br) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & br) + (16711680 & bc)) + (16711680 & cr)) / 3) & 16711680);
                    }
                    for (x = r; x < w_r; x++) {
                        bl = Dowpixels[x - r];
                        br = Dowpixels[x + r];
                        bc = Dowpixels[x];
                        cl = NowPixels[x - r];
                        cr = NowPixels[x + r];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((bl & TelnetOption.MAX_OPTION_VALUE) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & bl) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                    }
                    for (x = w_r; x < w; x++) {
                        cl = NowPixels[x - r];
                        bl = Dowpixels[x - r];
                        bc = Dowpixels[x];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((bl & TelnetOption.MAX_OPTION_VALUE) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & bl) + (16711680 & bc)) + (16711680 & cl)) / 3) & 16711680);
                    }
                    bmp.setPixels(NowPixels, 0, w, 0, y, w, 1);
                }
                for (y = r; y < h - r; y++) {
                    bmp.getPixels(TopPixels, 0, w, 0, y - r, w, 1);
                    bmp.getPixels(NowPixels, 0, w, 0, y, w, 1);
                    bmp.getPixels(Dowpixels, 0, w, 0, y + r, w, 1);
                    for (x = 0; x < r; x++) {
                        tc = TopPixels[x];
                        tr = TopPixels[x + r];
                        cr = NowPixels[x + r];
                        br = Dowpixels[x + r];
                        bc = Dowpixels[x];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tr & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tr) + (16711680 & tc)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cr)) / 5) & 16711680);
                    }
                    for (x = r; x < w_r; x++) {
                        tl = TopPixels[x - r];
                        tr = TopPixels[x + r];
                        tc = TopPixels[x];
                        bl = Dowpixels[x - r];
                        br = Dowpixels[x + r];
                        bc = Dowpixels[x];
                        cl = NowPixels[x - r];
                        cr = NowPixels[x + r];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | ((((((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (bl & TelnetOption.MAX_OPTION_VALUE)) + (br & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) >> 3) & TelnetOption.MAX_OPTION_VALUE)) | ((((((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & br)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) >> 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | ((((((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & br)) + (16711680 & bc)) + (16711680 & cl)) + (16711680 & cr)) >> 3) & 16711680);
                    }
                    for (x = w_r; x < w; x++) {
                        tl = TopPixels[x - r];
                        tc = TopPixels[x];
                        bl = Dowpixels[x - r];
                        bc = Dowpixels[x];
                        cl = NowPixels[x - r];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (bl & TelnetOption.MAX_OPTION_VALUE)) + (bc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & bc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tc)) + (16711680 & bl)) + (16711680 & bc)) + (16711680 & cl)) / 5) & 16711680);
                    }
                    bmp.setPixels(NowPixels, 0, w, 0, y, w, 1);
                }
                for (y = h - r; y < h; y++) {
                    bmp.getPixels(TopPixels, 0, w, 0, y - r, w, 1);
                    bmp.getPixels(NowPixels, 0, w, 0, y, w, 1);
                    for (x = 0; x < r; x++) {
                        tc = TopPixels[x];
                        tr = TopPixels[x + r];
                        cr = NowPixels[x + r];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((tc & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tc) + (16711680 & tr)) + (16711680 & cr)) / 3) & 16711680);
                    }
                    for (x = r; x < w_r; x++) {
                        tl = TopPixels[x - r];
                        tr = TopPixels[x + r];
                        tc = TopPixels[x];
                        cl = NowPixels[x - r];
                        cr = NowPixels[x + r];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((((tl & TelnetOption.MAX_OPTION_VALUE) + (tr & TelnetOption.MAX_OPTION_VALUE)) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) + (cr & TelnetOption.MAX_OPTION_VALUE)) / 5) & TelnetOption.MAX_OPTION_VALUE)) | (((((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tr)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cr)) / 5) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((((16711680 & tl) + (16711680 & tr)) + (16711680 & tc)) + (16711680 & cl)) + (16711680 & cr)) / 5) & 16711680);
                    }
                    for (x = w_r; x < w; x++) {
                        cl = NowPixels[x - r];
                        tl = TopPixels[x - r];
                        tc = TopPixels[x];
                        NowPixels[x] = ((ViewCompat.MEASURED_STATE_MASK | (((((tl & TelnetOption.MAX_OPTION_VALUE) + (tc & TelnetOption.MAX_OPTION_VALUE)) + (cl & TelnetOption.MAX_OPTION_VALUE)) / 3) & TelnetOption.MAX_OPTION_VALUE)) | (((((MotionEventCompat.ACTION_POINTER_INDEX_MASK & tl) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & tc)) + (MotionEventCompat.ACTION_POINTER_INDEX_MASK & cl)) / 3) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (((((16711680 & tl) + (16711680 & tc)) + (16711680 & cl)) / 3) & 16711680);
                    }
                    bmp.setPixels(NowPixels, 0, w, 0, y, w, 1);
                }
            }
        }
        return boRet;
    }

    static void blur(int[] srcPixels, int[] dstPixels, int width, int height, float[] kernel, int radius) {
        for (int y = 0; y < height; y++) {
            int index = y;
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                float b = 0.0f;
                float g = 0.0f;
                float r = 0.0f;
                float a = 0.0f;
                for (int i = -radius; i <= radius; i++) {
                    int subOffset = x + i;
                    if (subOffset < 0 || subOffset >= width) {
                        subOffset = (x + width) % width;
                    }
                    int pixel = srcPixels[offset + subOffset];
                    float blurFactor = kernel[radius + i];
                    a += ((float) ((pixel >> 24) & TelnetOption.MAX_OPTION_VALUE)) * blurFactor;
                    r += ((float) ((pixel >> 16) & TelnetOption.MAX_OPTION_VALUE)) * blurFactor;
                    g += ((float) ((pixel >> 8) & TelnetOption.MAX_OPTION_VALUE)) * blurFactor;
                    b += ((float) (pixel & TelnetOption.MAX_OPTION_VALUE)) * blurFactor;
                }
                int ca = (int) (0.5f + a);
                int cr = (int) (0.5f + r);
                int cg = (int) (0.5f + g);
                int cb = (int) (0.5f + b);
                if (ca > 255) {
                    ca = TelnetOption.MAX_OPTION_VALUE;
                }
                int i2 = ca << 24;
                if (cr > 255) {
                    cr = TelnetOption.MAX_OPTION_VALUE;
                }
                i2 |= cr << 16;
                if (cg > 255) {
                    cg = TelnetOption.MAX_OPTION_VALUE;
                }
                i2 |= cg << 8;
                if (cb > 255) {
                    cb = TelnetOption.MAX_OPTION_VALUE;
                }
                dstPixels[index] = i2 | cb;
                index += height;
            }
        }
    }

    static float[] createGaussianKernel(int radius) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }
        int i;
        float[] data = new float[((radius * 2) + 1)];
        float sigma = ((float) radius) / 3.0f;
        float twoSigmaSquare = (2.0f * sigma) * sigma;
        float sigmaRoot = (float) Math.sqrt(((double) twoSigmaSquare) * 3.141592653589793d);
        float total = 0.0f;
        for (i = -radius; i <= radius; i++) {
            int index = i + radius;
            data[index] = ((float) Math.exp((double) ((-((float) (i * i))) / twoSigmaSquare))) / sigmaRoot;
            total += data[index];
        }
        for (i = 0; i < data.length; i++) {
            data[i] = data[i] / total;
        }
        return data;
    }

    private boolean Filter_TestImage2(Bitmap bmp) {
        Canvas c = new Canvas(bmp);
        Paint paint = new Paint(1);
        paint.setColorFilter(adjustSaturation(-20.0f));
        c.drawBitmap(bmp, 0.0f, 0.0f, paint);
        return true;
    }

    private boolean Filter_TestImage3(Bitmap src) {
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = new int[(bmWidth * bmHeight)];
        src.getPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        for (int h = 0; h < bmHeight; h++) {
            for (int w = 0; w < bmWidth; w++) {
                int index = (h * bmWidth) + w;
                int b = newBitmap[index] & TelnetOption.MAX_OPTION_VALUE;
                newBitmap[index] = ((ViewCompat.MEASURED_STATE_MASK | (((newBitmap[index] >> 16) & TelnetOption.MAX_OPTION_VALUE) << 16)) | (((newBitmap[index] >> 8) & TelnetOption.MAX_OPTION_VALUE) << 8)) | TelnetOption.MAX_OPTION_VALUE;
            }
        }
        src.setPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        return true;
    }

    private boolean Filter_TestImage4(Bitmap src) {
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = new int[(bmWidth * bmHeight)];
        src.getPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        for (int h = 0; h < bmHeight; h++) {
            for (int w = 0; w < bmWidth; w++) {
                int index = (h * bmWidth) + w;
                int i = newBitmap[index] & TelnetOption.MAX_OPTION_VALUE;
                newBitmap[index] = (((((newBitmap[index] >> 24) & TelnetOption.MAX_OPTION_VALUE) << 24) | (((newBitmap[index] >> 16) & TelnetOption.MAX_OPTION_VALUE) << 16)) | (((newBitmap[index] >> 8) & TelnetOption.MAX_OPTION_VALUE) << 8)) | TelnetOption.MAX_OPTION_VALUE;
            }
        }
        src.setPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        return true;
    }

    private boolean Filter_TestImage5(Bitmap src) {
        boolean boRet = true;
        if (src == null) {
            return false;
        }
        int bmWidth = src.getWidth();
        int bmHeight = src.getHeight();
        int[] newBitmap = null;
        try {
            newBitmap = new int[bmWidth];
        } catch (OutOfMemoryError ex) {
            boRet = false;
            ex.printStackTrace();
        }
        if (!boRet) {
            return boRet;
        }
        for (int h = 0; h < bmHeight; h++) {
            src.getPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
            for (int w = 0; w < bmWidth; w++) {
                int b = newBitmap[w] & TelnetOption.MAX_OPTION_VALUE;
                newBitmap[w] = ((ViewCompat.MEASURED_STATE_MASK | (((newBitmap[w] >> 16) & TelnetOption.MAX_OPTION_VALUE) << 16)) | (((newBitmap[w] >> 8) & TelnetOption.MAX_OPTION_VALUE) << 8)) | TelnetOption.MAX_OPTION_VALUE;
            }
            src.setPixels(newBitmap, 0, bmWidth, 0, h, bmWidth, 1);
        }
        return true;
    }

    private int[][] MakeCurveTable(int n, POINTT[] pp, int channel, int[][] m_pCurveTable) {
        int i;
        int j;
        double[] m = new double[45];
        double[] d = new double[15];
        double[] resultdif = new double[15];
        for (i = 0; i < n; i++) {
            m[15 + i] = 2.0d;
        }
        for (i = 1; i < n - 1; i++) {
            double h1 = (double) (pp[i].f440x - pp[i - 1].f440x);
            double h2 = (double) (pp[i + 1].f440x - pp[i].f440x);
            double r = h2 / (h1 + h2);
            double u = 1.0d - r;
            m[i] = u;
            m[i + 30] = r;
            d[i] = (((3.0d * r) * ((double) (pp[i].f441y - pp[i - 1].f441y))) / h1) + (((3.0d * u) * ((double) (pp[i + 1].f441y - pp[i].f441y))) / h2);
        }
        m[0] = 1.0d;
        m[(n + 30) - 1] = 1.0d;
        float f = (float) (pp[1].f441y - pp[0].f441y);
        d[0] = (double) ((3.0f * r0) / ((float) (pp[1].f440x - pp[0].f440x)));
        f = (float) (pp[n - 1].f441y - pp[n - 2].f441y);
        d[n - 1] = (double) ((3.0f * r0) / ((float) (pp[n - 1].f440x - pp[n - 2].f440x)));
        double[] aa = new double[45];
        for (i = 0; i < n; i++) {
            resultdif[i] = 0.0d;
        }
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 15; j++) {
                aa[(i * 15) + j] = m[(i * 15) + j];
            }
        }
        for (i = 0; i < n; i++) {
            aa[i] = m[i] / aa[15 + i];
            aa[(15 + i) + 1] = m[(15 + i) + 1] - (m[(i + 30) + 1] * aa[i]);
        }
        resultdif[0] = d[0] / aa[15];
        for (i = 1; i < n; i++) {
            resultdif[i] = (d[i] - (m[i + 30] * resultdif[i - 1])) / aa[15 + i];
        }
        for (i = n - 2; i > -1; i--) {
            resultdif[i] = resultdif[i] - (aa[i] * resultdif[i + 1]);
        }
        double h = (double) (pp[1].f440x - pp[0].f440x);
        double a = (((double) ((pp[1].f441y - pp[0].f441y) * 3)) / (h * h)) - ((resultdif[1] + (2.0d * resultdif[0])) / h);
        double b = (((double) ((pp[0].f441y - pp[1].f441y) * 2)) / ((h * h) * h)) + ((resultdif[1] + resultdif[0]) / (h * h));
        j = 0;
        for (i = 0; i <= TelnetOption.MAX_OPTION_VALUE; i++) {
            int i2 = pp[0].f440x;
            if (i < r0) {
                m_pCurveTable[channel][i] = pp[0].f441y;
            } else {
                i2 = pp[n - 1].f440x;
                if (i > r0) {
                    m_pCurveTable[channel][i] = pp[n - 1].f441y;
                } else {
                    i2 = pp[j + 1].f440x;
                    if (i > r0) {
                        j++;
                        h = (double) (pp[j + 1].f440x - pp[j].f440x);
                        a = (((double) ((pp[j + 1].f441y - pp[j].f441y) * 3)) / (h * h)) - ((resultdif[j + 1] + (2.0d * resultdif[j])) / h);
                        b = (((double) ((pp[j].f441y - pp[j + 1].f441y) * 2)) / ((h * h) * h)) + ((resultdif[j + 1] + resultdif[j]) / (h * h));
                    }
                    double temp = (double) (i - pp[j].f440x);
                    temp = ((((double) pp[j].f441y) + (resultdif[j] * temp)) + ((a * temp) * temp)) + (((b * temp) * temp) * temp);
                    if (temp > ((double) TelnetOption.MAX_OPTION_VALUE)) {
                        m_pCurveTable[channel][i] = 255;
                    } else if (temp < 0.0d) {
                        m_pCurveTable[channel][i] = 0;
                    } else {
                        m_pCurveTable[channel][i] = (int) temp;
                    }
                }
            }
        }
        return m_pCurveTable;
    }

    private static void applyAlpha(Bitmap bb, Bitmap bAlpha) {
        int bmWidth = bb.getWidth();
        int bmHeight = bb.getHeight();
        int[] newBitmap = new int[(bmWidth * bmHeight)];
        int[] AlphaBitmap = new int[(bmWidth * bmHeight)];
        bb.getPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        bAlpha.getPixels(AlphaBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
        for (int h = 0; h < bmHeight; h++) {
            for (int w = 0; w < bmWidth; w++) {
                int index = (h * bmWidth) + w;
                int alpha1 = (newBitmap[index] >> 24) & TelnetOption.MAX_OPTION_VALUE;
                int b = newBitmap[index] & TelnetOption.MAX_OPTION_VALUE;
                int Alpha2 = (AlphaBitmap[index] >> 16) & TelnetOption.MAX_OPTION_VALUE;
                newBitmap[index] = (((r0 << 24) | (((newBitmap[index] >> 16) & TelnetOption.MAX_OPTION_VALUE) << 16)) | (((newBitmap[index] >> 8) & TelnetOption.MAX_OPTION_VALUE) << 8)) | b;
            }
        }
        bb.setPixels(newBitmap, 0, bmWidth, 0, 0, bmWidth, bmHeight);
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    private static ColorFilter adjustColor(float brightness, float contrast, float saturation, float hue) {
        ColorMatrix cm = new ColorMatrix();
        adjustHue(cm, hue);
        adjustContrast(cm, (int) contrast);
        adjustBrightness(cm, brightness);
        adjustSaturation(cm, saturation);
        return new ColorMatrixColorFilter(cm);
    }

    private static ColorFilter adjustHue(float value) {
        ColorMatrix cm = new ColorMatrix();
        adjustHue(cm, value);
        return new ColorMatrixColorFilter(cm);
    }

    private static ColorFilter adjustSaturation(float value) {
        ColorMatrix cm = new ColorMatrix();
        adjustSaturation(cm, value);
        return new ColorMatrixColorFilter(cm);
    }

    private static ColorFilter adjustBrightness(float value) {
        ColorMatrix cm = new ColorMatrix();
        adjustBrightness(cm, value);
        return new ColorMatrixColorFilter(cm);
    }

    private static ColorFilter adjustContrast(float value) {
        ColorMatrix cm = new ColorMatrix();
        adjustContrast(cm, (int) value);
        return new ColorMatrixColorFilter(cm);
    }

    private static void adjustHue(ColorMatrix cm, float value) {
        value = (cleanValue(value, 180.0f) / 180.0f) * 3.1415927f;
        if (value != 0.0f) {
            float cosVal = (float) Math.cos((double) value);
            float sinVal = (float) Math.sin((double) value);
            cm.postConcat(new ColorMatrix(new float[]{(((1.0f - 0.213f) * cosVal) + 0.213f) + ((-1046092972) * sinVal), (((-1060571709) * cosVal) + 0.715f) + ((-1060571709) * sinVal), (((-1033073852) * cosVal) + 0.072f) + ((1.0f - 0.072f) * sinVal), 0.0f, 0.0f, (((-1046092972) * cosVal) + 0.213f) + (0.143f * sinVal), (((1.0f - 0.715f) * cosVal) + 0.715f) + (0.14f * sinVal), (((-1033073852) * cosVal) + 0.072f) + (-0.283f * sinVal), 0.0f, 0.0f, (((-1046092972) * cosVal) + 0.213f) + ((-(1.0f - 0.213f)) * sinVal), (((-1060571709) * cosVal) + 0.715f) + (sinVal * 0.715f), (((1.0f - 0.072f) * cosVal) + 0.072f) + (sinVal * 0.072f), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        }
    }

    private static void adjustSaturation(ColorMatrix cm, float value) {
        value = cleanValue(value, 100.0f);
        if (value != 0.0f) {
            float x = 1.0f + (value > 0.0f ? (3.0f * value) / 100.0f : value / 100.0f);
            cm.postConcat(new ColorMatrix(new float[]{((1.0f - x) * 0.3086f) + x, (1.0f - x) * 0.6094f, (1.0f - x) * 0.082f, 0.0f, 0.0f, (1.0f - x) * 0.3086f, ((1.0f - x) * 0.6094f) + x, (1.0f - x) * 0.082f, 0.0f, 0.0f, (1.0f - x) * 0.3086f, (1.0f - x) * 0.6094f, ((1.0f - x) * 0.082f) + x, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        }
    }

    private static void adjustBrightness(ColorMatrix cm, float value) {
        if (cleanValue(value, 100.0f) != 0.0f) {
            cm.postConcat(new ColorMatrix(new float[]{1.0f, 0.0f, 0.0f, 0.0f, cleanValue(value, 100.0f), 0.0f, 1.0f, 0.0f, 0.0f, cleanValue(value, 100.0f), 0.0f, 0.0f, 1.0f, 0.0f, cleanValue(value, 100.0f), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        }
    }

    private static void adjustContrast(ColorMatrix cm, int value) {
        value = (int) cleanValue((float) value, 100.0f);
        if (value != 0) {
            float x;
            if (value < 0) {
                x = 127.0f + ((((float) value) / 100.0f) * 127.0f);
            } else {
                x = (float) (value % 1);
                if (x == 0.0f) {
                    x = (float) DELTA_INDEX[value];
                } else {
                    x = (((float) DELTA_INDEX[value << 0]) * (1.0f - x)) + (((float) DELTA_INDEX[(value << 0) + 1]) * x);
                }
                x = (x * 127.0f) + 127.0f;
            }
            cm.postConcat(new ColorMatrix(new float[]{x / 127.0f, 0.0f, 0.0f, 0.0f, (127.0f - x) * 0.5f, 0.0f, x / 127.0f, 0.0f, 0.0f, (127.0f - x) * 0.5f, 0.0f, 0.0f, x / 127.0f, 0.0f, (127.0f - x) * 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        }
    }

    private static float cleanValue(float p_val, float p_limit) {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }

    private Bitmap blurImageAmeliorate(Bitmap bmp) {
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[(width * height)];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int length = height - 1;
        for (int i = 1; i < length; i++) {
            int len = width - 1;
            for (int k = 1; k < len; k++) {
                int idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        int pixColor = pixels[(((i + m) * width) + k) + n];
                        int pixR = Color.red(pixColor);
                        int pixG = Color.green(pixColor);
                        newR += gauss[idx] * pixR;
                        newG += gauss[idx] * pixG;
                        newB += gauss[idx] * Color.blue(pixColor);
                        idx++;
                    }
                }
                newG /= 16;
                newB /= 16;
                pixels[(i * width) + k] = Color.argb(TelnetOption.MAX_OPTION_VALUE, Math.min(TelnetOption.MAX_OPTION_VALUE, Math.max(0, newR / 16)), Math.min(TelnetOption.MAX_OPTION_VALUE, Math.max(0, newG)), Math.min(TelnetOption.MAX_OPTION_VALUE, Math.max(0, newB)));
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        Bitmap MASK_Bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Config.ARGB_8888);
        MASK_Bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return MASK_Bitmap;
    }
}
