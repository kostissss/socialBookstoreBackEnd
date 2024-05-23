package com.example.socialBookStore.controllers;


import com.example.socialBookStore.dto.JwtResponseDto;
import com.example.socialBookStore.dto.UserDto;
import com.example.socialBookStore.models.Role;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.example.socialBookStore.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserDto userRequest ){
        User user = new User ();
        user.setUsername(userRequest.getUsername());
        user.setPasswordHash(userRequest.getPassword());
        user.setFullName(userRequest.getFullName());

       if( userService.save(user)){
           return ResponseEntity.ok("User created successfully");
       }
        return ResponseEntity.badRequest().body("An error occured with user creation");
    }

    @GetMapping("/profile")
    public Optional<User> loginUser() {
        return userService.getCurrentSessionUser();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> loginUser(@RequestBody UserDto userRequest ) {
        User user = new User ();
        user.setUsername(userRequest.getUsername());
        user.setPasswordHash(userRequest.getPassword());
        user.setFullName(userRequest.getFullName());

        if( userService.login(user)){
            return ResponseEntity.ok().body(Map.of("message", "Login successful", "token", jwtService.GenerateToken(user.getUsername())));
        }
        return ResponseEntity.badRequest().body("An error occured with user login");
    }

    @GetMapping("/")
    public List<User> getUsers(){

        return userService.getAllUsers();

    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserDto userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setUsername(userRequest.getUsername());
        user.setPasswordHash(userRequest.getPassword());
        user.setFullName(userRequest.getFullName());
        user.setAddress(userRequest.getAddress());
        user.setAge(userRequest.getAge());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setFavoriteAuthors(userRequest.getFavoriteAuthors());
        user.setFavoriteCategories(userRequest.getFavoriteCategories());

        try {
            if(userService.save(user)){
                return ResponseEntity.ok().body(Map.of("message", "Profile updated successfully"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Profile not updated successfully"));
        }catch (Exception e){
        return ResponseEntity.badRequest().body(e);}
    }



}