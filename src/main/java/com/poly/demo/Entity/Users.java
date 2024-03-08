package com.poly.demo.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;

	private String fullname;
	private String email;
	private String phone_number;

	private String address;
	private Date date;
	private boolean status;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	@JsonIgnore
	private List<Authority> authorities = new ArrayList<>();
	@Enumerated(EnumType.STRING) // Assuming you're using strings to represent roles
	private RoleEnum role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
