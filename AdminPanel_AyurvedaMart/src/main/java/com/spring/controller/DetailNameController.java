package com.spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.entity.DetailName;
import com.spring.service.DetailNameService;

@RestController
@RequestMapping("/api/details")
public class DetailNameController {
    private final DetailNameService detailNameService;

    public DetailNameController(DetailNameService detailNameService) {
        this.detailNameService = detailNameService;
    }

    @PostMapping
    public ResponseEntity<DetailName> addDetailName(@RequestBody DetailName detailName) {
        return ResponseEntity.ok(detailNameService.addDetailName(detailName));
    }
    
    // New endpoint to handle a bulk insert
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<DetailName>> addDetailNames(
        @RequestBody List<DetailName> detailNames,
        @RequestParam String categoryName
    ) {
        List<DetailName> savedDetails = detailNameService.addDetailNames(detailNames, categoryName);
        return ResponseEntity.ok(savedDetails);
    }
    
    @GetMapping("/category")
    public ResponseEntity<List<DetailName>> getDetailNamesByCategoryId(@RequestParam Long categoryId) {
    	
        List<DetailName> detailNames = detailNameService.getDetailNamesByCategoryId(categoryId);
        return ResponseEntity.ok(detailNames);
    }
    @GetMapping("/categoryName")
    public ResponseEntity<List<DetailName>> getDetailNamesByCategoryName(@RequestParam String categoryName) {
    	
    	List<DetailName> detailNames = detailNameService.getDetailNamesByCategoryName(categoryName);
    	return ResponseEntity.ok(detailNames);
    }
}
