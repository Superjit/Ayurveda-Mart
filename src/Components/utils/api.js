// utils/api.js
import axios from 'axios';
import { user_Url } from '../user_Url';

export const fetchAddresses = async (email, token) => {
  try {
    const response = await axios.get(
      `${user_Url}/customer/address?email=${email}`,
      { headers: { Authorization: `Bearer ${token}` } }
    );
    return response.data;
  } catch (error) {
    console.error('Error fetching addresses:', error);
    throw error;
  }
};
