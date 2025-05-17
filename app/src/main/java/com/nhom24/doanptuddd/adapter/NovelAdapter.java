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
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.BookDetailActivity;
import com.nhom24.doanptuddd.model.Novel;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.BookViewHolder> {
    private List<Novel> bookList;

    public NovelAdapter(List<Novel> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Novel book = bookList.get(position);
        holder.textViewBookName.setText(book.getTitle());

        Glide.with(holder.imageViewBook.getContext())
                .load(book.getCover_image())
                .placeholder(R.drawable.img_logo)
                .error(R.drawable.img_logo)
                .into(holder.imageViewBook);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                intent.putExtra("book_id", book.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList != null ? bookList.size() : 0;
    }

    public void updateBooks(List<Novel> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewBook;
        TextView textViewBookName;
        CardView cardView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBook = itemView.findViewById(R.id.img_book);
            textViewBookName = itemView.findViewById(R.id.txt_book_name);
            cardView = itemView.findViewById(R.id.layout_item_book);
        }
    }
}