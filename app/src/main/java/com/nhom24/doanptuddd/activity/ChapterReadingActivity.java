package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.ChapterImageAdapter;
import com.nhom24.doanptuddd.response.ChapterResponse;
import com.nhom24.doanptuddd.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.lang.reflect.Type;
import java.util.List;

public class ChapterReadingActivity extends AppCompatActivity {
    private static final String TAG = "ChapterReadingActivity";
    private RecyclerView recyclerView;
    private Button btnPrevious, btnNext;
    private ProgressBar progressBar;
    private int currentPage = 1; // Giữ thông tin trang hiện tại
    private String comicId;
    private String chapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_reading);

        // Initialize UI components
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        // Set up RecyclerView for vertical scrolling
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        // Get comic ID and chapter ID from Intent
        comicId = getIntent().getStringExtra("comic_id");
        chapterId = getIntent().getStringExtra("chapter_id");
        this.currentPage = Integer.parseInt(chapterId);

        if (chapterId == null || chapterId.isEmpty()) {
            Log.e(TAG, "chapterId is null or empty");
            Toast.makeText(this, "Không tìm thấy ID chương", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Show ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Fetch chapter images
        fetchChapterImages(comicId, chapterId);

        // Button previous
        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--; // Giảm số trang
                fetchChapterImages(comicId, getChapterIdForPage(currentPage));
            } else {
                Toast.makeText(ChapterReadingActivity.this, "Đã ở trang đầu tiên", Toast.LENGTH_SHORT).show();
            }
        });

        // Button next
        btnNext.setOnClickListener(v -> {
            currentPage++; // Tăng số trang
            fetchChapterImages(comicId, getChapterIdForPage(currentPage));
        });
    }

    // Hàm lấy chapterId dựa trên số trang
    private String getChapterIdForPage(int page) {
        return String.valueOf(page);
    }

    // Hàm để tải ảnh của chapter
    private void fetchChapterImages(String comicId, String chapterId) {
        Call<ChapterResponse.ChapterImageResponse> call = ApiService.comicAPIServer.getChapterImages(comicId, chapterId);

        call.enqueue(new Callback<ChapterResponse.ChapterImageResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse.ChapterImageResponse> call, Response<ChapterResponse.ChapterImageResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API response: " + new Gson().toJson(response.body()));
                    if (response.body().isSuccess()) {
                        ChapterResponse.ChapterImageResponse.Data data = response.body().getData();
                        if (data == null || data.getImgUrl() == null) {
                            Log.e(TAG, "Data or imgUrl is null");
                            Toast.makeText(ChapterReadingActivity.this, "Dữ liệu chương trống", Toast.LENGTH_LONG).show();
                            return; // Stay in activity
                        }

                        try {
                            // Parse imgUrl string to List<String>
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<String>>(){}.getType();
                            List<String> imageUrls = gson.fromJson(data.getImgUrl(), listType);
                            Log.d(TAG, "Parsed imageUrls: " + imageUrls);

                            if (imageUrls == null || imageUrls.isEmpty()) {
                                Log.e(TAG, "imageUrls is empty");
                                Toast.makeText(ChapterReadingActivity.this, "Không có hình ảnh cho chương này", Toast.LENGTH_LONG).show();
                                return; // Stay in activity
                            }

                            // Set up RecyclerView
                            ChapterImageAdapter adapter = new ChapterImageAdapter(imageUrls);
                            recyclerView.setAdapter(adapter);
                            Log.d(TAG, "RecyclerView adapter set with " + imageUrls.size() + " images");
                        } catch (Exception e) {
                            Log.e(TAG, "JSON parsing error: " + e.getMessage(), e);
                            Toast.makeText(ChapterReadingActivity.this, "Lỗi phân tích dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e(TAG, "API success=false");
                        Toast.makeText(ChapterReadingActivity.this, "Lỗi tải dữ liệu chương", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, "API response unsuccessful, code: " + response.code());
                    Toast.makeText(ChapterReadingActivity.this, "Lỗi tải dữ liệu: HTTP " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse.ChapterImageResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "API failure: " + t.getMessage(), t);
                Toast.makeText(ChapterReadingActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
