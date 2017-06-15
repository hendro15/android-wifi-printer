package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import com.example.sonic.hitiprinter.ImageFilter.ImageFilter;
import com.example.sonic.hitiprinter.ImageFilter.ImageFilter.IMAGE_FILTER_TYPE;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import com.example.sonic.hitiprinter.jumpinfo.JumpPreferenceKey;
import com.example.sonic.hitiprinter.printerprotocol.RequestState;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommand;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_SendPhotoPrinbiz;
import com.example.sonic.hitiprinter.printerprotocol.request.SendPhotoListener;
import com.example.sonic.hitiprinter.ui.edmview.EDMView.EDMViewHandler.ControllerState;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.utility.setting.DateInfo;
import java.net.Socket;
import org.xmlpull.v1.XmlPullParser;

public class PrepareSendData extends AsyncTask<String, Void, Bitmap> {
    LogManager LOG;
    String TAG;
    private SendPhotoInfo mBufferInfo;
    private Context mContext;
    private int mCountForSizeChange;
    private String mIP;
    MakePrintBitmap mMakeBitmap;
    private int mMaxHeight;
    private int mMaxWidth;
    private MobileUtility.MobileHandler mMobileHandler;
    private int mOutputHeight;
    private int mOutputWeidth;
    DateInfo mPhotoDate;
    private int mPort;
    private SendPhotoListener mPrePhotoListener;
    private SendPhotoInfo mPrintInfo;
    private PrintMode mPrintMode;
    private String mPrinterModel;
    HitiPPR_PrinterCommand m_PrintCommand;
    private Socket m_Socket;
    private int m_iResult;
    private boolean mbAskedLowQty;
    private boolean mbAskedScale;
    private boolean mbAskedSize;
    private boolean mbAskingState;
    private boolean mbNeedUnSharpen;
    private boolean mbSkipPhoto;
    private int miPathRoute;

    public PrepareSendData(Context context, int maxWidth, int maxHeight, int outputWidth, int outputHeight) {
        this.m_iResult = 0;
        this.m_Socket = null;
        this.m_PrintCommand = null;
        this.mMaxWidth = 0;
        this.mMaxHeight = 0;
        this.mOutputWeidth = 0;
        this.mOutputHeight = 0;
        this.mContext = null;
        this.mIP = XmlPullParser.NO_NAMESPACE;
        this.mPort = 0;
        this.mMobileHandler = null;
        this.mPrePhotoListener = null;
        this.mPrintMode = null;
        this.mPrinterModel = XmlPullParser.NO_NAMESPACE;
        this.mbNeedUnSharpen = false;
        this.miPathRoute = 0;
        this.mPrintInfo = null;
        this.mBufferInfo = null;
        this.mCountForSizeChange = 0;
        this.mbAskingState = false;
        this.mbAskedSize = false;
        this.mbAskedScale = false;
        this.mbAskedLowQty = false;
        this.mbSkipPhoto = false;
        this.mMakeBitmap = null;
        this.mPhotoDate = null;
        this.TAG = null;
        this.LOG = null;
        this.mMakeBitmap = new MakePrintBitmap(context);
        this.mOutputWeidth = outputWidth;
        this.mOutputHeight = outputHeight;
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
        this.mContext = context;
        this.TAG = "PrepareSendData";
        this.LOG = new LogManager(0);
    }

    public void SetHadlerAndListener(MobileUtility.MobileHandler mMobileHandler, SendPhotoListener mPrePhotoListener) {
        this.mMobileHandler = mMobileHandler;
        this.mPrePhotoListener = mPrePhotoListener;
    }

    private boolean HavePrePhotoListener() {
        return this.mPrePhotoListener != null;
    }

    public void SetSocketAttribute(String IP, int iPort, HitiPPR_PrinterCommand mPrintCommand) {
        this.m_Socket = mPrintCommand == null ? null : mPrintCommand.GetSocket();
        this.m_PrintCommand = mPrintCommand;
        this.mIP = IP;
        this.mPort = iPort;
    }

