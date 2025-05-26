package com.nhom24.doanptuddd.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nhom24.doanptuddd.R;
import com.nhom24.doanptuddd.helper.SessionManager;
import com.nhom24.doanptuddd.model.User;
import com.nhom24.doanptuddd.response.ApiResponse;
import com.nhom24.doanptuddd.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    EditText fullNameEditText, phoneEditText, birthDateEditText;
    Button continueButton;

    ImageView editProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprofile);

        sessionManager = new SessionManager(this);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        continueButton = findViewById(R.id.continueButton);
        editProfile = findViewById(R.id.editProfile);

        String accountName = sessionManager.getAccountName();
        String phone = sessionManager.getPhone();
        String birthday = sessionManager.getBirthday();
        String avatar = sessionManager.getAvatar();
        fullNameEditText.setText(accountName != null ? accountName : "");
        phoneEditText.setText(phone != null ? phone : "");
        birthDateEditText.setText(birthday != null ? birthday : "");

        if (avatar != null) {
            Glide.with(this)
                    .load(avatar)
                    .placeholder(R.drawable.img_avatar)
                    .error(R.drawable.img_avatar)
                    .into(editProfile);
        } else {
            editProfile.setImageResource(R.drawable.img_avatar);
        }

        Log.e("Phone", sessionManager.getPhone());

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = fullNameEditText.getText().toString().trim();
                String updatedPhone = phoneEditText.getText().toString().trim();
                String updatedBirthday = birthDateEditText.getText().toString().trim();
                EditProfile(updatedName, updatedPhone, updatedBirthday);
            }
        });

    }

    private void EditProfile(String name, String phone, String birthday) {
        String token = sessionManager.getToken();

        User user = new User(name, null, null, birthday, phone, null);

        Call<ApiResponse> call = ApiService.comicAPIServer.editProfle(user, token);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    sessionManager.saveUserInfo(
                            null,
                            name,
                            birthday,
                            phone,
                            null
                    );
                    Toast.makeText(EditProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không thể cập nhật", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("EditProfileActivity", "Error updating profile", t);
            }
        });
    }
}
