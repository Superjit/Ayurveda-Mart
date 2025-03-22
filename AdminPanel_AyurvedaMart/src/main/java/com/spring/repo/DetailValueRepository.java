package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.DetailValue;

public interface DetailValueRepository extends JpaRepository<DetailValue, Long>{

//	List<DetailValue> findByCategoryId(Long id);

	List<DetailValue> findByProductIdInAndDetailNameIdIn(List<Long> list, List<Long> list2);

	List<DetailValue> findByProductId(Long id);
	
	List<DetailValue> findByDetailNameId(Long detailNameId);

}
