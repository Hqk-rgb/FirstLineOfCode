package com.whf.network;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.whf.network.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        WebView webView = findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://www.baidu.com");


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendRequest.setOnClickListener(v -> {
            if (v.getId() == R.id.send_request){
                // 使用 httpURLConnection
                // sendRequestWithHttpURLConnection();
                // 使用 OKHttp
                sendRequestWithOKHttp();
            }
        });


    }

    private void sendRequestWithOKHttp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://www.baidu.com").build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                showResponse(responseData);
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void sendRequestWithHttpURLConnection() {
        //开启线程来发起网络请求
        new Thread(() -> {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://www.baidu.com");
                connection = (HttpURLConnection) url.openConnection();
                //从服务器获取数据
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in = connection.getInputStream();

                //向服务器提交数据
                /*connection.setRequestMethod("POST");
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes("username=admin&password=123456");*/

                //下面对获取到的输入流进行读取
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line = null;
                while (null != (line = reader.readLine())) {
                    response.append(line);
                }
                showResponse(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(null != reader) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(null != connection) {
                    connection.disconnect();
                }
            }
        }).start();
    }


    private void showResponse(final String response) {
        runOnUiThread(() -> binding.responseText.setText(response));
    }
}