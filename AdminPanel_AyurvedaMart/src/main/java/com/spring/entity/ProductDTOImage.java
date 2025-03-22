package com.spring.entity;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;


public class ProductDTOImage {
	
	private Long id;

   
    private String name;

   
    private Double price;

   
    private String status; // e.g., "Available", "Out of Stock"
    
    private Long quantity;
    
    private Long categoryId;
    
    private MultipartFile[] images;

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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImages(MultipartFile[] images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "ProductDTOImage [id=" + id + ", name=" + name + ", price=" + price + ", status=" + status
				+ ", quantity=" + quantity + ", categoryId=" + categoryId + ", images=" + Arrays.toString(images) + "]";
	}

	public ProductDTOImage(Long id, String name, Double price, String status, Long quantity, Long categoryId,
			MultipartFile[] images) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.status = status;
		this.quantity = quantity;
		this.categoryId = categoryId;
		this.images = images;
	}

	public ProductDTOImage() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
