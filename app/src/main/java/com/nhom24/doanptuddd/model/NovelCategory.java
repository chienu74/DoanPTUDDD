package com.nhom24.doanptuddd.model;

public class NovelCategory {
    private int id;
    private String categoryName;
    private String description;
    private String image;

    public NovelCategory(int id, String categoryName, String description, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
