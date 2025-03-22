package com.spring.controller;

import com.spring.entity.Category;
import com.spring.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

	 private final CategoryService categoryService;

	    public CategoryController(CategoryService categoryService) {
	        this.categoryService = categoryService;
	    }

	    // Add a new category
	    @PostMapping("/add")
	    public ResponseEntity<Category> addCategory(@RequestParam("adminEmail") String email ,@RequestBody Category category) {
	    	category.setEmail(email);
	        Category savedCategory = categoryService.addCategory(category);
	        return ResponseEntity.ok(savedCategory);
	    }

	    // Get all categories
	    @GetMapping("/data")
	    public ResponseEntity<List<Category>> getAllCategories(@RequestParam("adminEmail") String email) {
	        List<Category> categories = categoryService.getAllCategories(email);
	        return ResponseEntity.ok(categories);
	    }

	    // Get category by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
	        Category category = categoryService.getCategoryById(id);
	        return ResponseEntity.ok(category);
	    }

	    // Update an existing category
	    @PutMapping("/{id}")
	    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
	        Category updatedCategory = categoryService.updateCategory(id, category);
	        return ResponseEntity.ok(updatedCategory);
	    }

	    // Delete a category (logical delete if needed)
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
	        categoryService.deleteCategory(id);
	        return ResponseEntity.ok("Category with ID " + id + " has been deleted successfully.");
	    }
	    @GetMapping("/name/{email}")
	    public ResponseEntity<List<String>> getAllCategoryNames(@PathVariable String email) {
	        List<String> categoryNames = categoryService.getAllCategories(email)
	                .stream()
	                .map(Category::getCategoryName) // Extract category names
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(categoryNames);
	    }
}
