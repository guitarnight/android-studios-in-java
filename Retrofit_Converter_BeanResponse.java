implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation('com.google.code.gson:gson:2.9.0')
implementation 'com.google.android.material:material:1.3.0'
    
package com.example.retrofit02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.retrofit02.bean.BaseResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Retrofit retrofit;
    private Retrofit retrofit1;
    private WanandroidService wanandroidService;
    private WanandroidService wanandroidService1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //自动版通过加addConverterFactory(GsonConverterFactory.create())确定返回response格式
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.wanandroid.com/").build();
        wanandroidService = retrofit.create(WanandroidService.class);

        //手动版还是返回ResponseBody字符串
        retrofit1 = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/").build();
        wanandroidService1 = retrofit1.create(WanandroidService.class);
        //两者最大不同就是调用服务器注释接口时的返回值不同，一个是自带的返回转换器，一个是原样返回
        //不论手动版还是自动版，都要通过bejson创建接收json类放在隔壁bean包下
    }
    public void AutoLogin(View view) throws IOException {
        new Thread(){
            public void run(){
                Call<BaseResponse> call = wanandroidService.autoLogin("starnight531","jk110120130");
                Response<BaseResponse> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BaseResponse baseResponse = response.body();
                Log.i(TAG,"AutoLogin"+baseResponse);
            }
        }.start();
    }
    public void ManualLogin(View view) throws IOException {
        new Thread(){
            public void run(){
                Call<ResponseBody> call = wanandroidService1.manualLogin("starnight531","jk110120130");
                Response<ResponseBody> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = null;
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BaseResponse baseResponse = new Gson().fromJson(result,BaseResponse.class);
                Log.i(TAG,"ManualLogin"+baseResponse);
            }
        }.start();
    }
}
