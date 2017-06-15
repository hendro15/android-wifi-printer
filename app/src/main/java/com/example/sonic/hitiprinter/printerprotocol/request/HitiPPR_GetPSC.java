package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.PrinterErrorCode;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.printerprotocol.utility.PrinterInfo;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetPSC extends HitiPPR_PrinterCommandNew {
    String TAG;
    ArrayList<Byte> mFrameTypeList;
    private byte m_AttrCurrentMediaSize;
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private int m_AttrPrintFrameLength;
    private long m_AttrPrintFrameNumber;
    private Byte m_AttrPrinterStatus;
    private byte[] m_AttrPrinterStatusFlag;
    private Boolean m_AttrTexturePrint;
    private String m_Err001;
    boolean m_bCheckCapabilty;
    private boolean m_bCopiesPlus;
    private boolean m_bCpAttrGet;
    private boolean m_bNext;
    private byte m_byteJobStatus;
    private int m_iCopiesOfJob;
    HashMap<String, Integer> m_iFrameCountList;
    private int m_iJobId;
    private int m_iLastCopies;
    private int m_iTypeCnt;
    private String m_strChooseMediaSize;
    private String m_strProductIDString;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetPSC.1 */
    static /* synthetic */ class C04011 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusRequest.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponse.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetJobStatusResponseSuccess.ordinal()] = 7;
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesRequest.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponse.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterCapabilitiesResponseSuccess.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameRequest.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponse.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponseSuccess.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    public HitiPPR_GetPSC(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_bCheckCapabilty = false;
        this.m_AttrCurrentMediaSize = (byte) -1;
        this.m_AttrTexturePrint = null;
        this.m_strChooseMediaSize = null;
        this.m_iJobId = -1;
        this.m_byteJobStatus = (byte) -1;
        this.m_iCopiesOfJob = -1;
        this.m_iLastCopies = 0;
        this.m_bCpAttrGet = false;
        this.m_bNext = false;
        this.m_Err001 = null;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
        this.m_iTypeCnt = -1;
        this.m_strProductIDString = null;
        this.m_bCopiesPlus = false;
        this.m_iFrameCountList = null;
        this.mFrameTypeList = null;
        this.TAG = "HitiPPR_GetPSC";
    }

    private void InitialVariable() {
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrPrinterStatus = Byte.valueOf((byte) -1);
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrCurrentMediaSize = (byte) -1;
        this.m_AttrTexturePrint = null;
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
    }

    public void StartRequest() {
        GetPrinterStatusAndCapability();
    }

    public void GetPrinterStatusAndCapability() {
        if (IsRunning()) {
            String str;
            this.LOG.m385i("HitiPPR_GetPSC step: ", String.valueOf(GetCurrentStep()));
            switch (C04011.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    str = XmlPullParser.NO_NAMESPACE;
                    SendMessage(RequestState.REQUEST_GET_PRINT_STAUTS, this.m_Context.getString(this.R_STRING_PRINT_DONE));
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    str = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (this.m_ErrorString != null) {
                        str = this.m_ErrorString;
                    }
                    SendMessage(RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER, str);
                    Stop();
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    InitialVariable();
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
                            if (CheckCommandSuccess()) {
                                if (!this.m_bCheckCapabilty) {
                                    DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                    break;
                                } else {
                                    DecideNextStep(SettingStep.Step_GetPrinterCapabilitiesRequest);
                                    break;
                                }
                            }
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (this.m_iJobId != -1) {
                        if (!Get_Job_Status_Request(this.m_iJobId)) {
                            DecideNextStep(SettingStep.Step_Error);
                            break;
                        } else {
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetJobStatusResponse);
                            break;
                        }
                    }
                    DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] != 66 || this.m_lpReadData[3] != 2) {
                                    DecideNextStep(SettingStep.Step_Error);
                                    break;
                                }
                                this.m_bNext = true;
                                DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(5, 0, SettingStep.Step_GetJobStatusResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_byteJobStatus == -1) {
                                this.m_byteJobStatus = this.m_lpReadData[3];
                                if (this.m_lpReadData[4] != -1) {
                                    this.m_bCpAttrGet = true;
                                    DecideNextStepAndPrepareReadBuff(12, 0, null);
                                    break;
                                }
                            }
                            if (!this.m_bCpAttrGet) {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                            this.m_bCpAttrGet = false;
                            this.m_iCopiesOfJob = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                            if (this.m_iCopiesOfJob > 0 && this.m_iCopiesOfJob == this.m_iLastCopies + 1) {
                                this.m_iLastCopies = this.m_iCopiesOfJob;
                                this.m_bCopiesPlus = true;
                                DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
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
                                        if (!this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
                                            SetErrorMSG(this.m_AttrErrorDescription);
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                            break;
                                        }
                                        this.LOG.m384e("Err0001_JobStatus", Integer.toHexString(this.m_AttrPrinterStatus.byteValue()));
                                        if (this.m_byteJobStatus != -128) {
                                            SetErrorMSG(this.m_Context.getString(this.R_STRING_PRINT_BUSY));
                                            DecideNextStep(SettingStep.Step_Error);
                                            break;
                                        }
                                        this.m_Err001 = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0001);
                                        SetErrorMSG(this.m_Err001);
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
                            if (this.m_lpReadData[16] == -1) {
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (this.m_AttrPrinterStatus.byteValue() == 1) {
                                    if (ByteConvertUtility.CheckBit(this.m_AttrPrinterStatusFlag[0], Byte.MIN_VALUE) != 0) {
                                        if (this.m_byteJobStatus != -128) {
                                            SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                        } else {
                                            SetErrorMSG(this.m_Context.getString(this.R_STRING_PRINT_BUSY));
                                        }
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrErrorDescriptionLength = -1;
                                    this.m_AttrErrorDescription = null;
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                }
                                if (this.m_byteJobStatus != -128) {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY));
                                } else {
                                    SetErrorMSG(this.m_Context.getString(this.R_STRING_PRINT_BUSY));
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                    if (!Get_Printer_Capabilities_For_QuickPrint_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterCapabilitiesResponse);
                        break;
                    }
                case FTP.COMPRESSED_TRANSFER_MODE /*12*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(5, 0, SettingStep.Step_GetPrinterCapabilitiesResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.CANCELED /*13*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrCurrentMediaSize != -1) {
                                if (this.m_AttrTexturePrint == null) {
                                    if (this.m_lpReadData[3] == 1) {
                                        this.m_AttrTexturePrint = Boolean.valueOf(true);
                                    }
                                    if (this.m_strChooseMediaSize != null) {
                                        String strMediasize = this.m_strChooseMediaSize;
                                        if (this.m_strProductIDString.equals(WirelessType.TYPE_P530D)) {
                                            strMediasize = "6x8";
                                        }
                                        this.LOG.m385i(this.TAG, "strMediasize: " + this.m_strChooseMediaSize);
                                        if (!strMediasize.equals(GetPrinterMediaSize(this.m_AttrCurrentMediaSize))) {
                                            Stop();
                                            SendMessage(RequestState.REQUEST_PAPER_NOT_MATCH, this.m_strChooseMediaSize);
                                            break;
                                        }
                                    }
                                    DecideNextStep(SettingStep.Step_GetJobStatusRequest);
                                    break;
                                }
                            }
                            this.m_AttrCurrentMediaSize = this.m_lpReadData[3];
                            DecideNextStepAndPrepareReadBuff(5, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.TIMEOUT /*14*/:
                    if (this.m_iFrameCountList == null) {
                        this.m_iFrameCountList = new HashMap();
                    }
                    this.LOG.m385i(this.TAG, "PID: " + this.m_strProductIDString);
                    if (this.m_strProductIDString != null) {
                        this.mFrameTypeList = PrinterInfo.SetFrameType(this.m_strProductIDString);
                    }
                    GetPrintFrameCount();
                    break;
                case ConnectionResult.INTERRUPTED /*15*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetPrintFrameResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.API_UNAVAILABLE /*16*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrintFrameLength != -1) {
                                if (this.m_AttrPrintFrameNumber == -1) {
                                    if (this.m_AttrPrintFrameLength != 7) {
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrPrintFrameNumber = ByteConvertUtility.ByteToLong(this.m_lpReadData, 3, 4);
                                    int iTotalFrame = GetPrinterPageNumber(this.m_strProductIDString, this.m_AttrPrintFrameNumber);
                                    if (iTotalFrame < 0) {
                                        iTotalFrame = 0;
                                    }
                                    String strPrintout = PrinterInfo.GetFrameName(this.m_Context, ((Byte) this.mFrameTypeList.get(0)).byteValue());
                                    this.m_iFrameCountList.put(strPrintout, Integer.valueOf(iTotalFrame));
                                    this.mFrameTypeList.remove(0);
                                    this.LOG.m385i(this.TAG, "strMediasize: " + this.m_strChooseMediaSize);
                                    this.LOG.m385i(this.TAG, "strPrintout: " + strPrintout);
                                    this.LOG.m385i(this.TAG, "m_iFrameCountList: " + this.m_iFrameCountList);
                                    if (String.valueOf(strPrintout).equals(this.m_strChooseMediaSize) && this.m_bCopiesPlus) {
                                        this.m_bCopiesPlus = false;
                                        SendMessage(RequestState.REQUEST_CHECK_COPIES, String.valueOf(iTotalFrame));
                                    }
                                    this.m_AttrPrintFrameNumber = -1;
                                    this.m_AttrPrintFrameLength = -1;
                                    if (this.mFrameTypeList.size() <= 0) {
                                        if (!this.m_bNext) {
                                            SendMessage(RequestState.REQUEST_GPS_TOTAL_PRINTED_COUNT, null);
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                            break;
                                        }
                                        this.m_bNext = false;
                                        this.LOG.m383d(this.TAG, "Next: " + this.m_strChooseMediaSize);
                                        this.LOG.m383d(this.TAG, "Next: " + this.m_iFrameCountList);
                                        SendMessage(RequestState.REQUEST_QUICK_PRINT_NEXT, String.valueOf(this.m_iFrameCountList.get(this.m_strChooseMediaSize)));
                                        StopForNextCommand();
                                        break;
                                    }
                                    GetPrintFrameCount();
                                    break;
                                }
                            }
                            this.m_AttrPrintFrameLength = (this.m_lpReadData[1] << 8) | this.m_lpReadData[2];
                            DecideNextStepAndPrepareReadBuff(this.m_AttrPrintFrameLength, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
            }
            boolean bRet;
            if (IsConnectError()) {
                StopTimerOut();
                str = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    str = this.m_ErrorString;
                }
                this.LOG.m385i("GetPSC_IsConnectError", String.valueOf(str));
                if (str.equals(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_BUSY)) || str.equals(this.m_Context.getString(this.R_STRING_PRINT_BUSY))) {
                    StopForNextCommand();
                    SendMessage(RequestState.REQUEST_PRINTER_BUSY_OR_PRINTING, str);
                } else if (str.equals(this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0001))) {
                    Stop();
                    SendMessage(RequestState.REQUEST_ERROR_0001, String.valueOf(this.m_iJobId));
                } else {
                    this.LOG.m385i("m_iResponseErrorTimes", String.valueOf(this.m_iResponseErrorTimes));
                    this.m_iResponseErrorTimes++;
                    if (this.m_iResponseErrorTimes < 10) {
                        bRet = Reconnection();
                    } else {
                        bRet = false;
                    }
                    if (!bRet) {
                        Stop();
                        SendMessage(RequestState.REQUEST_GET_PRINT_STAUTS_ERROR, str);
                    }
                }
            } else if (IsTimeoutError()) {
                this.m_iResponseErrorTimes++;
                if (this.m_iResponseErrorTimes < 10) {
                    bRet = Reconnection();
                }
                if (!Reconnection()) {
                    Stop();
                    SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                }
            }
        }
    }

    private String GetPrinterMediaSize(byte byCurrentMediaSize) {
        if (byCurrentMediaSize == 1) {
            return "4x6";
        }
        if (byCurrentMediaSize == 2) {
            return "5x7";
        }
        if (byCurrentMediaSize == 3) {
            return "6x8";
        }
        if (byCurrentMediaSize == 4) {
            return "6x9";
        }
        if (byCurrentMediaSize == 5) {
            return "2x3";
        }
        if (this.m_strProductIDString.equals(WirelessType.TYPE_P530D)) {
            return "6x8";
        }
        return "4x6";
    }

    public String GetPrinterMediaSize() {
        return GetPrinterMediaSize(this.m_AttrCurrentMediaSize);
    }

    public boolean CheckTextureSupport() {
        return this.m_AttrTexturePrint.booleanValue();
    }

    public void SetChooseMediaSize(String strMediaSize) {
        this.m_strChooseMediaSize = strMediaSize;
    }

    public void OpenCapabilityCheck(boolean status) {
        this.m_bCheckCapabilty = status;
    }

    public void PutJobIdToCheck(int iJobId) {
        this.m_iJobId = iJobId;
    }

    public void PutLastCopiesToNextGetStatus(int iLastCp) {
        this.m_iLastCopies = iLastCp;
    }

    public byte GetNowJobIdState() {
        return this.m_byteJobStatus;
    }

    public int GetJobId() {
        return this.m_iJobId;
    }

    public int GetLastCopies() {
        return this.m_iLastCopies;
    }

    public HashMap<String, Integer> GetTotalPrintedFrame() {
        return this.m_iFrameCountList;
    }

    public void SetProductID(String strProductIDString) {
        this.m_strProductIDString = strProductIDString;
    }

    private void GetPrintFrameCount() {
        this.LOG.m385i("Get_PSC", "GetPrintFrameCount");
        if (this.m_strProductIDString != null && this.mFrameTypeList != null && !this.mFrameTypeList.isEmpty()) {
            if (Get_Print_Frame_Or_Page_Request(((Byte) this.mFrameTypeList.get(0)).byteValue())) {
                DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrintFrameResponse);
            } else {
                DecideNextStep(SettingStep.Step_Error);
            }
        }
    }
}
