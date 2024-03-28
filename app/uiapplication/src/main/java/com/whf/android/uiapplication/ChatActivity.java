package com.whf.android.uiapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whf.android.uiapplication.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
//    private EditText editText;
//    private Button send;
//    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initMsgs();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.msgRecyclerView.setLayoutManager(manager);
        adapter = new MsgAdapter(msgList);
        binding.msgRecyclerView.setAdapter(adapter);
        binding.send.setOnClickListener(v -> {
            String content = binding.inputText.getText().toString();
            if (!"".equals(content)){
                Msg msg = new Msg(content,Msg.TYPE_SENT);
                msgList.add(msg);
                // 当有新消息时刷新ListView中的显示
                adapter.notifyItemInserted(msgList.size()-1);
                // 将ListView定位到最后一行
                binding.msgRecyclerView.scrollToPosition(msgList.size()-1);
                // 输入框清空
                binding.inputText.setText("");
            }
        });
    }
    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you.",Msg.TYPE_RECEIVED);
        msgList.add(msg3);

    }
}