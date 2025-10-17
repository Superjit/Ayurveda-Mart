import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Product.css';

function Product() {
    const location = useLocation();
    const navigate = useNavigate();
    const { product, images, detailNames, detailValues } = location.state || {};
    const [currentImageIndex, setCurrentImageIndex] = useState(0);

    // Automatically change image every 10 seconds
    useEffect(() => {
        // const interval = setInterval(() => {
        //     setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
        // }, 5000); // Change image every 10 seconds

        // return () => clearInterval(interval);
    }, [images.length]);

    const handleImageClick = (index) => {
        setCurrentImageIndex(index);
    };

    return (
        <div className="product-page-container">
            <button className="back-button" onClick={() => navigate(-1)}>Back</button>
            <div className="product-layout">
                {/* Sidebar for product images */}
                <div className="product-image-section">
                      {/* Image Thumbnails at the Bottom */}
                      <div className="product-thumbnails">
                        {images.map((image, index) => (
                            <img
                                key={index}
                                src={`data:image/jpeg;base64,${image}`}
                                alt={`Thumbnail ${index}`}
                                className={`thumbnail ${index === currentImageIndex ? 'active' : ''}`}
                                onClick={() => handleImageClick(index)}
                            />
                        ))}
                    </div>
                    {images.length > 0 ? (
                        <img
                            src={`data:image/jpeg;base64,${images[currentImageIndex]}`}
                            alt={`Product Image`}
                            className="product-main-image"
                        />
                    ) : (
                        <p>No image available</p>
                    )}

                  
                </div>
                  {/* Product Description Section */}
                  <div className="product-description-section">
                <div className="product-description">
                        <h2>{product.name}</h2>
                        <p><strong>Price:</strong> ${product.price}</p>
                        <p><strong>Quantity:</strong> {product.quantity > 0 ? product.quantity : 'Out of Stock'}</p>
                        <p><strong>Status:</strong> {product.status === 'INACTIVE' ? 'Inactive' : 'Active'}</p>
                        <div className="product-actions">
                            {product.quantity > 0 && product.status !== 'INACTIVE' && (
                                <>
                                    <button
                                        className="add-to-cart"
                                        onClick={() => alert('Add to Cart functionality not yet implemented!')}
                                    >
                                        Add to Cart
                                    </button>
                                    <button
                                        className="buy-now"
                                        onClick={() => alert('Buy Now functionality not yet implemented!')}
                                    >
                                        Buy Now
                                    </button>
                                </>
                            )}
                        </div>
                    </div>

              
                    <div className="product-sidebar">
                        <h3>All Details</h3>
                        <ul>
                            {detailNames.map((detail, index) => (
                                <li key={index}>
                                    <strong>{detail.detailName}:</strong> {detailValues[index]?.value || 'N/A'}
                                </li>
                            ))}
                        </ul>
                    </div>

                 
                </div>
            </div>
        </div>
    );
}

export default Product;
