package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Response.ComicDetailResponse;
import com.nhom24.doanptuddd.adapter.ChapterAdapter;
import com.nhom24.doanptuddd.adapter.CommentAdapter;
import com.nhom24.doanptuddd.model.ComicDetail;
import com.nhom24.doanptuddd.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailActivity extends AppCompatActivity {
    private ImageView coverImage;
    private TextView comicTitle, comicDetails, comicStats;
    private RecyclerView chaptersRecyclerView, commentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        // Initialize UI
        coverImage = findViewById(R.id.cover_image);
        comicTitle = findViewById(R.id.comic_title);
        comicDetails = findViewById(R.id.comic_details);
        comicStats = findViewById(R.id.comic_stats);
        chaptersRecyclerView = findViewById(R.id.chapters_recycler_view);
        commentsRecyclerView = findViewById(R.id.comments_recycler_view);

        // Get comic ID from Intent
        String comicId = getIntent().getStringExtra("comic_id");
        if (comicId == null || comicId.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy comic ID", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu thiếu ID
            return;
        }

        // Fetch comic details
        ApiService.comicAPIServer.getComicDetail(comicId).enqueue(new Callback<ComicDetailResponse>() {
            @Override
            public void onResponse(Call<ComicDetailResponse> call, Response<ComicDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    ComicDetail comic = response.body().getData();
                    // Populate UI
                    comicTitle.setText(comic.getTitle());
                    comicDetails.setText(String.format("Chapter: %d\nStatus: %s\nAuthor: %s",
                            comic.getChapter(), comic.getStatus(), comic.getInfo().getAuthor()));
                    comicStats.setText(String.format("Likes: %d | Followers: %d | Views: %d",
                            comic.getInfo().getLikes(), comic.getInfo().getFollowers(), comic.getInfo().getViews()));
                    Glide.with(ComicDetailActivity.this)
                            .load(comic.getCoverImage())
                            .into(coverImage);

                    // Chapters RecyclerView
                    chaptersRecyclerView.setLayoutManager(new LinearLayoutManager(ComicDetailActivity.this));
                    ChapterAdapter chapterAdapter = new ChapterAdapter(comic.getChapters(), String.valueOf(comic.getComicId()));
                    chaptersRecyclerView.setAdapter(chapterAdapter);

                    // Comments RecyclerView
                    commentsRecyclerView.setLayoutManager(new LinearLayoutManager(ComicDetailActivity.this));
                    CommentAdapter commentAdapter = new CommentAdapter(response.body().getComments());
                    commentsRecyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<ComicDetailResponse> call, Throwable t) {
                Toast.makeText(ComicDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("ComicActivity", "Error fetching comics", t);
            }
        });
    }
}