package com.whf.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.helloworld.databinding.ActivitySecondBinding;
import com.whf.helloworld.util.DateUtil;

/**
 * @author whf
 */
public class SecondActivity extends BaseActivity {

    private ActivitySecondBinding binding;

    private String phoneNo = "10086";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBaidu.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.baiu.com"));
            startActivity(intent);
        });

        binding.btnMobile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            startActivity(intent);
        });

        binding.btnSms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sms:" + phoneNo));
            startActivity(intent);
        });

        // 从上个Activity获取信息
        Bundle bundle = getIntent().getExtras();
        String time = bundle.getString("request_time");
        String content = bundle.getString("extra_data");
        String data = String.format("收到请求信息：\n时间：%s\n内容：%s", time, content);
        binding.tvResult.setText(data);

        //返回数据给上个Activity
        binding.tvResponse.setText("待返回消息：没有");
        binding.btnResponse.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("response_time", DateUtil.getNowTime());
            bundle1.putString("response_content","没有");
            intent.putExtras(bundle1);
            // 携带意图返回上一个页面，OK表示处理成功
            setResult(Activity.RESULT_OK,intent);
            // 结束当前活动页面
            finish();
        });

        binding.btnFinishall.setOnClickListener(v -> {
            ActivityCollector.finishAll();
        });

    }




}