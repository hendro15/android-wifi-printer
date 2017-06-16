package com.example.sonic.hitiprinter.utility.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.example.sonic.hitiprinter.utility.Verify.ThreadMode;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher.RS_TYPE;
import org.xmlpull.v1.XmlPullParser;

public class ShowMSGDialog {
    public static final int MSG_ERROR_PRINTER = 0;
    public static final int MSG_ERROR_SIMPLE = 1;
    int R_ID_HOD_CLOSE;
    int R_ID_LEAVE_CLOSE;
    int R_ID_LEAVE_MSG;
    int R_ID_LEAVE_NO;
    int R_ID_LEAVE_OK;
    int R_ID_MSG_BOTTOM_TABLE;
    int R_ID_MSG_ERROR_MSG;
    int R_ID_MSG_ERROR_MSG2;
    int R_ID_MSG_ERROR_NO;
    int R_ID_MSG_ERROR_NOT_SHOW;
    int R_ID_MSG_ERROR_OK;
    int R_ID_MSG_ERROR_OK2;
    int R_ID_MSG_ERROR_TABLE_ROW;
    int R_ID_MSG_ERROR_TITLE;
    int R_ID_PROGRESS_BAR;
    int R_ID_QTY_ALL;
    int R_ID_QTY_MSG;
    int R_ID_QTY_NO;
    int R_ID_QTY_OK;
    int R_ID_SEL_CACCEL;
    int R_ID_SEL_CURRENR_SSID;
    int R_ID_SEL_LAST_SSID;
    int R_ID_SEL_OK;
    int R_ID_WAITING_MSG;
    int R_ID_WAITING_RATIO;
    int R_LAYOUT_DIALOG_HOD;
    int R_LAYOUT_DIALOG_LEAVE;
    int R_LAYOUT_DIALOG_WAITING;
    int R_LAYOUT_MSG_ERROR;
    int R_LAYOUT_QUALITY_CROP;
    int R_LAYOUT_SEL_CONN_WIFI;
    int R_STRING_CONN_SEARCHING;
    int R_STRING_CROP_GENERAL;
    int R_STRING_CROP_QUICK;
    int R_STRING_WIFI_SEL_CURRENT;
    int R_STRING_WIFI_SEL_LAST;
    int R_STYLE_DIALOG_MSG;
    CheckBox m_CheckAll;
    View m_ConnectWifiDialogView;
    Context m_Context;
    Dialog m_CropHintDialog;
    View m_CropHintView;
    DialogListener m_DialogListener;
    Dialog m_HODInfoDialog;
    View m_HODInfoView;
    Dialog m_HintDialog;
    QualityListener m_HintListener;
    View m_HintView;
    Dialog m_LeaveDialog;
    TextView m_LeaveMSGTextVIew;
    View m_LeaveView;
    Dialog m_MSGDialog;
    MSGListener m_MSGListener;
    Dialog m_MSGSimpleDialog;
    TextView m_MSGSimpleTextView;
    TextView m_MSGSimpleTitleTextView;
    View m_MSGSimpleView;
    TextView m_MSGTextView;
    TextView m_MSGTitleTextView;
    View m_MSGView;
    Dialog m_PrintDoneDialog;
    TextView m_PrintDoneTextView;
    View m_PrintDoneView;
    TextView m_QtyMSGTextView;
    TextView m_RatioTextView;
    Dialog m_SelConnetWifiDialog;
    View m_WaitingDialogView;
    Dialog m_WaitingHintDialog;
    TextView m_WaitingMSGTextView;
    ProgressBar m_WaitingProgressBar;
    boolean m_bCancable;
    String m_strRatio;

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.14 */
    class AnonymousClass14 implements OnCheckedChangeListener {
        final /* synthetic */ HintDialog val$Type;
        final /* synthetic */ JumpPreferenceKey val$pref;

