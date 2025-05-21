package com.nhom24.doanptuddd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Request.CommentRequest;
import com.nhom24.doanptuddd.adapter.CommentAdapter;
import com.nhom24.doanptuddd.adapter.NovelChapterAdapter;
import com.nhom24.doanptuddd.helper.SessionManager;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.repository.NovelChapterRepository;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NovelDetailActivity extends AppCompatActivity {
    private int novelId, firstChapterId, lastChapterId;
    private ImageView imageView;
    private TextView textView, txt_author_detail, txt_time_detail, txt_description_detail;
    private Button btn_first_chapter, btn_last_chapter, btn_post_comment;
    private RecyclerView recyclerView, recyclerViewComment;
    private ProgressBar progressBar;
    private EditText edt_comment;
    private SimpleDateFormat dateFormat, stringToDate;
    private NovelChapterRepository repository;
    private Intent intent;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_detail);


        intent = new Intent(NovelDetailActivity.this, NovelChapterActivity.class);
        stringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        novelId = getIntent().getIntExtra("book_id", 1);

        repository = new NovelChapterRepository();

        initView();
        initDataNovel();
        initDataComment(novelId);

        btn_first_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book_id", novelId);
                intent.putExtra("chapter_id", firstChapterId);
                startActivity(intent);
            }
        });
        btn_last_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book_id", novelId);
                intent.putExtra("chapter_id", lastChapterId);
                startActivity(intent);
            }
        });

        SessionManager sessionManager = new SessionManager(NovelDetailActivity.this);
        if(sessionManager.getToken()==null){
            btn_post_comment.setEnabled(false);
        }
        btn_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edt_comment.getText().toString();
                if (comment.isEmpty()) {
                    return;
                }
                CommentRequest request = new CommentRequest(comment);
                String token = "Bearer " + sessionManager.getToken();
                Log.e("BookDetailActivity", "token: " + token);

                repository.postNovelComment(novelId, request, token);

                edt_comment.setText("");
            }
        });
    }

    private void initView() {
        textView = findViewById(R.id.txt_book_name_detail);
        txt_time_detail = findViewById(R.id.txt_time_detail);
        txt_author_detail = findViewById(R.id.txt_author_detail);
        txt_description_detail = findViewById(R.id.txt_description_detail);
        imageView = findViewById(R.id.img_book_detail);
        recyclerView = findViewById(R.id.rcv_chapter_list);
        recyclerViewComment = findViewById(R.id.rcv_comment);
        progressBar = findViewById(R.id.progress_bar);
        btn_first_chapter = findViewById(R.id.btn_firt_chapter);
        btn_last_chapter = findViewById(R.id.btn_last_chapter);
        edt_comment = findViewById(R.id.edt_post_comment);
        btn_post_comment = findViewById(R.id.btn_post_comment);
    }

    private void initDataNovel() {
        NovelRepository novelRepository = new NovelRepository();
        progressBar.setVisibility(View.VISIBLE);
        novelRepository.getNovelById(novelId).observe(this, novel -> {
            if (novel != null) {
                ArrayList<NovelChapter> chapterArrayList = new ArrayList<>(novel.getChapters());
                intent.putParcelableArrayListExtra("chapters", chapterArrayList);
                textView.setText(novel.getTitle());
                txt_author_detail.setText(novel.getAuthor());
                txt_description_detail.setText(novel.getDescription());
                try {
                    Date date = stringToDate.parse(novel.getCreated_at());
                    if (date != null) {
                        txt_time_detail.setText(dateFormat.format(date));
                    }
                } catch (Exception e) {
                    Log.e("BookDetailActivity", "Error parsing date: " + e.getMessage());
                }
                Glide.with(this)
                        .load(novel.getCover_image())
                        .placeholder(R.drawable.img_logo)
                        .error(R.drawable.img_logo)
                        .into(imageView);
                initDataChapter(novel.getChapters());
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void initDataComment(int id) {

        repository.getNovelComment(id).observe(this, comments -> {
            if (comments != null) {
                CommentAdapter commentAdapter = new CommentAdapter(comments.getComments());
                recyclerViewComment.setAdapter(commentAdapter);
                recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.e("BookDetailActivity", "comments: null");
            }
        });
    }

    private void initDataChapter(List<NovelChapter> chapters) {
        firstChapterId = chapters.get(0).getId();
        lastChapterId = chapters.get(chapters.size() - 1).getId();
        Log.e("BookDetailActivity", "firstChapterId: " + firstChapterId);
        Log.e("BookDetailActivity", "lastChapterId: " + lastChapterId);
        NovelChapterAdapter novelChapterAdapter = new NovelChapterAdapter(chapters, novelId);
        recyclerView.setAdapter(novelChapterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}