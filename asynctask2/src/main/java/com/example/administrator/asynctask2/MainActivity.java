package com.example.administrator.asynctask2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
主要练习 Handler的消息地址
 */
public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView imageShow;
    private Button show;
    List<String> imageUrl = null;
    private int num = 0;
    private Handler nHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String str = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageShow = (ImageView) findViewById(R.id.image_show);
        show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new showButtonListener());
        imageUrl = new ArrayList<String>(); // 图片地址List
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2011/266/AIO90AV2508S.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/063N2L5N2HID.jpg");
        imageUrl.add("http://comic.sinaimg.cn/2011/0824/U5237P1157DT20110824161051.jpg");
        imageUrl.add("http://image.tianjimedia.com/uploadImages/2012/090/1429QO6389U8.jpg");
        imageUrl.add("http://new.aliyiyao.com/UpFiles/Image/2011/01/13/nc_129393721364387442.jpg");
    }


    public class showButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            num++;
            MyAsyncTask myAsyncTask = new MyAsyncTask(getApplicationContext());
            //这里实现循环加载
            myAsyncTask.execute(imageUrl.get(num % imageUrl.size()));

        }
    }

    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        public MyAsyncTask(Context context) {
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            /**
             根据url取得图片并返回
             后台线程中运行,负责耗时操作。
             */
            try {
                URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "传回图片了";
                //发送消息到ui线程
                nHandler.sendMessage(msg);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        /**
         * 在doInBackground 执行完成后，onPostExecute方法将被UI thread调用，
         * 后台的计算结果将通过该方法传递到UI thread.
         */

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
            imageShow.setVisibility(View.VISIBLE);
            if (bitmap != null) {
                imageShow.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        }

        /**
         * 该方法将在执行实际的后台操作前被UI thread调用。这个方法只是做一些准备工作，如在界面上显示一个进度条。
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "任务开始", Toast.LENGTH_SHORT).show();
        }

    }

}
