//package com.poly.demo.Service;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.poly.demo.Dao.FoodDao;
//import com.poly.demo.Dao.OrderDao;
//import com.poly.demo.Dao.UsersDao;
//import com.poly.demo.Entity.Cart;
//import com.poly.demo.Entity.Food;
//import com.poly.demo.Entity.Order;
//import com.poly.demo.Entity.Users;
//
//@Service
//public class OrderService {
//
//	@Autowired
//	FoodDao foodDao;
//	@Autowired
//	UsersDao usersDao;
//	@Autowired
//	OrderDao orderDao;
//	public void addToOrder(Users users, Food food) {
//		Order existingCartItem = orderDao.findByUserAndFood(users, food);
//		if(existingCartItem != null) {
//			existingCartItem.setQuantity(existingCartItem.getQuantity()+1);
//			orderDao.save(existingCartItem);
//		}else {
//			Order order = new Order();
//			order.setUser(users);
//			order.setFood(food);
//			order.setDate(new Date());
//			order.setStatus("Chờ Xử Lý");
//			order.setAddress(users.getAddress());
//			order.setQuantity(1);
//			orderDao.save(order);
//		}
//		
//	}
//
//}
