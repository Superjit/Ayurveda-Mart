package com.spring.entity;
import java.util.List;

public class CategoryDataDTO {
    private Category category;
    private Product product;
    private List<Product> products;
    private List<DetailName> detailNames;
    private List<DetailValue> detailValues;
    private List<byte[]> images; // Changed to store image data as byte arrays
    

    public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	// Getters and setters
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<DetailName> getDetailNames() {
        return detailNames;
    }

    public void setDetailNames(List<DetailName> detailNames) {
        this.detailNames = detailNames;
    }

    public List<DetailValue> getDetailValues() {
        return detailValues;
    }

    public void setDetailValues(List<DetailValue> detailValues) {
        this.detailValues = detailValues;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    // Constructors
    public CategoryDataDTO() {
        super();
    }

    public CategoryDataDTO(Category category, List<Product> products, List<DetailName> detailNames,
                           List<DetailValue> detailValues, List<byte[]> images) {
        super();
        this.category = category;
        this.products = products;
        this.detailNames = detailNames;
        this.detailValues = detailValues;
        this.images = images;
    }

	@Override
	public String toString() {
		return "CategoryDataDTO [category=" + category + ", product=" + product + ", products=" + products
				+ ", detailNames=" + detailNames + ", detailValues=" + detailValues + ", images=" + images + "]";
	}

	public CategoryDataDTO(Category category, Product product, List<Product> products, List<DetailName> detailNames,
			List<DetailValue> detailValues, List<byte[]> images) {
		super();
		this.category = category;
		this.product = product;
		this.products = products;
		this.detailNames = detailNames;
		this.detailValues = detailValues;
		this.images = images;
	}

	public CategoryDataDTO(List<Product> products, List<byte[]> images) {
		super();
		this.products = products;
		this.images = images;
	}
    
	
}
