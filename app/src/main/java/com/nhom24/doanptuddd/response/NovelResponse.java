package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.Novel;


public class NovelResponse {
    private boolean success;
    private Novel data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Novel getData() {
        return data;
    }

    public void setData(Novel data) {
        this.data = data;
    }
}
