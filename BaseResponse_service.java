package com.example.retrofit02;

import com.example.retrofit02.bean.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface WanandroidService {
    @POST("user/login")
    @FormUrlEncoded
    //responsebody只能返回字符串，BaseResponse返回json
    Call<BaseResponse> autoLogin(@Field("username") String username, @Field("passward") String pwd);

    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> manualLogin(@Field("username") String username, @Field("passward") String pwd);
    /*
    @HTTP(method = "POST",path = "{id}")
    Call<ResponseBody> httpPost(@Path("id") String path, @Body ResponseBody responseBody);

    @HTTP(method = "GET",path = "{id}")
    Call<ResponseBody> httpGet(@Path("id") String path, @Query("username") String name,@Query("password") String pwd);
    */
}
