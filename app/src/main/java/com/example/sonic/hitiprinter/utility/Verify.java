package com.example.sonic.hitiprinter.utility;

public interface Verify {

    public enum AnimaType {
        fadeIn,
        fadeOut,
        shine2,
        shine_message,
        non
    }

    public enum HintType {
        SnapPrint,
        GeneralPrint,
        CloudBack
    }

    public enum PrintMode {
        Snap,
        NormalMain,
        NFC,
        NFC_launch,
        Init,
        Init_Launch,
        camera,
        NormalPrint,
        EditPrint,
        CloudALbum,
        BLE
    }

    public enum SortType {
        Name,
        Date
    }

    public enum ThreadMode {
        Non,
        SetSSID,
        AutoWifi,
        GetInfo,
        FethcImage,
        AlbumMeta,
        ThumnailMeta,
        DeleteToEdit,
        MakePhoto,
        PaperJam
    }

    public enum VERIFFY_PROCESS {
        REGIST,
        LOGIN
    }

    public enum VerifyToWhere {
        NON,
        Member,
        DownLoadCenter,
        SampleAlbum,
        CloudAlbum,
        BorderPage,
        StampPage,
        RollerPage,
        Print
    }
}
