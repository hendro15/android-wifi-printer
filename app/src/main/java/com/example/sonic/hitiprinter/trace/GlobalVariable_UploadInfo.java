package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

public class GlobalVariable_UploadInfo extends BaseGlobalVariable {
    private String m_AESCount;
    private String m_SerialNumber;
    private int m_Upload;
    private int m_UploadE03;
    private int m_UploadMethod;
    private ArrayList<String> m_UploadPath;
    private ArrayList<String> m_UploadTime;
    private String m_Uploader;
    private int m_Version;
    private int m_iCountryCode;

    public GlobalVariable_UploadInfo(Context context) {
        super(context);
        this.m_Version = 0;
        this.m_SerialNumber = XmlPullParser.NO_NAMESPACE;
        this.m_AESCount = XmlPullParser.NO_NAMESPACE;
        this.m_Uploader = XmlPullParser.NO_NAMESPACE;
        this.m_Upload = -1;
        this.m_UploadMethod = 0;
        this.m_UploadE03 = 0;
        this.m_iCountryCode = 0;
        this.m_UploadPath = null;
        this.m_UploadTime = null;
        this.m_fsp = context.getSharedPreferences("pref_fgv_upload_info", 0);
        this.m_UploadPath = new ArrayList();
        this.m_UploadTime = new ArrayList();
        this.LOG.m385i("GlobalVariable_UploadInfo", "Create");
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_AESCount = this.m_fsp.getString("GV_M_AESCOUNT", XmlPullParser.NO_NAMESPACE);
            this.m_SerialNumber = this.m_fsp.getString("GV_M_SERIAL_NUMBER", XmlPullParser.NO_NAMESPACE);
            this.m_Uploader = this.m_fsp.getString("GV_M_UPLOADER", XmlPullParser.NO_NAMESPACE);
            this.m_Upload = this.m_fsp.getInt("GV_M_UPLOAD", -1);
            this.m_UploadMethod = this.m_fsp.getInt("GV_M_UPLOAD_METHOD", 0);
            this.m_UploadE03 = this.m_fsp.getInt("GV_M_UPLOAD_E03", 0);
            this.m_Version = this.m_fsp.getInt("GV_M_VERSION", 0);
            this.m_iCountryCode = this.m_fsp.getInt("GV_M_COUNTRY_CODE", 0);
            this.m_UploadPath.clear();
            this.m_UploadTime.clear();
            int i = 0;
            String strUploadPath = this.m_fsp.getString("GV_M_UPLOAD_PATH" + 0, XmlPullParser.NO_NAMESPACE);
            while (strUploadPath.compareTo(XmlPullParser.NO_NAMESPACE) != 0) {
                this.m_UploadPath.add(strUploadPath);
                i++;
                strUploadPath = this.m_fsp.getString("GV_M_UPLOAD_PATH" + i, XmlPullParser.NO_NAMESPACE);
            }
            i = 0;
            String strUploadTime = this.m_fsp.getString("GV_M_UPLOAD_TIME" + 0, XmlPullParser.NO_NAMESPACE);
            while (strUploadTime.compareTo(XmlPullParser.NO_NAMESPACE) != 0) {
                this.m_UploadTime.add(strUploadTime);
                i++;
                strUploadTime = this.m_fsp.getString("GV_M_UPLOAD_TIME" + i, XmlPullParser.NO_NAMESPACE);
            }
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_UploadInfo", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_UploadInfo", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                int i;
                Editor spe = this.m_fsp.edit();
                spe.clear();
                spe.putString("GV_M_AESCOUNT", this.m_AESCount);
                spe.putString("GV_M_SERIAL_NUMBER", this.m_SerialNumber);
                spe.putString("GV_M_UPLOADER", this.m_Uploader);
                spe.putInt("GV_M_UPLOAD", this.m_Upload);
                spe.putInt("GV_M_UPLOAD_METHOD", this.m_UploadMethod);
                spe.putInt("GV_M_UPLOAD_E03", this.m_UploadE03);
                spe.putInt("GV_M_VERSION", this.m_Version);
                spe.putInt("GV_M_COUNTRY_CODE", this.m_iCountryCode);
                for (i = 0; i < this.m_UploadPath.size(); i++) {
                    spe.putString("GV_M_UPLOAD_PATH" + i, (String) this.m_UploadPath.get(i));
                }
                for (i = 0; i < this.m_UploadTime.size(); i++) {
                    spe.putString("GV_M_UPLOAD_TIME" + i, (String) this.m_UploadTime.get(i));
                }
                this.LOG.m385i("save- m_Upload", String.valueOf(this.m_Upload));
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_UploadInfo", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_UploadInfo", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_UploadInfo", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public String GetSerialNumber() {
        return this.m_SerialNumber;
    }

