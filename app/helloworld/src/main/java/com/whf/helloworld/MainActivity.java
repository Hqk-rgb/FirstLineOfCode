package com.whf.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author whf
 */
public class MainActivity extends AppCompatActivity {

    // logt+Tab键 自动生成TAG
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "onCreate: 创建");

        setSupportActionBar(findViewById(R.id.toolbar));

        Button btn = findViewById(R.id.btn_pop);
        btn.setOnClickListener(v -> {
            // 销毁活动
            //finish();
            //Toast.makeText(MainActivity.this, "弹窗通知", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (item.getItemId() == R.id.add_item) {
            Toast.makeText(this, "你点击了添加按钮", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.remove_item) {
            Toast.makeText(this, "你点击了移除按钮", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return true;
        }
    }
}