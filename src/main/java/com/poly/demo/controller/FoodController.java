package com.poly.demo.controller;

import com.poly.demo.Dao.FoodDao;
import com.poly.demo.Entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class FoodController {
	@Autowired
	FoodDao fooddao;

	@PostMapping("/foods")
	public ResponseEntity<List<Food>> PostFoods() {
		try {
			List<Food> foods = fooddao.findAll();
			return ResponseEntity.ok(foods);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/foods")
	public ResponseEntity<List<Food>> getFoods() {
		try {
			List<Food> foods = fooddao.findAll();
			return ResponseEntity.ok(foods);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/foods/{id}")
	public ResponseEntity<Food> getOne(@PathVariable("id") Long id) {
		return fooddao.findById(id)
				.map(food -> ResponseEntity.ok().body(food))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/foods/{id}")
	public ResponseEntity<Food> PgetOne(@PathVariable("id") Long id) {
		return fooddao.findById(id)
				.map(food -> ResponseEntity.ok().body(food))
				.orElse(ResponseEntity.notFound().build());
	}
}
