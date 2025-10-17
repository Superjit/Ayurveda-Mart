import React, { useEffect, useState } from 'react';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
} from 'chart.js';
import { Pie, Bar, Line } from 'react-chartjs-2';
import './Dashboard.css';
import { BASE_URL } from '../../BaseUrl'; // Ensure the correct URL for your backend
import { jwtDecode } from 'jwt-decode';

// Register required components
ChartJS.register(
  ArcElement,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement
);

function Dashboard() {
  const [totalPrice, setTotalPrice] = useState(null);
  const [pendingOrders, setPendingOrders] = useState(null);
  const [deliverdOrders, setDeliverdOrders] = useState(null);
  const [totalOrders, setTotalQuantityOrders] = useState(null);
  const [totalCanceled, setotalCanceled] = useState(0);
  const [PendingOrdersPrice, setPendingOrdersPrice] = useState(0);
  const [DeliverdOrdersPrice, setDeliverdOrdersPrice] = useState(0);
  const [monthlyRevenue, setMonthlyRevenue] = useState([]);
  const [weeklyRevenue, setWeeklyRevenue] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('authToken');

    const decodedToken = jwtDecode(token);

    
    fetch(`${BASE_URL}/dashboard/findStatisticsByProductIds/${decodedToken.sub}`)
      .then(response => response.json()) // Ensure the response is JSON
      .then(data => {
       console.log(data);
        setTotalPrice(data[0]);
        setPendingOrders(data[1]);
        setPendingOrdersPrice(data[2]);
        setDeliverdOrders(data[3]);
        setDeliverdOrdersPrice(data[4]);
        setotalCanceled(data[5]);
        setTotalQuantityOrders(data[6]);
        
       
        // Set the total price in state
      })
      .catch(error => {
        console.error('Error fetching total price:', error);
      });

      fetch(`${BASE_URL}/dashboard/getTotalRevenueByMonth/${decodedToken.sub}`, {
        method: "GET", // Use GET since your endpoint doesn't require a body
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.json();
        })
        .then((data) => {
          // Transform the data coming in as arrays of strings to the desired format
          console.log(data);
      
          const transformedData = data.map((item) => {
            // Match the values using regular expressions to extract the month and revenue
            const monthMatch = item.match(/Month: (\d+)/); // Extract month (e.g., "1" from "Month: 1")
            const revenueMatch = item.match(/Revenue: (\d+(\.\d+)?)/); // Extract revenue (e.g., "12665.0" from "Revenue: 12665.0")
      
            return {
              month: monthMatch ? parseInt(monthMatch[1], 10) : null, // Extract and convert month to integer
              totalRevenue: revenueMatch ? parseFloat(revenueMatch[1]) : null, // Extract and convert revenue to float
            };
          });
      
          console.log("Transformed Data:", transformedData);
          setMonthlyRevenue(transformedData); // Set state or use the transformed data as required
        })
        .catch((error) => {
          console.error("Error fetching monthly revenue:", error);
        });
      
        fetch(`${BASE_URL}/dashboard/getTotalRevenueByDayOfWeek/${decodedToken.sub}`, {
          method: "GET", // Use GET since your endpoint doesn't require a body
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        })
          .then((response) => response.json())
          .then((data) => {
            // Transform the data coming in as strings to the desired format
            console.log(data);
    
            const transformedData = data.map((item) => {
              const dayMatch = item.match(/Day: (\w+)/); // Extract day (e.g., "Monday")
              const revenueMatch = item.match(/Revenue: (\d+(\.\d+)?)/); // Extract revenue (e.g., "12665.0")
              
              return {
                day: dayMatch ? dayMatch[1] : null, // Extract day name
                totalRevenue: revenueMatch ? parseFloat(revenueMatch[1]) : null, // Extract revenue
              };
            });
    
            console.log("Transformed Weekly Revenue:", transformedData);
            setWeeklyRevenue(transformedData); // Set state with the transformed data
          })
          .catch((error) => {
            console.error("Error fetching weekly revenue:", error);
          });

    // Additional fetch requests for other metrics like total orders, delivered, canceled, etc.
  }, []);

  const pieData = {
    labels: ['Delivered', 'Canceled', 'Pending'],
    datasets: [
      {
        data: [deliverdOrders, totalCanceled, pendingOrders ],
        backgroundColor: ['#4CAF50', '#F44336', '#FFC107'],
      },
    ],
  };

  
  const weeklyLabels = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  const weeklyRevenueData = new Array(7).fill(0); // Initialize with 0 for all days

  weeklyRevenue.forEach(({ day, totalRevenue }) => {
    const dayIndex = weeklyLabels.indexOf(day.slice(0, 3)); // Convert full day to short form (e.g., 'Monday' -> 'Mon')
    if (dayIndex >= 0) {
      weeklyRevenueData[dayIndex] = totalRevenue; // Set revenue for the correct day
    }
  });
  const weeklyRevenueChartData = {
    labels: weeklyLabels,
    datasets: [
      {
        label: 'Weekly Revenue',
        data: weeklyRevenueData,
        borderColor: '#4CAF50',
        backgroundColor: 'rgba(76, 175, 80, 0.2)', // Light green for background
      },
    ],
  };

  const monthlyLabels = [
    'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
  ];
  
  const revenueData = new Array(12).fill(0); // Initialize with 0 for all months

  // Map backend data to the corresponding months
  monthlyRevenue.forEach(({ month, totalRevenue }) => {
    revenueData[month - 1] = totalRevenue; // Adjust month index (1-based to 0-based)
    
  });

  const monthlyRevenueData = {
    labels: monthlyLabels,
    datasets: [
      {
        label: 'Revenue',
        data: revenueData,
        backgroundColor: [
           '#33FF57', '#3357FF', '#FF33A6', '#FF8C33',
          '#57FF33', '#33D4FF', '#8C33FF', '#FF33FF', '#57A6FF',
          '#FF5733', '#33FF57','#FF5733', // Repeat or define a pattern of colors as needed
        ], // Array of colors for each month
      },
    ],
  };


  return (
    <div className="dashboard-container">
      <div className="metrics-container">
        <div className="metric-card">
          <h3>Total Quantity of Products Orders</h3>
          <p>{totalOrders}</p>
        </div>
        <div className="metric-card">
          <h3>Total Delivery</h3>
          <p>{deliverdOrders}</p>
          <hr />
          <p>₹{DeliverdOrdersPrice}</p>
        </div>
        <div className="metric-card">
          <h3>Total Canceled</h3>
          <p>{totalCanceled}</p>
        </div>
        <div className="metric-card">
          <h3>Total Revenue</h3>
          <p>₹{totalPrice}</p>
        </div>
        {/* <div className="metric-card">
                    <h3>Total Revenue</h3>
                    <p>${totalRevenue}</p>
                </div> */}

        <div className="metric-card">
          <h3>Pending Orders</h3>
          <p>{pendingOrders}</p>
          <hr />
          <p>₹{PendingOrdersPrice}</p>
        </div>
      </div>

      <div className="charts-container">
        <div className="chart-block" key="pie-chart">
          <h3>Orders Overview</h3>
          <Pie data={pieData} />
        </div>
        <div className="chart-block" key="line">
          <h3>Weekly Orders</h3>
          <Line data={weeklyRevenueChartData} />
        </div>
        <div className="chart-block" key="bar-revenue-chart">
          <h3>Monthly Revenue</h3>
          <Bar data={monthlyRevenueData} />
        </div>
      
      </div>
    </div>
  );
}

export default Dashboard;
