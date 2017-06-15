package com.example.sonic.hitiprinter.ui.edmview;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.example.sonic.hitiprinter.sql.oadc.OADCItem;
import com.example.sonic.hitiprinter.sql.oadc.OADCItemUtility;
import com.example.sonic.hitiprinter.sql.offlineaddownloadinfo.OfflineADDownloadItem;
import com.example.sonic.hitiprinter.sql.offlineaddownloadinfo.OfflineADDownloadItemUtility;
import com.example.sonic.hitiprinter.trace.GlobalVariable_OfflineADDownloadInfo;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import java.util.Timer;
import java.util.TimerTask;
import org.ksoap2.SoapEnvelope;

public class EDMPlayController {
    private LogManager LOG;
    private Context m_Context;
    private ControllerHandler m_ControllerHandler;
    private int m_CurrentPlayCount;
    private EDMViewHandler m_EDMViewHandler;
    private OADCItemUtility m_OADCIUtility;
    private Cursor m_OADDICursor;
    private OfflineADDownloadItemUtility m_OADDIUtility;
    private PlayItem m_PlayItem;
    private TimeOutTask m_TimeOutTask;
    private Timer m_TimeOutTimer;
    private int m_TotalPlayCount;
    private boolean m_boStop;
    private String m_strCountry;
    private String m_strUser;

    public class ControllerHandler extends Handler {
        private static final String MSG = "MSG";
        private boolean m_boStop;

        public class ControllerState {
            public static final int PLAY_NEXT = 100;
        }

        public ControllerHandler() {
            this.m_boStop = false;
        }

        void SetStop(boolean boStop) {
            this.m_boStop = boStop;
        }

        public void handleMessage(Message msg) {
            if (!this.m_boStop) {
                switch (msg.what) {
                    case SoapEnvelope.VER10 /*100*/:
                        EDMPlayController.this.StartPlay();
                        break;
                }
                super.handleMessage(msg);
            }
        }

        public void SendMessage(int iMsg, String strMsg) {
            Message msg = Message.obtain();
            msg.what = iMsg;
            if (strMsg != null) {
                Bundle buddle = new Bundle();
                buddle.putString(MSG, strMsg);
                msg.setData(buddle);
            }
            sendMessage(msg);
        }
    }

    private class TimeOutTask extends TimerTask {
        private TimeOutTask() {
        }

        public void run() {
            EDMPlayController.this.LOG.m385i("TimeOutTask", "RUN TIMEOUT");
            if (!EDMPlayController.this.m_PlayItem.GetVideoPlay()) {
                EDMPlayController.this.m_PlayItem.SetVideoPlay(true);
                if (EDMPlayController.this.HaveControllerHandler()) {
                    EDMPlayController.this.m_ControllerHandler.SendMessage(100, null);
                }
            } else if (EDMPlayController.this.m_PlayItem.GetPhotoPlay()) {
                int iSC = EDMPlayController.this.m_PlayItem.GetShowCount() + 1;
                EDMPlayController.this.m_PlayItem.SetShowCount(iSC);
                EDMPlayController.this.LOG.m384e("iSC", String.valueOf(iSC));
                EDMPlayController.this.m_OADDIUtility.UpdateOADDI(EDMPlayController.this.m_PlayItem.GetPlayID(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, String.valueOf(iSC), null);
                EDMPlayController.this.m_OADCIUtility.UpdateOADC(EDMPlayController.this.m_Context, Integer.valueOf(EDMPlayController.this.m_PlayItem.GetOADCIID()).intValue(), EDMPlayController.this.m_PlayItem.GetADItemID(), EDMPlayController.this.m_PlayItem.GetTimeStamp(), EDMPlayController.this.m_strUser, OADCItem.WATCH_TYPE_WATCH, EDMPlayController.this.m_strCountry);
                if (EDMPlayController.this.m_CurrentPlayCount == EDMPlayController.this.m_TotalPlayCount) {
                    EDMPlayController.this.m_CurrentPlayCount = 0;
                } else {
                    EDMPlayController.this.m_CurrentPlayCount = EDMPlayController.this.m_CurrentPlayCount + 1;
                }
                EDMPlayController.this.Dispatch();
            } else {
                EDMPlayController.this.m_PlayItem.SetPhotoPlay(true);
                if (EDMPlayController.this.HaveControllerHandler()) {
                    EDMPlayController.this.m_ControllerHandler.SendMessage(100, null);
                }
            }
        }
    }

