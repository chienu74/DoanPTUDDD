package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.Request.ChangPassword;
import com.nhom24.doanptuddd.Request.CommentRequest;
import com.nhom24.doanptuddd.helper.SessionManager;
import com.nhom24.doanptuddd.model.Comment;
import com.nhom24.doanptuddd.response.CommentResponse;
import com.nhom24.doanptuddd.response.PassWordResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePaswordActivity extends AppCompatActivity {

    private EditText oldPassword, newPassword, fillPassword;
    private Button changPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        fillPassword = findViewById(R.id.fillPassword);
        changPassword = findViewById(R.id.changPassword);

        changPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassword.getText().toString().trim();
                String fillPass = fillPassword.getText().toString().trim();
                String oldPass = oldPassword.getText().toString().trim();

                if (oldPass.isEmpty() || newPass.isEmpty() || fillPass.isEmpty()) {
                    Toast.makeText(ChangePaswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!newPass.equals(fillPass)) {
                    Toast.makeText(ChangePaswordActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                }
                if (newPass.equals(fillPass)) {
                    SessionManager sessionManager=new SessionManager(ChangePaswordActivity.this);
                    String s =sessionManager.getToken();
                    String token ="Bearer "+ s;
                    ChangPassword password = new ChangPassword( oldPass ,newPass);
                    Call<PassWordResponse> call = ApiService.comicAPIServer.changPassword(password, token);
                    call.enqueue(new Callback<PassWordResponse>() {
                        @Override
                        public void onResponse(Call<PassWordResponse> call, Response<PassWordResponse> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                                oldPassword.setText("");
                                newPassword.setText("");
                                fillPassword.setText("");
                                Toast.makeText(ChangePaswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePaswordActivity.this,  "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PassWordResponse> call, Throwable t) {
                            Toast.makeText(ChangePaswordActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            Log.e("ComicDetailActivity", "Error posting comment", t);
                        }
                    });
                    // Mật khẩu trùng nhau
                } else {
                    Toast.makeText(ChangePaswordActivity.this, "Mật khẩu mới và điện lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
