package com.example.sonic.hitiprinter.ui.cacheadapter.viewholder;

import android.widget.ImageView;

public class BaseViewHolder implements Comparable<BaseViewHolder> {
    public Type mType;
    public ImageView m_HolderImageView;
    public int m_iID;

    public enum Type {
        Thumbnail,
        Glide,
        Index
    }

    public BaseViewHolder() {
        this.m_iID = -1;
        this.m_HolderImageView = null;
        this.mType = Type.Thumbnail;
    }

    public int compareTo(BaseViewHolder obj) {
        int iRet = 0;
        if (this.m_iID > obj.m_iID) {
            iRet = 1;
        }
        if (this.m_iID < obj.m_iID) {
            return -1;
        }
        return iRet;
    }
}
