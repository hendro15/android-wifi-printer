package com.example.sonic.hitiprinter.ui.cacheadapter.viewholder;

import java.util.ArrayList;

public class PenddingGroup<T> {
    Object m_Lock;
    ArrayList<T> m_PenddingItemList;
    int m_iSize;

    public PenddingGroup(int iSize) {
        this.m_Lock = new Object();
        this.m_iSize = 0;
        this.m_PenddingItemList = null;
        this.m_PenddingItemList = new ArrayList();
        this.m_iSize = iSize;
    }

    private void VictimOnePendding() {
        if (this.m_PenddingItemList.size() > this.m_iSize) {
            this.m_PenddingItemList.remove(0);
        }
    }

    public void AddPendding(T holder) {
        synchronized (this.m_Lock) {
            if (this.m_PenddingItemList.contains(holder)) {
                return;
            }
            VictimOnePendding();
            this.m_PenddingItemList.add(holder);
        }
    }

    public void RemovePendding(T holder) {
        synchronized (this.m_Lock) {
            this.m_PenddingItemList.remove(holder);
        }
    }

    public void RemoveFirstPenddingViewHolder() {
        synchronized (this.m_Lock) {
            if (this.m_PenddingItemList.size() >= 1) {
                this.m_PenddingItemList.remove(0);
            }
        }
    }

    public T GetFirstPenddingViewHolder() {
        T holder;
        synchronized (this.m_Lock) {
            holder = this.m_PenddingItemList.get(0);
        }
        return holder;
    }

    public boolean HavePendding() {
        boolean boRet = false;
        synchronized (this.m_Lock) {
            if (this.m_PenddingItemList.size() > 0) {
                boRet = true;
            }
        }
        return boRet;
    }

    public void ClearPendding() {
        synchronized (this.m_Lock) {
            this.m_PenddingItemList.clear();
        }
    }
}
