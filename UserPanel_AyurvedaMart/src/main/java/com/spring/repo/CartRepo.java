package com.spring.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Cart;

public interface CartRepo  extends JpaRepository<Cart, Long>{

	 @Query("SELECT COUNT(c) FROM Cart c WHERE c.user_email = :email")
	    long countByUser_email(String email);
	 
	 @Query("SELECT c FROM Cart c WHERE c.user_email = :user_email")
	 List<Cart> findByUser_email(@Param("user_email") String user_email);
	 
	 
	 @Query("SELECT c FROM Cart c WHERE c.productId = :productId AND c.user_email = :userEmail")
	 Optional<Cart> findByProductIdAndUser_email(@Param("productId") Long productId, @Param("userEmail") String userEmail);
	 
	 	
	
}