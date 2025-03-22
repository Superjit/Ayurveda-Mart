package com.spring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "category777")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,  length = 100)
    private String email;
    
    @Column(length = 50)
    private String categoryName;
    @Column(length = 150)
    private String description;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime categoryAddedAt;

    @PrePersist
    protected void onCreate() {
    	if (this.categoryAddedAt == null) {
    		this.categoryAddedAt = LocalDateTime.now();
    	}
    }
  
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	@Override
	public String toString() {
		return "Category [id=" + id + ", email=" + email + ", categoryName=" + categoryName + ", description="
				+ description + "]";
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Long id, String email, String categoryName, String description) {
		super();
		this.id = id;
		this.email = email;
		this.categoryName = categoryName;
		this.description = description;
	}

	
	
	
	
}
