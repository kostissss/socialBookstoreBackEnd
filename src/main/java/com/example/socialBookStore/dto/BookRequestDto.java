package com.example.socialBookStore.dto;

import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.User;

import java.time.Instant;

public class BookRequestDto {

    private Integer id;

    private User user;

    private Integer offerId;

    private Instant requestDate;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOffer(Integer offerId) {
        this.offerId = offerId;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BookRequestDto() {



    }

    public BookRequestDto(Integer id, User user, Integer offerId, Instant requestDate, String status) {
        this.id = id;
        this.user = user;
        this.offerId = offerId;
        this.requestDate = requestDate;
        this.status = status;
    }
}
