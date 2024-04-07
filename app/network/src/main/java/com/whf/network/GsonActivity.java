package com.whf.network;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whf.network.databinding.ActivityGsonBinding;
import com.whf.network.databinding.ActivityJsonBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GsonActivity extends AppCompatActivity {

    private ActivityGsonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGsonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendGson.setOnClickListener(v -> {
            if (v.getId() == R.id.send_gson){
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
                parseJsonWithGson(responseData);
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void parseJsonWithGson(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for (App app : appList){
            Log.d("xml", "id 为: "+app.getId());
            Log.d("xml", "name 为: "+app.getName());
            Log.d("xml", "version 为: "+app.getVersion());
            Log.d("xml", "Gson 为: "+app);
        }
    }
}