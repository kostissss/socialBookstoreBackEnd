package com.example.socialBookStore.services;

import com.example.socialBookStore.controllers.UserController;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService extends UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean save(User newUser) {

        Optional<User> user = userRepository.findByUsername(newUser.getUsername());
        if (user.isEmpty()) {
            String password = newUser.getPasswordHash();
            String encoded = passwordEncoder.encode(password);
            newUser.setPasswordHash(encoded);
            userRepository.save(newUser);
            return true;
        } else if ( Objects.equals(newUser.getId(),  getCurrentSessionUser().get().getId()) ) {
            try {


                newUser.setPasswordHash(user.get().getPasswordHash());
                System.out.println(newUser.getPasswordHash());
                userRepository.save(newUser);
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();

            }

        }
        return false;
    }

    public boolean login(User user) {
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());

            return user1.isPresent()?passwordEncoder.matches(user.getPasswordHash(), user1.get().getPasswordHash()) : false;


    }


    public Optional<User> getCurrentSessionUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Optional<User> user = userRepository.findByUsername(username);

        return user;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