    public void SetPrintAttribute(PrintMode mPrintMode, int miPathRoute, boolean mbNeedUnSharpen, SendPhotoInfo mPrintInfo) {
        this.mPrintMode = mPrintMode;
        this.mbNeedUnSharpen = mbNeedUnSharpen;
        this.miPathRoute = miPathRoute;
        this.mPrintInfo = mPrintInfo;
        this.mPrinterModel = mPrintInfo.GetPrinterModel();
    }

    public void GetAskState(boolean mbAskedSize, boolean mbAskedScale, boolean mbAskedLowQty) {
        this.mbAskedSize = mbAskedSize;
        this.mbAskedScale = mbAskedScale;
        this.mbAskedLowQty = mbAskedLowQty;
    }

    public void SetPhotoDate(DateInfo photoDate) {
        this.mPhotoDate = photoDate;
        this.LOG.m386v(this.TAG, "SetPhotoDate: " + this.mPhotoDate.GetDate());
        this.LOG.m386v(this.TAG, "GetColor: " + this.mPhotoDate.GetColor());
    }

    protected Bitmap doInBackground(String... strPhotoPath) {
        Bitmap photo = null;
        String mPhotoPath = strPhotoPath[0];
        if (mPhotoPath == null) {
            return null;
        }
        this.LOG.m386v(this.TAG, "doInBackground mPhotoPath: " + mPhotoPath);
        if (this.mPrintInfo.GetPaperType() == 6) {
            BitmapMonitorResult bmr = Make6x8_2up(CreatePhotoBuffer(strPhotoPath));
            photo = bmr.GetBitmap();
            this.m_iResult = bmr.GetResult();
        } else if (this.mPrintInfo.GetPaperType() != 8 && this.mPrintInfo.GetPaperType() != 4) {
            photo = GeneralCreateBitmap(mPhotoPath);
        } else if (strPhotoPath.length > 1) {
            Make6x6duplex(CreatePhotoBuffer(strPhotoPath));
        } else if (strPhotoPath.length == 1) {
            photo = GeneralCreateBitmap(strPhotoPath[0]);
        }
        this.LOG.m383d(this.TAG, new StringBuilder().append("doInBackground photo: ").append(photo).toString() == null ? "null " : "exist " + MobileInfo.GetHmsSStamp());
        return photo;
    }

    public void onProgressUpdate(Void... params) {
    }

    protected void onPostExecute(Bitmap result) {
        this.LOG.m385i(this.TAG, "onPostExecute: m_iResult: " + this.m_iResult);
        if (this.mbAskingState) {
            if (HavePrePhotoListener()) {
                this.mPrePhotoListener.SetAskState(this.mbAskedSize, this.mbAskedScale, this.mbAskedLowQty);
            }
            this.mbAskingState = false;
            return;
        }
        this.mCountForSizeChange = 0;
        this.mbAskedScale = false;
        this.mbAskedLowQty = false;
        if (this.mPrinterModel.equals(WirelessType.TYPE_P530D)) {
            if (this.m_iResult != 0) {
                if (this.m_PrintCommand != null) {
                    this.m_PrintCommand.Stop();
                }
                if (HavePrePhotoListener()) {
                    this.mPrePhotoListener.onCreateBitmapError(BitmapMonitorResult.GetError(this.mContext, this.m_iResult));
                    return;
                }
                return;
            } else if (this.mPrintInfo.GetPaperType() == 6 || this.mPrintInfo.GetDuplex()) {
                this.LOG.m386v(this.TAG, "onPostExecute: mbDuplex: " + this.mBufferInfo.mbDuplex);
                if (this.mBufferInfo.mbDuplex) {
                    ReadyToSendPhoto(this.mBufferInfo.GetBitmap(0), this.m_Socket, this.mBufferInfo.GetBitmap(1));
                    return;
                } else {
                    ReadyToSendPhoto(this.mBufferInfo.GetBitmap(0), this.m_Socket, null);
                    return;
                }
            }
        }
        if (result != null) {
            ReadyToSendPhoto(result, this.m_Socket, null);
        } else if (!this.mbSkipPhoto) {
        } else {
            if (HavePrePhotoListener()) {
                this.mPrePhotoListener.SkipToNextPhoto(this.m_Socket);
                return;
            }
            if (this.m_PrintCommand != null) {
                this.m_PrintCommand.Stop();
            }
            if (HavePrePhotoListener()) {
                this.mPrePhotoListener.onCreateBitmapError(BitmapMonitorResult.GetError(this.mContext, this.m_iResult));
            }
        }
    }

