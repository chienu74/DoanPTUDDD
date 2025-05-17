package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.NovelChapter;

public class ChapterResponse {
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

    public static class ChapterImageResponse {
        private boolean success;
        private Data data;

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public Data getData() { return data; }
        public void setData(Data data) { this.data = data; }

        public static class Data {
            private String imgUrl; // JSON string array

            public String getImgUrl() { return imgUrl; }
            public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
        }
    }
}