package com.example.socialBookStore.repositories;


import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@ComponentScan
public interface BookofferRepository extends JpaRepository<Bookoffer, Integer> {
    List<Bookoffer> findByUser(User user);

    List<Bookoffer> findByCategory(String category);

    List<Bookoffer> findByAuthor(String author);



    // Optional<Bookoffer> findByAuthor(Long author);
}
