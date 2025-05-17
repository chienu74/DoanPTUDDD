package com.nhom24.doanptuddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText accountNameEditText, gmailEditText, passwordEditText, retypePasswordEditText;
    private Button registerButton;
    private ProgressBar registerProgressBar;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ các thành phần giao diện
        accountNameEditText = findViewById(R.id.accountNameEditText);
        gmailEditText = findViewById(R.id.gmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        retypePasswordEditText = findViewById(R.id.retypePasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        registerProgressBar = findViewById(R.id.registerProgressBar);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://comic.minhquancao0.workers.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Xử lý sự kiện khi nhấn nút đăng ký
        registerButton.setOnClickListener(v -> registerUser());
    }

    // Xử lý đăng ký
    private void registerUser() {
        String accountName = accountNameEditText.getText().toString().trim();
        String gmail = gmailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String retypePassword = retypePasswordEditText.getText().toString().trim();

        // Kiểm tra dữ liệu nhập
        if (accountName.isEmpty() || gmail.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(retypePassword)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (accountName.length() < 2){
            Toast.makeText(this, "Vui lòng tên phải có từ 2 ký tự trở lên", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải từ 8 ký tự trở lên", Toast.LENGTH_SHORT).show();
            return;
        }

        registerButton.setEnabled(false);
        registerButton.setText(""); // Ẩn text trên nút
        registerProgressBar.setVisibility(View.VISIBLE);
        // Gọi API đăng ký
        User user = new User(accountName, password, gmail);
        apiService.registerUser(user).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    registerButton.setEnabled(true);
                    registerButton.setText("Đăng ký");
                    registerProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại! Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                registerButton.setEnabled(true);
                registerButton.setText("Đăng ký");
                registerProgressBar.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}