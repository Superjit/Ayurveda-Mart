import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Category.css';
import { BASE_URL } from '../../BaseUrl'; // Assuming BASE_URL is the base URL for your backend
import { jwtDecode } from 'jwt-decode';

const Category = () => {
  const [categories, setCategories] = useState([]); // State to hold categories from the backend
  const [error, setError] = useState(''); // To handle errors
  const [userRole, setUserRole] = useState(null);
    const [useremail, setUseremail] = useState(null);
 
  // Fetch categories from the backend when the component mounts
  useEffect(() => {
     const token=localStorage.getItem('authToken');
    
             const decodedToken = jwtDecode(token);
             setUseremail(decodedToken.sub);
             setUserRole(decodedToken.roles[0]);
          
    const fetchCategories = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/admin/categories/data?adminEmail=${decodedToken.sub}`, {
          headers: {
            'Authorization': `Bearer ${token}`, // Attach JWT token
          },
        });
        setCategories(response.data); // Set the categories from the response
      } catch (error) {
        console.error('Error fetching categories:', error);
        setError('Failed to load categories');
      }
    };

    fetchCategories();
  }, []); // Empty dependency array ensures this runs only once when the component mounts

  return (
    <div className="category-container">
      {error && <div className="error-message">{error}</div>} {/* Show error if any */}

      {categories.length > 0 ? (
        categories.map((category) => (
          <div className="category-item" key={category.id}> {/* Use category.id as key */}
            <h3 className="category-name">{category.categoryName}</h3>
            <p className="category-description">{category.description}</p>
            <div className="category-divider"></div>
          </div>
        ))
      ) : (
        <div>No categories available</div> // Display message if there are no categories
      )}
      
      <button type="submit" className="submit-button">
        Add New Category
      </button>
    </div>
  );
};

export default Category;
