package com.nhom24.doanptuddd.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_TOKEN = "token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        editor.remove(KEY_TOKEN).apply();
    }

    public  void clearUserInfo(){
        editor.remove("gmail");
        editor.remove("accountName");
        editor.remove("birthday");
        editor.remove("avatar");
        editor.remove("phone");
        editor.apply();
    }

    public void saveUserInfo(String gmail, String accountName, String birthday, String phone, String avatar) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gmail", gmail);
        editor.putString("accountName", accountName);
        editor.putString("birthday", birthday);
        editor.putString("avatar", avatar);
        editor.putString("phone", phone);
        editor.apply();
    }

    public String getGmail() {
        return sharedPreferences.getString("gmail", null);
    }

    public String getAccountName() {
        return sharedPreferences.getString("accountName", null);
    }

    public String getBirthday() {
        return sharedPreferences.getString("birthday", null);
    }

    public String getPhone() {
        return sharedPreferences.getString("phone", null);
    }

    public String getAvatar() {
        return sharedPreferences.getString("avatar", null);
    }



}
