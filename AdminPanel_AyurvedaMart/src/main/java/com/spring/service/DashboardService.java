package com.spring.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.DashboardMetrics;
import com.spring.repo.ProductRepository;

@Service
public class DashboardService {

  
    @Autowired
    private ProductRepository productRepository;

//    public DashboardMetrics getDashboardMetrics() {
//        // Total number of orders
//        long totalOrders = buyProductRepository.count();
//
//        // Total number of delivered orders
//        long totalDelivered = buyProductRepository.countByStatus("Delivered");
//
//        // Total number of canceled orders
//        long totalCanceled = buyProductRepository.countByStatus("Canceled");
//
//        // Total revenue (sum of prices for all orders)
//        double totalRevenue = buyProductRepository.sumTotalRevenue();
//
//        return new DashboardMetrics(totalOrders, totalDelivered, totalCanceled, totalRevenue);
//    }
}
