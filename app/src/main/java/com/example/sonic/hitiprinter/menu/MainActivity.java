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

//import com.example.sonic.hitiprinter.flurry.FlurryUtility;
import com.example.sonic.hitiprinter.R;
import com.example.sonic.hitiprinter.commons.PrinterManager;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommand;
import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommandNew;
import com.example.sonic.hitiprinter.printerprotocol.utility.IMobile;
import com.example.sonic.hitiprinter.printerprotocol.utility.MobileUtility;
import com.example.sonic.hitiprinter.printerprotocol.utility.PrinterInfo;
import com.example.sonic.hitiprinter.service.PrintBinder;
import com.example.sonic.hitiprinter.service.PrintBinder.IBinder;
import com.example.sonic.hitiprinter.service.PrintConnection;
import com.example.sonic.hitiprinter.service.PrintService;
import com.example.sonic.hitiprinter.service.PrintService.NotifyInfo;
import com.example.sonic.hitiprinter.trace.GlobalVariable_MultiSelContainer;
import com.example.sonic.hitiprinter.trace.GlobalVariable_WifiAutoConnectInfo;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMeta;
import com.example.sonic.hitiprinter.ui.drawview.garnishitem.utility.EditMetaUtility;
import com.example.sonic.hitiprinter.ui.edmview.EDMView;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.Verify.ThreadMode;
import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import com.example.sonic.hitiprinter.utility.dialog.MSGListener;
import com.example.sonic.hitiprinter.utility.dialog.ShowMSGDialog;
import com.example.sonic.hitiprinter.value.C0349R;
import com.google.android.gms.common.ConnectionResult;

import org.xmlpull.v1.XmlPullParser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final int GLOSSY_TEXTURE = 0;
    private static final int LEAVE_TO_MAIN = 2;
    private static final int LEAVE_TO_PHOTO = 3;
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
    protected GlobalVariable_MultiSelContainer m_multiSelContainer;
    protected PrintConnection mPrintConnection;
    //    private MakeEditPhoto m_MakeEditPhoto;

    private String m_strCurrentSSID;
    private String m_strLastSSID;
    private String m_strSecurityKey;
    private ArrayList<String> m_strSelectPhotoPathList;
    private ArrayList<Long> m_lSelectPhotoIDList;
    private ArrayList<Integer> m_iPhotoCopiesList;
    private boolean m_bNeedUnsharpen;

    PrintMode m_PrintMode;
    String TAG;
    String IP;
    PrintMode printMode;
    int m_iPort;
    private int m_iTotalPhotoCount;
    private int m_iSumOfPhoto;
    private ArrayList<String> m_strPhotoPathList;
    private int m_iPathRoute;
    private int m_iSelRoute;
    private Object m_PhotoMode;
    private boolean m_bID_SD_Route;

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
        m_strSelectPhotoPathList = new ArrayList<>();
        m_lSelectPhotoIDList = new ArrayList<>();
        m_iPhotoCopiesList = new ArrayList<>();
        m_strSelectPhotoPathList = new ArrayList<>();
        m_ShowMSGDialog = new ShowMSGDialog(getApplicationContext(), true);
        setLayout();
        onClick();
//        FlurryUtility.init(this, FlurryUtility.FLURRY_API_KEY_PRINBIZ);

