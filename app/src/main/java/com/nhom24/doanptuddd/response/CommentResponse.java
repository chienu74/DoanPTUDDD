package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.Comment;

public class CommentResponse {
    private boolean success;
    private String message;
    private Comment comment; // The newly posted comment

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Comment getComment() {
        return comment;
    }


}