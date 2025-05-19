package com.nhom24.doanptuddd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.LoginActivity;
import com.nhom24.doanptuddd.activity.MainActivity;
import com.nhom24.doanptuddd.helper.SessionManager;

public class AccountFragment extends Fragment {
    private TextView txtUserName;
    private Button buttonLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_account,container,false);
        SessionManager sessionManager = new SessionManager(getContext());
        if (sessionManager.getToken() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        txtUserName = view.findViewById(R.id.txt_user_name);
        txtUserName.setText("Đã đăng nhập");
        buttonLogout=view.findViewById(R.id.btn_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearToken();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}