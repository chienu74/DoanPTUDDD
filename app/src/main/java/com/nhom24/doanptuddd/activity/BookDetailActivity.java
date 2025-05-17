package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.NovelChapterAdapter;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView, txt_author_detail, txt_time_detail;
    private int bookId, chapterId;
    private RecyclerView recyclerView;
    private NovelChapterAdapter novelChapterAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        txt_author_detail = findViewById(R.id.txt_author_detail);
        txt_time_detail = findViewById(R.id.txt_time_detail);
        imageView = findViewById(R.id.img_book_detail);
        textView = findViewById(R.id.txt_book_name_detail);
        recyclerView = findViewById(R.id.rcv_chapter_list);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        bookId = getIntent().getIntExtra("book_id", 1);
        chapterId = getIntent().getIntExtra("chapter_id", 1);

        initDataApi();
        Log.d("BookDetailActivity", "bookId: " + bookId);
    }

    private void initDataApi() {
        NovelRepository novelRepository = new NovelRepository();
        novelRepository.getNovelById(bookId).observe(this, novel -> {
            if (novel != null) {
                textView.setText(novel.getTitle());
                txt_author_detail.setText(novel.getAuthor());
                Glide.with(this)
                        .load(novel.getCover_image())
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(imageView);
                initDataChapter(novel.getChapters());
            }
            progressBar.setVisibility(View.GONE);

        });
    }

    private void initDataChapter(List<NovelChapter> chapters) {
        novelChapterAdapter = new NovelChapterAdapter(chapters, bookId);
        recyclerView.setAdapter(novelChapterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}