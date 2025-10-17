import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { BASE_URL } from '../../../BaseUrl';
import './AdditionalDetailsForm.css'

const AdditionalDetailsForm = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { categoryId, id, remainingData,category } = location.state || {};
  console.log(categoryId);
  console.log(id);
  console.log(remainingData);
  // Get category passed from the previous page
  const [additionalDetails, setAdditionalDetails] = useState({}); // State to store user input details
  const [productDetailsData, setProductDetailsData] = useState([]); // State to store DetailNames
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (categoryId) {
      // Fetch detail names based on category
      axios.get(`${BASE_URL}/api/details/category?categoryId=${categoryId}`)
        .then((response) => {
          setProductDetailsData(response.data); // Set the fetched data
          setLoading(false); // Set loading to false when data is fetched
        })
        .catch((error) => {
          console.error('Error fetching detail names:', error);
          setError('Failed to load details.');
          setLoading(false);
        });
    }
  }, [categoryId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setAdditionalDetails((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAddDetails = () => {
    // Prepare detail values to send to backend
    const detailValues = Object.keys(additionalDetails).map((detailName) => ({
      value: additionalDetails[detailName],
      detailNameId: productDetailsData.find((item) => item.detailName === detailName)?.id, // Match detailNameId
      productId: id, // Add the productId here if you have it

    }));
    console.log(detailValues);

    // Send the details to the backend API
    axios.post(`${BASE_URL}/api/detail-values`, detailValues, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('authToken')}`, // Attach JWT token
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        console.log('Details added successfully:', response.data);
        const productId = response.data[0]?.productId;
        console.log(productId);

        // Optionally reset form or show success message
        navigate('/image-upload', { state: { productId,category } });

      })
      .catch((error) => {
        console.error('Error adding details:', error);
        setError('Failed to add details.');
      });
  };

  return (
    <div className='textareaContainer'>
        <h1 className='headText1'>Add Products For {category}</h1>

      <div className='addProductContainer1'>

        {/* Check for loading or error */}
        {loading ? (
          <p>Loading details...</p>
        ) : error ? (
          <p>{error}</p>
        ) : (
          // If productDetailsData has items, render the textareas
          remainingData.length > 0 ? (
            remainingData.map((detail, index) => (
              <div className='textInputADD33' key={index} style={{ marginBottom: '10px' }}>
                <label className=''>{detail.detailName}:</label>
                <textarea className='inputTextAdd14'
                  name={detail.detailName} // Use the detailName as the field name
                  value={additionalDetails[detail.detailName] || ''}
                  onChange={handleInputChange}
                  placeholder={`Enter ${detail.detailName}`}
                  style={{  height: '100px' }}
                />
              </div>
            ))
          ) : (
            <p>No additional categories available.</p>
          )
        )}

        <button className='btnAddMoredetail1' onClick={handleAddDetails}>Add Details</button>
      </div>
    </div>
  );
};

export default AdditionalDetailsForm;
