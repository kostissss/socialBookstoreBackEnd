package com.example.socialBookStore.controllers;


import com.example.socialBookStore.dto.BookOfferDto;
import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.services.BookOfferService;
import com.example.socialBookStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookoffers")
public class BookOfferController {

    @Autowired
    private BookOfferService bookOfferService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createBookOffer(@RequestBody BookOfferDto bookofferRequest) {
        Bookoffer bookoffer = new Bookoffer();
        bookoffer.setAuthor(bookofferRequest.getAuthor());
        bookoffer.setCategory(bookofferRequest.getCategory());
        bookoffer.setTitle(bookofferRequest.getTitle());
        bookoffer.setSummary(bookofferRequest.getSummary());
        //bookoffer.setCreatedAt(bookofferRequest.getCreatedAt());
        bookoffer.setUser(userService.getCurrentSessionUser().get());

        if(bookOfferService.save(bookoffer)){
            return ResponseEntity.ok("Book offer created successfully");
        }
        else {
            return ResponseEntity.badRequest().body("An error occured with book offer creation");

        }
    }

    @GetMapping("/")
    public List<Bookoffer> getAllBookOffers() {
        return bookOfferService.getAll();
    }

    @GetMapping("/usersOffers")
    public  List<Bookoffer> getCurrentUsersOffers() {
        return bookOfferService.getUsersOffers(userService.getCurrentSessionUser().get());
    }

    @GetMapping("/category/{category}")
    public List<Bookoffer> getBookOffersByCategory(@PathVariable String category) {
        return bookOfferService.getBookOffersByCategory(category);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBookOffersByAuthor(@PathVariable String author) {
        try {
            return ResponseEntity.ok(bookOfferService.getBookOffersByAuthor(author));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occured with book offer creation");
        }

    }

}
