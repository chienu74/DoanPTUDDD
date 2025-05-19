package com.nhom24.doanptuddd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhom24.doanptuddd.Request.CommentRequest;
import com.nhom24.doanptuddd.model.Comment;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.response.ChapterResponse;
import com.nhom24.doanptuddd.response.ComicDetailResponse;
import com.nhom24.doanptuddd.response.ComicResponse;
import com.nhom24.doanptuddd.response.NovelChapterResponse;
import com.nhom24.doanptuddd.response.NovelListCommentResponse;
import com.nhom24.doanptuddd.response.NovelListResponse;
import com.nhom24.doanptuddd.response.NovelResponse;
import com.nhom24.doanptuddd.response.CommentResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<ChapterResponse.ChapterImageResponse> getChapterImages(@Path("comicId") String comicId, @Path("chapterId") String chapterId);

    @POST("comics/{id}/comments")
    Call<CommentResponse> postComment(
            @Path("id") String comicId,
            @Body CommentRequest request,
            @Header("Authorization") String token
    );
//
    @GET("novels")
    Call<NovelListResponse> getNovels();

    @GET("novels/detail/{novelId}")
    Call<NovelResponse> getNovelById(@Path("novelId") int novelId);

    @GET("novels/{novelId}/chapter/{novelChapterId}")
    Call<NovelChapterResponse> getNovelChapter(@Path("novelId") int novelId, @Path("novelChapterId") int novelChapterId);

    @POST("novels/{novelId}/comments")
    Call<CommentResponse> postNovelComment(
            @Path("novelId") String novelId,
            @Body CommentRequest request,
            @Header("Authorization") String token
    );

    @GET("novels/{novelId}/comments")
    Call<NovelListCommentResponse> getNovelComment(@Path("novelId") int novelId);

    //
}