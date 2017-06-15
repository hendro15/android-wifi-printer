package com.example.sonic.hitiprinter.commons;

import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.support.annotation.RequiresApi;

/**
 * Created by Hendro E. Prabowo on 14/06/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyPrintDocument extends PrintDocumentAdapter {
    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {

    }
}
