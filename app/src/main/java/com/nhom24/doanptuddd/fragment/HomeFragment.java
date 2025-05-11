package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.CategoryBookAdapter;
import com.nhom24.doanptuddd.adapter.FamousBookAdapter;
import com.nhom24.doanptuddd.adapter.Book;
import com.nhom24.doanptuddd.adapter.CategoryBook;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView1, recyclerView2;
    private List<Book> books;
    private List<CategoryBook> categoryBooks;
    private FamousBookAdapter bookAdapter;
    private CategoryBookAdapter categoryBookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData();

        recyclerView1 = view.findViewById(R.id.rcv_fr_home1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bookAdapter = new FamousBookAdapter(books);
        recyclerView1.setAdapter(bookAdapter);

        recyclerView2 = view.findViewById(R.id.rcv_fr_home2);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryBookAdapter = new CategoryBookAdapter(categoryBooks);
        recyclerView2.setAdapter(categoryBookAdapter);

        return view;
    }

    private void initData() {
        books = new ArrayList<>();
        books.add(new Book("Book 1", R.drawable.avatar));
        books.add(new Book("Book 2", R.drawable.avatar));
        books.add(new Book("Book 3", R.drawable.avatar));
        books.add(new Book("Book 3", R.drawable.avatar));

        categoryBooks = new ArrayList<>();
        categoryBooks.add(new CategoryBook(R.drawable.logo, "Thể loại 1"));
        categoryBooks.add(new CategoryBook(R.drawable.logo, "Thể loại 2"));
        categoryBooks.add(new CategoryBook(R.drawable.logo, "Thể loại 3"));
        categoryBooks.add(new CategoryBook(R.drawable.logo, "Thể loại 4"));
    }
}