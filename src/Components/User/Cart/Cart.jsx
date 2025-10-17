import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { user_Url } from '../../user_Url';
import { jwtDecode } from 'jwt-decode';
import './Cart.css';

const Cart = ({ setCartCount }) => {
  const [cartItems, setCartItems] = useState([]);
  const [userRole, setUserRole] = useState(null);
  const [useremail, setUseremail] = useState(null);

  // Decode token to fetch user email and role
  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        setUseremail(decodedToken.sub);
        setUserRole(decodedToken.roles[0]);
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
  }, []);

  // Fetch cart items from the server
  useEffect(() => {    
    if (useremail) {
      axios
        .get(`${user_Url}/cart/cartItems?email=${useremail}`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('authToken')}` },
        })
        .then((response) => {
          console.log(response.data);
          
          const { carts, products, images } = response.data;
          const mergedCartItems = carts.map((cart, index) => ({
            ...cart,
            productName: products[index]?.name,
            productPrice: products[index]?.price,
            image: images[index], // Assuming 'images' is an array of base64 image strings
            status: products[index]?.status,
            quantity: carts[index].quantity, 
          }));
          setCartItems(mergedCartItems);
        })
        .catch((error) => console.error('Error fetching cart items:', error));
    }
  }, [useremail]);

  // Handle quantity changes
  const handleQuantityChange = (productId, delta) => {
    const updatedCart = cartItems.map((item) =>
      item.productId === productId
        ? { ...item, quantity: Math.max(item.quantity + delta, 1) }
        : item
    );
    setCartItems(updatedCart);

    axios
      .put(
        `${user_Url}/cart/update`,
        {
          user_email: useremail,
          productId,
          quantity: updatedCart.find((item) => item.productId === productId).quantity,
        },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem('authToken')}` },
        }
      )
      .catch((error) => console.error('Error updating quantity:', error));
  };

  // Handle removing an item from the cart
  const handleRemove = (productId) => {
    axios
      .delete(`${user_Url}/cart/remove?email=${useremail}&productId=${productId}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem('authToken')}` },
      })
      .then(() => {
        setCartItems(cartItems.filter((item) => item.productId !== productId));
        setCartCount((prev) => prev - 1);
      })
      .catch((error) => console.error('Error removing item:', error));
  };

  // Calculate subtotal, discount, delivery fee, and total
  const calculateSummary = () => {
    const subtotal = cartItems.reduce(
      (total, item) => total + (item.status !== 'INACTIVE' && item.quantity > 0 ? item.productPrice * item.quantity : 0),
      0
    );
    const discount = 0; // Add logic for discount if applicable
    const deliveryFee = 5; // Example static delivery fee
    const total = subtotal - discount + deliveryFee;
    return { subtotal, discount, deliveryFee, total };
  };

  const { subtotal, discount, deliveryFee, total } = calculateSummary();

  return (
    <div className="cart-container">
      <div className="cart-items-container">
        <h2 className="cart-heading text-white">Your Cart</h2>
        {cartItems.length === 0 ? (
          <p className="empty-cart-message">Your cart is empty.</p>
        ) : (
          <ul className="cart-items-list">
            {cartItems.map((item) => (
              <li
                key={item.cartId}
                className={`cart-item ${item.status === 'INACTIVE' || item.quantity === 0 ? 'out-of-stock' : ''}`}
              >
                <div className="cart-item-info">
                  <img className="cart-item-image" src={`data:image/jpeg;base64,${item.image}`} alt={item.productName} />
                  <span className="cart-item-name">{item.productName}</span>
                  {/* Display quantity and price only if product is active */}
                  {item.status !== 'INACTIVE' && item.quantity > 0 ? (
                    <>
                      <span className="cart-item-quantity">Quantity: {item.quantity}</span>
                      <span className="cart-item-price">Price: ${item.productPrice}</span>
                    </>
                  ) : (
                    <span className="cart-item-status">This item is unavailable</span>
                  )}
                </div>
                <div className="cart-item-controls">
                  <button
                    className="cart-item-btn"
                    onClick={() => handleQuantityChange(item.productId, 1)}
                    disabled={item.status === 'INACTIVE' || item.quantity === 0}
                  >
                    +
                  </button>
                  <button
                    className="cart-item-btn"
                    onClick={() => handleQuantityChange(item.productId, -1)}
                    disabled={item.quantity <= 1 || item.status === 'INACTIVE' || item.quantity === 0}
                  >
                    -
                  </button>
                  <button
                    className="cart-item-remove"
                    onClick={() => handleRemove(item.productId)}
                    disabled={item.status === 'INACTIVE' || item.quantity === 0}
                  >
                    Remove
                  </button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>

      <div className="order-summary-container">
        <h2 className="order-summary-heading">Order Summary</h2>
        <div className="order-summary-item">
          <span className="summary-label">Subtotal:</span>
          <span className="summary-value">${subtotal}</span>
        </div>
        <div className="order-summary-item">
          <span className="summary-label">Discount:</span>
          <span className="summary-value">${discount}</span>
        </div>
        <div className="order-summary-item">
          <span className="summary-label">Delivery Fee:</span>
          <span className="summary-value">${deliveryFee}</span>
        </div>
        <div className="order-summary-item total">
          <span className="summary-label">Total:</span>
          <span className="summary-value">${total}</span>
        </div>
        <button className="checkout-btn">Proceed to Checkout</button>
      </div>
    </div>
  );
};

export default Cart;
