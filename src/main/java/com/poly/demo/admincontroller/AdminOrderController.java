package com.poly.demo.admincontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.poly.demo.Service.EmailSenderService;
import com.poly.demo.util.MessageHandler;
import com.poly.demo.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.demo.Dao.FoodDao;
import com.poly.demo.Dao.OrderDao;
import com.poly.demo.Dao.OrderDetailDao;
import com.poly.demo.Dao.OrderRequestDto;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.Order;
import com.poly.demo.Entity.OrderDetail;
import com.poly.demo.Entity.Users;

@RestController
@RequestMapping("/admin")
public class AdminOrderController {
	@Autowired
	OrderDao orderdao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	FoodDao foodDao;
	@Autowired
	OrderDetailDao orderDetailDao;
	
	@GetMapping(value = {"/order"})
    public ResponseEntity<List<Order>> getListFood(){
        List<Order> order = orderdao.findAll();
        return ResponseEntity.ok(order);
	}
	@GetMapping("/orderStatuses")
    public ResponseEntity<List<Map<String, Object>>> getOrderStatus() {
        List<Map<String, Object>> statuses = Arrays.asList(
            createOrderStatus(1, "Pending"),
            createOrderStatus(2, "Ready"),
            createOrderStatus(3, "On The Way"),
            createOrderStatus(4, "Delivered"),
            createOrderStatus(5, "Cancelled")
        );

        return ResponseEntity.ok(statuses);
    }

