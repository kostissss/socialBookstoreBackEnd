package com.example.socialBookStore.controllers;

import com.example.socialBookStore.dto.BookRequestDto;
import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.Bookrequest;
import com.example.socialBookStore.models.Notification;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.services.BookOfferService;
import com.example.socialBookStore.services.BookRequestService;
import com.example.socialBookStore.services.NotificationService;
import com.example.socialBookStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookRequests")
public class BookRequestController {

    @Autowired
    private BookRequestService bookRequestService;

    @Autowired
    private BookOfferService bookOfferService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<?> createBookRequest(@RequestBody BookRequestDto bookRequestDto) {





        try {
            Bookrequest bookrequest = new Bookrequest();
            Bookoffer bookoffer = bookOfferService.getBookOfferById(bookRequestDto.getOfferId()).get();
            System.out.println(bookoffer.getId());

            User user =  userService.getCurrentSessionUser().get();

            if(bookoffer.getUser().getId().equals(user.getId())){
                return ResponseEntity.badRequest().body("You can't request your own book");
            }

            bookrequest.setOffer(bookoffer);
            bookrequest.setUser(user);
            bookrequest.setRequestDate(bookRequestDto.getRequestDate());
            bookrequest.setStatus(bookRequestDto.getStatus());
            bookrequest.setRequestDate((Instant.now()));





            bookRequestService.save(bookrequest);

            return ResponseEntity.ok("Successfully created book request!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occured with book request creation");
        }





    }


    @GetMapping("/offer/{offerId}")
    public ResponseEntity<?> getRequestByOfferId(@PathVariable String offerId){
        try {
            return ResponseEntity.ok(bookRequestService.getBookRequestByOfferId(Integer.valueOf(offerId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred ");
        }
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<?> approupdateBookRequest(@PathVariable String requestId) {
        try {
            Bookrequest bookrequest = bookRequestService.getBookRequestById(Integer.valueOf(requestId));
            bookrequest.setStatus("approved");
            Bookoffer bookoffer = bookrequest.getOffer();
            List<Bookrequest> bookrequests = bookRequestService.getBookRequestByOffer(bookoffer);
            bookrequests=bookrequests.stream().filter(bookrequest1 -> !bookrequest1.getStatus().equals("approved")).toList();
            bookRequestService.deleteBookRequests(bookrequests);

            Notification notification = new Notification();
            notification.setUser(bookrequest.getUser());
            notification.setCreatedAt(Instant.now());
            notification.setMessage("Your request for the book "+bookoffer.getTitle()+" has been approved");
            notificationService.save(notification);

            bookrequests.forEach(bookrequest1 -> {
                Notification notification1 = new Notification();
                notification1.setUser(bookrequest1.getUser());
                notification1.setCreatedAt(Instant.now());
                notification1.setMessage("Your request for the book "+bookoffer.getTitle()+" has been declined");
                notificationService.save(notification1);
            });


            bookRequestService.save(bookrequest);

            return ResponseEntity.ok("Succesfilly approved Request!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }


    @PostMapping("/delete/{requestId}")
    public ResponseEntity<?> deleteBookRequest(@PathVariable String requestId) {
        try {
            bookRequestService.deleteBookRequestById(Integer.valueOf(requestId));
            return ResponseEntity.ok("Book request deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

}
