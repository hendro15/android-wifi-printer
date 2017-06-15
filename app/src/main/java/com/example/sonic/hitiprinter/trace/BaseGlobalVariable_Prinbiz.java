package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher.RS_TYPE;

public abstract class BaseGlobalVariable_Prinbiz extends BaseGlobalVariable {
    protected static final String GV_M_ALBUM_ID = "GV_M_ALBUM_ID";
    protected static final String GV_M_ALBUM_ID_LEN = "GV_M_ALBUM_ID_LEN";
    protected static final String GV_M_ALBUM_ROUTE = "GV_M_ALBUM_ROUTE";
    protected static final String GV_M_ALBUM_SID = "GV_M_ALBUM_SID";
    protected static final String GV_M_ALBUM_SID_LEN = "GV_M_ALBUM_SID_LEN";
    protected static final String GV_M_BORDER_PATH = "GV_M_BORDER_PATH";
    protected static final String GV_M_BORDER_PATH_LEN = "GV_M_BORDER_PATH_LEN";
    protected static final String GV_M_COPIES_LEN = "GV_M_COPIES_LEN";
    protected static final String GV_M_EDIT_BORDER_LEN = "GV_M_EDIT_BORDER_LEN";
    protected static final String GV_M_EDIT_BORDER_POS = "GV_M_EDIT_BORDER_POS";
    protected static final String GV_M_EDIT_COLLAGE_LEN = "GV_M_EDIT_COLLAGE_LEN";
    protected static final String GV_M_EDIT_COLLAGE_META = "GV_M_EDIT_COLLAGE_META";
    protected static final String GV_M_EDIT_FILTER_LEN = "GV_M_EDIT_FILTER_LEN";
    protected static final String GV_M_EDIT_FILTER_POS = "GV_M_EDIT_FILTER_POS";
    protected static final String GV_M_EDIT_INDEX = "GV_M_EDIT_INDEX";
    protected static final String GV_M_EDIT_ISEDIT = "GV_M_EDIT_ISEDIT";
    protected static final String GV_M_EDIT_ISEDIT_LEN = "GV_M_EDIT_ISEDIT_LEN";
    protected static final String GV_M_EDIT_VERTICAL = "GV_M_EDIT_VERTICAL";
    protected static final String GV_M_EDIT_VERTICAL_LEN = "GV_M_EDIT_VERTICAL_LEN";
    protected static final String GV_M_EDIT_XML_LEN = "GV_M_EDIT_XML_LEN";
    protected static final String GV_M_EDIT_XML_META = "GV_M_EDIT_XML_META";
    protected static final String GV_M_FETCH_LEN = "GV_M_FETCH_LEN";
    protected static final String GV_M_FETCH_PATH = "GV_M_FETCH_PATH";
    protected static final String GV_M_GAL_ID = "GV_M_GAL_ID";
    protected static final String GV_M_GAL_ID_LEN = "GV_M_GAL_ID_LEN";
    protected static final String GV_M_ID_LEN = "GV_M_ID_LEN";
    protected static final String GV_M_INDEX_LEN = "GV_M_INDEX_LEN";
    protected static final String GV_M_LOADED_INDEX = "GV_M_LOADED_INDEX";
    protected static final String GV_M_LOADED_INDEX_LEN = "GV_M_LOADED_INDEX_LEN";
    protected static final String GV_M_LOADED_NAME = "GV_M_LOADED_NAME";
    protected static final String GV_M_LOADED_NAME_LEN = "GV_M_LOADED_NAME_LEN";
    protected static final String GV_M_LOADED_PATH = "GV_M_LOADED_PATH";
    protected static final String GV_M_LOADED_PATH_LEN = "GV_M_LOADED_PATH_LEN";
    protected static final String GV_M_LOADED_SIZE = "GV_M_LOADED_SIZE";
    protected static final String GV_M_LOADED_SIZE_LEN = "GV_M_LOADED_SIZE_LEN";
    protected static final String GV_M_NAME_LEN = "GV_M_NAME_LEN";
    protected static final String GV_M_PATH_LEN = "GV_M_PATH_LEN";
    protected static final String GV_M_PHOTO_COPIES = "GV_M_PHOTO_COPIES";
    protected static final String GV_M_PHOTO_ID = "GV_M_PHOTO_ID";
    protected static final String GV_M_PHOTO_NAME = "GV_M_PHOTO_NAME";
    protected static final String GV_M_PHOTO_PATH = "GV_M_PHOTO_PATH";
    protected static final String GV_M_PHOTO_SID = "GV_M_PHOTO_SID";
    protected static final String GV_M_PHOTO_SIZE = "GV_M_PHOTO_SIZE";
    protected static final String GV_M_SAVE_IMG_LEN = "GV_M_SAVE_IMG_LEN";
    protected static final String GV_M_SAVE_IMG_PATH = "GV_M_SAVE_IMG_PATH";
    protected static final String GV_M_SD_SEL_ALBUM_ID = "GV_M_SD_SEL_ALBUM_ID";
    protected static final String GV_M_SEL_ALBUM_ID = "GV_M_SEL_ALBUM_ID";
    protected static final String GV_M_SEL_ALBUM_NAME = "GV_M_SEL_ALBUM_NAME";
    protected static final String GV_M_SEL_ALBUM_STORAGE_ID = "GV_M_SEL_ALBUM_STORAGE_ID";
    protected static final String GV_M_SID_LEN = "GV_M_SIZE_LEN";
    protected static final String GV_M_SIZE_LEN = "GV_M_SIZE_LEN";
    protected static final String GV_M_TYPE_FACE = "GV_M_TYPE_FACE";
    protected static final String GV_M_TYPE_ID_PATH = "GV_M_TYPE_ID_PATH";
    protected static final String GV_M_TYPE_ID_REGION = "GV_M_TYPE_ID_REGION";
    protected static final String GV_M_TYPE_ID_STYLE = "GV_M_TYPE_ID_STYLE";
    protected static final String GV_M_TYPE_PAPER_TYPE = "GV_M_TYPE_PAPER_TYPE";
    protected static final String GV_M_TYPE_PRINT_DUPLEX = "GV_M_TYPE_PRINT_DUPLEX";
    protected static final String GV_M_TYPE_PRINT_METHOD = "GV_M_TYPE_PRINT_METHOD";
    protected static final String GV_M_TYPE_PRINT_SHARPEN = "GV_M_TYPE_PRINT_SHARPEN";
    protected static final String GV_M_TYPE_PRINT_TEXTURE = "GV_M_TYPE_PRINT_TEXTURE";
    protected static final String GV_M_VISION_FIRST_POSITION = "GV_M_VISION_FIRST_POSITION";
    protected static final String GV_M_VISION_FIRST_TOP = "GV_M_VISION_FIRST_TOP";
    protected static final String PREF_GV_ALBUM_LOADED_META = "pref_gv_album_loaded_meta";
    protected static final String PREF_GV_BORDER_META = "pref_gv_border_meta";
    protected static final String PREF_GV_GALLERY_LOADED_META = "pref_gv_gallery_loaded_meta";
    protected static final String PREF_GV_MULTI_SEL_CONTAINER = "pref_gv_multi_sel_container";
    protected static final String PREF_GV_MULTI_SEL_EDIT_META = "pref_gv_multi_sel_edit_meta";
    protected static final String PREF_GV_POOL_EDIT_META = "PREF_GV_POOL_EDIT_META";
    protected static final String PREF_GV_PRINBIZ_GENERAL_PRINT_INFO = "pref_gv_prinbiz_general_print_info";
    protected static final String PREF_GV_PRINBIZ_ID_PRINT_INFO = "pref_gv_prinbiz_id_print_info";
    protected static final String PREF_GV_PRINBIZ_QUICK_PRINT_INFO = "pref_gv_prinbiz_quick_print_info";
    protected static final String PREF_GV_SELECT_PHOTO_INFO = "pref_gv_select_photo_info";
    protected static final String PREF_GV_SEL_ALBUM_INFO = "pref_gv_sel_album_info";
    protected static final String PREF_GV_SEL_ALBUM_INFO_PATH_ID = "pref_gv_sel_album_info_path_id";
    protected static int R_STRING_GlobalVariable_DEBUG_LOG;

    static {
        R_STRING_GlobalVariable_DEBUG_LOG = 1;
    }

    public BaseGlobalVariable_Prinbiz(Context context) {
        super(context);
        this.m_Context = context;
        GetResourceID(context);
        this.LOG = new LogManager(0);
    }

    private void GetResourceID(Context context) {
        R_STRING_GlobalVariable_DEBUG_LOG = ResourceSearcher.getId(context, RS_TYPE.STRING, "GlobalVariable_DEBUG_LOG");
    }
}
