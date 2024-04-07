package com.whf.network;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.whf.network.databinding.ActivityXmlBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author dell
 */
public class XmlActivity extends AppCompatActivity {

    private ActivityXmlBinding binding;
    List<App> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityXmlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendXml.setOnClickListener(v -> {
            if (v.getId() == R.id.send_xml){
                // 使用 OKHttp
                sendRequestWithOKHttp();
            }
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(XmlActivity.this));
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataList);
            binding.recyclerView.setAdapter(adapter);
        });


    }



    private void sendRequestWithOKHttp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.10.10.137/get_data.xml")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseXMLWithPull(responseData);
                runOnUiThread(() -> {
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataList);
                    binding.recyclerView.setAdapter(adapter);
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void parseXMLWithPull(final String xmlData) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                if (eventType == XmlPullParser.START_TAG){
                    if ("id".equals(nodeName)){
                        id = xmlPullParser.nextText();
                    } else if ("name".equals(nodeName)) {
                        name = xmlPullParser.nextText();
                    } else if ("version".equals(nodeName)) {
                        version = xmlPullParser.nextText();
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if ("app".equals(nodeName)){
                        App app = new App(id,name,version);
                        dataList.add(app);

                        Log.d("xml", "id 为: "+id);
                        Log.d("xml", "name 为: "+name);
                        Log.d("xml", "version 为: "+version);
                        Log.d("xml", "数据集合: "+dataList);

                    }
                }
                eventType = xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Log.d("xml", "循环外 "+dataList);
    }
}