/**
 * package com.自己包名
 * 依赖
 * implementation("com.squareup.okhttp3:okhttp:4.9.0")
 * 权限清单
 * <uses-permission android:name="android.permission.INTERNET"/>
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        okHttpClient = new OkHttpClient();

    }
    public void SyncGet(View view){
        new Thread(){
            public void run(){
                //向服务器发起请求
                Request request =new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
                //准备好请求的call方法
                Call call = okHttpClient.newCall(request);
                try{
                    //接收请求请求的响应
                    Response response = call.execute();
                    Log.i(TAG,"SyncGet"+response.body().string());
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void SyncPost(View view){
        new Thread(){
            public void run(){
                FormBody formBody = new FormBody.Builder().add("a","1").add("b","2").build();
                Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i(TAG,"SyncPost"+response.body().string());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void AsyncGet(View view){
        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求错误回调
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            //请求响应回调,可能返回404，200
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.i(TAG,"AsyncGet"+response.body().string());
                }
            }
        });
    }
    public void AsyncPost(View view){
        FormBody formBody = new FormBody.Builder().add("a","1").add("b","2").build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.i(TAG,"SyncPost"+response.body().string());
                }
            }
        });
    }
}