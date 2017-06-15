package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_CheckPrinterTotalPrintedCount;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_GetPSC;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_GetPrinterInfo;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_GetUnCleanNumber;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PaperJamRun;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommand;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_RecoveryPrinter;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_SendPhotoPrinbiz;
import com.example.sonic.hitiprinter.printerprotocol.request.SendPhotoListener;
import com.example.sonic.hitiprinter.service.upload.UploadUtility;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.sql.printerInfo.PrintingInfo;
import com.example.sonic.hitiprinter.sql.printerInfo.PrintingInfoUtility;
import com.example.sonic.hitiprinter.trace.GlobalVariable_TotalPrintedRecord;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UploadInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_UserInfo;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.example.sonic.hitiprinter.utility.EncryptAndDecryptAES;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MediaUtil;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.utility.resource.ResourceId;
import com.example.sonic.hitiprinter.utility.resource.ResourceId.Page;
import com.example.sonic.hitiprinter.utility.setting.DateInfo;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.net.ftp.FTPClient;
import org.xmlpull.v1.XmlPullParser;

public abstract class MobileUtility {
    private static final int LOW_QTY_CROP = 3;
    private static final int SCALE_ADD = 2;
    private static final int SIZE_REDUCE = 1;
    private String IP;
    LogManager LOG;
    private float MAX_HEIGHT;
    private float MAX_WIDTH;
    private int OUTPUT_HEIGHT;
    private int OUTPUT_WIDTH;
    String TAG;
    HitiPPR_CheckPrinterTotalPrintedCount mCheckPrinterTotalPrintedCount;
    SendPhotoInfo mPrintinfo;
    ResourceId mRID;
    private Context m_Context;
    private GlobalVariable_TotalPrintedRecord m_GVTotalPrintedRecord;
    private GlobalVariable_UploadInfo m_GVUploadInfo;
    private GlobalVariable_UserInfo m_GVUserInfo;
    private HitiPPR_GetPSC m_GetPrintAndJobSatus;
    HitiPPR_GetUnCleanNumber m_GetUnCleanNuber;
    private HitiPPR_GetPrinterInfo m_HitiPPR_GetPrinterInfo;
    private HitiPPR_RecoveryPrinter m_HitiPPR_RecoveryPrinter;
    private Socket m_LastSocket;
    private MobileHandler m_MobileHandler;
    private IMobile m_OnMobileListener;
    HitiPPR_PaperJamRun m_PaperJamRun;
    HitiPPR_PrinterCommand m_PrintCommand;
    private PrintMode m_PrintMode;
    private HitiPPR_SendPhotoPrinbiz m_PrintSendPhoto;
    private PrintingInfoUtility m_SQLiteUtility;
    private boolean m_bCancelState;
    private boolean m_bCloudBackup;
    private boolean m_bDuplex;
    private boolean m_bJobIdAdded;
    private boolean m_bNeedUnSharpen;
    private boolean m_bSelTexture;
    private byte m_bSharepen;
    private boolean m_bSkipState;
    private boolean m_bStop;
    private byte m_byMediaSize;
    private byte m_byPrintQTY;
    private byte m_byPrintout;
    private int m_iLastPrintCopies;
    private int m_iPaperType;
    private int m_iPathRoute;
    private ArrayList<Integer> m_iPhotoCopiesList;
    private int m_iPort;
    private int m_iPrintCount;
    private ArrayList<Integer> m_iPrintedList;
    private ArrayList<Integer> m_iQueueJobIdList;
    private int m_iSQLiteID;
    private int m_iSendedNum;
    private JumpPreferenceKey m_pref;
    private PrepareSendData m_prepareSendData;
    private String m_strChooseMediaSize;
    private ArrayList<DateInfo> m_strDateInfoList;
    private String m_strModel;
    private ArrayList<String> m_strPhotoPathList;
    private String m_strProductIDString;
    private String m_strProductName;
    private boolean mbAskedLowQty;
    private boolean mbAskedScale;
    private boolean mbAskedSize;

    class MobileHandler extends MSGHandler {
        MobileHandler() {
        }

