package com.spring.entity;



import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_data777")
public class Product {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false,  length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 10)
    private String status; // e.g., "Available", "Out of Stock"
    
    private Long quantity;
    
    private Long categoryId;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime productAddedat;

    @PrePersist 
    protected void onCreate() {
    	if (this.productAddedat == null) {
    		this.productAddedat = LocalDateTime.now();
    	}
    }
 
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Long id, String email, String name, Double price, String status, Long quantity, Long categoryId) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.price = price;
		this.status = status;
		this.quantity = quantity;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", email=" + email + ", name=" + name + ", price=" + price + ", status=" + status
				+ ", quantity=" + quantity + ", categoryId=" + categoryId + "]";
	}

	
    
   
}
