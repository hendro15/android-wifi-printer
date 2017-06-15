package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.JobInfo;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.PrinterErrorCode;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_SendPhotoPrinbiz extends HitiPPR_PrinterCommand {
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private Byte m_AttrErrorType;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    private String m_AttrSSID;
    private int m_AttrSSIDLength;
    private Byte m_AttrSecurityAlgorithm;
    private String m_AttrSecurityKey;
    private int m_AttrSecurityKeyLength;
    private SendPhotoListener m_SendPhotoListner;
    private byte m_bDuplex;
    private byte m_bTextTure;
    private byte m_boMaskColor;
    private byte m_byteJobQTY;
    private byte m_byteMediaSize;
    private byte m_bytePrintout;
    private byte m_byteSharpen;
    private int m_iCopoes;
    private int m_iJobId;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_SendPhotoPrinbiz.1 */
    static /* synthetic */ class C04121 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$printerprotocol$SettingStep;

        static {
            $SwitchMap$com$hiti$printerprotocol$SettingStep = new int[SettingStep.values().length];
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_Complete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataErrorDueToPrinter.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationRequest.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_AuthenticationResponse.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoRequest.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponse.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetNetworkInfoResponseSuccess.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PrepareCreateJobRequest.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobRequest.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponse.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_CreateJobResponseSuccess.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobRequest.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_StartJobResponse.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataRequest.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataResponse.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    public HitiPPR_SendPhotoPrinbiz(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_boMaskColor = (byte) 0;
        this.m_iCopoes = 0;
        this.m_byteMediaSize = (byte) -1;
        this.m_bytePrintout = (byte) -1;
        this.m_bTextTure = (byte) 0;
        this.m_bDuplex = (byte) 0;
        this.m_byteJobQTY = (byte) 0;
        this.m_byteSharpen = (byte) -120;
        this.m_SendPhotoListner = null;
        this.m_iJobId = 0;
        this.m_OneJob = new JobInfo();
    }

    private void InitialPrinterVariable() {
        this.m_AttrSSIDLength = -1;
        this.m_AttrSSID = null;
        this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
        this.m_AttrSecurityKeyLength = -1;
        this.m_AttrSecurityKey = null;
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorType = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
    }

    public void SetListener(SendPhotoListener sendPhotolistner) {
        this.m_SendPhotoListner = sendPhotolistner;
    }

    private boolean HaveSendPhotoListener() {
        if (this.m_SendPhotoListner == null) {
            return false;
        }
        return true;
    }

    public void StartRequest() {
        SendPhoto();
    }

    public void SendPhoto() {
        if (IsRunning()) {
            String strMSG;
            this.LOG.m385i("SendPhoto_step", String.valueOf(GetCurrentStep()));
            switch (C04121.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    StopForNextCommand();
                    SendMessage(RequestState.REQUEST_SEND_PHOTO, String.valueOf(this.m_OneJob.shJobId));
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    strMSG = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (!(this.m_ErrorString == null || this.m_ErrorString.isEmpty())) {
                        strMSG = this.m_ErrorString;
                    }
                    SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER, strMSG);
                    Stop();
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    InitialPrinterVariable();
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                        break;
                    }
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_GetNetworkInfoRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (!Get_NetWork_Info_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetNetworkInfoResponse);
                        break;
                    }
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetNetworkInfoResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrSSIDLength != -1) {
                                if (this.m_AttrSSID != null) {
                                    if (this.m_AttrSecurityAlgorithm.byteValue() != (byte) -1) {
                                        if (this.m_AttrSecurityKeyLength != -1) {
                                            if (this.m_AttrSecurityKey == null) {
                                                this.m_AttrSecurityKey = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSecurityKeyLength);
                                                this.LOG.m385i("m_AttrSecurityKey", this.m_AttrSecurityKey);
                                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                                this.m_AttrSSIDLength = -1;
                                                this.m_AttrSSID = null;
                                                this.m_AttrSecurityAlgorithm = Byte.valueOf((byte) -1);
                                                this.m_AttrSecurityKeyLength = -1;
                                                this.m_AttrSecurityKey = null;
                                                break;
                                            }
                                        }
                                        this.m_AttrSecurityKeyLength = this.m_lpReadData[3];
                                        this.LOG.m385i("m_AttrSecurityKeyLength", String.valueOf(this.m_AttrSecurityKeyLength));
                                        DecideNextStepAndPrepareReadBuff(this.m_AttrSecurityKeyLength + 1, 0, null);
                                        break;
                                    }
                                    this.m_AttrSecurityAlgorithm = Byte.valueOf(this.m_lpReadData[3]);
                                    this.LOG.m385i("m_AttrSecurityAlgorithm", String.valueOf(this.m_AttrSecurityAlgorithm));
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                this.m_AttrSSID = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrSSIDLength);
                                this.LOG.m385i("m_AttrSSID", this.m_AttrSSID);
                                if (this.m_lpReadData[this.m_AttrSSIDLength] != (byte) -1) {
                                    DecideNextStepAndPrepareReadBuff(4, 0, null);
                                    break;
                                }
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                this.m_AttrSSIDLength = -1;
                                this.m_AttrSSID = null;
                                break;
                            }
                            this.m_AttrSSIDLength = this.m_lpReadData[2];
                            this.LOG.m385i("m_AttrSSIDLength", String.valueOf(this.m_AttrSSIDLength));
                            DecideNextStepAndPrepareReadBuff(this.m_AttrSSIDLength + 1, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                        break;
                    }
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(17, 0, SettingStep.Step_GetPrinterStatusResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                if (this.m_AttrErrorDescriptionLength != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        this.m_AttrErrorDescription = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrErrorDescriptionLength);
                                        this.LOG.m385i("m_AttrErrorDescription", this.m_AttrErrorDescription);
                                        if (!this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
                                            SetErrorMSG(this.m_AttrErrorDescription);
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                            break;
                                        }
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0001));
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                }
                                this.m_AttrErrorDescriptionLength = this.m_lpReadData[2];
                                DecideNextStepAndPrepareReadBuff(this.m_AttrErrorDescriptionLength + 1, 0, null);
                                break;
                            }
                            this.m_AttrPrinterStatusFlag = new byte[3];
                            this.m_AttrPrinterStatusFlag[0] = this.m_lpReadData[3];
                            this.m_AttrPrinterStatusFlag[1] = this.m_lpReadData[4];
                            this.m_AttrPrinterStatusFlag[2] = this.m_lpReadData[5];
                            this.m_AttrPrinterStatus = Byte.valueOf(this.m_lpReadData[10]);
                            this.m_AttrErrorType = Byte.valueOf(this.m_lpReadData[15]);
                            this.LOG.m385i("m_AttrPrinterStatusFlag", Integer.toHexString(this.m_AttrPrinterStatusFlag[0]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[1]) + " " + String.valueOf(this.m_AttrPrinterStatusFlag[2]));
                            this.LOG.m384e("m_AttrPrinterStatus", Integer.toHexString(this.m_AttrPrinterStatus.byteValue()));
                            this.LOG.m385i("m_AttrErrorType", Integer.toHexString(this.m_AttrErrorType.byteValue()));
                            this.LOG.m385i("m_lpReadData[16]", Integer.toHexString(this.m_lpReadData[16]));
                            if (this.m_lpReadData[16] == (byte) -1) {
                                if (this.m_AttrPrinterStatus.byteValue() != (byte) 1) {
                                    boolean bIsJobIdQueueEmpty = false;
                                    if (HaveSendPhotoListener()) {
                                        bIsJobIdQueueEmpty = this.m_SendPhotoListner.CheckJobIdIfEmpty();
                                    }
                                    if (bIsJobIdQueueEmpty) {
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                }
                                if (ByteConvertUtility.CheckBit(this.m_AttrPrinterStatusFlag[0], Byte.MIN_VALUE) != 0) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                    DecideNextStep(SettingStep.Step_Error);
                                    break;
                                }
                                this.m_AttrErrorDescriptionLength = 0;
                                this.m_AttrErrorDescription = null;
                                DecideNextStep(SettingStep.Step_PrepareCreateJobRequest);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (this.m_imageData == null) {
                        Stop();
                        break;
                    }
                    this.m_OneJob.shJobId = 0;
                    this.m_OneJob.bFrmt = (byte) 1;
                    this.m_OneJob.shCopies = (short) this.m_iCopoes;
                    this.m_OneJob.lSize = (long) this.m_imageData.length;
                    this.m_OneJob.bQlty = this.m_byteJobQTY;
                    this.m_OneJob.bType = (byte) 1;
                    this.m_OneJob.bMSize = this.m_byteMediaSize;
                    this.m_OneJob.bPrintLayout = this.m_bytePrintout;
                    this.m_OneJob.bTxtr = this.m_bTextTure;
                    this.m_OneJob.boDuplex = this.m_bDuplex;
                    this.m_offsetWrite = 0;
                    this.m_OneJob.iTotal = 1;
                    this.m_OneJob.iMaskSize = 0;
                    this.m_OneJob.iTotalSize = 0;
                    this.m_OneJob.bSharpen = this.m_byteSharpen;
                    this.m_OneJob.boMaskColor = this.m_boMaskColor;
                    this.LOG.m384e("m_bTextTure", String.valueOf(this.m_OneJob.bTxtr));
                    this.LOG.m384e("m_iCopoes", String.valueOf(this.m_OneJob.shCopies));
                    this.LOG.m384e("m_bDuplex", String.valueOf(this.m_OneJob.boDuplex));
                    this.LOG.m384e("bPrintLayout", String.valueOf(this.m_OneJob.bPrintLayout));
                    if (this.m_maskData != null) {
                        this.m_OneJob.iTotal = 2;
                        this.m_OneJob.iMaskSize = this.m_maskData.length;
                    }
                    if (this.m_duplexData != null) {
                        this.m_OneJob.iTotal = 2;
                        this.m_OneJob.iDuplexSize = this.m_duplexData.length;
                    }
                    int[] iSizeArray = new int[this.m_OneJob.iTotal];
                    iSizeArray[0] = (int) this.m_OneJob.lSize;
                    if (this.m_OneJob.iTotal > 1) {
                        if (this.m_maskData != null) {
                            iSizeArray[1] = this.m_OneJob.iMaskSize;
                        }
                        if (this.m_duplexData != null) {
                            iSizeArray[1] = this.m_OneJob.iDuplexSize;
                        }
                    }
                    this.m_OneJob.iTotalSize = GetTotalSize(this.m_OneJob.iTotal, iSizeArray);
                    DecideNextStep(SettingStep.Step_CreateJobRequest);
                    break;
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (!Create_Job_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_CreateJobResponse);
                        break;
                    }
                case ConnectionResult.CANCELED /*13*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(8, 0, SettingStep.Step_CreateJobResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.TIMEOUT /*14*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_lpReadData[7] != (byte) -1) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            this.m_OneJob.shJobId = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                            this.m_iJobId = this.m_OneJob.shJobId;
                            DecideNextStep(SettingStep.Step_StartJobRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.INTERRUPTED /*15*/:
                    if (!Start_Job_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_StartJobResponse);
                        break;
                    }
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_SendDataRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_FAILED /*17*/:
                    long nWrite = 0;
                    if (this.m_OneJob.iTotalSize <= 0) {
                        Stop();
                        break;
                    }
                    if (this.m_OneJob.iTotalSize < HitiPPR_PrinterCommand.MAX_DATA_SIZE) {
                        nWrite = (long) this.m_OneJob.iTotalSize;
                    }
                    this.LOG.m385i("Send data size: " + String.valueOf(nWrite), "Step_SendDataRequest");
                    nWrite = Send_Data_Request(nWrite);
                    this.m_offsetWrite += nWrite;
                    JobInfo jobInfo = this.m_OneJob;
                    jobInfo.iTotalSize = (int) (((long) jobInfo.iTotalSize) - nWrite);
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_SendDataResponse);
                    break;
                case ConnectionResult.SERVICE_UPDATING /*18*/:
                    if (ReadResponse(DNSConstants.RECORD_REAPER_INTERVAL)) {
                        if (IsReadComplete()) {
                            if (CheckCommandSuccess()) {
                                if (this.m_OneJob.iTotalSize <= 0) {
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_SendDataRequest);
                                    break;
                                }
                            }
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                Stop();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                if (strMSG.equals(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0001))) {
                    Stop();
                    SendMessage(RequestState.REQUEST_ERROR_0001, strMSG);
                } else if (strMSG.equals(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY))) {
                    StopForNextCommand();
                    SendMessage(RequestState.REQUEST_PRINTER_BUSY_OR_PRINTING, strMSG);
                } else {
                    boolean bRet;
                    this.m_iResponseErrorTimes++;
                    if (this.m_iResponseErrorTimes < 10) {
                        bRet = Reconnection();
                    } else {
                        bRet = false;
                    }
                    if (!bRet) {
                        Stop();
                        SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR, strMSG);
                    }
                }
            } else if (IsTimeoutError()) {
                Stop();
                strMSG = GetErrorMSGTimeOut();
                this.LOG.m384e("SendPhoto_IsTimeoutError_", "=" + String.valueOf(strMSG));
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, strMSG);
            }
        }
    }

    public String GetAttrSSID() {
        return this.m_AttrSSID;
    }

    public String GetAttrSecurityKey() {
        return this.m_AttrSecurityKey;
    }

    public void SetMaskColor(byte boMaskColor) {
        this.m_boMaskColor = boMaskColor;
    }

    public byte GetMaskColor() {
        return this.m_boMaskColor;
    }

    public int GetCopies() {
        return this.m_iCopoes;
    }

    public void SetCopies(int iCopies) {
        this.m_iCopoes = iCopies;
    }

    public void SetTextTurePrint(boolean status) {
        if (status) {
            this.m_bTextTure = (byte) 1;
        }
        this.LOG.m385i("SetTextTurePrint_" + status, String.valueOf(this.m_bTextTure));
    }

    public void SetDuplex(boolean status) {
        if (status) {
            this.m_bDuplex = (byte) 1;
        }
    }

    public void SetMediaSize(byte byChoseMediaSize) {
        this.m_byteMediaSize = byChoseMediaSize;
    }

    public void SetPrintout(byte byChosePrintout) {
        this.m_bytePrintout = byChosePrintout;
    }

    public void SetMethodQTY(byte byteJobQTY) {
        this.m_byteJobQTY = byteJobQTY;
    }

    public void SetSharpenValue(byte byteSharpen) {
        this.m_byteSharpen = byteSharpen;
    }

    public int GetJobId() {
        if (this.m_OneJob != null) {
            this.LOG.m384e("SendPhoto_GetJobId", "oneJob=" + String.valueOf(this.m_OneJob.shJobId));
        } else {
            this.LOG.m384e("SendPhoto_GetJobId!!!", "oneJob=" + String.valueOf(this.m_OneJob));
        }
        return this.m_iJobId;
    }
}
