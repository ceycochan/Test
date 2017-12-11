package weipiao.nshan.com.weakrefer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private Button btn_down;
    private ImageView iv_show;
    private static final String image_path = "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
    private MyHandler mHandler = new MyHandler(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_down = (Button) findViewById(R.id.btn_download);
        iv_show = (ImageView) findViewById(R.id.iv_show);
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread()).start();
            }
        });
    }

    static class MyHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        public MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mActivityReference.get();
            if (activity != null) {

            }
        }
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
                 在通过Post传入一个Runnable对象时，其实是生成了一个Message对象，
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
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
