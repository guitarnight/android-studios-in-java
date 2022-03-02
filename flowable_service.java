package com.example.retrofit03;

import bean.BaseResponse;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WanandroidService {
    //适配器修改Retrofit接口(默认只能是Call)返回类型
    @POST("user/login")
    @FormUrlEncoded
    Flowable<BaseResponse> login(@Field("username") String username, @Field("password") String pwd);

    @GET("lg/collect/list/{pageNum}/json")
    //就拿String查看返回的文章列表吧，不然也可以自己写一个类
    Flowable<ResponseBody> getArticle(@Path("pageNum") int pageNum);
}
