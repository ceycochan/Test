package com.example.administrator.destoryactivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.security.PrivilegedAction;


/**
 * ★★★销毁activity时注意关闭线程★★★
 * Created by Administrator on 2016/8/28 0028.
 */
public class SubActivity extends AppCompatActivity {
    private static final String TAG = "ThreadDemo";
    private int count = 0;
    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, Thread.currentThread().getName() + "SubActivity" + "" + count);
            count++;
            setTitle("" + count);
            //每2秒执行一次
            mHandler.postDelayed(mRunnable, 2000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        //通过Handler启动线程
        mHandler.post(mRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将线程销毁掉
        //mHandler.removeCallbacks(mRunnable);
    }

}
