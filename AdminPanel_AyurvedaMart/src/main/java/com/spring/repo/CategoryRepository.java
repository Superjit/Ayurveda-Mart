package com.spring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Category;
import java.util.List;


public interface CategoryRepository   extends JpaRepository<Category, Long>{

//	@Query(value = "SELECT id FROM Category WHERE categoryName =:categoryName", nativeQuery = true)
    Category findByCategoryName( String categoryName);
	
	@Query(value = "SELECT id FROM category777 WHERE category_name =:categoryName", nativeQuery = true)
    Long findByCategoryName1(@Param("categoryName") String categoryName);
	
	List<Category> findByEmail(String email);
	
}
