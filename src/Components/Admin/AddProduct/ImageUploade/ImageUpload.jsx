import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import { BASE_URL } from '../../../BaseUrl';
import './ImageUplaud.css'

const ImageUpload = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { productId, category } = location.state || {};
  const [images, setImages] = useState([]);
  const [isUploading, setIsUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState({});

  // Handle image selection
  const handleImageChange = (e) => {
    const files = Array.from(e.target.files);
    setImages((prevImages) => [...prevImages, ...files]);
  };

  // Handle image upload with progress tracking
  const handleUpload = async () => {
    if (images.length === 0) {
      alert('Please select images to upload.');
      return;
    }

    if (!productId) {
      alert('Product ID is missing. Cannot upload images.');
      return;
    }

    setIsUploading(true);

    const formData = new FormData();
    // Append all images to formData
    images.forEach((image) => {
      formData.append('images', image); // Appending all images
    });
    formData.append('productId', productId); // Append product ID

    try {
      await axios.post(`${BASE_URL}/api/product-images/upload`, formData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
          'Content-Type': 'multipart/form-data',
        },
        onUploadProgress: (progressEvent) => {
          const percentCompleted = Math.round(
            (progressEvent.loaded * 100) / progressEvent.total
          );
          setUploadProgress((prev) => ({
            ...prev,
            allImages: percentCompleted, // For overall progress
          }));
        },
      });

      alert('Images uploaded successfully!');
      setImages([]); // Reset images state
      setUploadProgress({}); // Clear progress state
      navigate('/add-product');
    } catch (error) {
      console.error('Error uploading images:', error);
      alert('Failed to upload images. Please try again.');
    } finally {
      setIsUploading(false);
    }
  };

  // Render image previews with upload progress
  const renderImagePreviews = () => {
    return images.map((image, index) => (
      <div key={index} style={{ display: 'inline-block', marginRight: '10px' }}>
        <img
          src={URL.createObjectURL(image)}
          alt={`Uploaded Preview ${index + 1}`}
          style={{ width: '100px', height: '100px', objectFit: 'cover' }}
        />
        <div style={{ marginTop: '5px', textAlign: 'center' }}>
          {uploadProgress[image.name] !== undefined ? (
            <span>{uploadProgress[image.name]}% Uploaded</span>
          ) : (
            <span >Ready to upload</span>
          )}
        </div>
      </div>
    ));
  };

  return (
    <div style={{ padding: '20px' ,width:'90%'}}>
      <h1 className='headTextimage'>Add Products For {category}</h1>

      <div className='imageContainer'>

        <input className='imageUpload'
          type="file"
          accept="image/*"
          multiple
          
          onChange={handleImageChange}
          style={{ display: 'block', marginBottom: '20px' }}
        />

        <div className='imageUploadContainer'>{renderImagePreviews()}</div>

        <button
          onClick={handleUpload}
          disabled={isUploading}
          style={{
            marginTop: '20px',
            padding: '10px 20px',
            backgroundColor: '#4CAF50',
            color: 'white',
            position:'relative',
            right:'0%',
            border: 'none',
            cursor: isUploading ? 'not-allowed' : 'pointer',
          }}
        >
          {isUploading ? 'Uploading...' : 'Upload Images'}
        </button>
      </div>
    </div>
  );
};

export default ImageUpload;
