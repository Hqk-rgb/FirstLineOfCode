package com.whf.advanced.util;

import android.widget.Toast;

import com.whf.advanced.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {


        public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
            if (!isNetworkAvailable()){
                Toast.makeText(MyApplication.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(() -> {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    //从服务器获取数据
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();

                    //下面对获取到的输入流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while (null != (line = reader.readLine())) {
                        response.append(line);
                    }
                    if (listener!=null){
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if (listener!=null){
                        listener.onError(e);
                    }
                }finally{
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }).start();
        }

    private static boolean isNetworkAvailable() {
            return true;
    }

//        public static void sendOkHttpRequest(String address, Callback callback){
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder().url(address).build();
//            client.newCall(request).enqueue(callback);
//        }

}
