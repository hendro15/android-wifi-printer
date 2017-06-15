package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.TextUtils;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import com.example.sonic.hitiprinter.printerprotocol.utility.SnapPrintCrop.SnapCropListener;
import com.example.sonic.hitiprinter.trace.GlobalVariable_OtherSetting;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MediaUtil;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.utility.setting.DateInfo;
import com.example.sonic.hitiprinter.utility.setting.DateUtility;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;

public class MakePrintBitmap {
    LogManager LOG;
    String TAG;
    Context m_Context;
    PrintMode m_PrintMode;
    int m_iCropLongLimit;
    int m_iCropShortLimit;

    public interface MakeBitmapListener {
        void CropBitmapFail(String str);

        void CropBitmapSuccess(BitmapMonitorResult bitmapMonitorResult);
    }

    /* renamed from: com.hiti.printerprotocol.utility.MakePrintBitmap.1 */
    class C07981 implements SnapCropListener {
        final /* synthetic */ MakeBitmapListener val$listener;
        final /* synthetic */ long val$snapPhotoID;
        final /* synthetic */ String val$strSnapPath;

        C07981(String str, long j, MakeBitmapListener makeBitmapListener) {
            this.val$strSnapPath = str;
            this.val$snapPhotoID = j;
            this.val$listener = makeBitmapListener;
        }

        public void CropSuccess(BitmapMonitorResult bmr) {
            MediaUtil.DeleteMedia(MakePrintBitmap.this.m_Context, this.val$strSnapPath, this.val$snapPhotoID);
            if (this.val$listener != null) {
                this.val$listener.CropBitmapSuccess(bmr);
            }
        }

        public void CropFail(String strError) {
            MediaUtil.DeleteMedia(MakePrintBitmap.this.m_Context, this.val$strSnapPath, this.val$snapPhotoID);
            if (this.val$listener != null) {
                this.val$listener.CropBitmapFail(strError);
            }
        }

        public void SetProgress(boolean bRet) {
        }
    }

    public MakePrintBitmap(Context context) {
        this.m_Context = null;
        this.m_PrintMode = null;
        this.m_iCropLongLimit = 0;
        this.m_iCropShortLimit = 0;
        this.LOG = null;
        this.TAG = null;
        this.m_Context = context;
        this.LOG = new LogManager(0);
        this.TAG = context.getClass().getSimpleName();
    }

    public void SetCropValue(int iShortLimit, int iLongLimit) {
        this.m_iCropShortLimit = iShortLimit;
        this.m_iCropLongLimit = iLongLimit;
    }

    public void CropSnapPicture(String strSnapPath, long snapPhotoID, MakeBitmapListener listener) {
        SnapPrintCrop snapCropPhoto = new SnapPrintCrop(this.m_Context, this.m_iCropShortLimit, this.m_iCropLongLimit);
        snapCropPhoto.SetSnapListener(new C07981(strSnapPath, snapPhotoID, listener));
        snapCropPhoto.execute(new String[]{strSnapPath});
    }

    BitmapMonitorResult GetOutputBitmap(BitmapMonitorResult bmr, int iOutputWidth, int iOutPutHeight) {
        if (bmr == null) {
            return null;
        }
        Bitmap bmpPhoto = bmr.GetBitmap();
        int iOutputW = iOutputWidth;
        int iOutputH = iOutPutHeight;
        if (bmpPhoto.getWidth() < bmpPhoto.getHeight()) {
            int temp = iOutputW;
            iOutputW = iOutputH;
            iOutputH = temp;
        }
        float fX = (float) ((iOutputW - bmpPhoto.getWidth()) / 2);
        float fY = (float) ((iOutputH - bmpPhoto.getHeight()) / 2);
        bmr = BitmapMonitor.CreateBitmap(iOutputW, iOutputH, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            Bitmap bmp = bmr.GetBitmap();
            bmp.eraseColor(-1);
            new Canvas(bmp).drawBitmap(bmpPhoto, fX, fY, null);
            bmpPhoto.recycle();
            bmr.SetPixelWarning(0.0d, 0);
            return bmr;
        }
        bmpPhoto.recycle();
        return bmr;
    }

