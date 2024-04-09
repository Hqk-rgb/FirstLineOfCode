package com.whf.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.service.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //启动服务
        binding.startService.setOnClickListener(v -> {
            Intent startIntent = new Intent(MainActivity.this, MyService.class);
            startService(startIntent);
        });
        //停止服务
        binding.stopService.setOnClickListener(v -> {
            Intent stopIntent = new Intent(MainActivity.this, MyService.class);
            stopService(stopIntent);
        });

        // 绑定服务
        binding.bindService.setOnClickListener(v -> {
            Intent bindIntent = new Intent(MainActivity.this, MyService.class);
            bindService(bindIntent,connection,BIND_AUTO_CREATE);
        });

        // 解绑服务
        binding.unbindService.setOnClickListener(v -> unbindService(connection));

        // 启动IntentService
        binding.intentService.setOnClickListener(v -> {
            Log.d("Intent", "Thread id is:"+Thread.currentThread().getId());
            Intent intentService = new Intent(MainActivity.this,MyIntentService.class);
            startService(intentService);
        });

    }
}