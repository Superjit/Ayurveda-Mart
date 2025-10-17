import React, { useState } from 'react';
import './signup.css'; // Import the CSS file
import axios from 'axios';
import { BASE_URL } from '../BaseUrl';
import { useNavigate } from 'react-router-dom'; // For navigation

function Signup() {
    const [userName, setUserName] = useState('');
    const [mobile_no, setMobile_no] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirm_Password, setConfirm_Password] = useState('');
    const navigate = useNavigate(); // Initialize navigate

    const handleUserName = (event) => setUserName(event.target.value);
    const handleMobile_no = (event) => setMobile_no(event.target.value);
    const handleEmailChange = (event) => setEmail(event.target.value);
    const handlePasswordChange = (event) => setPassword(event.target.value);
    const handleConfirm_PasswordChange = (event) => setConfirm_Password(event.target.value);

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (password !== confirm_Password) {
            alert('Passwords do not match');
            return;
        }

        const userData = {
            username: userName,
            mobileNo: mobile_no,
            email: email,
            password: password,
        };

        try {
            const response = await axios.post(`${BASE_URL}/register`, userData);
            alert(response.data); // Show success message
            navigate('/login'); // Redirect to login page
        } catch (error) {
            console.error('Error registering admin:', error);
            alert('Registration failed. Please try again.'); // Show failure message
        }
    };

    return (
        <div className="containers">
            <div className="centerContainers">
                <form onSubmit={handleSubmit}>
                    <div className="form_group_row1s">
                        <div className="form-group1s">
                            <label htmlFor="username">User Name</label>
                            <input
                                className="inputs"
                                type="text"
                                id="userName"
                                value={userName}
                                onChange={handleUserName}
                                required
                            />
                        </div>
                        <div className="form-group2s">
                            <label htmlFor="mobile_no">Mobile No.</label>
                            <input
                                className="inputs"
                                type="text"
                                id="mobile_no"
                                value={mobile_no}
                                onChange={handleMobile_no}
                                required
                            />
                        </div>
                    </div>
                    <div className="form-groups">
                        <label className="labelEmails" htmlFor="email">Email</label>
                        <input
                            className="inputs"
                            type="email"
                            id="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                    </div>
                    <div className="form_group_row1s">
                        <div className="form-group1s">
                            <label htmlFor="password">Password</label>
                            <input
                                className="inputs"
                                type="password"
                                id="password"
                                value={password}
                                onChange={handlePasswordChange}
                                required
                            />
                        </div>
                        <div className="form-group2s">
                            <label htmlFor="confirm_password">Confirm Password</label>
                            <input
                                className="inputs"
                                type="password"
                                id="confirm_password"
                                value={confirm_Password}
                                onChange={handleConfirm_PasswordChange}
                                required
                            />
                        </div>
                    </div>
                    <div className="button-groups">
                        <button className="btnSubmit" type="submit">SignUp</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Signup;
