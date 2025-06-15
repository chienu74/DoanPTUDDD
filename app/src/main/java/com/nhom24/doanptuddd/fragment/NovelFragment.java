package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.NovelAdapter;
import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.util.ArrayList;
import java.util.List;

public class NovelFragment extends Fragment {
    private NovelAdapter adapter;
    private RecyclerView recyclerView;
    private NovelRepository novelRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);

        recyclerView = view.findViewById(R.id.rcv_chapter_list2);
        novelRepository = new NovelRepository();
        adapter = new NovelAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Kiểm tra search query từ arguments
        Bundle args = getArguments();
        if (args != null) {
            String searchQuery = args.getString("search_query");
            if (searchQuery != null && !searchQuery.isEmpty()) {
                performSearch(searchQuery);
            } else {
                loadNovels();
            }
        } else {
            loadNovels();
        }

        return view;
    }

    public void performSearch(String query) {
        LiveData<List<Novel>> searchResults = novelRepository.searchNovels(query);
        searchResults.observe(getViewLifecycleOwner(), novelList -> {
            if (novelList != null && !novelList.isEmpty()) {
                adapter.updateData(novelList);
            } else {
                adapter.updateData(new ArrayList<>());
                Toast.makeText(getContext(), "Không tìm thấy kết quả cho \"" + query + "\"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNovels() {
        LiveData<List<Novel>> books = novelRepository.getNovels();
        books.observe(getViewLifecycleOwner(), novelList -> {
            if (novelList != null && !novelList.isEmpty()) {
                adapter.updateData(novelList);
            } else {
                adapter.updateData(new ArrayList<>());
                Toast.makeText(getContext(), "Không thể tải danh sách sách", Toast.LENGTH_SHORT).show();
            }
        });
    }
}