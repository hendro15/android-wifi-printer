package com.example.sonic.hitiprinter.ui.edmview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import javax.jmdns.impl.constants.DNSConstants;
import org.ksoap2.SoapEnvelope;

public class EDMView extends RelativeLayout {
    private LogManager LOG;
    private int UPDATE_TIME_UNIT;
    private Handler handler;
    Context m_Context;
    long m_CurrentTime;
    private int m_DefaultPhotoRes;
    private ImageView m_EDMImageView;
    private EDMPlayController m_EDMPlayController;
    protected TextView m_EDMPlayCountStatusTextView;
    protected TextView m_EDMPrintingHintTextView;
    protected TextView m_EDMPrintingStatusTextView;
    private VideoView m_EDMVideoView;
    private ProgressBar m_EDMVideoViewLoadProgressBar;
    private RelativeLayout m_EDMVideoViewRelativeLayout;
    private EDMViewHandler m_EDMViewHandler;
    private EDMViewListener m_EDMViewListener;
    private ImageButton m_PrintCancelImageButton;
    private Runnable m_UpdateTimer;
    private int m_VideoMaxTime;
    private VideoViewListener m_VideoViewListener;
    private boolean m_boDisablePlayCountState;
    private boolean m_boEnableEDMVideoViewLoadProgressBar;
    private int m_iBackgroundRes;
    private int m_iPhotoLimitHeight;
    private int m_iPhotoLimitWidth;
    private String m_strUser;

    /* renamed from: com.hiti.ui.edmview.EDMView.1 */
    class C04281 implements OnClickListener {
        C04281() {
        }

        public void onClick(View v) {
            if (EDMView.this.HaveEDMViewListener()) {
                EDMView.this.m_EDMViewListener.PrintCancel();
            }
        }
    }

    /* renamed from: com.hiti.ui.edmview.EDMView.2 */
    class C04292 implements OnClickListener {
        C04292() {
        }

        public void onClick(View v) {
        }
    }

    /* renamed from: com.hiti.ui.edmview.EDMView.3 */
    class C04313 implements OnPreparedListener {

