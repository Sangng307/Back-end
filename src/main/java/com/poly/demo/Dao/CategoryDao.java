package com.poly.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.demo.Entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
