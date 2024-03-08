package com.poly.demo.Dao;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.demo.Entity.Cart;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Users;




public interface CartDao extends JpaRepository<Cart,Long> {
	@Query("Select u from Cart u where u.user = :user ")

	List<Cart> findByUser(Users user);
	
	@Query("Select u from Cart u where u.user = :users and u.food = :food")
	Cart findByUserAndFood(Users users, Food food);
	

}
