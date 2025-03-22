package com.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.DashboardMetrics;
import com.spring.entity.Product;
import com.spring.entity.ProductWithAddressDTO;
import com.spring.repo.ProductRepository;
import com.spring.service.DashboardService;
import com.spring.service.UserPanel;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private UserPanel userPanel;

	@Autowired
	private ProductRepository productRepository;

//	@GetMapping("/dashboard/metrics")
//    public DashboardMetrics getDashboardMetrics() {
//        // Fetch the necessary data from the service
//        return dashboardService.getDashboardMetrics();
//    }

	// Endpoint to get total price by product ID
//	@GetMapping("/totalPriceByProductId/{productId}")
//	public Double getTotalPriceByProductId(@PathVariable Long productId) {
//
//		return userPanel.getTotalPriceById(productId);
//	}

	@GetMapping("/totalPriceOfProduct/{email}")
	public Double getTotalPriceBy(@PathVariable String email) {

		System.out.println(email);

		List<Long> products = productRepository.findProductIdsByEmail(email);
		
		return userPanel.getTotalPriceById(products);

//		Double totalPrice = 0.0;
//
//		for (Product product : products) {
//
//			Optional<Double> price = Optional.ofNullable(userPanel.getTotalPriceById(product.getId()));
//
//			if (!price.isEmpty()) {
//
//				System.out.println(price.get());
//				totalPrice = totalPrice + price.get();
//			}
//
//		}
//
//		return totalPrice;
	}

	// Endpoint to get the count of pending orders by product ID
	@GetMapping("/countPendingOrders/{email}")
	public Long countPendingOrdersByProductId(@PathVariable String email) {
		System.out.println("pending " + email);

		List<Long> productIds = productRepository.findProductIdsByEmail(email);

		 return userPanel.getPendingOrdersCountByProductId(productIds);
//		Long totalPending = 0l;
//
//		for (Product product : products) {
//
//			Optional<Long> pendingcount = Optional
//					.ofNullable(userPanel.getPendingOrdersCountByProductId(product.getId()));
//
//			if (!pendingcount.isEmpty()) {
//
//				System.out.println(pendingcount.get());
//				totalPending = totalPending + pendingcount.get();
//			}
//		}
//		return totalPending;
	}
	@GetMapping("/countDeliveredOrders/{email}")
	public Long countDeliveredOrdersByProductId(@PathVariable String email) {
		System.out.println("pending " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.countDeliveredOrdersByProductId(productIds);
	}
	
	
	@GetMapping("/sumQuantityOfProduct/{email}")
	public Long sumQuantityOfProduct(@PathVariable String email) {
		System.out.println("   " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.sumQuantityByProductId(productIds);
		
//		List<Product> products = productRepository.findByEmail(email);
//		
//		Long totalQuantityOfOrder = 0l;
//		
//		for (Product product : products) {
//			
//			Optional<Long> quantityOfProduct = Optional
//					.ofNullable(userPanel.sumQuantityByProductId(product.getId()));
//			
//			if (!quantityOfProduct.isEmpty()) {
//				
//				System.out.println(quantityOfProduct.get());
//				totalQuantityOfOrder = totalQuantityOfOrder + quantityOfProduct.get();
//			}
//		}
//		return totalQuantityOfOrder;
	}
	@GetMapping("/findStatisticsByProductIds/{email}")
	public String[] findStatisticsByProductIds(@PathVariable String email) {
		System.out.println("   " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.findStatisticsByProductIds(productIds);
	}
	
	
	@GetMapping("/getTotalRevenueByMonth/{email}")
	public Object[] getTotalRevenueByMonth(@PathVariable String email) {
		System.out.println("   " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.getTotalRevenueByMonth(productIds);
	}
	@GetMapping("/getTotalRevenueByDayOfWeek/{email}")
	public Object[] getTotalRevenueByDayOfWeek(@PathVariable String email) {
		System.out.println("   " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.getTotalRevenueByDayOfWeek(productIds);
	}
	
	
	@GetMapping("/findPendingProductsWithAddresses/{email}")
	public ProductWithAddressDTO[] findPendingProductsWithAddresses(@PathVariable String email) {
		System.out.println("   " + email);
		
		List<Long> productIds = productRepository.findProductIdsByEmail(email);
		
		return userPanel.findPendingProductsWithAddresses(productIds);
	}
	@PutMapping("/deliver/{id}")
	public String deliver(@PathVariable Long id) {
		System.out.println("   " + id);
		
	
		return userPanel.deliver(id);
	}
	@PutMapping("/dispatch/{id}")
	public String dispatch(@PathVariable Long id) {
		System.out.println("   " + id);
		
		
		return userPanel.dispatch(id);
	}
	
	
}
