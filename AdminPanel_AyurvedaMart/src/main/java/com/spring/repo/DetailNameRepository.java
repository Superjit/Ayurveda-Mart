package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.DetailName;

public interface DetailNameRepository extends JpaRepository<DetailName, Long>{

	List<DetailName> findByCategoryId(Long categoryId);



	

}
