package com.nhom24.doanptuddd.model;

public class CategoryBook {
    private int categoryBookId;
    private String categoryBookName;
    private String description;
    private String bookCategoryImage;

    public CategoryBook(int categoryBookId, String categoryBookName, String categoryBookDescription, String categoryBookImage) {
        this.categoryBookId = categoryBookId;
        this.categoryBookName = categoryBookName;
        this.description = categoryBookDescription;
        this.bookCategoryImage = categoryBookImage;
    }

    public int getCategoryBookId() {
        return categoryBookId;
    }

    public void setCategoryBookId(int categoryBookId) {
        this.categoryBookId = categoryBookId;
    }

    public String getCategoryBookName() {
        return categoryBookName;
    }

    public void setCategoryBookName(String categoryBookName) {
        this.categoryBookName = categoryBookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookCategoryImage() {
        return bookCategoryImage;
    }

    public void setBookCategoryImage(String bookCategoryImage) {
        this.bookCategoryImage = bookCategoryImage;
    }
}
