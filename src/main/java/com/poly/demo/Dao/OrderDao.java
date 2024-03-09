package com.poly.demo.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.poly.demo.reqRespModels.DailyOrderData;
import com.poly.demo.reqRespModels.DailyRevenueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.demo.Entity.Cart;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Order;
import com.poly.demo.Entity.OrderDetail;
import com.poly.demo.Entity.Users;
import org.springframework.data.repository.query.Param;

public interface OrderDao extends JpaRepository<Order, Long> {
	
//	@Query("Select u from Order u where u.user = :user ")
//
//	List<Order> findByUser(Users user);
//	
//	@Query("Select u from Order u where u.user = :users and u.food = :food")
//	Order findByUserAndFood(Users users, Food food);
	@Query("SELECT new com.poly.demo.reqRespModels.DailyRevenueData(o.date, o.price) " +
		"FROM Order o " +
		"WHERE o.date BETWEEN :startDay AND :endDay ")
	List<DailyRevenueData> getDailyRevenueData(
		@Param("startDay") Date startDay,
		@Param("endDay") Date endDay);

	@Query("SELECT new com.poly.demo.reqRespModels.DailyOrderData(o.date, COUNT (o.quantity)) " +
			"FROM Order o " +
			"WHERE o.date BETWEEN :startDay AND :endDay " +
			"GROUP BY o.date")
	List<DailyOrderData> getDailyOrder(
			@Param("startDay") Date startDay,
			@Param("endDay") Date endDay);

	List<Order> findByDateBetween(Date startDate, Date endDate);
	List<Order> findByUserUsernameContainingIgnoreCase(String username);
	List<Order> findByStatusIn(List<String> statusList);
	List<Order> findByStatus(String status);
    List<Order> findByUserId(long userId);
    List<Order> findByStatusAndUserId(String status, Long userId);
	List<Order> findByUser(Users user);
	
	@Query(value = "SELECT o.user.id AS userId, SUM(o.quantity) AS totalQuantityPurchased " +
	        "FROM Order o " +
	        "GROUP BY o.user.id " +
	        "ORDER BY totalQuantityPurchased DESC")
		List<Order> findBestSellingFood();

	@Query(value = "SELECT TOP 5 food_id AS foodId, SUM(o.quantity) AS totalQuantitySold " +
            "FROM [order] o " +
            "JOIN food ON o.food_id = food.id " +
            "GROUP BY food_id " +
            "ORDER BY totalQuantitySold ASC", nativeQuery = true)
		List<Order> findLowestSellingFood();
		
	
	
	

	
	
	
	
	
}
