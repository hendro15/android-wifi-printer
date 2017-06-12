package com.example.sonic.hitiprinter.hiti.print;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import com.example.sonic.hitiprinter.hiti.print.PrintService.IPrintService;
import com.example.sonic.hitiprinter.hiti.print.PrintService.NotifyInfo;
import com.example.sonic.hitiprinter.hiti.print.PrintConnection.IConnection;

/**
 * Created by Hendro E. Prabowo on 12/06/2017.
 */

public class PrintBinder extends Binder{
    PrintService service;

    public interface IBinder {
        NotifyInfo setNotifyInfo(NotifyInfo notifyInfo);

        void startPrint();
    }

    /* renamed from: com.hiti.service.print.PrintBinder.1 */
    static class C08011 implements IConnection {
        final /* synthetic */ IBinder val$callback;
        final /* synthetic */ PrintConnection val$connection;
        final /* synthetic */ Intent val$intent;

        /* renamed from: com.hiti.service.print.PrintBinder.1.1 */
        class C08001 implements IPrintService {
            C08001() {
            }

            public void start(PrintService service) {
                if (C08011.this.val$callback != null) {
                    service.startForeground(service.setNotification(C08011.this.val$callback.setNotifyInfo(service.getNotifyInfo())));
                    C08011.this.val$callback.startPrint();
                }
            }
        }

        C08011(PrintConnection printConnection, IBinder iBinder, Intent intent) {
            this.val$connection = printConnection;
            this.val$callback = iBinder;
            this.val$intent = intent;
        }

        public void getService(PrintService service) {
            if (service != null && this.val$connection != null) {
                service.setListener(new C08001());
                service.startService(this.val$intent);
            }
        }
    }

    public PrintBinder(PrintService service) {
        this.service = null;
        this.service = service;
    }

    PrintService getService() {
        return this.service;
    }

    public static PrintConnection start(Context context, IBinder callback) {
        Intent intent = new Intent(context, PrintService.class);
        PrintConnection connection = new PrintConnection(context);
        connection.setListener(new C08011(connection, callback, intent));
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        return connection;
    }

    public static void stop(Context context, PrintConnection connection) {
        if (connection.isBound()) {
            connection.setBound(false);
            connection.stop();
            context.unbindService(connection);
        }
    }
}
