package com.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.CustomerAddress;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, Long>{

	List<CustomerAddress> findByEmail(String email);

}
