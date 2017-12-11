package com.example.administrator.listpack2;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本Demo总结点:
 *
 * 1.本地Assets的建立
 * 2.本地Assets文件的访问
 * 3.ViewHolder的理解 |
 * 4.input-stream 转换 json 的工具类 整理
 * 5.ListActivity的应用
 */


public class MainActivity extends ListActivity {
    private List<Map<String, Object>> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> mList = new ArrayList<>();
        Map<String, Object> map = null;
        //new HashMap<String, Object>();
        InputStream is;
        try {
            is = getAssets().open("my_home_friends.txt");
            String json = Utils.readTextFile(is);
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                map = new HashMap<String, Object>();
                map.put("name", array.getJSONObject(i).getString("name"));
                map.put("info", array.getJSONObject(i).getString("info"));
                map.put("img", array.getJSONObject(i).getString("photo"));
                mList.add(map);
            }
            return mList;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
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
                convertView = mInflater.inflate(R.layout.activity_main, null);
                //实现将XML元素与viewHolder绑定
                holder.img = (ImageView) convertView.findViewById(R.id.iv_show);
                holder.name = (TextView) convertView.findViewById(R.id.tv_show1);
                holder.info = (TextView) convertView.findViewById(R.id.tv_show2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.img.setImageBitmap(getHome((String) mData.get(position).get("img")));
            holder.name.setText((String) mData.get(position).get("name"));
            holder.info.setText((String) mData.get(position).get("info"));
            return convertView;
        }

        public class ViewHolder {
            public ImageView img;
            public TextView name;
            public TextView info;
        }

        public Bitmap getHome(String photo) {
            String homeName = photo + ".jpg";
            InputStream is = null;
            try {
                is = getAssets().open("home/" + homeName);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
