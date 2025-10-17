import './ProductForm.css';
import axios from 'axios';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { BASE_URL } from '../../../BaseUrl';
import { useLocation } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';

const ProductForm = ({ formData, handleInputChange }) => {
  const location = useLocation();
  const { category } = location.state || {};
  console.log(category);
  console.log(formData);
  console.log(formData.productDetails);
  const remainingData =formData.productDetails;
  console.log(remainingData);
  
  
  const navigate = useNavigate();
 

  const requiredFields = ['Product Name', 'Product Price', 'Product Quantity'];
  const fieldMappings = {
    'Product Name': 'productName',
    'Product Price': 'productPrice',
    'Product Quantity': 'productQuantity',
  };
  const handleNextPage = () => {
    // Pass formData as state while navigating
    const { productName, productPrice, productQuantity } = formData;

    if (!productName || !productPrice || !productQuantity) {
      alert('Please fill all required fields before proceeding.');
      return;
    }

    // Prepare the product object for the backend
    const productData = {
      name: formData.productName,
      price: formData.productPrice,
      quantity: formData.productQuantity,
    };
    console.log(productData);
 const token=localStorage.getItem('authToken');
        
                 const decodedToken = jwtDecode(token);
                 

    axios.post(`${BASE_URL}/admin/products/add?categoryName=${category}&adminEmail=${decodedToken.sub}`, productData, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('authToken')}`, // Attach JWT token
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      console.log('Detail names added successfully:', response.data);

      const id = response.data.id;
      const categoryId = response.data.categoryId;
      // Redirect or show a success message here
      navigate('/add-product-details', { state: { categoryId ,id, remainingData,category} });
    })
      .catch((error) => {
        console.error('Error adding detail names:', error);
      });



  }
  return (

    <div className='addProductContainer4'>
      
      {requiredFields.map((field, index) => (
        <div className='textInputADD4' key={index} style={{ marginBottom: '10px' }}>
          <label>{field}:</label>
          <input className='inputTextAdd4'
            type={field === 'Product Price' || field === 'Product Quantity' ? 'number' : 'text'}
            name={fieldMappings[field]} // Use camelCase names
            value={formData[fieldMappings[field]] || ''} // Controlled input
            onChange={handleInputChange}
            placeholder={`Enter ${field}`}
            style={{ display: 'block', marginTop: '5px', width: '100%' }}
          />
        </div>
      ))}
      <button className='btnAddMoredetail' onClick={handleNextPage} >
        Add More Details
      </button>
    </div>
  );
};

export default ProductForm;
