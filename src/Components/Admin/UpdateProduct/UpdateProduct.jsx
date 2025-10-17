import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { BASE_URL } from '../../BaseUrl';
import './UpdateProduct.css'

const UpdateProduct = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const [product, setProduct] = useState({
    detailNames: [],
    detailValues: [],
    images: [],
    ...location.state?.product,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Log the product state to ensure data is loaded correctly
  useEffect(() => {
    console.log("Loaded Product State:", product);
  }, [product]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    console.log('Submitting Product:', product);

    const payload = {
      product: {
        id: product.id,
        name: product.name,
        price: product.price,
        status: product.status,
        quantity: product.quantity,
        images: product.images, // Include images in the payload if updated
      },
      category: {
        id: product.categoryId,
        name: product.categoryName,
      },
      detailNames: product.detailNames,
      detailValues: product.detailValues,
    };

    console.log('Payload Sent:', payload);

    try {
      const response = await axios.put(`${BASE_URL}/admin/products/${product.id}`, payload, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('authToken')}`,
        },
      });
      alert('Product updated successfully!');
      navigate('/AllProduct');
    } catch (error) {
      setError('Error updating product');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct((prevProduct) => ({
      ...prevProduct,
      [name]: value,
    }));
  };

  const handleDetailChange = (detailNameId, value) => {
    setProduct((prevProduct) => {
      const updatedDetailValues = [...(prevProduct.detailValues || [])];

      // Find the detail to update or add a new one
      const index = updatedDetailValues.findIndex((d) => d.detailNameId === detailNameId);
      if (index >= 0) {
        updatedDetailValues[index].value = value;
      } else {
        updatedDetailValues.push({ detailNameId, value });
      }

      return { ...prevProduct, detailValues: updatedDetailValues };
    });
  };

  return (
    <div className="update-product-wrapper">
      <h2>Update Product</h2>

      {error && <div className="error-message">{error}</div>}

      {loading ? (
        <div>Loading...</div>
      ) : (
        <form onSubmit={handleSubmit}>
          <div className="form-group-container">
            <label>Category</label>
            <select
              name="categoryId"
              value={product.categoryId}
              onChange={handleInputChange}
            >
              <option value={product.categoryId}>{product.categoryName}</option>
            </select>
          </div>

          <div className="form-group-container">
            <label>Product Name</label>
            <input
              type="text"
              name="name"
              value={product.name}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group-container">
            <label>Price</label>
            <input
              type="number"
              name="price"
              value={product.price}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group-container">
            <label>Status</label>
            <select
              name="status"
              value={product.status}
              onChange={handleInputChange}
            >
              <option value="ACTIVE">ACTIVE</option>
              <option value="INACTIVE">INACTIVE</option>
            </select>
          </div>

          <div className="form-group-container">
            <label>Quantity</label>
            <input
              type="number"
              name="quantity"
              value={product.quantity}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group-container">
            <h4 className="details-header">Product Details</h4>
            {product.detailNames?.length > 0 ? (
              product.detailNames.map((detailName) => (
                <div key={detailName.id}>
                  <label>{detailName.detailName}</label>
                  <textarea
                    value={
                      product.detailValues.find((d) => d.detailNameId === detailName.id)?.value || ''
                    }
                    onChange={(e) => handleDetailChange(detailName.id, e.target.value)}
                  />
                </div>
              ))
            ) : (
              <p>No details available for this product.</p>
            )}
          </div>

          <div className="form-group-container">
            <span
              className="submit-action"
              onClick={handleSubmit}
            >
              Update Product
            </span>
          </div>
        </form>
      )}
    </div>
  );
};

export default UpdateProduct;
