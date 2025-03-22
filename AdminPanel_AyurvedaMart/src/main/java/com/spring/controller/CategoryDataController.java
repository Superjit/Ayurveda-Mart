package com.spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.entity.CategoryDataDTO;
import com.spring.entity.Product;
import com.spring.service.CategoryDataService;

@RestController
@RequestMapping("/api/category-data")
public class CategoryDataController {

    private final CategoryDataService service;

    public CategoryDataController(CategoryDataService service) {
        this.service = service;
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryDataDTO> getCategoryData(@PathVariable String categoryName) {
        return ResponseEntity.ok(service.getCategoryDataByName(categoryName));
    }
    
    @GetMapping("/allForuser")
    public ResponseEntity<List<CategoryDataDTO>> getAllProductDataForuser() {
        return ResponseEntity.ok(service.getAllProductDataForUser());
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDataDTO>> getAllProductData(@RequestParam("adminEmail") String email) {
    	return ResponseEntity.ok(service.getAllProductData(email));
    }
    @GetMapping("/allProductWithImage")
    public ResponseEntity<List<CategoryDataDTO>> getAllProductWithImage() {
    	return ResponseEntity.ok(service.getAllProductData());
    }
}

