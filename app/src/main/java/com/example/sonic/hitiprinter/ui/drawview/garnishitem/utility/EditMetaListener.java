package com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility;

public abstract class EditMetaListener {
    public abstract void DelFLValueData();

    public abstract void FetchImageDone(int i, String str);

    public abstract void FetchImgError(String str);

    public abstract void FetchImgRatio(int i, String str);

    public abstract void FetchImgTimeOut(String str);

    public abstract void FetchingBegin();

    public abstract void InitDrawView(int i);

    public abstract void InitDrawViewEnd(int i);

    public abstract void SaveEditPhoto();

    public abstract void SaveEditPhotoDone();
}
