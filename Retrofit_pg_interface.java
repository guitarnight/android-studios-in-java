//package com.example.retrofit01;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface HttpbinService {
    
    @POST("post")   
    @FormUrlEncoded 
    Call<ResponseBody> post(@Field("username") String username, @Field("passward") String pwd);      //field注解为post 添加 键值对去匹配填空

    @GET("get")
    Call<ResponseBody> get(@Query("username") String username, @Query("passward") String pwd);

    @HTTP(method = "GET", path = "get",hasBody = false)
    Call<ResponseBody> http(@QueryMap Map<String,String> map);

    @POST("post")
    Call<ResponseBody> postBody(@Body RequestBody requestBody);

    @POST("{id}")                    
    Call<ResponseBody> postInPath(@Path("id") String path, @Header("os") String os);

    @Headers("{os:android,version:25.0}")
    @POST("post")
    Call<ResponseBody> postWithHeaders();

    @POST                          
    Call<ResponseBody> postUrl(@Url String url);
}