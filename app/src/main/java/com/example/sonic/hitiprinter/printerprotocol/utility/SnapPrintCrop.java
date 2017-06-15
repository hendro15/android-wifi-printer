package com.example.sonic.hitiprinter.printerprotocol.utility;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;

public class SnapPrintCrop extends AsyncTask<String, String, BitmapMonitorResult> {
    Context m_Context;
    boolean m_bCropSuceess;
    int m_iHeight;
    int m_iWidth;
    SnapCropListener m_listener;
    String m_strOriPhotoUri;

    public interface SnapCropListener {
        void CropFail(String str);

        void CropSuccess(BitmapMonitorResult bitmapMonitorResult);

        void SetProgress(boolean z);
    }

    public SnapPrintCrop(Context context, int iWidth, int iHeight) {
        this.m_strOriPhotoUri = null;
        this.m_Context = null;
        this.m_iWidth = 0;
        this.m_iHeight = 0;
        this.m_bCropSuceess = false;
        this.m_listener = null;
        this.m_Context = context;
        this.m_iWidth = iWidth;
        this.m_iHeight = iHeight;
    }

    public void SetSnapListener(SnapCropListener listener) {
        this.m_listener = listener;
    }

    protected void onPreExecute() {
        if (this.m_listener != null) {
            this.m_listener.SetProgress(true);
        }
    }

    protected BitmapMonitorResult doInBackground(String... strPath) {
        Uri uri;
        this.m_strOriPhotoUri = strPath[0];
        if (this.m_strOriPhotoUri.contains("file://")) {
            uri = Uri.parse(this.m_strOriPhotoUri);
        } else {
            uri = Uri.parse("file://" + this.m_strOriPhotoUri);
        }
        try {
            BitmapMonitorResult bmr = BitmapMonitor.CreateCroppedBitmapNew(this.m_Context, uri, this.m_iWidth, this.m_iHeight);
            if (!bmr.IsSuccess()) {
                return bmr;
            }
            if (this.m_listener != null) {
                this.m_listener.SetProgress(false);
            }
            this.m_bCropSuceess = true;
            this.m_listener.CropSuccess(bmr);
            return bmr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onProgressUpdate(String... progress) {
    }

    protected void onPostExecute(BitmapMonitorResult result) {
        Log.v("SnapCrop", "result:" + result);
        if (!this.m_bCropSuceess) {
            if (this.m_listener != null) {
                this.m_listener.SetProgress(false);
            }
            if (result != null) {
                Log.v("SnapCrop", "resultCode:" + result.GetError(this.m_Context));
                if (!result.IsSuccess()) {
                    this.m_listener.CropFail(result.GetError(this.m_Context));
                    return;
                }
                return;
            }
            this.m_listener.CropFail(BitmapMonitorResult.GetError(this.m_Context, 99));
        }
    }
}
