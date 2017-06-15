package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.example.sonic.hitiprinter.ui.hitiwebview.HITI_WEB_VIEW_STATUS;
import com.example.sonic.hitiprinter.utility.LogManager;

public abstract class BaseGlobalVariable implements BaseGlobalVariableInterface {
    public static final int GV_APPLY_ADD_DATE_NO = 0;
    public static final int GV_APPLY_ADD_DATE_YES = 1;
    public static final int GV_APPLY_BLE_NO = 0;
    public static final int GV_APPLY_BLE_YES = 1;
    public static final int GV_APPLY_GCM_NO = 0;
    public static final int GV_APPLY_GCM_YES = 1;
    public static final int GV_BLE_NOTIFIY_NO = 0;
    public static final int GV_BLE_NOTIFIY_YES = 1;
    public static final int GV_CLOSE_SNOW_GLOBE_HINT = 1;
    protected static final String GV_COLOR_HISTORY = "GV_COLOR_HISTORY";
    public static final int GV_DONT_CARE_WIFI = 1;
    public static final int GV_GCM_TYPE_AD_PUSH = 2;
    public static final int GV_GCM_TYPE_NEW_DOWNLOAD_PUSH = 1;
    public static final int GV_GCM_TYPE_NON = -1;
    public static final int GV_GCM_TYPE_NORMAL_PUSH = 0;
    public static final int GV_INVALID_USER = 0;
    public static final int GV_MASK_COLOR_GOLD = 1;
    public static final int GV_MASK_COLOR_SILVER = 0;
    protected static final String GV_M_ADD_DATE_COLOR = "GV_M_ADD_DATE_COLOR";
    protected static final String GV_M_ADD_DATE_FORMAT = "GGV_M_ADD_DATE_FORMAT";
    protected static final String GV_M_ADD_DATE_POSITION = "GV_M_ADD_DATE_POSITION";
    protected static final String GV_M_AESCOUNT = "GV_M_AESCOUNT";
    protected static final String GV_M_ALBUM_NAME = "GV_M_ALBUM_NAME";
    protected static final String GV_M_APPLY_ADD_DATE = "GV_M_APPLY_ADD_DATE";
    protected static final String GV_M_APPLY_BLE = "GV_M_APPLY_BLE";
    protected static final String GV_M_APPLY_GCM = "GV_M_APPLY_GCM";
    protected static final String GV_M_APP_COUTRYCODE = "GV_M_APP_COUTRYCODE";
    protected static final String GV_M_APP_MODE = "GV_M_APP_MODE";
    protected static final String GV_M_APP_VERSION = "GV_M_APP_VERSION";
    protected static final String GV_M_BLE_NOTIFY = "GV_M_BLE_NOTIFY";
    protected static final String GV_M_CLEAN_NUMBER = "GV_M_CLEAN_NUMBER";
    protected static final String GV_M_CLOUD_ALBUM_SORT_TYPE = "GV_M_CLOUD_ALBUM_SORT_TYPE";
    protected static final String GV_M_COUNTRY_CODE = "GV_M_COUNTRY_CODE";
    protected static final String GV_M_FLASH_CARD = "GV_M_FLASH_CARD";
    protected static final String GV_M_GCM_TYPE = "GV_M_GCM_TYPE";
    protected static final String GV_M_ICOUNT = "GV_M_ICOUNT";
    protected static final String GV_M_INDEX_FORMAT = "GV_M_INDEX_FORMAT";
    protected static final String GV_M_INDEX_SIZE = "GV_M_INDEX_SIZE";
    protected static final String GV_M_INDEX_TAG = "GV_M_INDEX_TAG";
    protected static final String GV_M_INFRA_PASSWORD = "GV_M_INFRA_PASSWORD";
    protected static final String GV_M_INFRA_SSID = "GV_M_INFRA_SSID";
    protected static final String GV_M_LAST_RELEASE_FLAG = "GV_M_LAST_RELEASE_FLAG";
    protected static final String GV_M_LOAD_AD_METHOD = "GV_M_LOAD_AD_METHOD";
    protected static final String GV_M_METAL_ENABLE = "GV_M_METAL_ENABLE";
    protected static final String GV_M_MIRROR = "GV_M_MIRROR";
    protected static final String GV_M_OADDI_COUNTRY = "GV_M_OADDI_COUNTRY";
    protected static final String GV_M_OADDI_INFO_ID = "GV_M_OADDI_INFO_ID";
    protected static final String GV_M_OADDI_NUMBER = "GV_M_OADDI_NUMBER";
    protected static final String GV_M_OADDI_TYPE = "GV_M_OADDI_TYPE";
    protected static final String GV_M_OADDI_VERIOSN = "GV_M_OADDI_VERIOSN";
    protected static final String GV_M_PASSWORD = "GV_M_PASSWORD";
    protected static final String GV_M_PATCH_002 = "GV_M_PATCH_002";
    protected static final String GV_M_PRINTER_IP = "GV_M_PRINTER_IP";
    protected static final String GV_M_PRINTER_MASK_COLOR = "GV_M_PRINTER_MASK_COLOR";
    protected static final String GV_M_PRINTER_NAME = "GV_M_PRINTER_NAME";
    protected static final String GV_M_PRINTER_NOT_ASK = "GV_M_PRINTER_NOT_ASK";
    protected static final String GV_M_PRINTER_PORT = "GV_M_PRINTER_PORT";
    protected static final String GV_M_PRINTER_PRINTOUT = "GV_M_PRINTER_PRINTOUT";
    protected static final String GV_M_PRINTER_PRINT_METHOD = "GV_M_PRINTER_PRINT_METHOD";
    protected static final String GV_M_PRINTER_PRODUCT_ID = "GV_M_PRINTER_PRODUCT_ID";
    protected static final String GV_M_SD_FW_VERSION_P231 = "GV_M_SD_FW_VERSION_P231";
    protected static final String GV_M_SD_FW_VERSION_P232 = "GV_M_SD_FW_VERSION_P232";
    protected static final String GV_M_SD_FW_VERSION_P310W = "GV_M_SD_FW_VERSION_P310W";
    protected static final String GV_M_SD_FW_VERSION_P461 = "GV_M_SD_FW_VERSION_P461";
    protected static final String GV_M_SD_FW_VERSION_P520L = "GV_M_SD_FW_VERSION_P520L";
    protected static final String GV_M_SD_FW_VERSION_P530D = "GV_M_SD_FW_VERSION_P530D";
    protected static final String GV_M_SD_FW_VERSION_P750L = "GV_M_SD_FW_VERSION_P750L";
    protected static final String GV_M_SELECT_PHOTO_COPY_LIST = "GV_M_SELECT_PHOTO_COPY_LIST";
    protected static final String GV_M_SELECT_PHOTO_ID_LIST = "GV_M_SELECT_PHOTO_ID_LIST";
    protected static final String GV_M_SELECT_PHOTO_PATH_LIST = "GV_M_SELECT_PHOTO_PATH_LIST";
    protected static final String GV_M_SERIAL_NUMBER = "GV_M_SERIAL_NUMBER";
    protected static final String GV_M_SHOW_EDIT_HINT = "GV_M_SHOW_EDIT_HINT";
    protected static final String GV_M_SHOW_SNOW_GLOBE_HINT = "GV_M_SHOW_SNOW_GLOBE_HINT";
    protected static final String GV_M_SNAP_PRINT_NOT_ASK = "GV_M_SNAP_PRINT_NOT_ASK";
    protected static final String GV_M_SSID = "GV_M_SSID";
    protected static final String GV_M_TOTAL_PRINTED_RECORD = "GV_M_TOTAL_PRINTED_RECORD";
    protected static final String GV_M_UPLOAD = "GV_M_UPLOAD";
    protected static final String GV_M_UPLOADER = "GV_M_UPLOADER";
    protected static final String GV_M_UPLOAD_E03 = "GV_M_UPLOAD_E03";
    protected static final String GV_M_UPLOAD_METHOD = "GV_M_UPLOAD_METHOD";
    protected static final String GV_M_UPLOAD_PATH = "GV_M_UPLOAD_PATH";
    protected static final String GV_M_UPLOAD_TIME = "GV_M_UPLOAD_TIME";
    protected static final String GV_M_VERIFY = "GV_M_VERIFY";
    protected static final String GV_M_VERIFY_PRINT_COUNT = "GV_M_VERIFY_PRINT_COUNT";
    protected static final String GV_M_VERSION = "GV_M_VERSION";
    public static final int GV_PRINT_METHOD_FREE_PRINT = 1;
    public static final int GV_PRINT_METHOD_NORMAL = 0;
    public static final int GV_SHOW_SNOW_GLOBE_HINT = 0;
    public static final int GV_USE_WIFI = 0;
    public static final int GV_VALID_USER = 1;
    protected static final String PREF_FGV_APP_INFO = "pref_fgv_app_info";
    protected static final String PREF_FGV_HISTORY_COLOR = "pref_fgv_history_color";
    protected static final String PREF_FGV_OFFLINE_AD_DOWNLOAD_INFO = "pref_fgv_offline_ad_download_info";
    protected static final String PREF_FGV_OTHER_SETTING = "pref_fgv_other_setting";
    protected static final String PREF_FGV_PATCH_INFO = "pref_fgv_patch_info";
    protected static final String PREF_FGV_PRINTER_INFO = "pref_fgv_printer_info";
    protected static final String PREF_FGV_SD_FW_INFO = "pref_fgv_sd_fw_info";
    protected static final String PREF_FGV_UPLOAD_INFO = "pref_fgv_upload_info";
    protected static final String PREF_FGV_USER_INFO = "pref_fgv_user_info";
    protected static final String PREF_FGV_WIFI_AUTO_CONNECT_INFO = "pref_fgv_wifi_auto_connect_info";
    protected static final String PREF_GV_SELECT_PHOTO_INFO = "pref_gv_select_photo_info";
    protected static final String PREF_GV_TOTAL_PRINTED_RECORD = "PREF_GV_TOTAL_PRINTED_RECORD";
    protected LogManager LOG;
    protected Context m_Context;
    private boolean m_boEdit;
    protected SharedPreferences m_fsp;
    protected SharedPreferences m_sp;

    public BaseGlobalVariable(Context context) {
        this.m_boEdit = false;
        this.LOG = null;
        this.m_Context = context;
        this.LOG = new LogManager(GV_USE_WIFI);
    }

    public void ClearGlobalVariable() {
        try {
            this.LOG.m385i("ClearGlobalVariable", "Entry");
            Editor spe = this.m_sp.edit();
            spe.clear();
            if (!spe.commit()) {
                this.LOG.m385i("ClearGlobalVariable", "commit fail");
            }
        } catch (Exception ex) {
            this.LOG.m385i("ClearGlobalVariable", HITI_WEB_VIEW_STATUS.ERROR);
            ex.printStackTrace();
        }
    }

    public void SetEdit(boolean boEdit) {
        this.m_boEdit = boEdit;
    }

    public boolean IsEdit() {
        return this.m_boEdit;
    }
}
