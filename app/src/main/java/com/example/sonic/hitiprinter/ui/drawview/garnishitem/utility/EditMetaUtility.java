package com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_GetImageData;
import com.example.sonic.hitiprinter.trace.GlobalVariable_AlbumSelInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_EditMeta;
import com.example.sonic.hitiprinter.trace.GlobalVariable_MultiSelContainer;
import com.example.sonic.hitiprinter.trace.GlobalVariable_PoolEditMeta;
import com.example.sonic.hitiprinter.trace.GlobalVariable_PrintSettingInfo;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.FilterColorValue;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import com.example.sonic.hitiprinter.utility.CIELab;
import com.example.sonic.hitiprinter.utility.LogManager;
import java.util.ArrayList;
import org.apache.commons.net.echo.EchoUDPClient;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.nntp.NNTPReply;
import org.apache.commons.net.tftp.TFTPClient;
import org.xmlpull.v1.XmlPullParser;

public class EditMetaUtility {
    String IP;
    LogManager LOG;
    String TAG;
    EditMeta m_EditMeta;
    EditMetaListener m_EditMetaListener;
    Bitmap m_OriBitmap;
    RGBListener m_RGBListener;
    int m_bSharpen;
    private Context m_context;
    EditHandle m_editHandler;
    HitiPPR_GetImageData m_fetchImage;
    int m_iDuplex;
    int m_iMethod;
    private int m_iPathRoute;
    int m_iPort;
    private int m_iPos;
    int m_iPrintOut;
    int m_iSharpen;
    int m_iTexture;
    JumpPreferenceKey m_pref;
    GlobalVariable_EditMeta m_prefEditMeta;
    JumpPreferenceKey m_prefFValue;
    GlobalVariable_MultiSelContainer m_prefMultiSel;
    GlobalVariable_PoolEditMeta m_prefPoolEdit;
    GlobalVariable_PrintSettingInfo m_prefQPinfo;
    Runnable m_rDrawView;

    private class EditHandle extends MSGHandler {
        private EditHandle() {
        }

