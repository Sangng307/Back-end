package com.poly.demo.controller;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String email;
    private String roles;
}
