package com.nhom24.doanptuddd;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.adapter.BookAdapter;
import com.nhom24.doanptuddd.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        recyclerView = findViewById(R.id.recyclerTopComics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        books = new ArrayList<>();
        books.add(new Book("One Piece", "https://example.com/onepiece.jpg"));
        books.add(new Book("Naruto", "https://example.com/naruto.jpg"));
        books.add(new Book("Dragon Ball", "https://example.com/dragonball.jpg"));

        bookAdapter = new BookAdapter(books);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(bookAdapter);

    }
}