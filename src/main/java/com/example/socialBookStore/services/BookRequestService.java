package com.example.socialBookStore.services;

import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.Bookrequest;
import com.example.socialBookStore.repositories.BookrequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRequestService {

    @Autowired
    private BookrequestRepository bookrequestRepository;

    @Autowired
    private BookOfferService BookOfferService;

    public boolean save(Bookrequest bookrequest) {

        try{
            bookrequestRepository.save(bookrequest);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public List<Bookrequest> getBookRequestByOfferId(Integer id) {

        Bookoffer offer = BookOfferService.getBookOfferById(id).get();

        return bookrequestRepository.findByOffer(offer);
    }
    public List<Bookrequest> getBookRequestByOffer(Bookoffer offer) {

        return bookrequestRepository.findByOffer(offer);
    }

    public Bookrequest getBookRequestById(Integer id) {
        return bookrequestRepository.findById(id).get();
    }

    public boolean deleteBookRequestById(Integer id) {
        try {
            bookrequestRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean deleteBookRequests(List<Bookrequest> bookrequests) {
        try {
            bookrequestRepository.deleteAll(bookrequests);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
