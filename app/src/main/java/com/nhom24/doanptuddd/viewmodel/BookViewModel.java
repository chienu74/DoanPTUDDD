package com.nhom24.doanptuddd.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.repository.NovelRepository;
import java.util.List;

public class BookViewModel extends ViewModel {
    private NovelRepository repository;

    public BookViewModel() {
        repository = new NovelRepository();
    }

    public LiveData<List<Novel>> getBooks() {
        LiveData<List<Novel>> books = repository.getNovels();
        if (books == null) {
            throw new IllegalStateException("getBooks() returned null from repository");
        }
        return books;
    }
}