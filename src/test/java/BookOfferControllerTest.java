import com.example.socialBookStore.controllers.BookOfferController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.socialBookStore.dto.BookOfferDto;
import com.example.socialBookStore.models.Bookoffer;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.services.BookOfferService;
import com.example.socialBookStore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookOfferControllerTest {

    @Mock
    private BookOfferService bookOfferService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookOfferController bookOfferController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBookOffer() {
        // Arrange
        BookOfferDto bookOfferDto = new BookOfferDto();
        bookOfferDto.setAuthor("Author Name");
        bookOfferDto.setCategory("Fiction");
        bookOfferDto.setTitle("Book Title");
        bookOfferDto.setSummary("Book Summary");

        User currentUser = new User();
        when(userService.getCurrentSessionUser()).thenReturn(Optional.of(currentUser));

        Bookoffer bookoffer = new Bookoffer();
        bookoffer.setAuthor(bookOfferDto.getAuthor());
        bookoffer.setCategory(bookOfferDto.getCategory());
        bookoffer.setTitle(bookOfferDto.getTitle());
        bookoffer.setSummary(bookOfferDto.getSummary());
        bookoffer.setUser(currentUser);

        when(bookOfferService.save(any(Bookoffer.class))).thenReturn(true);

        // Act
        ResponseEntity<String> response = bookOfferController.createBookOffer(bookOfferDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book offer created successfully", response.getBody());
        verify(bookOfferService, times(1)).save(any(Bookoffer.class));
    }

    @Test
    void getAllBookOffers() {
        // Arrange
        List<Bookoffer> bookOffers = List.of(new Bookoffer(), new Bookoffer());
        when(bookOfferService.getAll()).thenReturn(bookOffers);

        // Act
        List<Bookoffer> response = bookOfferController.getAllBookOffers();

        // Assert
        assertNotNull(response);
        assertEquals(bookOffers.size(), response.size());
        verify(bookOfferService, times(1)).getAll();
    }

    @Test
    void getCurrentUsersOffers() {
        // Arrange
        User currentUser = new User();
        when(userService.getCurrentSessionUser()).thenReturn(Optional.of(currentUser));

        List<Bookoffer> bookOffers = List.of(new Bookoffer(), new Bookoffer());
        when(bookOfferService.getUsersOffers(any(User.class))).thenReturn(bookOffers);

        // Act
        List<Bookoffer> response = bookOfferController.getCurrentUsersOffers();

        // Assert
        assertNotNull(response);
        assertEquals(bookOffers.size(), response.size());
        verify(userService, times(1)).getCurrentSessionUser();
        verify(bookOfferService, times(1)).getUsersOffers(currentUser);
    }

    @Test
    void getBookOffersByCategory() {
        // Arrange
        String category = "Fiction";
        List<Bookoffer> bookOffers = List.of(new Bookoffer(), new Bookoffer());
        when(bookOfferService.getBookOffersByCategory(category)).thenReturn(bookOffers);

        // Act
        List<Bookoffer> response = bookOfferController.getBookOffersByCategory(category);

        // Assert
        assertNotNull(response);
        assertEquals(bookOffers.size(), response.size());
        verify(bookOfferService, times(1)).getBookOffersByCategory(category);
    }

    @Test
    void getBookOffersByAuthor() {
        // Arrange
        String author = "Author Name";
        List<Bookoffer> bookOffers = List.of(new Bookoffer(), new Bookoffer());
        when(bookOfferService.getBookOffersByAuthor(author)).thenReturn(bookOffers);

        // Act
        ResponseEntity<?> response = bookOfferController.getBookOffersByAuthor(author);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookOffers, response.getBody());
        verify(bookOfferService, times(1)).getBookOffersByAuthor(author);
    }

    @Test
    void getBookOffersByAuthor_withException() {
        // Arrange
        String author = "Author Name";
        when(bookOfferService.getBookOffersByAuthor(author)).thenThrow(new RuntimeException("Error occurred"));

        // Act
        ResponseEntity<?> response = bookOfferController.getBookOffersByAuthor(author);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occured with book offer creation", response.getBody());
        verify(bookOfferService, times(1)).getBookOffersByAuthor(author);
    }
}
