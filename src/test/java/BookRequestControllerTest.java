import com.example.socialBookStore.controllers.BookRequestController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.socialBookStore.dto.BookRequestDto;
import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.Bookrequest;
import com.example.socialBookStore.models.Notification;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.services.BookOfferService;
import com.example.socialBookStore.services.BookRequestService;
import com.example.socialBookStore.services.NotificationService;
import com.example.socialBookStore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookRequestControllerTest {

    @Mock
    private BookRequestService bookRequestService;

    @Mock
    private BookOfferService bookOfferService;

    @Mock
    private UserService userService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookRequestController bookRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBookRequest() {
        // Arrange
        BookRequestDto bookRequestDto = new BookRequestDto();

        bookRequestDto.setRequestDate(Instant.now());
        bookRequestDto.setStatus("pending");

        Bookoffer bookoffer = new Bookoffer();
        bookoffer.setId(1);

        User currentUser = new User();
        when(bookOfferService.getBookOfferById(bookRequestDto.getOfferId())).thenReturn(Optional.of(bookoffer));
        when(userService.getCurrentSessionUser()).thenReturn(Optional.of(currentUser));

        Bookrequest bookrequest = new Bookrequest();
        bookrequest.setOffer(bookoffer);
        bookrequest.setUser(currentUser);
        bookrequest.setRequestDate(bookRequestDto.getRequestDate());
        bookrequest.setStatus(bookRequestDto.getStatus());

        when(bookRequestService.save(any(Bookrequest.class))).thenReturn(true);

        // Act
        ResponseEntity<?> response = bookRequestController.createBookRequest(bookRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully created book request!", response.getBody());
        verify(bookOfferService, times(1)).getBookOfferById(bookRequestDto.getOfferId());
        verify(userService, times(1)).getCurrentSessionUser();
        verify(bookRequestService, times(1)).save(any(Bookrequest.class));
    }

    @Test
    void getRequestByOfferId() {
        // Arrange
        int offerId = 1;
        List<Bookrequest> bookRequests = List.of(new Bookrequest(), new Bookrequest());
        when(bookRequestService.getBookRequestByOfferId(offerId)).thenReturn(bookRequests);

        // Act
        ResponseEntity<?> response = bookRequestController.getRequestByOfferId(String.valueOf(offerId));

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookRequests, response.getBody());
        verify(bookRequestService, times(1)).getBookRequestByOfferId(offerId);
    }

    @Test
    void approupdateBookRequest() {
        // Arrange
        int requestId = 1;
        Bookrequest bookrequest = new Bookrequest();
        bookrequest.setStatus("pending");

        Bookoffer bookoffer = new Bookoffer();
        bookoffer.setTitle("Book Title");
        bookrequest.setOffer(bookoffer);

        List<Bookrequest> bookRequests = List.of(new Bookrequest(), new Bookrequest());
        when(bookRequestService.getBookRequestById(requestId)).thenReturn(bookrequest);
        when(bookRequestService.getBookRequestByOffer(bookoffer)).thenReturn(bookRequests);
        when(bookRequestService.save(any(Bookrequest.class))).thenReturn(true);

        // Act
        ResponseEntity<?> response = bookRequestController.approupdateBookRequest(String.valueOf(requestId));

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Succesfilly approved Request!", response.getBody());
        assertEquals("approved", bookrequest.getStatus());
        verify(bookRequestService, times(1)).getBookRequestById(requestId);
        verify(bookRequestService, times(1)).getBookRequestByOffer(bookoffer);
        verify(bookRequestService, times(1)).deleteBookRequests(anyList());
        verify(notificationService, times(1)).save(any(Notification.class));
    }

    @Test
    void deleteBookRequest() {
        // Arrange
        int requestId = 1;
        when(bookRequestService.deleteBookRequestById(requestId)).thenReturn(true);

        // Act
        ResponseEntity<?> response = bookRequestController.deleteBookRequest(String.valueOf(requestId));

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book request deleted successfully", response.getBody());
        verify(bookRequestService, times(1)).deleteBookRequestById(requestId);
    }
}
