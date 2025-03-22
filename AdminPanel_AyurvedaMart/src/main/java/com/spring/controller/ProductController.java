package com.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Category;
import com.spring.entity.CategoryDataDTO;
import com.spring.entity.Product;
import com.spring.service.CategoryService;
import com.spring.service.ProductService;

@RestController
@RequestMapping("/admin/products")

public class ProductController {
	 
	 private final ProductService service;

	    public ProductController(ProductService service) {
	        this.service = service;
	    }

	    @PostMapping("/add")
	    public ResponseEntity<Product> addProduct(@RequestParam("categoryName") String categoryName ,@RequestParam("adminEmail") String email,@RequestBody Product product) {
	    	System.out.println(categoryName);
	    	product.setEmail(email);
	    	System.out.println(product);
	        return ResponseEntity.ok(service.addProduct(categoryName,product));
	    }
	    @PostMapping("/addUser")
	    public ResponseEntity<Product> addProduct(@RequestParam("categoryName") String categoryName ,@RequestBody Product product) {
	    	System.out.println(categoryName);
	    	System.out.println(product);
	    	
	    	return ResponseEntity.ok(service.addProductForUser(categoryName,product));
	    }
	   
	    @DeleteMapping("/{productId}")
	    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
	        try {
	        	service.deactivateProduct(productId);
	            return ResponseEntity.ok("Product deactivated successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	        }
	    }
	    
	    @PutMapping("/{productId}")
	    public ResponseEntity<CategoryDataDTO> updateProduct(@PathVariable Long productId, @RequestBody CategoryDataDTO categoryDataDTO) {
	    	System.out.println(categoryDataDTO.getProduct());
	    	System.out.println(categoryDataDTO.getCategory());
	    	System.out.println(categoryDataDTO.getDetailNames());
	    	System.out.println(categoryDataDTO.getDetailValues());
	    	
			
	        try {
	        	CategoryDataDTO categoryDataDTO2 = service.updateProduct(categoryDataDTO);
	            return ResponseEntity.ok(categoryDataDTO2);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
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
	   
	    
	    
	    
	    
}

