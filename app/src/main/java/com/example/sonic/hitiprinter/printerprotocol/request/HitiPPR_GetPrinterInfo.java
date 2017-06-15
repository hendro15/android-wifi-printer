package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_GetPrinterInfo extends HitiPPR_PrinterCommand {
    private String m_AttrBatteryLevel;
    private String m_AttrFirmwareVersionString;
    private int m_AttrFirmwareVersionStringLength;
    private String m_AttrProductIDString;
    private String m_AttrProductName;
    private int m_AttrProductNameLength;
    private String m_AttrSerialNumber;
    private int m_AttrSerialNumberStringLength;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetPrinterInfo.1 */
    static /* synthetic */ class C04021 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetProductIDResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterInfoResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    public HitiPPR_GetPrinterInfo(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrProductIDString = null;
        this.m_AttrProductNameLength = -1;
        this.m_AttrProductName = null;
        this.m_AttrFirmwareVersionStringLength = -1;
        this.m_AttrFirmwareVersionString = null;
        this.m_AttrSerialNumberStringLength = -1;
        this.m_AttrSerialNumber = null;
        this.m_AttrBatteryLevel = null;
    }

    public void StartRequest() {
        GetPrinterInfo();
    }

    private void InitialVariable() {
        this.m_AttrProductIDString = null;
        this.m_AttrFirmwareVersionStringLength = -1;
        this.m_AttrFirmwareVersionString = null;
        this.m_AttrSerialNumberStringLength = -1;
        this.m_AttrSerialNumber = null;
        this.m_AttrBatteryLevel = null;
    }

    public void GetPrinterInfo() {
        if (IsRunning()) {
            this.LOG.m385i("GetPrinterInfo", String.valueOf(GetCurrentStep()));
            switch (C04021.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_PRINTER_INFO, null);
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    InitialVariable();
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetProductIDRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_Product_ID_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetProductIDResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideErrorStatus();
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(6, 0, SettingStep.Step_GetProductIDResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete() && this.m_AttrProductIDString == null) {
                            this.m_AttrProductIDString = GetWirelessType(this.m_lpReadData, 3);
                            this.LOG.m385i("m_AttrProductIDString", this.m_AttrProductIDString);
                            DecideNextStep(SettingStep.Step_GetPrinterInfoRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Printer_Info_Request(this.m_AttrProductIDString)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterInfoResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideErrorStatus();
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(6, 0, SettingStep.Step_GetPrinterInfoResponseSuccess);
                            this.m_AttrProductIDString = null;
                            break;
                        }
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrProductIDString != null) {
                                if (this.m_AttrProductNameLength != -1) {
                                    if (this.m_AttrProductName != null) {
                                        if (this.m_AttrFirmwareVersionStringLength != -1) {
                                            if (this.m_AttrFirmwareVersionString != null) {
                                                if (this.m_AttrSerialNumberStringLength != -1) {
                                                    if (this.m_AttrSerialNumber != null) {
                                                        if (this.m_AttrBatteryLevel == null) {
                                                            if (this.m_AttrProductIDString.equals(WirelessType.TYPE_P520L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P310W) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P750L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P461) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P530D)) {
                                                                this.m_AttrBatteryLevel = "100";
                                                            } else {
                                                                float CurrentSize = (float) ByteConvertUtility.ByteToInt(this.m_lpReadData, 4, 4);
                                                                this.LOG.m385i("CurrentSize", String.valueOf(CurrentSize));
                                                                float MaxSize = (float) ByteConvertUtility.ByteToInt(this.m_lpReadData, 8, 4);
                                                                this.LOG.m385i("MaxSize", String.valueOf(MaxSize));
                                                                this.m_AttrBatteryLevel = Integer.toString((int) ((CurrentSize / MaxSize) * 100.0f)) + "%";
                                                            }
                                                            this.LOG.m385i("m_BatteryLevel", this.m_AttrBatteryLevel);
                                                            DecideNextStep(SettingStep.Step_Complete);
                                                            break;
                                                        }
                                                    }
                                                    int iResize = 0;
                                                    int i = 0;
                                                    while (i < this.m_AttrSerialNumberStringLength && this.m_lpReadData[i] != 0) {
                                                        iResize++;
                                                        i++;
                                                    }
                                                    this.m_AttrSerialNumberStringLength = 14;
                                                    this.m_AttrSerialNumber = "00000000000000";
                                                    if (iResize > 0) {
                                                        this.m_AttrSerialNumberStringLength = iResize;
                                                        this.m_AttrSerialNumber = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSerialNumberStringLength);
                                                    }
                                                    this.LOG.m385i("m_AttrSerialNumber", this.m_AttrSerialNumber);
                                                    this.LOG.m385i("m_AttrSerialNumberStringLength", String.valueOf(this.m_AttrSerialNumberStringLength));
                                                    if (!this.m_AttrProductIDString.equals(WirelessType.TYPE_P231) && !this.m_AttrProductIDString.equals(WirelessType.TYPE_P232)) {
                                                        if (this.m_AttrProductIDString.equals(WirelessType.TYPE_P520L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P310W) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P750L) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P461) || this.m_AttrProductIDString.equals(WirelessType.TYPE_P530D)) {
                                                            DecideNextStepAndPrepareReadBuff(1, 0, null);
                                                            break;
                                                        }
                                                    }
                                                    DecideNextStepAndPrepareReadBuff(13, 0, null);
                                                    break;
                                                }
                                                this.m_AttrSerialNumberStringLength = this.m_lpReadData[3];
                                                this.LOG.m385i("m_AttrSerialNumberStringLength", String.valueOf(this.m_AttrSerialNumberStringLength));
                                                DecideNextStepAndPrepareReadBuff(this.m_AttrSerialNumberStringLength, 0, null);
                                                break;
                                            }
                                            this.m_AttrFirmwareVersionString = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrFirmwareVersionStringLength);
                                            this.LOG.m385i("m_AttrFirmwareVersionString", this.m_AttrFirmwareVersionString);
                                            DecideNextStepAndPrepareReadBuff(4, 0, null);
                                            break;
                                        }
                                        this.m_AttrFirmwareVersionStringLength = this.m_lpReadData[3];
                                        this.LOG.m385i("m_AttrFirmwareVersionStringLength", String.valueOf(this.m_AttrFirmwareVersionStringLength));
                                        DecideNextStepAndPrepareReadBuff(this.m_AttrFirmwareVersionStringLength, 0, null);
                                        break;
                                    }
                                    this.m_AttrProductName = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrProductNameLength);
                                    this.LOG.m385i("m_AttrProductName!!", String.valueOf(this.m_AttrProductName));
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                this.m_AttrProductNameLength = this.m_lpReadData[2];
                                this.LOG.m385i("m_AttrProductNameLength", String.valueOf(this.m_AttrProductNameLength));
                                DecideNextStepAndPrepareReadBuff(this.m_AttrProductNameLength, 0, null);
                                break;
                            }
                            this.m_AttrProductIDString = GetWirelessType(this.m_lpReadData, 3);
                            this.LOG.m385i("m_AttrProductIDString", this.m_AttrProductIDString);
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
            }
            if (IsConnectError()) {
                boolean bRet;
                StopTimerOut();
                String strMSG = GetErrorMSGConnectFail();
                Stop();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                this.m_iResponseErrorTimes++;
                if (this.m_iResponseErrorTimes < 10) {
                    bRet = Reconnection();
                } else {
                    bRet = false;
                }
                if (!bRet) {
                    SendMessage(RequestState.REQUEST_GET_PRINTER_INFO_ERROR, strMSG);
                }
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    public String GetAttrFirmwareVersionString() {
        return this.m_AttrFirmwareVersionString;
    }

    public String GetAttrSerialNumber() {
        return this.m_AttrSerialNumber;
    }

    public String GetAttrBatteryLevel() {
        return this.m_AttrBatteryLevel;
    }

    public String GetAttrProductIDString() {
        return this.m_AttrProductIDString;
    }

    public String GetProductName() {
        return this.m_AttrProductName;
    }
}
