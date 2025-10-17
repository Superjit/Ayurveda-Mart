import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import './App.css';

// Common Components
import Navbar from './Components/Navbar/Navbar';
import Signup from './Components/SignUp/Signup';
import Login from './Components/Login/Login';

// Admin Components
import Dashboard from './Components/Admin/Dash_board/Dashboard';
import AddCategory from './Components/Admin/addCategory/AddCategory';
import Category from './Components/Admin/category/Category';
import AddProduct from './Components/Admin/AddProduct/AddProduct';
import AddProductFormData from './Components/Admin/AddProduct/AddProductFormData/AddProductFormData';
import AdditionalDetailsForm from './Components/Admin/AddProduct/AdditionalDetailsForm/AdditionalDetailsForm';
import ImageUpload from './Components/Admin/AddProduct/ImageUploade/ImageUpload';
import AllProduct from './Components/Admin/AllProducts/AllProduct';
import UpdateProduct from './Components/Admin/UpdateProduct/UpdateProduct';

// User Components
import Home from './Components/User/Home/AyurvedaHome';
import Shop from './Components/User/Shop/Shop';
import { user_Url } from './Components/user_Url';
import Cart from './Components/User/Cart/Cart';
import CustomerInfo from './Components/CustomerInfo/CustomerInfo';
import ProductDetails from './Components/ProductDetails/ProductDetails';
import Orders from './Components/Orders/Orders';
import Product from './Components/indivisualProduct/Product';
import UpdateImage from './Components/Admin/UpdateImage/UpdateImage';
import AboutUs from './Components/AboutUs/AboutUs';
import ContactUs from './Components/ContactUs/ContactUs';
import LatestOrder from './Components/Admin/LatestOrder/LatestOrder';
import Footer from './Components/Footer/Footer';

// Fallback Components
const NotFound = () => <h2>404 - Page Not Found</h2>;
const NotAuthorized = () => <h2>403 - Not Authorized</h2>;

// Helper Function to Get Role from Token
const getRoleFromToken = () => {
  const token = localStorage.getItem('authToken');
  if (!token) return null;
  try {
    const decodedToken = jwtDecode(token);
    return decodedToken.roles[0] || null; // Assuming 'roles' is an array in the token payload
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
};

// Role-Based Route Guard
const ProtectedRoute = ({ children, requiredRole }) => {
  const userRole = getRoleFromToken();
  if (!userRole) {
    return <Navigate to="/login" />;
  }
  if (userRole !== requiredRole) {
    return <NotAuthorized />;
  }
  return children;
};

function App() {
  const [cartCount, setCartCount] = useState(0);
  const [useremail, setUseremail] = useState('');

  useEffect(() => {
    const fetchCartCount = async () => {
      const token = localStorage.getItem('authToken');
      if (token) {
        const useremail1 = jwtDecode(token)?.sub; // Assuming 'sub' contains email in token
        setUseremail(useremail1);
        if (useremail) {
          try {
            const response = await axios.get(`${user_Url}/cart/count?email=${useremail}`, {
              headers: { Authorization: `Bearer ${token}` },
            });
            setCartCount(response.data.count); // Assuming 'count' is in the response
          } catch (error) {
            console.error('Error fetching cart count:', error);
          }
        }
      }
    };
    fetchCartCount();
  }, []);

  return (
    <Router>
      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<div className="App"><Login /></div>} />
        <Route path="/signup" element={<div className="App"><Signup /></div>} />
        <Route path="/" element={<><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><Home /></div><Footer/></>} />
        <Route path="/shop" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><Shop cartCount={cartCount} setCartCount={setCartCount} /><Footer/></div>} />
        <Route path="/cart" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><Cart cartCount={cartCount} setCartCount={setCartCount} /><Footer/></div>} />
        <Route path="/customerInfo" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><CustomerInfo cartCount={cartCount} setCartCount={setCartCount} /><Footer/></div>} />
        <Route path="/product-details" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><ProductDetails cartCount={cartCount} setCartCount={setCartCount} /><Footer/></div>} />
        <Route path="/orders" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><Orders/><Footer/></div>} />
        <Route path="/product" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><Product/><Footer/></div>} />

        <Route path="/about-us" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><AboutUs/><Footer/></div>} />
        <Route path="/contact" element={<div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><ContactUs/><Footer/></div>} />
       
       
       
        {/* Admin Routes */}
        <Route path="/dashboard" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><Dashboard /></div></div></ProtectedRoute>} />
        <Route path="/add-product" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><AddProduct /></div></div></ProtectedRoute>} />
        <Route path="/addproduct" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><AddProductFormData /></div></div></ProtectedRoute>} />
        <Route path="/add-product-details" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><AdditionalDetailsForm /></div></div></ProtectedRoute>} />
        <Route path="/image-upload" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><ImageUpload /></div></div></ProtectedRoute>} />
        <Route path="/add-category" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><AddCategory /></div></div></ProtectedRoute>} />
        <Route path="/categories" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><Category /></div></div></ProtectedRoute>} />
        <Route path="/AllProduct" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar cartCount={cartCount} setCartCount={setCartCount} /><div className="adminContainerBody"><AllProduct /></div></div></ProtectedRoute>} />
        <Route path="/updateProduct" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar  /><div className="adminContainerBody"><UpdateProduct /></div></div></ProtectedRoute>} />
        <Route path="/updateProductImages" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar  /><div className="adminContainerBody"><UpdateImage /></div></div></ProtectedRoute>} />
        <Route path="/latestorders" element={<ProtectedRoute requiredRole="ROLE_ADMIN"><div className="App"><Navbar  /><div className="adminContainerBody"><LatestOrder /></div></div></ProtectedRoute>} />

        {/* Fallback */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
}

export default App;