    private Map<String, Object> createOrderStatus(int id, String text) {
        return Map.of("id", id, "text", text);
    }
	 
//	@PostMapping("/create/order")
//	public ResponseEntity<String> addOrder(@RequestBody OrderRequestDto orderRequest) {
//	    try {
//	    	System.out.println(orderRequest);
//	        Long userId = orderRequest.getUser();
//	        String address = orderRequest.getAddress();
//	        Double price = orderRequest.getPrice();
//	        String status = orderRequest.getStatus();
//	        Date date = new SimpleDateFormat("dd/MM/yyyy HH mm ss").parse(orderRequest.getDate());
//	        int quantity = orderRequest.getQuantity();
//	        String payment = orderRequest.getPayment();
//	        String note = orderRequest.getNote();
//	        List<Food> FoodID = orderRequest.getFoodId();
//	        Users user = usersDao.findById(userId)
//	                .orElseThrow(() -> new RuntimeException("User not found"));
//	        Order order = new Order();
//	        order.setUser(user);
//	        order.setAddress(address);
//	        order.setDate(date);
//	        order.setQuantity(quantity);
//	        order.setNote(note);
//	        order.setPayment(payment);
//	        order.setPrice(price);
//	        order.setStatus(status);
//	        orderdao.save(order);
//            for (Food food : FoodID) {
//    	        OrderDetail orderDetail = new OrderDetail();
//    	        orderDetail.setOrder(order);
//    	        orderDetail.setFood(food);
//    	        System.out.println(orderDetail.getFood().toString()+"");
//    	        System.out.println(orderDetail.getOrder().toString());
//    	        orderDetailDao.save(orderDetail);
//			}
//	        return ResponseEntity.ok("Order created successfully");
//	    } catch (ParseException e) {
//	    	System.out.println(orderRequest);
//	        e.printStackTrace();
//	        return ResponseEntity.badRequest().body("Invalid date format");
//	    }
//	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getOne(@PathVariable("id") Long id){
		if (!orderdao.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderdao.findById(id).get());
	}
	
	@PutMapping("update/order/{id}")
	public Order update(@PathVariable("id")Integer id,@RequestBody Order order){
		return orderdao.save(order);
	}
	
	@GetMapping(value = {"/byUser/{userId}","/users/show/{id}"})
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        Users user = usersDao.findById(userId).orElse(null);
        if (user != null) {
            return orderdao.findByUser(user);
        } else {
            return null;
        }
    }
	@GetMapping("/orderss/{id}")
	public ResponseEntity<List<OrderDetail>> getListOrderDetail(@PathVariable("id") Long id){
		List<OrderDetail> list = orderDetailDao.findAll();
		List<OrderDetail> listOrderResponse = new ArrayList<>();
		for(OrderDetail ls : list) {
			if(ls.getOrder().getId()==id) {
				listOrderResponse.add(ls);
			}
		}
		return ResponseEntity.ok(listOrderResponse);
	}
	@GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(
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

	@RestController
	@RequestMapping("/api")
	public static class AdminUsersController {
		@Autowired
		UsersDao userdao;
		@Autowired
		MessageHandler messageHandler;
		@Autowired
		QRCodeGenerator codeGenerator;
		@Autowired
		EmailSenderService emailSenderService;
		@GetMapping(value = {"/user","/users"})
		public ResponseEntity<List<Users>> getListUser(){
			List<Users> users = userdao.findAll();
			return ResponseEntity.ok(users);
		}

		@GetMapping(value = {"/user/{id}","/users/{id}"})
		public ResponseEntity<Users> getuser(@PathVariable("id") Long id){
			if (!userdao.existsById(id)) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(userdao.findById(id).get());
		}
	//	@PostMapping("/create/user")
	//	public Users createBook(@RequestBody Users users) {
	//
	//		System.out.println(users);
	//	   return userdao.save(users);
	//	}

		//Link xác thực qua Mail
	//	@GetMapping("/active/user/{userId}")
	//	public String active(@PathVariable("userId")Long userId) {
	//
	//		Users users= userdao.findById(userId).get();
	//		users.setStatus(true);
	//		userdao.save(users);
	//		return "Actived your account";
	//	}
		@GetMapping("/user/{username}")
		public ResponseEntity<Users> getOne(@PathVariable("username") Long userId){
			if (!userdao.existsById(userId)) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(userdao.findById(userId).get());
		}
		@PutMapping("update/user/{username}")
		public Users update(@PathVariable("username")Long username,@RequestBody Users users){
			Users user = userdao.findById(username).get();
			System.out.println(users);
			userdao.save(users);
			return users;
		}
	//	@DeleteMapping("/delete/user/{username}")
	//	public ResponseEntity<Void> delete(@PathVariable("username") String usersname) {
	//		userdao.deleteById(usersname);
	//	    return ResponseEntity.noContent().build();
	//	}

		@PutMapping("update/user/role/{userId}")
		public String updateRole(@RequestBody JsonNode userInfo,
								 @PathVariable("userId") Long userId
		) {
	//		Users user = userdao.findById(userId).get();
	//		List<Authority> listIdAuth = user.getAuthorities();
	//		List<String> listRoleId = new ArrayList<>();
	//		for(Authority list : listIdAuth) {
	//			System.out.println(list.getRole().getId());
	//
	//		}
	//
	//		for(int i = 0 ; i < userInfo.size();i++) {
	//			listRoleId.add(userInfo.get("id").asText());
	//		}
	////		System.out.println(userInfo);
	//		System.out.println(listRoleId);
	////		String[] arrRole = userInfo.findValue("roleId").asText()
	////		System.out.println(userInfo.findValue("roleId").asText());
			return"update";
		}

	//	@PostMapping("/user/create")
	//	public String createBook(@RequestBody Users user) throws WriterException, IOException {
	//		try {
	//		// get list message from messahandler
	//		List<String> mess = messageHandler.createMessageList();
	//		Users users = userdao.findByEmail(user.getEmail());
	//		String data = mess.get(8)+users.getId();
	//		System.out.println(users.getId());
	//		codeGenerator.generateORcode(data);
	//		users.setStatus(false);
	//		emailSenderService.sendMailWithORcode(users.getEmail(), mess.get(0), mess.get(1)+users.getId()+mess.get(9),users);
	//	   return "sended QR";
	//	   }
	//		catch(IOException err) {
	//			return "not send";
	//		}
	//	}

	}
}