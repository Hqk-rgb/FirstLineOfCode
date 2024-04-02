package com.whf.android.persistence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.android.persistence.databinding.ActivityForgetPwdBinding;

public class ForgetPwdActivity extends AppCompatActivity {

    private ActivityForgetPwdBinding binding;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember){
            // 将账号密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            binding.account.setText(account);
            binding.password.setText(password);
            binding.rememberPass.setChecked(true);
        }

        binding.login.setOnClickListener(v -> {
            String account = binding.account.getText().toString();
            String password = binding.password.getText().toString();
            if (account.equals("admin") && password.equals("123456")){
                editor = pref.edit();
                if (binding.rememberPass.isChecked()){
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", binding.account.getText().toString());
                    editor.putString("password", binding.password.getText().toString());
                }else {
                    editor.clear();
                }
                editor.apply();
                Intent intent = new Intent(ForgetPwdActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(ForgetPwdActivity.this, "account or password is invalid!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}