package com.nhom24.doanptuddd.model;

import java.util.List;

public class Book {
    private int bookId;
    private String bookName;
    private String bookImage;
    private String bookDescription;

    public Book(int bookId, String bookName, String bookImage, String bookDescription) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookDescription = bookDescription;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
