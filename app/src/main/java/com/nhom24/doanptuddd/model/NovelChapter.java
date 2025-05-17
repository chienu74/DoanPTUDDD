package com.nhom24.doanptuddd.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NovelChapter implements Parcelable {
    private int id;
    private String title;
    private int novel_id;
    private String content;

    public NovelChapter(int id, String title, int novel_id, String content) {
        this.id = id;
        this.title = title;
        this.novel_id = novel_id;
        this.content = content;
    }

    protected NovelChapter(Parcel in) {
        id = in.readInt();
        title = in.readString();
        novel_id = in.readInt();
        content = in.readString();
    }

    public static final Creator<NovelChapter> CREATOR = new Creator<NovelChapter>() {
        @Override
        public NovelChapter createFromParcel(Parcel in) {
            return new NovelChapter(in);
        }

        @Override
        public NovelChapter[] newArray(int size) {
            return new NovelChapter[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNovel_id() {
        return novel_id;
    }

    public void setNovel_id(int novel_id) {
        this.novel_id = novel_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(novel_id);
        dest.writeString(content);
    }
}
