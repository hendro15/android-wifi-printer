package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import com.example.sonic.hitiprinter.utility.PringoConvenientConst;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.Util;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class HitiPPR_GetImageData extends HitiPPR_PrinterCommandNew {
    private static int BUFFER_READ_LEN;
    private static int LIMIT_DATA_TRANS;
    int m_AttrLenOfDiskName;
    int m_AttrLenOfObjName;
    byte[] m_PhotoID;
    byte[] m_StorageID;
    String m_ThumbnailPath;
    int m_cnt;
    int m_iReadSum;
    int m_intPhotoSize;
    int m_sizeOfImage;
    String m_strDiskName;
    String m_strImagePath;
    String m_strPhotoName;

    /* renamed from: com.hiti.printerprotocol.request.HitiPPR_GetImageData.1 */
    static /* synthetic */ class C04001 {
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
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoRequest.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoResponse.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetStorageInfoResponseSuccess.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageRequest.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponse.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$printerprotocol$SettingStep[SettingStep.Step_GetImageResponseSuccess.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    static {
        LIMIT_DATA_TRANS = 30000;
        BUFFER_READ_LEN = Util.DEFAULT_COPY_BUFFER_SIZE;
    }

    public HitiPPR_GetImageData(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
        this.m_PhotoID = null;
        this.m_StorageID = null;
        this.m_ThumbnailPath = null;
        this.m_strImagePath = null;
        this.m_strPhotoName = null;
        this.m_intPhotoSize = -1;
        this.m_AttrLenOfObjName = -1;
        this.m_AttrLenOfDiskName = -1;
        this.m_strDiskName = null;
        this.m_cnt = 0;
        this.m_sizeOfImage = 0;
        this.m_iReadSum = 0;
        Log.e("HitiPPR_GetImageData", "constructor");
    }

    public void PutIDs(byte[] photoID, byte[] storageID) {
        this.m_PhotoID = photoID;
        this.m_StorageID = storageID;
    }

    public int GetSizeOfImage() {
        return this.m_sizeOfImage;
    }

    public String GetImagePath() {
        return this.m_strImagePath;
    }

    public void StartRequest() {
        GetImageData();
    }

    private void GetImageData() {
        FileNotFoundException e;
        FileOutputStream fileOutputStream;
        IOException e2;
        String strMSG;
        if (IsRunning()) {
            this.LOG.m385i("GetImage_Step", String.valueOf(GetCurrentStep()));
            int i;
            switch (C04001.$SwitchMap$com$hiti$printerprotocol$SettingStep[GetCurrentStep().ordinal()]) {
                case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                    SendMessage(RequestState.REQUEST_GET_IMAGE_DATA, null);
                    Stop();
                    break;
                case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                    if (!Authentication_Request()) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_AuthenticationResponse);
                    break;
                case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStep(SettingStep.Step_GetStorageInfoRequest);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                    if (!Get_Storage_Info_Request(this.m_StorageID)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetStorageInfoResponse);
                    break;
                case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                String msg = XmlPullParser.NO_NAMESPACE;
                                if (this.m_lpReadData[2] == 70) {
                                    if (this.m_lpReadData[3] == 1) {
                                        msg = this.m_Context.getString(this.R_STRING_ERROR_FIND_NO_STORAGEID);
                                    }
                                }
                                if (this.m_lpReadData[2] == 64) {
                                    if (this.m_lpReadData[3] == 4) {
                                        msg = this.m_Context.getString(this.R_STRING_ERROR_STORAGE_ACCESS_DENIED);
                                    }
                                }
                                if (!msg.isEmpty()) {
                                    SetErrorMSG(msg);
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetStorageInfoResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        if (IsReadComplete()) {
                            i = this.m_AttrLenOfDiskName;
                            if (i != -1) {
                                if (this.m_strDiskName == null) {
                                    this.m_AttrLenOfDiskName = -1;
                                    DecideNextStep(SettingStep.Step_GetImageRequest);
                                    break;
                                }
                            }
                            this.m_AttrLenOfDiskName = this.m_lpReadData[6];
                            this.m_strDiskName = null;
                            DecideNextStepAndPrepareReadBuff(this.m_AttrLenOfDiskName, 0, null);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case EchoUDPClient.DEFAULT_PORT /*7*/:
                    if (!Get_Object_Request(this.m_StorageID, this.m_PhotoID)) {
                        DecideNextStep(SettingStep.Step_Error);
                        break;
                    }
                    DecideNextStepAndPrepareReadBuff(7, 0, SettingStep.Step_GetImageResponse);
                    break;
                case ConnectionResult.INTERNAL_ERROR /*8*/:
                    if (ReadResponse(DNSConstants.RECORD_REAPER_INTERVAL)) {
                        if (IsReadComplete()) {
                            if (!CheckCommandSuccess()) {
                                if (this.m_lpReadData[2] == 70) {
                                    if (this.m_lpReadData[3] == 4) {
                                        SetErrorMSG(this.m_Context.getString(this.R_STRING_ERROR_NOTHUMBNAIL));
                                    }
                                }
                                DecideNextStep(SettingStep.Step_Error);
                                break;
                            }
                            DecideNextStepAndPrepareReadBuff(4, 0, SettingStep.Step_GetImageResponseSuccess);
                            break;
                        }
                    }
                    DecideErrorStatus();
                    break;
                case ConnectionResult.SERVICE_INVALID /*9*/:
                    this.m_lpReadData = new byte[BUFFER_READ_LEN];
                    if (!ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                        DecideErrorStatus();
                    } else if (IsReadComplete()) {
                        this.m_sizeOfImage = ByteConvertUtility.ByteToInt(this.m_lpReadData, 0, 4);
                        String folderPath = this.m_Context.getExternalFilesDir(null).getAbsolutePath() + "/" + PringoConvenientConst.PRINGO_TEMP_FOLDER + "/" + PringoConvenientConst.PRINBIZ_FETCH_IMG_FOLDER;
                        String photoName = PringoConvenientConst.PRINBIZ_IMG_PRE_NAME + MobileInfo.GetTimeStamp() + PringoConvenientConst.PRINBIZ_PHOTO_EXT_JPG;
                        FileUtility.CreateFolder(folderPath);
                        this.m_strImagePath = folderPath + "/" + photoName;
                        try {
                            FileOutputStream fos = new FileOutputStream(this.m_strImagePath, false);
                            int iReadSum = 0;
                            boolean z = true;
                            while (true) {
                                if (!z) {
                                    try {
                                        if (IsReadComplete()) {
                                            fos.flush();
                                            fos.close();
                                            if (!z) {
                                                DecideNextStep(SettingStep.Step_Complete);
                                            }
                                        }
                                    } catch (FileNotFoundException e3) {
                                        e = e3;
                                        fileOutputStream = fos;
                                    } catch (IOException e4) {
                                        e2 = e4;
                                        fileOutputStream = fos;
                                    }
                                }
                                if (IsRunning()) {
                                    int iStart = 0;
                                    if (IsReadComplete()) {
                                        DecideNextStepAndPrepareReadBuff(BUFFER_READ_LEN, 0, null);
                                        iReadSum += BUFFER_READ_LEN;
                                        i = this.m_sizeOfImage;
                                        if (iReadSum >= i) {
                                            iReadSum -= BUFFER_READ_LEN;
                                            int iNeedRead = this.m_sizeOfImage - iReadSum;
                                            DecideNextStepAndPrepareReadBuff(iNeedRead, 0, null);
                                            z = false;
                                            iReadSum += iNeedRead;
                                        }
                                        iStart = 0;
                                    } else {
                                        this.m_cnt++;
                                        if (this.m_cnt > LIMIT_DATA_TRANS) {
                                            SetErrorMSG(this.m_Context.getString(this.R_STRING_CREATE_BITMAP_FILE_FORMAT));
                                            DecideNextStep(SettingStep.Step_Error);
                                        } else {
                                            iStart = GetOffsetRead();
                                        }
                                    }
                                    if (ReadResponse(TFTP.DEFAULT_TIMEOUT)) {
                                        int readLen = GetOffsetRead() - iStart;
                                        fos.write(this.m_lpReadData, iStart, readLen);
                                        this.m_iReadSum += readLen;
                                        SendMessage(RequestState.REQUEST_RATIO_OF_FETCH_IMG, String.valueOf(((int) (((float) (this.m_iReadSum * 100)) / ((float) this.m_sizeOfImage))) + "%"));
                                    } else if (IsRunning()) {
                                        DecideErrorStatus();
                                    }
                                }
                                fos.flush();
                                fos.close();
                                if (z) {
                                    DecideNextStep(SettingStep.Step_Complete);
                                }
                            }
                        } catch (FileNotFoundException e5) {
                            e = e5;
                            e.printStackTrace();
                            DecideNextStep(SettingStep.Step_Error);
                            if (!IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                SendMessage(RequestState.REQUEST_GET_IMAGE_DATA_ERROR, strMSG);
                                Stop();
                            } else if (!IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        } catch (IOException e6) {
                            e2 = e6;
                            e2.printStackTrace();
                            DecideNextStep(SettingStep.Step_Error);
                            if (!IsConnectError()) {
                                StopTimerOut();
                                strMSG = GetErrorMSGConnectFail();
                                if (this.m_ErrorString != null) {
                                    strMSG = this.m_ErrorString;
                                }
                                SendMessage(RequestState.REQUEST_GET_IMAGE_DATA_ERROR, strMSG);
                                Stop();
                            } else if (!IsTimeoutError()) {
                                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                                Stop();
                            }
                        }
                    }
            }
            if (!IsConnectError()) {
                StopTimerOut();
                strMSG = GetErrorMSGConnectFail();
                if (this.m_ErrorString != null) {
                    strMSG = this.m_ErrorString;
                }
                SendMessage(RequestState.REQUEST_GET_IMAGE_DATA_ERROR, strMSG);
                Stop();
            } else if (!IsTimeoutError()) {
                SendMessage(RequestState.REQUEST_TIMEOUT_ERROR, GetErrorMSGTimeOut());
                Stop();
            }
        }
    }
}
