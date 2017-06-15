package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_PrintSettingInfo extends BaseGlobalVariable_Prinbiz {
    private int m_iPaperType;
    private int m_iPrintDuplex;
    private int m_iPrintMethod;
    private int m_iPrintSharpen;
    private int m_iPrintTexture;
    private int m_iType;
    JumpPreferenceKey m_pref;
    private String m_strIDpath;
    private String m_strIDregion;
    private String m_strIDstyle;
    String m_strProductID;

    public GlobalVariable_PrintSettingInfo(Context context, int iType) {
        super(context);
        this.m_iPaperType = 1;
        this.m_iPrintTexture = 0;
        this.m_iPrintMethod = 0;
        this.m_iPrintSharpen = 0;
        this.m_iPrintDuplex = 0;
        this.m_strIDstyle = XmlPullParser.NO_NAMESPACE;
        this.m_strIDregion = XmlPullParser.NO_NAMESPACE;
        this.m_strIDpath = XmlPullParser.NO_NAMESPACE;
        this.m_iType = 0;
        this.m_pref = null;
        this.m_strProductID = null;
        this.m_iType = iType;
        this.m_pref = new JumpPreferenceKey(context);
        this.m_strProductID = this.m_pref.GetModelPreference();
        if (iType == ControllerState.PLAY_PHOTO) {
            this.m_fsp = context.getSharedPreferences(this.m_strProductID + "_" + "pref_gv_prinbiz_quick_print_info", 0);
        } else if (iType == ControllerState.NO_PLAY_ITEM) {
            this.m_fsp = context.getSharedPreferences(this.m_strProductID + "_" + "pref_gv_prinbiz_general_print_info", 0);
        } else if (iType == ControllerState.PLAY_COUNT_STATE) {
            this.m_fsp = context.getSharedPreferences(this.m_strProductID + "_" + "pref_gv_prinbiz_id_print_info", 0);
        } else {
            this.m_fsp = context.getSharedPreferences(this.m_strProductID + "_" + "pref_gv_prinbiz_quick_print_info", 0);
        }
    }

    public void RestoreGlobalVariable() {
        try {
            if (this.m_iType == ControllerState.PLAY_COUNT_STATE) {
                this.m_strIDstyle = this.m_fsp.getString("GV_M_TYPE_ID_STYLE", XmlPullParser.NO_NAMESPACE);
                this.m_strIDregion = this.m_fsp.getString("GV_M_TYPE_ID_REGION", XmlPullParser.NO_NAMESPACE);
                this.m_strIDpath = this.m_fsp.getString("GV_M_TYPE_ID_PATH", XmlPullParser.NO_NAMESPACE);
            }
            this.LOG.m384e("RestoreGlobalVariable", "m_strProductID=" + String.valueOf(this.m_strProductID));
            if (this.m_strProductID.equals(WirelessType.TYPE_P530D)) {
                this.m_iPaperType = this.m_fsp.getInt("GV_M_TYPE_PAPER_TYPE", 6);
            } else {
                this.m_iPaperType = this.m_fsp.getInt("GV_M_TYPE_PAPER_TYPE", 2);
            }
            this.m_iPrintTexture = this.m_fsp.getInt("GV_M_TYPE_PRINT_TEXTURE", 0);
            this.m_iPrintDuplex = this.m_fsp.getInt("GV_M_TYPE_PRINT_DUPLEX", 0);
            this.m_iPrintMethod = this.m_fsp.getInt("GV_M_TYPE_PRINT_METHOD", 0);
            this.m_iPrintSharpen = this.m_fsp.getInt("GV_M_TYPE_PRINT_SHARPEN", 1);
            SetEdit(false);
            this.LOG.m386v("RestoreGlobalVariable", "m_iPrintTexture=" + String.valueOf(this.m_iPrintTexture));
            this.LOG.m386v("RestoreGlobalVariable m_iPrintMethod", String.valueOf(this.m_iPrintMethod));
            this.LOG.m386v("RestoreGlobalVariable m_iPrintMethod", String.valueOf(this.m_iPrintSharpen));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_fsp.edit();
                if (this.m_iType == ControllerState.PLAY_COUNT_STATE) {
                    spe.putString("GV_M_TYPE_ID_STYLE", this.m_strIDstyle);
                    spe.putString("GV_M_TYPE_ID_REGION", this.m_strIDregion);
                    spe.putString("GV_M_TYPE_ID_PATH", this.m_strIDpath);
                }
                spe.putInt("GV_M_TYPE_PAPER_TYPE", this.m_iPaperType);
                spe.putInt("GV_M_TYPE_PRINT_TEXTURE", this.m_iPrintTexture);
                spe.putInt("GV_M_TYPE_PRINT_DUPLEX", this.m_iPrintDuplex);
                spe.putInt("GV_M_TYPE_PRINT_METHOD", this.m_iPrintMethod);
                spe.putInt("GV_M_TYPE_PRINT_SHARPEN", this.m_iPrintSharpen);
                this.LOG.m386v("SaveGlobalVariableForever m_iPrintTexture", String.valueOf(this.m_iPrintMethod));
                this.LOG.m386v("SaveGlobalVariableForever m_iPrintMethod", String.valueOf(this.m_iPrintMethod));
                if (!spe.commit()) {
                    this.LOG.m386v("GlobalVariable_PrinterInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
            } catch (Exception ex) {
                this.LOG.m386v("GlobalVariable_PrinterInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetIDpath() {
        return this.m_strIDpath;
    }

    public String GetIDregion() {
        return this.m_strIDregion;
    }

    public String GetIDstyle() {
        return this.m_strIDstyle;
    }

    public int GetServerPaperType() {
        this.LOG.m386v("GetServerPaperType", String.valueOf(this.m_iPaperType));
        return this.m_iPaperType;
    }

    public int GetPrintMethod() {
        this.LOG.m386v("GetPrintMethod", String.valueOf(this.m_iPrintMethod));
        return this.m_iPrintMethod;
    }

    public int GetPrintTexture() {
        this.LOG.m386v("GetPrintTexture", String.valueOf(this.m_iPrintTexture));
        return this.m_iPrintTexture;
    }

    public int GetPrintDuplex() {
        this.LOG.m386v("GetPrintDuplex", String.valueOf(this.m_iPrintDuplex));
        return this.m_iPrintDuplex;
    }

    public int GetPrintSharpen() {
        this.LOG.m384e("GetPrintSharpen", String.valueOf(this.m_iPrintSharpen));
        return this.m_iPrintSharpen;
    }

    public byte GetPrintSharpenValueForPrinter() {
        byte bSharepen = (byte) -120;
        switch (this.m_iPrintSharpen) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                bSharepen = Byte.MIN_VALUE;
                break;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                bSharepen = (byte) -120;
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                bSharepen = (byte) -112;
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                bSharepen = (byte) -104;
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                bSharepen = (byte) -96;
                break;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                bSharepen = (byte) -88;
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                bSharepen = (byte) -80;
                break;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                bSharepen = (byte) -72;
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                bSharepen = (byte) -64;
                break;
        }
        this.LOG.m384e("GetSharpenForPrinter_" + this.m_iPrintSharpen, String.valueOf(bSharepen));
        return bSharepen;
    }

    public void SetPrintSharpen(int iPrintSharpen) {
        this.LOG.m384e("SetPrintSharpen", String.valueOf(iPrintSharpen));
        this.m_iPrintSharpen = iPrintSharpen;
        SetEdit(true);
    }

    public void SetServerPaperType(int iPaperType) {
        this.LOG.m384e("SetPaperType", String.valueOf(iPaperType));
        this.m_iPaperType = iPaperType;
        SetEdit(true);
    }

    public void SetPrintMethod(int iPrintMethod) {
        this.LOG.m384e("SetPrintMethod", String.valueOf(iPrintMethod));
        this.m_iPrintMethod = iPrintMethod;
        SetEdit(true);
    }

    public void SetPrintTexture(int iPrintTexture) {
        this.LOG.m386v("SetPrintTexture", String.valueOf(iPrintTexture));
        this.m_iPrintTexture = iPrintTexture;
        SetEdit(true);
    }

    public void SetPrintDuplex(int iPrintDuplex) {
        this.LOG.m386v("SetPrintDuplex", String.valueOf(iPrintDuplex));
        this.m_iPrintDuplex = iPrintDuplex;
        SetEdit(true);
    }

    public void SetIDstyle(String strIDSize) {
        this.m_strIDstyle = strIDSize;
        SetEdit(true);
    }

    public void SetIDregion(String strIDregion) {
        this.m_strIDregion = strIDregion;
        SetEdit(true);
    }

    public void SetIDpath(String strIDpath) {
        this.m_strIDpath = strIDpath;
        SetEdit(true);
    }
}
