package com.example.sonic.hitiprinter.jni.hello;

import android.content.Context;
import android.content.res.AssetManager;

public class HelloJni {
    public native String SayGoodBye(Context context, AssetManager assetManager, int i);

    public native String SayHello(Context context, AssetManager assetManager, int i);

    static {
        System.loadLibrary("HelloJni");
    }
}
