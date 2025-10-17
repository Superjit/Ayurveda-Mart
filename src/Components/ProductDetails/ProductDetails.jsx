import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { useLocation, useNavigate } from 'react-router-dom';
import { user_Url } from '../user_Url';
import './ProductDetails.css'; // Import the CSS file

const ProductDetails = ({ setCartCount }) => {
  const location = useLocation();
  const { product, image, category, selectedAddress } = location.state || { category: 'Unknown Category' };
  console.log('Product:', product);
  console.log('Image:', image);
  console.log('Category:', category);
  console.log('Category:', selectedAddress);

  const [cartItems, setCartItems] = useState([]);
  const [paymentMethod, setPaymentMethod] = useState('');
  const [quantity, setQuantity] = useState(1);  // New state for quantity
  const [totalPrice, setTotalPrice] = useState(product?.price || 0);  // Calculate total price
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState(null);
  const [useremail, setUseremail] = useState(null);

  useEffect(() => {
     const token = localStorage.getItem('authToken');
        if (token) {
          try {
            const decodedToken = jwtDecode(token);
            console.log(decodedToken.sub);
            
            setUseremail(decodedToken.sub); // Assuming the token has a `role` field
            setUserRole(decodedToken.roles[0]); // Assuming the token has a `role` field
          } catch (error) {
            console.error('Error decoding token:', error);
          }
        }
    setTotalPrice(product?.price * quantity);  // Update price when quantity changes
  }, [quantity, product?.price]);

  const incrementQuantity = () => {
    setQuantity((prevQuantity) => prevQuantity + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 1) {
      setQuantity((prevQuantity) => prevQuantity - 1);
    }
  };

  const placeOrder = () => {
    if (!selectedAddress) {
      alert('Please select a delivery address');
      return;
    }
    if (!paymentMethod) {
      alert('Please select a payment method');
      return;
    }
    const orderDetails = {
      productId: product.id,
      categoryName:category.categoryName,
      quantity: quantity,
      price: totalPrice,
      email:useremail,
      paymentMethod: paymentMethod,
      addressId: selectedAddress.id,
    }
    console.log(orderDetails);

    const token = localStorage.getItem('authToken');
    axios
      .post(`${user_Url}/buyProduct/buy`, orderDetails, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(() => {
        alert('Order placed successfully!');
        navigate('/orders');
      })
      .catch((error) => console.error('Error placing order:', error));
  };

  return (
    <div className="container py-5">
      <h1 className="text-center mb-4 text-white">Product Details</h1>
      <div className="row">
        <div className="col-md-6">
          <div className="card shadow-sm border-light">
            <img
              className="card-img-top img-fluid"
              src={`data:image/jpeg;base64,${image}`}
              alt={product?.name || 'Product'}
            />
            <div className="card-body">
              <h5 className="card-title">{product?.name}</h5>
              <p className="card-text text-black">
                <strong>Price : </strong> ₹{product?.price}
              </p>
              <p className="card-text text-black">
                <strong>Total Price : </strong> ₹{totalPrice}
              </p>
              <p className="card-text text-black">
                <strong >Category : </strong> {category?.categoryName || 'Unknown'}
              </p>
              <p className="card-text text-black">
                <strong>Description : </strong> {category?.description || 'No description available.'}
              </p>
            </div>
          </div>
        </div>
      
        <div className="col-md-6 sideContainer">
          <h3 className="mb-3 text-white">Delivery Address</h3>
          {selectedAddress ? (
            <div className="list-group mb-3">
              <label key={selectedAddress.id} className="list-group-item list-group-item-action">
                <input
                  className="form-check-input me-2"
                  type="radio"
                  name="address"
                  value={selectedAddress.id}
                  checked={selectedAddress?.id === selectedAddress.id}
                />
                {selectedAddress.street}, {selectedAddress.city}, {selectedAddress.state}, {selectedAddress.country}, {selectedAddress.postalCode}
              </label>
            </div>
          ) : (
            <p>No address selected</p>
          )}

          <button
            className="btn btn-outline-primary"
            onClick={() => navigate('/customerInfo', { state: { product, image, category } })}
            style={{left:'1%'}}
          >
            Add New Address
          </button>

          <div className="mt-4">
            <h3 className="mb-3 text-white">Payment Method</h3>
            <select
              className="form-select"
              value={paymentMethod || ''}
              onChange={(e) => setPaymentMethod(e.target.value)}
            >
              <option value="">Select payment method</option>
              <option value="creditCard">Credit Card</option>
              <option value="paypal">PayPal</option>
              <option value="bankTransfer">Bank Transfer</option>
              <option value="cashOnDelivery">Cash on Delivery</option>
            </select>
          </div>

          {/* Quantity Control */}
          <div className="mb-8 text-white">
            <h4>Quantity</h4>
            <div className=" ">
              <button className="btn btn-outline-secondary ms-2" style={{ left:'7%',zIndex:1 }} onClick={decrementQuantity}>
                -
              </button>
              <input
                type="text"
                className=" text-center"
                value={quantity}
                onChange={(e) => setQuantity(Math.max(1, e.target.value))}
                min="1"
                style={{ width: '200px' }}
              />
              <button className="btn btn-outline-secondary ms-2" onClick={incrementQuantity}>
                +
              </button>
            </div>
          </div>

          <button className="btn btn-success mt-4 " style={{ left:'1%' }} onClick={placeOrder}>
            Place Order
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
