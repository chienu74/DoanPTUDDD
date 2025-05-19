package com.nhom24.doanptuddd.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nhom24.doanptuddd.Request.CommentRequest;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.response.CommentResponse;
import com.nhom24.doanptuddd.response.NovelChapterResponse;
import com.nhom24.doanptuddd.response.NovelListCommentResponse;
import com.nhom24.doanptuddd.service.ApiService;
import com.nhom24.doanptuddd.service.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovelChapterRepository {
    private final ApiService service;

    public NovelChapterRepository() {
        service = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<NovelChapter> getNovelChapter(int bookId, int chapterId) {
        MutableLiveData<NovelChapter> data = new MutableLiveData<>();
        service.getNovelChapter(bookId, chapterId).enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<NovelChapterResponse> call, @NonNull Response<NovelChapterResponse> response) {
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
            public void onFailure(@NonNull Call<NovelChapterResponse> call, @NonNull Throwable t) {
                data.setValue(null);
                Log.e("NovelChapterRepository", "Error: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<NovelListCommentResponse> getNovelComment(int novelId) {
        MutableLiveData<NovelListCommentResponse> data = new MutableLiveData<>();
        service.getNovelComment(novelId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<NovelListCommentResponse> call, @NonNull Response<NovelListCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                    Log.e("NovelChapterRepository", "Comment response unsuccessful: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NovelListCommentResponse> call, @NonNull Throwable t) {
                data.setValue(null);
                Log.e("NovelChapterRepository", "Error fetching comments: " + t.getMessage());
            }
        });
        return data;
    }

    public void postNovelComment(int novelId, CommentRequest request, String token) {
        service.postNovelComment(novelId+"", request, token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommentResponse> call, @NonNull Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("NovelChapterRepository", "Raw response: " + response.raw().body());
                    if (response.body() != null) {
                        Log.e("NovelChapterRepository", "Bình luận được đăng thành công: " + response.body().getMessage());
                    } else {
                        Log.e("NovelChapterRepository", "Response body null");
                    }
                } else {
                    Log.e("NovelChapterRepository", "Phản hồi thất bại: " + response.code() + ", message: " + response.message());
                    try {
                        Log.e("NovelChapterRepository", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentResponse> call, @NonNull Throwable t) {
                Log.e("NovelChapterRepository", "Error posting comment: " + t.getMessage());
            }
        });
    }
}