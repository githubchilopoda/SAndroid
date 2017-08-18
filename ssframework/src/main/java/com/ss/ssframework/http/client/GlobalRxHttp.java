package com.ss.ssframework.http.client;

import android.util.Log;

import com.ss.ssframework.http.config.GlobalRxHttpConfig;
import com.ss.ssframework.http.interceptor.HeaderInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 健 on 2017/7/23.
 * 网络请求工具类---使用的是全局配置的变量
 */

public class GlobalRxHttp {

    private static GlobalRxHttp instance;

    public GlobalRxHttp() {
        initGlobalRxHttpConfig();
    }

    public static GlobalRxHttp getInstance() {

        if (instance == null) {
            synchronized (GlobalRxHttp.class) {
                if (instance == null) {
                    instance = new GlobalRxHttp();
                }
            }

        }
        return instance;
    }

    /**
     * 初始化全局配置参数
     * */
    private void initGlobalRxHttpConfig(){
        setBaseUrl(GlobalRxHttpConfig.BASE_URL);
        setReadTimeout(15);
        setWriteTimeout(15);
        setConnectTimeout(15);
        setLog(true);
    }
    /**
     * 设置baseUrl
     *
     * @param baseUrl
     * @return
     */
    private GlobalRxHttp setBaseUrl(String baseUrl) {
        getGlobalRetrofitBuilder().baseUrl(baseUrl);
        return this;
    }

    /**
     * 添加统一的请求头
     *
     * @param headerMaps
     * @return
     */
    private GlobalRxHttp setHeaders(Map<String, Object> headerMaps) {
        getGlobalOkHttpBuilder().addInterceptor(new HeaderInterceptor(headerMaps));
        return this;
    }

    /**
     * 是否开启请求日志
     *
     * @param isShowLog
     * @return
     */
    private GlobalRxHttp setLog(boolean isShowLog) {
        if (isShowLog) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("RxHttpClient", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            getGlobalOkHttpBuilder().addInterceptor(loggingInterceptor);
        }
        return this;
    }

    /**
     * 设置读取超时时间
     *
     * @param second
     * @return
     */
    private GlobalRxHttp setReadTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置写入超时时间
     *
     * @param second
     * @return
     */
    private GlobalRxHttp setWriteTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 设置连接超时时间
     *
     * @param second
     * @return
     */
    private GlobalRxHttp setConnectTimeout(long second) {
        getGlobalOkHttpBuilder().readTimeout(second, TimeUnit.SECONDS);
        return this;
    }

    /**
     * 全局的 RetrofitBuilder
     *
     * @return
     */
    private Retrofit.Builder getGlobalRetrofitBuilder() {
        return RetrofitClient.getInstance().getRetrofitBuilder();
    }

    private OkHttpClient.Builder getGlobalOkHttpBuilder() {
        return HttpClient.getInstance().getBuilder();
    }

    /**
     * 使用全局变量的请求
     *
     * @param cls
     * @param <K>
     * @return
     */
    public <K> K createGApi(final Class<K> cls) {
        return RetrofitClient.getInstance().getRetrofit().create(cls);
    }
}
