package com.example.myapplicationcalculator;


import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author 黄明超
 * @version 1.0
 * @date 2019-11-22
 * @des
 * @updateAuthor $
 * @updateDes
 */
public class HttpUtil {

    private static volatile HttpUtil instance;

    private HttpUtil() {
        client = new OkHttpClient();
    }

    public static HttpUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (HttpUtil.class)
            {
                if (instance == null)
                {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }
    private OkHttpClient client;

    public void sendOkHttpGetRequest(String address,okhttp3.Callback callback)
    {
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
