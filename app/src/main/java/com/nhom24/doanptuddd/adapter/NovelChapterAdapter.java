package com.nhom24.doanptuddd.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.ChapterActivity;
import com.nhom24.doanptuddd.model.NovelChapter;

import java.util.List;

public class NovelChapterAdapter extends RecyclerView.Adapter<NovelChapterAdapter.ChapterViewHolder> {
    private List<NovelChapter> chapters;
    private int chapterId;

    public NovelChapterAdapter(List<NovelChapter> chapters, int chapterId) {
        this.chapters = chapters;
        this.chapterId = chapterId;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_chapter, null);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        NovelChapter chapter = chapters.get(position);
//        holder.textView.setText(chapter.getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChapterActivity.class);
//                intent.putExtra("chapter_id", chapter.getId());
                intent.putExtra("chapter_id", 1);
                intent.putExtra("book_id", chapterId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }


    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;
        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_chapter_item_chapter);
            linearLayout = itemView.findViewById(R.id.layout_item_chapter);
        }
    }
}
