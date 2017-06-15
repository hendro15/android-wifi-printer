package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.printerprotocol.printercommand.Job;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;

public class HitiPPR_RecoveryPrinter extends HitiPPR_PrinterCommand {
    Job mJob;
    private boolean m_boSilent;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_RecoveryPrinter.1 */
    static /* synthetic */ class C04101 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_RecoveryPrinterRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_RecoveryPrinterResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public HitiPPR_RecoveryPrinter(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_boSilent = false;
        this.mJob = null;
    }

    public void putJob(Job job) {
        if (job != null) {
            this.mJob = job;
            SetSocket(job.getSocket());
        }
    }

    public Job getJob() {
        return this.mJob;
    }

    public void SetSilentMode() {
        this.m_boSilent = true;
    }

    public void StartRequest() {
        RecoveryPrinter();
    }

    public void RecoveryPrinter() {
        if (IsRunning()) {
            this.LOG.m383d("RecoveryPrinter", "step: " + GetCurrentStep());
            switch (C04101.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_RECOVERY_PRINTER, null);
                    StopForNextCommand();
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
                                DecideNextStep(SettingStep.Step_RecoveryPrinterRequest);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Error_Recovery_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    } else {
                        DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_RecoveryPrinterResponse);
                        break;
                    }
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            } else {
                                DecideNextStep(SettingStep.Step_Complete);
                                break;
                            }
                        }
                    }
                    DecideErrorStatus();
                    break;
            }
            if (IsConnectError()) {
                if (this.m_boSilent) {
                    SendMessage(RequestState.REQUEST_RECOVERY_PRINTER_ERROR, null);
                    Stop();
                    return;
                }
                StopTimerOut();
                String strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_RECOVERY_PRINTER_ERROR, strMSG);
                Stop();
            } else if (!IsTimeoutError()) {
            } else {
                if (this.m_boSilent) {
                    SendMessage(RequestState.REQUEST_RECOVERY_PRINTER_ERROR, null);
                    Stop();
                    return;
                }
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }
}
