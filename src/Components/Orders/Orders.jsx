import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { user_Url } from '../user_Url';
import { jwtDecode } from 'jwt-decode';
import './Orders.css'; // Custom CSS for order history styling

const Orders = () => {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
       fetchOrderHistory();
    }, []);

    const fetchOrderHistory = ()=>{
        const token = localStorage.getItem('authToken');
        if (token) {
            try {
                const decodedToken = jwtDecode(token);
                const userEmail = decodedToken.sub;

                axios
                    .get(`${user_Url}/buyProduct/orders?userEmail=${userEmail}`, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            Accept: 'application/json',
                        },
                    })
                    .then((response) => {
                        const { products, buyProducts, images, addresses } = response.data;
                        console.log(response.data);
                        
                        const history = buyProducts.map((buyProduct, index) => ({
                            ...buyProduct,
                            productName: products[index]?.name || 'Unknown Product',
                            productPrice: products[index]?.price || 'N/A',
                            image: images[index] ? `data:image/jpeg;base64,${images[index]}` : null,
                            address: addresses[index] || { street: '', city: '', state: '' },
                            deliveredDate: buyProduct.deliveredDate,
                        }));

                        setOrders(history);
                    })
                    .catch((error) => {
                        console.error('Error fetching orders:', error);
                    });
            } catch (error) {
                console.error('Error decoding token:', error);
            }
        }
    }

    const formatDate = (date) => {
        if (!date) return 'N/A';
        return new Date(date).toLocaleString();
    };

    const getStepClass = (status, step) => {
        const steps = ['Pending', 'Dispatched', 'Delivered Successfully'];
        const currentStep = steps.indexOf(status);
        return currentStep >= step ? 'active-step' : '';
    };

    const handleCancelOrder = (orderId) => {
        const token = localStorage.getItem('authToken');
        if (token) {
            axios
                .post(
                    `${user_Url}/buyProduct/cancelOrder/${orderId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            Accept: 'application/json',
                        },
                    }
                )
                .then((response) => {
                    console.log(response.data);

                    if (response.data) {
                        alert('Order canceled successfully!');
                        fetchOrderHistory();
                        setOrders((prevOrders) =>
                            prevOrders.map((order) =>
                                order.id === orderId ? { ...order, status: 'Cancelled' } : order
                            )
                        );    
                    }else{
                        alert('Order cannot canceled canceled ...!');
                        fetchOrderHistory();
                    }
                    
                })
                .catch((error) => {
                    console.error('Error canceling order:', error);
                });
        }
    };

    return (
        <div className="container55  container py-5">
            <h1 className="text-center mb-4">Order History</h1>
            {orders.length > 0 ? (
                <div className="order-history">
                    {orders.map((order, index) => (
                        <div key={index} className="order-card shadow-sm border-light mb-4">
                            <div className="row g-0">
                                <div className="col-md-3">
                                    {order.image ? (
                                        <img
                                            src={order.image}
                                            alt={order.productName}
                                            className="img-fluid rounded-start"
                                            style={{ maxHeight: '200px', objectFit: 'cover' }}
                                        />
                                    ) : (
                                        <div className="placeholder-image">No Image Available</div>
                                    )}
                                </div>
                                <div className="col-md-9">
                                    <div className="card-body">
                                        <h5 className="card-title">{order.productName}</h5>
                                        <p className="card-text">
                                            <strong>Price : </strong> ₹{order.productPrice}
                                        </p>
                                        <p className="card-text">
                                            <strong>Quantity : </strong> {order.quantity}
                                        </p>
                                        <p className="card-text">
                                            <strong>Ordered On : </strong> {formatDate(order.buyDate)}
                                        </p>
                                        <p className="card-text">
                                            <strong>Delivery Address : </strong>{' '}
                                            {order.address
                                                ? `${order.address.street}, ${order.address.city}, ${order.address.state}`
                                                : 'No Address'}
                                        </p>
                                        <p className="card-text">
                                            <strong>Status : </strong>{' '}
                                            <span
                                                className={`badge ${
                                                    order.status === 'Delivered Successfully'
                                                        ? 'bg-success'
                                                        : order.status === 'Cancelled'
                                                        ? 'bg-danger'
                                                        : 'bg-warning text-dark'
                                                }`}
                                            >
                                                {order.status}
                                            </span>
                                        </p>

                                        {/* Cancel Button */}
                                        {order.status === 'Pending' && (
                                            <button
                                                className="btn btn-danger btn-sm mb-2"
                                                onClick={() => handleCancelOrder(order.buyProductId)}
                                            >
                                                Cancel Order
                                            </button>
                                        )}

                                        {/* Step Progress Bar */}
                                        <div className="step-progress">
                                            <div className={`step ${getStepClass(order.status, 0)}`}>
                                                <div className="circle">1</div>
                                                <p>Ordered</p>
                                            </div>
                                            <div className={`step ${getStepClass(order.status, 1)}`}>
                                                <div className="circle">2</div>
                                                <p>Dispatched</p>
                                            </div>
                                            <div className={`step ${getStepClass(order.status, 2)}`}>
                                                <div className="circle">3</div>
                                                <p>Delivered</p>
                                            </div>
                                        </div>

                                        {/* Show Delivered Date */}
                                        {(order.status === 'Dispatched' || order.status === 'Delivered Successfully')  && order.dispatchedDate && (
                                            <p className="card-text">
                                                <strong>Dispatched On : </strong> {formatDate(order.dispatchedDate)}
                                            </p>
                                        )}
                                        {order.status === 'Delivered Successfully' && order.deliveredDate && (
                                            <p className="card-text">
                                                <strong>Delivered On : </strong> {formatDate(order.deliveredDate)}
                                            </p>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            ) : (
                <p className="text-center">You have no orders yet.</p>
            )}
        </div>
    );
};

export default Orders;
