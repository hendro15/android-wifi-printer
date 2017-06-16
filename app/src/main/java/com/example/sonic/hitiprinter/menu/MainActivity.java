package com.example.sonic.hitiprinter.menu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonic.hitiprinter.R;
import com.example.sonic.hitiprinter.commons.PrinterManager;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.example.sonic.hitiprinter.printerprotocol.utility.MobileUtility;
import com.example.sonic.hitiprinter.printerprotocol.utility.PrinterInfo;
import com.example.sonic.hitiprinter.service.PrintBinder;
import com.example.sonic.hitiprinter.service.PrintBinder.IBinder;
import com.example.sonic.hitiprinter.service.PrintConnection;
import com.example.sonic.hitiprinter.service.PrintService;
import com.example.sonic.hitiprinter.service.PrintService.NotifyInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_WifiAutoConnectInfo;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMeta;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.Verify;
import com.example.sonic.hitiprinter.utility.Verify.ThreadMode;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.utility.dialog.MSGListener;
import com.example.sonic.hitiprinter.utility.dialog.ShowMSGDialog;
import com.example.sonic.hitiprinter.value.C0349R;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static final int GLOSSY_TEXTURE = 0;
    private static final int MATTE_TEXTURE = 1;
    private GlobalVariable_WifiAutoConnectInfo m_WifiInfo;

    protected Button btn_gallery, btn_print;
    protected ImageView iv_image;
    protected TextView tv_printModel;
    protected Intent i;
    protected PrinterManager printerManager;
    protected LogManager log;
    protected PrintConnection printConnection;
    protected MobileUtility m_MobileUtility;
    protected EditMeta m_EditMeta;
    protected EditMetaUtility m_EditMetaUtility = null;
    protected ShowMSGDialog m_ShowMSGDialog;

    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private String m_strSecurityKey;

    PrintMode m_PrintMode;
    String TAG;
    String IP;
    PrintMode printMode;
    int m_iPort;

    class C07963 implements PrinterInfo.Callback {
        C07963() {
        }

        public String setModelTextP310W() {
            return MainActivity.this.getString(C0349R.string.P310W);
        }

        public String setModelTextP520L() {
            return MainActivity.this.getString(C0349R.string.P520L);
        }

        public String setModelTextP750L() {
            return MainActivity.this.getString(C0349R.string.P750L);
        }

        public String setModelTextP530D() {
            return MainActivity.this.getString(C0349R.string.P530D);
        }
    }

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
        CheckWifi();
    }

    protected void setLayout() {
        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_print = (Button) findViewById(R.id.btn_print);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_printModel = (TextView)findViewById(R.id.tv_printmodel);
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

    private void CheckWifi() {
        this.log.m386v(this.TAG, "CheckWifi");
        NetworkInfo mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(MATTE_TEXTURE);
        this.m_WifiInfo = new GlobalVariable_WifiAutoConnectInfo(this);
        this.m_WifiInfo.RestoreGlobalVariable();
        this.m_strCurrentSSID = GetNowSSID();
        this.m_strLastSSID = this.m_WifiInfo.GetSSID();
        this.m_strSecurityKey = this.m_WifiInfo.GetPassword();
//        this.tv_printModel.setText(getString(C0349R.string.CONNECTING));
        this.m_strLastSSID = CleanSSID(this.m_strLastSSID);
        this.m_strCurrentSSID = CleanSSID(this.m_strCurrentSSID);
        if (mWifi.isConnected()) {
            if (this.m_strCurrentSSID.contains(this.m_strLastSSID)) {
                this.tv_printModel.setText(this.m_strCurrentSSID);
//                if (this.m_PrintMode == PrintMode.Snap) {
//                    ShowPrinterListDialog();
//                    return;
//                }
                return;
            }
            this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() == 0 || this.m_strLastSSID.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
//            ShowNoWiFiDialog();
        } else {
            this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.CONN_SEARCHING));
            this.tv_printModel.setText(getString(C0349R.string.CONN_SEARCHING));
//            this.m_wifiAutoConnect = new AutoWifiConnect(this, this.m_strLastSSID, this.m_strSecurityKey);
//            this.m_wifiAutoConnect.execute(new Void[GLOSSY_TEXTURE]);
        }
    }

    String CleanSSID(String strSSID){
        if(strSSID.contains("\"")){
            return strSSID.split("\"")[MATTE_TEXTURE];
        }
        return strSSID;
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    private String GetNowSSID(){
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if(wifiInfo.getSSID() == null){
            strSSID = XmlPullParser.NO_NAMESPACE;
        } else {
            strSSID = wifiInfo.getSSID();
        }
        return CleanSSID(strSSID);
    }

    public void ShowNoWiFiDialog() {
        this.m_ShowMSGDialog.StopMSGDialog();
        this.m_ShowMSGDialog.SetMSGListener(new MSGListener() {
            public void OKClick() {
                MainActivity.this.OpenWifi();
            }

            public void CancelClick() {
//                MainActivity.this.SetPrintButtonStatus(false);
//                PrintViewActivity.this.PrintStausAndCount(PrintViewActivity.this.getString(C0349R.string.PRINT_PAUSE), -1);
            }

            public void Close() {
            }
        });
        this.m_ShowMSGDialog.ShowMessageDialog(getString(C0349R.string.PLEASE_SELECT_NETWORK), getString(C0349R.string.UNABLE_TO_CONNECT_TO_PRINTER));
    }
}

