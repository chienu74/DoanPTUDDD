package com.nhom24.doanptuddd.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.ChapterReadingActivity;
import com.nhom24.doanptuddd.model.ComicDetail.Chapter;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private final List<Chapter> chapterList;
    private final String comicId; // Added to pass to ChapterReadingActivity

    public ChapterAdapter(List<Chapter> chapterList, String comicId) {
        this.chapterList = chapterList;
        this.comicId = comicId;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.chapterTitle.setText(chapter.getTitle() != null ? chapter.getTitle() : "N/A");
        holder.chapterNumber.setText("Chapter " + chapter.getChapterNumber());
        holder.updatedAt.setText(chapter.getUpdatedAt() != null ? chapter.getUpdatedAt() : "N/A");

        // Navigate to ChapterReadingActivity on click
        holder.itemView.setOnClickListener(v -> {
            Log.e("TAG", "ComicId: " + comicId );
            Intent intent = new Intent(v.getContext(), ChapterReadingActivity.class);
            intent.putExtra("comic_id", String.valueOf(comicId));
            intent.putExtra("chapter_id", String.valueOf(chapter.getChapterNumber()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chapterList != null ? chapterList.size() : 0;
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView chapterTitle, chapterNumber, updatedAt;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapter_title);
            chapterNumber = itemView.findViewById(R.id.chapter_number);
            updatedAt = itemView.findViewById(R.id.updated_at);
        }
    }
}