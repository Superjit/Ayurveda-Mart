package com.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.BuyProduct;
import com.spring.entity.Cart;
import com.spring.entity.CartDTO;
import com.spring.entity.CustomerAddress;
import com.spring.entity.OrderDTO;
import com.spring.entity.Product;
import com.spring.entity.ProductImage;
import com.spring.entity.ProductWithAddressDTO;
import com.spring.repo.BuyProductRepo;
import com.spring.services.BuyProductService;
import com.spring.services.CustomerAddressService;
import com.spring.services.ProductDTOService;

@RestController
@CrossOrigin("*")
@RequestMapping("/buyProduct")
public class BuyProductController {

	@Autowired
	private BuyProductService buyProductService;

	@Autowired
	private ProductDTOService service;

	@Autowired
	private CustomerAddressService addressService;

	@Autowired
	private BuyProductRepo repo;

	@PostMapping("/buy")
	public ResponseEntity<String> placeOrder(@RequestBody BuyProduct buyProduct) {

		System.out.println(buyProduct);
		
		
		try {
			// Assuming the service method will handle saving the BuyProduct and associating
			// it with the logged-in user
			boolean isOrderPlaced = buyProductService.placeOrder(buyProduct);

			if (isOrderPlaced) {
				return new ResponseEntity<>("Order placed successfully!", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Failed to place the order.", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error placing order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/cartbuy")
	public ResponseEntity<String> placeCartOrder(@RequestBody List<BuyProduct> buyProduct) {
		
		System.out.println(buyProduct);
		
		
		try {
			// Assuming the service method will handle saving the BuyProduct and associating
			// it with the logged-in user
			boolean isOrderPlaced = buyProductService.placeCartOrder(buyProduct);
			
			if (isOrderPlaced) {
				return new ResponseEntity<>("Order placed successfully!", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Failed to place the order.", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error placing order: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/deliver")
	public ResponseEntity<String> markAsDelivered(@RequestBody Long buyProductId) {
		System.out.println(buyProductId);
		LocalDateTime deliveredDate = LocalDateTime.now(); // Current time as delivered date
		String status = "Delivered Successfully"; // Updated status
		boolean isUpdated = buyProductService.updateDeliveredDetails(buyProductId, deliveredDate, status);
		if (isUpdated) {
			return ResponseEntity.ok("BuyProduct marked as delivered successfully!");
		} else {
			return ResponseEntity.status(404).body("BuyProduct not found or could not update delivery details.");
		}
	}
	@PutMapping("/dispatch")
	public ResponseEntity<String> dispatch(@RequestBody Long buyProductId) {
		System.out.println(buyProductId);
		LocalDateTime dispatchedDate = LocalDateTime.now(); // Current time as delivered date
		String status = "Dispatched"; // Updated status
		boolean isUpdated = buyProductService.updateDispatchedDetails(buyProductId, dispatchedDate, status);
		if (isUpdated) {
			return ResponseEntity.ok("BuyProduct marked as Dispatched successfully!");
		} else {
			return ResponseEntity.status(404).body("BuyProduct not found or could not update delivery details.");
		}
	}

	@GetMapping("/orders")
	public OrderDTO getOrders(@RequestParam("userEmail") String userEmail) {
		// Fetch the orders and map to OrderDTO
		 List<BuyProduct> buyProducts = buyProductService.getOrdersByEmail(userEmail);

		    // Sort the buyProducts list in descending order (assuming a field like createdDate or ID is available)
		    buyProducts.sort((b1, b2) -> b2.getBuyProductId().compareTo(b1.getBuyProductId())); // Replace getId() with the timestamp field if available

		    List<Product> products = new ArrayList<>();
		    List<byte[]> imageBytes = new ArrayList<>();
		    List<CustomerAddress> addresses = new ArrayList<>();

		    for (BuyProduct buyProduct : buyProducts) {
		        Product product = service.getProductById(buyProduct.getProductId());
		        products.add(product);

		        // Fetch product images
		        List<ProductImage> images = service.getProductImageById(product.getId());
		        List<byte[]> productImages = convertImagesToByteArray(images);
		        imageBytes.addAll(productImages);

		        // Fetch the address for the order
		        if (buyProduct.getAddressId() != null) {
		            CustomerAddress address = addressService.getAddressById(buyProduct.getAddressId());
		            addresses.add(address);
		        }
		    }

		    // Create the OrderDTO with populated data
		    OrderDTO orderDTO = new OrderDTO(products, buyProducts, imageBytes, addresses);

		    System.out.println("Data >>>>>>>>");
		    System.out.println(orderDTO);
		    return orderDTO;	
		    
	}

	private List<byte[]> convertImagesToByteArray(List<ProductImage> images) {

		System.out.println("image" + images);
		List<byte[]> imageBytes = new ArrayList<>();
		for (ProductImage productImage : images) {
			try {
				// Assuming imageData contains the file path
				Path imagePath = Path.of(productImage.getImageData());
				System.out.println("Reading image from path: " + imagePath);
				byte[] imageData = Files.readAllBytes(imagePath); // Convert file to byte array
				imageBytes.add(imageData); // Add to the list
			} catch (IOException e) {
				e.printStackTrace(); // Handle exception if the file cannot be read
			}
		}
		return imageBytes;
	}

	@PostMapping("/totalPriceByProductId")
	public Double getTotalPriceByProductId(@RequestBody List<Long> productId) {
		System.out.println(productId);
		return repo.findTotalPriceByProductId(productId);
	}

	// Endpoint to get the count of pending orders by product ID
	@PostMapping("/countPendingOrdersByProductId")
	public Long countPendingOrdersByProductId(@RequestBody List<Long> productId) {

		return repo.countPendingOrdersByProductId(productId);
	}

	@PostMapping("/countDeliveredOrdersByProductId")
	public Long countDeliveredOrders(@RequestBody List<Long> productId) {

		return repo.countDeliveredOrdersByProductId(productId);
	}

	@PostMapping("/sumQuantityByProductId")
	public Long sumQuantityByProductId(@RequestBody List<Long> productId) {

		return repo.sumQuantityByProductId(productId);
	}
	@PostMapping("/findStatisticsByProductIds")
	public List<String> findStatisticsByProductIds(@RequestBody List<Long> productId) {
		
		return repo.findStatisticsByProductIds(productId);
	}
	
	@PostMapping("/getTotalRevenueByMonth")
	public List<String> getTotalRevenueByMonth(@RequestBody List<Long> productId) {
		
		List<Object[]> results = repo.getTotalRevenueByMonth(productId);

		List<String> revenueSummaryList = new ArrayList<>();
		for (Object[] result : results) {
		    String summary = "Month: " + result[1] + ", Revenue: " + result[2];
		    revenueSummaryList.add(summary);
		}
		
		return revenueSummaryList;
	}
	@PostMapping("/getTotalRevenueByDayOfWeek")
	public List<String> getTotalRevenueByDayOfWeek(@RequestBody List<Long> productId) {
		
		 List<Object[]> rawData = repo.getTotalRevenueByDayOfWeek(productId);

	        // Array of day names for mapping the day of the week integer value
	        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	        // Create a list to store the result as strings
	        List<String> weeklyRevenue = new ArrayList<>();

	        // Process raw data and format it into the desired string format
	        for (Object[] data : rawData) {
	            int dayOfWeek = Integer.parseInt(data[0].toString());
	            double totalRevenue = Double.parseDouble(data[1].toString());

	            String revenueSummary = "Day: " + dayNames[dayOfWeek - 1] + ", Revenue: " + totalRevenue;
	            weeklyRevenue.add(revenueSummary);
	        }

	        return weeklyRevenue;
	    }
	
	 @PostMapping("/cancelOrder/{buyProductId}")
	    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long buyProductId) {
		 boolean updatedOrder =false;
			try {
				updatedOrder = buyProductService.cancelOrder(buyProductId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	    }
	 
	 @PostMapping("/findPendingProductsWithAddresses")
	 public List<ProductWithAddressDTO> getData(@RequestBody List<Long> productId) {
		 
		return repo.findPendingProductsWithAddresses(productId);
	 }
}
