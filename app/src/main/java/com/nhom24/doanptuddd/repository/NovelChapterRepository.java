package com.nhom24.doanptuddd.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.response.NovelChapterResponse;
import com.nhom24.doanptuddd.service.ApiService;
import com.nhom24.doanptuddd.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelChapterRepository {
    private ApiService service;

    public NovelChapterRepository() {
        service = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<NovelChapter> getNovelChapter(int bookId, int chapterId) {
        MutableLiveData<NovelChapter> data = new MutableLiveData<>();
        service.getNovelChapter(bookId, chapterId).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<NovelChapterResponse> call, Response<NovelChapterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NovelChapter chapter = response.body().getData();
                    if (chapter != null) {
                        data.setValue(chapter);
                    } else {
                        data.setValue(null);
                        Log.e("NovelChapterRepository", "Chapter data is null");
                    }
                } else {
                    data.setValue(null);
                    Log.e("NovelChapterRepository", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<NovelChapterResponse> call, Throwable t) {
                data.setValue(null);
                Log.e("NovelChapterRepository", "Error: " + t.getMessage());
            }
        });
        return data;
    }
}