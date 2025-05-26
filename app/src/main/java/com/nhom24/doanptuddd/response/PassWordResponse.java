package com.nhom24.doanptuddd.response;

import com.google.gson.annotations.SerializedName;

public class PassWordResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private  String message;

    public boolean isSuccess() {
        return success;
    }

    public String Message() {return  message;}
}
