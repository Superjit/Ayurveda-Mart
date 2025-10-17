import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AllProduct.css';
import { BASE_URL } from '../../BaseUrl';
import { useNavigate } from 'react-router-dom';
import Pagination from './Pagination/Pagination';
import { jwtDecode } from 'jwt-decode';

const AllProduct = () => {
  const [categoryData, setCategoryData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10); // Number of items per page
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAllProductData = async () => {
      const token = localStorage.getItem('authToken');

      const decodedToken = jwtDecode(token);
      try {
        const response = await axios.get(`${BASE_URL}/api/category-data/all?adminEmail=${decodedToken.sub}`);
        setCategoryData(response.data);
      } catch (error) {
        setError('Error fetching data');
      } finally {
        setLoading(false);
      }
    };
    fetchAllProductData();
  }, []);

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  // Flatten category data into a single array of products
  const allProducts = categoryData.flatMap((data) =>
    data.products.map((product) => ({
      ...product,
      categoryName: data.category.categoryName,
      categoryDescription: data.category.description,
      detailNames: data.detailNames,
      detailValues: data.detailValues,
      images: data.images,
    }))
  );

  // Pagination logic
  const totalItems = allProducts.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentData = allProducts.slice(startIndex, endIndex);

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleUpdateProduct = (product) => {
    navigate('/updateProduct', { state: { product } });
  };

  const handleAddImage = (productId, categoryName) => {

    console.log('Add image for product:', productId, 'Category:', categoryName);
    navigate('/image-upload', { state: { productId, categoryName } });
  };
  const handleUpdateImage = (productId) => {

    console.log('Add image for product:', productId,);
    navigate('/updateProductImages', { state: { productId } });
  };
  const handleDeleteProduct = async (productId) => {
    if (window.confirm('Are you sure you want to deactivate this product?')) {
      try {
        await axios.delete(`${BASE_URL}/admin/products/${productId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('authToken')}`,
          },
        });
        setCategoryData((prevData) =>
          prevData.map((data) => ({
            ...data,
            products: data.products.map((product) =>
              product.id === productId
                ? { ...product, status: 'INACTIVE' }
                : product
            ),
          }))
        );
      } catch (error) {
        console.error('Error deactivating product:', error);
        alert('Failed to deactivate the product.');
      }
    }
  };

  const renderProductDetails = (product) => {
    return product.detailNames.map((detailName) => {
      const detailValue = product.detailValues.find(
        (value) =>
          value.detailNameId === detailName.id && value.productId === product.id
      );
      return detailValue ? (
        <p key={detailName.id}>
          <strong>{detailName.detailName}:</strong> {detailValue.value}
        </p>
      ) : null;
    });
  };

  const renderProductImages = (product) => {
    if (product.images.length > 0) {

      return product.images.map((image, idx) => (
        <img
          key={idx}
          src={`data:image/jpeg;base64,${image}`}
          alt={`Product ${product.name} Image ${idx + 1}`}
          className="product-image1"
          onClick={() => handleUpdateImage(product.id)}
        />

      ));

    } else {
      return (
        <div>
          <p>No images available</p>
          <button
            className="btn btn-primary"
            onClick={() => handleAddImage(product.id, product.categoryName)}
          >
            Add Image
          </button>
        </div>
      );
    }
  };

  return (
    <>
      <div className="category-data-container1">
        <h2>All Products</h2>

        {currentData.length === 0 ? (
          <div>No data available</div>
        ) : (
          <div className="table-responsive1">
            <table className="table1 table-striped1 product-table1">
              <thead className="thead-light1">
                <tr>
                  <th>Category</th>
                  <th>Product Name</th>
                  <th>Price</th>
                  <th>Status</th>
                  <th>Quantity</th>
                  <th>Details</th>
                  <th>Images</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {currentData.map((product) => (
                  <tr key={product.id}>
                    <td>{product.categoryName}</td>
                    <td>{product.name}</td>
                    <td>${product.price}</td>
                    <td className={`status ${product.status.toLowerCase()}`}>
                      {product.status}
                    </td>
                    <td>{product.quantity}</td>
                    <td>{renderProductDetails(product)}</td>
                    <td> {renderProductImages(product)}  <button
                      className="btn btn-primary badge updatebtn"
                      onClick={() => handleUpdateImage(product.id)}
                    >
                      Update Image
                    </button></td>
                    <td>
                      <button
                        className="btn1 btn-warning1 btn-sm1"
                        onClick={() => handleUpdateProduct(product)}
                      >
                        Update
                      </button>
                      <button
                        className="btn1 btn-danger1 btn-sm1"
                        onClick={() => handleDeleteProduct(product.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      <Pagination
        totalPages={totalPages}
        currentPage={currentPage}
        onPageChange={(page) => setCurrentPage(page)}
      />
    </>
  );
};

export default AllProduct;  