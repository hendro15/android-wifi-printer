package com.example.sonic.hitiprinter.utility;

public class MathUtility {
    public static float Spacing(float x, float y, float dx, float dy) {
        float x2 = x - dx;
        float y2 = y - dy;
        return (float) Math.sqrt((double) ((x2 * x2) + (y2 * y2)));
    }

    public static float Diagonal(float x, float y) {
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public static boolean IsZero(float a, float b) {
        if (((double) (Math.abs(a - b) / Math.max(Math.abs(a), Math.abs(b)))) < 1.0E-6d) {
            return true;
        }
        return false;
    }

    public static boolean IsZero(float a) {
        if (new Float("0.0").compareTo(Float.valueOf(a)) == 0) {
            return true;
        }
        return false;
    }
}
