package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByStatus(String status);

	List<Product> findByCategoryId(Long id);

	@Query("SELECT p FROM Product p ORDER BY RANDOM()")
	List<Product> findAllProductsRandomly();
	
	List<Product> findByEmail(String email);
	
	
	
	 @Query("SELECT COUNT(p) FROM Product p WHERE p.email = :email")
	 Long countProductsByEmail(@Param("email") String email);
	 
	 @Query("SELECT p.id FROM Product p WHERE p.email = :email")
	 List<Long> findProductIdsByEmail(@Param("email") String email);
}
