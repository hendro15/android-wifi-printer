package com.example.sonic.hitiprinter.ui.edmview;

public abstract class EDMViewListener {
    public abstract void NoPlayItem();

    public abstract void PlayCountStatus(String str);

    public abstract void PreparePlayPhoto(String str, int i);

    public abstract void PreparePlayVideo(String str, int i);

    public abstract void PrintCancel();
}
