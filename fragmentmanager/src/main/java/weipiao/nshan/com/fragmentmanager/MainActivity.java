package weipiao.nshan.com.fragmentmanager;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener {
    private ViewPager mViewPager;
    private TextView weixin;
    private TextView friend;
    private TextView address;
    private TextView setting;
    private FragmentAdapter mAdapter;
    //private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        //manager = getSupportFragmentManager();
        initViews();
    }


    private void initViews() {
        weixin = (TextView) findViewById(R.id.weixin);
        friend = (TextView) findViewById(R.id.friend);
        address = (TextView) findViewById(R.id.address);
        setting = (TextView) findViewById(R.id.setting);
        weixin.setOnClickListener(this);
        friend.setOnClickListener(this);
        address.setOnClickListener(this);
        setting.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        //初始化启动APP展示的Fragment
        mViewPager.setCurrentItem(1);
    }

    //点击Button与VP联动
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixin:
                setTabSelection(0);
                break;
            case R.id.friend:
                setTabSelection(1);
                break;
            case R.id.address:
                setTabSelection(2);
                break;
            case R.id.setting:
                setTabSelection(3);
                break;
        }
    }

    //方法实现VP与Button联动
    private void setTabSelection(int index) {
        restBtn();
        switch (index) {
            case 0:
                weixin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_weixin_pressed, 0, 0);
                mViewPager.setCurrentItem(1, false);
                break;
            case 1:
                friend.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_find_frd_pressed, 0, 0);
                mViewPager.setCurrentItem(2, false);
                break;
            case 2:
                address.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_address_pressed, 0, 0);
                mViewPager.setCurrentItem(3, false);
                break;
            case 3:
                setting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_settings_pressed, 0, 0);
                mViewPager.setCurrentItem(4, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //Button与VP联动 (循环)
    @Override
    public void onPageSelected(int position) {
        restBtn();
        switch (position) {
            case 0:
                position = 4;
                mViewPager.setCurrentItem(position, false);
                setting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_settings_pressed, 0, 0);
                break;
            case 1:
                weixin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_weixin_pressed, 0, 0);
                break;
            case 2:
                friend.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_find_frd_pressed, 0, 0);
                break;
            case 3:
                address.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_address_pressed, 0, 0);
                break;
            case 4:
                setting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_settings_pressed, 0, 0);
                break;
            case 5:
                position = 1;
                mViewPager.setCurrentItem(position, false);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //重置Button:将指定的Button由[选择状态]--->[非选择状态]
    private void restBtn() {
        weixin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_weixin_normal, 0, 0);
        friend.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_find_frd_normal, 0, 0);
        address.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_address_normal, 0, 0);
        setting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_settings_normal, 0, 0);
    }

}
