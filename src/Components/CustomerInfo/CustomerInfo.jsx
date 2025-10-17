import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { fetchAddresses } from '../utils/api';
import { user_Url } from '../user_Url';
import './CustomerInfo.css'; // Import the CSS file

const CustomerInfo = ({ setCartCount }) => {
  const location = useLocation();
  const { product, image, category } = location.state || { category: 'Unknown Category' };
  console.log('Product:', product);
  console.log('Image:', image);
  console.log('Category:', category);

  const [addresses, setAddresses] = useState([]);
  const [selectedAddressId, setSelectedAddressId] = useState(null);
  const [newAddress, setNewAddress] = useState({
    email: '',
    mobileNumber: '',
    firstName: '',
    lastName: '',
    street: '',
    city: '',
    state: '',
    country: '',
    postalCode: '',
  });
  const [loading, setLoading] = useState(false);
  const [showAddressForm, setShowAddressForm] = useState(false); // New state for toggling form visibility
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      alert('Please log in to continue.');
      return navigate('/login');
    }

    const { sub: email } = jwtDecode(token);
    setNewAddress((prev) => ({ ...prev, email }));

    setLoading(true);
    fetchAddresses(email, token)
      .then((data) => {
        if (Array.isArray(data)) {
          setAddresses(data);
        } else if (data && data.id) {
          setAddresses([data]);
        } else {
          console.error('Invalid response format:', data);
          setAddresses([]);
        }
      })
      .catch((error) => {
        console.error('Failed to fetch addresses:', error);
        alert('Failed to fetch addresses.');
        setAddresses([]);
      })
      .finally(() => setLoading(false));
  }, [navigate]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewAddress((prev) => ({
      ...prev,
      [name]: name === 'mobileNumber' ? value.replace(/\D/g, '') : value,
    }));
  };

  const saveNewAddress = () => {
    if (Object.values(newAddress).some((field) => !field.trim())) {
      alert('All fields are required.');
      return;
    }

    const token = localStorage.getItem('authToken');
    setLoading(true);
    axios
      .post(`${user_Url}/customer/address`, newAddress, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setAddresses((prevAddresses) => [...prevAddresses, response.data]);
        alert('Address saved successfully!');
        setNewAddress({
          email: newAddress.email, 
          mobileNumber: '',
          firstName: '',
          lastName: '',
          street: '',
          city: '',
          state: '',
          country: '',
          postalCode: '',
        });
        setShowAddressForm(false); // Hide the form after saving
      })
      .catch((error) => {
        console.error('Error saving address:', error);
        alert('Failed to save address.');
      })
      .finally(() => setLoading(false));
  };

  const proceedToOrder = () => {
    if (!selectedAddressId) {
      alert('Please select an address.');
      return;
    }

    const selectedAddress = addresses.find((addr) => addr.id === selectedAddressId);
    navigate('/product-details', { state: { product, image, category, selectedAddress } });
  };

  return (
    <div className="customer-info">
      <h2>Customer Information</h2>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <>
          {addresses.length > 0 ? (
            <ul className="address-list">
              {addresses.map((addr) => (
                <li key={addr.id} className="address-item">
                  <input
                    type="radio"
                    name="address"
                    value={addr.id}
                    checked={selectedAddressId === addr.id}
                    onChange={() => setSelectedAddressId(addr.id)}
                  />
                  {addr.street}, {addr.city}, {addr.state}, {addr.country}, {addr.postalCode}
                </li>
              ))}
            </ul>
          ) : (
            <p>No addresses found. Please add a new address.</p>
          )}
        </>
      )}

      <button className="proceed-btn" onClick={proceedToOrder}>Proceed</button>

      {/* Add More Address Button */}
      <button className="add-address-btn" onClick={() => setShowAddressForm(true)}>
        Add More Address
      </button>

      {/* Address Form */}
      {showAddressForm && (
        <div className="address-form-container">
          <h3>Add New Address</h3>
          <form className="address-form">
            {Object.entries(newAddress).map(([key, value]) => (
              <div key={key}>
                <label>{key.replace(/([A-Z])/g, ' $1').toUpperCase()}:</label>
                <input
                  type="text"
                  name={key}
                  value={value}
                  onChange={handleInputChange}
                />
              </div>
            ))}
            <button type="button" className="save-btn" onClick={saveNewAddress}>
              Save
            </button>
            <button type="button" className="cancel-btn" onClick={() => setShowAddressForm(false)}>
              Cancel
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default CustomerInfo;
