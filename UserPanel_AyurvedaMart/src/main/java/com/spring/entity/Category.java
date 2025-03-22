package com.spring.entity;



public class Category {

	private Long id;
	private String categoryName;

	private String description;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category(Long id, String categoryName, String description) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.description = description;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", description=" + description + "]";
	}

}
