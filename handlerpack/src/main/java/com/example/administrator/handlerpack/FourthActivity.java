package com.example.administrator.handlerpack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class FourthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private TextView tv_show;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 3 || msg.what == 5) {
                tv_show.setText("what=" + msg.what + ",this is an empty msg");
            } else {
                tv_show.setText("what=" + msg.what + "," + msg.toString());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                // 使用Message.Obtain+Handler.sendMessage()发送消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = "使用Message.Obtain+Handler.sendMessage()发送消息";
                        mHandler.sendMessage(msg);
                    }
                }).start();
                break;
            case R.id.btn_2:
                // 使用Message.sendToTarget发送消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain(mHandler);
                        msg.what = 2;
                        msg.obj = "使用Message.sendToTarget发送消息";
                        /**
                         * Sends this Message to the Handler specified by {@link #getTarget}.
                         * Throws a null pointer exception if this field has not been set.
                         *
                         * 所以,使用这种传值方法必须在Message.obtain();中传入mHandler对象。
                         */
                        msg.sendToTarget();
                    }
                }).start();
                break;
            case R.id.btn_3:
                // 发送一个空消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(3);
                    }
                }).start();
                break;
            case R.id.btn_4:
                //使用Message.Obtain+Handler.sendMessage()发送延迟消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        msg.what = 4;
                        msg.obj = "使用Message.Obtain+Handler.sendMessage()发送延迟消息";
                        mHandler.sendMessageDelayed(msg, 1500);
                    }
                }).start();
                break;
            case R.id.btn_5:
                //// 发送一个延迟的空消息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessageDelayed(5, 1500);
                    }
                }).start();
                break;
        }
    }
}
