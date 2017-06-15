package com.example.sonic.hitiprinter.jscommand;

import android.content.Context;
import com.example.sonic.hitiprinter.sql.offlineadinfo.parser.OfflineADParser;
import com.example.sonic.hitiprinter.utility.EncryptAndDecryptAES;
import com.example.sonic.hitiprinter.utility.LogManager;
import org.xmlpull.v1.XmlPullParser;

public class JSCommand {
    public static final int SERVER_RESPONSE_ACCOUNT_VERIFY = 9;
    public static final int SERVER_RESPONSE_CLEAR_ALL_COOKIE = 3;
    public static final int SERVER_RESPONSE_CLOUD_ALBUM_SORT = 18;
    public static final int SERVER_RESPONSE_CLOUD_ALBUM_UPLOAD = 17;
    public static final int SERVER_RESPONSE_DOWNLOAD_URL = 5;
    public static final int SERVER_RESPONSE_INN_APP_BILLING = 16;
    public static final int SERVER_RESPONSE_OPEN_DOWNLOAD_CENTER = 14;
    public static final int SERVER_RESPONSE_OPEN_EXTERN_BROWSER = 13;
    public static final int SERVER_RESPONSE_OPEN_MEMBER = 15;
    public static final int SERVER_RESPONSE_RECEIVE_COUNT = 2;
    public static final int SERVER_RESPONSE_SHOP_DOWNLOAD_URL = 11;
    public static final int SERVER_RESPONSE_SHOW_AD_VEDIO = 12;
    public static final int SERVER_RESPONSE_SHOW_DIALOG = 1;
    public static final String SERVER_RESPONSE_SUB_ATTR_COUNTRY = "country=";
    public static final String SERVER_RESPONSE_SUB_ATTR_END = "_SuB_AtTr_EnD";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_EXPIRE = "expire=";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_EXT = "ext=";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_ID = "id=";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE = "language=";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_PATH = "SERVER_RESPONSE_SUB_ATTR_FILE_PATH";
    public static final String SERVER_RESPONSE_SUB_ATTR_FILE_SIZE = "size=";
    public static final String SERVER_RESPONSE_SUB_ATTR_START = "SuB_AtTr_StArT_";
    public static final String SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT = "point=";
    public static final int SERVER_RESPONSE_USER_LOGOUT = 10;
    public static String UP;
    LogManager LOG;
    final String SERVER_RESPONSE_ATTR;
    final String SERVER_RESPONSE_CONTENT;
    Context m_Context;

    static {
        UP = "A2B9C1D2A6B2C6D8";
    }

    public JSCommand(Context context) {
        this.SERVER_RESPONSE_ATTR = "SERVER_RESPONSE_Attr:";
        this.SERVER_RESPONSE_CONTENT = "_Content:";
        this.LOG = null;
        this.m_Context = null;
        this.m_Context = context;
        this.LOG = new LogManager(0);
    }

    public int GetServerResponseAttr(String message) {
        String strAttr = "SERVER_RESPONSE_Attr:";
        if (!message.contains(strAttr)) {
            return -1;
        }
        int iRet = Integer.parseInt(message.substring(message.indexOf(strAttr) + strAttr.length(), message.indexOf("_Content:")));
        this.LOG.m385i("iRet", String.valueOf(iRet));
        return iRet;
    }

    public String GetServerResponseContent(String message) {
        String strContent = "_Content:";
        if (!message.contains(strContent)) {
            return null;
        }
        String strRet = message.substring(message.indexOf(strContent) + strContent.length(), message.length());
        this.LOG.m385i("strRet", strRet);
        return strRet;
    }