    BitmapMonitorResult CropBitmap(String strPhotoPath, int iSingleWidth, int iSingleHeight, int outWidth, int outHeight, DateInfo mPhotoDate) {
        if (strPhotoPath.isEmpty()) {
            return DrawWhitePhoto(outWidth, outHeight);
        }
        int temp;
        BitmapMonitorResult bmr;
        Uri uri = Uri.parse("file://" + strPhotoPath);
        double dScale = 0.0d;
        int iSampleSize = 0;
        this.LOG.m386v(this.TAG, "CropBitmap " + strPhotoPath);
        if (BitmapMonitor.IsVertical(this.m_Context, uri)) {
            if (iSingleWidth > iSingleHeight) {
                temp = iSingleWidth;
                iSingleWidth = iSingleHeight;
                iSingleHeight = temp;
            }
            if (outWidth > outHeight) {
                temp = outWidth;
                outWidth = outHeight;
                outHeight = temp;
            }
        } else {
            if (iSingleWidth < iSingleHeight) {
                temp = iSingleWidth;
                iSingleWidth = iSingleHeight;
                iSingleHeight = temp;
            }
            if (outWidth < outHeight) {
                temp = outWidth;
                outWidth = outHeight;
                outHeight = temp;
            }
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapMonitor.CreateBitmap(new BufferedInputStream(this.m_Context.getContentResolver().openInputStream(uri)), null, options, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int iOrgWidth = options.outWidth;
        int iOrgHeight = options.outHeight;
        if (iSingleWidth == iOrgWidth && iSingleHeight == iOrgHeight) {
            bmr = BitmapMonitor.CreateBitmap(strPhotoPath, true);
        } else {
            bmr = BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, uri, iSingleWidth, iSingleHeight);
            dScale = bmr.GetScale();
            iSampleSize = bmr.GetSampleSize();
        }
        this.LOG.m385i(this.TAG, "CropBitmap GetResult: " + bmr.GetResult());
        if (!bmr.IsSuccess()) {
            return bmr;
        }
        Bitmap bmpPhoto = bmr.GetBitmap();
        int iOutputW = outWidth;
        int iOutputH = outHeight;
        if (BitmapMonitor.IsVertical(this.m_Context, uri) && iOutputW > iOutputH) {
            temp = iOutputW;
            iOutputW = iOutputH;
            iOutputH = temp;
        }
        if (iOutputW == bmpPhoto.getWidth() && iOutputH == bmpPhoto.getHeight()) {
            AddDateOnPhoto(bmpPhoto, mPhotoDate);
            return bmr;
        }
        float fX = (float) ((iOutputW - bmpPhoto.getWidth()) / 2);
        float fY = (float) ((iOutputH - bmpPhoto.getHeight()) / 2);
        bmr = BitmapMonitor.CreateBitmap(iOutputW, iOutputH, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            Bitmap bmp = bmr.GetBitmap();
            bmp.eraseColor(-1);
            new Canvas(bmp).drawBitmap(bmpPhoto, fX, fY, null);
            bmpPhoto.recycle();
            bmr.SetPixelWarning(dScale, iSampleSize);
            AddDateOnPhoto(bmp, mPhotoDate);
            return bmr;
        }
        bmpPhoto.recycle();
        return bmr;
    }

    void AddDateOnPhoto(Bitmap photo, DateInfo mPhotoDate) {
        GlobalVariable_OtherSetting m_pref = new GlobalVariable_OtherSetting(this.m_Context);
        m_pref.RestoreGlobalVariable();
        this.LOG.m386v(this.TAG, "AddDateOnPhoto GetApplyAddDate: " + m_pref.GetApplyAddDate());
        if (m_pref.GetApplyAddDate() != 0 && photo != null && mPhotoDate != null && !TextUtils.isEmpty(mPhotoDate.GetDate())) {
            DateUtility.AddDateOnPhoto(photo, mPhotoDate);
        }
    }

    BitmapMonitorResult Make2upPhoto(String strPhotoPath1, String strPhotoPath2, boolean bReverse, int iWidth, int iHeight, int iOutWidth, int iOutHeight, DateInfo mPhotoDate) {
        this.LOG.m385i(this.TAG, "Make2upPhoto iWidth: " + iWidth);
        this.LOG.m385i(this.TAG, "Make2upPhoto iHeight: " + iHeight);
        BitmapMonitorResult bmr1 = MakeOnePhoto(strPhotoPath1, iWidth, iHeight, bReverse, mPhotoDate);
        BitmapMonitorResult bmr2;
        if (bmr1.IsSuccess()) {
            bmr2 = MakeOnePhoto(strPhotoPath2, iWidth, iHeight, bReverse, mPhotoDate);
            if (bmr2.IsSuccess()) {
                return DrawTwoCanvas(bmr1, bmr2, bReverse, iOutWidth, iOutHeight);
            }
            return bmr2;
        }
        bmr2 = null;
        return bmr1;
    }

    BitmapMonitorResult MakeOnePhoto(String strPhotoPath, int iWidth, int iHeight, boolean bReverse, DateInfo mPhotoDate) {
        this.LOG.m385i(this.TAG, "MakeOnePhoto " + strPhotoPath);
        Bitmap bmpPhoto;
        if (strPhotoPath.isEmpty()) {
            bmpPhoto = null;
            return DrawWhitePhoto(iWidth, iHeight);
        }
        if (BitmapMonitor.IsVertical(this.m_Context, Uri.parse("file://" + strPhotoPath))) {
            bReverse = false;
        }
        BitmapMonitorResult bmr = CropBitmap(strPhotoPath, iWidth, iHeight, iWidth, iHeight, mPhotoDate);
        if (bmr.IsSuccess()) {
            Matrix matrix;
            Bitmap bmp;
            bmpPhoto = bmr.GetBitmap();
            this.LOG.m385i(this.TAG, "crop iWidth: " + bmpPhoto.getWidth());
            this.LOG.m385i(this.TAG, "crop iHeight: " + bmpPhoto.getHeight());
            if (bmpPhoto.getWidth() < bmpPhoto.getHeight()) {
                matrix = new Matrix();
                matrix.postRotate(90.0f);
                bmp = Bitmap.createBitmap(bmpPhoto, 0, 0, bmpPhoto.getWidth(), bmpPhoto.getHeight(), matrix, true);
                bmpPhoto.recycle();
            } else {
                bmp = bmpPhoto;
            }
            if (bReverse) {
                matrix = new Matrix();
                matrix.postRotate(180.0f);
                bmpPhoto = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                bmp.recycle();
                bmp = bmpPhoto;
            }
            bmr.SetBitmap(bmp);
            if (bmp == null) {
                bmr.SetResult(99);
            } else {
                bmr.SetResult(0);
                this.LOG.m385i(this.TAG, "rotate iWidth: " + bmp.getWidth());
                this.LOG.m385i(this.TAG, "rotate iHeight: " + bmp.getHeight());
            }
            return bmr;
        }
        bmpPhoto = null;
        return bmr;
    }

    private BitmapMonitorResult DrawTwoCanvas(BitmapMonitorResult bmr1, BitmapMonitorResult bmr2, boolean bReverse, int iOutputW, int iOutputH) {
        BitmapMonitorResult bmr;
        if (bmr1.IsSuccess() && bmr2.IsSuccess()) {
            Bitmap bmpPhoto1 = bmr1.GetBitmap();
            Bitmap bmpPhoto2 = bmr2.GetBitmap();
            bmr = BitmapMonitor.CreateBitmap(iOutputW, iOutputH, Config.ARGB_8888);
            if (bmr.IsSuccess()) {
                Bitmap bmp = bmr.GetBitmap();
                bmp.eraseColor(-1);
                Canvas canvas = new Canvas(bmp);
                canvas.drawBitmap(bmpPhoto1, 0.0f, !bReverse ? 0.0f : (float) (iOutputH - bmpPhoto2.getHeight()), null);
                canvas.drawBitmap(bmpPhoto2, 0.0f, bReverse ? 0.0f : (float) (iOutputH - bmpPhoto2.getHeight()), null);
                bmpPhoto1.recycle();
                bmpPhoto2.recycle();
                return bmr;
            }
            bmpPhoto1.recycle();
            bmpPhoto2.recycle();
            return bmr;
        }
        bmr = new BitmapMonitorResult();
        bmr.SetBitmap(null);
        bmr.SetResult(99);
        return bmr;
    }

    public static BitmapMonitorResult DrawWhitePhoto(int width, int height) {
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(width, height, Config.ARGB_8888);
        if (bmr.IsSuccess()) {
            bmr.GetBitmap().eraseColor(-1);
        }
        return bmr;
    }
}
