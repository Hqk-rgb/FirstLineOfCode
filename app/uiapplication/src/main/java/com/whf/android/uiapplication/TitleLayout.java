package com.whf.android.uiapplication;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context,  @Nullable AttributeSet attrs) {
        super(context,attrs);

        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button titleBack = findViewById(R.id.title_back);
        Button titleEdit = findViewById(R.id.title_edit);
        titleBack.setOnClickListener(v -> ((Activity)getContext()).finish());
        titleEdit.setOnClickListener(v -> Toast.makeText(getContext(), "你点击了编辑按钮", Toast.LENGTH_SHORT).show());
    }
}
