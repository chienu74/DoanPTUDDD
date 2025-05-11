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

public class CategoryBookAdapter extends RecyclerView.Adapter<CategoryBookAdapter.CategoryBookViewHolder> {
    private List<CategoryBook> categoryBooks;

    public CategoryBookAdapter(List<CategoryBook> categoryBooks) {
        this.categoryBooks = categoryBooks;
    }

    @NonNull
    @Override
    public CategoryBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryBookViewHolder holder, int position) {
        CategoryBook categoryBook = categoryBooks.get(position);
        holder.img_book_category.setImageResource(categoryBook.getCategoryImage());
        holder.txt_book_category_name.setText(categoryBook.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return categoryBooks.size();
    }

    public class CategoryBookViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_book_category;
        private TextView txt_book_category_name;
        public CategoryBookViewHolder(@NonNull View itemView) {
            super(itemView);
            img_book_category = itemView.findViewById(R.id.img_book_category);
            txt_book_category_name = itemView.findViewById(R.id.txt_book_category_name);
        }
    }
}
