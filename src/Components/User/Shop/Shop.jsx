import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Shop.css';
import { user_Url } from '../../user_Url';
import { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../../Sidebar/Sidebar';

function Shop({ setCartCount }) {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState(null);
  const [useremail, setUseremail] = useState(null);
  const [isVisible, setIsVisible] = useState(false);
  const [sidebarVisible, setSidebarVisible] = useState(false);

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

    // Fetch products from the backend
    axios
      .get(`${user_Url}/allProductWithImage`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
        },
      })
      .then((response) => {
        setProducts(response.data);
        console.log(response.data);
        
      })
      .catch((error) => {
        console.error('Error fetching products:', error);
      });
  }, []);

  const handleAddToCart = (product) => {
    if (userRole !== 'ROLE_USER') {
      alert('You are not authorized to add products to the cart.');
      return;
    }

    if (product.status === 'INACTIVE') {
      alert('This product is inactive and cannot be added to the cart.');
      return;
    }

    if (product.quantity === 0) {
      alert('This product is out of stock and cannot be added to the cart.');
      return;
    }

    const cartItem = {
      productId: product.id,
      user_email: useremail,
    };

    axios
      .post(`${user_Url}/cart/add`, cartItem, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
        },
      })
      .then((response) => {
        alert('Product added to cart successfully!');
        axios
          .get(`${user_Url}/cart/count?email=${useremail}`, {
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
            },
          })
          .then((response) => {
            if (setCartCount && typeof setCartCount === 'function') {
              setCartCount(response.data);
            }
          })
          .catch((error) => {
            console.error('Error fetching cart count:', error);
          });
      })
      .catch((error) => {
        console.error('Error adding to cart:', error);
        alert('Failed to add product to cart.');
      });
  };

  const handleBuyNow = (product, image, category) => {
    if (userRole !== 'ROLE_USER') {
      alert('You are not authorized to buy products.');
      return;
    }

    if (product.status === 'INACTIVE') {
      alert('This product is inactive and cannot be purchased.');
      return;
    }

    if (product.quantity === 0) {
      alert('This product is out of stock and cannot be purchased.');
      return;
    }

    navigate('/customerInfo', { state: { product, image, category } });
  };

  const onImageVisible = (entry) => {
    if (entry.isIntersecting) {
      setIsVisible(true);
    }
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach(onImageVisible);
  }, { threshold: 0.1 });

  useEffect(() => {
    const images = document.querySelectorAll('img');
    images.forEach((image) => observer.observe(image));

    return () => observer.disconnect();
  }, []);

  const toggleSidebar = () => {
    setSidebarVisible(!sidebarVisible);
  };

  return (
    <div className="bodyContainer">
     {/* <div className={`leftContainer ${sidebarVisible ? 'open' : ''}`}>
        <Sidebar />
      </div> */}
      <div className="shop-container">
      {/* <span className="sidebar-toggle" onClick={toggleSidebar}>
          {sidebarVisible ? '<' : '>'}
        </span> */}
        <h2>Products</h2>
        <div className="products-grid">
          {products.length === 0 ? (
            <p>No products available</p>
          ) : (
            products.map((product) => (
              <div
                className={`product-card ${product.products[0].status === 'INACTIVE' && product.products[0].quantity <= 0 ? 'inactive' : ''} ${product.products[0].quantity <= 0 ? 'out-of-stock' : ''}`}
                key={product.products[0].id}
              >
                <div
                  className="product-image-container"
                  onClick={() =>
                    product.products[0].status !== 'INACTIVE' &&
                    product.products[0].quantity !== 0 &&
                    navigate('/product', { state: { product: product.products[0],images: product.images,detailNames: product.detailNames, detailValues:product.detailValues }})
                  }
                >
                  {product.images && product.images.length > 0 ? (
                    <img
                      className="product-image"
                      src={isVisible ? `data:image/jpeg;base64,${product.images[0]}` : 'placeholder.jpg'}
                      alt={product.products[0].name}
                    />
                  ) : (
                    <p>No image available</p>
                  )}
                </div>
                <div className="product-info">
                  <h3>{product.products[0].name}</h3>
                  {product.products[0].status !== 'INACTIVE' && (
                    <>
                      <p>Price: ${product.products[0].price}</p>
                      <p>Quantity: {product.products[0].quantity}</p>
                    </>
                  )}
                  <p
                    className={`product-status ${product.products[0].status === 'INACTIVE' || product.products[0].quantity <= 0
                      ? 'out-of-stock-text'
                      : 'out-of-stock-text22'
                      }`}
                  >
                    {product.products[0].status === 'INACTIVE' || product.products[0].quantity <=0
                      ? 'Out of Stock'
                      : product.products[0].status}
                  </p>
                </div>
                <div className="product-buttons">
                  <span
                    className="button1"
                    onClick={() => handleAddToCart(product.products[0])}
                    disabled={product.products[0].quantity === 0 || product.products[0].status === 'INACTIVE'}
                  >
                    Add to Cart
                  </span>
                  <span
                    className="button1"
                    onClick={() =>
                      handleBuyNow(
                        product.products[0],
                        product.images[0],
                        product.category
                      )
                    }
                    disabled={product.products[0].quantity === 0 || product.products[0].status === 'INACTIVE'}
                  >
                    Buy Now
                  </span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

export default Shop;
