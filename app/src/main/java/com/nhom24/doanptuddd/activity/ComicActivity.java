package com.nhom24.doanptuddd.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Response.ComicResponse;
import com.nhom24.doanptuddd.adapter.ComicAdapter;
import com.nhom24.doanptuddd.model.Comic;
import com.nhom24.doanptuddd.service.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ComicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private List<Comic> comicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        comicList = new ArrayList<>();
        comicAdapter = new ComicAdapter(comicList);
        recyclerView.setAdapter(comicAdapter);

        Log.d("DEBUG", "ComicActivity started");

         fetchComics();
    }

    private void fetchComics() {
        ApiService.comicAPIServer.getComic().enqueue(new Callback<ComicResponse>() {
            @Override
            public void onResponse(Call<ComicResponse> call, Response<ComicResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Comic> comics = response.body().getData();
                    comicList.clear();
                    comicList.addAll(comics);
                    comicAdapter.notifyDataSetChanged();
                    Toast.makeText(ComicActivity.this, "Tải thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ComicActivity.this, "Lỗi dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComicResponse> call, Throwable t) {
                Toast.makeText(ComicActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("ComicActivity", "Error fetching comics", t);
            }
        });
    }


}
