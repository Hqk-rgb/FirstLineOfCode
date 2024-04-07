package com.whf.network;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.network.databinding.ActivityJsonBinding;
import com.whf.network.databinding.ActivityXmlBinding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonActivity extends AppCompatActivity {

    private ActivityJsonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJsonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendJson.setOnClickListener(v -> {
            if (v.getId() == R.id.send_json){
                // 使用 OKHttp
                sendRequestWithOKHttp();
            }
        });
    }


    private void sendRequestWithOKHttp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.10.10.137/get_data.json")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJsonWithJsonObject(responseData);
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void parseJsonWithJsonObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("xml", "id 为: "+id);
                Log.d("xml", "name 为: "+name);
                Log.d("xml", "version 为: "+version);
                //Log.d("xml", "数据集合: "+dataList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}