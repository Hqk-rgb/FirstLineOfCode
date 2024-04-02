package com.whf.android.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.android.persistence.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String inputText = load();
        if (!TextUtils.isEmpty(inputText)){
            binding.editText.setText(inputText);
            binding.editText.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        // SharedPreferences 存储
        binding.saveData.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putString("name","Joker");
            editor.putInt("age",28);
            editor.putBoolean("married",false);
            editor.apply();
        });
        // 读取 SharedPreferences 内容
        binding.restoreData.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            String name = pref.getString("name","");
            int age = pref.getInt("age",0);
            boolean married = pref.getBoolean("married",false);
            String desc = "Name: "+name+"\nAge: "+age+"\nMarried: "+married;
            binding.data.setText(desc);
        });

        // 创建数据库
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        binding.createDatabase.setOnClickListener(v -> dbHelper.getWritableDatabase());

        // 添加数据
        binding.addData.setOnClickListener(v -> {
            Toast.makeText(this, "添加数据成功", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name","Zoe");
            values.put("author","Riot Games");
            values.put("pages",500);
            values.put("price",60.0);
            db.insert("Book",null,values);
        });

        //更新数据
        binding.updateData.setOnClickListener(v -> {
            Toast.makeText(this, "更新数据成功", Toast.LENGTH_SHORT).show();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put("name","EZreal");
//            db.update("Book",values,"id = ?",new String[]{"2"});
            values.put("pages",800);
            db.update("Book",values,"id = ?",new String[]{"3"});
        });

        // 删除数据
        binding.deleteData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("Book", "pages > ?", new String[]{"500"});
        });

        //查询数据
        binding.queryData.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            StringBuilder dataBuilder = new StringBuilder();
            Cursor cursor = db.query("Book", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    // 遍历Cursor对象，取出数据并打印
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                    @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    String desc = "Name: " + name + "\nAuthor: " + author + "\nPages: " + pages + "\nPrice: " + price;
                    //binding.sqliteData.setText(desc);
                    dataBuilder.append(desc).append("\n\n");
                } while (cursor.moveToNext());
            }
            cursor.close();
            binding.sqliteData.setText(dataBuilder.toString());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = binding.editText.getText().toString();
        save(inputText);
    }

    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!= null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}