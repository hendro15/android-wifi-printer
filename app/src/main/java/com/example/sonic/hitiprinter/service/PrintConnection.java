package com.example.sonic.hitiprinter.service;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.example.sonic.hitiprinter.utility.LogManager;

public class PrintConnection implements ServiceConnection {
    LogManager LOG;
    String TAG;
    private boolean mBound;
    IConnection mListener;
    NotificationManager mNotificationManager;
    private PrintService mService;

    public interface IConnection {
        void getService(PrintService printService);
    }

    public PrintConnection(Context context) {
        this.mService = null;
        this.mBound = false;
        this.mListener = null;
        this.LOG = null;
        this.TAG = "PrintConnection";
        this.mNotificationManager = null;
        this.LOG = new LogManager(0);
        this.LOG.m383d(this.TAG, "PrintConnection");
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.LOG.m383d(this.TAG, "onServiceConnected");
        this.mService = ((PrintBinder) iBinder).getService();
        if (this.mListener != null) {
            this.mListener.getService(this.mService);
        }
        this.mBound = true;
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.LOG.m383d(this.TAG, "onServiceDisconnected");
        this.mBound = false;
    }

    public PrintService getService() {
        return this.mService;
    }

    public boolean isBound() {
        return this.mBound;
    }

    public void setBound(boolean bound) {
        this.mBound = bound;
    }

    public void setListener(IConnection listener) {
        this.LOG.m383d(this.TAG, "setListener");
        this.mListener = listener;
    }

    public void updateNotification(String message) {
        if (this.mService != null && this.mBound) {
            this.mNotificationManager.notify(this.mService.getNotifyID(), this.mService.getNotifyBuilder().setContentText(message).build());
        }
    }

    public void stop() {
        if (this.mService != null) {
            this.mNotificationManager.cancel(this.mService.getNotifyID());
            this.mService.stopSelf();
        }
    }
}
