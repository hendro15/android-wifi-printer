package com.example.sonic.hitiprinter.utility.resource;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher.RS_TYPE;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class ResourceId {
    public int R_COLOR_EDIT_DRAW_VIEW_BACKGROUND;
    public int R_COLOR_GS_COLOR;
    public int R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR;
    public int R_DRAWABLE_p310w;
    public int R_DRAWABLE_p520l;
    public int R_DRAWABLE_p750l;
    public int R_DRAWABLE_printer;
    public int R_ID_m_NfcDialogTextMessage;
    public int R_ID_m_NfcOKButton;
    public int R_ID_m_PrinterImageView;
    public int R_ID_m_PrinterListRelativeLayout;
    public int R_ID_m_PrinterListView;
    public int R_ID_m_PrinterTextView;
    public int R_ID_m_PwdCancelButton;
    public int R_ID_m_PwdOKButton;
    public int R_ID_m_PwdPasswordEditText;
    public int R_ID_m_ScanButton;
    public int R_ID_m_ScanProgressBar;
    public int R_LAYOUT_NFC_WRITE_DIALOG;
    public int R_LAYOUT_dialog_printer_list;
    public int R_LAYOUT_dialog_pwd_check;
    public int R_LAYOUT_item_printer_list;
    public int R_STRING_CANCEL;
    public int R_STRING_CONN_SEARCHING;
    public int R_STRING_CREATE_BITMAP_OUT_OF_MEMORY;
    public int R_STRING_DATA_DELIVERY;
    public int R_STRING_ERROR_MODEL;
    public int R_STRING_ERROR_PRINTER_0001;
    public int R_STRING_ERROR_PRINTER_CHECKED;
    public int R_STRING_ERROR_TITLT;
    public int R_STRING_MSG_PWD_WRONG;
    public int R_STRING_NFC_APPROACH_TAG;
    public int R_STRING_NFC_IS_WRITING;
    public int R_STRING_NFC_WRITE_FAIL;
    public int R_STRING_NFC_WRITE_SUCCESS;
    public int R_STRING_NOPHOTO;
    public int R_STRING_OK;
    public int R_STRING_OUTPUT_HEIGHT_310w_4x6;
    public int R_STRING_OUTPUT_WIDTH_310w_4x6;
    public int R_STRING_PLEASE_SELECT_NETWORK;
    public int R_STRING_PLEASE_WAIT;
    public int R_STRING_PRINTOUT_4x6_H;
    public int R_STRING_PRINTOUT_4x6_P310W_H;
    public int R_STRING_PRINTOUT_4x6_P310W_W;
    public int R_STRING_PRINTOUT_4x6_W;
    public int R_STRING_PRINTOUT_5x7_H;
    public int R_STRING_PRINTOUT_5x7_W;
    public int R_STRING_PRINTOUT_6x6_H;
    public int R_STRING_PRINTOUT_6x6_W;
    public int R_STRING_PRINTOUT_6x8_H;
    public int R_STRING_PRINTOUT_6x8_W;
    public int R_STRING_PRINT_DONE;
    public int R_STRING_PRINT_OUT_2x3;
    public int R_STRING_PRINT_OUT_4x6;
    public int R_STRING_PRINT_OUT_5x7;
    public int R_STRING_PRINT_OUT_6x6;
    public int R_STRING_PRINT_OUT_6x8;
    public int R_STRING_PRINT_OUT_6x8_2up;
    public int R_STRING_QTY_TO_LARGE;
    public int R_STRING_SCALE_TO_LARGE;
    public int R_STRING_SIZE_NOT_MATCH;
    public int R_STRING_SIZE_TO_SMALL;
    public int R_STRING_UNABLE_TO_CONNECT_TO_PRINTER;
    public int R_STYLE_Dialog_MSG;
    Context m_Context;

    /* renamed from: com.hiti.utility.resource.ResourceId.1 */
    static /* synthetic */ class C04821 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$resource$ResourceId$Page;

        static {
            $SwitchMap$com$hiti$utility$resource$ResourceId$Page = new int[Page.values().length];
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.MakeEditPhoto.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.MobileUtility.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.MDNS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.PwdCheck.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.NfcWrite.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceId$Page[Page.CheckWifi.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public enum Page {
        MakeEditPhoto,
        MobileUtility,
        MDNS,
        PwdCheck,
        NfcWrite,
        CheckWifi
    }

    public ResourceId(Context context, Page iPage) {
        this.R_COLOR_EDIT_DRAW_VIEW_BACKGROUND = 0;
        this.R_STRING_PRINTOUT_4x6_P310W_W = 0;
        this.R_STRING_PRINTOUT_4x6_P310W_H = 0;
        this.R_STRING_PRINTOUT_4x6_W = 0;
        this.R_STRING_PRINTOUT_4x6_H = 0;
        this.R_STRING_PRINTOUT_5x7_W = 0;
        this.R_STRING_PRINTOUT_5x7_H = 0;
        this.R_STRING_PRINTOUT_6x8_W = 0;
        this.R_STRING_PRINTOUT_6x8_H = 0;
        this.R_STRING_PRINTOUT_6x6_W = 0;
        this.R_STRING_PRINTOUT_6x6_H = 0;
        this.R_STRING_OUTPUT_HEIGHT_310w_4x6 = 0;
        this.R_STRING_OUTPUT_WIDTH_310w_4x6 = 0;
        this.R_STRING_PRINT_OUT_2x3 = 0;
        this.R_STRING_PRINT_OUT_4x6 = 0;
        this.R_STRING_PRINT_OUT_5x7 = 0;
        this.R_STRING_PRINT_OUT_6x6 = 0;
        this.R_STRING_PRINT_OUT_6x8 = 0;
        this.R_STRING_PRINT_OUT_6x8_2up = 0;
        this.R_STRING_ERROR_PRINTER_CHECKED = 0;
        this.R_STRING_DATA_DELIVERY = 0;
        this.R_STRING_PRINT_DONE = 0;
        this.R_STRING_SIZE_TO_SMALL = 0;
        this.R_STRING_SCALE_TO_LARGE = 0;
        this.R_STRING_QTY_TO_LARGE = 0;
        this.R_STRING_ERROR_MODEL = 0;
        this.R_STRING_NOPHOTO = 0;
        this.R_STRING_SIZE_NOT_MATCH = 0;
        this.R_STRING_ERROR_PRINTER_0001 = 0;
        this.R_STRING_CREATE_BITMAP_OUT_OF_MEMORY = 0;
        this.R_STRING_ERROR_TITLT = 0;
        this.R_STRING_PLEASE_WAIT = 0;
        this.R_LAYOUT_dialog_printer_list = 0;
        this.R_LAYOUT_item_printer_list = 0;
        this.R_LAYOUT_dialog_pwd_check = 0;
        this.R_ID_m_PrinterListRelativeLayout = 0;
        this.R_ID_m_ScanButton = 0;
        this.R_ID_m_ScanProgressBar = 0;
        this.R_ID_m_PrinterImageView = 0;
        this.R_ID_m_PrinterTextView = 0;
        this.R_ID_m_PrinterListView = 0;
        this.R_ID_m_PwdPasswordEditText = 0;
        this.R_ID_m_PwdOKButton = 0;
        this.R_ID_m_PwdCancelButton = 0;
        this.R_DRAWABLE_p310w = 0;
        this.R_DRAWABLE_p520l = 0;
        this.R_DRAWABLE_p750l = 0;
        this.R_DRAWABLE_printer = 0;
        this.R_STYLE_Dialog_MSG = 0;
        this.R_STRING_OK = 0;
        this.R_STRING_MSG_PWD_WRONG = 0;
        this.R_STRING_CANCEL = 0;
        this.R_LAYOUT_NFC_WRITE_DIALOG = 0;
        this.R_ID_m_NfcDialogTextMessage = 0;
        this.R_ID_m_NfcOKButton = 0;
        this.R_STRING_NFC_WRITE_SUCCESS = 0;
        this.R_STRING_NFC_IS_WRITING = 0;
        this.R_STRING_NFC_WRITE_FAIL = 0;
        this.R_STRING_NFC_APPROACH_TAG = 0;
        this.R_COLOR_GS_COLOR = 0;
        this.R_COLOR_REPLACE_EDIT_PHOTO_MASK_COLOR = 0;
        this.R_STRING_PLEASE_SELECT_NETWORK = 0;
        this.R_STRING_UNABLE_TO_CONNECT_TO_PRINTER = 0;
        this.R_STRING_CONN_SEARCHING = 0;
        this.m_Context = null;
        this.m_Context = context;
        GetResourceID(iPage);
    }

    private void GetResourceID(Page iPage) {
        switch (C04821.$SwitchMap$com$hiti$utility$resource$ResourceId$Page[iPage.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                this.R_COLOR_EDIT_DRAW_VIEW_BACKGROUND = ResourceSearcher.getId(this.m_Context, RS_TYPE.COLOR, "WHITE");
                this.R_STRING_CREATE_BITMAP_OUT_OF_MEMORY = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "CREATE_BITMAP_OUT_OF_MEMORY");
                this.R_STRING_ERROR_TITLT = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR");
                this.R_STRING_PLEASE_WAIT = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PLEASE_WAIT");
                break;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                break;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                this.R_LAYOUT_dialog_printer_list = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_printer_list");
                this.R_LAYOUT_item_printer_list = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "item_printer_list");
                this.R_ID_m_PrinterListRelativeLayout = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PrinterListRelativeLayout");
                this.R_ID_m_ScanButton = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_ScanButton");
                this.R_ID_m_ScanProgressBar = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_ScanProgressBar");
                this.R_ID_m_PrinterImageView = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PrinterImageView");
                this.R_ID_m_PrinterTextView = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PrinterTextView");
                this.R_ID_m_PrinterListView = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PrinterListView");
                this.R_DRAWABLE_p310w = ResourceSearcher.getId(this.m_Context, RS_TYPE.DRAWABLE, "p310w");
                this.R_DRAWABLE_p520l = ResourceSearcher.getId(this.m_Context, RS_TYPE.DRAWABLE, "p520l");
                this.R_DRAWABLE_p750l = ResourceSearcher.getId(this.m_Context, RS_TYPE.DRAWABLE, "p750l");
                this.R_DRAWABLE_printer = ResourceSearcher.getId(this.m_Context, RS_TYPE.DRAWABLE, "printer");
                this.R_STRING_ERROR_TITLT = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR");
                this.R_STYLE_Dialog_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STYLE, "Dialog_MSG");
                this.R_STRING_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "OK");
                return;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                this.R_LAYOUT_dialog_pwd_check = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_pwd_check");
                this.R_STYLE_Dialog_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STYLE, "Dialog_MSG");
                this.R_STRING_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "OK");
                this.R_STRING_ERROR_TITLT = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR");
                this.R_STRING_MSG_PWD_WRONG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "MSG_PWD_WRONG");
                this.R_ID_m_PwdPasswordEditText = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PwdPasswordEditText");
                this.R_ID_m_PwdOKButton = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PwdOKButton");
                this.R_ID_m_PwdCancelButton = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_PwdCancelButton");
                return;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                this.R_STYLE_Dialog_MSG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STYLE, "Dialog_MSG");
                this.R_LAYOUT_NFC_WRITE_DIALOG = ResourceSearcher.getId(this.m_Context, RS_TYPE.LAYOUT, "dialog_write_nfc");
                this.R_ID_m_NfcDialogTextMessage = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_NfcDialogTextMessage");
                this.R_ID_m_NfcOKButton = ResourceSearcher.getId(this.m_Context, RS_TYPE.ID, "m_NfcOKButton");
                this.R_STRING_OK = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "OK");
                this.R_STRING_CANCEL = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "CANCEL");
                this.R_STRING_NFC_WRITE_SUCCESS = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "NFC_WRITE_SUCCESS");
                this.R_STRING_NFC_IS_WRITING = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "NFC_IS_WRITING");
                this.R_STRING_NFC_WRITE_FAIL = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "NFC_WRITE_FAIL");
                this.R_STRING_NFC_APPROACH_TAG = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "NFC_APPROACH_TAG");
                return;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                this.R_STRING_PLEASE_SELECT_NETWORK = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PLEASE_SELECT_NETWORK");
                this.R_STRING_UNABLE_TO_CONNECT_TO_PRINTER = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "UNABLE_TO_CONNECT_TO_PRINTER");
                this.R_STRING_CONN_SEARCHING = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "CONN_SEARCHING");
                return;
            default:
                return;
        }
        this.R_STRING_OUTPUT_HEIGHT_310w_4x6 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "OUTPUT_HEIGHT_310w_4x6");
        this.R_STRING_OUTPUT_WIDTH_310w_4x6 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "OUTPUT_WIDTH_310w_4x6");
        this.R_STRING_PRINTOUT_4x6_P310W_W = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WIDTH_310w_4x6");
        this.R_STRING_PRINTOUT_4x6_P310W_H = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "HEIGHT_310w_4x6");
        this.R_STRING_PRINTOUT_4x6_W = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WIDTH_4x6");
        this.R_STRING_PRINTOUT_4x6_H = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "HEIGHT_4x6");
        this.R_STRING_PRINTOUT_5x7_W = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WIDTH_5x7");
        this.R_STRING_PRINTOUT_5x7_H = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "HEIGHT_5x7");
        this.R_STRING_PRINTOUT_6x8_W = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WIDTH_6x8");
        this.R_STRING_PRINTOUT_6x8_H = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "HEIGHT_6x8");
        this.R_STRING_PRINTOUT_6x6_W = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "WIDTH_6x6");
        this.R_STRING_PRINTOUT_6x6_H = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "HEIGHT_6x6");
        this.R_STRING_PRINT_OUT_2x3 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_2x3");
        this.R_STRING_PRINT_OUT_4x6 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_4x6");
        this.R_STRING_PRINT_OUT_5x7 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_5x7");
        this.R_STRING_PRINT_OUT_6x6 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_6x6");
        this.R_STRING_PRINT_OUT_6x8 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_6x8");
        this.R_STRING_PRINT_OUT_6x8_2up = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_OUT_6x8_2up");
        this.R_STRING_ERROR_PRINTER_CHECKED = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR_PRINTER_CHECKED");
        this.R_STRING_DATA_DELIVERY = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "DATA_DELIVERY");
        this.R_STRING_PRINT_DONE = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "PRINT_DONE");
        this.R_STRING_SIZE_TO_SMALL = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "SIZE_TO_SMALL");
        this.R_STRING_SCALE_TO_LARGE = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "SCALE_TO_LARGE");
        this.R_STRING_QTY_TO_LARGE = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "QTY_TO_LARGE");
        this.R_STRING_ERROR_MODEL = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR_MODEL");
        this.R_STRING_NOPHOTO = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "NOPHOTO");
        this.R_STRING_SIZE_NOT_MATCH = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "SIZE_NOT_MATCH");
        this.R_STRING_ERROR_PRINTER_0001 = ResourceSearcher.getId(this.m_Context, RS_TYPE.STRING, "ERROR_PRINTER_0001");
    }
}