    public String GetSubType_SERVER_RESPONSE_DOWNLOAD_URL(String strMsg, int iServerResponse, String subAttr) {
        String strRet = null;
        if (iServerResponse == SERVER_RESPONSE_DOWNLOAD_URL) {
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_SIZE)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_SIZE) + SERVER_RESPONSE_SUB_ATTR_FILE_SIZE.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_EXT)));
                if (strRet != null) {
                    this.LOG.m384e("Size", strRet);
                } else {
                    this.LOG.m384e("Size", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_EXT)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_EXT) + SERVER_RESPONSE_SUB_ATTR_FILE_EXT.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END)));
                if (strRet != null) {
                    this.LOG.m384e("EXT", strRet);
                } else {
                    this.LOG.m384e("EXT", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_PATH) && strMsg.contains(SERVER_RESPONSE_SUB_ATTR_END)) {
                if (strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) <= strMsg.length()) {
                    strRet = strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) + SERVER_RESPONSE_SUB_ATTR_END.length(), strMsg.length());
                }
                this.LOG.m384e("PATH", strRet);
            }
        }
        return strRet;
    }

    public String GetSubType_SERVER_RESPONSE_SHOP_DOWNLOAD_URL(String strMsg, int iServerResponse, String subAttr) {
        String strRet = null;
        if (iServerResponse == SERVER_RESPONSE_SHOP_DOWNLOAD_URL) {
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_EXPIRE)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_EXPIRE) + SERVER_RESPONSE_SUB_ATTR_FILE_EXPIRE.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_ID)));
                if (strRet != null) {
                    this.LOG.m384e("Expire", strRet);
                } else {
                    this.LOG.m384e("Expire", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_ID)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_ID) + SERVER_RESPONSE_SUB_ATTR_FILE_ID.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_SIZE)));
                if (strRet != null) {
                    this.LOG.m384e("ID", strRet);
                } else {
                    this.LOG.m384e("ID", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_SIZE)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_SIZE) + SERVER_RESPONSE_SUB_ATTR_FILE_SIZE.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE)));
                if (strRet != null) {
                    this.LOG.m384e("Size", strRet);
                } else {
                    this.LOG.m384e("Size", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE) + SERVER_RESPONSE_SUB_ATTR_FILE_LANGUAGE.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END)));
                if (strRet != null) {
                    this.LOG.m384e("Language", strRet);
                } else {
                    this.LOG.m384e("Language", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_PATH) && strMsg.contains(SERVER_RESPONSE_SUB_ATTR_END)) {
                if (strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) <= strMsg.length()) {
                    strRet = strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) + SERVER_RESPONSE_SUB_ATTR_END.length(), strMsg.length());
                }
                this.LOG.m384e("PATH", strRet);
            }
        }
        return strRet;
    }

    public String GetSubType_SERVER_RESPONSE_SHOW_AD_VEDIO(String strMsg, int iServerResponse, String subAttr) {
        String strRet = null;
        if (iServerResponse == SERVER_RESPONSE_SHOW_AD_VEDIO) {
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_ID)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_FILE_ID) + SERVER_RESPONSE_SUB_ATTR_FILE_ID.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_COUNTRY)));
                if (strRet != null) {
                    this.LOG.m384e("Vedio ID", strRet);
                } else {
                    this.LOG.m384e("Vedio ID", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_COUNTRY)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_COUNTRY) + SERVER_RESPONSE_SUB_ATTR_COUNTRY.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT)));
                if (strRet != null) {
                    this.LOG.m384e(OfflineADParser.ATTR_Country, strRet);
                } else {
                    this.LOG.m384e(OfflineADParser.ATTR_Country, "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT)) {
                strRet = IsNull(strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT) + SERVER_RESPONSE_SUB_ATTR_WATCH_VEDIO_POINT.length(), strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END)));
                if (strRet != null) {
                    this.LOG.m384e("Point", strRet);
                } else {
                    this.LOG.m384e("Point", "NULL");
                }
            }
            if (subAttr.equals(SERVER_RESPONSE_SUB_ATTR_FILE_PATH) && strMsg.contains(SERVER_RESPONSE_SUB_ATTR_END)) {
                if (strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) <= strMsg.length()) {
                    strRet = strMsg.substring(strMsg.indexOf(SERVER_RESPONSE_SUB_ATTR_END) + SERVER_RESPONSE_SUB_ATTR_END.length(), strMsg.length());
                }
                this.LOG.m384e("PATH", strRet);
            }
        }
        return strRet;
    }

    public String GetSubType(String strMsg, int iServerResponse, String subAttr) {
        String strRet = null;
        try {
            if (!strMsg.contains(SERVER_RESPONSE_SUB_ATTR_START)) {
                return null;
            }
            if (iServerResponse == SERVER_RESPONSE_DOWNLOAD_URL) {
                strRet = GetSubType_SERVER_RESPONSE_DOWNLOAD_URL(strMsg, iServerResponse, subAttr);
            } else if (iServerResponse == SERVER_RESPONSE_SHOP_DOWNLOAD_URL) {
                strRet = GetSubType_SERVER_RESPONSE_SHOP_DOWNLOAD_URL(strMsg, iServerResponse, subAttr);
            } else if (iServerResponse == SERVER_RESPONSE_SHOW_AD_VEDIO) {
                strRet = GetSubType_SERVER_RESPONSE_SHOW_AD_VEDIO(strMsg, iServerResponse, subAttr);
            }
            return strRet;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return strRet;
    }

    private String IsNull(String str) {
        String strRet = str;
        if (strRet == null || strRet.length() > 0) {
            return strRet;
        }
        return null;
    }

    public String ParseUP(String strMessage, String iv, String key) {
        return EncryptAndDecryptAES.DecryptStrNoPadding(strMessage.substring(SERVER_RESPONSE_SHOW_DIALOG), iv, key);
    }

    public String GetU(String strUP) {
        String strU = XmlPullParser.NO_NAMESPACE;
        if (strUP.length() > 0) {
            return strUP.substring(0, strUP.indexOf(UP));
        }
        return strU;
    }

    public String GetP(String strUP) {
        String strP = XmlPullParser.NO_NAMESPACE;
        if (strUP.length() > 0) {
            return strUP.substring(strUP.indexOf(UP) + UP.length(), strUP.length());
        }
        return strP;
    }

    public static boolean GetVerify(String strMessage) {
        if (Integer.valueOf(strMessage.substring(0, SERVER_RESPONSE_SHOW_DIALOG)).intValue() != SERVER_RESPONSE_SHOW_DIALOG || strMessage.length() <= SERVER_RESPONSE_SHOW_DIALOG) {
            return false;
        }
        return true;
    }
}
