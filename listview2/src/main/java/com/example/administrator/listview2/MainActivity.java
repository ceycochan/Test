package com.example.administrator.listview2;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目简介:加载本地文件完成下拉刷新 ListView
 */
public class MainActivity extends ListActivity {

    private PullToRefreshListView mPullRefreshListView;
    //建立数据源的容器
    private ArrayList<HashMap<String, Object>> listItem;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_list);

        //ListView actualListView = mPullRefreshListView.getRefreshableView();
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                mPullRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                /**
                 异步任务执行刷新加载
                 */
                new GetDataTask().execute();
            }
        });

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        listItem = getData();//获取LIST数据
        mAdapter = new MyAdapter(this);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, HashMap<String, Object>> {


        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap<String, Object> map = new HashMap<>();
            try {
                map = new HashMap<String, Object>();
                map.put("name", "林珊");
                map.put("info", "上传了一张新照片油画");
                map.put("img", "youhua");
            } catch (Exception e) {
                setTitle("map出错了");
                return null;
            }

            return map;
        }

        //这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            //在头部增加新内容
            try {
                listItem.add(result);
                //通知程序数据集已经改变 如果不做通知，那么将不会刷新listItem集合
                mAdapter.notifyDataSetChanged();
                // Call onRefreshComplete when the list has been refreshed.
                mPullRefreshListView.onRefreshComplete();
            } catch (Exception e) {
                setTitle(e.getMessage());
            }
            super.onPostExecute(result);
        }
    }

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("my_home_friends.txt");
            String json = Utils.readTextFile(inputStream);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                map = new HashMap<String, Object>();
                map.put("name", jsonArray.getJSONObject(i).getString("name"));
                map.put("info", jsonArray.getJSONObject(i).getString("info"));
                map.put("img", jsonArray.getJSONObject(i).getString("photo"));
                mList.add(map);
            }
            return mList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }

    public final class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView info;
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * 知识点:
         * 1. 声明布局填充器 以在ConvertView为空的是填充布局
         * 2. LayoutInflater.from(context)的调用以获取填充器
         */
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listItem.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder.img = (ImageView) convertView.findViewById(R.id.iv_show);
                holder.name = (TextView) convertView.findViewById(R.id.tv_show1);
                holder.info = (TextView) convertView.findViewById(R.id.tv_show2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img.setImageBitmap(getHome((String) listItem.get(position).get("img")));
            holder.name.setText((String) listItem.get(position).get("name"));
            holder.info.setText((String) listItem.get(position).get("info"));
            return convertView;
        }

        public Bitmap getHome(String photo) {
            String fileName = photo + ".jpg";
            InputStream is = null;
            InputStream inputStream = null;
            try {
                inputStream = getAssets().open("home/" + fileName);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }
}
