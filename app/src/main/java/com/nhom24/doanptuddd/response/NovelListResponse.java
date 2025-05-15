package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.Novel;

import java.util.List;

public class NovelListResponse {
    private boolean success;
    private List<Novel> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Novel> getData() {
        return data;
    }

    public void setData(List<Novel> data) {
        this.data = data;
    }
}