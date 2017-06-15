package com.example.sonic.hitiprinter.utility.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.AlbumViewHolder;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.BaseViewHolder;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.CacheBmp;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.GalleryViewHolder;
import com.example.sonic.hitiprinter.ui.cacheadapter.viewholder.PenddingGroup;
import com.example.sonic.hitiprinter.utility.ImageViewCheck;
import com.example.sonic.hitiprinter.utility.ImageViewCheck.IImageCheck;
import com.example.sonic.hitiprinter.utility.LogManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageAdapter extends BaseAdapter {
    public static final int CATCH_BITMAP_SIZE = 32;
    public static final int PENDDING_SIZE = 40;
    LogManager LOG;
    String TAG;
    ArrayList<Integer> mErrorThumbList;
    ImageViewCheck mImageViewCheck;
    LayoutInflater mInflater;
    Map<Integer, Boolean> mLowQualityList;
    protected boolean mStop;
    Type mType;
    CacheBmp m_CacheBmp;
    PenddingGroup<BaseViewHolder> m_PenddingGroup;

    public enum Type {
        Album,
        Gallery,
        Index
    }

    /* renamed from: com.hiti.utility.grid.ImageAdapter.1 */
    class C08061 implements IImageCheck {
        C08061() {
        }

        public Bitmap GetThumbnail(int id) {
            return ImageAdapter.this.GetPhotoThumbnail(id);
        }

        public boolean LowQualityCheck(int id) {
            return ImageAdapter.this.PhotoQualityCheck(id);
        }

        public void Next(BaseViewHolder holder, Bitmap bmp, boolean isLow) {
            ImageAdapter.this.LOG.m386v("Next", "ID: " + holder.m_iID + " bmp= " + bmp);
            if (!ImageAdapter.this.mStop) {
                if (ImageAdapter.this.mType == Type.Gallery) {
                    ImageAdapter.this.QualityCheckAdd(holder.m_iID, isLow);
                    ((GalleryViewHolder) holder).m_QtyView.setVisibility(isLow ? 0 : 8);
                }
                if (bmp != null) {
                    holder.m_HolderImageView.setImageBitmap(bmp);
                    ImageAdapter.this.m_CacheBmp.AddCache(holder.m_iID, bmp);
                    holder.m_HolderImageView.invalidate();
                    ImageAdapter.this.AfterTryToSetImageView(holder, bmp);
                }
                ImageAdapter.this.m_PenddingGroup.RemoveFirstPenddingViewHolder();
                if (ImageAdapter.this.m_PenddingGroup.HavePendding()) {
                    ImageAdapter.this.Check((BaseViewHolder) ImageAdapter.this.m_PenddingGroup.GetFirstPenddingViewHolder());
                }
            }
        }
    }

    public abstract Bitmap GetPhotoThumbnail(int i);

    public abstract int GetTotalCout();

    public abstract View SetHolder(LayoutInflater layoutInflater, BaseViewHolder baseViewHolder);

    public abstract void SetImageView(BaseViewHolder baseViewHolder);

    public abstract void SetItem(BaseViewHolder baseViewHolder);

    public ImageAdapter(Context context, Type type) {
        this.mInflater = null;
        this.mImageViewCheck = null;
        this.m_PenddingGroup = null;
        this.mErrorThumbList = null;
        this.mLowQualityList = null;
        this.mType = Type.Gallery;
        this.mStop = false;
        this.m_CacheBmp = null;
        this.LOG = null;
        this.TAG = "ImageAdapter";
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.m_CacheBmp = new CacheBmp(CATCH_BITMAP_SIZE);
        this.m_PenddingGroup = new PenddingGroup(PENDDING_SIZE);
        this.mType = type;
        this.mErrorThumbList = new ArrayList();
        this.mLowQualityList = new HashMap();
        this.LOG = new LogManager(0);
        this.TAG = context.getClass().getSimpleName();
    }

    public int getCount() {
        return GetTotalCout();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.mStop) {
            return convertView;
        }
        BaseViewHolder holder = this.mType == Type.Album ? new AlbumViewHolder() : new GalleryViewHolder();
        if (convertView == null) {
            convertView = SetHolder(this.mInflater, holder);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
            ClearViewHodler(holder);
        }
        holder.m_iID = position;
        SetItem(holder);
        if (this.m_CacheBmp.IsCache(holder.m_iID)) {
            holder.m_HolderImageView.setImageBitmap(this.m_CacheBmp.GetCache(holder.m_iID));
        } else if (HaveError(position)) {
            PenddingCheck(holder);
        } else {
            SetImageView(holder);
        }
        ViewQualityCheck(holder, IsLowQuality(position));
        ViewSelectCheck(holder);
        return convertView;
    }

    public void ClearCache() {
        if (!this.mStop) {
            this.m_CacheBmp.ClearCache();
            this.m_PenddingGroup.ClearPendding();
            this.mErrorThumbList.clear();
        }
    }

    public void AfterBuildImage(BaseViewHolder holder, boolean bError) {
        if (!this.mStop) {
            if (bError) {
                ErrorItemAdd(holder.m_iID);
            }
            if (bError || !HaveQualityCheck(holder.m_iID)) {
                PenddingCheck(holder);
            } else {
                holder.m_HolderImageView.invalidate();
            }
        }
    }

    void PenddingCheck(BaseViewHolder holder) {
        if (!this.mStop) {
            this.m_PenddingGroup.AddPendding(holder);
            if (this.mImageViewCheck == null || this.mImageViewCheck.getStatus() == Status.FINISHED) {
                Check((BaseViewHolder) this.m_PenddingGroup.GetFirstPenddingViewHolder());
            }
        }
    }

    void Check(BaseViewHolder holder) {
        this.LOG.m385i(this.TAG, "Check ID:" + holder.m_iID);
        this.mImageViewCheck = new ImageViewCheck(new C08061());
        this.mImageViewCheck.execute(new BaseViewHolder[]{holder});
    }

    boolean HaveError(int id) {
        return this.mErrorThumbList.contains(Integer.valueOf(id));
    }

    void ErrorItemAdd(int id) {
        if (!HaveError(id)) {
            this.mErrorThumbList.add(Integer.valueOf(id));
        }
    }

    boolean HaveQualityCheck(int id) {
        if (this.mType != Type.Gallery) {
            return true;
        }
        return this.mLowQualityList.containsKey(Integer.valueOf(id));
    }

    void QualityCheckAdd(int id, boolean bLow) {
        if (!HaveQualityCheck(id)) {
            this.mLowQualityList.put(Integer.valueOf(id), Boolean.valueOf(bLow));
        }
    }

    boolean IsLowQuality(int id) {
        return this.mLowQualityList.containsKey(Integer.valueOf(id)) ? ((Boolean) this.mLowQualityList.get(Integer.valueOf(id))).booleanValue() : false;
    }

    public void Stop() {
        if (this.mImageViewCheck != null) {
            this.mImageViewCheck.Stop();
            this.mImageViewCheck.cancel(true);
        }
        this.mImageViewCheck = null;
        this.mStop = true;
    }

    public void ClearViewHodler(BaseViewHolder holder) {
    }

    public boolean PhotoQualityCheck(int id) {
        return false;
    }

    public void ViewQualityCheck(BaseViewHolder mHolder, boolean bLow) {
    }

    public void ViewSelectCheck(BaseViewHolder mHolder) {
    }

    protected void AfterTryToSetImageView(BaseViewHolder mHolder, Bitmap bitmap) {
    }
}
