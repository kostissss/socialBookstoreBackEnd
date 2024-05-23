package com.example.socialBookStore.repositories;


import com.example.socialBookStore.models.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@ComponentScan
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);


}
