package com.poly.demo.admincontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.demo.Dao.FoodDao;
import com.poly.demo.Dao.OrderAndDetailDTO;
import com.poly.demo.Dao.OrderDao;
import com.poly.demo.Dao.OrderDetailDao;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Order;
import com.poly.demo.Entity.OrderDetail;
import com.poly.demo.Entity.Users;

@RestController
@RequestMapping("/admin")
public class AdminStatisticalController {
	@Autowired
	OrderDao orderdao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	FoodDao foodDao;
	@Autowired
	OrderDetailDao orderDetailDao;
		
//	@GetMapping("/statistica")
//	public ResponseEntity<List<Order>> getOrderAndDetail() {
//	    List<Order> orders = orderdao.findAll();
//	    return ResponseEntity.ok(orders);
//	}
//	@PostMapping("/statistical/filter")
//	public ResponseEntity<List<Order>> filterByDate(
//		    @RequestParam("ngayBatDau") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngaybatdau,
//		    @RequestParam("ngayKetThuc") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ngayketthuc
//		) {
//	    try {
//	        List<Order> orders = orderdao.findByDateBetween(
//	            Date.from(ngaybatdau.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//	            Date.from(ngayketthuc.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant())
//	        );
//	        return ResponseEntity.ok(orders);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}
//	
//	@PostMapping("/statistical/search")
//	public ResponseEntity<List<Order>> searchOrdersByUserName(@RequestBody Map<String, String> requestData) {
//	    String username = requestData.get("username");
//	    List<Order> orders = orderdao.findByUserUsernameContainingIgnoreCase(username);
//	    return ResponseEntity.ok(orders);
//	}
//	@PostMapping("/statistical/search/status")
//	public ResponseEntity<List<Order>> searchOrdersByStatus(@RequestBody Map<String, List<String>> requestData) {
//	    List<String> statusList = requestData.get("status");
//	    List<Order> orders = orderdao.findByStatusIn(statusList);
//	    return ResponseEntity.ok(orders);
//	}
//	
//	@PostMapping("/statistical/search/bySale")
//    public ResponseEntity<List<Order>> searchOrders(@RequestBody Map<String, String> requestData) {
//        String orderBy = requestData.get("orderBy");
//        List<Order> orders;
//
//        if ("bestSelling".equals(orderBy)) {
//            orders = orderdao.findBestSellingFood();
//        } else if ("worstSelling".equals(orderBy)) {
//        	orders = orderdao.findLowestSellingFood();
//        } else {
//            orders = orderdao.findAll();
//        }
//        return ResponseEntity.ok(orders);
//    }
	@GetMapping("/statistica")
    public ResponseEntity<List<Order>> getStatistica(
            @RequestParam(value = "_start", defaultValue = "0") int start,
            @RequestParam(value = "_end", defaultValue = "10") int end,
            @RequestParam(value = "status.text", required = false) String statusText,
            @RequestParam(value = "user.id", required = false) Long userId
    ) {
        // Fetch orders from the database based on the provided parameters
        List<Order> orders;
        if (statusText != null && userId != null && userId != 0) {
            orders = orderdao.findByStatusAndUserId(statusText, userId);
        } else if (statusText != null) {
            orders = orderdao.findByStatus(statusText);
        } else if (userId != null && userId != 0) { 
            orders = orderdao.findByUserId(userId);
        } else {
            orders = orderdao.findAll();
        }
        return ResponseEntity.ok(orders);
    }
	
}
