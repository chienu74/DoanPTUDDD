package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Request.CommentRequest;
import com.nhom24.doanptuddd.adapter.ChapterAdapter;
import com.nhom24.doanptuddd.adapter.CommentAdapter;
import com.nhom24.doanptuddd.helper.SessionManager;
import com.nhom24.doanptuddd.model.ComicDetail;
import com.nhom24.doanptuddd.model.Comment; // Assuming you have a Comment model
import com.nhom24.doanptuddd.response.ComicDetailResponse;
import com.nhom24.doanptuddd.response.CommentResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicDetailActivity extends AppCompatActivity {
    private ImageView coverImage;
    private TextView comicTitle, comicDetails, comicStats;
    private RecyclerView chaptersRecyclerView, commentsRecyclerView;
    private Button buttonComment;
    private EditText editTextComment;
    private CommentAdapter commentAdapter; // Store adapter for updating comments

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
        editTextComment = findViewById(R.id.comment_input);
        buttonComment = findViewById(R.id.comment_button123);

        // Get comic ID from Intent
        String comicId = getIntent().getStringExtra("comic_id");
        if (comicId == null || comicId.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy comic ID", Toast.LENGTH_SHORT).show();
            finish();
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
                    ChapterAdapter chapterAdapter = new ChapterAdapter(comic.getChapters(), comicId);
                    chaptersRecyclerView.setAdapter(chapterAdapter);

                    // Comments RecyclerView
                    commentsRecyclerView.setLayoutManager(new LinearLayoutManager(ComicDetailActivity.this));
                    commentAdapter = new CommentAdapter(response.body().getComments());
                    commentsRecyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<ComicDetailResponse> call, Throwable t) {
                Toast.makeText(ComicDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("ComicActivity", "Error fetching comics", t);
            }
        });

        // Comment input handling
        buttonComment.setEnabled(false);
        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buttonComment.setEnabled(s.length() > 0); // Enable button if text is not empty
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Post comment on button click
        buttonComment.setOnClickListener(v -> {
            String commentText = editTextComment.getText().toString().trim();
            if (commentText.isEmpty()) {
                Toast.makeText(ComicDetailActivity.this, "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
                return;
            }

            // Call API to post comment
            postComment(comicId, commentText);
        });
    }

    private void postComment(String comicId, String commentText) {
        // Assuming ApiService has a method to post comments
        SessionManager sessionManager=new SessionManager(this);
        String s =sessionManager.getToken();
        String token ="Bearer "+ s;
        CommentRequest request = new CommentRequest(commentText);
        Log.d("API", "Gửi bình luận: " + request);
        Call<CommentResponse> call = ApiService.comicAPIServer.postComment(comicId, request, token);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
//                     Assuming CommentResponse returns the new comment
                    Comment newComment = response.body().getComment();
                    commentAdapter.addComment(newComment); // Update RecyclerView
                    editTextComment.setText(""); // Clear input
                    buttonComment.setEnabled(false); // Disable button
                    Toast.makeText(ComicDetailActivity.this, "Đã gửi bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ComicDetailActivity.this, "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Toast.makeText(ComicDetailActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("ComicDetailActivity", "Error posting comment", t);
            }
        });
    }
}