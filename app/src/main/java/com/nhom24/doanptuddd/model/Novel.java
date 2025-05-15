package com.nhom24.doanptuddd.model;


import java.util.List;

public class Novel {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String cover_image;
    private String created_at;
    private String updated_at;
    private List<NovelChapter> chapters;

    public Novel(int id, String title, String author, String genre, String description, String cover_image, String created_at, String updated_at, List<NovelChapter> chapters) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
        this.cover_image = cover_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.chapters = chapters;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<NovelChapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<NovelChapter> chapters) {
        this.chapters = chapters;
    }
}