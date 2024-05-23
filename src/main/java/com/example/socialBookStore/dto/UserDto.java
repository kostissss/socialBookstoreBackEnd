package com.example.socialBookStore.dto;



public class UserDto {

    private String username;

    private String password;
    private String fullName;


    private String address;

    private Integer age;

    private String phoneNumber;

    private String favoriteCategories;


    private String favoriteAuthors;

    private Integer id;


    public UserDto(String username ,String email, String password, String role, String fullName,
                   String address,
                   Integer age,String phoneNumber,String favoriteCategories,String favoriteAuthors, Integer id) {
        super();
        this.username = username;

        this.password = password;

        this.fullName = fullName;

        this.address = address;
        this.age = age;

        this.phoneNumber = phoneNumber;

        this.favoriteCategories = favoriteCategories;

        this.favoriteAuthors = favoriteAuthors;

        this.id = id;


    }

    public UserDto() {

    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFavoriteCategories() {
        return favoriteCategories;
    }

    public void setFavoriteCategories(String favoriteCategories) {
        this.favoriteCategories = favoriteCategories;
    }

    public String getFavoriteAuthors() {
        return favoriteAuthors;
    }

    public void setFavoriteAuthors(String favoriteAuthors) {
        this.favoriteAuthors = favoriteAuthors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}