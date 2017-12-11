package com.example.administrator.listview2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class Utils {
    public static String readTextFile(InputStream inputStream) {
        String readStr = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                readStr += temp;
            }
            br.close();
            inputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readStr;
    }
}
