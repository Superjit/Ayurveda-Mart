package com.spring.entity;

import java.time.LocalDateTime;

public class ProductWithAddressDTO {
	
	 private Long buyProductId;
	    private String email;
	    private Long productId;
	    private String categoryName;
	    private int quantity;
	    private Double price;
	    private String paymentMethod;
	    private Long addressId;
	    private LocalDateTime buyDate;
	    private String status;
	    private LocalDateTime deliveredDate;

	   
	    private String addressEmail;
	    private Long mobileNumber;
	    private String firstName;
	    private String lastName;
	    private String street;
	    private String city;
	    private String state;
	    private String country;
	    private String postalCode;
		@Override
		public String toString() {
			return "ProductWithAddressDTO [buyProductId=" + buyProductId + ", email=" + email + ", productId="
					+ productId + ", categoryName=" + categoryName + ", quantity=" + quantity + ", price=" + price
					+ ", paymentMethod=" + paymentMethod + ", addressId=" + addressId + ", buyDate=" + buyDate
					+ ", status=" + status + ", deliveredDate=" + deliveredDate + ", addressEmail=" + addressEmail
					+ ", mobileNumber=" + mobileNumber + ", firstName=" + firstName + ", lastName=" + lastName
					+ ", street=" + street + ", city=" + city + ", state=" + state + ", country=" + country
					+ ", postalCode=" + postalCode + "]";
		}
		public ProductWithAddressDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ProductWithAddressDTO(Long buyProductId, String email, Long productId, String categoryName, int quantity,
				Double price, String paymentMethod, Long addressId, LocalDateTime buyDate, String status,
				LocalDateTime deliveredDate, String addressEmail, Long mobileNumber, String firstName, String lastName,
				String street, String city, String state, String country, String postalCode) {
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
			this.addressEmail = addressEmail;
			this.mobileNumber = mobileNumber;
			this.firstName = firstName;
			this.lastName = lastName;
			this.street = street;
			this.city = city;
			this.state = state;
			this.country = country;
			this.postalCode = postalCode;
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public LocalDateTime getDeliveredDate() {
			return deliveredDate;
		}
		public void setDeliveredDate(LocalDateTime deliveredDate) {
			this.deliveredDate = deliveredDate;
		}
		public String getAddressEmail() {
			return addressEmail;
		}
		public void setAddressEmail(String addressEmail) {
			this.addressEmail = addressEmail;
		}
		public Long getMobileNumber() {
			return mobileNumber;
		}
		public void setMobileNumber(Long mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		
	    
}
