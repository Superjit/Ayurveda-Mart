package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	List<ProductImage> findByProductIdIn(List<Long> productIds);

	List<ProductImage> findByProductId(Long id);
}
