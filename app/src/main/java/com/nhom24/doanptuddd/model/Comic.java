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

    public int getId() { return id; }

    public String getTitle() { return title; }
    public int getChapter() { return chapter; }
    public String getUpdatedAt() { return updatedAt; }
    public String getStatus() { return status; }
    public int getIsNew() { return isNew; }
    public String getCoverImage() { return coverImage; }

    public String getChapterString() { return "Chap " + chapter; }
    public boolean isFull() { return "Full".equalsIgnoreCase(status); }
    public boolean isNewBoolean() { return isNew == 1; }
}