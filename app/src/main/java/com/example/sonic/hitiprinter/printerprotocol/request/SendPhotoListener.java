package com.example.sonic.hitiprinter.printerprotocol.request;

import com.example.sonic.hitiprinter.utility.Verify.PrintMode;
import java.net.Socket;

public interface SendPhotoListener {
    boolean CheckJobIdIfEmpty();

    int GetCopies();

    void GetHitiPPR_SendPhotoPrinbiz(HitiPPR_SendPhotoPrinbiz hitiPPR_SendPhotoPrinbiz);

    void SendingPhoto(PrintMode printMode);

    void SetAskState(boolean z, boolean z2, boolean z3);

    void SkipToNextPhoto(Socket socket);

    void onCreateBitmapError(String str);
}
