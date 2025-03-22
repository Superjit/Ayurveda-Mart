package com.spring.service;

import org.springframework.stereotype.Service;

import com.spring.entity.Category;
import com.spring.entity.CategoryDataDTO;
import com.spring.entity.DetailName;
import com.spring.entity.DetailValue;
import com.spring.entity.Product;
import com.spring.entity.ProductImage;
import com.spring.repo.CategoryRepository;
import com.spring.repo.DetailNameRepository;
import com.spring.repo.DetailValueRepository;
import com.spring.repo.ProductImageRepository;
import com.spring.repo.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class CategoryDataService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final DetailNameRepository detailNameRepository;
    private final DetailValueRepository detailValueRepository;
    private final ProductImageRepository productImageRepository;

    public CategoryDataService(CategoryRepository categoryRepository, ProductRepository productRepository,
                               DetailNameRepository detailNameRepository, DetailValueRepository detailValueRepository,
                               ProductImageRepository productImageRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.detailNameRepository = detailNameRepository;
        this.detailValueRepository = detailValueRepository;
        this.productImageRepository = productImageRepository;
    }

    public CategoryDataDTO getCategoryDataByName(String categoryName) {
        // Fetch category by name
        Category category = categoryRepository.findByCategoryName(categoryName);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        // Fetch related data
        List<Product> products = productRepository.findByCategoryId(category.getId());
        List<DetailName> detailNames = detailNameRepository.findByCategoryId(category.getId());
        List<DetailValue> detailValues = detailValueRepository.findByProductIdInAndDetailNameIdIn(
                products.stream().map(Product::getId).toList(),
                detailNames.stream().map(DetailName::getId).toList());
        List<ProductImage> images = productImageRepository.findByProductIdIn(
                products.stream().map(Product::getId).toList());

        // Convert images to byte arrays
        List<byte[]> imageBytes = convertImagesToByteArray(images);

        // Construct and return the DTO
        return new CategoryDataDTO(category, products, detailNames, detailValues, imageBytes);
    }

    public List<CategoryDataDTO> getAllProductDataForUser() {
    	
    	
        List<Product> products = productRepository.findAllProductsRandomly();
        Collections.shuffle(products, new Random()); 

        return products.stream().map(product -> {
            // Fetch category data based on categoryId from the product
            Category category = categoryRepository.findById(product.getCategoryId()).orElse(new Category());

            // Fetch detail names using categoryId
            List<DetailName> detailNames = detailNameRepository.findByCategoryId(product.getCategoryId());
            List<DetailValue> detailValues = detailValueRepository.findByProductId(product.getId());;

            // Fetch detail values based on detail name IDs
//            for (DetailName detailname : detailNames) {
//                detailValues = detailValueRepository.findByDetailNameId(detailname.getId());
//                System.out.println(detailname);
//            }

            // Fetch product images and convert the file paths into byte arrays
            List<ProductImage> images = productImageRepository.findByProductId(product.getId());
            List<byte[]> imageBytes = convertImagesToByteArray(images);

            // Return the DTO with the category, product, details, and image byte data
            return new CategoryDataDTO(
                    category,
                    List.of(product),
                    detailNames,
                    detailValues,
                    imageBytes // Set the image byte arrays in the DTO
            );
        }).toList();
    }
    public List<CategoryDataDTO> getAllProductData(String email) {
    	
    	
    	List<Product> products = productRepository.findByEmail(email);
    	
    	return products.stream().map(product -> {
    		// Fetch category data based on categoryId from the product
    		Category category = categoryRepository.findById(product.getCategoryId()).orElse(new Category());
    		
    		// Fetch detail names using categoryId
    		List<DetailName> detailNames = detailNameRepository.findByCategoryId(product.getCategoryId());
    		List<DetailValue> detailValues = detailValueRepository.findByProductId(product.getId());;
    		
    		// Fetch detail values based on detail name IDs
//            for (DetailName detailname : detailNames) {
//                detailValues = detailValueRepository.findByDetailNameId(detailname.getId());
//                System.out.println(detailname);
//            }
    		
    		// Fetch product images and convert the file paths into byte arrays
    		List<ProductImage> images = productImageRepository.findByProductId(product.getId());
    		List<byte[]> imageBytes = convertImagesToByteArray(images);
    		
    		// Return the DTO with the category, product, details, and image byte data
    		return new CategoryDataDTO(
    				category,
    				List.of(product),
    				detailNames,
    				detailValues,
    				imageBytes // Set the image byte arrays in the DTO
    				);
    	}).toList();
    }
    public List<CategoryDataDTO> getAllProductData() {
    	
    	
    	List<Product> products = productRepository.findAll();
    	
    	   Collections.shuffle(products);
    	
    	return products.stream().map(product -> {
    		// Fetch category data based on categoryId from the product
    		Category category = categoryRepository.findById(product.getCategoryId()).orElse(new Category());
    		
    		// Fetch detail names using categoryId
    		List<DetailName> detailNames = detailNameRepository.findByCategoryId(product.getCategoryId());
    		List<DetailValue> detailValues = detailValueRepository.findByProductId(product.getId());;
    		
    		// Fetch detail values based on detail name IDs
//            for (DetailName detailname : detailNames) {
//                detailValues = detailValueRepository.findByDetailNameId(detailname.getId());
//                System.out.println(detailname);
//            }
    		
    		// Fetch product images and convert the file paths into byte arrays
    		List<ProductImage> images = productImageRepository.findByProductId(product.getId());
    		List<byte[]> imageBytes = convertImagesToByteArray(images);
    		
    		// Return the DTO with the category, product, details, and image byte data
    		return new CategoryDataDTO(
    				category,
    				List.of(product),
    				detailNames,
    				detailValues,
    				imageBytes // Set the image byte arrays in the DTO
    				);
    	}).toList();
    }

    public CategoryDataDTO getCategoryDataByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElse(new Product());
        if (product != null) {
            Category category = categoryRepository.findById(product.getCategoryId()).orElse(new Category());
            List<DetailName> detailNames = detailNameRepository.findByCategoryId(category.getId());
            List<DetailValue> detailValues = detailValueRepository.findByProductId(product.getId());
           
            List<ProductImage> images = productImageRepository.findByProductId(productId);

            // Convert images to byte arrays
            List<byte[]> imageBytes = convertImagesToByteArray(images);

            return new CategoryDataDTO(category, List.of(product), detailNames, detailValues, imageBytes);
        }
        return null;
    }

    // Helper method to convert images to byte arrays
    private List<byte[]> convertImagesToByteArray(List<ProductImage> images) {
        List<byte[]> imageBytes = new ArrayList<>();
        for (ProductImage productImage : images) {
            try {
                Path imagePath = Path.of(productImage.getImageData()); // Assuming imageData contains the file path
                byte[] imageData = Files.readAllBytes(imagePath); // Convert the image file into a byte array
                imageBytes.add(imageData); // Add the byte array to the list
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception if the file cannot be read
            }
        }
        return imageBytes;
    }
    
    public List<CategoryDataDTO> getAllProductWithImage() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
           
            // Fetch product images and convert the file paths into byte arrays
            List<ProductImage> images = productImageRepository.findByProductId(product.getId());
            List<byte[]> imageBytes = convertImagesToByteArray(images);

            // Return the DTO with the category, product, details, and image byte data
            return new CategoryDataDTO(
                    List.of(product),
                    imageBytes // Set the image byte arrays in the DTO
            );
        }).toList();
    }
}
