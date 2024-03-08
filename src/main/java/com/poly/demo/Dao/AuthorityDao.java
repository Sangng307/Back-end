package com.poly.demo.Dao;

import com.poly.demo.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
}
