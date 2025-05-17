package com.nhom24.doanptuddd.Response;

import com.nhom24.doanptuddd.model.ComicDetail;
import com.nhom24.doanptuddd.model.Comment;
import java.util.List;

public class ComicDetailResponse {
    private boolean success;
    private ComicDetail data;
    private List<Comment> comments;

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public ComicDetail getData() { return data; }
    public void setData(ComicDetail data) { this.data = data; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}