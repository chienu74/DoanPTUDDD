package com.nhom24.doanptuddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.NovelChapterAdapter;
import com.nhom24.doanptuddd.model.NovelChapter;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NovelDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView, txt_author_detail, txt_time_detail, txt_description_detail;
    private Button btn_first_chapter, btn_last_chapter;
    private int bookId, chapterId, firtChapterId, lastChapterId;
    private RecyclerView recyclerView;
    private NovelChapterAdapter novelChapterAdapter;
    private ProgressBar progressBar;
    private SimpleDateFormat dateFormat, stringToDate;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        txt_author_detail = findViewById(R.id.txt_author_detail);
        txt_time_detail = findViewById(R.id.txt_time_detail);
        imageView = findViewById(R.id.img_book_detail);
        textView = findViewById(R.id.txt_book_name_detail);
        txt_description_detail = findViewById(R.id.txt_description_detail);
        recyclerView = findViewById(R.id.rcv_chapter_list);
        progressBar = findViewById(R.id.progress_bar);
        btn_first_chapter = findViewById(R.id.btn_firt_chapter);
        btn_last_chapter = findViewById(R.id.btn_last_chapter);

        intent = new Intent(NovelDetailActivity.this, NovelChapterActivity.class);
        stringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        progressBar.setVisibility(View.VISIBLE);
        bookId = getIntent().getIntExtra("book_id", 1);

        initDataApi();


        btn_first_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book_id", bookId);
                intent.putExtra("chapter_id", firtChapterId);
                startActivity(intent);
            }
        });
        btn_last_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("book_id", bookId);
                intent.putExtra("chapter_id", lastChapterId);
                startActivity(intent);
            }
        });

    }

    private void initDataApi() {
        NovelRepository novelRepository = new NovelRepository();
        novelRepository.getNovelById(bookId).observe(this, novel -> {
            if (novel != null) {
                ArrayList<NovelChapter> chapterArrayList = new ArrayList<>(novel.getChapters());
                intent.putParcelableArrayListExtra("chapters", chapterArrayList);
                textView.setText(novel.getTitle());
                txt_author_detail.setText(novel.getAuthor());
                txt_description_detail.setText(novel.getDescription());
                try {
                    Date date = stringToDate.parse(novel.getCreated_at());
                    txt_time_detail.setText(dateFormat.format(date));
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

    private void initDataChapter(List<NovelChapter> chapters) {
        firtChapterId = chapters.get(0).getId();
        lastChapterId = chapters.get(chapters.size() - 1).getId();
        Log.e("BookDetailActivity", "firtChapterId: " + firtChapterId);
        Log.e("BookDetailActivity", "lastChapterId: " + lastChapterId);
        novelChapterAdapter = new NovelChapterAdapter(chapters, bookId);
        recyclerView.setAdapter(novelChapterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}