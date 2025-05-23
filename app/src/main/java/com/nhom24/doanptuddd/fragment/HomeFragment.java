package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.adapter.CategoryAdapter;
import com.nhom24.doanptuddd.adapter.NovelAdapter;
import com.nhom24.doanptuddd.model.Novel;
import com.nhom24.doanptuddd.model.NovelCategory;
import com.nhom24.doanptuddd.repository.NovelRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView1, recyclerView2;
    private NovelAdapter bookAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView1 = view.findViewById(R.id.rcv_fr_home1);
        recyclerView2 = view.findViewById(R.id.rcv_fr_home2);
        progressBar = view.findViewById(R.id.progressBar);

        initDataFromApi();
        initDataCategory();
        return view;
    }


    private void initDataFromApi() {
        NovelRepository novelRepository = new NovelRepository();
        LiveData<List<Novel>> books = novelRepository.getNovels();
        progressBar.setVisibility(View.VISIBLE);
        books.observe(getViewLifecycleOwner(), novelList -> {
            if (novelList != null) {
                novelList=novelList.subList(0,5);
                bookAdapter = new NovelAdapter(novelList);
                recyclerView1.setAdapter(bookAdapter);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(getContext(), "Không thể tải danh sách sách", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initDataCategory() {
        List<NovelCategory> categoryList = new ArrayList<>();
        categoryList.add(new NovelCategory(1, "Hành Động", "Hành Động","https://i.hinhhinh.com/ebook/190x247/luong-tu-ao-tuong_1747839241.jpg?gt=hdfgdfg&mobile=2"));
        categoryList.add(new NovelCategory(1, "Hành Động", "Hành Động","https://s135.hinhhinh.com//game-thu-tai-xuat-trong-luc-vo-song_1747492361.jpg?gt=hdfgdfg&mobile=2"));
        categoryList.add(new NovelCategory(1, "Hành Động", "Hành Động","https://i.hinhhinh.com/ebook/190x247/luong-tu-ao-tuong_1747839241.jpg?gt=hdfgdfg&mobile=2"));
        categoryList.add(new NovelCategory(1, "Hành Động", "Hành Động","https://s135.hinhhinh.com//game-thu-tai-xuat-trong-luc-vo-song_1747492361.jpg?gt=hdfgdfg&mobile=2"));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
        recyclerView2.setAdapter(categoryAdapter);
        recyclerView2.setLayoutManager(new GridLayoutManager(this.getContext(),2));

    }

}
