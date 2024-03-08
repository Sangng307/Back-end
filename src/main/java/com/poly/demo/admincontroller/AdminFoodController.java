package com.poly.demo.admincontroller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.poly.demo.Dao.FoodDao;
import com.poly.demo.Entity.Food;


//@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminFoodController {
	@Autowired
	FoodDao fooddao;
	
	@GetMapping(value = {"/food"})
	public ResponseEntity<List<Food>> getListFood() {
	    List<Food> foods = fooddao.findAll();
	    return ResponseEntity.ok(foods);
	}
	@PostMapping("/products")
	public Food createBook(@RequestBody Food food) {
	   return fooddao.save(food);
	}
	@GetMapping(value = {"/food/{id}","/products/{id}"})
	public ResponseEntity<Food> getOne(@PathVariable("id") Long id){
		if (!fooddao.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(fooddao.findById(id).get());
	}
	@PatchMapping("/products/{id}")
	public Food update(@PathVariable("id")Long id,@RequestBody Food food){
		System.out.println(food+"sout");
		return fooddao.save(food);
	}
	@DeleteMapping("/delete/food/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		fooddao.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
	@GetMapping("/products")
    public ResponseEntity<List<Food>> getProducts(
            @RequestParam(value = "_start", defaultValue = "0") int start,
            @RequestParam(value = "_end", defaultValue = "10") int end,
            @RequestParam(value = "category.id", required = false) Long categoryId,
            @RequestParam(value = "name_like", required = false) String foodName
    ) {
        List<Food> products;
        
        if (categoryId != null && foodName != null) {
            products = fooddao.findByCategory_IdAndFoodNameLike(categoryId, "%" + foodName + "%");
        } else if (categoryId != null) {
            products = fooddao.findByCategory_Id(categoryId);
        } else if (foodName != null) {
            products = fooddao.findByFoodNameLike("%" + foodName + "%");
        } else {
            products = fooddao.findAll();
        }

        int total = products.size();
        int startIndex = Math.min(start, total);
        int endIndex = Math.min(end, total);
        List<Food> result = products.subList(startIndex, endIndex);
       
        return ResponseEntity.ok(result);
    }
	
}
