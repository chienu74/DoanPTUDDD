package com.nhom24.doanptuddd.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.NovelDetailActivity;
import com.nhom24.doanptuddd.model.Novel;

import java.util.ArrayList;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelViewHolder> {
    private List<Novel> bookList;

    public NovelAdapter(List<Novel> bookList) {
        this.bookList = bookList != null ? bookList : new ArrayList<>();
    }

    // Phương thức cập nhật dữ liệu
    public void updateData(List<Novel> newBookList) {
        this.bookList.clear();
        this.bookList.addAll(newBookList != null ? newBookList : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NovelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic, parent, false);
        return new NovelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelViewHolder holder, int position) {
        Novel book = bookList.get(position);
        holder.textViewBookName.setText(book.getTitle() != null ? book.getTitle() : "Không có tiêu đề");

        Glide.with(holder.imageViewBook.getContext())
                .load(book.getCover_image())
                .placeholder(R.drawable.img_logo)
                .error(R.drawable.img_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewBook);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NovelDetailActivity.class);
            intent.putExtra("book_id", book.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class NovelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBook;
        TextView textViewBookName;
        CardView cardView;

        public NovelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBook = itemView.findViewById(R.id.img_book);
            textViewBookName = itemView.findViewById(R.id.txt_book_name);
            cardView = itemView.findViewById(R.id.layout_item_book);
        }
    }
}