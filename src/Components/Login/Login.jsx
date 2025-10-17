import React, { useState } from 'react';
import './login.css'; // Import the CSS file
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { BASE_URL } from '../BaseUrl';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Create login data object
        const loginData = {
            email,
            password
        };

        try {
            // Send POST request to Spring Boot login API
            const response = await axios.post(`${BASE_URL}/login`, loginData);
            // Assuming backend sends JWT, username, and role on successful login
            const { jwt, username, role } = response.data;

            // Store JWT, username, and role in localStorage
            localStorage.setItem('authToken', jwt);
            localStorage.setItem('username', username); // Store the username received from backend
           
            setMessage('Login successful!');

            // Redirect to homepage if role is USER, else redirect to dashboard
            if (role === 'USER') {
                navigate('/');  // Redirect to homepage
            } else {
                navigate('/dashboard');  // Redirect to dashboard if the role is ADMIN
            }

        } catch (error) {
            setMessage('Login failed. Please try again.');
            console.error('Login error:', error);
        }
    };

    const goSignup = () => {
        navigate('/signup');
    };

    return (
        <div className="container">
            <div className="login-container"></div>
            <div className="centerContainer">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input 
                            type="email" 
                            id="email" 
                            value={email} 
                            onChange={handleEmailChange} 
                            required 
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input 
                            type="password" 
                            id="password" 
                            value={password} 
                            onChange={handlePasswordChange} 
                            required 
                        />
                    </div>
                    <span className='forget'>Forget Password?</span>
                    <div className="button-group">
                        <span className="button" onClick={goSignup}>SignUp</span>
                        <button type="submit">Login</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;
