package com.nhom24.doanptuddd.service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhom24.doanptuddd.Response.ChapterImageResponse;
import com.nhom24.doanptuddd.Response.ComicDetailResponse;
import com.nhom24.doanptuddd.Response.ComicResponse;
import com.nhom24.doanptuddd.model.Book;
import com.nhom24.doanptuddd.model.Comic;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {
    @POST("user/register") // Đường dẫn API backend
    Call<ApiResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<ApiResponse> login(@Body User user);

    //
    String baseUrl = "https://4000-firebase-hoho-1746800926873.cluster-fdkw7vjj7bgguspe3fbbc25tra.cloudworkstations.dev/api/";
   String  URL = "https://comic.minhquancao0.workers.dev/api/";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService comicAPIServer = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    ApiService apiService = new Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //
    @GET("books")
    default Call<List<Book>> getBooks() {
        return null;
    }

    @GET("comics")
    Call<ComicResponse> getComic();

    @GET("comics/{id}")
    Call<ComicDetailResponse> getComicDetail(@Path("id") String id);

    @GET("comics/{comicId}/chapters/{chapterId}/images")
    Call<ChapterImageResponse> getChapterImages(@Path("comicId") String comicId, @Path("chapterId") String chapterId);

}