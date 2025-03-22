package com.spring.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductImage22232")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500)
    private String imageData;

    @Column(nullable = false)
    private Long productId; // Store productId directly without mapping

    // Constructors
    public ProductImage() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductImage(Long id, String imageData, Long productId) {
		super();
		this.id = id;
		this.imageData = imageData;
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", imageData=" + imageData + ", productId=" + productId + "]";
	}

	
   
}
