package com.example.socialBookStore.services;

import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.repositories.BookofferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookOfferService {

    @Autowired
    private BookofferRepository bookofferRepository;

    public boolean save(Bookoffer bookoffer) {

        try{
            bookofferRepository.save(bookoffer);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public List<Bookoffer> getAll() {

        return bookofferRepository.findAll();
    }

    public List<Bookoffer> getUsersOffers(User user) {
        return bookofferRepository.findByUser(user);
    }

    public List <Bookoffer> getBookOffersByCategory(String category) {
        return bookofferRepository.findByCategory(category);
    }

    public List <Bookoffer> getBookOffersByAuthor(String author) {
        return bookofferRepository.findByAuthor(author);
    }

    public Optional<Bookoffer> getBookOfferById(Integer id) {
        return bookofferRepository.findById(id);
    }


}
