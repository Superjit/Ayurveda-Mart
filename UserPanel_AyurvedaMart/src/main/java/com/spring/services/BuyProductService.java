package com.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.entity.BuyProduct;
import com.spring.entity.Product;
import com.spring.repo.BuyProductRepo;

@Service
public class BuyProductService {

	private final BuyProductRepo buyProductRepo;
	

	private final ProductDTOService service;

	public BuyProductService(BuyProductRepo buyProductRepo, ProductDTOService service) {
		super();
		this.buyProductRepo = buyProductRepo;
		this.service = service;
	} 
 
	public boolean updateDeliveredDetails(Long buyProductId, LocalDateTime diliveredDate, String status) {
		int rowsAffected = buyProductRepo.updateDeliveredDetailsById(buyProductId, diliveredDate, status);
		return rowsAffected > 0;
	}
	public boolean updateDispatchedDetails(Long buyProductId, LocalDateTime dispatchedDate, String status) {
		int rowsAffected = buyProductRepo.updatedispatchedDetailsById(buyProductId, dispatchedDate, status);
		return rowsAffected > 0;
	}

	public boolean placeOrder(BuyProduct buyProduct) {
		
		
		try {
			Product product = service.getProductById(buyProduct.getProductId());
			
			
			System.out.println(product.getQuantity());
			if(product.getQuantity() > buyProduct.getQuantity()) {
				
			buyProduct.setPrice(product.getPrice() * buyProduct.getQuantity());// for set price in back end

			product.setQuantity(product.getQuantity() - buyProduct.getQuantity());
			System.out.println(product);

			buyProductRepo.save(buyProduct);
			service.updateProductQuantity(buyProduct.getCategoryName(), product);
			return true;
			}else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
	public boolean placeCartOrder(List<BuyProduct> buyProduct) {
		boolean flag =false;
		for (BuyProduct buyProduct2 : buyProduct) {
			
		try {
			Product product = service.getProductById(buyProduct2.getProductId());
			
			
			System.out.println(product.getQuantity());
			if(product.getQuantity() > buyProduct2.getQuantity()) {
				
				buyProduct2.setPrice(product.getPrice() * buyProduct2.getQuantity());// for set price in back end
				
				product.setQuantity(product.getQuantity() - buyProduct2.getQuantity());
				System.out.println(product);
				
				buyProductRepo.save(buyProduct2);
				service.updateProductQuantity(buyProduct2.getCategoryName(), product);
				flag = true;
			}else {
				flag =false;
			}
			
		} catch (Exception e) {
			flag= false;
		}
		}
		return flag;
	}
	
	
	 public List<BuyProduct> getOrdersByEmail(String email) {
	        return buyProductRepo.findByEmail(email);
	    }

	 
	 public Boolean cancelOrder(Long buyProductId) throws Exception {
	        BuyProduct buyProduct = buyProductRepo.findById(buyProductId).orElse(new BuyProduct());
	        
	        if ("Delivered Successfully".equals(buyProduct.getStatus())) {
	            throw new Exception("Cannot cancel a delivered order");
	        }else if("Dispatched".equals(buyProduct.getStatus())) {
	        	throw new Exception("Cannot cancel a Dispatched order");
	        }else {
				
	        	buyProduct.setStatus("Cancelled");
	        	buyProductRepo.save(buyProduct);// Update status to "Cancelled"
	        	return true;
			}
	        
	    }
	 
	
}
