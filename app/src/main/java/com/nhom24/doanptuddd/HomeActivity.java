package com.nhom24.doanptuddd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.adapter.BookAdapter;
import com.nhom24.doanptuddd.model.Book;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        recyclerView = findViewById(R.id.recyclerTopComics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        books = new ArrayList<>();
        books.add(new Book("One Piece", R.drawable.logo));
        books.add(new Book("Naruto", R.drawable.logo));
        books.add(new Book("Dragon Ball", R.drawable.logo));

        BookAdapter adapter = new BookAdapter(books);
        recyclerView.setAdapter(adapter);

    }
}