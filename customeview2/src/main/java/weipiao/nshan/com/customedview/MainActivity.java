package weipiao.nshan.com.customedview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CharAvatarView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avatarView = (CharAvatarView) findViewById(R.id.avatar);
        avatarView.setText("sss");
    }
}
