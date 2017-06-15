package com.example.sonic.hitiprinter.menu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sonic.hitiprinter.R;
import com.example.sonic.hitiprinter.commons.PrinterManager;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.example.sonic.hitiprinter.printerprotocol.utility.MobileUtility;
import com.example.sonic.hitiprinter.service.PrintBinder;
import com.example.sonic.hitiprinter.service.PrintBinder.IBinder;
import com.example.sonic.hitiprinter.service.PrintConnection;
import com.example.sonic.hitiprinter.service.PrintService;
import com.example.sonic.hitiprinter.service.PrintService.NotifyInfo;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMeta;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.value.C0349R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final int GLOSSY_TEXTURE = 0;
    private static final int MATTE_TEXTURE = 1;

    protected Button btn_gallery, btn_print;
    protected ImageView iv_image;
    protected Intent i;
    protected PrinterManager printerManager;
    protected LogManager log;
    protected PrintConnection printConnection;
    protected MobileUtility m_MobileUtility;
    protected EditMeta m_EditMeta;
    protected EditMetaUtility m_EditMetaUtility = null;

    PrintMode m_PrintMode;
    String TAG;
    String IP;
    PrintMode printMode;
    int m_iPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printerManager = new PrinterManager();
        log = new LogManager(GLOSSY_TEXTURE);
        TAG = getClass().getSimpleName();
        this.IP = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_IP;
        this.m_iPort = HitiPPR_PrinterCommandNew.DEFAULT_AP_MODE_PORT;
        setLayout();
        onClick();
    }

    protected void setLayout() {
        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_print = (Button) findViewById(R.id.btn_print);
        iv_image = (ImageView) findViewById(R.id.iv_image);
    }

    protected void onClick() {
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(MainActivity.this, permission, 100);
            }
        });

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                printerManager.printDocument();
                startPrintservie();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                i = new Intent(Intent.ACTION_PICK);
                getImageFromAlbum();
            } else {
                Toast.makeText(this, "Ijin tidak diberikan", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void getImageFromAlbum() {
        try {
            i.setType("image/*");
            startActivityForResult(i, RESULT_CANCELED);
        } catch (Exception e) {
            Log.i("Error", e.toString());

        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                iv_image.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MainActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void GeneralPrint(Socket socket) {
        log.m385i(this.TAG, "GeneralPrint: " + this.printMode);
//        SetPrintButtonStatus(true);
//        if (this.m_bNextPhoto) {
//            this.m_bNextPhoto = false;
//            this.m_MobileUtility.SkipToPrintNext(null);
//        } else if (this.m_bWaitForRecovery) {
//            RecoveryPrint();
//        } else if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
            this.m_MobileUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
            this.m_MobileUtility.SetStop(false);
            this.m_MobileUtility.SendPhoto(socket, this.IP, this.m_iPort);
//        } else {
//            this.m_SDcardUtility.SetPrinterInfo(this.m_EditMetaUtility, this.m_PrintMode);
//            this.m_SDcardUtility.SetStop(false);
//            this.m_SDcardUtility.SendPhoto();
//        }
    }

    protected void startPrintservie(){
        log.m383d(TAG, "startPrintService");
        stopPrintService();
        this.printConnection = PrintBinder.start(this, new IBinder() {
            @Override
            public NotifyInfo setNotifyInfo(PrintService.NotifyInfo notifyInfo) {
                notifyInfo.icon = C0349R.drawable.print_button;
                notifyInfo.title = getString(C0349R.string.app_name);
                notifyInfo.message = getString(C0349R.string.PRINTER_STATUS_INITIALIZING);
                return notifyInfo;
            }

            @Override
            public void startPrint() {
                log.m383d(TAG, "General Print");
                GeneralPrint(null);
            }
        });
    }

    void stopPrintService(){
        if(this.printConnection != null){
            PrintBinder.stop(this, this.printConnection);
        }
    }
}

