//package com.example.retrofit01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;              
    private HttpbinService httpbinService;  
    public String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpbinService = retrofit.create(HttpbinService.class);
    }
    public void AsyncPost(View view){
        retrofit2.Call<ResponseBody> call = httpbinService.post("starnight531","jk110120130");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.i(TAG,"AsyncPost"+response.body().string());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void SyncPost(View view){
        new Thread(){
            public void run(){
                FormBody formBody = new FormBody.Builder()
                        .add("username","starnight531")
                        .add("passward","jk110120130")
                        .build();
                try {                    
                    Response<ResponseBody> response = httpbinService.postBody(formBody).execute();
                    Log.i(TAG,"SyncPost"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}