package com.poly.demo.controller;

import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserProfileService userProfileService;
    private final UsersDao usersDao;
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(Authentication authentication) {
        // Assuming you have a UserDetails object representing the authenticated user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Retrieve the user profile based on the authenticated user's information
        UserProfile userProfile = userProfileService.getUserProfile(userDetails.getUsername());

        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<Users> user = usersDao.findById(userId);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
