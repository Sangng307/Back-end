package com.poly.demo.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.poly.demo.Entity.Food;


@Repository
public interface FoodDao extends JpaRepository<Food, Long> {
	@Query("SELECT f FROM Food f WHERE f.food_name LIKE CONCAT('%', :foodName, '%')")
    List<Food> findByFoodNameLike(@Param("foodName") String foodName);
	
	List<Food> findByCategory_Id(Long categoryId);
	
	@Query("SELECT f FROM Food f WHERE f.category.id = :categoryId AND f.food_name LIKE %:foodName%")
    List<Food> findByCategory_IdAndFoodNameLike(@Param("categoryId") Long categoryId, @Param("foodName") String foodName);
}

