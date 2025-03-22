package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Category;
import com.spring.entity.DetailName;


public interface ProductDetailRepository extends JpaRepository<DetailName, Long> {

//	List<ProductDetail> findByCategory(Category category);
	

}