        public void handleMessage(Message msg) {
            String strErr;
            switch (msg.what) {
                case RequestState.REQUEST_TIMEOUT_ERROR /*312*/:
                    strErr = msg.getData().getString(MSGHandler.MSG);
                    Log.e("REQUEST_TIMEOUT_ERROR", String.valueOf(strErr));
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.FetchImgTimeOut(strErr);
                    }
                case RequestState.REQUEST_GET_IMAGE_DATA /*370*/:
                    String strImagePath = EditMetaUtility.this.m_fetchImage.GetImagePath();
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.FetchImageDone(EditMetaUtility.this.m_iPos, strImagePath);
                    }
                case RequestState.REQUEST_GET_IMAGE_DATA_ERROR /*371*/:
                    strErr = msg.getData().getString(MSGHandler.MSG);
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.FetchImgError(strErr);
                    }
                case RequestState.REQUEST_INIT_DWAW_VIEW_END /*373*/:
                    int iPos = msg.arg1;
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.InitDrawViewEnd(iPos);
                    }
                case RequestState.REQUEST_INIT_DWAW_VIEW /*374*/:
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.InitDrawView(msg.arg1);
                    }
                case RequestState.REQUEST_RATIO_OF_FETCH_IMG /*377*/:
                    String strRatio = msg.getData().getString(MSGHandler.MSG);
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.FetchImgRatio(EditMetaUtility.this.m_iPos, strRatio);
                    }
                case RequestState.REQUEST_SAVE_RDIT_PHOTO /*378*/:
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.SaveEditPhoto();
                    }
                case RequestState.REQUEST_SAVE_RDIT_PHOTO_DONE /*379*/:
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.SaveEditPhotoDone();
                    }
                case NNTPReply.MORE_AUTH_INFO_REQUIRED /*381*/:
                    if (EditMetaUtility.this.HaveEditMetaListener()) {
                        EditMetaUtility.this.m_EditMetaListener.DelFLValueData();
                    }
                default:
            }
        }
    }

    public EditMetaUtility(Context context) {
        this.m_context = null;
        this.m_iPos = -1;
        this.m_prefEditMeta = null;
        this.m_prefMultiSel = null;
        this.m_prefQPinfo = null;
        this.m_prefPoolEdit = null;
        this.m_pref = null;
        this.m_prefFValue = null;
        this.m_EditMetaListener = null;
        this.m_RGBListener = null;
        this.m_fetchImage = null;
        this.m_editHandler = null;
        this.m_EditMeta = null;
        this.m_iPathRoute = 0;
        this.m_OriBitmap = null;
        this.m_rDrawView = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_iPrintOut = 0;
        this.m_iMethod = 0;
        this.m_iTexture = 0;
        this.m_iDuplex = 0;
        this.m_iSharpen = 0;
        this.m_bSharpen = 0;
        this.LOG = null;
        this.TAG = null;
        this.m_context = context;
        this.m_editHandler = new EditHandle();
        this.LOG = new LogManager(0);
        this.TAG = context.getClass().getSimpleName();
        this.m_pref = new JumpPreferenceKey(this.m_context);
        this.m_iPathRoute = this.m_pref.GetPathSelectedPref();
        this.LOG.m385i(this.TAG, "EditMetaUtility m_iPathRoute:" + this.m_iPathRoute);
        this.m_prefEditMeta = new GlobalVariable_EditMeta(this.m_context, GetSrcRoute(context));
        this.m_prefMultiSel = new GlobalVariable_MultiSelContainer(this.m_context, GetSrcRoute(context));
        this.m_prefQPinfo = new GlobalVariable_PrintSettingInfo(this.m_context, this.m_iPathRoute);
        this.m_prefPoolEdit = new GlobalVariable_PoolEditMeta(this.m_context);
        this.m_prefFValue = new JumpPreferenceKey(context, "filterValue");
        this.m_pref.SetPreference(JumpPreferenceKey.EDM_SHOW, false);
    }

    public EditMetaUtility(Context context, int iType) {
        this.m_context = null;
        this.m_iPos = -1;
        this.m_prefEditMeta = null;
        this.m_prefMultiSel = null;
        this.m_prefQPinfo = null;
        this.m_prefPoolEdit = null;
        this.m_pref = null;
        this.m_prefFValue = null;
        this.m_EditMetaListener = null;
        this.m_RGBListener = null;
        this.m_fetchImage = null;
        this.m_editHandler = null;
        this.m_EditMeta = null;
        this.m_iPathRoute = 0;
        this.m_OriBitmap = null;
        this.m_rDrawView = null;
        this.IP = XmlPullParser.NO_NAMESPACE;
        this.m_iPort = 0;
        this.m_iPrintOut = 0;
        this.m_iMethod = 0;
        this.m_iTexture = 0;
        this.m_iDuplex = 0;
        this.m_iSharpen = 0;
        this.m_bSharpen = 0;
        this.LOG = null;
        this.TAG = null;
        this.m_context = context;
        this.m_prefPoolEdit = new GlobalVariable_PoolEditMeta(context);
        this.m_prefFValue = new JumpPreferenceKey(context, "filterValue");
    }

    public void SetIPandPort(String IP, int iPort) {
        this.IP = IP;
        this.m_iPort = iPort;
    }

    public void SetFilterValue(int i, ArrayList<FilterColorValue> fcvl) {
        if (fcvl != null) {
            this.m_prefFValue.SetFilterValue("filterValue_" + i, fcvl);
        }
    }

    public float GetFilterValue(int i, int j, String item) {
        if (this.m_prefFValue == null) {
            this.m_prefFValue = new JumpPreferenceKey(this.m_context, "filterValue");
        }
        return this.m_prefFValue.GetFilterValue("filterValue_" + i + "_" + j + item);
    }

    public String GetModel() {
        if (this.m_pref != null) {
            return this.m_pref.GetModelPreference();
        }
        return null;
    }

    public int GetPrintTexture() {
        Log.i("GetPrintTexture", XmlPullParser.NO_NAMESPACE + this.m_iTexture);
        return this.m_iTexture;
    }

    public int GetPrintDuplex() {
        Log.i("GetPrintDuplex", XmlPullParser.NO_NAMESPACE + this.m_iDuplex);
        return this.m_iDuplex;
    }

    public int GetTexturePref() {
        this.m_prefQPinfo.RestoreGlobalVariable();
        return this.m_prefQPinfo.GetPrintTexture();
    }

    public int GetPrintDuplexPref() {
        this.m_prefQPinfo.RestoreGlobalVariable();
        return this.m_prefQPinfo.GetPrintDuplex();
    }

    public int GetServerPaperType() {
        this.m_prefQPinfo.RestoreGlobalVariable();
        return this.m_prefQPinfo.GetServerPaperType();
    }

    public int GetPrintMethod() {
        Log.i("GetPrintMethod", XmlPullParser.NO_NAMESPACE + this.m_iMethod);
        return this.m_iMethod;
    }

    public int GetMethodPref() {
        this.m_prefQPinfo.RestoreGlobalVariable();
        return this.m_prefQPinfo.GetPrintMethod();
    }

    public int GetSharpenPref() {
        this.m_prefQPinfo.RestoreGlobalVariable();
        return this.m_prefQPinfo.GetPrintSharpen();
    }

    public byte GetPrintSharpenByte() {
        return GetSarpenValue(this.m_iSharpen);
    }

    public static byte GetSarpenValue(int iSharpen) {
        switch (iSharpen) {
            case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE /*0*/:
                return Byte.MIN_VALUE;
            case FTPClient.ACTIVE_REMOTE_DATA_CONNECTION_MODE /*1*/:
                return (byte) -120;
            case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE /*2*/:
                return (byte) -112;
            case FTPClient.PASSIVE_REMOTE_DATA_CONNECTION_MODE /*3*/:
                return (byte) -104;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                return (byte) -96;
            case TFTPClient.DEFAULT_MAX_TIMEOUTS /*5*/:
                return (byte) -88;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                return (byte) -80;
            case EchoUDPClient.DEFAULT_PORT /*7*/:
                return (byte) -72;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                return (byte) -64;
            default:
                return (byte) -120;
        }
    }

    public void SetPrintMethod(int iMethod) {
        this.m_iMethod = iMethod;
        this.LOG.m385i("SetPrintMethod", XmlPullParser.NO_NAMESPACE + this.m_iMethod);
    }

    public void SetPrintSharpen(int iSharpen) {
        this.m_iSharpen = iSharpen;
    }

    public void SetPrintTexture(int iTexture) {
        this.LOG.m385i("SetPrintTexture", XmlPullParser.NO_NAMESPACE + iTexture);
        this.m_iTexture = iTexture;
    }

    public void SetPrintDuplex(int iDuplex) {
        this.LOG.m385i("SetPrintDuplex", XmlPullParser.NO_NAMESPACE + iDuplex);
        this.m_iDuplex = iDuplex;
    }

    public void ClearEditMeta() {
        this.m_prefEditMeta.ClearGlobalVariable();
    }

    public void ClearMultiSelMeta() {
        this.m_prefMultiSel.ClearGlobalVariable();
    }

    public void ClearPoolEditMeta() {
        this.LOG.m385i("ClearPoolEditMeta", "ClearPoolEditMeta");
        this.m_prefPoolEdit.ClearGlobalVariable();
    }

    public void CleanFilterValuePref() {
        if (this.m_prefFValue != null) {
            this.m_prefFValue.CleanPreference();
        }
    }

    public void SetFetchStop() {
        if (this.m_fetchImage != null) {
            this.m_fetchImage.StopTimerOut();
            this.m_fetchImage.Stop();
        }
    }

    public static void SetSrcRoute(Context context, int iSourceRoute) {
        GlobalVariable_AlbumSelInfo m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(context, false);
        m_prefAlbumInfo.RestoreGlobalVariable();
        m_prefAlbumInfo.SetAlbumRoute(iSourceRoute);
        m_prefAlbumInfo.SaveGlobalVariable();
    }

    public static int GetSrcRoute(Context context) {
        GlobalVariable_AlbumSelInfo m_prefAlbumInfo = new GlobalVariable_AlbumSelInfo(context, false);
        m_prefAlbumInfo.RestoreGlobalVariable();
        return m_prefAlbumInfo.GetAlbumRoute();
    }

    public static void SetPathRoute(Context context, int pathType) {
        new JumpPreferenceKey(context).SetPreference(JumpPreferenceKey.PATHSELECTED, pathType);
    }

    public int GetPathRoute() {
        return this.m_iPathRoute;
    }

    public EditMeta GetEditMeta(int iSrcRoute) {
        this.m_EditMeta = new EditMeta(iSrcRoute);
        this.m_prefMultiSel.RestoreGlobalVariable();
        this.m_prefEditMeta.RestoreGlobalVariable();
        this.m_prefPoolEdit.RestoreGlobalVariable();
        if (iSrcRoute == 1) {
            this.m_EditMeta.SetMobilePathAndID(this.m_prefMultiSel.GetMobilePathList(), this.m_prefMultiSel.GetMobileIDList());
        } else {
            this.m_EditMeta.SetSDcardMeta(this.m_prefMultiSel.GetSDcardIDList(), this.m_prefMultiSel.GetSDcardSIDList());
            this.m_EditMeta.SetThumbPathAndName(this.m_prefMultiSel.GetThumbPathList(), this.m_prefMultiSel.GetPhotoNameList());
            this.m_EditMeta.SetFetchPath(this.m_prefEditMeta.GetFetchPathList());
        }
        this.m_EditMeta.SetCopies(this.m_prefMultiSel.GetPhotoCopiesList());
        this.m_EditMeta.SetEditSelPos(this.m_prefEditMeta.GetEditSelPosList());
        this.m_EditMeta.SetXMLVerticalIsEdit(this.m_prefPoolEdit.GetXMLpathList(), this.m_prefPoolEdit.GetIsVerticalList(), this.m_prefPoolEdit.GetIsEditList());
        this.m_EditMeta.SetBorderAndFilterPos(this.m_prefPoolEdit.GetBorderPosList(), this.m_prefPoolEdit.GetFilterPosList());
        this.m_EditMeta.SetCollagePath(this.m_prefPoolEdit.GetCollagePathList());
        return this.m_EditMeta;
    }

    public void SetEditMeta(EditMeta m_EditMeta) {
        SetSrcRoute(this.m_context, m_EditMeta.GetSrcRoute());
        this.m_prefMultiSel.ClearGlobalVariable();
        this.m_prefEditMeta.ClearGlobalVariable();
        this.m_prefPoolEdit.ClearGlobalVariable();
        if (m_EditMeta.GetSrcRoute() == 1) {
            this.m_prefMultiSel.SetMobilePhotoPathAndId(m_EditMeta.GetMobilePathList(), m_EditMeta.GetMobileIDList());
        } else {
            this.m_prefMultiSel.SetPhotoIdAndStorageIdList(m_EditMeta.GetSDcardIDList(), m_EditMeta.GetSDcardSIDList());
            this.m_prefMultiSel.SetThumbPathAndNameList(m_EditMeta.GetThumbPathList(), m_EditMeta.GetPhotoNameList());
            this.m_prefEditMeta.SetFetchImgPath(m_EditMeta.GetFetchPathList());
        }
        this.m_prefMultiSel.SetPhotoCopiesList(m_EditMeta.GetCopiesList());
        this.m_prefMultiSel.SaveGlobalVariable();
        this.m_prefPoolEdit.SetXMLandVerticalList(m_EditMeta.GetXMLList(), m_EditMeta.GetIsVerticalList(), m_EditMeta.GetIsEditList());
        this.m_prefPoolEdit.SetBorderAndFilterList(m_EditMeta.GetBorderPosList(), m_EditMeta.GetFilterPosList());
        this.m_prefPoolEdit.SetCollagePathList(m_EditMeta.GetCollagePathList());
        this.m_prefPoolEdit.SaveGlobalVariable();
        this.m_prefEditMeta.SetEditSelPos(m_EditMeta.GetSelPosList());
        this.m_prefEditMeta.SaveGlobalVariable();
    }

    public void SetEditMetaListener(EditMetaListener editMetaListener) {
        this.m_EditMetaListener = editMetaListener;
    }

    private boolean HaveEditMetaListener() {
        if (this.m_EditMetaListener == null) {
            return false;
        }
        return true;
    }

    public void FetchPhoto(int pos, int iImgID, int iImgSID) {
        this.m_iPos = pos;
        if (HaveEditMetaListener()) {
            this.m_EditMetaListener.FetchingBegin();
        }
        byte[] byID = ByteConvertUtility.IntToByte(iImgID);
        byte[] bySID = ByteConvertUtility.IntToByte(iImgSID);
        Log.e("Fetch_IP", XmlPullParser.NO_NAMESPACE + this.IP);
        Log.e("Fetch_port", XmlPullParser.NO_NAMESPACE + this.m_iPort);
        this.m_fetchImage = new HitiPPR_GetImageData(this.m_context, this.IP, this.m_iPort, this.m_editHandler);
        this.m_fetchImage.PutIDs(byID, bySID);
        this.m_fetchImage.start();
    }

    public void FetchImageStop() {
        if (this.m_fetchImage != null) {
            this.m_fetchImage.Stop();
        }
    }

    public void InitDrawView(int iPos) {
        Message msg = new Message();
        msg.what = RequestState.REQUEST_INIT_DWAW_VIEW;
        msg.arg1 = iPos;
        this.m_editHandler.sendMessageDelayed(msg, 50);
    }

    public void DeleteFLValueData() {
        Message msg = new Message();
        msg.what = NNTPReply.MORE_AUTH_INFO_REQUIRED;
        this.m_editHandler.sendMessageDelayed(msg, 50);
    }

    public void DrawViewEnd(int iPos) {
        Message msg = new Message();
        msg.arg1 = iPos;
        msg.what = RequestState.REQUEST_INIT_DWAW_VIEW_END;
        this.m_editHandler.sendMessage(msg);
    }

    public void onSaveEditPhoto() {
        Message msg = new Message();
        msg.what = RequestState.REQUEST_SAVE_RDIT_PHOTO;
        this.m_editHandler.sendMessageDelayed(msg, 50);
    }

    public void SaveEditPhotoDone() {
        Message msg = new Message();
        msg.what = RequestState.REQUEST_SAVE_RDIT_PHOTO_DONE;
        this.m_editHandler.sendMessage(msg);
    }

    public Bitmap ChangeColor(Bitmap inBmp, int iPoint) {
        int iHeight = inBmp.getHeight();
        int iWidth = inBmp.getWidth();
        Bitmap outBmp = Bitmap.createBitmap(iWidth, iHeight, inBmp.getConfig());
        for (int j = 0; j < iHeight; j++) {
            for (int i = 0; i < iWidth; i++) {
                float[] fFixRGB = ColorModifier(RGBcolor(inBmp.getPixel(i, j)), iPoint);
                outBmp.setPixel(i, j, Color.rgb((int) fFixRGB[0], (int) fFixRGB[1], (int) fFixRGB[2]));
            }
        }
        return outBmp;
    }

    private float[] RGBcolor(int color) {
        return new float[]{(float) Color.red(color), (float) Color.green(color), (float) Color.blue(color)};
    }

    private float[] ColorModifier(float[] fOriRGB, int iPoint) {
        return GetFixRGB(fOriRGB, GetRatio(iPoint));
    }

    private float[] GetFixRGB(float[] fOriRGB, float fRatio) {
        CIELab cieLab = CIELab.getInstance();
        float[] fMinLab = cieLab.FromRGBtoLab(new float[]{0.0f, 0.0f, 0.0f});
        float[] fMaxLab = cieLab.FromRGBtoLab(new float[]{255.0f, 255.0f, 255.0f});
        float[] fOriLab = cieLab.FromRGBtoLab(fOriRGB);
        float[] fDiff = new float[3];
        float[] fFixLab = new float[3];
        float[] fFixRGB = new float[3];
        for (int i = 0; i < fOriLab.length; i++) {
            if (fRatio > 0.0f) {
                fDiff[i] = fMaxLab[i] - fOriLab[i];
            } else {
                fDiff[i] = fOriLab[i] - fMinLab[i];
            }
            fFixLab[i] = fOriLab[i] + (fDiff[i] * fRatio);
        }
        return cieLab.FromLabToRGB(fFixLab);
    }

    private float GetRatio(int iPoint) {
        int iMid = 100 / 2;
        return ((float) (iPoint - 50)) / ((float) 50);
    }

    public BitmapMonitorResult ModifyRGB(Bitmap oriBmp, float fRed, float fGreen, float fBlue, int half) {
        if (oriBmp == null) {
            return null;
        }
        float R = (((float) half) + fRed) / ((float) half);
        float G = (((float) half) + fGreen) / ((float) half);
        float B = (((float) half) + fBlue) / ((float) half);
        float[] rgb = new float[]{R, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, G, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, B, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        new ColorMatrix().set(rgb);
        BitmapMonitorResult modifyBmr = BitmapMonitor.CreateBitmap(oriBmp.getWidth(), oriBmp.getHeight(), oriBmp.getConfig());
        Canvas canvas = new Canvas(modifyBmr.GetBitmap());
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(rgb));
        canvas.drawBitmap(oriBmp, 0.0f, 0.0f, paint);
        oriBmp.recycle();
        return modifyBmr;
    }
}