        /* renamed from: com.hiti.ui.edmview.EDMView.3.1 */
        class C04301 implements OnInfoListener {
            C04301() {
            }

            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == 701) {
                    if (EDMView.this.HaveVideoViewListener()) {
                        EDMView.this.m_VideoViewListener.Loading();
                    }
                } else if (what == 702 && EDMView.this.HaveVideoViewListener()) {
                    EDMView.this.m_VideoViewListener.StopLoading();
                }
                return false;
            }
        }

        C04313() {
        }

        public void onPrepared(MediaPlayer mp) {
            EDMView.this.LOG.m384e("m_EDMVideoViewLoadProgressBar", "Changed");
            if (EDMView.this.HaveVideoViewListener()) {
                EDMView.this.m_VideoViewListener.StartLoading();
            }
            if (VERSION.SDK_INT >= 9) {
                EDMView.this.ShowEDMVideoViewLoadProgressBar(true);
                mp.setOnInfoListener(new C04301());
            }
        }
    }

    /* renamed from: com.hiti.ui.edmview.EDMView.4 */
    class C04324 implements OnCompletionListener {
        C04324() {
        }

        public void onCompletion(MediaPlayer mp) {
            EDMView.this.LOG.m384e("onCompletion", "onCompletion");
            if (EDMView.this.HaveVideoViewListener()) {
                EDMView.this.m_VideoViewListener.Complete();
            }
        }
    }

    /* renamed from: com.hiti.ui.edmview.EDMView.5 */
    class C04335 implements OnErrorListener {
        C04335() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            EDMView.this.LOG.m385i("setOnErrorListener", "setOnErrorListener");
            switch (what) {
            }
            if (EDMView.this.HaveVideoViewListener()) {
                EDMView.this.m_VideoViewListener.Error(what);
            }
            return true;
        }
    }

    /* renamed from: com.hiti.ui.edmview.EDMView.6 */
    class C04346 implements Runnable {
        C04346() {
        }

        public void run() {
            long NowTime = Long.valueOf(MobileInfo.GetHmsSStamp()).longValue();
            int SCAP = (int) (((long) ((int) NowTime)) - EDMView.this.m_CurrentTime);
            EDMView.this.LOG.m384e("m_CurrentTime", String.valueOf(EDMView.this.m_CurrentTime));
            EDMView.this.LOG.m384e("NowTime", String.valueOf(NowTime));
            EDMView.this.LOG.m384e("SCAP", String.valueOf(SCAP));
            EDMView.this.m_EDMVideoViewLoadProgressBar.setProgress(SCAP);
            EDMView.this.handler.postDelayed(this, (long) EDMView.this.UPDATE_TIME_UNIT);
        }
    }

    public class EDMViewHandler extends Handler {
        private static final String MSG = "MSG";
        private static final String TIMEOUT = "TIMEOUT";
        private boolean m_boStop;

        public class ControllerState {
            public static final int NO_PLAY_ITEM = 102;
            public static final int PLAY_COUNT_STATE = 103;
            public static final int PLAY_PHOTO = 101;
            public static final int PLAY_VIDEO = 100;
        }

        public EDMViewHandler() {
            this.m_boStop = false;
        }

        public void SetStop(boolean boStop) {
            this.m_boStop = boStop;
        }

        public void handleMessage(Message msg) {
            if (!this.m_boStop) {
                Bundle buddle;
                String strMSG;
                String strTimeOut;
                switch (msg.what) {
                    case SoapEnvelope.VER10 /*100*/:
                        buddle = msg.getData();
                        strMSG = null;
                        strTimeOut = null;
                        if (buddle != null) {
                            strMSG = buddle.getString(MSG);
                            strTimeOut = buddle.getString(TIMEOUT);
                        }
                        if (strMSG != null) {
                            EDMView.this.PlayVideo(strMSG, Integer.valueOf(strTimeOut).intValue());
                            break;
                        }
                        break;
                    case ControllerState.PLAY_PHOTO /*101*/:
                        buddle = msg.getData();
                        strMSG = null;
                        strTimeOut = null;
                        if (buddle != null) {
                            strMSG = buddle.getString(MSG);
                            strTimeOut = buddle.getString(TIMEOUT);
                        }
                        if (strMSG != null) {
                            EDMView.this.PlayPhoto(strMSG, Integer.valueOf(strTimeOut).intValue());
                            break;
                        }
                        break;
                    case ControllerState.NO_PLAY_ITEM /*102*/:
                        EDMView.this.NoPlayItem();
                        break;
                    case ControllerState.PLAY_COUNT_STATE /*103*/:
                        buddle = msg.getData();
                        strMSG = null;
                        if (buddle != null) {
                            strMSG = buddle.getString(MSG);
                        }
                        if (strMSG != null) {
                            EDMView.this.PlayCountState(strMSG);
                            break;
                        }
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

        public void SendMessage(int iMsg, String strMsg, String strTimeOut) {
            Message msg = Message.obtain();
            msg.what = iMsg;
            Bundle buddle = new Bundle();
            if (strMsg != null) {
                buddle.putString(MSG, strMsg);
            }
            if (strTimeOut != null) {
                buddle.putString(TIMEOUT, strTimeOut);
            }
            msg.setData(buddle);
            sendMessage(msg);
        }
    }

    public EDMView(Context context) {
        super(context);
        this.LOG = null;
        this.m_EDMVideoViewRelativeLayout = null;
        this.m_EDMVideoView = null;
        this.m_EDMVideoViewLoadProgressBar = null;
        this.m_VideoViewListener = null;
        this.m_EDMImageView = null;
        this.m_EDMPlayCountStatusTextView = null;
        this.m_EDMPrintingStatusTextView = null;
        this.m_EDMPrintingHintTextView = null;
        this.m_PrintCancelImageButton = null;
        this.m_EDMPlayController = null;
        this.m_iBackgroundRes = -1;
        this.m_DefaultPhotoRes = -1;
        this.m_EDMViewHandler = null;
        this.m_EDMViewListener = null;
        this.m_iPhotoLimitWidth = 0;
        this.m_iPhotoLimitHeight = 0;
        this.UPDATE_TIME_UNIT = DNSConstants.PROBE_CONFLICT_INTERVAL;
        this.m_VideoMaxTime = 0;
        this.m_boDisablePlayCountState = false;
        this.m_strUser = "NULL";
        this.m_boEnableEDMVideoViewLoadProgressBar = true;
        this.handler = new Handler();
        this.m_CurrentTime = 0;
        this.m_UpdateTimer = new C04346();
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.m_EDMViewHandler = new EDMViewHandler();
        SetupEDMView();
    }

    public EDMView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.LOG = null;
        this.m_EDMVideoViewRelativeLayout = null;
        this.m_EDMVideoView = null;
        this.m_EDMVideoViewLoadProgressBar = null;
        this.m_VideoViewListener = null;
        this.m_EDMImageView = null;
        this.m_EDMPlayCountStatusTextView = null;
        this.m_EDMPrintingStatusTextView = null;
        this.m_EDMPrintingHintTextView = null;
        this.m_PrintCancelImageButton = null;
        this.m_EDMPlayController = null;
        this.m_iBackgroundRes = -1;
        this.m_DefaultPhotoRes = -1;
        this.m_EDMViewHandler = null;
        this.m_EDMViewListener = null;
        this.m_iPhotoLimitWidth = 0;
        this.m_iPhotoLimitHeight = 0;
        this.UPDATE_TIME_UNIT = DNSConstants.PROBE_CONFLICT_INTERVAL;
        this.m_VideoMaxTime = 0;
        this.m_boDisablePlayCountState = false;
        this.m_strUser = "NULL";
        this.m_boEnableEDMVideoViewLoadProgressBar = true;
        this.handler = new Handler();
        this.m_CurrentTime = 0;
        this.m_UpdateTimer = new C04346();
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.m_EDMViewHandler = new EDMViewHandler();
        SetupEDMView();
    }

    private void SetupEDMView() {
        this.m_EDMPrintingStatusTextView = new TextView(this.m_Context);
        this.m_EDMPlayCountStatusTextView = new TextView(this.m_Context);
        this.m_EDMPrintingHintTextView = new TextView(this.m_Context);
        this.m_EDMImageView = new ImageView(this.m_Context);
        this.m_EDMVideoView = new VideoView(this.m_Context);
        this.m_EDMVideoViewLoadProgressBar = new ProgressBar(this.m_Context, null, 16842872);
        this.m_EDMVideoViewRelativeLayout = new RelativeLayout(this.m_Context);
        this.m_PrintCancelImageButton = new ImageButton(this.m_Context);
        this.m_PrintCancelImageButton.setId(118576);
        this.m_EDMPlayCountStatusTextView.setId(131499);
        this.m_EDMPrintingStatusTextView.setId(201236);
        this.m_EDMPrintingHintTextView.setId(79863);
        this.m_EDMVideoViewLoadProgressBar.setId(777888);
        LayoutParams lpcib = new LayoutParams(-2, -2);
        lpcib.addRule(11);
        lpcib.addRule(12);
        lpcib.setMargins(5, 5, 10, 5);
        this.m_PrintCancelImageButton.setBackgroundColor(getResources().getColor(17170445));
        this.m_PrintCancelImageButton.setLayoutParams(lpcib);
        addView(this.m_PrintCancelImageButton);
        this.m_PrintCancelImageButton.setOnClickListener(new C04281());
        LayoutParams lptv = new LayoutParams(-2, -2);
        lptv.addRule(12);
        lptv.addRule(0, this.m_PrintCancelImageButton.getId());
        lptv.addRule(9);
        lptv.setMargins(5, 0, 0, 5);
        this.m_EDMPlayCountStatusTextView.setLayoutParams(lptv);
        addView(this.m_EDMPlayCountStatusTextView);
        LayoutParams lpstv = new LayoutParams(-2, -2);
        lpstv.addRule(2, this.m_EDMPlayCountStatusTextView.getId());
        lpstv.addRule(0, this.m_PrintCancelImageButton.getId());
        lpstv.addRule(9);
        lpstv.setMargins(5, 0, 0, 0);
        this.m_EDMPrintingStatusTextView.setLayoutParams(lpstv);
        addView(this.m_EDMPrintingStatusTextView);
        LayoutParams lphtv = new LayoutParams(-2, -2);
        lphtv.addRule(2, this.m_EDMPrintingStatusTextView.getId());
        lphtv.addRule(0, this.m_PrintCancelImageButton.getId());
        lphtv.addRule(9);
        lphtv.setMargins(5, 0, 0, 0);
        this.m_EDMPrintingHintTextView.setLayoutParams(lphtv);
        addView(this.m_EDMPrintingHintTextView);
        LayoutParams lpp = new LayoutParams(-1, -2);
        lpp.addRule(12);
        lpp.addRule(2, this.m_EDMPrintingHintTextView.getId());
        this.m_EDMVideoViewLoadProgressBar.setLayoutParams(lpp);
        addView(this.m_EDMVideoViewLoadProgressBar);
        LayoutParams lpiv = new LayoutParams(-1, -1);
        lpiv.addRule(2, this.m_EDMVideoViewLoadProgressBar.getId());
        lpiv.addRule(13);
        this.m_EDMImageView.setScaleType(ScaleType.FIT_CENTER);
        this.m_EDMImageView.setLayoutParams(lpiv);
        this.m_EDMImageView.setOnClickListener(new C04292());
        addView(this.m_EDMImageView);
        LayoutParams lpr = new LayoutParams(-1, -1);
        lpr.addRule(13);
        lpr.addRule(2, this.m_EDMVideoViewLoadProgressBar.getId());
        this.m_EDMVideoViewRelativeLayout.setLayoutParams(lpr);
        addView(this.m_EDMVideoViewRelativeLayout);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.addRule(13);
        lp.addRule(10);
        lp.addRule(12);
        this.m_EDMVideoView.setLayoutParams(lp);
        this.m_EDMVideoViewRelativeLayout.addView(this.m_EDMVideoView);
        this.m_EDMVideoView.setOnPreparedListener(new C04313());
        this.m_EDMVideoView.setOnCompletionListener(new C04324());
        this.m_EDMVideoView.setOnErrorListener(new C04335());
    }

    public void Stop() {
        if (this.m_EDMVideoView != null) {
            this.m_EDMVideoView.stopPlayback();
        }
        if (this.m_EDMPlayController != null) {
            this.m_EDMPlayController.Stop();
        }
        this.m_EDMViewHandler.SetStop(true);
        StopVideoProgressBar();
    }

    private boolean HaveEDMViewListener() {
        if (this.m_EDMViewListener == null) {
            return false;
        }
        return true;
    }

    public void SetEDMViewListener(EDMViewListener Listener) {
        this.m_EDMViewListener = Listener;
    }

    private boolean HaveVideoViewListener() {
        if (this.m_VideoViewListener == null) {
            return false;
        }
        return true;
    }

    public void SetVideoViewListener(VideoViewListener Listener) {
        this.m_VideoViewListener = Listener;
    }

    public void SetBackground(int iRes) {
        if (iRes != -1) {
            this.m_iBackgroundRes = iRes;
            setBackgroundResource(iRes);
        }
    }

    public void ShowEDMView(String strUser, int iPhotoLimitWidth, int iPhotoLimitHeight, int iRes, int iDefaultPhotoRes) {
        Stop();
        SetWatchUser(strUser);
        SetBackground(iRes);
        this.m_DefaultPhotoRes = iDefaultPhotoRes;
        this.m_iPhotoLimitWidth = iPhotoLimitWidth;
        this.m_iPhotoLimitHeight = iPhotoLimitHeight;
        this.m_EDMViewHandler.SetStop(false);
        this.m_EDMPlayController = new EDMPlayController(this.m_Context);
        this.m_EDMPlayController.SetWatchUser(strUser);
        this.m_EDMPlayController.SetEDMViewHandler(this.m_EDMViewHandler);
        this.m_EDMPlayController.Dispatch();
    }

    private BitmapMonitorResult MakeEDMPhoto(String strPhotoPath, int iPhotoLimitWidth, int iPhotoLimitHeight) {
        BitmapMonitorResult bmr = BitmapMonitor.GetBitmapFromFile(this.m_Context, strPhotoPath, false);
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        Bitmap EDMBitmap = bmr.GetBitmap();
        float fWidth = ((float) EDMBitmap.getWidth()) * 0.8f;
        float fHeight = ((float) EDMBitmap.getHeight()) * 0.8f;
        if (!(iPhotoLimitWidth == 0 || iPhotoLimitHeight == 0)) {
            float fPhotoLimitWidth80 = ((float) iPhotoLimitWidth) * 0.8f;
            float fRatio = fWidth / fHeight;
            fWidth = fPhotoLimitWidth80;
            fHeight = fPhotoLimitWidth80 / fRatio;
        }
        bmr = BitmapMonitor.CreateScaledBitmap(EDMBitmap, (int) fWidth, (int) fHeight, true);
        if (!(EDMBitmap == null || EDMBitmap.isRecycled())) {
            EDMBitmap.recycle();
        }
        return bmr;
    }

    private void PlayVideo(String strVideoPath, int iTimeOut) {
        if (HaveEDMViewListener()) {
            this.m_EDMViewListener.PreparePlayVideo(strVideoPath, iTimeOut);
        }
        this.m_VideoMaxTime = iTimeOut;
        this.m_EDMImageView.setVisibility(8);
        this.m_EDMVideoViewRelativeLayout.setVisibility(0);
        this.m_EDMVideoView.setVisibility(0);
        ShowEDMVideoViewLoadProgressBar(true);
        this.m_EDMVideoView.requestFocus();
        this.m_EDMVideoView.setVideoPath(strVideoPath);
        this.m_EDMVideoView.start();
        StartVideoProgressBar();
    }

    private void PlayPhoto(String strPhotoPath, int iTimeOut) {
        if (HaveEDMViewListener()) {
            this.m_EDMViewListener.PreparePlayPhoto(strPhotoPath, iTimeOut);
        }
        StopVideoProgressBar();
        this.m_EDMVideoView.stopPlayback();
        this.m_EDMImageView.setVisibility(0);
        this.m_EDMVideoView.setVisibility(8);
        this.m_EDMVideoViewRelativeLayout.setVisibility(8);
        ShowEDMVideoViewLoadProgressBar(false);
        BitmapDrawable d = (BitmapDrawable) this.m_EDMImageView.getDrawable();
        this.m_EDMImageView.setImageResource(this.m_DefaultPhotoRes);
        if (d != null) {
            Bitmap b = d.getBitmap();
            if (!(b == null || b.isRecycled())) {
                b.recycle();
            }
        }
        BitmapMonitorResult bmr = MakeEDMPhoto(strPhotoPath, this.m_iPhotoLimitWidth, this.m_iPhotoLimitHeight);
        if (bmr.IsSuccess()) {
            this.m_EDMImageView.setImageBitmap(bmr.GetBitmap());
        }
    }

    private void PlayCountState(String strState) {
        if (this.m_boDisablePlayCountState) {
            this.m_EDMPlayCountStatusTextView.setText(strState);
        }
        if (HaveEDMViewListener()) {
            this.m_EDMViewListener.PlayCountStatus(strState);
        }
    }

    private void NoPlayItem() {
        if (HaveEDMViewListener()) {
            this.m_EDMViewListener.NoPlayItem();
        }
        this.m_EDMImageView.setVisibility(8);
        this.m_EDMVideoView.setVisibility(8);
        this.m_EDMVideoViewRelativeLayout.setVisibility(8);
        StopVideoProgressBar();
        ShowEDMVideoViewLoadProgressBar(false);
    }

    private void StopVideoProgressBar() {
        this.handler.removeCallbacks(this.m_UpdateTimer);
    }

    private void StartVideoProgressBar() {
        if (GetEDMVideoViewLoadProgressBarEnable()) {
            this.m_CurrentTime = Long.valueOf(MobileInfo.GetHmsSStamp()).longValue();
            this.m_EDMVideoViewLoadProgressBar.setProgress(0);
            this.m_EDMVideoViewLoadProgressBar.setMax(this.m_VideoMaxTime);
            this.handler.removeCallbacks(this.m_UpdateTimer);
            this.handler.postDelayed(this.m_UpdateTimer, (long) this.UPDATE_TIME_UNIT);
        }
    }

    public void SetDisablePlayCountState() {
        this.m_boDisablePlayCountState = false;
    }

    public void SetEDMPlayCountStatusText(String strState) {
        this.m_EDMPlayCountStatusTextView.setText(strState);
    }

    public void SetEDMPlayCountStatusTextColor(int iColor) {
        this.m_EDMPlayCountStatusTextView.setTextColor(iColor);
    }

    public void SetEDMPrintingStatusText(String strState) {
        this.m_EDMPrintingStatusTextView.setText(strState);
    }

    public void SetEDMPrintingStatusTextColor(int iColor) {
        this.m_EDMPrintingStatusTextView.setTextColor(iColor);
    }

    public void SetEDMPrintingHintStatusText(String strState) {
        this.m_EDMPrintingHintTextView.setText(strState);
    }

    public void SetEDMPrintingHintStatusTextColor(int iColor) {
        this.m_EDMPrintingHintTextView.setTextColor(iColor);
    }

    public void SetWatchUser(String strUser) {
        if (strUser != null) {
            this.m_strUser = strUser;
        }
    }

    public void SetEDMVideoViewLoadProgressBarEnable(boolean boEnable) {
        this.m_boEnableEDMVideoViewLoadProgressBar = boEnable;
    }

    public void SetPrintCancelImage(int iRes) {
        this.m_PrintCancelImageButton.setImageResource(iRes);
    }

    public boolean GetEDMVideoViewLoadProgressBarEnable() {
        return this.m_boEnableEDMVideoViewLoadProgressBar;
    }

    public void ShowEDMVideoViewLoadProgressBar(boolean boVisible) {
        if (!GetEDMVideoViewLoadProgressBarEnable()) {
            this.m_EDMVideoViewLoadProgressBar.setVisibility(8);
        } else if (boVisible) {
            this.m_EDMVideoViewLoadProgressBar.setVisibility(0);
        } else {
            this.m_EDMVideoViewLoadProgressBar.setVisibility(8);
        }
    }

    void BrowserInternet(String url) {
        try {
            Intent intent = new Intent();
            this.m_Context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
