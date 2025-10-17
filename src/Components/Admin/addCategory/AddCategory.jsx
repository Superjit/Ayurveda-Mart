import React, { useState, useEffect } from 'react';
import './AddCategory.css';
import axios from 'axios';
import { BASE_URL } from '../../BaseUrl';
import { jwtDecode } from 'jwt-decode';

export default function AddCategory() {
  const [categoryName, setCategoryName] = useState('');
  const [description, setDescription] = useState('');
  const [role, setRole] = useState('');
  const [error, setError] = useState(''); // To display error message if needed
  const [loading, setLoading] = useState(false); // To show loading state when submitting
  const [userRole, setUserRole] = useState(null);
  const [useremail, setUseremail] = useState(null);

  

  // Fetch the user's role from the backend when the component mounts
  useEffect(() => {
    const fetchRole = async () => {
      try {
        const response = await axios.get(`${BASE_URL}/api/user/role`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('authToken')}` // Pass token in header
          }
        });
        console.log('User role:', response.data); // Log the role
        setRole(response.data); // Set the role returned by the backend
      } catch (error) {
        console.error('Error fetching role:', error);
        setError('Failed to fetch user role');
      }
    };
  
    fetchRole();
  }, []);
  

  const handleCategoryNameChange = (e) => {
    setCategoryName(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
     const token=localStorage.getItem('authToken');
        
                 const decodedToken = jwtDecode(token);
                 setUseremail(decodedToken.sub);
                 setUserRole(decodedToken.roles[0]);

    if (!categoryName || !description) {
      setError('Category Name and Description are required!');
      return;
    }

    const categoryData = { categoryName, description };
    setLoading(true);
    setError(''); // Clear previous error messages
    console.log(categoryData);
    
    try {
      const response = await axios.post(`${BASE_URL}/admin/categories/add?adminEmail=${decodedToken.sub}`, categoryData, {
        headers: { 'Authorization': `Bearer ${localStorage.getItem('authToken')}` }
      });

      console.log('Category added:', response.data);
      // Reset form after successful submission
      setCategoryName('');
      setDescription('');
      setError('');
      alert('Category added successfully!');
    } catch (error) {
      console.error('Error adding category:', error);
      // setError('An error occurred while adding the category. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // If the user is not an Admin, show a permission message
  if (role !== 'ROLE_ADMIN') {
    return <div>You do not have permission to access this page.</div>;
  }

  return (
    <div>
      <div className="add-category-container">
        <form onSubmit={handleSubmit} className="add-category-form">
          <input
            type="text"
            placeholder="Category Name"
            value={categoryName}
            onChange={handleCategoryNameChange}
            className="input-field"
          />
          <textarea
            placeholder="Description"
            value={description}
            onChange={handleDescriptionChange}
            className="textarea-field"
          />
          {/* {error && <div className="error-message">{error}</div>} */}
          <button type="submit" className="submit-button1" disabled={loading}>
            {loading ? 'Adding...' : 'Add Category'}
          </button>
        </form>
      </div>
    </div>
  );
}
