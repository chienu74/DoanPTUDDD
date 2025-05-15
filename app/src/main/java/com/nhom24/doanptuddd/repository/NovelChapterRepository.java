package com.nhom24.doanptuddd.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.response.ChapterResponse;
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
        service.getNovelChapter(bookId, chapterId).enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                if (response.isSuccessful()) {
                    ChapterResponse chapterResponse = response.body();
                    if (chapterResponse != null && chapterResponse.isSuccess()) {
                        data.setValue(chapterResponse.getData());
                    }
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
