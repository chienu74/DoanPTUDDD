package com.nhom24.doanptuddd.Request;

public class ForgotEmail {
    private String gmail;
    private String otp;
    private String password;

    public ForgotEmail(String gmail, String otp, String password) {
        this.gmail = gmail;
        this.otp = otp;
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

