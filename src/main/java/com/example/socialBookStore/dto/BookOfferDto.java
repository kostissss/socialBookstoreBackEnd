package com.example.socialBookStore.dto;

import java.time.Instant;

public class BookOfferDto {

    private Integer id;
    private String title;
    private String author;
    private String category;
    private String summary;

    private Instant createdAt;

    public BookOfferDto() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }



    public BookOfferDto(Integer id, String title, String author, String category, String summary, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.summary = summary;
        this.createdAt = createdAt;
    }
}
