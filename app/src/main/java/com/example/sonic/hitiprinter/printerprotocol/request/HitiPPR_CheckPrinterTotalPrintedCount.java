package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.printerprotocol.utility.PrinterInfo;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_CheckPrinterTotalPrintedCount extends HitiPPR_PrinterCommand {
    ArrayList<Byte> mFrameTypeList;
    HitiPPR_GetPSC mHitiPPR_GetPSC;
    private int m_AttrPrintFrameLength;
    private long m_AttrPrintFrameNumber;
    private String m_AttrProductIDString;
    private int m_AttrTotalPageNumber;
    HashMap<String, Integer> m_iFrameCountList;
    private int m_iTypeCnt;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_CheckPrinterTotalPrintedCount.1 */
    static /* synthetic */ class C03941 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetPrintFrameResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public Integer GetTotalPrintedFrame(String paperSize) {
        return (Integer) this.m_iFrameCountList.get(paperSize);
    }

    public HashMap<String, Integer> GetTotalPrintedFrame() {
        return this.m_iFrameCountList;
    }

    public String GetProductID() {
        return this.m_AttrProductIDString;
    }

    public void SetProductID(String strProductId) {
        this.m_AttrProductIDString = strProductId;
    }

    public void SetGPSrequest(HitiPPR_GetPSC mHitiPPR_GetPSC) {
        this.mHitiPPR_GetPSC = mHitiPPR_GetPSC;
    }

    public HitiPPR_GetPSC GetGPSrequest() {
        return this.mHitiPPR_GetPSC;
    }

    public HitiPPR_CheckPrinterTotalPrintedCount(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
        this.m_AttrTotalPageNumber = -1;
        this.m_iTypeCnt = -1;
        this.m_AttrProductIDString = null;
        this.m_iFrameCountList = null;
        this.mFrameTypeList = null;
        this.mHitiPPR_GetPSC = null;
    }

    public void StartRequest() {
        CheckPrintComplete();
    }

    private void InitalVariable() {
        this.m_AttrPrintFrameLength = -1;
        this.m_AttrPrintFrameNumber = -1;
        this.m_AttrTotalPageNumber = -1;
        this.m_iTypeCnt = -1;
    }

    public void CheckPrintComplete() {
        if (IsRunning()) {
            switch (C03941.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_ALBUM_ID_META, null);
                    StopForNextCommand();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    InitalVariable();
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
                                DecideNextStep(SettingStep.Step_GetPrintFrameRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (this.m_AttrProductIDString != null) {
                        this.mFrameTypeList = PrinterInfo.SetFrameType(this.m_AttrProductIDString);
                    }
                    this.m_iFrameCountList = new HashMap();
                    GetPrintFrame();
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
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
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (this.m_AttrPrintFrameLength != -1) {
                                if (this.m_AttrPrintFrameNumber == -1) {
                                    if (this.m_AttrPrintFrameLength != 7) {
                                        DecideNextStep(SettingStep.Step_Error);
                                        break;
                                    }
                                    this.m_AttrPrintFrameNumber = ByteConvertUtility.ByteToLong(this.m_lpReadData, 3, 4);
                                    int iTotalFrame = GetPrinterPageNumber(this.m_AttrProductIDString, this.m_AttrPrintFrameNumber);
                                    if (iTotalFrame < 0) {
                                        iTotalFrame = 0;
                                    }
                                    String strPrintout = PrinterInfo.GetFrameName(this.m_Context, ((Byte) this.mFrameTypeList.get(0)).byteValue());
                                    this.m_iFrameCountList.put(strPrintout, Integer.valueOf(iTotalFrame));
                                    this.mFrameTypeList.remove(0);
                                    this.LOG.m383d("iPaperCountList " + strPrintout, String.valueOf(iTotalFrame));
                                    this.m_AttrPrintFrameNumber = -1;
                                    this.m_AttrTotalPageNumber = -1;
                                    this.m_AttrPrintFrameLength = -1;
                                    if (this.mFrameTypeList.size() <= 0) {
                                        DecideNextStep(SettingStep.Step_Complete);
                                        break;
                                    } else {
                                        GetPrintFrame();
                                        break;
                                    }
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
            if (IsConnectError()) {
                StopTimerOut();
                Stop();
                String strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                if (!Reconnection()) {
                    SendMessage(RequestState.REQUEST_GET_ALBUM_ID_META_ERROR, strMSG);
                }
            } else if (IsTimeoutError()) {
                Stop();
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
            }
        }
    }

    private void GetPrintFrame() {
        if (this.m_AttrProductIDString != null && this.mFrameTypeList != null && !this.mFrameTypeList.isEmpty()) {
            if (Get_Print_Frame_Or_Page_Request(((Byte) this.mFrameTypeList.get(0)).byteValue())) {
                DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetPrintFrameResponse);
            } else {
                DecideNextStep(SettingStep.Step_Error);
            }
        }
    }
}
