package com.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.entity.Category;
import com.spring.entity.CategoryDataDTO;
import com.spring.entity.DetailName;
import com.spring.entity.DetailValue;
import com.spring.entity.Product;
import com.spring.repo.CategoryRepository;
import com.spring.repo.DetailValueRepository;
import com.spring.repo.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository repository;
	private final CategoryRepository categoryRepository;
	private final DetailValueRepository detailValueRepository;
	

    

  

    
	public ProductService(ProductRepository repository, CategoryRepository categoryRepository,
			DetailValueRepository detailValueRepository) {
		super();
		this.repository = repository;
		this.categoryRepository = categoryRepository;
		this.detailValueRepository = detailValueRepository;
	}

	public Product addProductForUser(String categoryName,Product product) {
		Product product2=repository.findById(product.getId()).orElse(new Product());
		product.setEmail(product2.getEmail());
		Long id =categoryRepository.findByCategoryName1(categoryName);
		System.out.println(id);
		product.setStatus("ACTIVE");
		product.setCategoryId(id);
        return repository.save(product);
    }
	public Product addProduct(String categoryName,Product product) {
		
		Long id =categoryRepository.findByCategoryName1(categoryName);
		System.out.println(id);
		product.setStatus("ACTIVE");
		product.setCategoryId(id);
		return repository.save(product);
	}
	
	 public Product deactivateProduct(Long productId) {
	        Optional<Product> productOptional = repository.findById(productId);
	        if (productOptional.isPresent()) {
	            Product product = productOptional.get();
	            product.setStatus("INACTIVE");
	            return repository.save(product);
	        } else {
	            throw new RuntimeException("Product not found");
	        }
	    }
	
	 public CategoryDataDTO updateProduct(CategoryDataDTO categoryDataDTO) {
		 
//		 	System.out.println(categoryDataDTO.getProduct());
//	    	System.out.println(categoryDataDTO.getCategory());
//	    	System.out.println(categoryDataDTO.getDetailNames());
//	    	System.out.println(categoryDataDTO.getDetailValues());
	    	
	    	Product product = categoryDataDTO.getProduct();
	    	 Product product2 =repository.findById(product.getId()).orElse(new Product());
	    	 product.setEmail(product2.getEmail());
	    	product.setCategoryId(categoryDataDTO.getCategory().getId());
	    	Long categoryId = categoryDataDTO.getCategory().getId();
//	    	List<DetailName> detailNames= categoryDataDTO.getDetailNames(); 
	    	List<DetailValue> detailValues= categoryDataDTO.getDetailValues(); 
	    	for (DetailValue detailValue : detailValues) {
				detailValue.setProductId(product.getId());
			}
	    	
	    	System.out.println(detailValues);
	    	
	    	List<DetailValue> detailValues2= detailValueRepository.saveAll(detailValues);
	    	repository.save(product);
	    	
	    	System.out.println(product);
	    	
	    	categoryDataDTO.setDetailValues(detailValues2);
	    	categoryDataDTO.setProduct(product);
	    	
			return categoryDataDTO;
		
			
	 }

	public Product getProductById(Long productId) {
		
		Product products =repository.findById(productId).orElse(new Product());
		
		
		return products;
	}

	public List<Product> getProductsByIds(List<Long> ids) {
	    return repository.findAllById(ids); // Assuming `productRepository` is a JPA repository
	}

	
	 
}

