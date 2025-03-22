package com.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.ImageData;
import com.spring.entity.Product;
import com.spring.entity.ProductImage;
import com.spring.service.ProductImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImages(
            @RequestParam("images") MultipartFile[] images,
            @RequestParam("productId") Long productId) {
        
        if (images == null || images.length == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No images provided for upload.");
        }

        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product ID is required.");
        }

        try {
            productImageService.saveImages(images, productId);
            return ResponseEntity.status(HttpStatus.OK).body("Images uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload images: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/images/{productId}")
    public ResponseEntity<List<ProductImage>> getProductImageUpdateById(@PathVariable Long productId) {
    	try {
    		System.out.println("image/{productId}"+productId);
    		List<ProductImage> productImage = productImageService.getProductImagesByProductIds(productId);
    		System.out.println(productImageService.getProductImagesByProductIds(productId));
    		return ResponseEntity.ok(productImageService.getProductImagesByProductIds(productId));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    }
    
    @GetMapping("/image/{productId}")
    public ResponseEntity<List<ImageData>> getProductImageById(@PathVariable Long productId) {
    	try {
    		System.out.println("image/{productId}"+productId);
    		List<ImageData> productImage = productImageService.getProductImageByProductId(productId);
    		System.out.println(productImageService.getProductImageByProductId(productId));
    		return ResponseEntity.ok(productImageService.getProductImageByProductId(productId));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    }
    @PutMapping("/update/{productId}/{imageId}")
    public ResponseEntity<String> updateProductImage(
            @PathVariable Long productId,
            @PathVariable Long imageId,
            @RequestParam("image") MultipartFile image) {
        try {
            productImageService.updateProductImage(productId, imageId, image);
            return ResponseEntity.ok("Image updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update image: " + e.getMessage());
        }
    }
}
