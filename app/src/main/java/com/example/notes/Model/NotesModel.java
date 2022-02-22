package com.example.notes.Model;

import android.graphics.Bitmap;

public class NotesModel {
    String title,content;
    Bitmap image;

    public NotesModel() {
    }

    public NotesModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NotesModel(String title, String content, Bitmap image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