    private SendPhotoInfo CreatePhotoBuffer(String[] strPhotoPath) {
        this.mBufferInfo = new SendPhotoInfo(this.mPrintInfo.GetPrinterModel());
        this.mBufferInfo.Copy(strPhotoPath, this.mPrintInfo.GetDuplex());
        this.LOG.m386v(this.TAG, "doInBackground length:" + strPhotoPath.length);
        return this.mBufferInfo;
    }

    private BitmapMonitorResult Make6x8_2up(SendPhotoInfo mBufferInfo) {
        BitmapMonitorResult bmr = null;
        String[] strPhotoPath = mBufferInfo.GetBuffer();
        if (strPhotoPath.length == 2) {
            bmr = Make6x8_2upPhoto(strPhotoPath, false);
            if (bmr.IsSuccess()) {
                return bmr;
            }
        } else if (strPhotoPath.length == 4) {
            bmr = Make6x8_2upPhoto(strPhotoPath, false);
            if (bmr.IsSuccess()) {
                bmr = Make6x8_2upPhoto(mBufferInfo.GetBuffer(2, 0), true);
            }
        }
        return bmr;
    }

    private BitmapMonitorResult Make6x8_2upPhoto(String[] strPhotoPath, boolean bReverse) {
        BitmapMonitorResult bmr = this.mMakeBitmap.Make2upPhoto(strPhotoPath[0], strPhotoPath[1], bReverse, this.mMaxWidth, this.mMaxHeight, this.mOutputWeidth, this.mOutputHeight, this.mPhotoDate);
        if (bmr.IsSuccess()) {
            this.mBufferInfo.AddBitmap(bmr.GetBitmap());
        }
        this.mBufferInfo.Remove(2);
        return bmr;
    }

    private void Make6x6duplex(SendPhotoInfo mBufferInfo) {
        String[] strPhotoPath = mBufferInfo.GetBuffer();
        if (strPhotoPath.length == 2 && Make6x6Photo(strPhotoPath[0], false) != null) {
            Make6x6Photo(mBufferInfo.GetBuffer()[0], true);
        }
    }

    private Bitmap Make6x6Photo(String mPhotoPath, boolean bReverse) {
        Bitmap bitmap = GeneralCreateBitmap(mPhotoPath);
        if (bitmap != null) {
            this.mBufferInfo.AddBitmap(bitmap);
        }
        this.mBufferInfo.Remove(1);
        return bitmap;
    }