        AnonymousClass14(JumpPreferenceKey jumpPreferenceKey, HintDialog hintDialog) {
            this.val$pref = jumpPreferenceKey;
            this.val$Type = hintDialog;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            this.val$pref.SetPreference(this.val$Type.toString(), isChecked);
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.15 */
    class AnonymousClass15 implements OnClickListener {
        final /* synthetic */ CheckBox val$m_CheckAll;

        AnonymousClass15(CheckBox checkBox) {
            this.val$m_CheckAll = checkBox;
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_HintDialog.dismiss();
            ShowMSGDialog.this.m_HintListener.CancelClick(this.val$m_CheckAll.isChecked());
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.16 */
    class AnonymousClass16 implements OnClickListener {
        final /* synthetic */ CheckBox val$m_CheckAll;

        AnonymousClass16(CheckBox checkBox) {
            this.val$m_CheckAll = checkBox;
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_HintDialog.dismiss();
            ShowMSGDialog.this.m_HintListener.OKClick(this.val$m_CheckAll.isChecked());
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.17 */
    class AnonymousClass17 implements OnClickListener {
        final /* synthetic */ Dialog val$dialog;
        final /* synthetic */ ICleanListener val$listener;

        AnonymousClass17(Dialog dialog, ICleanListener iCleanListener) {
            this.val$dialog = dialog;
            this.val$listener = iCleanListener;
        }

        public void onClick(View v) {
            this.val$dialog.dismiss();
            this.val$listener.OKclicked();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.18 */
    class AnonymousClass18 implements OnClickListener {
        final /* synthetic */ Dialog val$dialog;
        final /* synthetic */ ICleanListener val$listener;

        AnonymousClass18(Dialog dialog, ICleanListener iCleanListener) {
            this.val$dialog = dialog;
            this.val$listener = iCleanListener;
        }

        public void onClick(View v) {
            this.val$dialog.dismiss();
            this.val$listener.CancelClicked();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.1 */
    class C04601 implements OnClickListener {
        C04601() {
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_SelConnetWifiDialog.dismiss();
            if (ShowMSGDialog.this.m_DialogListener != null) {
                ShowMSGDialog.this.m_DialogListener.CancelConnetion(ThreadMode.SetSSID);
            }
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.2 */
    class C04612 implements OnClickListener {
        final /* synthetic */ RadioButton val$m_SelCurrentRadioButton;
        final /* synthetic */ RadioButton val$m_SelLastRadioButton;
        final /* synthetic */ String val$strCurrentSSID;
        final /* synthetic */ String val$strLastSSID;

        C04612(RadioButton radioButton, String str, RadioButton radioButton2, String str2) {
            this.val$m_SelCurrentRadioButton = radioButton;
            this.val$strCurrentSSID = str;
            this.val$m_SelLastRadioButton = radioButton2;
            this.val$strLastSSID = str2;
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_SelConnetWifiDialog.dismiss();
            if (this.val$m_SelCurrentRadioButton.isChecked() && ShowMSGDialog.this.m_DialogListener != null) {
                ShowMSGDialog.this.m_DialogListener.SetNowConnSSID(this.val$strCurrentSSID);
            }
            if (this.val$m_SelLastRadioButton.isChecked()) {
                ShowMSGDialog.this.ShowWaitingHintDialog(ThreadMode.Non, ShowMSGDialog.this.m_Context.getString(ShowMSGDialog.this.R_STRING_CONN_SEARCHING));
                if (ShowMSGDialog.this.m_DialogListener != null) {
                    ShowMSGDialog.this.m_DialogListener.SetLastConnSSID(this.val$strLastSSID);
                }
            }
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.3 */
    class C04623 implements OnCancelListener {
        final /* synthetic */ ThreadMode val$mode;

        C04623(ThreadMode threadMode) {
            this.val$mode = threadMode;
        }

        public void onCancel(DialogInterface dialog) {
            if (ShowMSGDialog.this.m_bCancable) {
                dialog.dismiss();
                if (ShowMSGDialog.this.m_DialogListener != null) {
                    ShowMSGDialog.this.m_DialogListener.CancelConnetion(this.val$mode);
                }
            }
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.4 */
    class C04634 implements OnClickListener {
        C04634() {
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_HODInfoDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.5 */
    class C04645 implements OnClickListener {
        C04645() {
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_CropHintDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.6 */
    class C04656 implements OnCheckedChangeListener {
        final /* synthetic */ JumpPreferenceKey val$pref;

        C04656(JumpPreferenceKey jumpPreferenceKey) {
            this.val$pref = jumpPreferenceKey;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            this.val$pref.SetPreference(HintDialog.Crop.toString(), isChecked);
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.7 */
    class C04667 implements OnClickListener {
        C04667() {
        }

        public void onClick(View v) {
            ShowMSGDialog.this.m_LeaveDialog.dismiss();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.8 */
    class C04678 implements OnClickListener {
        C04678() {
        }

        public void onClick(View arg0) {
            ShowMSGDialog.this.m_LeaveDialog.dismiss();
            ShowMSGDialog.this.m_DialogListener.LeaveCancel();
        }
    }

    /* renamed from: com.hiti.utility.dialog.ShowMSGDialog.9 */
    class C04689 implements OnClickListener {
        C04689() {
        }

        public void onClick(View arg0) {
            ShowMSGDialog.this.m_LeaveDialog.dismiss();
            ShowMSGDialog.this.m_DialogListener.LeaveConfirm();
        }
    }

    public enum HintDialog {
        Quality,
        Hint,
        Error,
        Crop,
        Leave
    }

    public interface ICleanListener {
        void CancelClicked();

        void OKclicked();

        TextView SetContent(View view);

        Dialog SetDialog();

        Button SetNoButton(View view);

        Button SetOKButton(View view);

        TextView SetTitle(View view);

        View SetView(Dialog dialog);
    }

    public ShowMSGDialog(Context context, boolean bCancel) {
        this.m_Context = null;
        this.m_DialogListener = null;
        this.m_MSGListener = null;
        this.m_HintListener = null;
        this.m_SelConnetWifiDialog = null;
        this.m_WaitingHintDialog = null;
        this.m_HODInfoDialog = null;
        this.m_CropHintDialog = null;
        this.m_LeaveDialog = null;
        this.m_PrintDoneDialog = null;
        this.m_MSGDialog = null;
        this.m_MSGSimpleDialog = null;
        this.m_HintDialog = null;
        this.m_ConnectWifiDialogView = null;
        this.m_WaitingDialogView = null;
        this.m_HODInfoView = null;
        this.m_CropHintView = null;
        this.m_LeaveView = null;
        this.m_PrintDoneView = null;
        this.m_MSGView = null;
        this.m_MSGSimpleView = null;
        this.m_HintView = null;
        this.m_WaitingMSGTextView = null;
        this.m_RatioTextView = null;
        this.m_LeaveMSGTextVIew = null;
        this.m_PrintDoneTextView = null;
        this.m_MSGTextView = null;
        this.m_MSGSimpleTextView = null;
        this.m_MSGTitleTextView = null;
        this.m_MSGSimpleTitleTextView = null;
        this.m_QtyMSGTextView = null;
        this.m_CheckAll = null;
        this.m_WaitingProgressBar = null;
        this.m_strRatio = null;
        this.m_bCancable = false;
        this.R_STYLE_DIALOG_MSG = MSG_ERROR_PRINTER;
        this.R_LAYOUT_SEL_CONN_WIFI = MSG_ERROR_PRINTER;
        this.R_LAYOUT_MSG_ERROR = MSG_ERROR_PRINTER;
        this.R_LAYOUT_DIALOG_WAITING = MSG_ERROR_PRINTER;
        this.R_LAYOUT_DIALOG_HOD = MSG_ERROR_PRINTER;
        this.R_LAYOUT_DIALOG_LEAVE = MSG_ERROR_PRINTER;
        this.R_LAYOUT_QUALITY_CROP = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_TABLE_ROW = MSG_ERROR_PRINTER;
        this.R_ID_SEL_CURRENR_SSID = MSG_ERROR_PRINTER;
        this.R_ID_SEL_LAST_SSID = MSG_ERROR_PRINTER;
        this.R_ID_SEL_CACCEL = MSG_ERROR_PRINTER;
        this.R_ID_SEL_OK = MSG_ERROR_PRINTER;
        this.R_ID_PROGRESS_BAR = MSG_ERROR_PRINTER;
        this.R_ID_WAITING_MSG = MSG_ERROR_PRINTER;
        this.R_ID_WAITING_RATIO = MSG_ERROR_PRINTER;
        this.R_ID_HOD_CLOSE = MSG_ERROR_PRINTER;
        this.R_ID_MSG_BOTTOM_TABLE = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_OK = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_NO = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_OK2 = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_NOT_SHOW = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_MSG = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_MSG2 = MSG_ERROR_PRINTER;
        this.R_ID_MSG_ERROR_TITLE = MSG_ERROR_PRINTER;
        this.R_ID_QTY_MSG = MSG_ERROR_PRINTER;
        this.R_ID_QTY_OK = MSG_ERROR_PRINTER;
        this.R_ID_QTY_NO = MSG_ERROR_PRINTER;
        this.R_ID_QTY_ALL = MSG_ERROR_PRINTER;
        this.R_ID_LEAVE_NO = MSG_ERROR_PRINTER;
        this.R_ID_LEAVE_OK = MSG_ERROR_PRINTER;
        this.R_ID_LEAVE_CLOSE = MSG_ERROR_PRINTER;
        this.R_ID_LEAVE_MSG = MSG_ERROR_PRINTER;
        this.R_STRING_WIFI_SEL_CURRENT = MSG_ERROR_PRINTER;
        this.R_STRING_WIFI_SEL_LAST = MSG_ERROR_PRINTER;
        this.R_STRING_CONN_SEARCHING = MSG_ERROR_PRINTER;
        this.R_STRING_CROP_QUICK = MSG_ERROR_PRINTER;
        this.R_STRING_CROP_GENERAL = MSG_ERROR_PRINTER;
        this.m_Context = context;
        this.m_bCancable = bCancel;
        GetResourceID();
    }

    private void GetResourceID() {
        this.R_STYLE_DIALOG_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STYLE, "Dialog_MSG");
        this.R_LAYOUT_SEL_CONN_WIFI = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_sel_conn_wifi");
        this.R_LAYOUT_MSG_ERROR = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_msg_error");
        this.R_LAYOUT_DIALOG_HOD = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_hodinfo");
        this.R_LAYOUT_DIALOG_LEAVE = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_printview_leave");
        this.R_LAYOUT_QUALITY_CROP = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_qality_change");
        this.R_LAYOUT_DIALOG_WAITING = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_waiting");
        this.R_ID_MSG_ERROR_TABLE_ROW = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorIconTableRow");
        this.R_ID_SEL_CURRENR_SSID = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_SelCurrentRadioButton");
        this.R_ID_SEL_LAST_SSID = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_SelLastRadioButton");
        this.R_ID_SEL_CACCEL = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_SelConnCancelButton");
        this.R_ID_SEL_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_SelConnOKButton");
        this.R_ID_PROGRESS_BAR = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_WaitingProgressBar");
        this.R_ID_WAITING_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_WaitingMSGTextView");
        this.R_ID_WAITING_RATIO = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_RatioTextView");
        this.R_ID_HOD_CLOSE = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_HODcloseImageButton");
        this.R_ID_MSG_BOTTOM_TABLE = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorBottomTableRow");
        this.R_ID_MSG_ERROR_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorOKImageButton");
        this.R_ID_MSG_ERROR_NO = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorNoImageButton");
        this.R_ID_MSG_ERROR_OK2 = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorOK2ImageButton");
        this.R_ID_MSG_ERROR_NOT_SHOW = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorNotShowAgainCheckBox");
        this.R_ID_MSG_ERROR_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorMSGTextView");
        this.R_ID_MSG_ERROR_MSG2 = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorMSG2TextView");
        this.R_ID_MSG_ERROR_TITLE = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_MsgErrorTitleTextView");
        this.R_ID_QTY_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_QualityMSGTextView");
        this.R_ID_QTY_NO = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_QtyCancelButton");
        this.R_ID_QTY_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_QtyOKButton");
        this.R_ID_QTY_ALL = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_QtyApplyAllCheckBox");
        this.R_ID_LEAVE_NO = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_LeaveNoImageButton");
        this.R_ID_LEAVE_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_LeaveYesImageButton");
        this.R_ID_LEAVE_CLOSE = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_LeaveCloseImageButton");
        this.R_ID_LEAVE_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_LeaveMSG1TextView");
        this.R_STRING_WIFI_SEL_CURRENT = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "DIALOG_CONN_WIFI_SEL_CURRENT");
        this.R_STRING_WIFI_SEL_LAST = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "DIALOG_CONN_WIFI_SEL_LAST");
        this.R_STRING_CONN_SEARCHING = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "CONN_SEARCHING");
        this.R_STRING_CROP_QUICK = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WARRING_CROP_QUICK");
        this.R_STRING_CROP_GENERAL = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WARRING_CROP_GENERAL");
    }

    public void SetDialogListener(DialogListener listener) {
        this.m_DialogListener = listener;
    }

    public void CreateConnectWifiHintDialog(String strCurrentSSID, String strLastSSID) {
        if (this.m_SelConnetWifiDialog != null) {
            this.m_SelConnetWifiDialog.dismiss();
            this.m_SelConnetWifiDialog = null;
        }
        if (this.m_SelConnetWifiDialog == null) {
            this.m_SelConnetWifiDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_SelConnetWifiDialog.setCanceledOnTouchOutside(false);
            this.m_SelConnetWifiDialog.setCancelable(false);
            this.m_ConnectWifiDialogView = this.m_SelConnetWifiDialog.getLayoutInflater().inflate(this.R_LAYOUT_SEL_CONN_WIFI, null);
            RadioButton m_SelCurrentRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(this.R_ID_SEL_CURRENR_SSID);
            RadioButton m_SelLastRadioButton = (RadioButton) this.m_ConnectWifiDialogView.findViewById(this.R_ID_SEL_LAST_SSID);
            ImageButton m_OKButton = (ImageButton) this.m_ConnectWifiDialogView.findViewById(this.R_ID_SEL_OK);
            ((ImageButton) this.m_ConnectWifiDialogView.findViewById(this.R_ID_SEL_CACCEL)).setOnClickListener(new C04601());
            m_OKButton.setOnClickListener(new C04612(m_SelCurrentRadioButton, strCurrentSSID, m_SelLastRadioButton, strLastSSID));
            this.m_SelConnetWifiDialog.setContentView(this.m_ConnectWifiDialogView);
            m_SelCurrentRadioButton.setText(this.m_Context.getString(this.R_STRING_WIFI_SEL_CURRENT) + strCurrentSSID);
            m_SelLastRadioButton.setText(this.m_Context.getString(this.R_STRING_WIFI_SEL_LAST) + strLastSSID);
        }
        this.m_SelConnetWifiDialog.show();
    }

    private String GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) this.m_Context.getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        return CleanSSID(strSSID);
    }

    String CleanSSID(String strSSID) {
        if (strSSID.contains("\"")) {
            return strSSID.split("\"")[MSG_ERROR_SIMPLE];
        }
        return strSSID;
    }

    public void SetRatio(String strRatio) {
        this.m_strRatio = strRatio;
    }

    public void ShowWaitingHintDialog(ThreadMode mode, String strMSG) {
        if (this.m_WaitingHintDialog == null) {
            this.m_WaitingHintDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_WaitingDialogView = this.m_WaitingHintDialog.getLayoutInflater().inflate(this.R_LAYOUT_DIALOG_WAITING, null);
            this.m_WaitingProgressBar = (ProgressBar) this.m_WaitingDialogView.findViewById(this.R_ID_PROGRESS_BAR);
            this.m_WaitingHintDialog.setCanceledOnTouchOutside(this.m_bCancable);
            this.m_WaitingHintDialog.setCancelable(this.m_bCancable);
            this.m_WaitingProgressBar.setVisibility(MSG_ERROR_PRINTER);
            this.m_WaitingProgressBar.setIndeterminate(true);
            this.m_WaitingMSGTextView = (TextView) this.m_WaitingDialogView.findViewById(this.R_ID_WAITING_MSG);
            this.m_RatioTextView = (TextView) this.m_WaitingDialogView.findViewById(this.R_ID_WAITING_RATIO);
            this.m_WaitingHintDialog.setContentView(this.m_WaitingDialogView);
            this.m_WaitingHintDialog.getWindow().addFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
        }
        this.m_WaitingHintDialog.setOnCancelListener(new C04623(mode));
        this.m_WaitingMSGTextView.setText(strMSG);
        if (this.m_strRatio != null) {
            this.m_RatioTextView.setVisibility(MSG_ERROR_PRINTER);
            this.m_RatioTextView.setText(this.m_strRatio);
            this.m_strRatio = null;
        } else {
            this.m_RatioTextView.setVisibility(8);
        }
        this.m_WaitingHintDialog.show();
    }

    public boolean IsWaitingDialogShowing() {
        return this.m_WaitingHintDialog.isShowing();
    }

    public void StopWaitingDialog() {
        if (this.m_WaitingHintDialog != null) {
            this.m_WaitingHintDialog.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            this.m_WaitingHintDialog.dismiss();
        }
    }

    public void StopMSGDialog() {
        if (this.m_MSGDialog != null) {
            this.m_MSGDialog.cancel();
        }
        if (this.m_MSGSimpleDialog != null) {
            this.m_MSGSimpleDialog.cancel();
        }
        if (this.m_WaitingHintDialog != null) {
            this.m_WaitingHintDialog.getWindow().clearFlags(TransportMediator.FLAG_KEY_MEDIA_NEXT);
            this.m_WaitingHintDialog.cancel();
        }
        if (this.m_HintDialog != null) {
            this.m_HintDialog.cancel();
        }
    }

    public void ShowHODInfoDialog() {
        if (this.m_HODInfoDialog == null) {
            this.m_HODInfoDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_HODInfoView = this.m_HODInfoDialog.getLayoutInflater().inflate(this.R_LAYOUT_DIALOG_HOD, null);
            ((ImageButton) this.m_HODInfoView.findViewById(this.R_ID_HOD_CLOSE)).setOnClickListener(new C04634());
            this.m_HODInfoDialog.setContentView(this.m_HODInfoView);
        }
        this.m_HODInfoDialog.show();
    }

    public boolean IsNeedHintAgain(HintDialog type, int iPathRoute) {
        return !new JumpPreferenceKey(this.m_Context, String.valueOf(iPathRoute)).GetStatePreference(type.toString());
    }

    public void ShowCropWaringDialog(int iPathRoute, String title) {
        String strMSG = XmlPullParser.NO_NAMESPACE;
        if (iPathRoute == ControllerState.PLAY_PHOTO) {
            strMSG = this.m_Context.getString(this.R_STRING_CROP_QUICK);
        } else if (iPathRoute == ControllerState.NO_PLAY_ITEM) {
            strMSG = this.m_Context.getString(this.R_STRING_CROP_GENERAL);
        } else {
            return;
        }
        if (this.m_CropHintDialog == null) {
            this.m_CropHintDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_MSGView = this.m_CropHintDialog.getLayoutInflater().inflate(this.R_LAYOUT_MSG_ERROR, null);
            this.m_CropHintDialog.setCancelable(false);
            this.m_CropHintDialog.setCanceledOnTouchOutside(false);
            JumpPreferenceKey pref = new JumpPreferenceKey(this.m_Context, String.valueOf(iPathRoute));
            TableRow m_BottomTableRow = (TableRow) this.m_MSGView.findViewById(this.R_ID_MSG_BOTTOM_TABLE);
            ImageButton m_OKImageButton = (ImageButton) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_OK2);
            CheckBox m_NotAgainCheckBox = (CheckBox) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_NOT_SHOW);
            this.m_MSGTextView = (TextView) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_MSG);
            this.m_MSGTitleTextView = (TextView) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_TITLE);
            m_BottomTableRow.setVisibility(4);
            m_OKImageButton.setVisibility(MSG_ERROR_PRINTER);
            if (strMSG.contains("#")) {
                String[] strMessage = strMSG.split("#");
                this.m_MSGTextView.setText(strMessage[MSG_ERROR_PRINTER]);
                TableRow mMessageTableRow = (TableRow) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_TABLE_ROW);
                ((TextView) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_MSG2)).setText(strMessage[MSG_ERROR_SIMPLE]);
                mMessageTableRow.setVisibility(MSG_ERROR_PRINTER);
            } else {
                this.m_MSGTextView.setText(strMSG);
            }
            this.m_MSGTitleTextView.setText(title);
            m_OKImageButton.setOnClickListener(new C04645());
            if (IsNeedHintAgain(HintDialog.Crop, iPathRoute)) {
                m_NotAgainCheckBox.setVisibility(MSG_ERROR_PRINTER);
                m_NotAgainCheckBox.setOnCheckedChangeListener(new C04656(pref));
            } else {
                m_NotAgainCheckBox.setVisibility(8);
            }
            this.m_CropHintDialog.setContentView(this.m_MSGView);
        }
        this.m_CropHintDialog.show();
    }

    public void ShowLeaveDialog(String strMSG) {
        if (this.m_LeaveDialog == null) {
            this.m_LeaveDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_LeaveView = this.m_LeaveDialog.getLayoutInflater().inflate(this.R_LAYOUT_DIALOG_LEAVE, null);
            this.m_LeaveDialog.setCancelable(false);
            this.m_LeaveDialog.setCanceledOnTouchOutside(false);
            ImageButton m_NoImageButton = (ImageButton) this.m_LeaveView.findViewById(this.R_ID_LEAVE_NO);
            ImageButton m_YesImageButton = (ImageButton) this.m_LeaveView.findViewById(this.R_ID_LEAVE_OK);
            ImageButton m_CloseImageButton = (ImageButton) this.m_LeaveView.findViewById(this.R_ID_LEAVE_CLOSE);
            this.m_LeaveMSGTextVIew = (TextView) this.m_LeaveView.findViewById(this.R_ID_LEAVE_MSG);
            m_CloseImageButton.setOnClickListener(new C04667());
            m_NoImageButton.setOnClickListener(new C04678());
            m_YesImageButton.setOnClickListener(new C04689());
            this.m_LeaveDialog.setContentView(this.m_LeaveView);
        }
        this.m_LeaveMSGTextVIew.setText(String.valueOf(strMSG));
        this.m_LeaveDialog.show();
    }

    public void ShowPrintDoneDialog(String strMSG) {
        if (this.m_PrintDoneDialog == null) {
            this.m_PrintDoneDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_PrintDoneView = this.m_PrintDoneDialog.getLayoutInflater().inflate(this.R_LAYOUT_DIALOG_LEAVE, null);
            this.m_PrintDoneDialog.setCancelable(false);
            this.m_PrintDoneDialog.setCanceledOnTouchOutside(false);
            ImageButton m_NoImageButton = (ImageButton) this.m_PrintDoneView.findViewById(this.R_ID_LEAVE_NO);
            ImageButton m_YesImageButton = (ImageButton) this.m_PrintDoneView.findViewById(this.R_ID_LEAVE_OK);
            ImageButton m_CloseImageButton = (ImageButton) this.m_PrintDoneView.findViewById(this.R_ID_LEAVE_CLOSE);
            this.m_PrintDoneTextView = (TextView) this.m_PrintDoneView.findViewById(this.R_ID_LEAVE_MSG);
            m_CloseImageButton.setVisibility(8);
            m_NoImageButton.setVisibility(8);
            m_YesImageButton.setEnabled(true);
            m_YesImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ShowMSGDialog.this.m_PrintDoneDialog.dismiss();
                    ShowMSGDialog.this.m_DialogListener.LeaveConfirm();
                }
            });
            this.m_PrintDoneDialog.setContentView(this.m_PrintDoneView);
        }
        this.m_PrintDoneTextView.setText(String.valueOf(strMSG));
        this.m_PrintDoneDialog.show();
    }

    public boolean IsLeavDialogShowing() {
        if (this.m_LeaveDialog == null || !this.m_LeaveDialog.isShowing()) {
            return false;
        }
        return true;
    }

    public void CloseLeaveDialog() {
        if (this.m_LeaveDialog != null) {
            this.m_LeaveDialog.cancel();
        }
    }

    public void SetMSGListener(MSGListener listener) {
        this.m_MSGListener = listener;
    }

    public void ShowMessageDialog(String strMSG, String strTitle) {
        if (this.m_MSGDialog == null) {
            this.m_MSGDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_MSGView = this.m_MSGDialog.getLayoutInflater().inflate(this.R_LAYOUT_MSG_ERROR, null);
            this.m_MSGDialog.setCancelable(false);
            this.m_MSGDialog.setCanceledOnTouchOutside(false);
            TableRow m_BottomTableRow = (TableRow) this.m_MSGView.findViewById(this.R_ID_MSG_BOTTOM_TABLE);
            ImageButton m_NoImageButton = (ImageButton) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_NO);
            ImageButton m_OKImageButton = (ImageButton) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_OK);
            ImageButton m_OK2ImageButton = (ImageButton) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_OK2);
            CheckBox m_NotAgainCheckBox = (CheckBox) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_NOT_SHOW);
            this.m_MSGTextView = (TextView) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_MSG);
            this.m_MSGTitleTextView = (TextView) this.m_MSGView.findViewById(this.R_ID_MSG_ERROR_TITLE);
            m_BottomTableRow.setVisibility(MSG_ERROR_PRINTER);
            m_OK2ImageButton.setVisibility(8);
            m_NotAgainCheckBox.setVisibility(8);
            m_NoImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ShowMSGDialog.this.m_MSGDialog.dismiss();
                    ShowMSGDialog.this.m_MSGListener.CancelClick();
                }
            });
            m_OKImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ShowMSGDialog.this.m_MSGDialog.dismiss();
                    ShowMSGDialog.this.m_MSGListener.OKClick();
                }
            });
            this.m_MSGDialog.setContentView(this.m_MSGView);
        }
        if (strTitle == null || strTitle.isEmpty()) {
            this.m_MSGTitleTextView.setVisibility(8);
        } else {
            this.m_MSGTitleTextView.setVisibility(MSG_ERROR_PRINTER);
        }
        this.m_MSGTitleTextView.setText(strTitle);
        this.m_MSGTextView.setText(String.valueOf(strMSG));
        this.m_MSGDialog.show();
    }

    public void ShowSimpleMSGDialog(String strMSG, String strTitle) {
        if (this.m_MSGSimpleDialog == null) {
            this.m_MSGSimpleDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_MSGSimpleView = this.m_MSGSimpleDialog.getLayoutInflater().inflate(this.R_LAYOUT_MSG_ERROR, null);
            this.m_MSGSimpleDialog.setCancelable(false);
            this.m_MSGSimpleDialog.setCanceledOnTouchOutside(false);
            CheckBox m_NotAgainCheckBox = (CheckBox) this.m_MSGSimpleView.findViewById(this.R_ID_MSG_ERROR_NOT_SHOW);
            TableRow m_BottomTableRow = (TableRow) this.m_MSGSimpleView.findViewById(this.R_ID_MSG_BOTTOM_TABLE);
            ImageButton m_OKImageButton = (ImageButton) this.m_MSGSimpleView.findViewById(this.R_ID_MSG_ERROR_OK2);
            this.m_MSGSimpleTitleTextView = (TextView) this.m_MSGSimpleView.findViewById(this.R_ID_MSG_ERROR_TITLE);
            this.m_MSGSimpleTextView = (TextView) this.m_MSGSimpleView.findViewById(this.R_ID_MSG_ERROR_MSG);
            m_NotAgainCheckBox.setVisibility(8);
            m_BottomTableRow.setVisibility(4);
            m_OKImageButton.setVisibility(MSG_ERROR_PRINTER);
            m_OKImageButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ShowMSGDialog.this.m_MSGSimpleDialog.dismiss();
                    if (ShowMSGDialog.this.m_MSGListener != null) {
                        ShowMSGDialog.this.m_MSGListener.OKClick();
                    }
                }
            });
            this.m_MSGSimpleDialog.setContentView(this.m_MSGSimpleView);
        }
        this.m_MSGSimpleTitleTextView.setText(String.valueOf(strTitle));
        this.m_MSGSimpleTextView.setText(String.valueOf(strMSG));
        this.m_MSGSimpleDialog.show();
    }

    public void SetHintListener(QualityListener listner) {
        this.m_HintListener = listner;
    }

    public void ShowHintDialog(String strHint, HintDialog Type, int iPathRoute) {
        if (this.m_HintDialog == null) {
            this.m_HintDialog = new Dialog(this.m_Context, this.R_STYLE_DIALOG_MSG);
            this.m_HintView = this.m_HintDialog.getLayoutInflater().inflate(this.R_LAYOUT_QUALITY_CROP, null);
            this.m_HintDialog.setCanceledOnTouchOutside(false);
            this.m_HintDialog.setCancelable(false);
            JumpPreferenceKey pref = new JumpPreferenceKey(this.m_Context, String.valueOf(iPathRoute));
            this.m_QtyMSGTextView = (TextView) this.m_HintView.findViewById(this.R_ID_QTY_MSG);
            ImageButton m_CancelButton = (ImageButton) this.m_HintView.findViewById(this.R_ID_QTY_NO);
            ImageButton m_OKButton = (ImageButton) this.m_HintView.findViewById(this.R_ID_QTY_OK);
            CheckBox m_CheckAll = (CheckBox) this.m_HintView.findViewById(this.R_ID_QTY_ALL);
            m_CheckAll.setVisibility(MSG_ERROR_PRINTER);
            m_CheckAll.setOnCheckedChangeListener(new AnonymousClass14(pref, Type));
            m_CheckAll.setChecked(false);
            m_CancelButton.setOnClickListener(new AnonymousClass15(m_CheckAll));
            m_OKButton.setOnClickListener(new AnonymousClass16(m_CheckAll));
            this.m_HintDialog.setContentView(this.m_HintView);
        }
        this.m_QtyMSGTextView.setText(strHint);
        this.m_HintDialog.show();
    }

    public Dialog SetCleanHintDialog(String strTitle, String strMSG, ICleanListener listener) {
        Dialog dialog = listener.SetDialog();
        View view = listener.SetView(dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Button m_NoButton = listener.SetNoButton(view);
        Button m_OKButton = listener.SetOKButton(view);
        TextView mTitleTextView = listener.SetTitle(view);
        TextView mContentTextView = listener.SetContent(view);
        mTitleTextView.setText(strTitle);
        mContentTextView.setText(strMSG);
        m_OKButton.setOnClickListener(new AnonymousClass17(dialog, listener));
        m_NoButton.setOnClickListener(new AnonymousClass18(dialog, listener));
        dialog.setContentView(view);
        return dialog;
    }
}
