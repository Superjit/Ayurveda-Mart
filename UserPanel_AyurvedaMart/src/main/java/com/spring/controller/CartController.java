package com.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Cart;
import com.spring.entity.CartDTO;
import com.spring.entity.Product;
import com.spring.entity.ProductImage;
import com.spring.services.CartService;
import com.spring.services.ProductDTOService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductDTOService service;

	@PostMapping("/add")
	public ResponseEntity<String> addToCart(@RequestBody Cart cart) {

		cartService.addToCart(cart);

		return ResponseEntity.ok("Product added to cart successfully!");
	}

	@GetMapping("/count")
	public long getCartCount(@RequestParam String email) {
		System.out.println(email);
		System.out.println(cartService.getCartCount(email));
		return cartService.getCartCount(email);
	}

	@GetMapping("/cartItems")
	public CartDTO getCartItems(@RequestParam String email) {
		System.out.println(cartService.getCartItems(email));

		List<Cart> carts = cartService.getCartItems(email);
		List<Product> products = new ArrayList<>();
		for (Cart cart : carts) {
			Product product = service.getProductById(cart.getProductId());
			products.add(product);
		}
		List<ProductImage> images = new ArrayList<>();
		List<byte[]> imageBytes = new ArrayList<>();
		for (Product product : products) {
			List<ProductImage> image = service.getProductImageById(product.getId());
			System.out.println("image 2222 :--" + image);
			List<byte[]> productImages = convertImagesToByteArray(image);
			imageBytes.addAll(productImages);
		}

		System.out.println("Image bytes: " + imageBytes);
		System.out.println("Products: " + products);
		CartDTO cartDTO = new CartDTO(carts, products, imageBytes);

		System.out.println(cartDTO);
		return cartDTO;
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

	@PutMapping("/update")
	public ResponseEntity<String> updateCartItem(@RequestBody Cart cart) {

		System.out.println(cart);

		cartService.updateCartItem(cart);
		return ResponseEntity.ok("Cart updated successfully");
	}

	@DeleteMapping("/remove")
	public ResponseEntity<String> removeCartItem(@RequestParam String email, @RequestParam Long productId) {

		// Decode token and validate if required
		cartService.removeCartItem(email, productId);
		return ResponseEntity.ok("Cart item removed successfully");
	}
}
