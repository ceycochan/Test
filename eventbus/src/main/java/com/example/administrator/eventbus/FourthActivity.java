package com.example.administrator.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.eventbus.event.FourthEvent;
import com.example.administrator.eventbus.event.SecondEvent;
import com.example.administrator.eventbus.event.ThirdEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/9/15 0015.
 */
public class FourthActivity extends AppCompatActivity {

    private Button btn_second;
    private Button btn_third;
    private Button btn_fourth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        btn_second = (Button) findViewById(R.id.SecondEvent);
        btn_third = (Button) findViewById(R.id.ThirdEvent);
        btn_fourth = (Button) findViewById(R.id.FourthEvent);

        btn_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SecondEvent("2nd event was posted"));
            }
        });

        btn_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ThirdEvent("3rd event was posted"));
            }
        });

        btn_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FourthEvent("4th event was posted"));
            }
        });

    }

}
