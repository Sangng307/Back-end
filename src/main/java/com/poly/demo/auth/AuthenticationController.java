package com.poly.demo.auth;

import com.poly.demo.Dao.AuthorityDao;
import com.poly.demo.Entity.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationRespone> register(
            @RequestBody RegisterRequest request
    ){

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationRespone> authenticate(
            @RequestBody AuthenticationRequest request
    ){

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationRespone> authenticateUser(
            @RequestBody AuthenticationRequest request
    ){

        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }
}
