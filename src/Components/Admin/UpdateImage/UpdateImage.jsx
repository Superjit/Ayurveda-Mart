import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { BASE_URL } from '../../BaseUrl';
import './UpdateImage.css';

const UpdateImage = () => {
  const { state } = useLocation();
  const { productId } = state; // get the productId from state
  const [productImages, setProductImages] = useState([]);
  const [selectedImage, setSelectedImage] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProductImages = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/product-images/image/${productId}`);
        setProductImages(response.data);
      } catch (error) {
        console.error('Error fetching product images:', error);
      }
    };

    fetchProductImages();
  }, [productId]);

  const handleImageChange = (event) => {
    setSelectedImage(event.target.files[0]);
  };

  const handleImageUpdate = async (imageId) => {
    if (!selectedImage) {
      alert('Please select an image to update.');
      return;
    }

    const formData = new FormData();
    formData.append('image', selectedImage);

    try {
      await axios.put(`${BASE_URL}/api/product-images/update/${productId}/${imageId}`, formData);
      alert('Image updated successfully!');
      navigate(-1); // Go back to the previous page
    } catch (error) {
      console.error('Error updating image:', error);
      alert('Failed to update image.');
    }
  };

  return (
    <div className="update-image-container">
      <h2>Update Images for Product {productId}</h2>

      <div className="product-image-gallery">
        {productImages.length > 0 ? (
          productImages.map((image) => (
            <div key={image.imageId} className="image-card">
              <img
                src={`data:image/jpeg;base64,${image.imageData}`}
                alt={`Product Image ${image.imageId}`}
                className="product-image"
              />
              <input
                type="file"
                accept="image/*"
                onChange={handleImageChange}
                className="file-input"
              />
              <button 
                onClick={() => handleImageUpdate(image.imageId)}
                className="update-button"
              >
                Update Image
              </button>
            </div>
          ))
        ) : (
          <p>No images available for this product.</p>
        )}
      </div>
    </div>
  );
};

export default UpdateImage;
