package com.example.socialBookStore.repositories;

import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.Bookrequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookrequestRepository extends JpaRepository<Bookrequest, Integer> {
    List<Bookrequest> findByOffer(Bookoffer offer);





}
