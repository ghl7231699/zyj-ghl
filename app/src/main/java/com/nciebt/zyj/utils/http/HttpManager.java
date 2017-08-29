package com.nciebt.zyj.utils.http;

import com.nciebt.zyj.App;
import com.nciebt.zyj.common.config.SslContextFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类名称：HttpManager
 * 类描述：网络配置（okHttpClientH配置及Retrofit的初始化）
 * 创建人：ghl
 * 创建时间：2017/4/11 14:51
 * 修改人：ghl
 * 修改时间：2017/4/11 14:51
 *
 * @version v1.0
 */

public class HttpManager {
    private static OkHttpClient provideOkHttpClient() {
        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //开发记录整个body，否则只记录基本信息，如返回200，http协议版本等
        if (App.getContext().DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return new OkHttpClient.Builder().addInterceptor(logging)
                //连接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(SslContextFactory.createSSLSocketFactory(), new SslContextFactory.TrustAllManager())
                .hostnameVerifier(new SslContextFactory.TrustAllHostnameVerifier())
                .build();
    }

    public static HttpService provideHttpService(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(url)
                .build();
        return retrofit == null ? null : retrofit.create(HttpService.class);
    }

}
