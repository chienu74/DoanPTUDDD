package com.nhom24.doanptuddd.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.fragment.NovelFragment;
import com.nhom24.doanptuddd.model.NovelCategory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<NovelCategory> categoryList;

    public CategoryAdapter(List<NovelCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        NovelCategory category = categoryList.get(position);
        holder.txtCategoryName.setText(category.getCategoryName());
        Glide.with(holder.imgCategory.getContext())
                .load(category.getImage())
                .placeholder(R.drawable.img_logo)
                .error(R.drawable.img_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgCategory);
        holder.layoutItemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NovelFragment novelFragment = new NovelFragment();
                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, novelFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        ImageView imgCategory;
        RelativeLayout layoutItemCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txt_book_category_name);
            imgCategory = itemView.findViewById(R.id.img_book_category);
            layoutItemCategory = itemView.findViewById(R.id.layout_item_category);
        }
    }
}
