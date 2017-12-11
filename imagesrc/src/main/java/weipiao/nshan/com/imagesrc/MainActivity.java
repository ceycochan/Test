package weipiao.nshan.com.imagesrc;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {


    private TextView tv_test;
    private Button btn_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_text = (Button) findViewById(R.id.test);
        tv_test = (TextView) findViewById(R.id.test);
        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubThread().start();
            }
        });
    }

    class SubThread extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            tv_test.setText("hahahaha");
            Toast.makeText(getApplicationContext(), "subThread", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
