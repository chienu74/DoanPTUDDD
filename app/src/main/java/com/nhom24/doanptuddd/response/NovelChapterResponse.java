package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.NovelChapter;

public class NovelChapterResponse {
    private boolean success;
    private NovelChapter data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public NovelChapter getData() {
        return data;
    }

    public void setData(NovelChapter data) {
        this.data = data;
    }


}
