package com.nhom24.doanptuddd.response;

import com.nhom24.doanptuddd.model.Comic;

import java.util.List;

public class ComicResponse {
    private boolean success;
    private List<Comic> data;

    public boolean isSuccess() { return success; }
    public List<Comic> getData() { return data; }
}
