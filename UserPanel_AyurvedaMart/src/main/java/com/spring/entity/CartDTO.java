package com.spring.entity;

import java.util.List;

public class CartDTO {
	
	private List<Cart> carts;
	
	private List<Product> products;
	
	 private List<byte[]> images;
	@Override
	public String toString() {
		return "CartDTO [carts=" + carts + ", products=" + products + ", images=" + images + "]";
	}
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(List<Cart> carts, List<Product> products, List<byte[]> images) {
		super();
		this.carts = carts;
		this.products = products;
		this.images = images;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<byte[]> getImages() {
		return images;
	}
	public void setImages(List<byte[]> images) {
		this.images = images;
	} 
	
	 
	 

}
