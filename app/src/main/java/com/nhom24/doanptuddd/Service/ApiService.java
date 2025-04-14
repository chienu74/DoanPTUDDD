package com.nhom24.doanptuddd.Service;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {
    @POST("user/register") // Đường dẫn API backend
    Call<ApiResponse> registerUser(@Body User user);

    @POST("user/login")
    Call<ApiResponse> login(@Body User user);

}