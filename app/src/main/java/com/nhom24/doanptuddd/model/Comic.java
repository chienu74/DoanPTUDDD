package com.nhom24.doanptuddd.model;

public class Comic {
    private int id;
    private String title;
    private int chapter;
    private String updatedAt;
    private String status;
    private int isNew;
    private String coverImage;


    public Comic(int id, String title, int chapter, String updatedAt, String status, int isNew, String coverImage) {
        this.id = id;
        this.title = title;
        this.chapter = chapter;
        this.updatedAt = updatedAt;
        this.status = status;
        this.isNew = isNew;
        this.coverImage = coverImage;
    }
//
    public String getChapterString() { return "Chap " + chapter; }
    public boolean isFull() { return "Full".equalsIgnoreCase(status); }
    public boolean isNewBoolean() { return isNew == 1; }
//
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}