package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.response.ComicResponse;
import com.nhom24.doanptuddd.adapter.ComicAdapter;
import com.nhom24.doanptuddd.model.Comic;
import com.nhom24.doanptuddd.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicFragment extends Fragment {
    private RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private List<Comic> comicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        comicList = new ArrayList<>();
        comicAdapter = new ComicAdapter(comicList);
        recyclerView.setAdapter(comicAdapter);

        Log.d("DEBUG", "ComicActivity started");

        fetchComics();
        return view;
    }

    private void fetchComics() {
        ApiService.comicAPIServer.getComic().enqueue(new Callback<ComicResponse>() {
            @Override
            public void onResponse(Call<ComicResponse> call, Response<ComicResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Comic> comics = response.body().getData();
                    comicList.clear();
                    comicList.addAll(comics);
                    comicAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Tải thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Lỗi dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComicResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("ComicActivity", "Error fetching comics", t);
            }
        });
    }
}
