package weipiao.nshan.com.fm;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class HomeFragment extends Fragment {
    private View rootView;
    private TextView tvTabNew;
    private TextView tvTabHot;
    private ViewPager mPager;

    private ArrayList<Fragment> fragmentList;
    private Fragment home1;
    private Fragment home2;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 2;

    private Resources resources;
    private ImageView ivBottomLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            resources = getResources();
            initWidth(rootView);
            initTextView(rootView);
            initViewPager(rootView);
            TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
            tvTabHot.setTextColor(resources.getColor(R.color.silver));
            animation.setFillAfter(true);
            animation.setDuration(500);
            ivBottomLine.startAnimation(animation);
        } else {
            //防止重新加载,导致出现闪退
            if (null != rootView) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (null != parent) {
                    parent.removeView(rootView);
                }
            }
        }
        return rootView;
    }

    private void initTextView(View parentView) {
        tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
        tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);
        tvTabNew.setOnClickListener(new MyOnClickListener(0));
        tvTabHot.setOnClickListener(new MyOnClickListener(1));
    }

    private void initViewPager(View parentView) {
        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
        fragmentList = new ArrayList<Fragment>();
        home1 = new HomeFragment_1();
        home2 = new HomeFragment_2();
        fragmentList.add(home1);
        fragmentList.add(home2);
        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);
    }

    private void initWidth(View parentView) {
        ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        /**
         知识点：

         */
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        int avg = (int) (screenW / num);
        position_one = avg + offset;
    }

    public class MyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    //自定义VP监听器
    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = null;
            switch (position) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, offset, 0, 0);
                        tvTabHot.setTextColor(resources.getColor(R.color.silver));
                    }
                    tvTabNew.setTextColor(resources.getColor(R.color.white));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, position_one, 0, 0);
                        tvTabNew.setTextColor(resources.getColor(R.color.silver));
                    }
                    tvTabHot.setTextColor(resources.getColor(R.color.white));
                    break;
            }
            currIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(500);
            ivBottomLine.startAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
