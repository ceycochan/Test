package com.example.administrator.eventbus.event;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class FirstEvent {
    private String mStr;

    public FirstEvent(String mStr) {
        this.mStr = mStr;
    }

    public String getmStr() {
        return mStr;
    }
}