    private Bitmap GeneralCreateBitmap(String mPhotoPath) {
        Uri uri;
        Bitmap photo = null;
        BitmapMonitorResult bmr = null;
        float fWidth = (float) this.mMaxWidth;
        float fHeight = (float) this.mMaxHeight;
        JumpPreferenceKey jumpPreferenceKey = new JumpPreferenceKey(this.mContext);
        boolean bSizeDownSelAll = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.SIZE_DOWN_SEL_ALL);
        boolean bScaleSelAll = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.SCALL_SEL_ALL);
        boolean bLowQtySelAll = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.LOW_QUALITY_SEL_ALL);
        boolean bSizeDown = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.SIZE_DOWN_WILL);
        boolean bScaleToLarge = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.SCALL_SEL_WILL);
        boolean bLowQtyWill = jumpPreferenceKey.GetStatePreference(JumpPreferenceKey.LOW_QUALITY_WILL);
        this.LOG.m385i(this.TAG, "GeneralCreateBitmap " + mPhotoPath);
        this.LOG.m385i(this.TAG, "PrintMode: " + this.mPrintMode);
        if (mPhotoPath.isEmpty()) {
            uri = null;
        } else {
            uri = Uri.parse("file://" + mPhotoPath);
        }
        if (this.mPrintMode == PrintMode.EditPrint || !this.mbNeedUnSharpen || this.miPathRoute == ControllerState.PLAY_PHOTO) {
            this.LOG.m385i(this.TAG, "mbNeedUnSharpen: " + this.mbNeedUnSharpen);
            this.LOG.m385i(this.TAG, "3mPrintMode: " + this.mPrintMode);
            bmr = this.mMakeBitmap.CropBitmap(mPhotoPath, this.mMaxWidth, this.mMaxHeight, this.mOutputWeidth, this.mOutputHeight, this.mPhotoDate);
            if (bmr == null || !bmr.IsSuccess()) {
                this.m_iResult = bmr.GetResult();
                return null;
            } else if (bmr.IsSuccess()) {
                photo = bmr.GetBitmap();
            } else {
                this.m_iResult = bmr.GetResult();
                return null;
            }
        }
        boolean bLowQty;
        this.LOG.m385i(this.TAG, "!!!mPhotoPath: " + mPhotoPath);
        if (uri == null) {
            bLowQty = false;
        } else {
            bLowQty = BitmapMonitor.IsPhotoLowQuality(this.mContext, uri, this.mMaxWidth, this.mMaxHeight);
        }
        this.LOG.m385i(this.TAG, "!!!bLowQty: " + bLowQty);
        if (bLowQty) {
            if (this.mbAskedLowQty) {
                this.mbAskingState = false;
            } else if (bLowQtySelAll) {
                this.mbAskedLowQty = true;
                this.mbAskingState = false;
            } else {
                this.mMobileHandler.sendEmptyMessage(RequestState.REQUEST_CREATE_BITMAP_LOW_QTY);
                this.mbAskedLowQty = true;
                this.mbAskingState = true;
                return null;
            }
            this.LOG.m385i(this.TAG, "!!!bLowQtyWill: " + bLowQtyWill);
            if (bLowQtyWill) {
                bmr = this.mMakeBitmap.CropBitmap(mPhotoPath, (int) fWidth, (int) fHeight, this.mOutputWeidth, this.mOutputHeight, this.mPhotoDate);
                if (bmr == null || !bmr.IsSuccess()) {
                    this.m_iResult = bmr.GetResult();
                    return null;
                }
                photo = bmr.GetBitmap();
            } else {
                this.mbSkipPhoto = true;
                return null;
            }
        }
        int i = this.mCountForSizeChange;
        while (i < 5) {
            this.mbAskingState = false;
            if (i > 0) {
                if (!this.mbAskedSize) {
                    if (bSizeDownSelAll) {
                        this.mbAskedSize = true;
                    } else {
                        this.mMobileHandler.sendEmptyMessage(RequestState.REQUEST_CREATE_BITMAP_SIZE_CHANGE);
                        this.mCountForSizeChange = i;
                        this.mbAskedSize = true;
                        this.mbAskingState = true;
                        return null;
                    }
                }
                if (bSizeDown) {
                    fWidth *= 0.8f;
                    fHeight *= 0.8f;
                } else {
                    this.mbSkipPhoto = true;
                    return null;
                }
            }
            bmr = this.mMakeBitmap.CropBitmap(mPhotoPath, (int) fWidth, (int) fHeight, this.mOutputWeidth, this.mOutputHeight, this.mPhotoDate);
            if (bmr.GetScale() > 1.0d) {
                if (!this.mbAskedScale) {
                    if (bScaleSelAll) {
                        this.mbAskedScale = true;
                    } else {
                        this.mMobileHandler.sendEmptyMessage(RequestState.REQUEST_CREATE_BITMAP_SCALE_HINT);
                        this.mbAskingState = true;
                        this.mbAskedScale = true;
                        return null;
                    }
                }
                if (!bScaleToLarge) {
                    this.mbSkipPhoto = true;
                    return null;
                }
            }
            if (bmr.IsSuccess()) {
                i = 5;
                photo = bmr.GetBitmap();
            } else {
                i++;
                if (i >= 5) {
                    this.m_iResult = bmr.GetResult();
                    return null;
                }
            }
        }
        this.LOG.m383d(this.TAG, "mbNeedUnSharpen: " + this.mbNeedUnSharpen + " " + MobileInfo.GetTimeReocrd());
        if (this.mbNeedUnSharpen && photo != null) {
            this.LOG.m383d(this.TAG, "imageFilter");
            if (!new ImageFilter(this.mContext).ProcessImage(photo, IMAGE_FILTER_TYPE.IMAGE_FILTER_TYPE_UnSharpMask)) {
                photo.recycle();
                this.m_iResult = 99;
                return null;
            }
        }
        this.m_iResult = bmr.GetResult();
        this.LOG.m383d(this.TAG, "imageFilter result:" + this.m_iResult + " " + MobileInfo.GetTimeReocrd());
        return photo;
    }

    private void ReadyToSendPhoto(Bitmap photo, Socket socket, Bitmap photo2) {
        this.LOG.m385i(this.TAG, new StringBuilder().append("ReadyToSendPhoto: photo: ").append(photo).toString() == null ? "null" : "exist");
        this.LOG.m385i(this.TAG, new StringBuilder().append("ReadyToSendPhoto: photo2: ").append(photo2).toString() == null ? "null" : "exist");
        int iCopies = 0;
        HitiPPR_SendPhotoPrinbiz mPrintSendPhoto = new HitiPPR_SendPhotoPrinbiz(this.mContext, this.mIP, this.mPort, this.mMobileHandler);
        mPrintSendPhoto.SetTextTurePrint(this.mPrintInfo.GetTexture());
        mPrintSendPhoto.SetMediaSize(this.mPrintInfo.GetMediaSize());
        mPrintSendPhoto.SetPrintout(this.mPrintInfo.GetPrintout());
        mPrintSendPhoto.SetMethodQTY(this.mPrintInfo.GetQuality());
        mPrintSendPhoto.SetSharpenValue(this.mPrintInfo.GetSharpen());
        mPrintSendPhoto.SetDuplex(this.mPrintInfo.GetDuplex());
        mPrintSendPhoto.SetListener(this.mPrePhotoListener);
        if (HavePrePhotoListener()) {
            iCopies = this.mPrePhotoListener.GetCopies();
        }
        mPrintSendPhoto.SetSocket(socket);
        mPrintSendPhoto.SetCopies(iCopies);
        if (HavePrePhotoListener()) {
            this.mPrePhotoListener.SendingPhoto(this.mPrintMode);
        }
        boolean bRet = mPrintSendPhoto.PreparePhoto(photo, null, photo2);
        if (photo != null) {
            photo.recycle();
        }
        if (photo2 != null) {
            photo2.recycle();
        }
        this.LOG.m386v(this.TAG, "ReadyToSendPhoto: PreparePhoto make photo: " + bRet);
        if (bRet) {
            if (HavePrePhotoListener()) {
                this.mPrePhotoListener.GetHitiPPR_SendPhotoPrinbiz(mPrintSendPhoto);
            }
            mPrintSendPhoto.start();
        }
    }
}
