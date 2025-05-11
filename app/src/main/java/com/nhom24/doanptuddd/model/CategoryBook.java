package com.nhom24.doanptuddd.model;

public class CategoryBook {
    private int categoryBookId;
    private String categoryBookName;
    private String categoryBookDescription;
    private String categoryBookImage;

    public CategoryBook(int categoryBookId, String categoryBookName, String categoryBookDescription, String categoryBookImage) {
        this.categoryBookId = categoryBookId;
        this.categoryBookName = categoryBookName;
        this.categoryBookDescription = categoryBookDescription;
        this.categoryBookImage = categoryBookImage;
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

    public String getCategoryBookDescription() {
        return categoryBookDescription;
    }

    public void setCategoryBookDescription(String categoryBookDescription) {
        this.categoryBookDescription = categoryBookDescription;
    }

    public String getCategoryBookImage() {
        return categoryBookImage;
    }

    public void setCategoryBookImage(String categoryBookImage) {
        this.categoryBookImage = categoryBookImage;
    }
}
