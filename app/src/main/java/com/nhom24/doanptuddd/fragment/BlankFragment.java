package com.nhom24.doanptuddd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nhom24.doanptuddd.R;

public class BlankFragment extends Fragment {
    private TextView textViewResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        textViewResult = view.findViewById(R.id.textViewResult);
//        initDAta();
        return view;
    }

//    private void initDAta() {
//        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        Call<Book> call = apiService.getBookById(2);
//        call.enqueue(new Callback<Book>() {
//            @Override
//            public void onResponse(Call<Book> call, Response<Book> response) {
//
//                if (response.isSuccessful()) {
//                    Book book = response.body();
//                }else {
//                    String errorMessage = "Error: HTTP " + response.code();
//                    if (response.errorBody() != null) {
//                        try {
//                            errorMessage += "\n" + response.errorBody().string();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    textViewResult.setText(errorMessage);
//                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
//                    Log.e("API_ERROR", errorMessage);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Book> call, Throwable t) {
//                String errorMessage = "API call failed: " + t.getMessage();
//                Log.e("API_FAILURE", errorMessage, t);
//                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
//                textViewResult.setText(errorMessage);
//            }
//        });
//    }
}