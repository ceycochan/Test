package com.example.administrator.test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private PullToRefreshListView mPullListView;
    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
    private LinkedList<String> mListItem;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPullListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        mListItem = new LinkedList<String>();
        //初始化适配器
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, mListItem);

        //绑定适配器 方法有2
        /**
         第一种方法：
         */
        ListView mPullListViewActual = mPullListView.getRefreshableView();
        mPullListViewActual.setAdapter(mAdapter);

        /**
         第二种方法：
         */
        //mPullListView.setAdapter(mAdapter);
        /**
         这里设置刷新方式
         1.Pull_From_End
         2.Pull_From_Start
         3.Mode.BOTH 两端刷新
         */
        //mPullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        //mPullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullListView.setMode(PullToRefreshBase.Mode.BOTH);

        mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                //Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().
                        setLastUpdatedLabel(label);
                //Do work to refresh the list here
                new GetDataTask().execute();
            }
        });


        mListItem.addAll(Arrays.asList(mStrings));
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            //simulate a background job
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Add after refresh....I add";
        }

        //这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //在头部增加新添内容
            mListItem.addFirst(s);
            //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
            mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullListView.onRefreshComplete();
        }
    }
}
