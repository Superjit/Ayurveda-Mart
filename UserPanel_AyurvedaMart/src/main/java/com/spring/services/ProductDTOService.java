package com.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.entity.CategoryDataDTO;
import com.spring.entity.Product;
import com.spring.entity.ProductImage;

@Service
public class ProductDTOService {

	private final RestTemplate restTemplate;

	@Value("${service.a.url:http://localhost:8085}") // Inject Service A URL from application properties
	private String serviceAUrl;

	// Constructor injection for RestTemplate and OrderRepository
	public ProductDTOService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();

	}

	// Fetch customer details from Service A
	public List<CategoryDataDTO> getAllProducts() {
		String url = serviceAUrl + "/api/category-data/allForuser";
		return List.of(restTemplate.getForObject(url, CategoryDataDTO[].class));
	}

	public List<CategoryDataDTO> getAllProductsWithImage() {
		String url = serviceAUrl + "/api/category-data/allProductWithImage";
		return List.of(restTemplate.getForObject(url, CategoryDataDTO[].class));
	}

	public Product getProductById(Long productId) {
		String url = serviceAUrl + "/admin/products/" + productId;
		return restTemplate.getForObject(url, Product.class);
	}

	public Product updateProductQuantity(String categoryName, Product product) {
		System.out.println(categoryName);
		System.out.println(product);

		String url = serviceAUrl + "/admin/products/addUser?categoryName=" + categoryName;

		// Set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create the HTTP entity with the product as the request body
		HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);

		// Use RestTemplate to send the POST request
		ResponseEntity<Product> response = restTemplate.postForEntity(url, requestEntity, Product.class);

		// Return the Product object from the response
		return response.getBody();
	}

	public List<ProductImage> getProductImageById(Long id) {
		// TODO Auto-generated method stub
		String url = serviceAUrl + "/api/product-images/images/" + id;
		ResponseEntity<ProductImage[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
				ProductImage[].class);
		System.out.println(List.of(response.getBody()));
		return List.of(response.getBody());
//		        if (response.getStatusCode() == HttpStatus.OK) {
//		            return List.of(response.getBody());
//		        } else {
//		            throw new RuntimeException("Failed to fetch product image data");
//		        }
	}



}
