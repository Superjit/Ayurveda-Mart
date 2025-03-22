package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


public class BuyProduct {

	private Long buyProductId;
	

	private String email;


	private Long productId;
	

	private String categoryName;
	

	private int quantity;
	

	private Double price;
	

	private String paymentMethod;
	
	
	 
	private Long addressId;
	

    private LocalDateTime buyDate;
	
	

	private String status ;

	// Getter and Setter for status
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}
	
	
	
	@Column(nullable = true)
	private LocalDateTime deliveredDate;

	// Getter and Setter for deliveredDate
	public LocalDateTime getDeliveredDate() {
	    return deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
	    this.deliveredDate = deliveredDate;
	}




	@Override
	public String toString() {
		return "BuyProduct [buyProductId=" + buyProductId + ", email=" + email + ", productId=" + productId
				+ ", categoryName=" + categoryName + ", quantity=" + quantity + ", price=" + price + ", paymentMethod="
				+ paymentMethod + ", addressId=" + addressId + ", buyDate=" + buyDate + ", status=" + status
				+ ", deliveredDate=" + deliveredDate + "]";
	}

	public BuyProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public BuyProduct(Long buyProductId, String email, Long productId, String categoryName, int quantity, Double price,
			String paymentMethod, Long addressId, LocalDateTime buyDate, String status, LocalDateTime deliveredDate) {
		super();
		this.buyProductId = buyProductId;
		this.email = email;
		this.productId = productId;
		this.categoryName = categoryName;
		this.quantity = quantity;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.addressId = addressId;
		this.buyDate = buyDate;
		this.status = status;
		this.deliveredDate = deliveredDate;
	}

	public Long getBuyProductId() {
		return buyProductId;
	}

	public void setBuyProductId(Long buyProductId) {
		this.buyProductId = buyProductId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public LocalDateTime getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(LocalDateTime buyDate) {
		this.buyDate = buyDate;
	}

	 @PrePersist
	    private void setBuyDate() {
	        if (this.buyDate == null) {
	            this.buyDate = LocalDateTime.now();
	        }
	    }

	
}
