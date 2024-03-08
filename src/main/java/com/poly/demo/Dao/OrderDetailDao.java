package com.poly.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.demo.Entity.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Long> {

	
	
}
