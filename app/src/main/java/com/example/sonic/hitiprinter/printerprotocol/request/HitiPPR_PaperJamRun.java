package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.PrinterErrorCode;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_PaperJamRun extends HitiPPR_PrinterCommandNew {
    private String m_AttrErrorDescription;
    private int m_AttrErrorDescriptionLength;
    private int m_AttrPaperJamLength;
    private byte[] m_AttrPrinterStatusFlag;
    private boolean m_bEjectJamDone;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_PaperJamRun.1 */
    static /* synthetic */ class C04081 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrinterStatusResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PaperJamRunRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PaperJamRunResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_PaperJamRunResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_SendDataErrorDueToPrinter.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public HitiPPR_PaperJamRun(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrPrinterStatusFlag = null;
        this.m_AttrErrorDescriptionLength = -1;
        this.m_AttrErrorDescription = null;
        this.m_AttrPaperJamLength = -1;
        this.m_bEjectJamDone = false;
    }

    public void StartRequest() {
        PaperJamRun();
    }

    public void PaperJamRun() {
        if (IsRunning()) {
            String strMSG;
            this.LOG.m385i("PaperJamRun", String.valueOf(GetCurrentStep()));
            switch (C04081.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_PAPER_JAM_RUN, null);
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
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
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_Printer_Status_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrinterStatusResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
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
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrinterStatusFlag != null) {
                                if (this.m_AttrErrorDescriptionLength != -1) {
                                    if (this.m_AttrErrorDescription == null) {
                                        this.m_AttrErrorDescription = ByteConvertUtility.ByteToString(this.m_lpReadData, 0, this.m_AttrErrorDescriptionLength);
                                        SetErrorMSG(this.m_AttrErrorDescription);
                                        this.LOG.m385i("Printer error, eject jam done: " + this.m_bEjectJamDone, String.valueOf(this.m_AttrErrorDescription));
                                        if (!CheckPrinterStatusNotBusy(this.m_AttrPrinterStatusFlag)) {
                                            try {
                                                sleep(500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                        } else if (!CheckPrinterStatusCoverOpen(this.m_AttrPrinterStatusFlag)) {
                                            SetErrorMSG("NEED COVER OPEN FOR EJECT JAM");
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                        } else if (!this.m_bEjectJamDone) {
                                            this.m_bEjectJamDone = true;
                                            DecideNextStep(SettingStep.Step_PaperJamRunRequest);
                                        } else if (this.m_AttrErrorDescription.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0100)) {
                                            DecideNextStep(SettingStep.Step_Complete);
                                        } else {
                                            DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                        }
                                        this.m_AttrPrinterStatusFlag = null;
                                        this.m_AttrErrorDescriptionLength = -1;
                                        this.m_AttrErrorDescription = null;
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
                            this.LOG.m385i("m_AttrPrinterStatusFlag[0]", String.valueOf(this.m_AttrPrinterStatusFlag[0]));
                            this.LOG.m385i("m_AttrPrinterStatusFlag[1]", String.valueOf(this.m_AttrPrinterStatusFlag[1]));
                            this.LOG.m385i("m_AttrPrinterStatusFlag[2]", String.valueOf(this.m_AttrPrinterStatusFlag[2]));
                            if (this.m_lpReadData[16] == (byte) -1) {
                                SetErrorMSG("NEED COVER OPEN FOR EJECT JAM");
                                DecideNextStep(SettingStep.Step_SendDataErrorDueToPrinter);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(3, 0, null);
                            break;
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Paper_Jam_Run_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_PaperJamRunResponse);
                        break;
                    }
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_PaperJamRunResponseSuccess);
                                break;
                            }
                        }
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    }
                    if (IsReadComplete()) {
                        if (this.m_AttrPaperJamLength != -1) {
                            if (this.m_lpReadData[1] == -32 && this.m_lpReadData[2] == 52 && CheckPrinterStatusNotBusy(this.m_lpReadData)) {
                                DecideNextStep(SettingStep.Step_GetPrinterStatusRequest);
                                break;
                            }
                        }
                        this.m_AttrPaperJamLength = this.m_lpReadData[2];
                        DecideNextStepAndPrepareReadBuff(this.m_AttrPaperJamLength, 0, null);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
                case ConnectionResult.DEVELOPER_ERROR /*10*/:
                    strMSG = this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
                    if (this.m_ErrorString != null) {
                        strMSG = this.m_ErrorString;
                    }
                    SendMessage(RequestState.REQUEST_PAPER_JAM_RUN_ERROR_DUETO_PRINTER, strMSG);
                    Stop();
                    break;
            }
            if (IsConnectError()) {
                StopTimerOut();
                Stop();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_PAPER_JAM_RUN_ERROR, strMSG);
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    private boolean CheckPrinterStatusPaperJam(byte[] mStatusFlag) {
        this.LOG.m385i("CheckPrinterStatusPaperJam", XmlPullParser.NO_NAMESPACE + mStatusFlag[1]);
        if (ByteConvertUtility.CheckBit(mStatusFlag[1], (byte) 2) != 0) {
            return true;
        }
        return false;
    }

    private boolean CheckPrinterStatusCoverOpen(byte[] mStatusFlag) {
        this.LOG.m385i("CheckPrinterStatusCoverOpen", XmlPullParser.NO_NAMESPACE + mStatusFlag[2]);
        if (ByteConvertUtility.CheckBit(mStatusFlag[2], (byte) 8) != 0) {
            return true;
        }
        return false;
    }

    private boolean CheckPrinterStatusNotBusy(byte[] mStatusFlag) {
        this.LOG.m385i("CheckPrinterStatusNotBusy", XmlPullParser.NO_NAMESPACE + mStatusFlag[0]);
        if (ByteConvertUtility.CheckBit(mStatusFlag[0], Byte.MIN_VALUE) == 0) {
            return true;
        }
        return false;
    }

    public static boolean IsPaperJamErrorType(String strMSG) {
        if (strMSG == null) {
            return false;
        }
        if (strMSG.contains("050")) {
            return true;
        }
        if (strMSG.contains("051")) {
            return true;
        }
        if (strMSG.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0301)) {
            return true;
        }
        if (strMSG.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0600)) {
            return true;
        }
        return false;
    }
}
