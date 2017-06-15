package com.example.sonic.hitiprinter.trace;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.example.sonic.hitiprinter.utility.setting.DateInfo.DateType;
import java.util.HashMap;
import java.util.Map;

public class GlobalVariable_OtherSetting extends BaseGlobalVariable {
    Map<DateType, Integer> mAddDateMap;
    private int m_ApplyAddDate;
    private int m_ApplyBLE;
    private int m_ApplyGCM;
    private int m_BLENotify;
    private boolean m_EditPrintNotAskAgain;
    private int m_GCMType;
    private int m_LoadADMethod;
    private boolean m_SnapPrintNotAskAgain;

    public GlobalVariable_OtherSetting(Context context) {
        super(context);
        this.m_ApplyGCM = 1;
        this.m_ApplyBLE = 1;
        this.m_BLENotify = 0;
        this.m_GCMType = -1;
        this.m_LoadADMethod = 1;
        this.m_ApplyAddDate = 0;
        this.mAddDateMap = null;
        this.m_SnapPrintNotAskAgain = false;
        this.m_EditPrintNotAskAgain = false;
        this.m_fsp = context.getSharedPreferences("pref_fgv_other_setting", 0);
        this.LOG.m385i("GlobalVariable_OtherSetting", "Create");
        this.mAddDateMap = new HashMap();
    }

    public void RestoreGlobalVariable() {
        try {
            this.m_ApplyGCM = this.m_fsp.getInt("GV_M_APPLY_GCM", 1);
            this.LOG.m385i("m_ApplyGCM", String.valueOf(this.m_ApplyGCM));
            this.m_ApplyBLE = this.m_fsp.getInt("GV_M_APPLY_BLE", 1);
            this.LOG.m385i("m_ApplyBLE", String.valueOf(this.m_ApplyBLE));
            this.m_BLENotify = this.m_fsp.getInt("GV_M_BLE_NOTIFY", 0);
            this.LOG.m385i("m_BLENotify", String.valueOf(this.m_BLENotify));
            this.m_GCMType = this.m_fsp.getInt("GV_M_GCM_TYPE", -1);
            this.LOG.m385i("m_GCMType", String.valueOf(this.m_GCMType));
            this.m_LoadADMethod = this.m_fsp.getInt("GV_M_LOAD_AD_METHOD", 1);
            this.LOG.m385i("m_LoadADMethod", String.valueOf(this.m_LoadADMethod));
            this.m_SnapPrintNotAskAgain = this.m_fsp.getBoolean("GV_M_SNAP_PRINT_NOT_ASK", false);
            this.m_EditPrintNotAskAgain = this.m_fsp.getBoolean("GV_M_PRINTER_NOT_ASK", false);
            this.m_ApplyAddDate = this.m_fsp.getInt("GV_M_APPLY_ADD_DATE", 0);
            this.mAddDateMap.put(DateType.Format, Integer.valueOf(this.m_fsp.getInt("GGV_M_ADD_DATE_FORMAT", 0)));
            this.mAddDateMap.put(DateType.Position, Integer.valueOf(this.m_fsp.getInt("GV_M_ADD_DATE_POSITION", 0)));
            this.mAddDateMap.put(DateType.Color, Integer.valueOf(this.m_fsp.getInt("GV_M_ADD_DATE_COLOR", 0)));
            SetEdit(false);
            this.LOG.m385i("GlobalVariable_OtherSetting", "RestoreGlobalVariable");
        } catch (Exception e) {
            this.LOG.m384e("GlobalVariable_OtherSetting", "RestoreGlobalVariable Fail");
            e.printStackTrace();
        }
    }

    public void SaveGlobalVariable() {
        SaveGlobalVariableForever();
    }

