package com.nhom24.doanptuddd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Request.ForgotEmail;
import com.nhom24.doanptuddd.response.PassWordResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input);

        TextInputEditText etOtp = findViewById(R.id.etOtp);
        TextInputEditText etPassword = findViewById(R.id.etPassword);
        MaterialButton btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        String gmail = getIntent().getStringExtra("email");

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etOtp.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (otp.length() == 6 && !password.isEmpty()) {
                    // Xác minh OTP và mật khẩu với server (giả lập)
                    ForgotEmail body = new ForgotEmail(gmail, otp, password);
                    Log.e("check", new Gson().toJson(body));
                    Call<PassWordResponse> call = ApiService.comicAPIServer.passwordChange(body);
                    call.enqueue(new Callback<PassWordResponse>() {
                        @Override
                        public void onResponse(Call<PassWordResponse> call, Response<PassWordResponse> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                Toast.makeText(OtpInputActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OtpInputActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(OtpInputActivity.this, "Email hoặc otp không đúng vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PassWordResponse> call, Throwable t) {
                            Toast.makeText(OtpInputActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            Log.e("ComicDetailActivity", "Error posting comment", t);
                        }
                    });

                } else {
                    if (otp.length() != 6) {
                        etOtp.setError("Mã OTP phải có 6 chữ số");
                    }
                    if (password.isEmpty()) {
                        etPassword.setError("Vui lòng nhập mật khẩu");
                    }
                }
            }
        });
    }
}