        public void handleMessage(Message msg) {
            if (!IsStop()) {
                Socket m_Socket;
                String strMSG;
                String strErr;
                int iLastPrintedCopies;
                String strTotalFrame;
                String strHint;
                switch (msg.what) {
                    case RequestState.REQUEST_RECOVERY_PRINTER /*305*/:
                        m_Socket = MobileUtility.this.m_HitiPPR_RecoveryPrinter.GetSocket();
                        if (MobileUtility.this.m_bJobIdAdded) {
                            MobileUtility.this.m_bJobIdAdded = false;
                            MobileUtility.this.m_iSendedNum = MobileUtility.this.m_iPrintedList.size();
                            if (MobileUtility.this.HaveMobileListener()) {
                                MobileUtility.this.m_OnMobileListener.SendingPhotoDone(m_Socket, MobileUtility.this.m_iSendedNum);
                            }
                        }
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.RecoveryDone(m_Socket);
                        }
                    case RequestState.REQUEST_RECOVERY_PRINTER_ERROR /*306*/:
                    case RequestState.REQUEST_PAPER_JAM_RUN_ERROR /*394*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccur(strMSG);
                        }
                    case RequestState.REQUEST_SEND_PHOTO /*307*/:
                        int iJobId = MobileUtility.this.m_PrintSendPhoto.GetJobId();
                        MobileUtility.this.m_LastSocket = MobileUtility.this.m_PrintSendPhoto.GetSocket();
                        MobileUtility.this.m_iQueueJobIdList.add(Integer.valueOf(iJobId));
                        if (MobileUtility.this.m_iPrintedList.contains(Integer.valueOf(0))) {
                            MobileUtility.this.m_iPrintedList.remove(MobileUtility.this.m_iPrintedList.indexOf(Integer.valueOf(0)));
                        }
                        MobileUtility.this.LOG.m385i("(4)Send Photo done Queue size", String.valueOf(MobileUtility.this.m_iQueueJobIdList.size()));
                        MobileUtility.this.LOG.m385i("(4)Send Photo done Printed size", String.valueOf(MobileUtility.this.m_iPrintedList.size()));
                        int iNum = MobileUtility.this.m_iQueueJobIdList.size();
                        boolean bRet = MobileUtility.this.m_strModel.equals(WirelessType.TYPE_P530D);
                        if (bRet) {
                            iNum *= MobileUtility.this.mPrintinfo.GetNumber();
                        }
                        MobileUtility.this.m_iSendedNum = ((bRet ? MobileUtility.this.mPrintinfo.GetNumber() : MobileUtility.SIZE_REDUCE) * MobileUtility.this.m_iPrintedList.size()) + iNum;
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.SendingPhotoDone(MobileUtility.this.m_LastSocket, MobileUtility.this.m_iSendedNum);
                        }
                        MobileUtility.this.m_bJobIdAdded = true;
                        if (MobileUtility.this.m_bCloudBackup) {
                            MobileUtility.this.CloudBack();
                        }
                        MobileUtility.this.GetPrinterStatus(false, MobileUtility.this.m_LastSocket);
                    case RequestState.REQUEST_SEND_PHOTO_ERROR /*308*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        m_Socket = MobileUtility.this.m_PrintSendPhoto.GetSocket();
                        MobileUtility.this.LOG.m385i("(4)Send Photo Socket Error", String.valueOf(strErr));
                        MobileUtility.this.GetPrinterStatus(false, m_Socket);
                    case RequestState.REQUEST_SEND_PHOTO_ERROR_DUETO_PRINTER /*309*/:
                        strErr = msg.getData().getString(MSGHandler.MSG) + "\n" + MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_ERROR_PRINTER_CHECKED);
                        iLastPrintedCopies = 0;
                        if (MobileUtility.this.m_GetPrintAndJobSatus != null) {
                            iLastPrintedCopies = MobileUtility.this.m_GetPrintAndJobSatus.GetLastCopies();
                        }
                        if (iLastPrintedCopies > 0) {
                            MobileUtility.this.m_iLastPrintCopies = iLastPrintedCopies;
                        } else {
                            MobileUtility.this.m_iLastPrintCopies = 0;
                        }
                        MobileUtility.this.m_iQueueJobIdList.clear();
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccurDueToPrinter(strErr);
                        }
                    case RequestState.REQUEST_GET_PRINTER_INFO /*310*/:
                        When_REQUEST_GET_PRINTER_INFO();
                    case RequestState.REQUEST_GET_PRINTER_INFO_ERROR /*311*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccur(strErr);
                        }
                    case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorTimeOut(strMSG);
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META /*342*/:
                        if (MobileUtility.this.mCheckPrinterTotalPrintedCount != null) {
                            Integer iFrame = MobileUtility.this.mCheckPrinterTotalPrintedCount.GetTotalPrintedFrame(MobileUtility.this.m_strChooseMediaSize);
                            MobileUtility.this.m_iPrintCount = ((Integer) MobileUtility.this.m_iPhotoCopiesList.get(0)).intValue();
                            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT m_strChooseMediaSize: " + MobileUtility.this.m_strChooseMediaSize);
                            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT frame: " + iFrame);
                            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "REQUEST_CHECK_TOTAL_PRINTED_COUNT frameMap: " + MobileUtility.this.mCheckPrinterTotalPrintedCount.GetTotalPrintedFrame());
                            if (iFrame != null) {
                                MobileUtility.this.NewRecordPrint(iFrame.intValue());
                            }
                            MobileUtility.this.mCheckPrinterTotalPrintedCount.Stop();
                            MobileUtility.this.PrintSendPhoto(null);
                        }
                    case RequestState.REQUEST_GET_ALBUM_ID_META_ERROR /*343*/:
                    case RequestState.REQUEST_GET_PRINT_STAUTS_ERROR /*359*/:
                        strErr = msg.getData().getString(MSGHandler.MSG);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccur(strErr);
                        }
                    case RequestState.REQUEST_QUICK_PRINT_NEXT /*356*/:
                        strTotalFrame = msg.getData().getString(MSGHandler.MSG);
                        MobileUtility.this.m_bJobIdAdded = false;
                        MobileUtility.this.SetTotoalPrintedRecord();
                        When_Print_Next(strTotalFrame, MobileUtility.this.m_GetPrintAndJobSatus);
                    case RequestState.REQUEST_GET_PRINT_STAUTS /*358*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        MobileUtility.this.m_bJobIdAdded = false;
                        When_PRINTR_IDLE(strMSG, MobileUtility.this.m_GetPrintAndJobSatus);
                    case RequestState.REQUEST_CREATE_BITMAP_SCALE_HINT /*360*/:
                        MobileUtility.this.LOG.m386v(MobileUtility.this.TAG, "REQUEST_CREATE_BITMAP_SCALE_HINT");
                        strHint = MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_SCALE_TO_LARGE);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.PhotoLawQty(strHint, MobileUtility.SCALE_ADD, MobileUtility.this.m_PrintCommand);
                        }
                    case RequestState.REQUEST_CREATE_BITMAP_SIZE_CHANGE /*361*/:
                        MobileUtility.this.LOG.m386v(MobileUtility.this.TAG, "REQUEST_CREATE_BITMAP_SIZE_CHANGE");
                        strHint = MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_SIZE_TO_SMALL);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.PhotoLawQty(strHint, MobileUtility.SIZE_REDUCE, MobileUtility.this.m_PrintCommand);
                        }
                    case RequestState.REQUEST_CREATE_BITMAP_LOW_QTY /*362*/:
                        MobileUtility.this.LOG.m386v(MobileUtility.this.TAG, "REQUEST_CREATE_BITMAP_LOW_QTY");
                        strHint = MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_QTY_TO_LARGE);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.PhotoLawQty(strHint, MobileUtility.LOW_QTY_CROP, MobileUtility.this.m_PrintCommand);
                        }
                    case RequestState.REQUEST_CHECK_COPIES /*363*/:
                        strTotalFrame = msg.getData().getString(MSGHandler.MSG);
                        iLastPrintedCopies = MobileUtility.this.m_GetPrintAndJobSatus.GetLastCopies();
                        int iTotalFrame = strTotalFrame.equals(null) ? -1 : Integer.parseInt(strTotalFrame);
                        MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "REQUEST_CHECK_COPIES iTotalFrame: " + iTotalFrame);
                        iLastPrintedCopies += MobileUtility.this.m_iLastPrintCopies;
                        if (iLastPrintedCopies > 90) {
                            iLastPrintedCopies = MobileUtility.SIZE_REDUCE;
                        }
                        When_Copies_Plus(iLastPrintedCopies, iTotalFrame);
                    case RequestState.REQUEST_PRINTER_BUSY_OR_PRINTING /*372*/:
                        strMSG = msg.getData().getString(MSGHandler.MSG);
                        MobileUtility.this.m_bJobIdAdded = false;
                        if (MobileUtility.this.m_bCancelState) {
                            When_Cancel_Print();
                        } else {
                            When_Printer_Is_Busy(strMSG, MobileUtility.this.m_GetPrintAndJobSatus);
                        }
                    case RequestState.REQUEST_ERROR_0001 /*380*/:
                        MobileUtility.this.m_bJobIdAdded = false;
                        m_Socket = MobileUtility.this.m_GetPrintAndJobSatus.GetSocket();
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccur(MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_ERROR_PRINTER_0001));
                        }
                        MobileUtility.this.GetPrinterStatus(false, m_Socket);
                    case RequestState.REQUEST_RE_CHECK_IF_BUSY /*383*/:
                        MobileUtility.this.GetPrinterStatus(false, MobileUtility.this.GetSocket());
                    case RequestState.REQUEST_SNAP_CROP_DONE /*386*/:
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.SendingPhoto(MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_DATA_DELIVERY));
                        }
                        MobileUtility.this.GetPrinterStatus(false, MobileUtility.this.GetSocket());
                    case RequestState.REQUEST_PAPER_NOT_MATCH /*387*/:
                        When_SIZE_NOT_MATCH(msg.getData().getString(MSGHandler.MSG));
                    case RequestState.REQUEST_GET_UN_CLEAN_NUMBER /*388*/:
                    case RequestState.REQUEST_GET_UN_CLEAN_NUMBER_ERROR /*389*/:
                        String strCleanNumber = msg.getData().getString(MSGHandler.MSG);
                        MobileUtility.this.LOG.m385i(MobileUtility.this.TAG, "REQUEST_GET_UN_CLEAN_NUMBER: " + strCleanNumber);
                        if (strCleanNumber.contains("error")) {
                            strCleanNumber = OADCItem.WATCH_TYPE_NON;
                        }
                        int iCopies = MobileUtility.this.MobileSumOfCopies(0);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.PrintDone(MobileUtility.this.GetSocket(), iCopies, MobileUtility.this.m_iPrintedList.size(), Integer.parseInt(strCleanNumber));
                        }
                    case RequestState.REQUEST_PAPER_JAM_RUN /*393*/:
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.PaperJamDone();
                        }
                    case RequestState.REQUEST_PAPER_JAM_RUN_ERROR_DUETO_PRINTER /*395*/:
                        String strError = msg.getData().getString(MSGHandler.MSG);
                        if (MobileUtility.this.HaveMobileListener()) {
                            MobileUtility.this.m_OnMobileListener.ErrorOccurDueToPrinter(strError);
                        }
                    case RequestState.REQUEST_GPS_TOTAL_PRINTED_COUNT /*396*/:
                        MobileUtility.this.SetTotoalPrintedRecord();
                    default:
                }
            }
        }

        private void When_REQUEST_GET_PRINTER_INFO() {
            String strGetVersion = MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrFirmwareVersionString().replace(".", XmlPullParser.NO_NAMESPACE).substring(0, MobileUtility.LOW_QTY_CROP);
            MobileUtility.this.m_strProductIDString = MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrProductIDString();
            MobileUtility.this.m_strProductName = MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetProductName();
            if (MobileUtility.this.m_strProductIDString != null) {
                MobileUtility.this.m_GVUploadInfo.RestoreGlobalVariable();
                MobileUtility.this.m_GVUploadInfo.SaveGlobalVariableForever();
                String strPrinterPID = MobileUtility.this.m_strProductName == null ? MobileUtility.this.m_strProductIDString : MobileUtility.this.m_strProductName.contains("P525") ? WirelessType.TYPE_P525L : MobileUtility.this.m_strProductIDString;
                MobileUtility.this.LOG.m385i(MobileUtility.this.TAG, "When_REQUEST_GET_PRINTER_INFO PID: " + strPrinterPID);
                MobileUtility.this.m_GVTotalPrintedRecord = new GlobalVariable_TotalPrintedRecord(MobileUtility.this.m_Context, strPrinterPID);
                MobileUtility.this.LOG.m385i("APP choose m_strModel", "=" + String.valueOf(MobileUtility.this.m_strModel));
                if (!MobileUtility.this.m_strModel.equals(MobileUtility.this.m_strProductIDString)) {
                    String strErr = MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_ERROR_MODEL);
                    if (MobileUtility.this.HaveMobileListener()) {
                        MobileUtility.this.m_OnMobileListener.ErrorOccur(strErr);
                        return;
                    }
                    return;
                } else if (MobileUtility.this.HaveMobileListener()) {
                    MobileUtility.this.m_OnMobileListener.EndCheckPrintInfo(strGetVersion, MobileUtility.this.m_strProductIDString);
                }
            }
            String strUpdateVersion = ((String) BurnFWUtility.GetTheNewestFWVersion(MobileUtility.this.m_Context, MobileUtility.this.m_strProductIDString, true).second).substring(0, MobileUtility.LOW_QTY_CROP);
            String strSerialNumber = MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetAttrSerialNumber();
            MobileUtility.this.LOG.m385i("strSerialNumber", "=" + String.valueOf(strSerialNumber));
            MobileUtility.this.LOG.m385i("strUpdateVersion", "=" + String.valueOf(strUpdateVersion));
            if (strSerialNumber != null) {
                MobileUtility.this.m_GVUploadInfo.RestoreGlobalVariable();
                MobileUtility.this.m_GVUploadInfo.SetSerialNumber(strSerialNumber);
                MobileUtility.this.m_GVUploadInfo.SaveGlobalVariableForever();
                MobileUtility.this.m_GVTotalPrintedRecord.RestoreGlobalVariable();
                MobileUtility.this.m_GVTotalPrintedRecord.SetSerialNumber(strSerialNumber);
                MobileUtility.this.m_GVTotalPrintedRecord.SaveGlobalVariable();
            }
            if (Integer.parseInt(strGetVersion) >= Integer.parseInt(strUpdateVersion)) {
                MobileUtility.this.LOG.m385i("GetInfo done", String.valueOf(MobileUtility.this.m_strPhotoPathList));
                if (!MobileUtility.this.m_strPhotoPathList.isEmpty()) {
                    MobileUtility.this.GetPrinterStatus(true, MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetSocket());
                } else if (MobileUtility.this.HaveMobileListener()) {
                    MobileUtility.this.m_OnMobileListener.ErrorOccur(MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_NOPHOTO));
                }
            } else if (MobileUtility.this.HaveMobileListener()) {
                MobileUtility.this.m_OnMobileListener.StartBurnFW(MobileUtility.this.m_HitiPPR_GetPrinterInfo.GetSocket());
            }
        }

        private void When_Copies_Plus(int iLastPrintedCopies, int iTotalFrame) {
            MobileUtility.this.LOG.m383d("When_Copies_Plus frame", String.valueOf(iTotalFrame));
            if (iLastPrintedCopies > 0) {
                int iSumCp = MobileUtility.this.MobileSumOfCopies(iLastPrintedCopies);
                MobileUtility.this.m_iPrintCount = ((Integer) MobileUtility.this.m_iPhotoCopiesList.get(0)).intValue();
                MobileUtility.this.SavePrintCount(iTotalFrame);
                if (MobileUtility.this.HaveMobileListener()) {
                    MobileUtility.this.m_OnMobileListener.ChangeCopies("plus", iSumCp);
                }
            }
        }

        private boolean When_PRINTR_IDLE(String strMSG, HitiPPR_GetPSC mPrintCommand) {
            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "When_PRINTR_IDLE: " + strMSG);
            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "When_PRINTR_IDLE: " + MobileUtility.this.m_strPhotoPathList);
            if (!strMSG.equals(MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_PRINT_DONE))) {
                return false;
            }
            if (MobileUtility.this.m_bCancelState) {
                When_Cancel_Print();
            } else if (!MobileUtility.this.m_iQueueJobIdList.isEmpty()) {
                MobileUtility.this.GetPrinterStatus(false, mPrintCommand.GetSocket());
            } else if (MobileUtility.this.m_strPhotoPathList.isEmpty()) {
                MobileUtility.this.GetUnCleanNumber(mPrintCommand.GetSocket());
            } else {
                MobileUtility.this.GetFirstPrintFrame(mPrintCommand, mPrintCommand.GetSocket());
            }
            return true;
        }

        private void When_SIZE_NOT_MATCH(String strSetMediaSize) {
            MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "When_SIZE_NOT_MATCH strSetMediaSize: " + strSetMediaSize);
            String strPrinterMediaSize = MobileUtility.this.m_GetPrintAndJobSatus.GetPrinterMediaSize();
            String[] strNoMatch = MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_SIZE_NOT_MATCH).split(":");
            String strMSG = strNoMatch[0] + " (" + strSetMediaSize + ")" + strNoMatch[MobileUtility.SIZE_REDUCE] + " (" + strPrinterMediaSize + ")";
            if (strNoMatch.length == MobileUtility.LOW_QTY_CROP) {
                strMSG = strMSG + strNoMatch[MobileUtility.SCALE_ADD];
            }
            if (MobileUtility.this.HaveMobileListener()) {
                MobileUtility.this.m_OnMobileListener.MediaSizeNotMatch(strMSG);
            }
        }

        private void When_Printer_Is_Busy(String strMSG, HitiPPR_GetPSC mPrintCommand) {
            if (!MobileUtility.this.m_bStop) {
                MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "When_Printer_Is_Busy: " + strMSG);
                byte byJobState = mPrintCommand.GetNowJobIdState();
                if (MobileUtility.this.HaveMobileListener()) {
                    MobileUtility.this.m_OnMobileListener.IsPrinterBusy(strMSG);
                }
                MobileUtility.this.SetSocket(mPrintCommand.GetSocket());
                if (byJobState == -128) {
                    if (MobileUtility.this.m_iQueueJobIdList.size() < MobileUtility.SCALE_ADD) {
                        MobileUtility.this.PrintSendPhoto(mPrintCommand);
                    } else {
                        MobileUtility.this.m_MobileHandler.sendEmptyMessageDelayed(RequestState.REQUEST_RE_CHECK_IF_BUSY, 2000);
                    }
                } else if (!MobileUtility.this.m_iQueueJobIdList.isEmpty()) {
                    MobileUtility.this.m_MobileHandler.sendEmptyMessageDelayed(RequestState.REQUEST_RE_CHECK_IF_BUSY, 2000);
                } else if (MobileUtility.this.m_strPhotoPathList.isEmpty()) {
                    MobileUtility.this.GetUnCleanNumber(mPrintCommand.GetSocket());
                } else {
                    MobileUtility.this.m_MobileHandler.sendEmptyMessageDelayed(RequestState.REQUEST_RE_CHECK_IF_BUSY, 2000);
                }
            }
        }

        private void When_Print_Next(String strFrameCount, HitiPPR_GetPSC mPrintCommand) {
            if (!MobileUtility.this.m_bStop) {
                int iTotalFrame = strFrameCount.equals(null) ? -1 : Integer.parseInt(strFrameCount);
                MobileUtility.this.LOG.m386v("When_Print_Next: frame: ", String.valueOf(iTotalFrame));
                if (MobileUtility.this.HaveMobileListener()) {
                    MobileUtility.this.LOG.m383d(MobileUtility.this.TAG, "When_Print_Next m_iPhotoCopiesList " + MobileUtility.this.m_iPhotoCopiesList);
                    if (MobileUtility.this.m_strModel.equals(WirelessType.TYPE_P530D)) {
                        if (!MobileUtility.this.mPrintinfo.IsEmpty()) {
                            MobileUtility.this.m_iPrintedList.add(Integer.valueOf(MobileUtility.this.mPrintinfo.GetNumber()));
                            MobileUtility.this.mPrintinfo.RemoveBuffer();
                            for (int i = 0; i < MobileUtility.this.mPrintinfo.GetNumber(); i += MobileUtility.SIZE_REDUCE) {
                                if (MobileUtility.this.m_iPhotoCopiesList.size() > 0) {
                                    MobileUtility.this.m_iPhotoCopiesList.remove(0);
                                }
                            }
                            if (MobileUtility.this.m_iQueueJobIdList.size() > 0) {
                                MobileUtility.this.m_iQueueJobIdList.remove(0);
                            }
                        }
                    } else if (MobileUtility.this.m_strPhotoPathList.size() > 0) {
                        MobileUtility.this.m_iPrintedList.add(Integer.valueOf(((Integer) MobileUtility.this.m_iPhotoCopiesList.get(0)).intValue()));
                        MobileUtility.this.RemoveDataList();
                    }
                    int iCopies = MobileUtility.this.MobileSumOfCopies(0);
                    MobileUtility.this.SavePrintCount(iTotalFrame);
                    if (MobileUtility.this.HaveMobileListener()) {
                        MobileUtility.this.m_OnMobileListener.ChangeCopies("next", iCopies);
                    }
                    if (!MobileUtility.this.m_strPhotoPathList.isEmpty()) {
                        if (MobileUtility.this.m_strModel.equals(WirelessType.TYPE_P530D)) {
                            MobileUtility.this.m_iPrintCount = MobileUtility.SIZE_REDUCE;
                        } else {
                            MobileUtility.this.m_iPrintCount = ((Integer) MobileUtility.this.m_iPhotoCopiesList.get(0)).intValue();
                        }
                        MobileUtility.this.NewRecordPrint(iTotalFrame);
                    }
                    if (MobileUtility.this.m_bCancelState) {
                        When_Cancel_Print();
                    } else if (!MobileUtility.this.m_iQueueJobIdList.isEmpty()) {
                        MobileUtility.this.GetPrinterStatus(false, mPrintCommand.GetSocket());
                    } else if (MobileUtility.this.m_strModel.equals(WirelessType.TYPE_P530D) && MobileUtility.this.mPrintinfo.IsEmpty()) {
                        MobileUtility.this.GetUnCleanNumber(mPrintCommand.GetSocket());
                    } else if (MobileUtility.this.m_strPhotoPathList.isEmpty()) {
                        MobileUtility.this.GetUnCleanNumber(mPrintCommand.GetSocket());
                    } else {
                        MobileUtility.this.PrintSendPhoto(mPrintCommand);
                    }
                }
            }
        }

        private void When_Cancel_Print() {
            MobileUtility.this.m_MobileHandler.SetStop(true);
            MobileUtility.this.m_bCancelState = false;
        }
    }

    class PrePhotoListener implements SendPhotoListener {
        PrePhotoListener() {
        }

        public boolean CheckJobIdIfEmpty() {
            if (MobileUtility.this.m_iQueueJobIdList.isEmpty()) {
                return true;
            }
            return false;
        }

        public void SetAskState(boolean bAskedSize, boolean bAskedScale, boolean bAskedLowQty) {
            MobileUtility.this.mbAskedSize = bAskedSize;
            MobileUtility.this.mbAskedScale = bAskedScale;
            MobileUtility.this.mbAskedLowQty = bAskedLowQty;
        }

        public void SkipToNextPhoto(Socket socket) {
            MobileUtility.this.m_bSkipState = true;
            MobileUtility.this.SkipToPrintNext(socket);
        }

        public int GetCopies() {
            if (MobileUtility.this.m_strModel.equals(WirelessType.TYPE_P530D)) {
                return MobileUtility.SIZE_REDUCE;
            }
            int iCopies;
            if (MobileUtility.this.m_iQueueJobIdList.isEmpty()) {
                iCopies = ((Integer) MobileUtility.this.m_iPhotoCopiesList.get(0)).intValue();
                if (MobileUtility.this.m_iLastPrintCopies > 0) {
                    iCopies -= MobileUtility.this.m_iLastPrintCopies;
                }
            } else {
                iCopies = ((Integer) MobileUtility.this.m_iPhotoCopiesList.get(MobileUtility.this.m_iQueueJobIdList.size())).intValue();
            }
            return iCopies;
        }

        public void SendingPhoto(PrintMode mPrintMode) {
            if (mPrintMode != PrintMode.Snap && MobileUtility.this.HaveMobileListener()) {
                MobileUtility.this.m_OnMobileListener.SendingPhoto(MobileUtility.this.m_Context.getString(MobileUtility.this.mRID.R_STRING_DATA_DELIVERY));
            }
        }

        public void onCreateBitmapError(String strErr) {
            if (MobileUtility.this.HaveMobileListener()) {
                MobileUtility.this.m_OnMobileListener.ErrorBitmap(strErr);
            }
        }

        public void GetHitiPPR_SendPhotoPrinbiz(HitiPPR_SendPhotoPrinbiz sendPhoto) {
            MobileUtility.this.m_PrintSendPhoto = sendPhoto;
        }
    }

    public abstract int[][] SetPrintoutSize(int i);

    public MobileUtility(Context context, ArrayList<String> strPhotoPathList, ArrayList<Integer> iPhotoCopiesList, boolean bNeedUnSharpen) {
        this.m_iPrintCount = SIZE_REDUCE;
        this.m_iLastPrintCopies = 0;
        this.m_Context = null;
        this.m_strProductIDString = null;
        this.m_strProductName = null;
        this.m_MobileHandler = null;
        this.m_OnMobileListener = null;
        this.m_strChooseMediaSize = null;
        this.m_bSelTexture = false;
        this.m_bDuplex = false;
        this.m_bCancelState = false;
        this.m_byMediaSize = (byte) 1;
        this.m_byPrintout = (byte) 1;
        this.m_iPaperType = SIZE_REDUCE;
        this.MAX_WIDTH = 0.0f;
        this.MAX_HEIGHT = 0.0f;
        this.OUTPUT_WIDTH = 0;
        this.OUTPUT_HEIGHT = 0;
        this.mbAskedScale = false;
        this.mbAskedSize = false;
        this.mbAskedLowQty = false;
        this.m_strPhotoPathList = null;
        this.m_iPrintedList = null;
        this.m_iQueueJobIdList = null;
        this.m_iPhotoCopiesList = null;
        this.m_strDateInfoList = null;
        this.m_GVUploadInfo = null;
        this.m_GVUserInfo = null;
        this.m_GVTotalPrintedRecord = null;
        this.m_SQLiteUtility = null;
        this.m_iSQLiteID = -1;
        this.m_byPrintQTY = (byte) 1;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.m_GetPrintAndJobSatus = null;
        this.m_prepareSendData = null;
        this.m_PrintSendPhoto = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.m_bNeedUnSharpen = false;
        this.m_bJobIdAdded = false;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_iSendedNum = 0;
        this.m_bCloudBackup = false;
        this.m_bSharepen = (byte) -120;
        this.m_LastSocket = null;
        this.m_PrintCommand = null;
        this.m_GetUnCleanNuber = null;
        this.m_PaperJamRun = null;
        this.m_iPathRoute = 0;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.m_bStop = false;
        this.m_bSkipState = false;
        this.mPrintinfo = null;
        this.mRID = null;
        this.mCheckPrinterTotalPrintedCount = null;
        this.LOG = null;
        this.TAG = "MobileUtility";
        this.m_Context = context;
        this.m_bNeedUnSharpen = bNeedUnSharpen;
        this.m_strPhotoPathList = strPhotoPathList;
        this.m_iPhotoCopiesList = iPhotoCopiesList;
        this.m_iPrintedList = new ArrayList();
        this.m_iQueueJobIdList = new ArrayList();
        this.m_strDateInfoList = new ArrayList();
        this.m_MobileHandler = new MobileHandler();
        this.m_SQLiteUtility = new PrintingInfoUtility(this.m_Context);
        this.m_GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        this.m_GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
        this.LOG = new LogManager(0);
        this.mRID = new ResourceId(context, Page.MobileUtility);
        GetPref(this.m_Context);
        if (this.m_iPhotoCopiesList.size() < this.m_strPhotoPathList.size()) {
            for (int i = this.m_iPhotoCopiesList.size(); i < this.m_strPhotoPathList.size(); i += SIZE_REDUCE) {
                this.m_iPhotoCopiesList.add(Integer.valueOf(SIZE_REDUCE));
            }
        }
        this.LOG.m383d(this.TAG, "MobileUtility m_strPhotoPathList: " + this.m_strPhotoPathList);
        this.LOG.m383d(this.TAG, "MobileUtility m_iPhotoCopiesList: " + this.m_iPhotoCopiesList);
    }

    public MobileUtility(Context context, boolean bNeedUnSharpen) {
        this.m_iPrintCount = SIZE_REDUCE;
        this.m_iLastPrintCopies = 0;
        this.m_Context = null;
        this.m_strProductIDString = null;
        this.m_strProductName = null;
        this.m_MobileHandler = null;
        this.m_OnMobileListener = null;
        this.m_strChooseMediaSize = null;
        this.m_bSelTexture = false;
        this.m_bDuplex = false;
        this.m_bCancelState = false;
        this.m_byMediaSize = (byte) 1;
        this.m_byPrintout = (byte) 1;
        this.m_iPaperType = SIZE_REDUCE;
        this.MAX_WIDTH = 0.0f;
        this.MAX_HEIGHT = 0.0f;
        this.OUTPUT_WIDTH = 0;
        this.OUTPUT_HEIGHT = 0;
        this.mbAskedScale = false;
        this.mbAskedSize = false;
        this.mbAskedLowQty = false;
        this.m_strPhotoPathList = null;
        this.m_iPrintedList = null;
        this.m_iQueueJobIdList = null;
        this.m_iPhotoCopiesList = null;
        this.m_strDateInfoList = null;
        this.m_GVUploadInfo = null;
        this.m_GVUserInfo = null;
        this.m_GVTotalPrintedRecord = null;
        this.m_SQLiteUtility = null;
        this.m_iSQLiteID = -1;
        this.m_byPrintQTY = (byte) 1;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.m_GetPrintAndJobSatus = null;
        this.m_prepareSendData = null;
        this.m_PrintSendPhoto = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.m_bNeedUnSharpen = false;
        this.m_bJobIdAdded = false;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_iSendedNum = 0;
        this.m_bCloudBackup = false;
        this.m_bSharepen = (byte) -120;
        this.m_LastSocket = null;
        this.m_PrintCommand = null;
        this.m_GetUnCleanNuber = null;
        this.m_PaperJamRun = null;
        this.m_iPathRoute = 0;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.m_bStop = false;
        this.m_bSkipState = false;
        this.mPrintinfo = null;
        this.mRID = null;
        this.mCheckPrinterTotalPrintedCount = null;
        this.LOG = null;
        this.TAG = "MobileUtility";
        this.m_Context = context;
        this.m_bNeedUnSharpen = bNeedUnSharpen;
        this.m_strPhotoPathList = new ArrayList();
        this.m_iPhotoCopiesList = new ArrayList();
        this.m_iPrintedList = new ArrayList();
        this.m_iQueueJobIdList = new ArrayList();
        this.m_strDateInfoList = new ArrayList();
        this.m_MobileHandler = new MobileHandler();
        this.m_SQLiteUtility = new PrintingInfoUtility(this.m_Context);
        this.m_GVUploadInfo = new GlobalVariable_UploadInfo(this.m_Context);
        this.m_GVUserInfo = new GlobalVariable_UserInfo(this.m_Context);
        this.LOG = new LogManager(0);
        this.mRID = new ResourceId(context, Page.MobileUtility);
        GetPref(this.m_Context);
    }

    public MobileUtility(Context context) {
        this.m_iPrintCount = SIZE_REDUCE;
        this.m_iLastPrintCopies = 0;
        this.m_Context = null;
        this.m_strProductIDString = null;
        this.m_strProductName = null;
        this.m_MobileHandler = null;
        this.m_OnMobileListener = null;
        this.m_strChooseMediaSize = null;
        this.m_bSelTexture = false;
        this.m_bDuplex = false;
        this.m_bCancelState = false;
        this.m_byMediaSize = (byte) 1;
        this.m_byPrintout = (byte) 1;
        this.m_iPaperType = SIZE_REDUCE;
        this.MAX_WIDTH = 0.0f;
        this.MAX_HEIGHT = 0.0f;
        this.OUTPUT_WIDTH = 0;
        this.OUTPUT_HEIGHT = 0;
        this.mbAskedScale = false;
        this.mbAskedSize = false;
        this.mbAskedLowQty = false;
        this.m_strPhotoPathList = null;
        this.m_iPrintedList = null;
        this.m_iQueueJobIdList = null;
        this.m_iPhotoCopiesList = null;
        this.m_strDateInfoList = null;
        this.m_GVUploadInfo = null;
        this.m_GVUserInfo = null;
        this.m_GVTotalPrintedRecord = null;
        this.m_SQLiteUtility = null;
        this.m_iSQLiteID = -1;
        this.m_byPrintQTY = (byte) 1;
        this.m_HitiPPR_GetPrinterInfo = null;
        this.m_HitiPPR_RecoveryPrinter = null;
        this.m_GetPrintAndJobSatus = null;
        this.m_prepareSendData = null;
        this.m_PrintSendPhoto = null;
        this.m_pref = null;
        this.m_strModel = null;
        this.m_bNeedUnSharpen = false;
        this.m_bJobIdAdded = false;
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        this.m_iSendedNum = 0;
        this.m_bCloudBackup = false;
        this.m_bSharepen = (byte) -120;
        this.m_LastSocket = null;
        this.m_PrintCommand = null;
        this.m_GetUnCleanNuber = null;
        this.m_PaperJamRun = null;
        this.m_iPathRoute = 0;
        this.m_PrintMode = PrintMode.NormalPrint;
        this.m_bStop = false;
        this.m_bSkipState = false;
        this.mPrintinfo = null;
        this.mRID = null;
        this.mCheckPrinterTotalPrintedCount = null;
        this.LOG = null;
        this.TAG = "MobileUtility";
        this.m_Context = context;
        this.m_MobileHandler = new MobileHandler();
    }

    public void AddPhoto(String strPhotoPath, int iCopies) {
        this.m_strPhotoPathList.add(strPhotoPath);
        this.m_iPhotoCopiesList.add(Integer.valueOf(iCopies));
    }

    public void AddIndexPhotoList(ArrayList<String> indexPhotoPathList) {
        ArrayList<String> pathList = new ArrayList();
        ArrayList<Integer> copiesList = new ArrayList();
        Iterator it = indexPhotoPathList.iterator();
        while (it.hasNext()) {
            pathList.add((String) it.next());
            copiesList.add(Integer.valueOf(SIZE_REDUCE));
        }
        it = this.m_strPhotoPathList.iterator();
        while (it.hasNext()) {
            pathList.add((String) it.next());
        }
        Iterator it2 = this.m_iPhotoCopiesList.iterator();
        while (it2.hasNext()) {
            copiesList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
        }
        this.m_strPhotoPathList.clear();
        this.m_strPhotoPathList = pathList;
        this.m_iPhotoCopiesList.clear();
        this.m_iPhotoCopiesList = copiesList;
    }

    public void SetDateInfo(ArrayList<DateInfo> photoDateList) {
        this.LOG.m386v(this.TAG, "SetDateTime size: " + photoDateList.size());
        if (photoDateList != null) {
            this.m_strDateInfoList.clear();
            Iterator it = photoDateList.iterator();
            while (it.hasNext()) {
                DateInfo date = (DateInfo) it.next();
                if (!TextUtils.isEmpty(date.GetFormat())) {
                    String createTime = MediaUtil.GetPhotoCreatedTime(this.m_Context, date.GetPhotoId(), date.GetPhotoPath(), date.GetFormat());
                    this.LOG.m386v(this.TAG, "SetDateTime id: " + date.GetPhotoId() + " create time: " + createTime);
                    date.SetDate(createTime);
                    this.m_strDateInfoList.add(date);
                }
            }
        }
    }

    public void AddNoEditPhotoList(ArrayList<String> strPhotoPathList, ArrayList<Integer> iCopiesList) {
        this.LOG.m383d(this.TAG, "AddNoEditPhotoList " + strPhotoPathList);
        copiesCheck(strPhotoPathList, iCopiesList);
        this.m_PrintMode = PrintMode.NormalPrint;
    }

    void copiesCheck(ArrayList<String> strPhotoPathList, ArrayList<Integer> iCopiesList) {
        Iterator it = strPhotoPathList.iterator();
        while (it.hasNext()) {
            this.m_strPhotoPathList.add((String) it.next());
        }
        Iterator it2 = iCopiesList.iterator();
        while (it2.hasNext()) {
            this.m_iPhotoCopiesList.add(Integer.valueOf(((Integer) it2.next()).intValue()));
        }
    }

    private void GetPref(Context context) {
        this.m_pref = new JumpPreferenceKey(context);
        this.m_strModel = this.m_pref.GetModelPreference();
        this.LOG.m386v(this.TAG, "GetPref Printer Model: " + this.m_strModel);
    }

    public void SetMobileValue(MobileUnit m_MobileUnit) {
        m_MobileUnit.GetJobIdList(this.m_iPrintedList, this.m_iQueueJobIdList);
        this.m_strProductIDString = m_MobileUnit.GetProductId();
    }

    public void GetPrinterStautus() {
        GetPrinterStatus(false, null);
    }

    public void SetMobileListener(IMobile mobileListener) {
        this.m_OnMobileListener = mobileListener;
    }

    private boolean HaveMobileListener() {
        if (this.m_OnMobileListener == null) {
            return false;
        }
        return true;
    }

    public void SetPrinterInfo(EditMetaUtility m_EditMetaUtility, PrintMode mPrintMode) {
        this.m_PrintMode = mPrintMode;
        SetTextureAndDuplex(m_EditMetaUtility);
        SetMethodAndSharpen(m_EditMetaUtility);
        SetPrintOutAndMediaSize(m_EditMetaUtility);
        setPrinterInfo(m_EditMetaUtility.GetModel());
    }

    private void setPrinterInfo(String model) {
        this.LOG.m383d(this.TAG, "setPrinterInfo model:" + model);
        if (this.mPrintinfo == null) {
            this.mPrintinfo = new SendPhotoInfo(model);
        }
        this.mPrintinfo.SetPhotoSize(this.m_byMediaSize, this.m_byPrintout, this.m_bDuplex, this.m_iPaperType);
        this.mPrintinfo.SetPhotoFace(this.m_bSelTexture, this.m_byPrintQTY, this.m_bSharepen);
    }

    public void SetPrinterInfo(int paperType, boolean IsTexture, boolean IsDuplex, int methodSpeed, int sharpen) {
        this.m_bSelTexture = IsTexture;
        if (this.m_PrintMode == PrintMode.Snap) {
            IsDuplex = false;
        }
        this.m_bDuplex = IsDuplex;
        this.m_byPrintQTY = (byte) methodSpeed;
        this.m_bSharepen = EditMetaUtility.GetSarpenValue(sharpen);
        this.m_iPaperType = paperType;
        this.m_byMediaSize = PrinterInfo.GetMediaSize(this.m_iPaperType);
        this.m_byPrintout = PrinterInfo.GetPrintLayout(this.m_iPaperType);
        this.m_strChooseMediaSize = PrinterInfo.GetPrintoutItem(this.m_Context, this.m_iPaperType);
        setPrinterInfo(new JumpPreferenceKey(this.m_Context).GetModelPreference());
        setPrintout(SetPrintoutSize(paperType));
    }

    private void SetTextureAndDuplex(EditMetaUtility m_EditMetaUtility) {
        boolean z;
        boolean z2 = false;
        int iSelTexture = m_EditMetaUtility.GetPrintTexture();
        int iSelDuplex = m_EditMetaUtility.GetPrintDuplex();
        if (iSelTexture == 0) {
            z = false;
        } else {
            z = true;
        }
        this.m_bSelTexture = z;
        if (!(this.m_PrintMode == PrintMode.Snap || iSelDuplex == 0)) {
            z2 = true;
        }
        this.m_bDuplex = z2;
    }

    private void SetPrintOutAndMediaSize(EditMetaUtility m_EditMetaUtility) {
        this.m_iPathRoute = m_EditMetaUtility.GetPathRoute();
        this.m_iPaperType = m_EditMetaUtility.GetServerPaperType();
        this.m_byMediaSize = PrinterInfo.GetMediaSize(this.m_iPaperType);
        this.m_byPrintout = PrinterInfo.GetPrintLayout(this.m_iPaperType);
        this.m_strChooseMediaSize = PrinterInfo.GetPrintoutItem(this.m_Context, this.m_iPaperType);
        setPrintout(SetPrintoutSize(this.m_iPaperType));
    }

    private void setPrintout(int[][] length) {
        this.MAX_WIDTH = (float) length[0][0];
        this.MAX_HEIGHT = (float) length[0][SIZE_REDUCE];
        this.OUTPUT_WIDTH = length[SIZE_REDUCE][0];
        this.OUTPUT_HEIGHT = length[SIZE_REDUCE][SIZE_REDUCE];
    }

    private void SetMethodAndSharpen(EditMetaUtility m_EditMetaUtility) {
        switch (m_EditMetaUtility.GetPrintMethod()) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                this.m_byPrintQTY = (byte) 1;
                break;
            case SIZE_REDUCE /*1*/:
                this.m_byPrintQTY = (byte) 2;
                break;
        }
        this.m_bSharepen = m_EditMetaUtility.GetPrintSharpenByte();
    }

    public void Stop() {
        this.LOG.m385i("Have m_OnMobileListene Stop", "m_MobileHandler= " + String.valueOf(this.m_MobileHandler));
        this.m_bStop = true;
        if (this.m_SQLiteUtility != null) {
            this.m_SQLiteUtility.Close();
        }
        if (this.m_HitiPPR_GetPrinterInfo != null) {
            this.m_HitiPPR_GetPrinterInfo.Stop();
        }
        if (this.m_HitiPPR_RecoveryPrinter != null) {
            this.m_HitiPPR_RecoveryPrinter.Stop();
        }
        if (this.m_GetPrintAndJobSatus != null) {
            this.m_GetPrintAndJobSatus.Stop();
        }
        if (this.m_MobileHandler != null) {
            this.m_MobileHandler.SetStop(true);
        }
    }

    public void SetStop(boolean bStop) {
        if (this.m_MobileHandler != null) {
            if (bStop) {
                this.m_MobileHandler.SetStop(true);
            } else {
                this.m_MobileHandler.SetStop(false);
            }
        }
    }

    public void SetCancel() {
        this.m_bCancelState = true;
    }

    public void SetCloudBackup(boolean bCloudBackup) {
        if (this.m_PrintMode == PrintMode.Snap) {
            bCloudBackup = false;
        }
        this.m_bCloudBackup = bCloudBackup;
    }

    public void SendPhoto(Socket socket, String strIP, int iPort) {
        this.LOG.m383d(this.TAG, "SendPhoto");
        if (HaveMobileListener()) {
            this.m_OnMobileListener.StartCheckPrintInfo();
        }
        this.IP = strIP;
        this.m_iPort = iPort;
        this.m_bSkipState = false;
        this.m_MobileHandler.SetStop(false);
        this.m_HitiPPR_GetPrinterInfo = new HitiPPR_GetPrinterInfo(this.m_Context, strIP, iPort, this.m_MobileHandler);
        this.m_HitiPPR_GetPrinterInfo.SetSocket(socket);
        this.m_HitiPPR_GetPrinterInfo.SetRetry(false);
        this.m_HitiPPR_GetPrinterInfo.start();
    }

    public void SendForQty(int iQtyType, boolean bQty, boolean bSelAll, HitiPPR_PrinterCommand mPrinterCommand) {
        JumpPreferenceKey pef = new JumpPreferenceKey(this.m_Context);
        this.LOG.m386v(this.TAG, "SendForQty");
        switch (iQtyType) {
            case SIZE_REDUCE /*1*/:
                pef.SetPreference(JumpPreferenceKey.SIZE_DOWN_WILL, bQty);
                pef.SetPreference(JumpPreferenceKey.SIZE_DOWN_SEL_ALL, bSelAll);
                break;
            case SCALE_ADD /*2*/:
                pef.SetPreference(JumpPreferenceKey.SCALL_SEL_WILL, bQty);
                pef.SetPreference(JumpPreferenceKey.SCALL_SEL_ALL, bSelAll);
                break;
            case LOW_QTY_CROP /*3*/:
                pef.SetPreference(JumpPreferenceKey.LOW_QUALITY_WILL, bQty);
                pef.SetPreference(JumpPreferenceKey.LOW_QUALITY_SEL_ALL, bSelAll);
                break;
        }
        PrintSendPhoto(mPrinterCommand);
    }

    public void StartPrintForNotBurnFirmware(Socket socket) {
        if (this.m_iQueueJobIdList.isEmpty()) {
            GetPrinterStatus(true, socket);
        } else {
            GetPrinterStatus(false, socket);
        }
    }

    public void Recovery() {
        this.LOG.m385i(this.TAG, "Recovery");
        this.m_HitiPPR_RecoveryPrinter = new HitiPPR_RecoveryPrinter(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
        this.m_HitiPPR_RecoveryPrinter.start();
    }

    private void NewRecordPrint(int iTotalFrame) {
        String strUploader;
        int i;
        this.m_GVUploadInfo.RestoreGlobalVariable();
        this.m_GVUserInfo.RestoreGlobalVariable();
        String strProductID = this.m_strProductName.contains("P525") ? WirelessType.TYPE_P525L : this.m_strProductIDString;
        if (UploadUtility.HaveVerify(this.m_GVUserInfo)) {
            strUploader = this.m_GVUploadInfo.GetUploader();
        } else {
            strUploader = "###";
        }
        String strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(LOW_QTY_CROP);
        PrintingInfoUtility printingInfoUtility = this.m_SQLiteUtility;
        String GetSerialNumber = this.m_GVUploadInfo.GetSerialNumber();
        int AdjustPaperType = PrinterInfo.AdjustPaperType(this.m_iPaperType, this.m_bDuplex);
        String MakeAESCount = EncryptAndDecryptAES.MakeAESCount(this.m_Context, this.m_iPrintCount, strTimeStamp);
        String MakeAESCount2 = EncryptAndDecryptAES.MakeAESCount(this.m_Context, 0, strTimeStamp);
        int ChangeProductIDValueForServer = PrinterInfo.ChangeProductIDValueForServer(strProductID);
        if (this.m_PrintMode == PrintMode.Snap) {
            i = SIZE_REDUCE;
        } else {
            i = 0;
        }
        this.m_iSQLiteID = (int) printingInfoUtility.InsertPrintingInfo(GetSerialNumber, strTimeStamp, strUploader, 0, AdjustPaperType, MakeAESCount, MakeAESCount2, 0, ChangeProductIDValueForServer, iTotalFrame, i);
        this.LOG.m386v("*******new_ID:" + this.m_iSQLiteID, "should printCount=" + String.valueOf(this.m_iPrintCount));
        this.LOG.m386v("*******new_ID:" + this.m_iSQLiteID, " m_iTotalFrame=" + String.valueOf(iTotalFrame));
    }

    private void SavePrintCount(int iTotalFrame) {
        String strUploader;
        this.LOG.m386v("*************SavePrintCount", "ID: " + this.m_iSQLiteID);
        PrintingInfo printingInfo = this.m_SQLiteUtility.GetPrintingInfoById((long) this.m_iSQLiteID);
        int iLastCount = 0;
        this.m_GVUserInfo.RestoreGlobalVariable();
        if (UploadUtility.HaveVerify(this.m_GVUserInfo)) {
            strUploader = this.m_GVUploadInfo.GetUploader();
        } else {
            strUploader = "###";
        }
        if (printingInfo.GetID() == -1) {
            String strProductID;
            if (this.m_strProductName.contains("P525")) {
                strProductID = WirelessType.TYPE_P525L;
            } else {
                strProductID = this.m_strProductIDString;
            }
            String strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(LOW_QTY_CROP);
            this.m_GVUploadInfo.RestoreGlobalVariable();
            this.m_iSQLiteID = (int) this.m_SQLiteUtility.InsertPrintingInfo(this.m_GVUploadInfo.GetSerialNumber(), strTimeStamp, strUploader, 0, PrinterInfo.AdjustPaperType(this.m_iPaperType, this.m_bDuplex), EncryptAndDecryptAES.MakeAESCount(this.m_Context, this.m_iPrintCount, strTimeStamp), EncryptAndDecryptAES.MakeAESCount(this.m_Context, SIZE_REDUCE, strTimeStamp), 0, PrinterInfo.ChangeProductIDValueForServer(strProductID), iTotalFrame, 0);
            this.LOG.m386v("*************NewID_" + String.valueOf(this.m_iSQLiteID), "iTotalFrame=" + iTotalFrame);
            return;
        }
        String strAESCount = printingInfo.GetRealCount();
        strTimeStamp = printingInfo.GetPrintingTime();
        if (!(strAESCount == null || strAESCount.equals("NULL"))) {
            iLastCount = Integer.valueOf(EncryptAndDecryptAES.OpenCount(this.m_Context, EncryptAndDecryptAES.OpenAESCount(this.m_Context, strAESCount, strTimeStamp))).intValue();
        }
        iLastCount += SIZE_REDUCE;
        this.LOG.m386v("*********SaveID_" + printingInfo.GetID(), "printcount=" + String.valueOf(iLastCount + "/" + this.m_iPrintCount));
        this.m_SQLiteUtility.UpdatePrintingInfo(this.m_iSQLiteID, null, null, null, null, null, null, EncryptAndDecryptAES.MakeAESCount(this.m_Context, iLastCount, strTimeStamp), null, null, -1);
        this.LOG.m386v("*************SaveID_" + String.valueOf(printingInfo.GetID()), "iTotalFrame=" + iTotalFrame);
    }

    private void GetPrinterStatus(boolean status, Socket socket) {
        this.LOG.m386v(this.TAG, "GetPrinterStatus stop: " + this.m_bStop);
        if (!this.m_bStop) {
            int iJobId = -1;
            int iLastCp = 0;
            if (!this.m_iQueueJobIdList.isEmpty()) {
                iJobId = ((Integer) this.m_iQueueJobIdList.get(0)).intValue();
            }
            if (this.m_GetPrintAndJobSatus == null) {
                this.m_GetPrintAndJobSatus = new HitiPPR_GetPSC(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
            } else {
                if (iJobId != -1) {
                    iLastCp = this.m_GetPrintAndJobSatus.GetLastCopies();
                }
                this.m_GetPrintAndJobSatus = null;
                this.m_GetPrintAndJobSatus = new HitiPPR_GetPSC(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
            }
            this.m_GetPrintAndJobSatus.SetSocket(socket);
            this.m_GetPrintAndJobSatus.SetChooseMediaSize(this.m_strChooseMediaSize);
            this.m_GetPrintAndJobSatus.OpenCapabilityCheck(status);
            this.m_GetPrintAndJobSatus.PutJobIdToCheck(iJobId);
            this.m_GetPrintAndJobSatus.PutLastCopiesToNextGetStatus(iLastCp);
            this.m_GetPrintAndJobSatus.SetProductID(this.m_strProductIDString);
            this.m_GetPrintAndJobSatus.start();
        }
    }

    private int MobileSumOfCopies(int iLastCp) {
        int iSumCp = 0;
        Iterator it = this.m_iPrintedList.iterator();
        while (it.hasNext()) {
            iSumCp += ((Integer) it.next()).intValue();
        }
        return iSumCp + iLastCp;
    }

    private void SetTotoalPrintedRecord() {
        this.LOG.m385i(this.TAG, "SetTotoalPrintedRecord");
        if (this.m_GetPrintAndJobSatus != null) {
            HashMap<String, Integer> iPrintedCountList = this.m_GetPrintAndJobSatus.GetTotalPrintedFrame();
            this.LOG.m385i(this.TAG, "Save iPrintedCountList: " + iPrintedCountList);
            if (iPrintedCountList != null) {
                this.m_GVTotalPrintedRecord.RestoreGlobalVariable();
                this.m_GVTotalPrintedRecord.ClearGlobalVariable();
                this.m_GVTotalPrintedRecord.SetPrintOutList(iPrintedCountList);
                this.m_GVTotalPrintedRecord.SaveGlobalVariable();
            }
        }
    }

    private void PrintSendPhoto(HitiPPR_PrinterCommand mPrintCommand) {
        this.m_PrintCommand = mPrintCommand;
        this.LOG.m386v(this.TAG, "PrintSendPhoto list: " + this.m_strPhotoPathList);
        this.LOG.m386v(this.TAG, "QueueJobIdList list: " + this.m_iQueueJobIdList);
        if (this.m_strPhotoPathList.size() > 0) {
            CreateNewSendDataMission(mPrintCommand);
            if (this.m_strPhotoPathList.size() > this.m_iQueueJobIdList.size()) {
                if (this.m_strDateInfoList.size() > 0) {
                    this.m_prepareSendData.SetPhotoDate((DateInfo) this.m_strDateInfoList.get(this.m_iQueueJobIdList.size()));
                }
                if (HaveMobileListener()) {
                    this.m_OnMobileListener.PreparePhoto();
                }
                this.m_prepareSendData.execute(GetPrintPhotoPath());
                return;
            }
            this.m_MobileHandler.sendEmptyMessageDelayed(RequestState.REQUEST_RE_CHECK_IF_BUSY, 1500);
            return;
        }
        this.m_MobileHandler.sendEmptyMessageDelayed(RequestState.REQUEST_RE_CHECK_IF_BUSY, 1500);
    }

    void CreateNewSendDataMission(HitiPPR_PrinterCommand mPrintCommand) {
        this.LOG.m383d(this.TAG, "CreateNewSendDataMission: " + this.m_PrintMode);
        this.m_prepareSendData = new PrepareSendData(this.m_Context, (int) this.MAX_WIDTH, (int) this.MAX_HEIGHT, this.OUTPUT_WIDTH, this.OUTPUT_HEIGHT);
        this.m_prepareSendData.SetHadlerAndListener(this.m_MobileHandler, new PrePhotoListener());
        this.m_prepareSendData.SetPrintAttribute(this.m_PrintMode, this.m_iPathRoute, this.m_bNeedUnSharpen, this.mPrintinfo);
        this.m_prepareSendData.SetSocketAttribute(this.IP, this.m_iPort, mPrintCommand);
        this.m_prepareSendData.GetAskState(this.mbAskedSize, this.mbAskedScale, this.mbAskedLowQty);
    }

    private String[] GetPrintPhotoPath() {
        String[] list = this.mPrintinfo.GetPrintPhotoPath(this.m_strPhotoPathList, this.m_iQueueJobIdList.size());
        this.LOG.m386v(this.TAG, "GetPrintPhotoPath: " + list.length);
        int length = list.length;
        for (int i = 0; i < length; i += SIZE_REDUCE) {
            this.LOG.m386v(this.TAG, "GetPrintPhotoPath: " + list[i]);
        }
        return list;
    }

    void RemoveDataList() {
        this.m_strPhotoPathList.remove(0);
        this.m_iPhotoCopiesList.remove(0);
        this.m_iQueueJobIdList.remove(0);
        if (this.m_strDateInfoList.size() > 0) {
            this.m_strDateInfoList.remove(0);
        }
    }

    public void SkipToPrintNext(Socket socket) {
        if (this.m_strPhotoPathList.size() >= SIZE_REDUCE) {
            this.m_strPhotoPathList.remove(0);
        }
        if (this.m_strDateInfoList.size() >= SIZE_REDUCE) {
            this.m_strDateInfoList.remove(0);
        }
        if (this.m_strPhotoPathList.isEmpty()) {
            GetPrinterStatus(true, socket);
        } else {
            SendPhoto(socket, this.IP, this.m_iPort);
        }
    }

    public MobileUnit GetMobileValue() {
        MobileUnit m_MobileUnit = new MobileUnit();
        m_MobileUnit.SetPhotoList(this.m_strPhotoPathList, this.m_iPhotoCopiesList);
        m_MobileUnit.SetJobIdList(this.m_iPrintedList, this.m_iQueueJobIdList);
        return m_MobileUnit;
    }

    private void SetSocket(Socket socket) {
        this.m_LastSocket = socket;
    }

    private Socket GetSocket() {
        return this.m_LastSocket;
    }

    private void CloudBack() {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(FileUtility.GetSDAppRootPath(this.m_Context) + "/" + "PrintPhotoJPEGCompress.jpg", true);
        if (bmr.IsSuccess() && UploadUtility.NeedUploadPhoto(this.m_Context)) {
            UploadUtility.AddUploadPhoto(this.m_Context, bmr.GetBitmap(), null);
        }
    }

    private void GetUnCleanNumber(Socket socket) {
        this.LOG.m386v(this.TAG, "GetUnCleanNumber");
        SetSocket(socket);
        this.m_GetUnCleanNuber = new HitiPPR_GetUnCleanNumber(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
        this.m_GetUnCleanNuber.SetSocket(socket);
        this.m_GetUnCleanNuber.start();
    }

    public void EjectPaperJam() {
        this.LOG.m386v(this.TAG, "EjectPaperJam");
        this.m_PaperJamRun = new HitiPPR_PaperJamRun(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
        this.m_PaperJamRun.start();
    }

    public void StopPaperJam() {
        if (this.m_PaperJamRun != null) {
            this.m_PaperJamRun.Stop();
        }
    }

    public boolean GetSkipState() {
        return this.m_bSkipState;
    }

    void GetFirstPrintFrame(HitiPPR_GetPSC requestGPS, Socket socket) {
        this.LOG.m383d(this.TAG, "GetFirstPrintFrame");
        this.mCheckPrinterTotalPrintedCount = new HitiPPR_CheckPrinterTotalPrintedCount(this.m_Context, this.IP, this.m_iPort, this.m_MobileHandler);
        this.mCheckPrinterTotalPrintedCount.SetProductID(this.m_strProductIDString);
        this.mCheckPrinterTotalPrintedCount.SetGPSrequest(requestGPS);
        this.mCheckPrinterTotalPrintedCount.SetSocket(socket);
        this.mCheckPrinterTotalPrintedCount.start();
    }
}