    public EDMPlayController(Context context) {
        this.LOG = null;
        this.m_OADDIUtility = null;
        this.m_OADCIUtility = null;
        this.m_OADDICursor = null;
        this.m_EDMViewHandler = null;
        this.m_ControllerHandler = null;
        this.m_TimeOutTimer = null;
        this.m_TimeOutTask = null;
        this.m_PlayItem = null;
        this.m_boStop = false;
        this.m_CurrentPlayCount = 0;
        this.m_TotalPlayCount = 0;
        this.m_strUser = "NULL";
        this.m_strCountry = "886";
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.m_OADDIUtility = new OfflineADDownloadItemUtility(this.m_Context);
        this.m_OADCIUtility = new OADCItemUtility(this.m_Context);
        this.m_ControllerHandler = new ControllerHandler();
        GlobalVariable_OfflineADDownloadInfo GVOfflineADDownloadInfo = new GlobalVariable_OfflineADDownloadInfo(context);
        GVOfflineADDownloadInfo.RestoreGlobalVariable();
        if (GVOfflineADDownloadInfo.GetOADDI_Country().length() > 0) {
            this.m_strCountry = GVOfflineADDownloadInfo.GetOADDI_Country();
        }
    }

    public void SetEDMViewHandler(EDMViewHandler edmViewHandler) {
        this.m_EDMViewHandler = edmViewHandler;
    }

    private boolean HaveEDMViewHandler() {
        if (this.m_EDMViewHandler == null) {
            return false;
        }
        return true;
    }

    private boolean HaveControllerHandler() {
        if (this.m_ControllerHandler == null) {
            return false;
        }
        return true;
    }

    public void Dispatch() {
        if (this.m_boStop) {
            if (this.m_OADDIUtility != null) {
                this.m_OADDIUtility.Close();
            }
            if (this.m_OADCIUtility != null) {
                this.m_OADCIUtility.Close();
            }
        } else if (FileUtility.SDCardState()) {
            OfflineADDownloadItem oaddi = GetShowOADDI();
            if (oaddi.GetID() != -1) {
                int PlayID = oaddi.GetID();
                String strVideoPath = oaddi.GetAD_Video_File_Path();
                boolean boVideoPlay = false;
                if (strVideoPath.equals("NULL")) {
                    boVideoPlay = true;
                } else if (!FileUtility.FileExist(strVideoPath)) {
                    boVideoPlay = true;
                }
                int VideoPlayTime = 0;
                if (!oaddi.GetAD_Video_Time().equals("NULL")) {
                    VideoPlayTime = Integer.valueOf(oaddi.GetAD_Video_Time()).intValue();
                }
                String strPhotoPath = oaddi.GetAD_Photo_File_Path();
                boolean boPhotoPlay = false;
                if (strPhotoPath.equals("NULL")) {
                    boPhotoPlay = true;
                } else if (!FileUtility.FileExist(strPhotoPath)) {
                    boPhotoPlay = true;
                }
                int PhotoPlayTime = 0;
                if (!oaddi.GetAD_Photo_Time().equals("NULL")) {
                    PhotoPlayTime = Integer.valueOf(oaddi.GetAD_Photo_Time()).intValue();
                }
                if (boVideoPlay && boPhotoPlay) {
                    if (HaveEDMViewHandler()) {
                        this.m_EDMViewHandler.SendMessage(ControllerState.NO_PLAY_ITEM, null);
                    }
                    this.m_OADDIUtility.UpdateOADDI(PlayID, null, null, null, null, null, null, "NULL", null, null, null, null, "NULL", null, null, null, String.valueOf(0));
                    this.LOG.m384e("Dispatch", "No File");
                    return;
                }
                int ShowCount = oaddi.GetAD_Show_Count();
                String strTimeStamp = MobileInfo.GetTimeStamp() + MobileInfo.MakeRandString(3);
                Long oadcid = this.m_OADCIUtility.InsertOADC(this.m_Context, oaddi.GetAD_Item_ID(), strTimeStamp, this.m_strUser, OADCItem.WATCH_TYPE_NON, this.m_strCountry);
                if (oadcid.longValue() != -1) {
                    this.m_PlayItem = new PlayItem(PlayID, oaddi.GetAD_Item_ID(), String.valueOf(oadcid), strTimeStamp, strVideoPath, boVideoPlay, VideoPlayTime, strPhotoPath, boPhotoPlay, PhotoPlayTime, ShowCount);
                    if (HaveEDMViewHandler()) {
                        this.m_EDMViewHandler.SendMessage(ControllerState.PLAY_COUNT_STATE, String.valueOf(this.m_CurrentPlayCount) + "/" + String.valueOf(this.m_TotalPlayCount));
                    }
                    StartPlay();
                }
            } else if (HaveEDMViewHandler()) {
                this.m_EDMViewHandler.SendMessage(ControllerState.NO_PLAY_ITEM, null);
            }
        } else if (HaveEDMViewHandler()) {
            this.m_EDMViewHandler.SendMessage(ControllerState.NO_PLAY_ITEM, null);
        }
    }

