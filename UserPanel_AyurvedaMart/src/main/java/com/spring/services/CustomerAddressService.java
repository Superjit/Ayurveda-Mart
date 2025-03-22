package com.spring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.entity.CustomerAddress;
import com.spring.repo.CustomerAddressRepo;

@Service
public class CustomerAddressService {
	
	
	private final CustomerAddressRepo customerAddressRepo;

	public CustomerAddressService(CustomerAddressRepo customerAddressRepo) {
		super();
		this.customerAddressRepo = customerAddressRepo;
	}
	
	 // Get address by email
    public List<CustomerAddress> getAddressByEmail(String email) {
        return customerAddressRepo.findByEmail(email);
    }

    // Save address
    public CustomerAddress saveAddress(CustomerAddress customerAddress) {
        return customerAddressRepo.save(customerAddress);
    }

    public CustomerAddress getAddressById(Long addressId) {
        return customerAddressRepo.findById(addressId).orElse(new CustomerAddress());
    }
	
	

}
