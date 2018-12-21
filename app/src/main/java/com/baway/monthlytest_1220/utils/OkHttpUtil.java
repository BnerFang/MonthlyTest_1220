package com.baway.monthlytest_1220.utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 作者:  方诗康
 * 描述: 封装  OkHttpUtil  工具类
 */
public class OkHttpUtil {
    private static volatile OkHttpUtil mInstance;
    private final OkHttpClient mClient;
    private OkHttpListener mOkHttpListener;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://成功
                    String data = (String) msg.obj;
                    mOkHttpListener.OkHttpSuccess(data);
                    break;
                case 1://失败
                    String error = (String) msg.obj;
                    mOkHttpListener.OkHttpError(error);
                    break;
            }
        }
    };

    //生成    OkHttpListener  set方法
    public void setOkHttpListener(OkHttpListener okHttpListener) {
        mOkHttpListener = okHttpListener;
    }

    /**
     * 第一步，写一个单例，这里用的懒汉式，也可以使用饿汉
     * @return
     */
    public static OkHttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (null == mInstance) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 完成构造方法，OkHttpClient
     */
    public OkHttpUtil() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //使用构造者模式
        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .writeTimeout(10, TimeUnit.SECONDS)//写超时
                .addInterceptor(interceptor)//添加拦截器
                .build();
    }

    /**
     * 异步set请求
     * @param url
     * @return
     */
    public OkHttpUtil OkHttpGet(String url){
        //获取clench对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //获取Request对象
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            /**
             * 失败回调方法
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 1;
                message.obj = e.getMessage();
                mHandler.sendMessage(message);
            }

            /**
             * 成功回调方法
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 0;
                message.obj = response.body().string();
                mHandler.sendMessage(message);
            }
        });
        return this;
    }


    /**
     * 异步Post请求
     * @param url
     * @param formBody
     * @return
     */
    public OkHttpUtil OkHttoPost(String url, FormBody formBody){
        //创建 clench 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //获取Request
        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            /**
             * 失败回调方法
             * @param call
             * @param e
             */
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 1;
                message.obj = e.getMessage();
                mHandler.sendMessage(message);
            }

            /**
             * 成功回调方法
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 0;
                message.obj = response.body().string();
                mHandler.sendMessage(message);
            }
        });
        return this;
    }

    //定义一个接口,进行回调
    public interface OkHttpListener{
        //成功
        void OkHttpSuccess(String data);
        //失败
        void OkHttpError(String error);

    }
}
