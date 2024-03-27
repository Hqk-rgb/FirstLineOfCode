package com.whf.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.helloworld.databinding.ActivityMainBinding;
import com.whf.helloworld.util.DateUtil;

/**
 * @author whf
 */
public class MainActivity extends BaseActivity {

    // logt+Tab键 自动生成TAG
    private static final String TAG = "MainActivity";

    private ActivityResultLauncher<Intent> register;

    private final String request = "下班了吗";

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
        TextView tv_send = findViewById(R.id.tv_send);
        btn.setOnClickListener(v -> {
            // 销毁活动
            //finish();
            Toast.makeText(MainActivity.this, "发送消息给下一Activity", Toast.LENGTH_SHORT).show();
            // 显式 Intent
            Intent intent = new Intent(this, SecondActivity.class);
            // 创建包裹发送信息至下一个Activity
            Bundle bundle = new Bundle();
            bundle.putString("extra_data",tv_send.getText().toString());
            bundle.putString("request_time", DateUtil.getNowTime());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText("待发送的消息："+request);
        TextView tv_response = findViewById(R.id.tv_response);
        Button btn_request = findViewById(R.id.btn_request);
        btn_request.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            // 创建一个新包裹
            Bundle bundle = new Bundle();
            bundle.putString("request_time", DateUtil.getNowTime());
            bundle.putString("request_content",request);
            intent.putExtras(bundle);
            register.launch(intent);
        });


        // 返回数据 StartActivityForResult()
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o ->{
            if (o != null){
                Intent intent = o.getData();
                if (intent != null && o.getResultCode() == Activity.RESULT_OK){
                    Bundle bundle = intent.getExtras();
                    String response_time = bundle.getString("response_time");
                    String response_content = bundle.getString("response_content");
                    String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s",response_time,response_content);
                    tv_response.setText(desc);
                }
            }
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