import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { BASE_URL } from '../../BaseUrl';
import './LatestOrder.css';
import { Navigate } from 'react-router-dom';

const LatestOrder = () => {
    const [latestOrder, setLatestOrder] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchLeetestOrder();
    }, []);  // Dependency array will run the effect when email changes

    const fetchLeetestOrder = () => {
        const token = localStorage.getItem('authToken');
        const decodedToken = jwtDecode(token);

        // Fetch the latest order data without async/await
        axios.get(`${BASE_URL}/dashboard/findPendingProductsWithAddresses/${decodedToken.sub}`)
            .then((response) => {
                setLatestOrder(response.data);
                console.log(response.data);

                setLoading(false);  // Data fetched successfully, stop loading
            })
            .catch((err) => {
                setError('Failed to fetch data');
                setLoading(false);  // Stop loading on error
            });
    }

    const handleDispatched = (orderId) => {
        // Send PUT request to mark order as dispatched
        axios
            .put(`${BASE_URL}/dashboard/dispatch/${orderId}`)
            .then((response) => {
                console.log(`Order ${orderId} marked as dispatched`);
                fetchLeetestOrder(); // Refresh data after update
            })
            .catch((err) => {
                console.error('Failed to mark as dispatched', err);
            });
    };

    const handleDelivered = (orderId) => {
        // Send PUT request to mark order as delivered
        axios.put(`${BASE_URL}/dashboard/deliver/${orderId}`)
            .then((response) => {
                console.log(`Order ${orderId} marked as delivered`);
                // You can update the state or refresh the data here if necessary
                fetchLeetestOrder();
            })
            .catch((err) => {
                console.error('Failed to mark as delivered', err);
            });
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div className="container my-5 latest-order-container0">
            <h3 className="text-center textHead mb-4">Latest Pending Orders</h3>
            {latestOrder.length > 0 ? (
                latestOrder.map((order, index) => (
                    <div key={index} className="card0 mb-4 shadow-sm">
                        <div className="card-body0">
                            <p><strong>Product ID:</strong> {order.productId}</p>
                            <p><strong>Order ID:</strong> {order.buyProductId}</p>
                            <p><strong>Category:</strong> {order.categoryName}</p>
                            <p><strong>Quantity:</strong> {order.quantity}</p>
                            <p><strong>Status:</strong> {order.status}</p>
                            <p><strong>Price:</strong> ${order.price}</p>
                            <h5 className="mt-3">Address Details:</h5>
                            <p><strong>Name:</strong> {order.firstName} {order.lastName}</p>
                            <p><strong>Email:</strong> {order.addressEmail}</p>
                            <p><strong>Street:</strong> {order.street}</p>
                            <p><strong>City:</strong> {order.city}</p>
                            <p><strong>State:</strong> {order.state}</p>
                            <p><strong>Postal Code:</strong> {order.postalCode}</p>
                            <div className='btnContainerForLatest'>

                                <button
                                    className="btn btn-warning me-3"
                                    onClick={() => handleDispatched(order.buyProductId)}
                                    disabled={order.status === 'Dispatched' || order.status === 'Delivered'}
                                >
                                    Mark as Dispatched
                                </button>
                                <button
                                    className="btn btn-success"
                                    onClick={() => handleDelivered(order.buyProductId)}
                                >
                                    Mark as Delivered
                                </button>

                            </div>
                        </div>
                    </div>
                ))
            ) : (
                <p className="text-center0 text-muted">No pending orders found.</p>
            )}
        </div>
    );
};

export default LatestOrder;
