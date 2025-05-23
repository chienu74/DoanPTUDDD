package com.nhom24.doanptuddd.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.ComicDetailActivity;
import com.nhom24.doanptuddd.model.Comic;
import com.nhom24.doanptuddd.model.Comic;


import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private List<Comic> comicList;

    public ComicAdapter(List<Comic> comicList) {
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
        return  new ComicAdapter.ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        holder.txtBookName.setText(comic.getTitle());
        holder.tvChapter.setText(comic.getChapterString());
        holder.tvTime.setText(comic.getUpdatedAt());
        Glide.with(holder.itemView.getContext())
                .load(comic.getCoverImage())
                .into(holder.imgBook);

        holder.tvNewLabel.setVisibility(comic.isNewBoolean() ? View.VISIBLE : View.GONE);
        holder.tvFullLabel.setVisibility(comic.isFull() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ComicDetailActivity.class);
            intent.putExtra("comic_id", String.valueOf(comic.getId()));
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView txtBookName;
        TextView tvChapter;
        TextView tvTime;
        TextView tvNewLabel;
        TextView tvFullLabel;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.img_book);
            txtBookName = itemView.findViewById(R.id.txt_book_name);
            tvChapter = itemView.findViewById(R.id.tv_chapter);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvNewLabel = itemView.findViewById(R.id.tv_new_label);
            tvFullLabel = itemView.findViewById(R.id.tv_full_label);
        }
    }
}