package com.spring.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="DetailValue22" )
public class DetailValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 300)
    private String value;

   
    
    private Long detailNameId;
    private Long productId;
   

    
    
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "DetailValue [id=" + id + ", value=" + value + ", detailNameId=" + detailNameId + ", productId="
				+ productId + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public Long getDetailNameId() {
		return detailNameId;
	}
	public void setDetailNameId(Long detailNameId) {
		this.detailNameId = detailNameId;
	}

    // Getters and Setters
    
}
