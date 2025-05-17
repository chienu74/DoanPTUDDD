package com.nhom24.doanptuddd.service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhom24.doanptuddd.model.Book;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    @POST("user/register") // Đường dẫn API backend
    Call<ApiResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<ApiResponse> login(@Body User user);
    //
    String baseUrl = "https://4000-firebase-hoho-1746800926873.cluster-fdkw7vjj7bgguspe3fbbc25tra.cloudworkstations.dev/api/";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    //
    @GET("books")
    Call<List<Book>> getBooks();



}