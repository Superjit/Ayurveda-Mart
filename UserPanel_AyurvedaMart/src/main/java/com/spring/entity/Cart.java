package com.spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
@Entity
@Table(name = "cart_data")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cartId; 

	@Column(length = 100)
	private String user_email;
	
	
	private Long productId;
	
	private double price;
	
	private int quantity = 1;
	
	@Column(nullable = false, updatable = false)
    private LocalDateTime productAddedDate;

	

	 @PrePersist
	    private void setBuyDate() {
	        if (this.productAddedDate == null) {
	            this.productAddedDate = LocalDateTime.now();
	        }
	    }



	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", user_email=" + user_email + ", productId=" + productId + ", price=" + price
				+ ", quantity=" + quantity + ", productAddedDate=" + productAddedDate + "]";
	}



	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Cart(Long cartId, String user_email, Long productId, double price, int quantity,
			LocalDateTime productAddedDate) {
		super();
		this.cartId = cartId;
		this.user_email = user_email;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.productAddedDate = productAddedDate;
	}



	public Long getCartId() {
		return cartId;
	}



	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}



	public String getUser_email() {
		return user_email;
	}



	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}



	public Long getProductId() {
		return productId;
	}



	public void setProductId(Long productId) {
		this.productId = productId;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public LocalDateTime getProductAddedDate() {
		return productAddedDate;
	}



	public void setProductAddedDate(LocalDateTime productAddedDate) {
		this.productAddedDate = productAddedDate;
	}

	 
	
}
