import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ProductForm from '../ProductForm/ProductForm';
import AdditionalDetailsForm from '../AdditionalDetailsForm/AdditionalDetailsForm';
import ImageUpload from '../ImageUploade/ImageUpload';
import { useLocation } from 'react-router-dom';
import { BASE_URL } from '../../../BaseUrl';
import axios from 'axios';
import './AddProductFormData.css'

const AddProductFormData = () => {
  const location = useLocation();
  const { category,productDetails } = location.state || {};
  console.log(category);
  console.log(productDetails);
  
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    productName: '',
    productPrice: '',
    productQuantity: '',
    productDetails: productDetails,
  });
console.log(formData);

  const [additionalDetails, setAdditionalDetails] = useState([]);
  const [showImageUpload, setShowImageUpload] = useState(false);


  const categories = ['Product Name', 'Product Price', 'Product Quantity']; // Example categories

  // Separate required and optional categories
  const requiredFields = ['Product Name', 'Product Price', 'Product Quantity'];
  const remainingFields = categories.filter(
    (category) => !requiredFields.includes(category)
  );

 
  console.log(category);
  
  // Handle changes for form data
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'productPrice' || name === 'productQuantity' ? parseFloat(value) : value,
    }));
  };

  // Add more details
  // const handleAddMoreDetails = () => {
  //   const { productName, productPrice, productQuantity } = formData;

  //   if (!productName || !productPrice || !productQuantity) {
  //     alert('Please fill all required fields before adding more details.');
  //     return;
  //   }

  //   // Navigate to AdditionalDetailsForm and pass the remaining fields
  //   navigate('/add-product-details', { state: { remainingFields, formData } });
  // };

  // Handle image submission
  const handleImageSubmit = () => {
    console.log('Image Uploaded. Proceeding further.');
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1 className='headTextdata'>Add Products For {category}</h1>

      <ProductForm
        formData={formData}
        handleInputChange={handleInputChange}
        // handleAddMoreDetails={handleAddMoreDetails}
       
      />
      
      {showImageUpload && <ImageUpload handleImageSubmit={handleImageSubmit} />}
    </div>
  );
};

export default AddProductFormData;
