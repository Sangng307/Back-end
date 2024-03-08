package com.poly.demo.Entity;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@SuppressWarnings("serial")

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="authorities")
public class Authority implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String authority;
	@ManyToOne(optional = false)
	@JoinColumn(name="username")
	private Users user;

	public Authority(String authority) {
		this.authority =authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
