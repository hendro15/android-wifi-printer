package com.example.sonic.hitiprinter.printerprotocol;

public class WirelessType {
    public static String TYPE_P231;
    public static String TYPE_P232;
    public static String TYPE_P235;
    public static String TYPE_P310W;
    public static String TYPE_P461;
    public static String TYPE_P520L;
    public static String TYPE_P525L;
    public static String TYPE_P530D;
    public static String TYPE_P750L;
    public static String TYPE_UNKNOWN;

    static {
        TYPE_UNKNOWN = "0000";
        TYPE_P231 = PrinterErrorCode.ERROR_CODE_PRINTER_0504;
        TYPE_P232 = PrinterErrorCode.ERROR_CODE_PRINTER_0507;
        TYPE_P235 = "050b";
        TYPE_P520L = PrinterErrorCode.ERROR_CODE_PRINTER_0502;
        TYPE_P310W = "050a";
        TYPE_P750L = PrinterErrorCode.ERROR_CODE_PRINTER_0501;
        TYPE_P461 = PrinterErrorCode.ERROR_CODE_PRINTER_0509;
        TYPE_P530D = "000F";
        TYPE_P525L = "special!!!!";
    }

    public static int GetServerMachineID(String type) {
        if (type.equals(TYPE_P231)) {
            return 1;
        }
        if (type.equals(TYPE_P232)) {
            return 2;
        }
        if (type.equals(TYPE_P310W)) {
            return 4;
        }
        if (type.equals(TYPE_P520L)) {
            return 3;
        }
        if (type.equals(TYPE_P750L)) {
            return 5;
        }
        if (type.equals(TYPE_P461)) {
            return 7;
        }
        if (type.equals(TYPE_P530D)) {
            return 8;
        }
        return 0;
    }
}
