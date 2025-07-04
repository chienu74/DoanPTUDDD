package com.nhom24.doanptuddd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList != null ? commentList : new ArrayList<>();
    }

    // Add a new comment and notify RecyclerView
    public void addComment(Comment comment) {
        if (comment != null) {
            commentList.add(0, comment); // Add to top
            notifyItemInserted(0);
            notifyDataSetChanged(); // Ensure UI updates
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.userName.setText(comment.getUserName());
        holder.content.setText(comment.getContent());
        holder.createdAt.setText(comment.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userName, content, createdAt;

        public CommentViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            content = itemView.findViewById(R.id.comment_content);
            createdAt = itemView.findViewById(R.id.created_at);
        }
    }
}