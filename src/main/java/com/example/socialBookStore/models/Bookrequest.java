package com.example.socialBookStore.models;

import jakarta.persistence.*;


import java.time.Instant;


@Entity
@Table(name = "bookrequests")
public class Bookrequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private Integer id;

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

    public Bookoffer getOffer() {
        return offer;
    }

    public void setOffer(Bookoffer offer) {
        this.offer = offer;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id", nullable = false)
    private Bookoffer offer;



    @Column(name = "request_date")
    private Instant requestDate;

    @Lob
    @Column(name = "status")
    private String status;

}