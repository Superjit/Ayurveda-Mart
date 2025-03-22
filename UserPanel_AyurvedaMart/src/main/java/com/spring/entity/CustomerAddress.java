package com.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 100)
	private String email;
	
	
	private Long mobileNumber;
	
	@Column(length = 50)
	private String firstName;
	
	@Column(length = 50)
	private String lastName;

	@Column(length = 100)
	private String street;

	@Column(length = 20)
	private String city;

	@Column(length = 20)
	private String state;

	@Column(length = 20)
	private String country;

	@Column(length = 10)
	private String postalCode;

	@Override
	public String toString() {
		return "CustomerAddress [id=" + id + ", email=" + email + ", mobileNumber=" + mobileNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", postalCode=" + postalCode + "]";
	}

	public CustomerAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerAddress(Long id, String email, Long mobileNumber, String firstName, String lastName, String street,
			String city, String state, String country, String postalCode) {
		super();
		this.id = id;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
