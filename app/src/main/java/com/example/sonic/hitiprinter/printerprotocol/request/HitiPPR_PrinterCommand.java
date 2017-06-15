package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.media.TransportMediator;
import com.example.sonic.hitiprinter.printerprotocol.JobInfo;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.printerprotocol.PrinterErrorCode;
import com.example.sonic.hitiprinter.printerprotocol.SettingStep;
import com.example.sonic.hitiprinter.printerprotocol.WirelessType;
import com.example.sonic.hitiprinter.utility.FileUtility;
import com.example.sonic.hitiprinter.utility.LogManager;
import com.example.sonic.hitiprinter.utility.MobileInfo;
import com.example.sonic.hitiprinter.utility.PackBits;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher;
import com.example.sonic.hitiprinter.utility.resource.ResourceSearcher.RS_TYPE;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import javax.jmdns.impl.constants.DNSConstants;
import org.apache.commons.net.telnet.TelnetOption;

public abstract class HitiPPR_PrinterCommand extends Thread {
    public static final int CMD_MAJOR_VERSION = 0;
    public static final int CMD_MINOR_VERSION = 9;
    public static final int MAX_DATA_SIZE = 5242880;
    public static final int MAX_RE_CONNECTION_TIMES = 2;
    public static final String PRINGO_PRINTER_IP = "192.168.5.1";
    public static final int PRINGO_PRINTER_PORT = 54321;
    public static final int SEND_JPEG = 1;
    public static final int SEND_MASK = 2;
    public static final int TIME_OUT = 5000;
    public static final String m_strInputPassword = "hiti";
    public byte[] lpCmd;
    public LogManager LOG;
    public int R_STRING_CLEAN_ING;
    public int R_STRING_CREATE_BITMAP_FILE_FORMAT;
    public int R_STRING_ERROR_FIND_NO_STORAGEID;
    public int R_STRING_ERROR_NOPHOTO;
    public int R_STRING_ERROR_NOTHUMBNAIL;
    public int R_STRING_ERROR_NOT_SUPPORT_STORAGE;
    public int R_STRING_ERROR_PRINTER_0001;
    public int R_STRING_ERROR_PRINTER_0100;
    public int R_STRING_ERROR_PRINTER_0101;
    public int R_STRING_ERROR_PRINTER_0102;
    public int R_STRING_ERROR_PRINTER_0201;
    public int R_STRING_ERROR_PRINTER_0300;
    public int R_STRING_ERROR_PRINTER_0301;
    public int R_STRING_ERROR_PRINTER_0302;
    public int R_STRING_ERROR_PRINTER_0400;
    public int R_STRING_ERROR_PRINTER_0401;
    public int R_STRING_ERROR_PRINTER_0500;
    public int R_STRING_ERROR_PRINTER_0501;
    public int R_STRING_ERROR_PRINTER_0502;
    public int R_STRING_ERROR_PRINTER_0503;
    public int R_STRING_ERROR_PRINTER_0504;
    public int R_STRING_ERROR_PRINTER_0505;
    public int R_STRING_ERROR_PRINTER_0506;
    public int R_STRING_ERROR_PRINTER_0507;
    public int R_STRING_ERROR_PRINTER_0508;
    public int R_STRING_ERROR_PRINTER_0509;
    public int R_STRING_ERROR_PRINTER_0510;
    public int R_STRING_ERROR_PRINTER_0511;
    public int R_STRING_ERROR_PRINTER_0600;
    public int R_STRING_ERROR_PRINTER_0700;
    public int R_STRING_ERROR_PRINTER_0800;
    public int R_STRING_ERROR_PRINTER_0900;
    public int R_STRING_ERROR_PRINTER_1000;
    public int R_STRING_ERROR_PRINTER_1200;
    public int R_STRING_ERROR_PRINTER_1300;
    public int R_STRING_ERROR_PRINTER_1400;
    public int R_STRING_ERROR_PRINTER_1500;
    public int R_STRING_ERROR_PRINTER_BUSY;
    public int R_STRING_ERROR_PRINTER_CONNECT;
    public int R_STRING_ERROR_PRINTER_FIRMWARE_NO_CAPABILITY;
    public int R_STRING_ERROR_PRINTER_TIMEOUT;
    public int R_STRING_ERROR_PRINTER_UNKNOWN;
    public int R_STRING_ERROR_SIZE_OVER_25;
    public int R_STRING_ERROR_STORAGE_ACCESS_DENIED;
    public int R_STRING_PRINTER_STATUS_IDLE;
    public int R_STRING_PRINTER_STATUS_INITIALIZING;
    public int R_STRING_PRINTER_STATUS_PAUSED;
    public int R_STRING_PRINTER_STATUS_PRINTING;
    public int R_STRING_PRINTTING_COMPLETE;
    public int R_STRING_PRINT_BUSY;
    public int R_STRING_PRINT_DONE;
    public int R_STRING_PRINT_OUT_6x8_2up;
    public int R_STRING_ROOT_FOLDER;
    public int R_STRING_VERSION;
    public int R_STRING_VERSION_310W;
    public int R_STRING_VERSION_461;
    public int R_STRING_VERSION_530D;
    public int R_STRING_VERSION_750L;
    public int R_STRING_VERSION_P232;
    public int R_STRING_VERSION_P520;
    protected Context m_Context;
    public String m_ErrorString;
    private String m_IP;
    private InputStream m_InputStream;
    protected MSGHandler m_MSGHandler;
    public JobInfo m_OneJob;
    private OutputStream m_OutputStream;
    private Socket m_Socket;
    private TimeOutTask m_TimeOutTask;
    private Timer m_TimeOutTimer;
    protected boolean m_bDirectConnect;
    private int m_bDirty3Times;
    private boolean m_bNextCommand;
    protected boolean m_bRetry;
    private boolean m_boRun;
    public byte[] m_duplexData;
    private SettingStep m_iCommunicationStep;
    private int m_iNeedRead;
    private int m_iPort;
    public int m_iReconnectionTimes;
    public int m_iResponseErrorTimes;
    public byte[] m_imageData;
    public byte[] m_lpReadData;
    public byte[] m_maskData;
    private int m_offsetRead;
    protected long m_offsetWrite;

