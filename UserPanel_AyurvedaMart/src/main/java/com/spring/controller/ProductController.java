package com.spring.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.CategoryDataDTO;
import com.spring.entity.Product;
import com.spring.repo.BuyProductRepo;
import com.spring.services.ProductDTOService;

@RestController
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductDTOService service;
	
	private BuyProductRepo repo;
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<CategoryDataDTO>> getProductsAll() {
		
		List<CategoryDataDTO> categoryDataDTOs=service.getAllProducts();
		
		System.out.println(categoryDataDTOs);
		return ResponseEntity.ok(categoryDataDTOs);
		
	}
	@GetMapping("/allProductWithImage")
	public ResponseEntity<List<CategoryDataDTO>> getProductWithImage() {
		
		List<CategoryDataDTO> categoryDataDTOs=service.getAllProductsWithImage();
		System.out.println(categoryDataDTOs);
		return ResponseEntity.ok(categoryDataDTOs);
		
	}
	
	@GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        try {
        	Product product = service.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	 
//	@GetMapping("/totalPriceByProductId/{id}")
//	public Double gettotalPriceByProductId(@PathVariable Long id) {
//		
//		return repo.findTotalPriceByProductId(id);
//	}
	
	

}
