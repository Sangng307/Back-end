package com.poly.demo.auth;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String phone_number;
    private String email;
    private String password;
    private Date date;
}
