package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import android.support.v4.media.TransportMediator;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetUnCleanNumber extends HitiPPR_PrinterCommandNew {
    private int m_AttrCleanLength;
    private int m_AttrUnCleanNumber;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetUnCleanNumber.1 */
    static /* synthetic */ class C04051 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetUnCleanNumberResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public HitiPPR_GetUnCleanNumber(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_AttrUnCleanNumber = -1;
        this.m_AttrCleanLength = -1;
    }

    public void StartRequest() {
        CheckUnCleanNuber();
    }

    public void CheckUnCleanNuber() {
        this.LOG.m385i("CheckUnCleanNuber", XmlPullParser.NO_NAMESPACE + GetCurrentStep());
        if (IsRunning()) {
            switch (C04051.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_UN_CLEAN_NUMBER, String.valueOf(this.m_AttrUnCleanNumber));
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
                                DecideNextStep(SettingStep.Step_GetUnCleanNumberRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_UnClean_Number_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetUnCleanNumberResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStepAndPrepareReadBuff(3, 0, SettingStep.Step_GetUnCleanNumberResponseSuccess);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                        break;
                    }
                    if (IsReadComplete()) {
                        if (this.m_AttrCleanLength != -1) {
                            byte iFlag = this.m_lpReadData[0];
                            this.LOG.m385i("Get Clean Flag", XmlPullParser.NO_NAMESPACE + iFlag);
                            if ((iFlag & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0 && this.m_AttrUnCleanNumber == -1) {
                                int iUnCleanFrame = ByteConvertUtility.ByteToInt(this.m_lpReadData, 3, 4);
                                this.LOG.m385i("iUnCleanFrame", XmlPullParser.NO_NAMESPACE + iUnCleanFrame);
                                if (iUnCleanFrame != -1) {
                                    this.m_AttrUnCleanNumber = iUnCleanFrame / 4;
                                    DecideNextStep(SettingStep.Step_Complete);
                                    break;
                                }
                            }
                        }
                        this.m_AttrCleanLength = this.m_lpReadData[2];
                        this.LOG.m385i("m_AttrCleanLength", XmlPullParser.NO_NAMESPACE + this.m_AttrCleanLength);
                        DecideNextStepAndPrepareReadBuff(this.m_AttrCleanLength, 0, null);
                        break;
                    }
                    DecideNextStep(SettingStep.Step_Error);
                    break;
            }
            String strMSG;
            if (IsConnectError()) {
                StopTimerOut();
                Stop();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_GET_UN_CLEAN_NUMBER_ERROR, "error");
            } else if (IsTimeoutError()) {
                Stop();
                strMSG = GetErrorMSGTimeOut();
                SendMessage(RequestState.REQUEST_GET_UN_CLEAN_NUMBER_ERROR, "error");
            }
        }
    }

    public int GetUnCleanNumber() {
        return this.m_AttrUnCleanNumber;
    }
}
