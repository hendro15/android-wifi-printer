package com.example.sonic.hitiprinter.printerprotocol.utility;

import com.example.sonic.hitiprinter.printerprotocol.request.HitiPPR_PrinterCommand;
import java.net.Socket;

public interface IMobile {
    void CancelPrint(String str);

    void ChangeCopies(String str, int i);

    void EndCheckPrintInfo(String str, String str2);

    void ErrorBitmap(String str);

    void ErrorOccur(String str);

    void ErrorOccurDueToPrinter(String str);

    void ErrorTimeOut(String str);

    void GetMethodAndSharpen(int i, int i2);

    void InitialBusy(String str);

    void IsPrinterBusy(String str);

    void MediaSizeNotMatch(String str);

    void PaperJamAgain(String str);

    void PaperJamDone();

    void PhotoLawQty(String str, int i, HitiPPR_PrinterCommand hitiPPR_PrinterCommand);

    void PreparePhoto();

    void PrintDone(Socket socket, int i, int i2, int i3);

    void RecoveryDone(Socket socket);

    void SendingPhoto(String str);

    void SendingPhotoDone(Socket socket, int i);

    void StartBurnFW(Socket socket);

    void StartCheckPrintInfo();
}
