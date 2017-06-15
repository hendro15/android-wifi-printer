package com.example.sonic.hitiprinter.utility;

public class CIELab {
    private static final double f444N = 0.13793103448275862d;

    private static class Holder {
        static final CIELab INSTANCE;

        private Holder() {
        }

        static {
            INSTANCE = new CIELab();
        }
    }

    public static CIELab getInstance() {
        return Holder.INSTANCE;
    }

    public float[] FromRGBtoLab(float[] rgbvalue) {
        return FromCIEXYZtoLab(FromRGBtoCIEXYZ(rgbvalue));
    }

    private float[] FromRGBtoCIEXYZ(float[] rgbvalue) {
        double R = (double) rgbvalue[0];
        double G = (double) rgbvalue[1];
        double B = (double) rgbvalue[2];
        double X = ((1.07645d * R) - (0.237662d * G)) + (0.161212d * B);
        double Y = ((0.410964d * R) + (0.554342d * G)) + (0.034694d * B);
        double Z = ((-0.010954d * R) - (0.013389d * G)) + (1.024343d * B);
        return new float[]{(float) X, (float) Y, (float) Z};
    }

    private float[] FromCIEXYZtoLab(float[] xyz) {
        double l = m382f((double) xyz[1]);
        double L = (116.0d * l) - 16.0d;
        double a = 500.0d * (m382f((double) xyz[0]) - l);
        double b = 200.0d * (l - m382f((double) xyz[2]));
        return new float[]{(float) L, (float) a, (float) b};
    }

    public float[] FromLabToRGB(float[] colorLab) {
        return FromXYZtoRGB(FromLabToCIEXYZ(colorLab));
    }

    private float[] FromLabToCIEXYZ(float[] colorLab) {
        double i = (((double) colorLab[0]) + 16.0d) * 0.008620689655172414d;
        double X = fInv((((double) colorLab[1]) * 0.002d) + i);
        double Y = fInv(i);
        double Z = fInv(i - (((double) colorLab[2]) * 0.005d));
        return new float[]{(float) X, (float) Y, (float) Z};
    }

    private float[] FromXYZtoRGB(float[] xyz) {
        double X = (double) xyz[0];
        double Y = (double) xyz[1];
        double Z = (double) xyz[2];
        double R = ((0.7982d * X) + (0.3389d * Y)) - (0.1371d * Z);
        double G = ((-0.5918d * X) + (1.5512d * Y)) + (0.0406d * Z);
        double B = ((8.0E-4d * X) + (0.0239d * Y)) + (0.9753d * Z);
        return new float[]{(float) R, (float) G, (float) B};
    }

    private double m382f(double x) {
        if (x > 0.008856451679035631d) {
            return Math.cbrt(x);
        }
        return (7.787037037037037d * x) + f444N;
    }

    private static double fInv(double x) {
        if (x > 0.20689655172413793d) {
            return (x * x) * x;
        }
        return 0.12841854934601665d * (x - f444N);
    }

    public float getMaxValue(int component) {
        return 128.0f;
    }

    public float getMinValue(int component) {
        return component == 0 ? 0.0f : -128.0f;
    }

    public String getName(int idx) {
        return String.valueOf("Lab".charAt(idx));
    }

    private Object readResolve() {
        return getInstance();
    }
}
