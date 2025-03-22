package com.spring.entity;

import java.util.List;

public class OrderDTO {
	
	private List<Product> products;
	private List<BuyProduct> buyProducts;
	private List<byte[]> images;
	private List<CustomerAddress> addresses;
	@Override
	public String toString() {
		return "OrderDTO [products=" + products + ", buyProducts=" + buyProducts + ", images=" + images + ", addresses="
				+ addresses + "]";
	}
	public OrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDTO(List<Product> products, List<BuyProduct> buyProducts, List<byte[]> images,
			List<CustomerAddress> addresses) {
		super();
		this.products = products;
		this.buyProducts = buyProducts;
		this.images = images;
		this.addresses = addresses;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<BuyProduct> getBuyProducts() {
		return buyProducts;
	}
	public void setBuyProducts(List<BuyProduct> buyProducts) {
		this.buyProducts = buyProducts;
	}
	public List<byte[]> getImages() {
		return images;
	}
	public void setImages(List<byte[]> images) {
		this.images = images;
	}
	public List<CustomerAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}
	
	


}
