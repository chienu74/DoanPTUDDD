package com.nhom24.doanptuddd.model;

import java.util.List;

public class ChapterBook {
    private int chapterId;
    private String chapterName;
    private String chapterContent;
    private int bookId;
    private Book book;
    private List<ChapterBook> chapters;

    public ChapterBook(int chapterId, String chapterName, String chapterContent, int bookId, Book book, List<ChapterBook> chapters) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.chapterContent = chapterContent;
        this.bookId = bookId;
        this.book = book;
        this.chapters = chapters;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<ChapterBook> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterBook> chapters) {
        this.chapters = chapters;
    }
}
