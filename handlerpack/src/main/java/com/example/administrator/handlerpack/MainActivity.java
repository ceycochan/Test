package com.example.administrator.handlerpack;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 主要讲解handler中的post & runnable的结合用法
 */
public class MainActivity extends AppCompatActivity {

    private Button btn01;
    private Button btn02;
    private TextView tv_msg;
    //声明一个Handler对象
    private static Handler mHandler = new Handler();
    private Button btn03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn01 = (Button) findViewById(R.id.btn_01);
        btn02 = (Button) findViewById(R.id.btn_02);
        btn03 = (Button) findViewById(R.id.btn_03);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新启动一个子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // tv_msg.setText("...");
                        // 以上操作会报错，无法在子线程中访问UI组件，UI组件的属性必须在UI线程中访问
                        // 使用post方式修改UI组件tvMessage的Text属性
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_msg.setText("使用Handler.post在工作线程中发送一段执行到消息队列中，在主线程中执行");
                            }
                        });
                    }
                }).start();
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 使用postDelayed方式修改UI组件tvMessage的Text属性值
                        // 并且延迟3S执行
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tv_msg.setText("使用Handler.postDelayed在工作线程中发送一段执行到消息队列中，在主线程中延迟3S执行。");
                            }
                        }, 3000);
                    }
                }).start();
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

/**
 * 有一点值得注意的是，对于Post方式而言，它其中Runnable对象的run()方法的代码，
 * 均执行在UI线程上，所以对于这段代码而言，不能执行在UI线程上的操作，一样无法使用post方式执行，比如说访问网络，
 */