    private void StartPlay() {
        StopTimerOut();
        if (this.m_boStop) {
            if (this.m_OADDIUtility != null) {
                this.m_OADDIUtility.Close();
            }
            if (this.m_OADCIUtility != null) {
                this.m_OADCIUtility.Close();
            }
        } else if (this.m_PlayItem != null && this.m_TimeOutTimer == null) {
            this.m_TimeOutTimer = new Timer(true);
            int iTimeOut = 0;
            if (!this.m_PlayItem.GetVideoPlay()) {
                this.LOG.m385i("StartPlay", "GetVideoPlay");
                iTimeOut = this.m_PlayItem.GetVideoPlayTime();
                if (HaveEDMViewHandler()) {
                    this.m_EDMViewHandler.SendMessage(100, this.m_PlayItem.GetVideoPath(), String.valueOf(iTimeOut));
                }
            } else if (!this.m_PlayItem.GetPhotoPlay()) {
                this.LOG.m385i("StartPlay", "GetPhotoPlay");
                iTimeOut = this.m_PlayItem.GetPhotoPlayTime();
                if (HaveEDMViewHandler()) {
                    this.m_EDMViewHandler.SendMessage(ControllerState.PLAY_PHOTO, this.m_PlayItem.GetPhotoPath(), String.valueOf(iTimeOut));
                }
            }
            this.m_TimeOutTask = new TimeOutTask();
            this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) iTimeOut);
        }
    }

    private void StopTimerOut() {
        this.LOG.m385i("EDMPlayController", "StopTimerOut");
        if (this.m_TimeOutTimer != null) {
            this.m_TimeOutTimer.cancel();
            this.m_TimeOutTimer = null;
        }
        if (this.m_TimeOutTask != null) {
            this.m_TimeOutTask.cancel();
            this.m_TimeOutTask = null;
        }
    }

    public void Stop() {
        if (HaveControllerHandler()) {
            this.m_ControllerHandler.SetStop(true);
        }
        StopTimerOut();
        this.m_boStop = true;
    }

    OfflineADDownloadItem GetShowOADDI() {
        OfflineADDownloadItem oaddi = new OfflineADDownloadItem();
        if (this.m_OADDICursor == null) {
            String strTimeStamp = MobileInfo.GetTimeStamp();
            this.m_OADDICursor = this.m_OADDIUtility.GetShowOADDICursor(strTimeStamp);
            if (this.m_OADDICursor == null) {
                return oaddi;
            }
            this.m_TotalPlayCount = this.m_OADDIUtility.GetShowOADDISize(strTimeStamp);
            if (!this.m_OADDICursor.moveToFirst()) {
                return oaddi;
            }
        }
        if (this.m_OADDICursor != null) {
            oaddi = this.m_OADDIUtility.GetOADDIFromCursor(this.m_OADDICursor);
            if (!this.m_OADDICursor.moveToNext()) {
                this.m_OADDICursor.close();
                this.m_OADDICursor = null;
            }
        }
        return oaddi;
    }

    public void SetWatchUser(String strUser) {
        if (strUser != null) {
            this.m_strUser = strUser;
        }
    }
}
