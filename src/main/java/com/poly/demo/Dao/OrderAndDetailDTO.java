package com.poly.demo.Dao;

import java.util.Date;

import com.poly.demo.Entity.Order;
import com.poly.demo.Entity.OrderDetail;

import lombok.Data;

@Data
public class OrderAndDetailDTO {
    private Long orderId;
    private Long orderDetailId;
    private Double orderPrice;  // Assuming this is the price from OrderDetail
    private String orderStatus; // Assuming this is the status from OrderDetail
    private String orderPayment; // Assuming this is the payment from OrderDetail
    private String orderNote;    // Assuming this is the note from OrderDetail
    private String orderAddress; // Assuming this is the address from Order
    private Date orderDate;      // Assuming this is the date from Order
    private int orderQuantity;   // Assuming this is the quantity from Order

    public OrderAndDetailDTO(Order order, OrderDetail orderDetail) {
        this.orderId = order.getId();
        this.orderDetailId = orderDetail.getId();
        this.orderPrice = order.getPrice();
        this.orderStatus = order.getStatus();
        this.orderPayment = order.getPayment();
        this.orderNote = order.getNote();
        this.orderAddress = order.getAddress();
        this.orderDate = order.getDate();
        this.orderQuantity = order.getQuantity();
    }

	public OrderAndDetailDTO() {
		super();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderPayment() {
		return orderPayment;
	}

	public void setOrderPayment(String orderPayment) {
		this.orderPayment = orderPayment;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
    
}

