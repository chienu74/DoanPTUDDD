package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.Comment;

import java.util.List;

public class NovelListCommentResponse {
    private boolean success;
    private String message;
    private List<Comment> comment;

    public NovelListCommentResponse(boolean success, String message, List<Comment> comments) {
        this.success = success;
        this.message = message;
        this.comment = comments;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getComments() {
        return comment;
    }

    public void setComments(List<Comment> comments) {
        this.comment = comments;
    }
}
