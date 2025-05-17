package com.nhom24.doanptuddd.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.response.NovelListResponse;
import com.nhom24.doanptuddd.response.NovelResponse;
import com.nhom24.doanptuddd.service.ApiService;
import com.nhom24.doanptuddd.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelRepository {
    private ApiService service;

    public NovelRepository() {
        try {
            service = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            if (service == null) {
                Log.e("BookRepository", "Failed to create ApiService");
            }
        } catch (Exception e) {
            Log.e("BookRepository", "Error initializing ApiService: " + e.getMessage());
        }
    }

    public LiveData<List<Novel>> getNovels() {
        MutableLiveData<List<Novel>> data = new MutableLiveData<>();
        if (service == null) {
            Log.e("NovelRepository", "ApiService is null, cannot fetch novels");
            data.setValue(null);
            return data;
        }

        service.getNovels().enqueue(new Callback<NovelListResponse>() {
            @Override
            public void onResponse(Call<NovelListResponse> call, Response<NovelListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NovelListResponse novelListResponse = response.body();
                    if (novelListResponse.isSuccess()) {
                        data.setValue(novelListResponse.getData());
                    } else {
                        Log.e("NovelRepository", "API returned success=false");
                        data.setValue(null);
                    }
                } else {
                    Log.e("NovelRepository", "Error: " + response.code() + " " + response.message());
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NovelListResponse> call, Throwable t) {
                Log.e("NovelRepository", "Failure: " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Novel> getNovelById(int bookId) {
        MutableLiveData<Novel> data = new MutableLiveData<>();
        if (service == null) {
            Log.e("NovelRepository", "ApiService is null, cannot fetch novel by ID");
            data.setValue(null);
            return data;
        }
        service.getNovelById(bookId).enqueue(new Callback<NovelResponse>() {
            @Override
            public void onResponse(Call<NovelResponse> call, Response<NovelResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getData());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NovelResponse> call, Throwable t) {
                Log.e("NovelRepository", "Failure: " + t.getMessage());
                data.setValue(null);
            }
        });

        return data;
    }
}