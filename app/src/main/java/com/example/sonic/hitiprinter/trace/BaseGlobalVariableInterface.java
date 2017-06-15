package com.example.sonic.hitiprinter.trace;

public interface BaseGlobalVariableInterface {
    void ClearGlobalVariable();

    void RestoreGlobalVariable();

    void SaveGlobalVariable();

    void SaveGlobalVariableForever();
}
