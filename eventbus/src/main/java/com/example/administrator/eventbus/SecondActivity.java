package com.example.administrator.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.eventbus.event.FirstEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class SecondActivity extends AppCompatActivity {

    private Button btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                EventBus以post方法发送消息
                */
                EventBus.getDefault().post(new FirstEvent("First btn had been clicked"));
            }
        });
    }
}
