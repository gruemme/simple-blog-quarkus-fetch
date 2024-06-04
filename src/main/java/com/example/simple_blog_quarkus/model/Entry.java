package com.example.simple_blog_quarkus.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false, length = 32768)
    private String text;
    @CreationTimestamp
    @Column(updatable = false)
    private Instant created;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Image titleImage;
    @ManyToOne(optional = false)
    @JsonManagedReference
    private BlogUser author;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Tag> tags = new HashSet<>();

    public Entry() {
    }

    public Entry(String title,
                 String text,
                 Image titleImage,
                 BlogUser author,
                 Set<Tag> tags) {
        this.title = title;
        this.text = text;
        this.titleImage = titleImage;
        this.author = author;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Instant getCreated() {
        return created;
    }

    public Image getTitleImage() {
        return titleImage;
    }

    public BlogUser getAuthor() {
        return author;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", created=" + created +
                ", author=" + author +
                '}';
    }
}
