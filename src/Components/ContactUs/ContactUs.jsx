import React, { useState } from 'react';
import './ContactUs.css';

const ContactUs = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Thank you for contacting us! We will get back to you soon.');
    setFormData({ name: '', email: '', message: '' });
  };

  return (
    <div className="container mt-5">
    <h1 className="text-center mb-4">Contact Us</h1>
    <div className="row justify-content-center">
      {/* Contact Form Section */}
      <div className="col-md-6">
        <div className="card shadow-lg p-4">
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Your Name"
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Your Email"
                className="form-control"
                required
              />
            </div>
            <div className="mb-3">
              <textarea
                name="message"
                value={formData.message}
                onChange={handleChange}
                placeholder="Your Message"
                className="form-control"
                rows="4"
                required
              />
            </div>
            <button type="submit" className="btn btn-primary w-100">Send Message</button>
          </form>
        </div>
      </div>

      {/* Contact Info Section */}
      <div className="col-md-6 mt-4 mt-md-0">
        <div className="card p-4 shadow-lg">
          <h3 className="mb-3">Get in Touch</h3>
          <p>For any inquiries or support, feel free to reach out to us through the form, or contact us directly using the details below:</p>
          <p><strong>Email:</strong> support@ayurvedamart.com</p>
          <p><strong>Phone:</strong> +1 (123) 456-7890</p>
        </div>
      </div>
    </div>
  </div>
);
};

export default ContactUs;