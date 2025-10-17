import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import './navbar.css';
import { BASE_URL } from '../BaseUrl';
import { jwtDecode } from 'jwt-decode';
import { user_Url } from '../user_Url';

export default function Navbar({ cartCount, setCartCount }) {
    const [cartCount1, setCartCount1] = useState(0); // Cart count state
    const [username, setUsername] = useState(null);
    const [role, setRole] = useState(null);
    const [categories, setCategories] = useState([]);
    const [showCategories, setShowCategories] = useState(false);
    const [showLogout, setShowLogout] = useState(false); // For showing the logout button
    const navigate = useNavigate();
    const [userRole, setUserRole] = useState(null);
    const [useremail, setUseremail] = useState(null);
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    console.log(userRole);
    
    useEffect(() => {
        const storedUsername = localStorage.getItem('username');
        const token = localStorage.getItem('authToken');
        let decodedToken='';
        if (token) {
            try {
                decodedToken = jwtDecode(token);
                setUseremail(decodedToken.sub); // Assuming the token has a `sub` field for email
                setUserRole(decodedToken.roles[0]); // Assuming the token has a `roles` field
                console.log(decodedToken.roles[0]);
            } catch (error) {
                console.error('Error decoding token:', error);
            }
        }

        if (storedUsername) {
            setUsername(storedUsername);
        }

        // Fetch categories from the backend
        axios
            .get(`${BASE_URL}/admin/categories/name/${decodedToken.sub}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}`, // JWT token
                }
            })
            .then((response) => {
                setCategories(response.data);
            })
            .catch((error) => {
                console.error('Error fetching categories:', error);
            });
    }, []);

    useEffect(() => {
        // Fetch cart count if useremail is available
        if (useremail) {
            axios
                .get(`${user_Url}/cart/count?email=${useremail}`, {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('authToken')}`, // JWT token
                    }
                })
                .then((response) => {
                    if (setCartCount && typeof setCartCount === 'function') {
                        setCartCount(response.data); // Update the cart count in Navbar
                    }
                })
                .catch((error) => {
                    console.error('Error fetching cart count:', error);
                });
        }
    }, []); // Runs whenever useremail is set


    const handleCategoryClick = (category) => {
        navigate('/add-product', { state: { category } });
    };

    const handleLogout = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        localStorage.removeItem('role');
        setUsername(null);
        setRole(null);
        navigate('/login');
    };

    const handleCartClick = () => {
        navigate('/cart'); // Navigate to the cart component
    };
    const toggleMenu = () => setIsMenuOpen((prev) => !prev);
    return (
        <div>
            <div className="container-fluid">
                <nav className="navbar navbar-expand-lg navbar-light">
                    <div className="logo">
                        <div className="logo-box">
                            <img src="logo.png" alt="Logo" />
                        </div>
                        <button
                            className="navbar-toggler"
                            type="button"
                            onClick={toggleMenu}
                            aria-controls="navbarResponsive"
                            aria-expanded={isMenuOpen}
                            aria-label="Toggle navigation"
                        >
                            <span className="navbar-toggler-icon">...</span>
                        </button>

                    </div>
                    <div
                        className={`navbar-collapse ${isMenuOpen ? 'show' : ''}`}
                        id="navbarResponsive"
                    >
                        <div className="search-container d-flex flex-grow-1 mx-4">
                            <input type="text" placeholder="Search..." className="form-control me-2" />
                            <Link to="search" className="search-link">Search</Link>
                        </div>

                        <div className="menu d-flex gap-4">
                            {userRole !== 'ROLE_ADMIN' && <Link to="/shop">Shop</Link>}
                            {userRole !== 'ROLE_ADMIN' && <Link to="/about-us">About Us</Link>}
                            {userRole !== 'ROLE_ADMIN' && <Link to="/contact">Contact</Link>}
                            {userRole === 'ROLE_ADMIN' && <Link to="/AllProduct">All Products</Link>}
                            {userRole !== 'ROLE_ADMIN' && <Link to='/orders'>My Orders</Link>}

                            {userRole !== 'ROLE_ADMIN' && (
                                <div className="cart-icon" onClick={handleCartClick}>
                                    <Link>
                                        <span>Cart</span>
                                    </Link>
                                    {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
                                </div>
                            )}

                            {username ? (
                                <div
                                    className="username-container"
                                    onMouseEnter={() => setShowLogout(true)}
                                    onMouseLeave={() => setShowLogout(false)}
                                >
                                    <span>{username}</span>
                                    {showLogout && (
                                        <button className="logout-button-hover" onClick={handleLogout}>
                                            Logout
                                        </button>
                                    )}
                                </div>
                            ) : (
                                <Link to="/login">Login</Link>
                            )}
                        </div>
            </div >
                </nav>
            </div>

            {userRole === 'ROLE_ADMIN' && (
                <aside className="sidebar">
                    <div className="sidebar-header">Ayurveda Mart</div>
                    <ul className="sidebar-menu list-unstyled">
                        <li><Link to="/dashboard">Dashboard</Link></li>
                        <li><Link to="/latestorders">Latest Orders</Link></li>
                        <li><Link to="/categories">Categories</Link></li>
                        <li><Link to="/add-category">Add Category</Link></li>
                        <li
                            className="add-product-item"
                            onMouseEnter={() => setShowCategories(true)}
                            onMouseLeave={() => setShowCategories(false)}
                        >
                            <Link href="#add-products">Add Products</Link>
                            {showCategories && (
                                <div className="category-hover-box">
                                    <input
                                        type="text"
                                        placeholder="Search Category..."
                                        className="hover-search"
                                    />
                                    <div className="hover-categories">
                                        {categories.map((category, index) => (
                                            <div
                                                className="hover-category"
                                                key={index}
                                                onClick={() => handleCategoryClick(category)}
                                            >
                                                {category}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}
                        </li>
                    </ul>
                </aside>
            )}
        </div>
    );
}
