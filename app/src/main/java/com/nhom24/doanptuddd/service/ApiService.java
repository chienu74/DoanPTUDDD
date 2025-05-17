package com.nhom24.doanptuddd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.response.ChapterResponse;
import com.nhom24.doanptuddd.response.ComicDetailResponse;
import com.nhom24.doanptuddd.response.ComicResponse;
import com.nhom24.doanptuddd.response.NovelChapterResponse;
import com.nhom24.doanptuddd.response.NovelListResponse;
import com.nhom24.doanptuddd.response.NovelResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String URL = "https://comic.minhquancao0.workers.dev/api/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService comicAPIServer = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("user/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<ApiResponse> login(@Body User user);

    //
    @GET("comics")
    Call<ComicResponse> getComic();

    @GET("comics/{id}")
    Call<ComicDetailResponse> getComicDetail(@Path("id") String id);

    @GET("comics/{comicId}/chapters/{chapterId}/images")
    Call<ChapterResponse.ChapterImageResponse> getChapterImages(@Path("chapterId") String chapterId, @Path("comicId") String comicId);

    //

    @GET("novels")
    Call<NovelListResponse> getNovels();

    @GET("novels/detail/{novelId}")
    Call<NovelResponse> getNovelById(@Path("novelId") int novelId);

    @GET("novels/{novelId}/chapter/{novelChapterId}")
    Call<NovelChapterResponse> getNovelChapter(@Path("novelId") int novelId, @Path("novelChapterId") int novelChapterId);

    //
}