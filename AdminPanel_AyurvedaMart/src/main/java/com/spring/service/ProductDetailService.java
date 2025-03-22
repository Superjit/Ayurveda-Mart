package com.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.Category;
import com.spring.entity.Product;
import com.spring.entity.DetailName;
import com.spring.repo.CategoryRepository;
import com.spring.repo.ProductDetailRepository;
import com.spring.repo.ProductRepository;

@Service
public class ProductDetailService {
	
	  private final CategoryRepository categoryRepository;
	    private final ProductDetailRepository productDetailRepository;
	    private final ProductRepository productRepository;

	    // Constructor Injection
	    public ProductDetailService(CategoryRepository categoryRepository,
	                                 ProductDetailRepository productDetailRepository,
	                                 ProductRepository productRepository) {
	        this.categoryRepository = categoryRepository;
	        this.productDetailRepository = productDetailRepository;
	        this.productRepository = productRepository;
	    }

//	  
}
