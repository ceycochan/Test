package weipiao.nshan.com.fm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private RadioGroup radioGroup;
    private FragmentTransaction transaction;
    private Fragment homeFragment;
    private Fragment sortFragment;
    private Fragment personFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        //初始化默认指定RadioButton为被点击状态
        ((RadioButton) radioGroup.findViewById(R.id.radio0)).setChecked(true);

        transaction = manager.beginTransaction();
        Fragment fragment = new HomeFragment();
        transaction.replace(R.id.content, fragment).commit();
        //RadioButton关联Fragment
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        transaction = manager.beginTransaction();
                        homeFragment = new HomeFragment();
                        transaction.replace(R.id.content, homeFragment);
                        transaction.commit();
                        break;
                    case R.id.radio1:
                        transaction = manager.beginTransaction();
                        sortFragment = new SortFragment();
                        transaction.replace(R.id.content, sortFragment);
                        transaction.commit();
                        break;
                    case R.id.radio2:
                        transaction = manager.beginTransaction();
                        personFragment = new PersonFragment();
                        transaction.replace(R.id.content, personFragment);
                        transaction.commit();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
