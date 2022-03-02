package com.example.okhttp02;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CookieUnitTest {
    static  Map<String,List<Cookie>> cookies = new HashMap<>();
    /*public static void main(String[] args){
        cookieTest();
    }*/
    public void cookieTest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File("C:\\Users\\金科\\android_cache_test"),10224*1024))
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        cookies.put(httpUrl.host(),list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        List<Cookie> cookies = CookieUnitTest.this.cookies.get(httpUrl.host());
                        return cookies == null? new ArrayList<>() : cookies;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("os","android").addHeader("version","25.0").build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                }).build();

        FormBody formBody = new FormBody.Builder().add("username","starnight531").add("password","jk110120130").build();
        Request request = new Request.Builder().url("https://www.wanandroid.com/user/login").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            System.out.println(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }

        request = new Request.Builder().url("https://www.wanandroid.com/lg/collect/list/0/json").build();
        call = okHttpClient.newCall(request);
        try{
            Response response = call.execute();
            System.out.println(response.body().string());
            //System.out.println(response.body().string());         发出请求服务器给的响应可能是空，body空指针
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
