package com.example.sonic.hitiprinter.commons;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.sonic.hitiprinter.R;

import java.util.List;

/**
 * Created by Hendro E. Prabowo on 14/06/2017.
 */

public class PrinterManager {
    public void printDocument(WebView webView, String title, Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printDocumentAdapter = webView.createPrintDocumentAdapter();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) printDocumentAdapter = webView.createPrintDocumentAdapter(title);

            String documentName = context.getString(R.string.app_name);

            PrintJob printJob = printManager.print(documentName, printDocumentAdapter, new PrintAttributes.Builder().build());
            List<PrintJob> printJobs = printManager.getPrintJobs();
            printJobs.add(printJob);
        } else {
            Toast.makeText(context, "Print error", Toast.LENGTH_SHORT);
        }
    }
}
