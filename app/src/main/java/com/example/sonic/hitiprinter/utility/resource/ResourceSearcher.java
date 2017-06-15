package com.example.sonic.hitiprinter.utility.resource;

import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import java.lang.reflect.Field;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.tftp.TFTPClient;

public class ResourceSearcher {

    /* renamed from: com.hiti.utility.resource.ResourceSearcher.1 */
    static /* synthetic */ class C04831 {
        static final /* synthetic */ int[] $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE;

        static {
            $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE = new int[RS_TYPE.values().length];
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.ANIM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.ATTR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.DIMEN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.DRAWABLE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.ID.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.LAYOUT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.MENU.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.STYLE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[RS_TYPE.STYLEABLE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    public enum RS_TYPE {
        ANIM,
        ATTR,
        COLOR,
        DIMEN,
        DRAWABLE,
        ID,
        LAYOUT,
        MENU,
        STRING,
        STYLE,
        STYLEABLE
    }

    public static int getId(Context content, RS_TYPE Type, String strName) {
        if (Type == RS_TYPE.STYLEABLE) {
            return getResourceDeclareStyleableInt(content, strName);
        }
        return content.getResources().getIdentifier(strName, ConvertResourceString(Type), content.getPackageName());
    }

    public static int[] getIds(Context content, RS_TYPE Type, String strName) {
        if (Type == RS_TYPE.STYLEABLE) {
            return getResourceDeclareStyleableIntArray(content, strName);
        }
        return null;
    }

    private static final int[] getResourceDeclareStyleableIntArray(Context context, String name) {
        try {
            for (Field f : Class.forName(context.getPackageName() + ".R$styleable").getFields()) {
                if (f.getName().equals(name)) {
                    return (int[]) f.get(null);
                }
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    private static final int getResourceDeclareStyleableInt(Context context, String name) {
        int i = 0;
        try {
            for (Field f : Class.forName(context.getPackageName() + ".R$styleable").getFields()) {
                if (f.getName().equals(name)) {
                    i = f.getInt(null);
                    break;
                }
            }
        } catch (Throwable th) {
        }
        return i;
    }

    private static String ConvertResourceString(RS_TYPE Type) {
        switch (C04831.$SwitchMap$com$hiti$utility$resource$ResourceSearcher$RS_TYPE[Type.ordinal()]) {
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return "anim";
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return "attr";
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return "color";
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return "dimen";
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return "drawable";
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return "id";
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return "layout";
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return "menu";
            case ConnectionResult.SERVICE_INVALID /*9*/:
                return "string";
            case ConnectionResult.DEVELOPER_ERROR /*10*/:
                return "style";
            case ConnectionResult.LICENSE_CHECK_FAILED /*11*/:
                return "styleable";
            default:
                return null;
        }
    }
}
