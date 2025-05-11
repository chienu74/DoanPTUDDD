package com.nhom24.doanptuddd.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;

import java.util.List;

public class FamousBookAdapter extends RecyclerView.Adapter<FamousBookAdapter.BookViewHolder> {
    private List<Book> books;

    public FamousBookAdapter(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.txt_book_name.setText(book.getTitle());
        holder.img_book.setImageResource(book.getImageResId());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_book_name;
        private ImageView img_book;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_book_name = itemView.findViewById(R.id.txt_book_name);
            img_book = itemView.findViewById(R.id.img_book);
        }
    }
}