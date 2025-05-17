package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.NovelAdapter;
import com.nhom24.doanptuddd.adapter.NovelChapterAdapter;
import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {
    private RecyclerView recyclerView;
    private NovelAdapter adapter;
    private List<Novel> novelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = view.findViewById(R.id.rcv_chapter_list2);
        initDataFromApi();
        adapter=new NovelAdapter(novelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        return view;
    }

    private void initDataFromApi() {
        NovelRepository novelRepository = new NovelRepository();
        LiveData<List<Novel>> books = novelRepository.getNovels();
        books.observe(getViewLifecycleOwner(), novelList -> {
            if (novelList != null) {
                adapter.updateBooks(novelList);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Không thể tải danh sách sách", Toast.LENGTH_SHORT).show();
            }
        });

    }
}