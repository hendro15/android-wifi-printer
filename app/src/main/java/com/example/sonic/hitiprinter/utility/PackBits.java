package com.example.sonic.hitiprinter.utility;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.support.v4.media.TransportMediator;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitor;
import com.example.sonic.hitiprinter.bitmapmanager.BitmapMonitorResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.apache.commons.net.telnet.TelnetOption;

public class PackBits {

    public class MyByteArrayOutputStream extends OutputStream {
        private final byte[] bytes;
        private int count;

        public MyByteArrayOutputStream(int length) {
            this.count = 0;
            this.bytes = new byte[length];
        }

        public void write(int value) throws IOException {
            if (this.count >= this.bytes.length) {
                throw new IOException("Write exceeded expected length (" + this.count + ", " + this.bytes.length + ")");
            }
            this.bytes[this.count] = (byte) value;
            this.count++;
        }

        public byte[] toByteArray() {
            if (this.count >= this.bytes.length) {
                return this.bytes;
            }
            byte[] result = new byte[this.count];
            System.arraycopy(this.bytes, 0, result, 0, this.count);
            return result;
        }

        public int getBytesWritten() {
            return this.count;
        }
    }

    public byte[] decompress(byte[] bytes, int expected) {
        int total = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        while (total < expected) {
            int i2 = i + 1;
            int n = bytes[i];
            int count;
            int j;
            if (n >= 0 && n <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
                count = n + 1;
                total += count;
                j = 0;
                i = i2;
                while (j < count) {
                    i2 = i + 1;
                    baos.write(bytes[i]);
                    j++;
                    i = i2;
                }
                i2 = i;
            } else if (n >= -127 && n <= -1) {
                i = i2 + 1;
                int b = bytes[i2];
                count = (-n) + 1;
                total += count;
                for (j = 0; j < count; j++) {
                    baos.write(b);
                }
                i2 = i;
            } else if (n == -128) {
            }
            i = i2;
        }
        return baos.toByteArray();
    }

    private int findNextDuplicate(byte[] bytes, int start) {
        if (start >= bytes.length) {
            return -1;
        }
        byte prev = bytes[start];
        for (int i = start + 1; i < bytes.length; i++) {
            byte b = bytes[i];
            if (b == prev) {
                return i - 1;
            }
            prev = b;
        }
        return -1;
    }

    private int findRunLength(byte[] bytes, int start) {
        byte b = bytes[start];
        int iOffset = start + 1;
        int i = start + 1;
        while (i < bytes.length && i < iOffset + TransportMediator.FLAG_KEY_MEDIA_NEXT && bytes[i] == b) {
            i++;
        }
        return i - start;
    }

    public byte[] compress(byte[] bytes) throws IOException {
        MyByteArrayOutputStream baos = new MyByteArrayOutputStream(bytes.length * 2);
        int ptr = 0;
        while (ptr < bytes.length) {
            int dup = findNextDuplicate(bytes, ptr);
            int actual_len;
            if (dup == ptr) {
                actual_len = Math.min(findRunLength(bytes, dup), TransportMediator.FLAG_KEY_MEDIA_NEXT);
                baos.write(-(actual_len - 1));
                baos.write(bytes[ptr]);
                ptr += actual_len;
            } else {
                int len = dup - ptr;
                if (dup > 0) {
                    int runlen = findRunLength(bytes, dup);
                    if (runlen < 3) {
                        int nextptr = (ptr + len) + runlen;
                        int nextdup = findNextDuplicate(bytes, nextptr);
                        if (nextdup != nextptr) {
                            dup = nextdup;
                            len = dup - ptr;
                        }
                    }
                }
                if (dup < 0) {
                    len = bytes.length - ptr;
                }
                actual_len = Math.min(len, TransportMediator.FLAG_KEY_MEDIA_NEXT);
                baos.write(actual_len - 1);
                for (int i = 0; i < actual_len; i++) {
                    baos.write(bytes[ptr]);
                    ptr++;
                }
            }
        }
        byte[] result = baos.toByteArray();
        try {
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte[] GetPackBitsMask(Bitmap bmp) {
        byte[] RetByteArray = null;
        BitmapMonitorResult bmr = BitmapMonitor.CreateBitmap(bmp.getWidth(), bmp.getHeight(), Config.ALPHA_8);
        if (!bmr.IsSuccess()) {
            return null;
        }
        Bitmap retBmp = bmr.GetBitmap();
        new Canvas(retBmp).drawBitmap(bmp, 0.0f, 0.0f, null);
        int iBufferSize = bmp.getWidth() * bmp.getHeight();
        ByteBuffer RLEByteBuffer = ByteBuffer.allocate(iBufferSize);
        retBmp.copyPixelsToBuffer(RLEByteBuffer);
        retBmp.recycle();
        byte[] RLEByteArray = new byte[iBufferSize];
        RLEByteBuffer.position(0);
        RLEByteBuffer.get(RLEByteArray);
        try {
            byte[] result = compress(RLEByteArray);
            RetByteArray = new byte[(result.length + 4)];
            RetByteArray[0] = (byte) (iBufferSize >> 24);
            RetByteArray[1] = (byte) (iBufferSize >> 16);
            RetByteArray[2] = (byte) (iBufferSize >> 8);
            RetByteArray[3] = (byte) (iBufferSize & TelnetOption.MAX_OPTION_VALUE);
            System.arraycopy(result, 0, RetByteArray, 4, result.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RLEByteBuffer.clear();
        return RetByteArray;
    }

    public byte[] GetMaskFromPackBitsMask(byte[] byteArray, int size) {
        return decompress(byteArray, size);
    }
}
