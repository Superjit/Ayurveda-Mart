package com.spring.controller;



import com.spring.entity.CustomerAddress;
import com.spring.services.CustomerAddressService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/address")
@CrossOrigin("*")
public class CustomerAddressController {

    @Autowired
    private CustomerAddressService customerAddressService;

    // Get address by email
    @GetMapping
    public ResponseEntity<List<CustomerAddress>> getCustomerAddress(@RequestParam String email) {
        List<CustomerAddress> address = customerAddressService.getAddressByEmail(email);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Save customer address
    @PostMapping
    public ResponseEntity<CustomerAddress> saveCustomerAddress(@RequestBody CustomerAddress customerAddress) {
        CustomerAddress savedAddress = customerAddressService.saveAddress(customerAddress);
        return ResponseEntity.ok(savedAddress);
    }
}
