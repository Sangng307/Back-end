package com.poly.demo.Dao;

import java.util.List;

import com.poly.demo.Entity.Food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderRequestDto {
	private Long user;
    private String address;
    private Double price;
    private String status;
    private List<Food> foodId;
    private String date;
    private int quantity;
    private String payment;
    private String note;
	public OrderRequestDto(Long user, String address, Double price, String status, List<Food> foodId, String date,
			int quantity, String payment, String note) {
		super();
		this.user = user;
		this.address = address;
		this.price = price;
		this.status = status;
		this.foodId = foodId;
		this.date = date;
		this.quantity = quantity;
		this.payment = payment;
		this.note = note;
	}
	public OrderRequestDto() {
		super();
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Food> getFoodId() {
		return foodId;
	}
	public void setFoodId(List<Food> foodId) {
		this.foodId = foodId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
    
    
}