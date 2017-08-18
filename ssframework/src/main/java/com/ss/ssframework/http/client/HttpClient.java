package com.ss.ssframework.http.client;

import okhttp3.OkHttpClient;

/**
 * Created by 健 on 2017/7/23.
 * okHttp client
 */

public class HttpClient {

    private static HttpClient instance;

    private OkHttpClient.Builder builder;

    public HttpClient() {
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance() {

        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }

        }
        return instance;
    }


    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

}
