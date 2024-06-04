package com.example.simple_blog_quarkus.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;

import java.util.Base64;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contentType;
    @Lob
    @Column(nullable = false)
    private byte[] imageContent;
    @OneToOne(mappedBy = "titleImage", optional = false)
    @JsonBackReference
    private Entry entry;

    public Image() {
    }

    public Image(String contentType, byte[] imageContent) {
        this.contentType = contentType;
        this.imageContent = imageContent;
    }

    public Long getId() {
        return id;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public Entry getEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "Image{" + "id=" + id + ", contentType='" + contentType + '\'' + '}';
    }
}
