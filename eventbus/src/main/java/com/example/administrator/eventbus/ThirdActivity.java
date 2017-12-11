package com.example.administrator.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.eventbus.event.FourthEvent;
import com.example.administrator.eventbus.event.SecondEvent;
import com.example.administrator.eventbus.event.ThirdEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class ThirdActivity extends AppCompatActivity {

    private Button btn_fourth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        EventBus.getDefault().register(this);
        btn_fourth = (Button) findViewById(R.id.btn_fourth);

        btn_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FourthActivity.class));
            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recvEvent(SecondEvent secondEvent) {
        Log.i("nshane", "main收到了信息:" + secondEvent.getmMsg());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recvEvent(ThirdEvent thirdEvent) {
        Log.i("nshane", "main收到了信息:" + thirdEvent.getmMsg());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void recv2Event(ThirdEvent thirdEvent) {
        Log.i("nshane", "posting收到了信息:" + thirdEvent.getmMsg());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void recv3Event(ThirdEvent thirdEvent) {
        Log.i("nshane", "background收到了信息:" + thirdEvent.getmMsg());
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void recvEvent(FourthEvent fourthEvent) {
        Log.i("nshane", "posting收到了信息:" + fourthEvent.getmMsg());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
