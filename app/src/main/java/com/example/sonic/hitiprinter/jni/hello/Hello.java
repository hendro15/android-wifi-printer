package com.example.sonic.hitiprinter.jni.hello;

import android.content.Context;

public class Hello {
    public static String SayHello(Context context, int iSayCount) {
        return new HelloJni().SayHello(context, context.getResources().getAssets(), iSayCount);
    }

    public static String SayGoodBye(Context context, int iSayCount) {
        return new HelloJni().SayGoodBye(context, context.getResources().getAssets(), iSayCount);
    }
}
