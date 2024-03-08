package com.poly.demo.controller;

import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.RoleEnum;
import com.poly.demo.Entity.Users;
import com.poly.demo.util.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UsersDao userRepository;

    public UserProfile getUserProfile(String email) {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            RoleEnum role = user.getRole(); // Assuming you have a method to retrieve the user's role
            return mapUserToProfile(user, role);
        } else {
            throw new UnauthorizedException("User profile not found for email: " + email);
        }
    }

    // Helper method to map User entity to UserProfile
    private UserProfile mapUserToProfile(Users user, RoleEnum authority) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(user.getId());
        userProfile.setEmail(user.getEmail());
        userProfile.setRoles(authority.ROLE_USER.name());
        // Map other attributes as needed
        return userProfile;
    }
}
