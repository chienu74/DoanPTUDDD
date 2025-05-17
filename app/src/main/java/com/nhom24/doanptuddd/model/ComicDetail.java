package com.nhom24.doanptuddd.model;

import java.util.List;

public class ComicDetail {
    private String title;
    private int chapter;
    private String updatedAt;
    private String status;
    private boolean isNew;
    private String coverImage;
    private List<Chapter> chapters;
    private Info info;

    private int comicId;
    private List<Comment> comments; // Added to handle comments from JSON

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public int getComicId() {
        return comicId;
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Chapter {
        private int chapterNumber;
        private String title;
        private String updatedAt;

        // Getters and Setters
        public int getChapterNumber() {
            return chapterNumber;
        }

        public void setChapterNumber(int chapterNumber) {
            this.chapterNumber = chapterNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

    public static class Info {
        private String author;
        private int likes;
        private int followers;
        private int views;

        // Getters and Setters
        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }
    }
}