    public void SetSerialNumber(String SerialNumber) {
        this.m_SerialNumber = SerialNumber;
        SetEdit(true);
    }

    public String GetAESCount() {
        return this.m_AESCount;
    }

    public void SetAESCount(String AESCount) {
        this.m_AESCount = AESCount;
        SetEdit(true);
    }

    public String GetUploader() {
        return this.m_Uploader;
    }

    public void SetUploader(String Uploader) {
        this.m_Uploader = Uploader;
        SetEdit(true);
    }

    public int GetUpload() {
        return this.m_Upload;
    }

    public void SetUpload(int Upload) {
        this.m_Upload = Upload;
        SetEdit(true);
    }

    public int GetUploadMethod() {
        return this.m_UploadMethod;
    }

    public void SetUploadMethod(int UploadMethod) {
        this.m_UploadMethod = UploadMethod;
        SetEdit(true);
    }

    public int GetUploadE03() {
        return this.m_UploadE03;
    }

    public void SetUploadE03(int UploadE03) {
        this.m_UploadE03 = UploadE03;
        SetEdit(true);
    }

    public String GetUploadPath(int iIndex) {
        return (String) this.m_UploadPath.get(iIndex);
    }

    private void AddUploadPath(String UploadPath) {
        this.m_UploadPath.add(UploadPath);
        SetEdit(true);
    }

    private void RemoveUploadPath(int iIndex) {
        this.m_UploadPath.remove(iIndex);
        SetEdit(true);
    }

    private void ClearUploadPath() {
        this.m_UploadPath.clear();
        SetEdit(true);
    }

    public String GetUploadTime(int iIndex) {
        return (String) this.m_UploadTime.get(iIndex);
    }

    private void AddUploadTime(String UploadTime) {
        this.m_UploadTime.add(UploadTime);
        SetEdit(true);
    }

    private void RemoveUploadTime(int iIndex) {
        this.m_UploadTime.remove(iIndex);
        SetEdit(true);
    }

    private void ClearUploadTime() {
        this.m_UploadTime.clear();
        SetEdit(true);
    }

    public void AddUploadFile(String UploadPath, String UploadTime) {
        AddUploadPath(UploadPath);
        AddUploadTime(UploadTime);
        SetEdit(true);
    }

    public void RemoveUploadFile(int iIndex) {
        this.LOG.m384e("RemoveUploadFile", "id: " + iIndex);
        RemoveUploadPath(iIndex);
        RemoveUploadTime(iIndex);
        SetEdit(true);
    }

    public void ClearUploadFile() {
        this.LOG.m384e("ClearUploadFile", "ClearUploadFile");
        ClearUploadPath();
        ClearUploadTime();
        SetEdit(true);
    }

    public int GetUploadFileSize() {
        return this.m_UploadPath.size();
    }

    public int GetVersion() {
        return this.m_Version;
    }

    public void SetVersion(int Version) {
        this.m_Version = Version;
        SetEdit(true);
    }

    public void SetCountryCode(int iCountryCode) {
        this.m_iCountryCode = iCountryCode;
        SetEdit(true);
    }

    public int GetCountryCode() {
        return this.m_iCountryCode;
    }
}
