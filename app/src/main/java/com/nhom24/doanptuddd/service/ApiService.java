package com.nhom24.doanptuddd.service;

import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.response.ChapterResponse;
import com.nhom24.doanptuddd.response.NovelListResponse;
import com.nhom24.doanptuddd.response.NovelResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {
    @POST("user/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<ApiResponse> login(@Body User user);

    @GET("novels")
    Call<NovelListResponse> getNovels();

    @GET("novels/detail/{id}")
    Call<NovelResponse> getNovelById(@Path("id") int id);

    @GET("novels/{novelId}/chapter/{id}")
    Call<ChapterResponse> getNovelChapter(@Path("novelId") int novelId, @Path("id") int chapterId);
}