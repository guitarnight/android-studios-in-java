package com.example.retrofit03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.BaseResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public String TAG = "MainActivity";
    private Retrofit retrofit;
    private WanandroidService wanandroidService;
    public Map<String,List<Cookie>> cookies = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .callFactory(new OkHttpClient.Builder()
                        .cookieJar(new CookieJar() {
                            @Override
                            public void saveFromResponse(HttpUrl url, List<Cookie> list) {
                                cookies.put(url.host(),list);
                            }

                            @Override
                            public List<Cookie> loadForRequest(HttpUrl url) {
                                List<Cookie> cookies = MainActivity.this.cookies.get(url.host());
                                return cookies==null?new ArrayList<>():cookies;
                            }
                        })
                        .build())
                .addConverterFactory(GsonConverterFactory.create())       //转换器   内容接收
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //添加适配器 内容返回
                .build();
        wanandroidService = retrofit.create(WanandroidService.class);
    }
    public void AdapterPost(View view){
        new Thread(){
            public void run(){
                wanandroidService.login("starnight531","jk110120130")
                        .flatMap(new Function<BaseResponse, Publisher<ResponseBody>>() {
                            @Override
                            public Publisher<ResponseBody> apply(BaseResponse beanResponse) throws Throwable {
                                return wanandroidService.getArticle(0);
                            }
                        })         //产生新接口
                        .observeOn(Schedulers.io())//创建子进程
                        //.subscribeOn(Scheduler.newThread())
                        .subscribeOn(AndroidSchedulers.mainThread())    //主线程观察
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Throwable {
                                Log.i(TAG,responseBody.string());
                            }
                        });
            }
        }.start();
    }
}