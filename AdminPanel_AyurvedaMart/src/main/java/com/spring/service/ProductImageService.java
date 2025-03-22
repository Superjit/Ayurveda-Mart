package com.spring.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.entity.ImageData;
import com.spring.entity.ProductImage;
import com.spring.repo.ProductImageRepository;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public void saveImages(MultipartFile[] images, Long productId) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        
        for (MultipartFile image : images) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());

            // Ensure the file name is unique by appending a number if the file already exists
            String originalFileName = fileName;
            int fileCount = 1;

            // Get the file path
            Path path = Paths.get(uploadDir + File.separator + fileName);

            // Check if file exists and append number if needed
            while (Files.exists(path)) {
                // Extract the file extension
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                // Remove the extension from the original file name
                String baseName = fileName.substring(0, fileName.lastIndexOf("."));
                // Generate a new file name with a number suffix
                fileName = baseName + "_" + fileCount + fileExtension;
                // Create the new path with the updated file name
                path = Paths.get(uploadDir + File.separator + fileName);
                fileCount++;
            }

            // Create ProductImage object
            ProductImage productImage = new ProductImage();
            productImage.setProductId(productId);  // Set the product ID to the image
            productImage.setImageData(uploadDir+fileName);  // Set the file name or path in the database (fileName in this case)

            // Ensure the directory exists
            Files.createDirectories(path.getParent());

            // Save the image to the directory
            image.transferTo(path.toFile());

            // Add the product image to the list
            productImages.add(productImage);
        }

        // Save all images to the database
        productImageRepository.saveAll(productImages);
    }
    
    
    public List<ImageData> getProductImageByProductId(Long productId){
    	
    	 List<ProductImage> images=productImageRepository.findByProductId(productId);
//    	   List<ProductImage> images = productImageRepository.findByProductId(product.getId());
           List<ImageData> imageBytes = convertImagesToByteArray(images);
           
           
    	
    	return	imageBytes;
    	
    }

	public List<ProductImage> getProductImagesByProductIds(Long ids) {
		// TODO Auto-generated method stub
		return productImageRepository.findByProductId(ids);
	}
	
	
	 public void updateProductImage(Long productId, Long imageId, MultipartFile image) throws IOException {
	        // Find the image by productId and imageId
	        ProductImage productImage = productImageRepository.findById(imageId)
	                .orElseThrow(() -> new IOException("Image not found"));

	        // Delete the old image file
	        String oldImagePath = productImage.getImageData();
	        Path oldPath = Paths.get(oldImagePath);
	        Files.deleteIfExists(oldPath);

	        // Save the new image
	        String newFileName = StringUtils.cleanPath(image.getOriginalFilename());
	        Path newPath = Paths.get(uploadDir + File.separator + newFileName);
	        Files.createDirectories(newPath.getParent());
	        image.transferTo(newPath.toFile());

	        // Update the image data in the database
	        productImage.setImageData(newPath.toString());
	        productImageRepository.save(productImage);
	    }

	 private List<ImageData> convertImagesToByteArray(List<ProductImage> images) {
		    List<ImageData> imageDataList = new ArrayList<>();
		    for (ProductImage productImage : images) {
		        try {
		            Path imagePath = Path.of(productImage.getImageData()); // Assuming imageData contains the file path
		            byte[] imageData = Files.readAllBytes(imagePath); // Convert the image file into a byte array
		            ImageData image = new ImageData(productImage.getId(), imageData); // Create ImageData with ID and byte array
		            imageDataList.add(image); // Add the ImageData to the list
		        } catch (IOException e) {
		            e.printStackTrace(); // Handle exception if the file cannot be read
		        }
		    }
		    return imageDataList;
		}

    
    
}
