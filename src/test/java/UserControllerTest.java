import com.example.socialBookStore.controllers.UserController;
import com.example.socialBookStore.models.User;
import  org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.socialBookStore.dto.JwtResponseDto;
import com.example.socialBookStore.dto.UserDto;
import com.example.socialBookStore.models.User;
import com.example.socialBookStore.services.JwtService;
import com.example.socialBookStore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setFullName("Test User");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(userDto.getPassword());
        user.setFullName(userDto.getFullName());

        when(userService.save(any(User.class))).thenReturn(true);

        // Act
        ResponseEntity<String> response = userController.registerUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User created successfully", response.getBody());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void loginUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("password");
        userDto.setFullName("Test User");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(userDto.getPassword());
        user.setFullName(userDto.getFullName());

        when(userService.login(any(User.class))).thenReturn(true);
        when(jwtService.GenerateToken(anyString())).thenReturn("fake-jwt-token");

        // Act
        ResponseEntity<?> response = userController.loginUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("Login successful", responseBody.get("message"));
        assertEquals("fake-jwt-token", responseBody.get("token"));
        verify(userService, times(1)).login(any(User.class));
        verify(jwtService, times(1)).GenerateToken(anyString());
    }

    @Test
    void getUsers() {
        // Arrange
        List<User> users = List.of(
                new User(),
                new User()
        );

        when(userService.getAllUsers()).thenReturn(users);

        // Act
        List<User> response = userController.getUsers();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(users, response);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void updateProfile() {
        // Arrange
        UserDto userDto = new UserDto();

        userDto.setUsername("testuser");
        userDto.setPassword("newpassword");
        userDto.setFullName("Test User");
        userDto.setAddress("123 Street");
        userDto.setAge(30);
        userDto.setPhoneNumber("1234567890");
        userDto.setFavoriteAuthors(("Author1, Author2"));
        userDto.setFavoriteCategories(("Category1, Category2"));

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPasswordHash(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setAddress(userDto.getAddress());
        user.setAge(userDto.getAge());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setFavoriteAuthors(userDto.getFavoriteAuthors());
        user.setFavoriteCategories(userDto.getFavoriteCategories());

        when(userService.save(any(User.class))).thenReturn(true);

        // Act
        ResponseEntity<?> response = userController.updateProfile(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        assertEquals("Profile updated successfully", responseBody.get("message"));
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void loginUser_currentSession() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setFullName("Test User");

        when(userService.getCurrentSessionUser()).thenReturn(Optional.of(user));

        // Act
        Optional<User> response = userController.loginUser();

        // Assert
        assertTrue(response.isPresent());
        assertEquals(user, response.get());
        verify(userService, times(1)).getCurrentSessionUser();
    }
}
