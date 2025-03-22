package com.spring.entity;

public class ImageData {
    private Long imageId; // Image ID
    private byte[] imageData; // Image byte array

    // Constructor
    public ImageData(Long imageId, byte[] imageData) {
        this.imageId = imageId;
        this.imageData = imageData;
    }

    // Getters and Setters
    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

