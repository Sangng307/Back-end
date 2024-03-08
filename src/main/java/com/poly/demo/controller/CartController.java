package com.poly.demo.controller;

import java.util.List;

import com.poly.demo.Entity.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.demo.Dao.CartDao;
import com.poly.demo.Dao.FoodDao;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Cart;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Users;


@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	CartDao cartDao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	FoodDao foodDao;


	@PostMapping("/cart/add")
	public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO) {
		Cart cart = new Cart();
		cart.setFood(cartDTO.getFoodId());
		cart.setUser(cartDTO.getUserId());
		cart.setQuantity(cartDTO.getQuantity());
		Cart savedCartItem = cartDao.save(cart);
		return ResponseEntity.ok(savedCartItem);
	}

}
