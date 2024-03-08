package com.poly.demo.Service;

import java.util.List;

import com.poly.demo.Entity.Food;


public interface FoodService {
	public List<Food>  getListFood();
    public Food getFoodById(int id);
    public List<Food> getListFoodSearch(String keyword);
}
