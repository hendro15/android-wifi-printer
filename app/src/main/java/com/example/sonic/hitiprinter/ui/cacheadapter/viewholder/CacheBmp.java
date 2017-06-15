package com.example.sonic.hitiprinter.ui.cacheadapter.viewholder;

import android.graphics.Bitmap;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;

public class CacheBmp {
    private ArrayList<Pair<Integer, Bitmap>> m_CacheGroup;
    Object m_Lock;
    int m_iSize;

    public CacheBmp(int iSize) {
        this.m_Lock = new Object();
        this.m_CacheGroup = null;
        this.m_iSize = 0;
        this.m_CacheGroup = new ArrayList();
        this.m_iSize = iSize;
    }

    public boolean IsCache(int iID) {
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                if (((Integer) ((Pair) it.next()).first).intValue() == iID) {
                    return true;
                }
            }
            return false;
        }
    }

    public Bitmap GetCache(int iID) {
        Bitmap retBmp = null;
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                Pair<Integer, Bitmap> P = (Pair) it.next();
                if (((Integer) P.first).intValue() == iID) {
                    retBmp = (Bitmap) P.second;
                }
            }
        }
        return retBmp;
    }

    public boolean AddCache(int iID, Bitmap bmp) {
        if (!IsCache(iID)) {
            synchronized (this.m_Lock) {
                VictimOneCache();
                this.m_CacheGroup.add(new Pair(Integer.valueOf(iID), bmp));
            }
        }
        return true;
    }

    private void VictimOneCache() {
        if (this.m_CacheGroup.size() > this.m_iSize) {
            this.m_CacheGroup.remove(0);
        }
    }

    public void ClearCache() {
        synchronized (this.m_Lock) {
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                ((Bitmap) ((Pair) it.next()).second).recycle();
            }
            this.m_CacheGroup.clear();
        }
    }

    public void ClearCache(int iID) {
        synchronized (this.m_Lock) {
            Pair<Integer, Bitmap> RP = null;
            Iterator it = this.m_CacheGroup.iterator();
            while (it.hasNext()) {
                Pair<Integer, Bitmap> P = (Pair) it.next();
                if (((Integer) P.first).intValue() == iID) {
                    ((Bitmap) P.second).recycle();
                    RP = P;
                    break;
                }
            }
            if (RP != null) {
                this.m_CacheGroup.remove(RP);
            }
        }
    }
}
