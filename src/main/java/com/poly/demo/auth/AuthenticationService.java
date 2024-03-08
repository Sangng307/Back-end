package com.poly.demo.auth;


import com.poly.demo.Dao.AuthorityDao;

import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Authority;

import com.poly.demo.Entity.RoleEnum;
import com.poly.demo.Entity.Users;
import com.poly.demo.util.JwtService;
import com.poly.demo.util.UnauthorizedException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersDao userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthorityDao authorityDao;

    public AuthenticationRespone register(RegisterRequest request) {
        Date currentDate = new Date();
        var user = Users.builder()
                .username(request.getUsername())
                .phone_number(request.getPhone_number())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .date(currentDate)
                .status(true)
                .build();
        userRepository.save(user);

        Authority authority = new Authority();
        authority.setUser(user);
        authority.setAuthority(RoleEnum.ROLE_USER.name());

        authorityDao.save(authority);
        System.out.println(authority);

        return AuthenticationRespone.builder().build();
    }

    public AuthenticationRespone authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            // Handle authentication failure
        }

        // Retrieve user from the repository
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Check if the user has the required authority
        boolean hasAuthority = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(RoleEnum.ROLE_ADMIN.name()));

        if (!hasAuthority) {
            // Handle unauthorized access
            throw new UnauthorizedException("User doesn't have required authority");
        }

        // Generate JWT token
        var jwtToken = jwtService.generateToken((UserDetails) user);
        var username = user.getUsername();

        return AuthenticationRespone.builder()
                .token(jwtToken)
                .username(username)
                .build();
    }

    public AuthenticationRespone authenticateUser(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            // Handle authentication failure
        }

        // Retrieve user from the repository
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Generate JWT token
        var jwtToken = jwtService.generateToken((UserDetails) user);
        var username = user.getUsername();
        var id = user.getId();
        return AuthenticationRespone.builder()
                .token(jwtToken)
                .username(username)
                .userId(id)
                .build();
    }
}
