import com.example.socialBookStore.controllers.NotificationController;
import com.example.socialBookStore.services.NotificationService;
import org.junit.jupiter.api.Test;
import com.example.socialBookStore.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteNotification_success() {
        // Arrange
        Integer notificationId = 1;
        doNothing().when(notificationService).deleteNotificationById(notificationId);

        // Act
        ResponseEntity<?> response = notificationController.deleteNotification(notificationId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification deleted successfully", response.getBody());
        verify(notificationService, times(1)).deleteNotificationById(notificationId);
    }

    @Test
    void deleteNotification_failure() {
        // Arrange
        Integer notificationId = 1;
        doThrow(new RuntimeException("Deletion error")).when(notificationService).deleteNotificationById(notificationId);

        // Act
        ResponseEntity<?> response = notificationController.deleteNotification(notificationId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occured with notification deletion", response.getBody());
        verify(notificationService, times(1)).deleteNotificationById(notificationId);
    }
}
