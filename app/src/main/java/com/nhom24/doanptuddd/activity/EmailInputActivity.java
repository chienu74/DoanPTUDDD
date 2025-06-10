package com.nhom24.doanptuddd.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Request.ChangPassword;
import com.nhom24.doanptuddd.Request.ForgotEmail;
import com.nhom24.doanptuddd.response.PassWordResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_input);

        TextInputEditText etEmail = findViewById(R.id.etEmail);
        MaterialButton btnSendOtp = findViewById(R.id.btnSendOtp);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = etEmail.getText().toString().trim();
                if (!gmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(gmail).matches()) {
                    // Gửi yêu cầu OTP tới server (giả lập)
                    ForgotEmail body = new ForgotEmail(gmail, null, null);
                    Call<PassWordResponse> call = ApiService.comicAPIServer.forgotEmai(body);
                    call.enqueue(new Callback<PassWordResponse>() {
                        @Override
                        public void onResponse(Call<PassWordResponse> call, Response<PassWordResponse> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                Intent intent = new Intent(EmailInputActivity.this, OtpInputActivity.class);
                                intent.putExtra("email", gmail);
                                startActivity(intent);
                                Toast.makeText(EmailInputActivity.this, "Mã OTP đã gửi vào Email của bạn", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EmailInputActivity.this, "Email không đúng vui lòng thử lại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PassWordResponse> call, Throwable t) {
                            Toast.makeText(EmailInputActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            Log.e("ComicDetailActivity", "Error posting comment", t);
                        }
                    });

                } else {
                    etEmail.setError("Vui lòng nhập email hợp lệ");
                }
            }
        });
    }
}