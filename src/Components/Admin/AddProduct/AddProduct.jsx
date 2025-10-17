import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import './AddProduct.css'
import { BASE_URL } from '../../BaseUrl';
import axios from 'axios';

const AddProduct = () => {
    const location = useLocation();
    const { category } = location.state || { category: 'Unknown Category' };
    console.log(category);

    const defaultCategories = [
        'Product Name',
        'Product Price',
        'Product Quantity',

    ];

    const [categories] = useState(defaultCategories);
    const [newCategory, setNewCategory] = useState('');
    const [userCategories, setUserCategories] = useState([]);
    const navigate = useNavigate();

    const [productDetailsData, setProductDetailsData] = useState([]); // State to store DetailNames
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (category) {
            // Fetch detail names based on category
            axios.get(`${BASE_URL}/api/details/categoryName?categoryName=${category}`)
                .then((response) => {
                  
                    console.log(response.data);
                    const detailNames = response.data.map((item) => item.detailName);

                    console.log(detailNames); // Log the extracted detail names for verification
                
                    setProductDetailsData(detailNames);
                    // setProductDetailsData(response.data); // Set the fetched data
                    setLoading(false); // Set loading to false when data is fetched
                })
                .catch((error) => {
                    console.error('Error fetching detail names:', error);
                    setError('Failed to load details.');
                    setLoading(false);
                });
        }
    }, [category]);

    const handleAddCategory = () => {
        if (newCategory.trim() === '') return; // Prevent empty entries
        setUserCategories([...userCategories, newCategory]);
        setNewCategory(''); // Clear the input field
    };

    const handleGoToAddProduct = () => {
        const productDetailsDataasd = userCategories.map((categoryName) => ({
            detailName: categoryName, // Each user category is a 'detailName'
            
        }));
        // console.log();
        
        const productDetailsDataBase = productDetailsData.map((categoryName) => ({
            detailName: categoryName, // Each user category is a 'detailName'
            
        }));
        console.log(productDetailsDataasd);
        console.log(productDetailsDataBase);
        console.log(productDetailsData);
        
        const productDetails = [
            ...new Map(
              [...productDetailsDataasd, ...productDetailsDataBase].map((item) => [item.detailName, item])
            ).values()
          ];
          console.log(productDetails);
          

        axios.post(`${BASE_URL}/api/details/bulk?categoryName=${category}`, productDetailsDataasd, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('authToken')}`, // Attach JWT token
                'Content-Type': 'application/json',
            }
        })
            .then((response) => {
                console.log('Detail names added successfully:', response.data);
                // Redirect or show a success message here
                navigate('/addProduct', { state: { category, productDetails } });
            })
            .catch((error) => {
                console.error('Error adding detail names:', error);
            });
    };


    const handleRemoveCategory = (indexToRemove) => {
        setUserCategories(userCategories.filter((_, index) => index !== indexToRemove));
        // setProductDetailsData(productDetailsData.filter((_, index) => index !== indexToRemove));
    };
    const handleRemoveCategorydataBase = (indexToRemove) => {
        // setUserCategories(userCategories.filter((_, index) => index !== indexToRemove));
        setProductDetailsData(productDetailsData.filter((_, index) => index !== indexToRemove));
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1 className='categoryHeading'>{category}</h1>

            <div className="addDetailContainer">
                <div style={{ marginBottom: '20px' }}>
                    {categories.map((category, index) => (
                        <button className="categoryData" key={`default-${index}`} style={{ cursor: 'not-allowed' }}>
                            {category}
                        </button>
                    ))}
                    {Array.isArray(productDetailsData) && productDetailsData.map((category, index) => (
                        <button className="categoryData" key={`fetched-${index}`}  >
                            {category}<span className="removeCategory" onClick={() => handleRemoveCategorydataBase(index)}>
                                &times;
                            </span>{/* Accessing the 'detailName' property */}
                        </button>
                    ))}
                    {userCategories.map((category, index) => (
                        <button className='categoryData' key={`default-${index}`}>
                            {category} <span className="removeCategory" onClick={() => handleRemoveCategory(index)}>
                                &times;
                            </span>
                        </button>
                    ))}
                </div>

                <div style={{ display: 'flex', gap: '10px' }}>
                    <input className='categoryInput' type="text" value={newCategory} onChange={(e) => setNewCategory(e.target.value)} placeholder="Enter category name" />
                    <button className='btnAddCategory' onClick={handleAddCategory}>
                        Add
                    </button>
                </div>

                <div >
                    <button className="btnAddProduct" onClick={handleGoToAddProduct}>
                        Go to Add Product
                    </button>
                </div>
            </div>
        </div>
    );
};

export default AddProduct;
