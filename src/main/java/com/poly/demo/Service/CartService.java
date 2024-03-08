//package com.poly.demo.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.poly.demo.Dao.CartDao;
//import com.poly.demo.Dao.FoodDao;
//import com.poly.demo.Dao.UsersDao;
//import com.poly.demo.Entity.Cart;
//import com.poly.demo.Entity.Food;
//import com.poly.demo.Entity.Users;
//
//@Service
//public class CartService {
//
//	@Autowired
//	FoodDao foodDao;
//	@Autowired
//	UsersDao usersDao;
//	@Autowired 
//	CartDao cartDao;
//	
//	public void addToCart(Users users, Food food) {
//		Cart existingCartItem = cartDao.findByUserAndFood(users, food);
//		if(existingCartItem != null) {
//			existingCartItem.setQuantity(existingCartItem.getQuantity()+1);
//			cartDao.save(existingCartItem);
//		}else {
//			Cart cart = new Cart();
//			cart.setUser(users);
//			cart.setFood(food);
//			cart.setQuantity(1);
//			cartDao.save(cart);
//		}
//		
//	}
//}
