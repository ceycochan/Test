package weipiao.nshan.com.sharedpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = (EditText) findViewById(R.id.et_name);
        EditText etPass = (EditText) findViewById(R.id.et_pass);
    }
}
