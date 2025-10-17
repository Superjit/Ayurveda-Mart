import React from 'react';
import './AboutUs.css';

const AboutUs = () => {
  return (
    <div className="container mt-5">
    <h1 className="text-center mb-4">About Us</h1>
    <div className="row align-items-center">
      {/* Description Section */}
      <div className=" text-white">
        <p className='text-white'>
          Welcome to Ayurveda Mart, your trusted source for natural and organic Ayurvedic products. 
          We are committed to providing you with high-quality, pure, and authentic products that promote 
          health, wellness, and vitality. Our range includes herbal remedies, skincare products, wellness 
          teas, and much more, all designed to help you live a balanced and healthy life.
        </p>
        <p className='text-white'>
          At Ayurveda Mart, we believe in the ancient wisdom of Ayurveda and its ability to heal and restore. 
          Our mission is to bring the healing power of nature to your doorstep and support you on your wellness journey. 
          With years of expertise in Ayurvedic practices, we carefully source our products to ensure they meet the 
          highest standards of quality and efficacy.
        </p>
      </div>

      {/* Image Section */}
      <div className=" text-center ">
        <img 
          src='AyurvedaMart.png' 
          alt="Ayurveda Mart" 
          className="img-fluid rounded shadow"
          style={{ maxHeight: '300px', objectFit: 'cover' }}
        />
      </div>
    </div>
  </div>
);
};

export default AboutUs;