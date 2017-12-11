package com.example.administrator.handlerpack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Handler如果使用sendMessage的方式把消息入队到消息队列中，需要传递一个Message对象，
 * 而在Handler中，
 * 需要重写handleMessage()方法，用于获取工作线程传递过来的消息，此方法运行在UI线程上。
 */
public class ThirdActivity extends AppCompatActivity {

    private Button btn_down;
    private Button btn_next;
    private ImageView iv_show;
    private static String image_path = "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
    private ProgressDialog dialog;
    private static int IS_FINISH = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                Bitmap bitmap = (Bitmap) msg.obj;
                iv_show.setImageBitmap(bitmap);
                // 隐藏对话框
                dialog.dismiss();
            }
        }
    };

    /**
     * Dialog并不属于UI组件，你从它所在的包下就可以看出来，它是属于android.app.Dialog包下的。
     * 这里所谓的UI组件仅是android.view.view包下的组件。所以Dialog可以在子线程中操作。
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        btn_down = (Button) findViewById(R.id.btn_download);
        btn_next = (Button) findViewById(R.id.btn_next);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在下载.......");
        dialog.setCancelable(false);

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread()).start();
                dialog.show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
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
                Message msg = Message.obtain();
                msg.obj = bitmap;
                msg.what = IS_FINISH;
                mHandler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
