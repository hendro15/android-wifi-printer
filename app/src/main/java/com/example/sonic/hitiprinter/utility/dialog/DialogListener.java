package com.example.sonic.hitiprinter.utility.dialog;

import com.example.sonic.hitiprinter.utility.Verify.ThreadMode;

public abstract class DialogListener {
    public abstract void CancelConnetion(ThreadMode threadMode);

    public abstract void LeaveCancel();

    public abstract void LeaveClose();

    public abstract void LeaveConfirm();

    public abstract void SetLastConnSSID(String str);

    public abstract void SetNowConnSSID(String str);
}
