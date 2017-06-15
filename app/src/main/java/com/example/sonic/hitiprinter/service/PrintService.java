package com.example.sonic.hitiprinter.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import com.example.sonic.hitiprinter.utility.LogManager;

public class PrintService extends Service {
    private static final int PRINT_SERVICE_NOTIFICATION_ID = 18;
    LogManager LOG;
    String TAG;
    private IPrintService callback;
    Builder mNotificationBuilder;
    private NotifyInfo mNotifyInfo;
    private PrintBinder mPrintBinder;

    public interface IPrintService {
        void start(PrintService printService);
    }

    public class NotifyInfo {
        public int icon;
        public String message;
        public String title;

        public NotifyInfo() {
            this.icon = 0;
            this.title = null;
            this.message = null;
        }
    }

    public PrintService() {
        this.callback = null;
        this.mNotifyInfo = null;
        this.mNotificationBuilder = null;
        this.LOG = null;
        this.TAG = "PrintService";
    }

    public void onCreate() {
        this.LOG = new LogManager(0);
        this.LOG.m383d(this.TAG, "onCreate");
        this.mPrintBinder = new PrintBinder(this);
        this.mNotifyInfo = new NotifyInfo();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.LOG.m383d(this.TAG, "onStartCommand");
        if (this.callback != null) {
            this.callback.start(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        return this.mPrintBinder;
    }

    public void setListener(IPrintService callback) {
        this.LOG.m383d(this.TAG, "setListener");
        this.callback = callback;
    }

    public void startForeground(Notification notification) {
        this.LOG.m383d(this.TAG, "startForeground");
        startForeground(PRINT_SERVICE_NOTIFICATION_ID, notification);
    }

    public void onDestroy() {
        this.LOG.m383d(this.TAG, "onDestroy");
    }

    public int getNotifyID() {
        return PRINT_SERVICE_NOTIFICATION_ID;
    }

    public Builder getNotifyBuilder() {
        return this.mNotificationBuilder;
    }

    public NotifyInfo getNotifyInfo() {
        return this.mNotifyInfo;
    }

    public Notification setNotification(NotifyInfo info) {
        if (info == null) {
            return null;
        }
        Builder builder = new Builder(this);
        if (info.icon != 0) {
            builder.setSmallIcon(info.icon);
        }
        if (info.title != null) {
            builder.setContentTitle(info.title);
        }
        if (info.message != null) {
            builder.setContentText(info.message);
        }
        this.mNotificationBuilder = builder;
        return builder.build();
    }
}
