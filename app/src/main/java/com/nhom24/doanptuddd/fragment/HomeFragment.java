package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.NovelAdapter;
import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.repository.NovelRepository;
import com.nhom24.doanptuddd.viewmodel.BookViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView1;
    private NovelAdapter bookAdapter;
    private BookViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView1 = view.findViewById(R.id.rcv_fr_home1);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bookAdapter = new NovelAdapter(null);
        recyclerView1.setAdapter(bookAdapter);

        initDataFromApi();

        return view;
    }

    private void initDataFromApi() {
        NovelRepository novelRepository = new NovelRepository();
        LiveData<List<Novel>> books = novelRepository.getNovels();
        progressBar.setVisibility(View.VISIBLE);
        books.observe(getViewLifecycleOwner(), novelList -> {
            if (novelList != null) {
                bookAdapter.updateBooks(novelList);
                bookAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), "Không thể tải danh sách sách", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