//        CheckWifi();
    }

    class MobileNoEditFlow extends MobileUtility {
        public MobileNoEditFlow(Context context, ArrayList<String> strPhotoPathList, ArrayList<Integer> iPhotoCopiesList, boolean bNeedUnSharpen) {
            super(context, strPhotoPathList, iPhotoCopiesList, bNeedUnSharpen);
        }

        public int[][] SetPrintoutSize(int iPaperType) {
            return MainActivity.this.SetPrintoutFromat(iPaperType);
        }
    }

    protected void SetNoEditPrintFlow(PrintMode mode) {
        this.log.m385i("SetNoEditPrintFlow", "Mode: " + mode);
        if (this.m_iSelRoute == MATTE_TEXTURE) {
            if (this.m_MobileUtility != null) {
                this.m_MobileUtility.SetStop(true);
            }
            this.m_MobileUtility = new MobileNoEditFlow(this, this.m_strPhotoPathList, this.m_iPhotoCopiesList, this.m_bNeedUnsharpen);
            this.m_MobileUtility.SetMobileListener(new OnMobileListener());
            return;
        }
    }

    protected void setLayout() {
        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_print = (Button) findViewById(R.id.btn_print);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_printModel = (TextView) findViewById(R.id.tv_printmodel);
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
                editMeta();
//                startPrintservie();
//                SaveMultiSelPref();
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
                Log.i("image", imageUri.getPath());
                m_strSelectPhotoPathList.add(imageUri.getPath());
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

    protected void startPrintservie() {
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

    void stopPrintService() {
        if (this.printConnection != null) {
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
                if (this.m_PrintMode == PrintMode.Snap) {
//                    ShowPrinterListDialog();
                    return;
                }
                return;
            }
            this.m_ShowMSGDialog.CreateConnectWifiHintDialog(this.m_strCurrentSSID, this.m_strLastSSID);
        } else if (this.m_strLastSSID.length() == 0 || this.m_strLastSSID.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
//            ShowNoWiFiDialog();
            Toast.makeText(this, "Wifi not available", Toast.LENGTH_SHORT).show();
        } else {
            this.m_ShowMSGDialog.ShowWaitingHintDialog(ThreadMode.AutoWifi, getString(C0349R.string.CONN_SEARCHING));
            this.tv_printModel.setText(getString(C0349R.string.CONN_SEARCHING));
//            this.m_wifiAutoConnect = new AutoWifiConnect(this, this.m_strLastSSID, this.m_strSecurityKey);
//            this.m_wifiAutoConnect.execute(new Void[GLOSSY_TEXTURE]);
        }
    }

    String CleanSSID(String strSSID) {
        if (strSSID.contains("\"")) {
            return strSSID.split("\"")[MATTE_TEXTURE];
        }
        return strSSID;
    }

    void OpenWifi() {
        startActivityForResult(new Intent("android.settings.WIFI_SETTINGS"), 10);
    }

    private String GetNowSSID() {
        String strSSID;
        WifiInfo wifiInfo = ((WifiManager) getSystemService("wifi")).getConnectionInfo();
        if (wifiInfo.getSSID() == null) {
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
        this.m_ShowMSGDialog.ShowMessageDialog(getString(R.string.PLEASE_SELECT_NETWORK), getString(R.string.UNABLE_TO_CONNECT_TO_PRINTER));
    }

//    protected void photoList(String strPhotoPath) {
//        m_strSelectPhotoPathList.add(strPhotoPath);
//    }

    private EditMeta editMeta() {
        this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
        this.m_iTotalPhotoCount = MATTE_TEXTURE;
        this.m_iSumOfPhoto = MATTE_TEXTURE;
        this.m_iPathRoute = EDMView.EDMViewHandler.ControllerState.PLAY_PHOTO;
        this.m_iSelRoute = MATTE_TEXTURE;
        this.m_EditMetaUtility = new EditMetaUtility(this);
//        if (this.m_PrintMode == PrintMode.Snap) {
//            return null;
//        }
        int i;
//        if (this.m_PhotoMode == JumpPreferenceKey.PHOTO_MODE.MODE_OUTPHOTO) {
//            i = EDMView.EDMViewHandler.ControllerState.NO_PLAY_ITEM;
//        } else {
        i = this.m_EditMetaUtility.GetPathRoute();
//        }
        this.m_iPathRoute = i;
//        if (this.m_PhotoMode == JumpPreferenceKey.PHOTO_MODE.MODE_OUTPHOTO) {
        i = MATTE_TEXTURE;
//        } else {
//            i = EditMetaUtility.GetSrcRoute(this);
//        }
        this.m_iSelRoute = i;
//        if (this.m_iPathRoute == EDMView.EDMViewHandler.ControllerState.PLAY_COUNT_STATE && this.m_iSelRoute == LEAVE_TO_MAIN) {
//            this.m_bID_SD_Route = true;
//            this.m_iSelRoute = MATTE_TEXTURE;
//        }
        EditMeta multiData = this.m_EditMetaUtility.GetEditMeta(this.m_iSelRoute);
//        if (this.m_iSelRoute == MATTE_TEXTURE) {
//            this.m_strPhotoPathList = multiData.GetMobilePathList();
//        } else {
//            this.m_iMultiSelPhotoIdList = multiData.GetSDcardIDList();
//            this.m_MultiSelStorageIdList = multiData.GetSDcardSIDList();
//        }
//        if (this.m_iPathRoute != EDMView.EDMViewHandler.ControllerState.PLAY_PHOTO) {
        this.m_iPhotoCopiesList = multiData.GetCopiesList();
//        } else if (this.m_iSelRoute == MATTE_TEXTURE) {
//            for (i = GLOSSY_TEXTURE; i < this.m_strPhotoPathList.size(); i += MATTE_TEXTURE) {
//                this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
//            }
//        } else {
//            for (i = GLOSSY_TEXTURE; i < this.m_iMultiSelPhotoIdList.size(); i += MATTE_TEXTURE) {
//                this.m_iPhotoCopiesList.add(Integer.valueOf(MATTE_TEXTURE));
//            }
//        }
        SetSumCopies();
        return multiData;
    }

//    private void SaveMultiSelPref() {
//        this.m_multiSelContainer = new GlobalVariable_MultiSelContainer(this, 1);
//        this.m_multiSelContainer.RestoreGlobalVariable();
//        this.m_multiSelContainer.ClearGlobalVariable();
//        this.m_multiSelContainer.SetMobilePhotoPathAndId(this.m_strSelectPhotoPathList, this.m_lSelectPhotoIDList);
//        if (!this.m_iCopiesList.isEmpty()) {
//            this.m_multiSelContainer.SetPhotoCopiesList(this.m_iCopiesList);
//        }
//        this.m_multiSelContainer.SaveGlobalVariable();
//    }

    private void SetSumCopies() {
        this.m_iTotalPhotoCount = this.m_iPhotoCopiesList.size();
        Iterator it = this.m_iPhotoCopiesList.iterator();
        while (it.hasNext()) {
            this.m_iSumOfPhoto += ((Integer) it.next()).intValue();
        }
    }

    private class OnMobileListener implements IMobile {
        private OnMobileListener() {
        }

        public void GetMethodAndSharpen(int iMethod, int iSharpen) {
        }

        public void StartCheckPrintInfo() {
            MainActivity.this.PrintStausAndCount(MainActivity.this.getString(C0349R.string.CHECK_PRINT_STATUS), -1);
        }

        public void EndCheckPrintInfo(String strVersion, String strProductID) {
//            MainActivity.this.m_strProductID = strProductID;
//            MainActivity.this.m_strPrinterFWversion = strVersion;
        }

        public void MediaSizeNotMatch(String strMSG) {
            MainActivity.this.log.m383d(MainActivity.this.TAG, "MediaSizeNotMatch: " + strMSG);
            if (MainActivity.this.m_iPathRoute != EDMView.EDMViewHandler.ControllerState.PLAY_COUNT_STATE) {
//                MainActivity.this.m_SettingImageButton.setEnabled(true);
//                MainActivity.this.m_SettingImageButton.setVisibility(MainActivity.GLOSSY_TEXTURE);
            }
//            MainActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void InitialBusy(String strMSG) {
            MainActivity.this.PrintStausAndCount(strMSG, -1);
//            MainActivity.this.SetPrintButtonStatus(false);
        }

        public void IsPrinterBusy(String strMSG) {
//            MainActivity.this.StatusCircle(true);
            MainActivity.this.PrintStausAndCount(strMSG, -1);
        }

        public void PreparePhoto() {
            MainActivity.this.PrintStausAndCount(MainActivity.this.getString(C0349R.string.PRINT_MAKE_IMAGE), -1);
        }

        public void SendingPhoto(String strMSG) {
//            MainActivity.this.StatusCircle(true);
            MainActivity.this.PrintStausAndCount(strMSG, -1);
        }

        public void SendingPhotoDone(Socket socket, int iNum) {
            if (iNum > MainActivity.this.m_iTotalPhotoCount) {
                iNum = MainActivity.this.m_iTotalPhotoCount;
            }
            String message = iNum + "/" + MainActivity.this.m_iTotalPhotoCount;
//            MainActivity.this.m_SendedCountTextView.setText(message);
            MainActivity.this.updatePrintNotification(MainActivity.this.getString(C0349R.string.SENDED_DONE_NUM) + message);
//            if (MainActivity.this.m_PrintMode == PrintMode.EditPrint && MainActivity.this.m_MakeEditPhoto != null) {
            if (MainActivity.this.m_PrintMode == PrintMode.EditPrint) {
//                MainActivity.this.m_MakeEditPhoto.MakeNextPhoto(socket);
            }
        }

        public void PrintDone(Socket socket, int iCopies, int iPrintedNumber, int iUnCleanNumber) {
//            MainActivity.this.StatusCircle(false);
            MainActivity.this.log.m385i("PrintDone", "count=" + String.valueOf(iPrintedNumber));
            if (MainActivity.this.m_iSumOfPhoto == iCopies) {
//                MainActivity.this.PrintDoneState(iCopies);
//                MainActivity.this.CleanModeCheck(iUnCleanNumber);
            } else if (MainActivity.this.m_PrintMode == PrintMode.EditPrint) {
//                MainActivity.this.m_iEditSumCopies = iCopies;
//                MainActivity.this.m_iPrintedNumber = iPrintedNumber;
//                if (!MainActivity.this.HaveNoEditPhoto()) {
//                    MainActivity.this.m_MobileUtility.Stop();
//                    MainActivity.this.PrintDoneState(iCopies);
//                    MainActivity.this.CleanModeCheck(iUnCleanNumber);
//                } else if (MainActivity.this.m_iSelRoute == MainActivity.LEAVE_TO_MAIN) {
//                    MainActivity.this.NextToNoEditPhoto(socket);
//                }
            } else {
                MainActivity.this.m_MobileUtility.Stop();
//                MainActivity.this.PrintDoneState(iCopies);
//                MainActivity.this.CleanModeCheck(iUnCleanNumber);
            }
        }

        public void ChangeCopies(String strMSG, int iCopies) {
            MainActivity.this.PrintStausAndCount(strMSG, iCopies);
        }

        public void CancelPrint(String strMSG) {
//            MainActivity.this.StatusCircle(false);
            MainActivity.this.PrintStausAndCount(strMSG, -1);
//            MainActivity.this.SetPrintButtonStatus(false);
            MainActivity.this.updatePrintNotification(strMSG);
        }

        public void RecoveryDone(Socket socket) {
//            MainActivity.this.m_bWaitForRecovery = false;
//            if (MainActivity.this.m_bCnclButRcv) {
//                MainActivity.this.m_bCnclButRcv = false;
//            } else {
            MainActivity.this.m_MobileUtility.SendPhoto(socket, MainActivity.this.IP, MainActivity.this.m_iPort);
//            }
        }

        public void StartBurnFW(Socket socket) {
//            if (MainActivity.this.m_bBurnFChecked) {
//                MainActivity.this.StartPrintForNotBurnFireWare(socket);
//                return;
//            }
//            MainActivity.this.m_bBurnFChecked = true;
//            MainActivity.this.ShowBurnFirwareDialog(socket);
        }

        public void ErrorOccur(String strErr) {
//            MainActivity.this.StatusCircle(false);
            if (strErr.equals(MainActivity.this.getString(C0349R.string.ERROR_PRINTER_0001))) {
//                MainActivity.this.ShowSimpleErrMessage(strErr);
                return;
            }
            MainActivity.this.m_MobileUtility.SetStop(true);
//            MainActivity.this.ShowConnectErrorAlertDialog(strErr);
        }

        public void ErrorOccurDueToPrinter(String strErr) {
//            strErr = MainActivity.this.ErrorMessageCheck(strErr);
//            MainActivity.this.StatusCircle(false);
            MainActivity.this.m_MobileUtility.SetStop(true);
//            MainActivity.this.m_bWaitForRecovery = true;
//            MainActivity.this.ShowPrinterErrorAlertDialog(strErr, false);
        }

        public void ErrorTimeOut(String strMSG) {
            MainActivity.this.m_MobileUtility.SetStop(true);
//            MainActivity.this.ShowConnectErrorAlertDialog(strMSG);
        }

        public void PhotoLawQty(String strMSG, int iType, HitiPPR_PrinterCommand m_PrintCommand) {
            MainActivity.this.updatePrintNotification(strMSG);
//            MainActivity.this.ShowLowQalityDialog(strMSG, iType, m_PrintCommand);
        }

        public void ErrorBitmap(String strErr) {
//            MainActivity.this.ShowBitmapErrorAlertDialog(strErr);
        }

        public void PaperJamDone() {
//            MainActivity.this.ShowPrinterErrorAlertDialog(PrintViewActivity.this.getString(C0349R.string.PAPER_JAM_DONE), false);
        }

        public void PaperJamAgain(String strError) {
//            MainActivity.this.ShowPrinterErrorAlertDialog(strError, true);
        }
    }

    private void PrintStausAndCount(String strStatus, int count) {
        String strCnt = XmlPullParser.NO_NAMESPACE;
        if (!(strStatus == null || strStatus == "plus" || strStatus == "next")) {
//            sthis.m_PrintStatusTextView.setText(strStatus);
            updatePrintNotification(strStatus);
        }
        if (count != -1) {
//            if (this.m_iEditSumCopies > 0) {
//                count += this.m_iEditSumCopies;
//            }
            strCnt = String.valueOf(count) + "/" + String.valueOf(this.m_iSumOfPhoto);
            if (this.m_PrintMode == PrintMode.EditPrint || this.m_iSelRoute == MATTE_TEXTURE) {
                String text = count + "/" + this.m_iSumOfPhoto;
//                this.m_PaperCountTextView.setText(text);
                updatePrintNotification(text);
//            } else if (!this.m_bPrintDone) {
//                if (strStatus == "plus" || strStatus == "next") {
//                    this.m_PaperCountTextView.setText(strCnt);
//                    updatePrintNotification(strCnt);
//                }
            }
        }
    }

    void updatePrintNotification(String message) {
        if (this.mPrintConnection != null) {
            this.mPrintConnection.updateNotification(message);
        }
    }

    private int[][] SetPrintoutFromat(int iPaperType) {
        int MAX_HEIGHT;
        int MAX_WIDTH;
        int OUTPUT_WIDTH;
        int OUTPUT_HEIGHT;
        this.log.m385i(this.TAG, "SetPrintoutFromat  iPaperType: " + iPaperType);
        int[][] length = (int[][]) Array.newInstance(Integer.TYPE, new int[]{LEAVE_TO_MAIN, LEAVE_TO_MAIN});
        switch (iPaperType) {
            case LEAVE_TO_MAIN /*2*/:
                if (!this.m_EditMetaUtility.GetModel().equals(WirelessType.TYPE_P310W)) {
                    MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                    MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                    OUTPUT_WIDTH = MAX_WIDTH;
                    OUTPUT_HEIGHT = MAX_HEIGHT;
                    break;
                }
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_310w_4x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_310w_4x6));
                OUTPUT_HEIGHT = Integer.parseInt(getString(C0349R.string.OUTPUT_HEIGHT_310w_4x6));
                OUTPUT_WIDTH = Integer.parseInt(getString(C0349R.string.OUTPUT_WIDTH_310w_4x6));
                break;
            case LEAVE_TO_PHOTO /*3*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_5x7));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_5x7));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            case ConnectionResult.SIGN_IN_REQUIRED /*4*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x8));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x8));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            case ConnectionResult.RESOLUTION_REQUIRED /*6*/:
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                OUTPUT_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x8_2up));
                OUTPUT_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x8_2up));
                break;
            case ConnectionResult.INTERNAL_ERROR /*8*/:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_6x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_6x6));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
            default:
                MAX_HEIGHT = Integer.parseInt(getString(C0349R.string.HEIGHT_4x6));
                MAX_WIDTH = Integer.parseInt(getString(C0349R.string.WIDTH_4x6));
                OUTPUT_WIDTH = MAX_WIDTH;
                OUTPUT_HEIGHT = MAX_HEIGHT;
                break;
        }
        length[GLOSSY_TEXTURE][GLOSSY_TEXTURE] = MAX_WIDTH;
        length[GLOSSY_TEXTURE][MATTE_TEXTURE] = MAX_HEIGHT;
        length[MATTE_TEXTURE][GLOSSY_TEXTURE] = OUTPUT_WIDTH;
        length[MATTE_TEXTURE][MATTE_TEXTURE] = OUTPUT_HEIGHT;
        this.log.m385i(this.TAG, "SetPrintoutFromat  width: " + MAX_WIDTH);
        this.log.m385i(this.TAG, "SetPrintoutFromat  height: " + MAX_HEIGHT);
        return length;
    }
}