    public class TimeOutTask extends TimerTask {
        public void run() {
            if (HitiPPR_PrinterCommand.this.m_iCommunicationStep.equals(SettingStep.Step_SendDataResponse)) {
                HitiPPR_PrinterCommand.this.LOG.m384e("RUN TIMEOUT", "STOP:" + MobileInfo.GetTimeStamp());
            }
            HitiPPR_PrinterCommand.this.m_iCommunicationStep = SettingStep.Step_TimeOutError;
            try {
                HitiPPR_PrinterCommand.this.m_InputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void StartRequest();

    public HitiPPR_PrinterCommand(Context context, String IP, int iPort, MSGHandler msgHandler) {
        this.R_STRING_ERROR_PRINTER_0100 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0101 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0102 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0300 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0301 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0400 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0500 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0501 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0502 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0503 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_1300 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_1400 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0001 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0800 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0504 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0505 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0506 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0507 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0508 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0509 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0510 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0511 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0700 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0900 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_1000 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_1200 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_1500 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_UNKNOWN = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_CONNECT = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_TIMEOUT = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_BUSY = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0302 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0600 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0401 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_0201 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_PRINTER_FIRMWARE_NO_CAPABILITY = CMD_MAJOR_VERSION;
        this.R_STRING_PRINTER_STATUS_INITIALIZING = CMD_MAJOR_VERSION;
        this.R_STRING_PRINTER_STATUS_IDLE = CMD_MAJOR_VERSION;
        this.R_STRING_PRINTER_STATUS_PRINTING = CMD_MAJOR_VERSION;
        this.R_STRING_PRINTER_STATUS_PAUSED = CMD_MAJOR_VERSION;
        this.R_STRING_PRINTTING_COMPLETE = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_FIND_NO_STORAGEID = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_NOT_SUPPORT_STORAGE = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_STORAGE_ACCESS_DENIED = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_SIZE_OVER_25 = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_NOTHUMBNAIL = CMD_MAJOR_VERSION;
        this.R_STRING_ERROR_NOPHOTO = CMD_MAJOR_VERSION;
        this.R_STRING_CREATE_BITMAP_FILE_FORMAT = CMD_MAJOR_VERSION;
        this.R_STRING_PRINT_DONE = CMD_MAJOR_VERSION;
        this.R_STRING_PRINT_BUSY = CMD_MAJOR_VERSION;
        this.R_STRING_PRINT_OUT_6x8_2up = CMD_MAJOR_VERSION;
        this.R_STRING_CLEAN_ING = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_P232 = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_P520 = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_310W = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_750L = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_461 = CMD_MAJOR_VERSION;
        this.R_STRING_VERSION_530D = CMD_MAJOR_VERSION;
        this.R_STRING_ROOT_FOLDER = CMD_MAJOR_VERSION;
        this.m_Context = null;
        this.m_MSGHandler = null;
        this.m_Socket = null;
        this.m_boRun = false;
        this.m_InputStream = null;
        this.m_OutputStream = null;
        this.m_lpReadData = null;
        this.m_iNeedRead = CMD_MAJOR_VERSION;
        this.m_offsetRead = CMD_MAJOR_VERSION;
        this.m_offsetWrite = 0;
        this.m_TimeOutTimer = null;
        this.m_TimeOutTask = null;
        this.m_iCommunicationStep = SettingStep.Step_Complete;
        this.m_IP = PRINGO_PRINTER_IP;
        this.m_iPort = PRINGO_PRINTER_PORT;
        this.m_ErrorString = null;
        this.m_imageData = null;
        this.m_maskData = null;
        this.m_duplexData = null;
        this.m_iReconnectionTimes = CMD_MAJOR_VERSION;
        this.LOG = null;
        this.m_bNextCommand = false;
        this.m_bRetry = true;
        this.m_bDirty3Times = SEND_JPEG;
        this.m_iResponseErrorTimes = CMD_MAJOR_VERSION;
        this.m_bDirectConnect = false;
        this.m_Context = context;
        GetResourceID(context);
        this.LOG = new LogManager(CMD_MAJOR_VERSION);
        this.m_lpReadData = new byte[DNSConstants.FLAGS_RD];
        this.m_MSGHandler = msgHandler;
        if (IP != null) {
            this.m_IP = IP;
            this.m_iPort = iPort;
        }
    }

    private void GetResourceID(Context context) {
        this.R_STRING_ROOT_FOLDER = ResourceSearcher.getId(context, RS_TYPE.STRING, "ROOT");
        this.R_STRING_ERROR_FIND_NO_STORAGEID = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_FIND_NO_STORAGEID");
        this.R_STRING_ERROR_NOT_SUPPORT_STORAGE = ResourceSearcher.getId(context, RS_TYPE.STRING, "NOT_SUPPORT_STORAGE");
        this.R_STRING_ERROR_STORAGE_ACCESS_DENIED = ResourceSearcher.getId(context, RS_TYPE.STRING, "STORAGE_ACCESS_DENIED");
        this.R_STRING_ERROR_SIZE_OVER_25 = ResourceSearcher.getId(context, RS_TYPE.STRING, "SIZE_OVER_25");
        this.R_STRING_ERROR_NOTHUMBNAIL = ResourceSearcher.getId(context, RS_TYPE.STRING, "NOTHUMBNAIL");
        this.R_STRING_ERROR_NOPHOTO = ResourceSearcher.getId(context, RS_TYPE.STRING, "NOPHOTO");
        this.R_STRING_CREATE_BITMAP_FILE_FORMAT = ResourceSearcher.getId(context, RS_TYPE.STRING, "CREATE_BITMAP_FILE_FORMAT");
        this.R_STRING_PRINT_DONE = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINT_DONE");
        this.R_STRING_PRINT_BUSY = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINT_BUSY");
        this.R_STRING_PRINT_OUT_6x8_2up = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINT_OUT_6x8_2up");
        this.R_STRING_ERROR_PRINTER_0100 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0100");
        this.R_STRING_ERROR_PRINTER_0101 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0101");
        this.R_STRING_ERROR_PRINTER_0102 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0102");
        this.R_STRING_ERROR_PRINTER_0300 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0300");
        this.R_STRING_ERROR_PRINTER_0301 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0301");
        this.R_STRING_ERROR_PRINTER_0400 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0400");
        this.R_STRING_ERROR_PRINTER_0500 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0500");
        this.R_STRING_ERROR_PRINTER_0501 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0501");
        this.R_STRING_ERROR_PRINTER_0502 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0502");
        this.R_STRING_ERROR_PRINTER_0503 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0503");
        this.R_STRING_ERROR_PRINTER_1300 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_1300");
        this.R_STRING_ERROR_PRINTER_1400 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_1400");
        this.R_STRING_ERROR_PRINTER_0001 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0001");
        this.R_STRING_ERROR_PRINTER_0800 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0800");
        this.R_STRING_ERROR_PRINTER_0504 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0504");
        this.R_STRING_ERROR_PRINTER_0505 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0505");
        this.R_STRING_ERROR_PRINTER_0506 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0506");
        this.R_STRING_ERROR_PRINTER_0507 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0507");
        this.R_STRING_ERROR_PRINTER_0508 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0508");
        this.R_STRING_ERROR_PRINTER_0509 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0509");
        this.R_STRING_ERROR_PRINTER_0510 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0510");
        this.R_STRING_ERROR_PRINTER_0511 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0511");
        this.R_STRING_ERROR_PRINTER_0700 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0700");
        this.R_STRING_ERROR_PRINTER_0900 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0900");
        this.R_STRING_ERROR_PRINTER_1000 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_1000");
        this.R_STRING_ERROR_PRINTER_1200 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_1200");
        this.R_STRING_ERROR_PRINTER_1500 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_1500");
        this.R_STRING_ERROR_PRINTER_UNKNOWN = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_UNKNOWN");
        this.R_STRING_ERROR_PRINTER_CONNECT = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_CONNECT");
        this.R_STRING_ERROR_PRINTER_TIMEOUT = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_TIMEOUT");
        this.R_STRING_ERROR_PRINTER_BUSY = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_BUSY");
        this.R_STRING_ERROR_PRINTER_0600 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0600");
        this.R_STRING_ERROR_PRINTER_0401 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0401");
        this.R_STRING_ERROR_PRINTER_0201 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0201");
        this.R_STRING_ERROR_PRINTER_0302 = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_0302");
        this.R_STRING_ERROR_PRINTER_FIRMWARE_NO_CAPABILITY = ResourceSearcher.getId(context, RS_TYPE.STRING, "ERROR_PRINTER_FIRMWARE_NO_CAPABILITY");
        this.R_STRING_VERSION = ResourceSearcher.getId(context, RS_TYPE.STRING, "version");
        this.R_STRING_VERSION_P232 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p232");
        this.R_STRING_VERSION_P520 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_p520");
        this.R_STRING_VERSION_310W = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_310w");
        this.R_STRING_VERSION_750L = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_750l");
        this.R_STRING_VERSION_461 = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_461");
        this.R_STRING_VERSION_530D = ResourceSearcher.getId(context, RS_TYPE.STRING, "version_530D");
        this.R_STRING_PRINTER_STATUS_INITIALIZING = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINTER_STATUS_INITIALIZING");
        this.R_STRING_PRINTER_STATUS_IDLE = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINTER_STATUS_IDLE");
        this.R_STRING_PRINTER_STATUS_PRINTING = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINTER_STATUS_PRINTING");
        this.R_STRING_PRINTER_STATUS_PAUSED = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINTER_STATUS_PAUSED");
        this.R_STRING_PRINTTING_COMPLETE = ResourceSearcher.getId(context, RS_TYPE.STRING, "PRINTTING_COMPLETE");
    }

    public void SendMessage(int iMsg, String strMsg) {
        Message msg = Message.obtain();
        msg.what = iMsg;
        if (strMsg != null) {
            Bundle buddle = new Bundle();
            buddle.putString(MSGHandler.MSG, strMsg);
            msg.setData(buddle);
        }
        if (this.m_MSGHandler != null) {
            this.m_MSGHandler.sendMessage(msg);
        }
    }

    public void SetSocket(Socket m_Socket) {
        this.m_Socket = m_Socket;
    }

    public Socket GetSocket() {
        return this.m_Socket;
    }

    public boolean InitSocket() {
        UnknownHostException e;
        SocketException e2;
        IOException e3;
        boolean bRet = true;
        InetAddress serverAddr = null;
        SocketAddress socketAddress = null;
        try {
            if (this.m_Socket == null) {
                serverAddr = InetAddress.getByName(this.m_IP);
                int i = this.m_iPort == 0 ? PRINGO_PRINTER_PORT : this.m_iPort;
                this.m_iPort = i;
                SocketAddress sc_add = new InetSocketAddress(serverAddr, i);
                try {
                    this.m_Socket = new Socket();
                    this.m_Socket.connect(sc_add, TIME_OUT);
                    this.LOG.m385i("InitSocket", "1st conn OK");
                    socketAddress = sc_add;
                } catch (UnknownHostException e4) {
                    e = e4;
                    socketAddress = sc_add;
                    CloseSocket();
                    this.m_bRetry = true;
                    e.printStackTrace();
                    bRet = false;
                    bRet = DirtyNewSocket(serverAddr, socketAddress);
                    if (!bRet) {
                        this.m_Socket = null;
                    }
                    this.m_bNextCommand = false;
                    this.m_bDirty3Times = SEND_JPEG;
                    return bRet;
                } catch (SocketException e5) {
                    e2 = e5;
                    socketAddress = sc_add;
                    CloseSocket();
                    this.m_bRetry = true;
                    e2.printStackTrace();
                    bRet = false;
                    bRet = DirtyNewSocket(serverAddr, socketAddress);
                    if (bRet) {
                        this.m_Socket = null;
                    }
                    this.m_bNextCommand = false;
                    this.m_bDirty3Times = SEND_JPEG;
                    return bRet;
                } catch (IOException e6) {
                    e3 = e6;
                    socketAddress = sc_add;
                    CloseSocket();
                    this.m_bRetry = true;
                    e3.printStackTrace();
                    bRet = false;
                    bRet = DirtyNewSocket(serverAddr, socketAddress);
                    if (bRet) {
                        this.m_Socket = null;
                    }
                    this.m_bNextCommand = false;
                    this.m_bDirty3Times = SEND_JPEG;
                    return bRet;
                }
            }
            this.m_bDirectConnect = true;
            this.m_InputStream = this.m_Socket.getInputStream();
            this.m_OutputStream = this.m_Socket.getOutputStream();
            this.m_boRun = true;
            this.m_iCommunicationStep = SettingStep.Step_AuthenticationRequest;
        } catch (UnknownHostException e7) {
            e = e7;
            CloseSocket();
            this.m_bRetry = true;
            e.printStackTrace();
            bRet = false;
            bRet = DirtyNewSocket(serverAddr, socketAddress);
            if (bRet) {
                this.m_Socket = null;
            }
            this.m_bNextCommand = false;
            this.m_bDirty3Times = SEND_JPEG;
            return bRet;
        } catch (SocketException e8) {
            e2 = e8;
            CloseSocket();
            this.m_bRetry = true;
            e2.printStackTrace();
            bRet = false;
            bRet = DirtyNewSocket(serverAddr, socketAddress);
            if (bRet) {
                this.m_Socket = null;
            }
            this.m_bNextCommand = false;
            this.m_bDirty3Times = SEND_JPEG;
            return bRet;
        } catch (IOException e9) {
            e3 = e9;
            CloseSocket();
            this.m_bRetry = true;
            e3.printStackTrace();
            bRet = false;
            bRet = DirtyNewSocket(serverAddr, socketAddress);
            if (bRet) {
                this.m_Socket = null;
            }
            this.m_bNextCommand = false;
            this.m_bDirty3Times = SEND_JPEG;
            return bRet;
        }
        if (!bRet && this.m_bRetry) {
            bRet = DirtyNewSocket(serverAddr, socketAddress);
        }
        if (bRet) {
            this.m_Socket = null;
        }
        this.m_bNextCommand = false;
        this.m_bDirty3Times = SEND_JPEG;
        return bRet;
    }

    private boolean DirtyNewSocket(InetAddress serverAddr, SocketAddress sc_add) {
        InterruptedException e;
        UnknownHostException e2;
        SocketException e3;
        SocketTimeoutException e4;
        IOException e5;
        boolean bRet = false;
        this.m_bDirectConnect = false;
        while (this.m_bDirty3Times < SEND_MASK && !bRet) {
            this.LOG.m384e("m_bDirty3Times", "times=" + this.m_bDirty3Times);
            try {
                sleep(3000);
                SocketAddress sc_add2 = new InetSocketAddress(InetAddress.getByName(this.m_IP), this.m_iPort);
                try {
                    this.m_Socket = new Socket();
                    this.m_Socket.connect(sc_add2, TIME_OUT);
                    this.m_InputStream = this.m_Socket.getInputStream();
                    this.m_OutputStream = this.m_Socket.getOutputStream();
                    this.m_boRun = true;
                    this.m_iCommunicationStep = SettingStep.Step_AuthenticationRequest;
                    bRet = true;
                    this.LOG.m385i("Dirty", "Dirty success");
                    sc_add = sc_add2;
                } catch (UnknownHostException e7) {
                    e2 = e7;
                    sc_add = sc_add2;
                    e2.printStackTrace();
                    CloseSocket();
                    bRet = false;
                    this.m_bDirty3Times += SEND_JPEG;
                } catch (SocketException e8) {
                    e3 = e8;
                    sc_add = sc_add2;
                    CloseSocket();
                    e3.printStackTrace();
                    bRet = false;
                    this.m_bDirty3Times += SEND_JPEG;
                } catch (SocketTimeoutException e9) {
                    e4 = e9;
                    sc_add = sc_add2;
                    CloseSocket();
                    e4.printStackTrace();
                    bRet = false;
                    this.m_bDirty3Times += SEND_JPEG;
                } catch (IOException e10) {
                    e5 = e10;
                    sc_add = sc_add2;
                    e5.printStackTrace();
                    CloseSocket();
                    bRet = false;
                    this.m_bDirty3Times += SEND_JPEG;
                }
            } catch (InterruptedException e11) {
                e = e11;
                e.printStackTrace();
                bRet = false;
                this.m_bDirty3Times += SEND_JPEG;
            } catch (UnknownHostException e12) {
                e2 = e12;
                e2.printStackTrace();
                CloseSocket();
                bRet = false;
                this.m_bDirty3Times += SEND_JPEG;
            }
            this.m_bDirty3Times += SEND_JPEG;
        }
        return bRet;
    }

    public void SetRetry(boolean bRetry) {
        this.m_bRetry = bRetry;
    }

    public boolean Reconnection() {
        if (!this.m_bRetry) {
            return false;
        }
        this.LOG.m384e("Reconnection", "sleep1000");
        try {
            InitialByteDate();
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return InitSocket();
    }

    public void StopForNextCommand() {
        this.m_bNextCommand = true;
        this.m_boRun = false;
        this.LOG.m385i("StopForNextCommand", "StopForNextCommand");
    }

    public void CloseSocket() {
        if (this.m_OutputStream != null) {
            try {
                this.m_OutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.m_OutputStream = null;
        }
        if (this.m_InputStream != null) {
            try {
                this.m_InputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.m_InputStream = null;
        }
        if (this.m_Socket != null) {
            try {
                this.m_Socket.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
            this.m_Socket = null;
        }
    }

    public int WriteRequest(byte[] lpCmd, int nOffset, int nCmdLen) {
        if (this.m_OutputStream == null) {
            return CMD_MAJOR_VERSION;
        }
        try {
            this.m_OutputStream.write(lpCmd, nOffset, nCmdLen);
            this.m_OutputStream.flush();
            return nCmdLen;
        } catch (Exception e) {
            e.printStackTrace();
            return CMD_MAJOR_VERSION;
        }
    }

    public boolean ReadResponse(int iTimeOut) {
        boolean bRet = true;
        int nTemp = CMD_MAJOR_VERSION;
        StartTimerOut(iTimeOut);
        if (this.m_InputStream == null || this.m_iNeedRead <= 0) {
            StopTimerOut();
            bRet = false;
        } else {
            try {
                nTemp = this.m_InputStream.read(this.m_lpReadData, this.m_offsetRead, this.m_iNeedRead);
            } catch (SocketException e) {
                this.LOG.m385i("ReadResponse", e.toString());
                e.printStackTrace();
                bRet = false;
            } catch (SocketTimeoutException e2) {
                this.LOG.m385i("ReadResponse", e2.toString());
                e2.printStackTrace();
                bRet = false;
            } catch (IOException e3) {
                this.LOG.m385i("ReadResponse", e3.toString());
                e3.printStackTrace();
                bRet = false;
            }
            if (nTemp > 0) {
                this.m_iNeedRead -= nTemp;
                this.m_offsetRead += nTemp;
                StopTimerOut();
            }
            if (nTemp == -1) {
                this.LOG.m385i("nTemp", String.valueOf(nTemp));
                bRet = false;
            }
        }
        if (!bRet) {
            this.LOG.m384e("ReadResponse-Error", String.valueOf(this.m_iNeedRead));
            this.LOG.m384e("ReadResponse-bRet", String.valueOf(bRet));
        }
        return bRet;
    }

    public int GetOffsetRead() {
        return this.m_offsetRead;
    }

    public void RemoveAllResource() {
        if (this.m_imageData != null) {
            this.m_imageData = null;
        }
        if (this.m_maskData != null) {
            this.m_maskData = null;
        }
        if (this.m_duplexData != null) {
            this.m_duplexData = null;
        }
        if (this.m_OneJob != null) {
            this.m_OneJob = null;
        }
        if (this.m_lpReadData != null) {
            this.m_lpReadData = null;
        }
        this.m_offsetWrite = 0;
        this.m_offsetRead = CMD_MAJOR_VERSION;
        this.m_iNeedRead = CMD_MAJOR_VERSION;
        StopTimerOut();
    }

    private void InitialByteDate() {
        this.m_offsetWrite = 0;
        this.m_offsetRead = CMD_MAJOR_VERSION;
        this.m_iNeedRead = CMD_MAJOR_VERSION;
        StopTimerOut();
    }

    public boolean IsReadComplete() {
        if (this.m_iNeedRead == 0) {
            return true;
        }
        return false;
    }

    public void Stop() {
        this.LOG.m385i("Stop", "PrinterCommand");
        this.m_boRun = false;
        this.m_bNextCommand = false;
        CloseSocket();
    }

    public void StartTimerOut(int iTimeOut) {
        if (this.m_TimeOutTimer == null) {
            if (iTimeOut == 0) {
                iTimeOut = TIME_OUT;
            }
            this.m_TimeOutTimer = new Timer(true);
            this.m_TimeOutTask = new TimeOutTask();
            this.m_TimeOutTimer.schedule(this.m_TimeOutTask, (long) iTimeOut);
        }
    }

    public void StopTimerOut() {
        if (this.m_TimeOutTimer != null) {
            this.m_TimeOutTimer.cancel();
            this.m_TimeOutTimer = null;
        }
        if (this.m_TimeOutTask != null) {
            this.m_TimeOutTask.cancel();
            this.m_TimeOutTask = null;
        }
    }

    public void SetErrorMSG(String strErrorMSG) {
        this.m_ErrorString = GetErrorMSG(strErrorMSG);
    }

    public String GetErrorMSG(String strMSG) {
        String strRetMSG = strMSG;
        if (!strMSG.contains(":")) {
            return strRetMSG;
        }
        String strErrorCode = strMSG.substring(CMD_MAJOR_VERSION, strMSG.indexOf(":"));
        if (strRetMSG.length() > 5) {
            strRetMSG = strMSG.substring(strMSG.indexOf(":") + SEND_JPEG, strMSG.length());
        }
        this.LOG.m385i("strErrorCode", strErrorCode);
        this.LOG.m385i("iErrorCode", String.valueOf(strErrorCode));
        this.LOG.m385i("strRetMSG", strRetMSG);
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0100)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0100);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0101)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0101);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0102)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0102);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0300)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0300);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0301)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0301);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0400)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0400);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0500)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0500);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0501)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0501);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0502)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0502);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0503)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0503);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_1300)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_1300);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_1400)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_1400);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0001)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0001);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0302)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0302);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0600)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0600);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0401)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0401);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0201)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0201);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0800)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0800);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0504)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0504);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0505)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0505);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0506)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0506);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0507)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0507);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0508)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0508);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0509)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0509);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0510)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0510);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0511)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0511);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0700)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0700);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_0900)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_0900);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_1000)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_1000);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_1200)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_1200);
        }
        if (strErrorCode.contains(PrinterErrorCode.ERROR_CODE_PRINTER_1500)) {
            return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_1500);
        }
        return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_UNKNOWN);
    }

    public String GetErrorMSGConnectFail() {
        return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_CONNECT);
    }

    public String GetErrorMSGTimeOut() {
        return this.m_Context.getString(this.R_STRING_ERROR_PRINTER_TIMEOUT);
    }

    public boolean IsRunning() {
        return this.m_boRun;
    }

    public void run() {
        this.m_boRun = true;
        if (!InitSocket()) {
            DecideNextStep(SettingStep.Step_Error);
        }
        while (this.m_boRun) {
            try {
                StartRequest();
                sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
                this.m_boRun = false;
            }
        }
        if (!this.m_bNextCommand) {
            Stop();
        }
        RemoveAllResource();
    }

    public SettingStep GetCurrentStep() {
        return this.m_iCommunicationStep;
    }

    public void DecideErrorStatus() {
        if (this.m_iCommunicationStep != SettingStep.Step_TimeOutError) {
            this.m_iCommunicationStep = SettingStep.Step_Error;
        }
    }

    public void DecideNextStepAndPrepareReadBuff(int iNeedRead, int offsetRead, SettingStep nextStep) {
        this.m_iNeedRead = iNeedRead;
        this.m_offsetRead = offsetRead;
        if (nextStep != null) {
            this.m_iCommunicationStep = nextStep;
        }
    }

    public void DecideNextStep(SettingStep nextStep) {
        this.m_iCommunicationStep = nextStep;
    }

    public boolean CheckCommandSuccess() {
        if (this.m_lpReadData.length > 3 && this.m_lpReadData[SEND_MASK] == 64 && this.m_lpReadData[3] == 0) {
            return true;
        }
        this.LOG.m385i("CheckCommandSuccess[2]", Integer.toHexString(this.m_lpReadData[SEND_MASK]));
        this.LOG.m385i("CheckCommandSuccess[3]", Integer.toHexString(this.m_lpReadData[3]));
        return false;
    }

    public boolean CheckCommandSuccess(byte[] verifyBuf) {
        if (this.m_lpReadData.length > 3 && this.m_lpReadData[SEND_MASK] == verifyBuf[CMD_MAJOR_VERSION] && this.m_lpReadData[3] == verifyBuf[SEND_JPEG]) {
            return true;
        }
        return false;
    }

    public boolean IsConnectError() {
        if (this.m_iCommunicationStep == SettingStep.Step_Error) {
            return true;
        }
        return false;
    }

    public boolean IsTimeoutError() {
        if (this.m_iCommunicationStep == SettingStep.Step_TimeOutError) {
            return true;
        }
        return false;
    }

    public String GetPrinterStatus(Byte bStatus) {
        if (bStatus.byteValue() == 0) {
            return this.m_Context.getString(this.R_STRING_PRINTER_STATUS_INITIALIZING);
        }
        if (bStatus.byteValue() == SEND_JPEG) {
            return this.m_Context.getString(this.R_STRING_PRINTER_STATUS_IDLE);
        }
        if (bStatus.byteValue() == SEND_MASK) {
            return this.m_Context.getString(this.R_STRING_PRINTER_STATUS_PRINTING);
        }
        if (bStatus.byteValue() == 3) {
            return this.m_Context.getString(this.R_STRING_PRINTER_STATUS_PAUSED);
        }
        return null;
    }

    public boolean Authentication_Request() {
        int i;
        lpCmd = new byte[TransportMediator.FLAG_KEY_MEDIA_NEXT];
        String strLoginName = m_strInputPassword;
        byte[] bLoginName = strLoginName.getBytes();
        Byte bLoginNameSize = Byte.valueOf((byte) 0);
        String strPassword = m_strInputPassword;
        byte[] bPassword = strPassword.getBytes();
        Byte bPasswordSize = Byte.valueOf((byte) 0);
        lpCmd[CMD_MAJOR_VERSION] = (byte) 0;
        lpCmd[SEND_JPEG] = (byte) 9;
        lpCmd[SEND_MASK] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 6;
        lpCmd[5] = (byte) 1;
        lpCmd[6] = (byte) 7;
        lpCmd[7] = (byte) 1;
        lpCmd[8] = (byte) 16;
        for (i = CMD_MAJOR_VERSION; i < strLoginName.length(); i += SEND_JPEG) {
            bLoginNameSize = Byte.valueOf((byte) (bLoginNameSize.byteValue() + SEND_JPEG));
        }
        int nCmdLen = CMD_MINOR_VERSION + SEND_JPEG;
        lpCmd[CMD_MINOR_VERSION] = bLoginNameSize.byteValue();
        for (i = CMD_MAJOR_VERSION; i < bLoginName.length; i += SEND_JPEG) {
            this.LOG.m385i("bLoginName Byte", String.valueOf(bLoginName[i]));
            lpCmd[i + 10] = bLoginName[i];
        }
        int length = bLoginName.length + 10;
        nCmdLen = length + SEND_JPEG;
        lpCmd[length] = (byte) 7;
        length = nCmdLen + SEND_JPEG;
        lpCmd[nCmdLen] = (byte) 1;
        nCmdLen = length + SEND_JPEG;
        lpCmd[length] = (byte) 17;
        for (i = CMD_MAJOR_VERSION; i < strPassword.length(); i += SEND_JPEG) {
            bPasswordSize = Byte.valueOf((byte) (bPasswordSize.byteValue() + SEND_JPEG));
        }
        length = nCmdLen + SEND_JPEG;
        lpCmd[nCmdLen] = bPasswordSize.byteValue();
        for (i = CMD_MAJOR_VERSION; i < bPassword.length; i += SEND_JPEG) {
            this.LOG.m385i("Byte", String.valueOf(bPassword[i]));
            lpCmd[length + i] = bPassword[i];
        }
        length += bPassword.length;
        nCmdLen = length + SEND_JPEG;
        lpCmd[length] = (byte) -1;
        if (WriteRequest(lpCmd, CMD_MAJOR_VERSION, nCmdLen) == nCmdLen) {
            return true;
        }
        return false;
    }

    public boolean Get_NetWork_Info_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 7, (byte) 1, (byte) -1, (byte) 0}, CMD_MAJOR_VERSION, 8) == 8) {
            return true;
        }
        return false;
    }

    public boolean Get_Product_ID_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 1, (byte) -1, (byte) 1, (byte) 1, (byte) 1}, CMD_MAJOR_VERSION, 10) == 10) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Info_Request(String strProductID) {
        lpCmd = new byte[16];
        int i = CMD_MAJOR_VERSION + SEND_JPEG;
        lpCmd[CMD_MAJOR_VERSION] = (byte) 0;
        int i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 9;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = Byte.MIN_VALUE;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 0;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) 1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 1;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) -1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 5;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) 1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 1;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) 1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 4;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) 1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 5;
        i = i2 + SEND_JPEG;
        lpCmd[i2] = (byte) 1;
        i2 = i + SEND_JPEG;
        lpCmd[i] = (byte) 6;
        if (strProductID.equals(WirelessType.TYPE_P231) || strProductID.equals(WirelessType.TYPE_P232) || strProductID.equals(WirelessType.TYPE_P235)) {
            i = i2 + SEND_JPEG;
            lpCmd[i2] = (byte) 1;
            i2 = i + SEND_JPEG;
            lpCmd[i] = (byte) 18;
        }
        int nCmdLen = i2;
        if (WriteRequest(lpCmd, CMD_MAJOR_VERSION, nCmdLen) == nCmdLen) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Status_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 3, (byte) 2, (byte) -1}, CMD_MAJOR_VERSION, 7) == 7) {
            return true;
        }
        return false;
    }

    public boolean Create_Job_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 2, (byte) 1, (byte) -1}, CMD_MAJOR_VERSION, 7) == 7) {
            return true;
        }
        return false;
    }

    public boolean Start_Job_Request() {
        lpCmd = new byte[74];
        this.LOG.m385i("Size " + String.valueOf(this.m_OneJob.lSize), "Start_Job_Request");
        lpCmd[33] = (byte) 1;
        lpCmd[34] = (byte) 1;
        lpCmd[35] = (byte) 7;
        lpCmd[36] = (byte) 1;
        lpCmd[37] = this.m_OneJob.bQlty;
        lpCmd[38] = (byte) 1;
        lpCmd[39] = (byte) 1;
        lpCmd[40] = (byte) 8;
        lpCmd[41] = (byte) 1;
        lpCmd[42] = this.m_OneJob.bType;
        lpCmd[43] = (byte) 1;
        lpCmd[44] = (byte) 1;
        lpCmd[45] = (byte) 9;
        lpCmd[46] = (byte) 1;
        lpCmd[47] = this.m_OneJob.bMSize;
        lpCmd[48] = (byte) 1;
        lpCmd[49] = (byte) 1;
        lpCmd[50] = (byte) 22;
        lpCmd[51] = (byte) 1;
        lpCmd[52] = this.m_OneJob.bPrintLayout;
        lpCmd[53] = (byte) 9;
        lpCmd[54] = (byte) 1;
        lpCmd[55] = (byte) 13;
        lpCmd[56] = (byte) 1;
        lpCmd[57] = this.m_OneJob.bTxtr;
        this.LOG.m385i("Texture " + String.valueOf(this.m_OneJob.bTxtr), "Start_Job_Request");
        lpCmd[58] = (byte) 9;
        lpCmd[59] = (byte) 1;
        lpCmd[60] = (byte) 14;
        lpCmd[61] = (byte) 1;
        lpCmd[62] = this.m_OneJob.boDuplex;
        lpCmd[63] = (byte) 1;
        lpCmd[64] = (byte) 2;
        lpCmd[65] = (byte) 5;
        lpCmd[66] = (byte) 1;
        lpCmd[67] = this.m_OneJob.boMaskColor;
        lpCmd[68] = (byte) 1;
        lpCmd[69] = (byte) 1;
        lpCmd[70] = (byte) 25;
        lpCmd[71] = (byte) 1;
        lpCmd[72] = this.m_OneJob.bSharpen;
        lpCmd[73] = (byte) -1;
        if (WriteRequest(lpCmd, CMD_MAJOR_VERSION, 74) == 74) {
            return true;
        }
        return false;
    }

    public boolean Get_Job_Status() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 3, (byte) 1, (byte) 3, (byte) 2, (byte) 1, (byte) 4, (byte) (this.m_OneJob.shJobId >> 24), (byte) (this.m_OneJob.shJobId >> 16), (byte) (this.m_OneJob.shJobId >> 8), (byte) (this.m_OneJob.shJobId & TelnetOption.MAX_OPTION_VALUE), (byte) -1}, CMD_MAJOR_VERSION, 15) == 15) {
            return true;
        }
        return false;
    }

    public long Send_Data_Request(long bytesToWrite) {
        lpCmd = new byte[28];
        int[] iSizeArray = new int[this.m_OneJob.iTotal];
        iSizeArray[CMD_MAJOR_VERSION] = (int) this.m_OneJob.lSize;
        long nSendLen = bytesToWrite;
        lpCmd[CMD_MAJOR_VERSION] = (byte) 0;
        lpCmd[SEND_JPEG] = (byte) 9;
        lpCmd[SEND_MASK] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 4;
        lpCmd[5] = (byte) 3;
        lpCmd[6] = (byte) 3;
        lpCmd[7] = (byte) 2;
        lpCmd[8] = (byte) 1;
        lpCmd[CMD_MINOR_VERSION] = (byte) 4;
        lpCmd[10] = (byte) (this.m_OneJob.shJobId >> 24);
        lpCmd[11] = (byte) (this.m_OneJob.shJobId >> 16);
        lpCmd[12] = (byte) (this.m_OneJob.shJobId >> 8);
        lpCmd[13] = (byte) (this.m_OneJob.shJobId & TelnetOption.MAX_OPTION_VALUE);
        lpCmd[14] = (byte) 1;
        lpCmd[15] = (byte) 4;
        lpCmd[16] = (byte) 1;
        lpCmd[17] = (byte) 1;
        lpCmd[18] = (byte) 0;
        lpCmd[19] = (byte) 3;
        lpCmd[20] = (byte) 4;
        lpCmd[21] = (byte) 2;
        lpCmd[22] = (byte) 4;
        lpCmd[23] = (byte) ((int) (nSendLen >> 24));
        lpCmd[24] = (byte) ((int) (nSendLen >> 16));
        lpCmd[25] = (byte) ((int) (nSendLen >> 8));
        lpCmd[26] = (byte) ((int) nSendLen);
        lpCmd[27] = (byte) -1;
        long nWrite = (long) WriteRequest(lpCmd, CMD_MAJOR_VERSION, 28);
        if (this.m_OneJob.iTotal > SEND_JPEG) {
            if (this.m_maskData != null) {
                iSizeArray[SEND_JPEG] = this.m_OneJob.iMaskSize;
            }
            if (this.m_duplexData != null) {
                iSizeArray[SEND_JPEG] = this.m_OneJob.iDuplexSize;
            }
        }
        if (this.m_duplexData == null) {
            nWrite = MaskHeader(false, this.m_OneJob.iTotal, iSizeArray);
        } else {
            nWrite = MaskHeader(true, this.m_OneJob.iTotal, iSizeArray);
        }
        nWrite += (long) WriteRequest(this.m_imageData, (int) this.m_offsetWrite, (int) this.m_OneJob.lSize);
        if (this.m_OneJob.iTotal <= SEND_JPEG) {
            return nWrite;
        }
        if (this.m_maskData != null) {
            nWrite += (long) WriteRequest(this.m_maskData, CMD_MAJOR_VERSION, this.m_OneJob.iMaskSize);
        }
        if (this.m_duplexData != null) {
            return nWrite + ((long) WriteRequest(this.m_duplexData, CMD_MAJOR_VERSION, this.m_OneJob.iDuplexSize));
        }
        return nWrite;
    }

    public long MaskHeader(boolean bDuplex, int iTotalCount, int... iFileSize) {
        int i;
        int iSendLen = (iTotalCount * 8) + SEND_JPEG;
        byte[] lpCmd = new byte[iSendLen];
        byte bFileCount = (byte) 0;
        for (i = CMD_MAJOR_VERSION; i < iTotalCount; i += SEND_JPEG) {
            bFileCount = (byte) (bFileCount + SEND_JPEG);
        }
        int iIndex = CMD_MAJOR_VERSION + SEND_JPEG;
        lpCmd[CMD_MAJOR_VERSION] = bFileCount;
        int iSignature = SEND_JPEG;
        for (i = CMD_MAJOR_VERSION; i < iTotalCount; i += SEND_JPEG) {
            int iOffset;
            if (i != 0) {
                iSignature = bDuplex ? SEND_JPEG : SEND_MASK;
            }
            int i2 = iIndex + SEND_JPEG;
            lpCmd[iIndex] = (byte) (iSignature >> 24);
            iIndex = i2 + SEND_JPEG;
            lpCmd[i2] = (byte) (iSignature >> 16);
            i2 = iIndex + SEND_JPEG;
            lpCmd[iIndex] = (byte) (iSignature >> 8);
            iIndex = i2 + SEND_JPEG;
            lpCmd[i2] = (byte) (iSignature & TelnetOption.MAX_OPTION_VALUE);
            if (i == 0) {
                iOffset = (iTotalCount * 8) + SEND_JPEG;
            } else {
                int iOtherSize = CMD_MAJOR_VERSION;
                for (int j = CMD_MAJOR_VERSION; j < i; j += SEND_JPEG) {
                    iOtherSize += iFileSize[j];
                }
                iOffset = ((iTotalCount * 8) + SEND_JPEG) + iOtherSize;
            }
            i2 = iIndex + SEND_JPEG;
            lpCmd[iIndex] = (byte) (iOffset >> 24);
            iIndex = i2 + SEND_JPEG;
            lpCmd[i2] = (byte) (iOffset >> 16);
            i2 = iIndex + SEND_JPEG;
            lpCmd[iIndex] = (byte) (iOffset >> 8);
            iIndex = i2 + SEND_JPEG;
            lpCmd[i2] = (byte) (iOffset & TelnetOption.MAX_OPTION_VALUE);
        }
        return (long) WriteRequest(lpCmd, CMD_MAJOR_VERSION, iSendLen);
    }

    public boolean Get_Printer_Capabilities_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 2, (byte) -1, (byte) 1, (byte) 1, (byte) 19}, CMD_MAJOR_VERSION, 10) == 10) {
            return true;
        }
        return false;
    }

    public boolean Get_Update_Firmware_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 5, (byte) 2, (byte) -1}, CMD_MAJOR_VERSION, 7) == 7) {
            return true;
        }
        return false;
    }

    public long Send_Firmware_Data_Request(long bytesToWrite) {
        lpCmd = new byte[20];
        long nSendLen = bytesToWrite;
        lpCmd[CMD_MAJOR_VERSION] = (byte) 0;
        lpCmd[SEND_JPEG] = (byte) 9;
        lpCmd[SEND_MASK] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 4;
        lpCmd[5] = (byte) 3;
        lpCmd[6] = (byte) 1;
        lpCmd[7] = (byte) 4;
        lpCmd[8] = (byte) 1;
        lpCmd[CMD_MINOR_VERSION] = (byte) 1;
        lpCmd[10] = (byte) 1;
        lpCmd[11] = (byte) 3;
        lpCmd[12] = (byte) 4;
        lpCmd[13] = (byte) 2;
        lpCmd[14] = (byte) 4;
        lpCmd[15] = (byte) ((int) (nSendLen >> 24));
        lpCmd[16] = (byte) ((int) (nSendLen >> 16));
        lpCmd[17] = (byte) ((int) (nSendLen >> 8));
        lpCmd[18] = (byte) ((int) nSendLen);
        lpCmd[19] = (byte) -1;
        return (long) WriteRequest(lpCmd, CMD_MAJOR_VERSION, 20);
    }

    public long Send_Firmware_Content_Request(byte[] data) {
        return (long) WriteRequest(data, CMD_MAJOR_VERSION, data.length);
    }

    public boolean Auto_Power_off_Request(byte[] boPowerOffTime) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 6, (byte) 80, (byte) -32, (byte) 49, (byte) 0, boPowerOffTime[CMD_MAJOR_VERSION], boPowerOffTime[SEND_JPEG]}, CMD_MAJOR_VERSION, 16) == 16) {
            return true;
        }
        return false;
    }

    public boolean Configure_NetWork_Setting_Request(String strSSID, String strPassword) {
        int nCmdLen;
        byte[] lpCmd = new byte[TransportMediator.FLAG_KEY_MEDIA_NEXT];
        lpCmd[CMD_MAJOR_VERSION] = (byte) 0;
        lpCmd[SEND_JPEG] = (byte) 9;
        lpCmd[SEND_MASK] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 7;
        lpCmd[5] = (byte) 2;
        lpCmd[6] = (byte) 1;
        lpCmd[7] = (byte) 8;
        lpCmd[8] = (byte) 1;
        lpCmd[CMD_MINOR_VERSION] = (byte) 1;
        lpCmd[10] = (byte) 0;
        lpCmd[11] = (byte) 7;
        lpCmd[12] = (byte) 8;
        lpCmd[13] = (byte) 2;
        lpCmd[14] = (byte) strSSID.length();
        int nCmdLen2 = 15;
        byte[] bSSID = strSSID.getBytes();
        int i = CMD_MAJOR_VERSION;
        while (i < strSSID.length()) {
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = bSSID[i];
            i += SEND_JPEG;
            nCmdLen2 = nCmdLen;
        }
        if (strPassword.length() != 0) {
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = (byte) 1;
            nCmdLen2 = nCmdLen + SEND_JPEG;
            lpCmd[nCmdLen] = (byte) 8;
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = (byte) 3;
            nCmdLen2 = nCmdLen + SEND_JPEG;
            lpCmd[nCmdLen] = (byte) 1;
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = (byte) 0;
            nCmdLen2 = nCmdLen + SEND_JPEG;
            lpCmd[nCmdLen] = (byte) 8;
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = (byte) 8;
            nCmdLen2 = nCmdLen + SEND_JPEG;
            lpCmd[nCmdLen] = (byte) 4;
            nCmdLen = nCmdLen2 + SEND_JPEG;
            lpCmd[nCmdLen2] = (byte) strPassword.length();
            byte[] bPassword = strPassword.getBytes();
            i = CMD_MAJOR_VERSION;
            nCmdLen2 = nCmdLen;
            while (i < strPassword.length()) {
                nCmdLen = nCmdLen2 + SEND_JPEG;
                lpCmd[nCmdLen2] = bPassword[i];
                i += SEND_JPEG;
                nCmdLen2 = nCmdLen;
            }
        }
        nCmdLen = nCmdLen2 + SEND_JPEG;
        lpCmd[nCmdLen2] = (byte) -1;
        if (WriteRequest(lpCmd, CMD_MAJOR_VERSION, nCmdLen) == nCmdLen) {
            return true;
        }
        return false;
    }

    public boolean Get_Print_Frame_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 4, (byte) 80, Byte.MIN_VALUE, (byte) 5, (byte) 1}, CMD_MAJOR_VERSION, 14) == 14) {
            return true;
        }
        return false;
    }

    public boolean Error_Recovery_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 5, (byte) 1, (byte) -1}, CMD_MAJOR_VERSION, 7) == 7) {
            return true;
        }
        return false;
    }

    public boolean Get_Print_Frame_Or_Page_Request(byte byPaperType) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 4, (byte) 80, Byte.MIN_VALUE, (byte) 5, byPaperType}, CMD_MAJOR_VERSION, 14) == 14) {
            return true;
        }
        return false;
    }

    public int GetTotalSize(int iTotalCount, int... iFileSize) {
        int iTotalFileSize = CMD_MAJOR_VERSION;
        for (int i = CMD_MAJOR_VERSION; i < iTotalCount; i += SEND_JPEG) {
            iTotalFileSize += iFileSize[i];
            this.LOG.m385i("iFileSize ", String.valueOf(iFileSize[i]));
        }
        return ((iTotalCount * 8) + SEND_JPEG) + iTotalFileSize;
    }

    public boolean PreparePhoto(Bitmap editPhoto, Bitmap maskPhoto, Bitmap duplexPhoto) {
        this.LOG.m385i("PreparePhoto", "editPhoto: " + editPhoto);
        this.LOG.m385i("PreparePhoto", "duplexPhoto: " + duplexPhoto);
        if (editPhoto != null) {
            String strEditPath = FileUtility.GetSDAppRootPath(this.m_Context) + "/" + "PrintPhotoJPEGCompress.jpg";
            if (!FileUtility.SaveBitmap(strEditPath, editPhoto, CompressFormat.JPEG)) {
                return false;
            }
            this.m_imageData = FileUtility.GetFileToByte(strEditPath);
            this.LOG.m384e("Send Size", String.valueOf(this.m_imageData.length));
        }
        if (maskPhoto != null) {
            this.m_maskData = new PackBits().GetPackBitsMask(maskPhoto);
            if (this.m_maskData == null) {
                return false;
            }
        }
        if (duplexPhoto != null) {
            String strDuplexPath = FileUtility.GetSDAppRootPath(this.m_Context) + "/" + "PrintPhotoJPEGDuplex.jpg";
            if (!FileUtility.SaveBitmap(strDuplexPath, duplexPhoto, CompressFormat.JPEG)) {
                return false;
            }
            this.m_duplexData = FileUtility.GetFileToByte(strDuplexPath);
            if (this.m_duplexData == null) {
                return false;
            }
        }
        return true;
    }

    public String GetWirelessType(byte[] buf, int iStart) {
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == 4) {
            return WirelessType.TYPE_P231;
        }
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == 7) {
            return WirelessType.TYPE_P232;
        }
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == SEND_MASK) {
            return WirelessType.TYPE_P520L;
        }
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == 10) {
            return WirelessType.TYPE_P310W;
        }
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == SEND_JPEG) {
            return WirelessType.TYPE_P750L;
        }
        if (buf[iStart] == (byte) 5 && buf[iStart + SEND_JPEG] == CMD_MINOR_VERSION) {
            return WirelessType.TYPE_P461;
        }
        if (buf[iStart] == 0 && buf[iStart + SEND_JPEG] == 15) {
            return WirelessType.TYPE_P530D;
        }
        return WirelessType.TYPE_UNKNOWN;
    }

    public int GetPrinterPageNumber(String strPrinterID, long printerTotalFrame) {
        if (strPrinterID == null) {
            return -1;
        }
        if (strPrinterID.equals(WirelessType.TYPE_P231)) {
            return (int) printerTotalFrame;
        }
        if (strPrinterID.equals(WirelessType.TYPE_P232)) {
            return (int) printerTotalFrame;
        }
        if (strPrinterID.equals(WirelessType.TYPE_P310W)) {
            return (int) (printerTotalFrame / 4);
        }
        if (strPrinterID.equals(WirelessType.TYPE_P461)) {
            return (int) (printerTotalFrame / 4);
        }
        if (strPrinterID.equals(WirelessType.TYPE_P520L)) {
            return (int) printerTotalFrame;
        }
        if (strPrinterID.equals(WirelessType.TYPE_P750L)) {
            return (int) printerTotalFrame;
        }
        if (strPrinterID.equals(WirelessType.TYPE_P530D)) {
            return (int) printerTotalFrame;
        }
        return -1;
    }
}
