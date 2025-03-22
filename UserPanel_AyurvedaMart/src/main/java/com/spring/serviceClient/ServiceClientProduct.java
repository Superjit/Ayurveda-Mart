package com.spring.serviceClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.entity.CategoryDataDTO;



@FeignClient(name = "ADMINPANEL_AYURVEDAMART", url = "${service.a.url:http://localhost:8085}")
public interface ServiceClientProduct {
	
//	  @GetMapping("/api/category-data/all") // Endpoint of the product service
//	    List<CategoryDataDTO> getActiveProducts();
//	
//	  @GetMapping("/api/category-data/allProductWithImage") // Endpoint of the product service
//	  List<CategoryDataDTO> getActiveProductsWithImage();
//	  

}
