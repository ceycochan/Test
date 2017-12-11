package weipiao.nshan.com.fragmentmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments=new ArrayList<Fragment>();
        mFragments.add(new DFragment());
        mFragments.add(new AFragment());
        mFragments.add(new BFragment());
        mFragments.add(new CFragment());
        mFragments.add(new DFragment());
        mFragments.add(new AFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
