package com.whf.advanced;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> Toast.makeText(LongRunningService.this, "定时任务", Toast.LENGTH_SHORT).show()).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int hour = 60 * 60 * 1000; // 一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime()+hour;
        Intent i = new Intent(this, LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i, PendingIntent.FLAG_IMMUTABLE);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
}