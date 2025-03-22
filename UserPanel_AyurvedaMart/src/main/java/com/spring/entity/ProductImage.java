package com.spring.entity;


public class ProductImage {

    private Long id;

    private String imageData;

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
