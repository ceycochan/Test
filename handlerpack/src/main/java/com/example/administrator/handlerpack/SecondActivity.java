package com.example.administrator.handlerpack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 主要讲解handler中的post & runnable的结合用法
 */
public class SecondActivity extends AppCompatActivity {

    private ImageView iv_show;
    private Button btn_down;
    private static final String image_path = "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
    private ProgressDialog dialog;
    // 一个静态的Handler，Handler建议声明为静态的
    private static Handler mHandler = new Handler();
    private Button btn_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        btn_down = (Button) findViewById(R.id.btn_download);
        btn_next = (Button) findViewById(R.id.btn_next);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在下载");
        dialog.setCancelable(false);

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启一个子线程，用于下载图片
                new Thread(new MyThread()).start();
                //显示对话框
                dialog.show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    class MyThread implements Runnable {

        @Override
        public void run() {
            //下载一张图片
            URL url = null;
            try {
                url = new URL(image_path);
                URLConnection conn = url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                // 得到一个Bitmap对象，并且为了使其在post内部可以访问，必须声明为final
                final Bitmap bitmap = BitmapFactory.decodeStream(is);
                /**
                 在通过Post传入一个Runnable对象时，其实是先生成了一个Message对象，
                 然后将Runnabe对象赋值给了这个Message对象的callback属性，
                 最后是将整个Message对象发送到了消息队列中。
                 */
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在Post中操作UI组件ImageView
                        iv_show.setImageBitmap(bitmap);
                    }
                });
                // 隐藏对话框
                dialog.dismiss();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
