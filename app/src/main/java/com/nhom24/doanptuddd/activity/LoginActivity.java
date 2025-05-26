package com.nhom24.doanptuddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom24.doanptuddd.helper.SessionManager;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.service.ApiService;
import com.nhom24.doanptuddd.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private TextView txtRegister;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    private static final String BASE_URL = "https://comic.minhquancao0.workers.dev/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        txtRegister = findViewById(R.id.txt_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Kiểm tra dữ liệu nhập
                if (TextUtils.isEmpty(gmail) || TextUtils.isEmpty(gmail)) {
                    emailInput.setError("Vui lòng nhập tài khoản");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordInput.setError("Vui lòng nhập mật khẩu");
                    return;
                }

                loginButton.setEnabled(false);
                loginButton.setText(""); // Ẩn text trên nút
                loginProgressBar.setVisibility(View.VISIBLE);


                // Tạo đối tượng User
                User user = new User(null, password, gmail, null, null, null); // accountName có thể bỏ qua

                // Gọi API đăng nhập
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);
                Call<ApiResponse> call = apiService.login(user);

                // Xử lý kết quả từ API
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse apiResponse = response.body();
                            if (apiResponse.isSuccess()) {
                                // Đăng nhập thành công
                                String token = apiResponse.getToken();
                                User user = apiResponse.getData();
                                SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                sessionManager.saveToken(token);
                                sessionManager.saveUserInfo(user.getGmail(), user.getAccountName(), user.getBirthday(), user.getPhone(),user.getAvatar());
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                // Chuyển sang màn hình chính
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                                finish();
                            } else {
                                // Thất bại
                                loginButton.setEnabled(true);
                                loginButton.setText("Đăng nhập"); // Ẩn text trên nút
                                loginProgressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            loginButton.setEnabled(true);
                            loginButton.setText("Đăng nhập"); // Ẩn text trên nút
                            loginProgressBar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        loginButton.setEnabled(true);
                        loginButton.setText("Đăng nhập"); // Ẩn text trên nút
                        loginProgressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
