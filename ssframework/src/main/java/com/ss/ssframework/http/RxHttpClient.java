package com.ss.ssframework.http;

import com.ss.ssframework.http.client.GlobalRxHttp;
import com.ss.ssframework.http.client.SingleRxHttp;

/**
 * Created by 健 on 2017/7/23.
 * 网络请求
 */

public class RxHttpClient {
    private static RxHttpClient instance;

    public static RxHttpClient getGInstance() {
        if (instance == null) {
            synchronized (RxHttpClient.class) {
                if (instance == null) {
                    instance = new RxHttpClient();
                }
            }

        }
        return instance;
    }

    /**
     * 使用全局参数创建请求
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> cls) {
        return GlobalRxHttp.getInstance().createGApi(cls);
    }

    /**
     * 获取单个请求配置实例
     *
     * @return
     */
    public SingleRxHttp getSInstance() {
        return SingleRxHttp.getInstance();
    }
}
