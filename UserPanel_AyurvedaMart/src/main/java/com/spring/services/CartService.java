package com.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.entity.Cart;
import com.spring.entity.Product;
import com.spring.repo.CartRepo;

@Service
public class CartService {

	private final CartRepo cartRepo;
	private final ProductDTOService service;

	public CartService(CartRepo cartRepo, ProductDTOService service) {
		super();
		this.cartRepo = cartRepo;
		this.service = service;
	}

	public void addToCart(Cart cart) {

		Product product = service.getProductById(cart.getProductId());

		Optional<Cart> cart2 = cartRepo.findByProductIdAndUser_email(cart.getProductId(), cart.getUser_email());

		if (cart2.isPresent()) {
			System.out.println("Data cart " + cart2.get());

			cart.setCartId(cart2.get().getCartId());
			cart.setPrice(product.getPrice() * (cart2.get().getQuantity() + 1));
			cart.setQuantity(cart2.get().getQuantity() + 1);
		}

		System.out.println("cart data: -- " + cart);

		cartRepo.save(cart);
	}

	public long getCartCount(String email) {
		// Assuming you have a Cart entity that stores items for a user
		return cartRepo.countByUser_email(email);
	}

	public List<Cart> getCartItems(String email) {
		List<Cart> cartItems = cartRepo.findByUser_email(email);
		return cartItems;
	}

	public void updateCartItem(Cart cart) {

		Product product = service.getProductById(cart.getProductId());

		Optional<Cart> cart2 = cartRepo.findByProductIdAndUser_email(cart.getProductId(), cart.getUser_email());

		if (cart2.isPresent()) {
			System.out.println("Data cart " + cart2.get());

			cart.setCartId(cart2.get().getCartId());
			cart.setPrice(product.getPrice() * cart.getQuantity());
			cart.setQuantity(cart.getQuantity());
		}
		cartRepo.save(cart);

	}

	public void removeCartItem(String email, Long productId) {
		
		Optional<Cart> cart2 = cartRepo.findByProductIdAndUser_email(productId, email);

		if (cart2.isPresent()) {
			System.out.println("Data cart " + cart2.get());
			
			cartRepo.deleteById(cart2.get().getCartId());
		}
		
	}

}
