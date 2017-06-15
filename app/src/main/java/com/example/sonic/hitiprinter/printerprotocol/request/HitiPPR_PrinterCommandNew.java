package com.example.sonic.hitiprinter.printerprotocol.request;

import android.content.Context;
import android.support.v4.media.TransportMediator;
import com.example.sonic.hitiprinter.printerprotocol.MSGHandler;
import com.example.sonic.hitiprinter.utility.ByteConvertUtility;
import org.apache.commons.net.telnet.TelnetOption;

public abstract class HitiPPR_PrinterCommandNew extends HitiPPR_PrinterCommand {
    public static final String DEFAULT_AP_MODE_IP = "192.168.5.1";
    public static final int DEFAULT_AP_MODE_PORT = 54321;

    public enum Error {
        NOTHUMBNAIL,
        SIZE_OVER_25,
        ERROR_FIND_NO_STORAGEID,
        NOT_SUPPORT_STORAGE,
        STORAGE_ACCESS_DENIED
    }

    public HitiPPR_PrinterCommandNew(Context context, String IP, int iPort, MSGHandler msgHandler) {
        super(context, IP, iPort, msgHandler);
    }

    public boolean Get_Storage_Info_Request(byte[] storageId) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 2, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) -1}, 0, 15) == 15) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Capabilities_For_StorageId_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 2, (byte) -1, (byte) 1, (byte) 1, (byte) 21}, 0, 10) == 10) {
            return true;
        }
        return false;
    }

    public boolean Get_Storage_Id_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 1, (byte) -1}, 0, 7) == 7) {
            return true;
        }
        return false;
    }

    public boolean Get_Object_Handle_Request(byte[] storageId, byte[] parentObjH, byte[] objFormat) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 4, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) 2, (byte) 9, (byte) 4, (byte) 2, objFormat[0], objFormat[1], (byte) 3, (byte) 9, (byte) 2, (byte) 4, parentObjH[0], parentObjH[1], parentObjH[2], parentObjH[3], (byte) -1}, 0, 29) == 29) {
            return true;
        }
        return false;
    }

    public boolean Get_Object_Handle_With_Range_Index_Request(byte[] storageId, byte[] parentObjH, byte[] rangeIndex) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 4, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) 2, (byte) 9, (byte) 4, (byte) 2, (byte) 56, (byte) 1, (byte) 3, (byte) 9, (byte) 2, (byte) 4, parentObjH[0], parentObjH[1], parentObjH[2], parentObjH[3], (byte) 3, (byte) 9, (byte) 5, (byte) 4, rangeIndex[0], rangeIndex[1], rangeIndex[2], rangeIndex[3], (byte) -1}, 0, 37) == 37) {
            return true;
        }
        return false;
    }

    public boolean Get_Object_Number_Request(byte[] storageId, byte[] parentObjH, byte[] objFormat) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 3, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) 2, (byte) 9, (byte) 4, (byte) 2, objFormat[0], objFormat[1], (byte) 3, (byte) 9, (byte) 2, (byte) 4, parentObjH[0], parentObjH[1], parentObjH[2], parentObjH[3], (byte) -1}, 0, 29) == 29) {
            return true;
        }
        return false;
    }

    public boolean Get_Object_Info_Request(byte[] nowStorageId, byte[] objId) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 5, (byte) 3, (byte) 9, (byte) 1, (byte) 4, nowStorageId[0], nowStorageId[1], nowStorageId[2], nowStorageId[3], (byte) 3, (byte) 9, (byte) 2, (byte) 4, objId[0], objId[1], objId[2], objId[3], (byte) -1}, 0, 23) == 23) {
            return true;
        }
        return false;
    }

    public boolean Get_Thumbnail_Request(byte[] storageId, byte[] objId) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 7, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) 3, (byte) 9, (byte) 2, (byte) 4, objId[0], objId[1], objId[2], objId[3], (byte) -1}, 0, 23) == 23) {
            return true;
        }
        return false;
    }

    public boolean Get_Object_Request(byte[] storageId, byte[] objId) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 8, (byte) 6, (byte) 3, (byte) 9, (byte) 1, (byte) 4, storageId[0], storageId[1], storageId[2], storageId[3], (byte) 3, (byte) 9, (byte) 2, (byte) 4, objId[0], objId[1], objId[2], objId[3], (byte) -1}, 0, 23) == 23) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Capabilities_For_Consumer_Remain_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 2, (byte) -1, (byte) 1, (byte) 1, (byte) 12}, 0, 10) == 10) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Capabilities_For_Media_Size() {
        byte[] lpCmd = new byte[14];
        lpCmd[0] = (byte) 0;
        lpCmd[1] = (byte) 9;
        lpCmd[2] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 1;
        lpCmd[5] = (byte) 2;
        lpCmd[6] = (byte) -1;
        lpCmd[7] = (byte) 1;
        lpCmd[8] = (byte) 1;
        lpCmd[9] = (byte) 9;
        if (WriteRequest(lpCmd, 0, 10) == 10) {
            return true;
        }
        return false;
    }

    public boolean Get_Printer_Capabilities_For_QuickPrint_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 2, (byte) -1, (byte) 2, (byte) 1, (byte) 9, (byte) 1, (byte) 13}, 0, 12) == 12) {
            return true;
        }
        return false;
    }

    public boolean Start_Job_For_QuickPrint_Request(byte[][] ObjIdList, byte[][] StorageIdList) {
        this.LOG.m385i("Start_Job_For_QuickPrint_Request", "number of object: " + ObjIdList.length);
        int nCmdLen = ((ObjIdList.length * 16) + 68) + 1;
        byte[] lpCmd = new byte[nCmdLen];
        lpCmd[0] = (byte) 0;
        lpCmd[1] = (byte) 9;
        lpCmd[2] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 2;
        lpCmd[5] = (byte) 2;
        lpCmd[6] = (byte) 3;
        lpCmd[7] = (byte) 2;
        lpCmd[8] = (byte) 1;
        lpCmd[9] = (byte) 4;
        lpCmd[10] = (byte) (this.m_OneJob.shJobId >> 24);
        lpCmd[11] = (byte) (this.m_OneJob.shJobId >> 16);
        lpCmd[12] = (byte) (this.m_OneJob.shJobId >> 8);
        lpCmd[13] = (byte) (this.m_OneJob.shJobId & TelnetOption.MAX_OPTION_VALUE);
        lpCmd[14] = (byte) 1;
        lpCmd[15] = (byte) 1;
        lpCmd[16] = (byte) 10;
        lpCmd[17] = (byte) 1;
        lpCmd[18] = this.m_OneJob.bFrmt;
        lpCmd[19] = (byte) 2;
        lpCmd[20] = (byte) 2;
        lpCmd[21] = (byte) 2;
        lpCmd[22] = (byte) 2;
        lpCmd[23] = (byte) (this.m_OneJob.shCopies >> 8);
        lpCmd[24] = (byte) (this.m_OneJob.shCopies & TelnetOption.MAX_OPTION_VALUE);
        lpCmd[25] = (byte) 3;
        lpCmd[26] = (byte) 2;
        lpCmd[27] = (byte) 3;
        lpCmd[28] = (byte) 4;
        lpCmd[29] = (byte) ((int) (this.m_OneJob.lSize >> 24));
        lpCmd[30] = (byte) ((int) (this.m_OneJob.lSize >> 16));
        lpCmd[31] = (byte) ((int) (this.m_OneJob.lSize >> 8));
        lpCmd[32] = (byte) ((int) this.m_OneJob.lSize);
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
        this.LOG.m385i("bMSize", ":" + this.m_OneJob.bMSize);
        lpCmd[48] = (byte) 1;
        lpCmd[49] = (byte) 1;
        lpCmd[50] = (byte) 22;
        lpCmd[51] = (byte) 1;
        lpCmd[52] = this.m_OneJob.bPrintLayout;
        this.LOG.m385i("bPrintLayout", ":" + this.m_OneJob.bPrintLayout);
        lpCmd[53] = (byte) 9;
        lpCmd[54] = (byte) 1;
        lpCmd[55] = (byte) 14;
        lpCmd[56] = (byte) 1;
        lpCmd[57] = this.m_OneJob.boDuplex;
        this.LOG.m385i("boDuplex", ":" + this.m_OneJob.boDuplex);
        lpCmd[58] = (byte) 9;
        lpCmd[59] = (byte) 1;
        lpCmd[60] = (byte) 13;
        lpCmd[61] = (byte) 1;
        lpCmd[62] = this.m_OneJob.bTxtr;
        lpCmd[63] = (byte) 1;
        lpCmd[64] = (byte) 2;
        lpCmd[65] = (byte) 5;
        lpCmd[66] = (byte) 1;
        lpCmd[67] = this.m_OneJob.boMaskColor;
        int i = 68;
        for (byte[] objId : ObjIdList) {
            int i2 = i + 1;
            lpCmd[i] = (byte) 3;
            i = i2 + 1;
            lpCmd[i2] = (byte) 9;
            i2 = i + 1;
            lpCmd[i] = (byte) 2;
            i = i2 + 1;
            lpCmd[i2] = (byte) 4;
            i2 = i + 1;
            lpCmd[i] = objId[0];
            i = i2 + 1;
            lpCmd[i2] = objId[1];
            i2 = i + 1;
            lpCmd[i] = objId[2];
            i = i2 + 1;
            lpCmd[i2] = objId[3];
            this.LOG.m385i("objId", ":" + ByteConvertUtility.ByteToInt(objId, 0, 4));
        }
        for (byte[] storageId : StorageIdList) {
            int i2 = i + 1;
            lpCmd[i] = (byte) 3;
            i = i2 + 1;
            lpCmd[i2] = (byte) 9;
            i2 = i + 1;
            lpCmd[i] = (byte) 1;
            i = i2 + 1;
            lpCmd[i2] = (byte) 4;
            i2 = i + 1;
            lpCmd[i] = storageId[0];
            i = i2 + 1;
            lpCmd[i2] = storageId[1];
            i2 = i + 1;
            lpCmd[i] = storageId[2];
            i = i2 + 1;
            lpCmd[i2] = storageId[3];
            this.LOG.m385i("objSId", ":" + ByteConvertUtility.ByteToInt(storageId, 0, 4));
        }
        lpCmd[i] = (byte) -1;
        if (WriteRequest(lpCmd, 0, nCmdLen) == nCmdLen) {
            return true;
        }
        return false;
    }

    public boolean Get_Job_Status_Request(int m_ErrorJobId) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 3, (byte) 1, (byte) 3, (byte) 2, (byte) 1, (byte) 4, (byte) (m_ErrorJobId >> 24), (byte) (m_ErrorJobId >> 16), (byte) (m_ErrorJobId >> 8), (byte) (m_ErrorJobId & TelnetOption.MAX_OPTION_VALUE), (byte) -1}, 0, 15) == 15) {
            return true;
        }
        return false;
    }

    public boolean Send_Data_On_Printer_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 4, (byte) 3, (byte) 3, (byte) 2, (byte) 1, (byte) 4, (byte) (this.m_OneJob.shJobId >> 24), (byte) (this.m_OneJob.shJobId >> 16), (byte) (this.m_OneJob.shJobId >> 8), (byte) (this.m_OneJob.shJobId & TelnetOption.MAX_OPTION_VALUE), (byte) 1, (byte) 4, (byte) 1, (byte) 1, (byte) 0, (byte) 3, (byte) 4, (byte) 2, (byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) -1}, 0, 28) == 28) {
            return true;
        }
        return false;
    }

    public boolean Configure_Infra_NetWork_Setting_Request(int iWifiMode, String strSSID, String strPassword) {
        int nCmdLen;
        byte[] lpCmd = new byte[TransportMediator.FLAG_KEY_MEDIA_NEXT];
        lpCmd[0] = (byte) 0;
        lpCmd[1] = (byte) 9;
        lpCmd[2] = Byte.MIN_VALUE;
        lpCmd[3] = (byte) 0;
        lpCmd[4] = (byte) 7;
        lpCmd[5] = (byte) 2;
        lpCmd[6] = (byte) 1;
        lpCmd[7] = (byte) 8;
        lpCmd[8] = (byte) 1;
        lpCmd[9] = (byte) 1;
        lpCmd[10] = (byte) iWifiMode;
        lpCmd[11] = (byte) 7;
        lpCmd[12] = (byte) 8;
        lpCmd[13] = (byte) 2;
        lpCmd[14] = (byte) strSSID.length();
        int nCmdLen2 = 15;
        byte[] bSSID = strSSID.getBytes();
        int i = 0;
        while (i < strSSID.length()) {
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = bSSID[i];
            i++;
            nCmdLen2 = nCmdLen;
        }
        if (strPassword.length() != 0) {
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = (byte) 1;
            nCmdLen2 = nCmdLen + 1;
            lpCmd[nCmdLen] = (byte) 8;
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = (byte) 3;
            nCmdLen2 = nCmdLen + 1;
            lpCmd[nCmdLen] = (byte) 1;
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = (byte) 0;
            nCmdLen2 = nCmdLen + 1;
            lpCmd[nCmdLen] = (byte) 8;
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = (byte) 8;
            nCmdLen2 = nCmdLen + 1;
            lpCmd[nCmdLen] = (byte) 4;
            nCmdLen = nCmdLen2 + 1;
            lpCmd[nCmdLen2] = (byte) strPassword.length();
            byte[] bPassword = strPassword.getBytes();
            i = 0;
            nCmdLen2 = nCmdLen;
            while (i < strPassword.length()) {
                nCmdLen = nCmdLen2 + 1;
                lpCmd[nCmdLen2] = bPassword[i];
                i++;
                nCmdLen2 = nCmdLen;
            }
        }
        nCmdLen = nCmdLen2 + 1;
        lpCmd[nCmdLen2] = (byte) -1;
        if (WriteRequest(lpCmd, 0, nCmdLen) == nCmdLen) {
            return true;
        }
        return false;
    }

    public boolean Configure_AP_NetWork_Setting_Request(int iWifiMode) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 7, (byte) 2, (byte) 1, (byte) 8, (byte) 1, (byte) 1, (byte) iWifiMode, (byte) -1}, 0, 12) == 12) {
            return true;
        }
        return false;
    }

    public boolean Get_NetWork_Current_Config_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 7, (byte) 1, (byte) -1, (byte) 2}, 0, 8) == 8) {
            return true;
        }
        return false;
    }

    public boolean Get_Print_Frame_Or_Page_Request(byte byPaperType) {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 4, (byte) 80, Byte.MIN_VALUE, (byte) 5, byPaperType}, 0, 14) == 14) {
            return true;
        }
        return false;
    }

    public boolean Clean_Mode_Run_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 4, (byte) 80, (byte) -32, (byte) 35, (byte) 1}, 0, 14) == 14) {
            return true;
        }
        return false;
    }

    public boolean Get_UnClean_Number_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 3, (byte) 80, Byte.MIN_VALUE, (byte) 24}, 0, 13) == 13) {
            return true;
        }
        return false;
    }

    public boolean Paper_Jam_Run_Request() {
        if (WriteRequest(new byte[]{(byte) 0, (byte) 9, Byte.MIN_VALUE, (byte) 0, (byte) 0, (byte) 0, (byte) -1, (byte) -91, (byte) 0, (byte) 4, (byte) 80, (byte) -32, (byte) 52, (byte) 0}, 0, 14) == 14) {
            return true;
        }
        return false;
    }
}
