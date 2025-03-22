package com.spring.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring.entity.DetailValue;
import com.spring.service.DetailValueService;

@RestController
@RequestMapping("/api/detail-values")
public class DetailValueController {
    private final DetailValueService service;

    public DetailValueController(DetailValueService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<List<DetailValue>> addDetailValues(@RequestBody List<DetailValue> detailValues) {
    	System.out.println(detailValues);
        List<DetailValue> savedDetails = service.addDetailValues(detailValues); // Assuming you create a method to handle list
        return ResponseEntity.ok(savedDetails);
    }
}
