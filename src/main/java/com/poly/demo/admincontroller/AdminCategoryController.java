package com.poly.demo.admincontroller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.demo.Dao.CategoryDao;
import com.poly.demo.Entity.Category;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {
	@Autowired
	CategoryDao categorydao;
	
	@GetMapping("/category")
    public ResponseEntity<List<Category>> getListFood(){
        List<Category> category = categorydao.findAll();
        return ResponseEntity.ok(category);
	}
	@PostMapping("/create/category")
	public Category createBook(@RequestBody JsonNode category) {
		Category cate = new Category();
		cate.setName_category(category.get("nameCategory").asText());
	   return categorydao.save(cate);
	}
	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getOne(@PathVariable("id") Long id){
		if (!categorydao.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categorydao.findById(id).get());
	}
	@PatchMapping("category/{id}")
	public Category update(@PathVariable("id")Long id,@RequestBody JsonNode category){
		Category cate = categorydao.findById(id).get();
		cate.setName_category(category.get("name_category").asText());
		return categorydao.save(cate);
	}
	@DeleteMapping("/delete/category/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		categorydao.deleteById(id);
	    return ResponseEntity.noContent().build();
	}
}
