package com.example.sonic.hitiprinter.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Pair;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.BaseViewHolder;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.BaseViewHolder.Type;

public class ImageViewCheck extends AsyncTask<BaseViewHolder, Void, Pair<BaseViewHolder, Pair<Boolean, Bitmap>>> {
    LogManager LOG;
    String TAG;
    IImageCheck mListener;
    boolean mStop;

    public interface IImageCheck {
        Bitmap GetThumbnail(int i);

        boolean LowQualityCheck(int i);

        void Next(BaseViewHolder baseViewHolder, Bitmap bitmap, boolean z);
    }

    public ImageViewCheck(IImageCheck listener) {
        this.mListener = null;
        this.mStop = false;
        this.LOG = null;
        this.TAG = "GetThumbnail";
        this.mListener = listener;
        this.LOG = new LogManager(0);
    }

    public void Stop() {
        this.mStop = true;
    }

    protected Pair<BaseViewHolder, Pair<Boolean, Bitmap>> doInBackground(BaseViewHolder... params) {
        BaseViewHolder holder = params[0];
        boolean isLow = false;
        this.LOG.m385i(this.TAG, "doInBackground: " + holder.m_iID);
        if (this.mStop) {
            return null;
        }
        if (holder.mType != Type.Index) {
            isLow = this.mListener.LowQualityCheck(holder.m_iID);
        }
        return new Pair(holder, new Pair(Boolean.valueOf(isLow), this.mListener.GetThumbnail(holder.m_iID)));
    }

    protected void onPostExecute(Pair<BaseViewHolder, Pair<Boolean, Bitmap>> pair) {
        if (!this.mStop) {
            this.LOG.m385i(this.TAG, "onPostExecute");
            this.mListener.Next(pair.first, (Bitmap) ((Pair) pair.second).second, ((Boolean) ((Pair) pair.second).first).booleanValue());
        }
    }

    public static boolean IsLowQuality(Context context, String mPhotoPath, int iLimitWidth, int iLimitHeight) {
        return BitmapMonitor.IsPhotoLowQuality(context, Uri.parse("file://" + mPhotoPath), iLimitWidth, iLimitHeight);
    }
}
