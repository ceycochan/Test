package weipiao.nshan.com.threadtest;

import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class ThreadTestActivity extends AppCompatActivity {

    private static final String TAG = "TestThreadPriority";

    private boolean mNeedExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        MyThread a = new MyThread("Thread A");
        a.setOSPriority(Process.THREAD_PRIORITY_LOWEST);//19
        a.setPriority(Thread.MAX_PRIORITY);//10

        MyThread b = new MyThread("Thread B");
        b.setOSPriority(Process.THREAD_PRIORITY_URGENT_AUDIO); // -19
        b.setPriority(Thread.MIN_PRIORITY); // 1

        a.start();
        b.start();
    }

    @Override
    public void onBackPressed() {
        mNeedExit = true;
        super.onBackPressed();
    }

    private class MyThread extends Thread {
        private int mOSPriority = Process.THREAD_PRIORITY_DEFAULT;
        private int mLoopCount = 0;

        public MyThread(String threadName) {
            super(threadName);
        }

        public void setOSPriority(int p) {
            mOSPriority = p;
        }

        @Override
        public void run() {
            super.run();
            Process.setThreadPriority(mOSPriority);

            while (!mNeedExit) {
                mLoopCount++;
                Math.log(Math.random() * 1000);//用于测试计算

                Log.d(TAG,
                        new StringBuilder().append(getName())
                                .append(" os priority: ").append(mOSPriority)
                                .append(" java priority: ")
                                .append(getPriority()).append(" loop count: ")
                                .append(mLoopCount).toString());

            }

            Log.d(TAG,
                    new StringBuilder().append(getName()).append(" exiting...")
                            .append(" os priority: ").append(mOSPriority)
                            .append(" java priority: ").append(getPriority())
                            .append(" loop count: ").append(mLoopCount)
                            .toString());
        }
    }
}
