package com.example.simple_blog_quarkus.dto;

import java.util.List;

public class EntryInput {

    private String title;
    private String text;
    private byte[] imageData;
    private String imageContentType;
    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "EntryInput{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", imageContentType='" + imageContentType + '\'' +
                ", tags=" + tags +
                '}';
    }
}
