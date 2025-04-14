package com.nhom24.doanptuddd.model;
import com.google.gson.annotations.SerializedName;
public class User {
    @SerializedName("accountName") // Tên field đúng với backend
    private String accountName;

    @SerializedName("password")
    private String password;

    @SerializedName("gmail")
    private String gmail;

    public User(String accountName, String password, String gmail) {
        this.accountName = accountName;
        this.password = password;
        this.gmail = gmail;
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
}
