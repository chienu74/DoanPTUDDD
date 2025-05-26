package com.nhom24.doanptuddd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.activity.ChangePaswordActivity;
import com.nhom24.doanptuddd.activity.EditProfileActivity;
import com.nhom24.doanptuddd.activity.LoginActivity;
import com.nhom24.doanptuddd.activity.MainActivity;
import com.nhom24.doanptuddd.helper.SessionManager;

public class AccountFragment extends Fragment {
    private TextView txtUserName, textProfile, passwordEditText;
    private Button buttonLogout;
    private ImageView imageView;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        sessionManager = new SessionManager(getContext());

        // Nếu không có token thì chuyển về LoginActivity
        if (sessionManager.getToken() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }

        // Lấy thông tin user
        String accountName = sessionManager.getAccountName();
        String avatar = sessionManager.getAvatar();


        // Gán dữ liệu lên view
        txtUserName = view.findViewById(R.id.txt_user_name);
        txtUserName.setText(accountName != null ? accountName : "Người dùng");

        textProfile = view.findViewById(R.id.profile_edit);
        textProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePaswordActivity.class);
                startActivity(intent);
            }
        });

        imageView = view.findViewById(R.id.accountImage);
        if (avatar != null) {
            Glide.with(this)
                    .load(avatar)
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_avatar);
        }

        // Xử lý đăng xuất
        buttonLogout = view.findViewById(R.id.btn_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearToken();
                sessionManager.clearUserInfo(); // Xoá toàn bộ thông tin
                redirectToLogin(); // Về lại màn Login và xoá back stack
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Nếu không có token khi quay lại Fragment, chuyển hướng luôn
        if (sessionManager.getToken() == null) {
            redirectToLogin();
        }
    }

    private void redirectToLogin() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish(); // Kết thúc Activity hiện tại (MainActivity)
    }
}
