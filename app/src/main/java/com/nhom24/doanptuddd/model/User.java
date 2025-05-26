package com.nhom24.doanptuddd.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("accountName") // Tên field đúng với backend
    private String accountName;

    @SerializedName("password")
    private String password;

    @SerializedName("gmail")
    private String gmail;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("phone")
    private  String phone;

    @SerializedName("avatar")
    private String avatar;

    public User(String accountName, String password, String gmail, String birthday, String phone, String avatar) {
        this.accountName = accountName;
        this.password = password;
        this.gmail = gmail;
        this.birthday = birthday;
        this.phone = phone;
        this.avatar = avatar;
    }

    // Getter và Setter
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
