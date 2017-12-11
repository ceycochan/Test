package com.example.administrator.destoryactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ThreadDemo";
    private Button btn_trans;
    private int count = 0;
    private Handler mHandler = new MyHandler();
    boolean stopThread = false;
    /**
     * 1.开一个线程每2秒钟想UI线程提交一个信息去请求更新title
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (!stopThread) {
                count++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 虽然Message的构造函数是public的，
                 但是最好是使用Message.obtain( )或Handler.obtainMessage( )函数来获取Message对象，
                 因为Message的实现中包含了回收再利用的机制，可以提供效率。
                 */
                Message message = mHandler.obtainMessage();
                message.what = 0;
                message.obj = count;
                mHandler.sendMessage(message);
            }
        }
    };


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, Thread.currentThread().getName() + "" + msg.obj);
            setTitle("" + msg.obj);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_trans = (Button) findViewById(R.id.btn_trans);
        new Thread(mRunnable).start();
        btn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubActivity.class));
                /**

                */
                MainActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("-----onDestory------");
        stopThread = true;

    }

}