    public void SaveGlobalVariableForever() {
        if (IsEdit()) {
            try {
                Editor spe = this.m_fsp.edit();
                spe.putInt("GV_M_APPLY_GCM", this.m_ApplyGCM);
                spe.putInt("GV_M_APPLY_BLE", this.m_ApplyBLE);
                spe.putInt("GV_M_BLE_NOTIFY", this.m_BLENotify);
                spe.putInt("GV_M_GCM_TYPE", this.m_GCMType);
                spe.putInt("GV_M_LOAD_AD_METHOD", this.m_LoadADMethod);
                spe.putBoolean("GV_M_SNAP_PRINT_NOT_ASK", this.m_SnapPrintNotAskAgain);
                spe.putBoolean("GV_M_PRINTER_NOT_ASK", this.m_EditPrintNotAskAgain);
                if (!this.mAddDateMap.isEmpty()) {
                    spe.putInt("GGV_M_ADD_DATE_FORMAT", ((Integer) this.mAddDateMap.get(DateType.Format)).intValue());
                    spe.putInt("GV_M_ADD_DATE_POSITION", ((Integer) this.mAddDateMap.get(DateType.Position)).intValue());
                    spe.putInt("GV_M_ADD_DATE_COLOR", ((Integer) this.mAddDateMap.get(DateType.Color)).intValue());
                }
                spe.putInt("GV_M_APPLY_ADD_DATE", this.m_ApplyAddDate);
                if (!spe.commit()) {
                    this.LOG.m384e("GlobalVariable_OtherSetting", "SaveGlobalVariableForever Fail");
                }
                SetEdit(false);
                this.LOG.m385i("GlobalVariable_OtherSetting", "SaveGlobalVariableForever");
            } catch (Exception ex) {
                this.LOG.m384e("GlobalVariable_OtherSetting", "SaveGlobalVariableForever Fail");
                ex.printStackTrace();
            }
        }
    }

    public int GetApplyGCM() {
        return this.m_ApplyGCM;
    }

    public void SetApplyGCM(int ApplyGCM) {
        this.m_ApplyGCM = ApplyGCM;
        SetEdit(true);
    }

    public int GetApplyBLE() {
        return this.m_ApplyBLE;
    }

    public void SetApplyBLE(int ApplyGCM) {
        this.m_ApplyBLE = ApplyGCM;
        SetEdit(true);
    }

    public int GetBLENotify() {
        return this.m_BLENotify;
    }

    public void SetBLENotify(int BLENotify) {
        this.m_BLENotify = BLENotify;
        SetEdit(true);
    }

    public int GetGCMType() {
        return this.m_GCMType;
    }

    public void SetGCMType(int GCMType) {
        this.m_GCMType = GCMType;
        SetEdit(true);
    }

    public int GetLoadADMethod() {
        return this.m_LoadADMethod;
    }

    public void SetLoadADMethod(int LoadADMethod) {
        this.m_LoadADMethod = LoadADMethod;
        SetEdit(true);
    }

    public void SetSnapPrintNotAskAgain(boolean bRet) {
        this.m_SnapPrintNotAskAgain = bRet;
        SetEdit(true);
    }

    public boolean GetSnapPrintNotAskAgain() {
        return this.m_SnapPrintNotAskAgain;
    }

    public void SetEditPrintNotAskAgain(boolean bRet) {
        this.m_EditPrintNotAskAgain = bRet;
        SetEdit(true);
    }

    public boolean GetEditPrintNotAskAgain() {
        return this.m_EditPrintNotAskAgain;
    }

    public void SetAddDateItemId(DateType key, int iItemId) {
        this.mAddDateMap.put(key, Integer.valueOf(iItemId));
        SetEdit(true);
    }

    public int GetAddDateItemId(DateType key) {
        return ((Integer) this.mAddDateMap.get(key)).intValue();
    }

    public int GetApplyAddDate() {
        return this.m_ApplyAddDate;
    }

    public void SetApplyAddDate(int ApplyAddDate) {
        this.m_ApplyAddDate = ApplyAddDate;
        SetEdit(true);
    }
}
