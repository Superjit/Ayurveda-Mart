package com.spring.entity;



public class Product {

   
    private Long id;

 
    private String name;

 
    private Double price;

 
    private String status; // e.g., "Available", "Out of Stock"
    
    private Long quantity;
    
    private Long categoryId;

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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", status=" + status + ", quantity="
				+ quantity + ", categoryId=" + categoryId + "]";
	}
    
    
   
}
