package com.nhom24.doanptuddd.response;

import com.google.gson.annotations.SerializedName;
import com.nhom24.doanptuddd.model.User;

public class ApiResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("data")
    private User data; // <-- đây là quan trọng

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getToken() { return token; }

    public User getData() { return data; }
}