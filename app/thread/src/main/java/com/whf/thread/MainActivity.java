package com.whf.thread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.thread.databinding.ActivityMainBinding;

/**
 * @author dell
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int UPDATE_TEXT = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_TEXT) {//在这里可以进行UI操作
                binding.text.setText("Nice to meet you.");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.changeText.setOnClickListener(v -> {
            if (v.getId() == R.id.change_text) {
                new Thread(() -> {
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message);    //将Message对象发送出去
                }).start();
            }
        });
    }

}
