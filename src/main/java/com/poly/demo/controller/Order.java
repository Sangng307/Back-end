package com.poly.demo.controller;

import com.poly.demo.Dao.OrderDao;
import com.poly.demo.Dao.OrderDetailDao;
import com.poly.demo.Dao.OrderRequestDto;
import com.poly.demo.Dao.UsersDao;
import com.poly.demo.Entity.Food;
import com.poly.demo.Entity.OrderDetail;
import com.poly.demo.Entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Order {
    private final UsersDao usersDao;
    private final OrderDao orderdao;
    private final OrderDetailDao orderDetailDao;
    @PostMapping("/create/order")
    public ResponseEntity<String> addOrder(@RequestBody OrderRequestDto orderRequest) {
        System.out.println(orderRequest);
        try {
            System.out.println(orderRequest);
            Long userId = orderRequest.getUser();
            String address = orderRequest.getAddress();
            Double price = orderRequest.getPrice();
            String status = orderRequest.getStatus();
            Date date = new SimpleDateFormat("dd/MM/yyyy HH mm ss").parse(orderRequest.getDate());
            int quantity = orderRequest.getQuantity();
            System.out.println(quantity);
            String payment = orderRequest.getPayment();
            String note = orderRequest.getNote();
            List<Food> FoodID = orderRequest.getFoodId();
            Users user = usersDao.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            com.poly.demo.Entity.Order order = new com.poly.demo.Entity.Order();
            order.setUser(user);
            order.setAddress(address);
            order.setDate(date);
            order.setQuantity(quantity);
            order.setNote(note);
            order.setPayment(payment);
            order.setPrice(price);
            order.setStatus(status);
            orderdao.save(order);
            for (Food food : FoodID) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setFood(food);
                System.out.println(orderDetail.getFood().toString()+"");
                System.out.println(orderDetail.getOrder().toString());
                orderDetailDao.save(orderDetail);
            }
            return ResponseEntity.ok("Order created successfully");
        } catch (ParseException e) {
            System.out.println(orderRequest);
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid date format");
        }
    }
}
