// services/authService.js
import axios from 'axios';
const apiClient = axios.create({
    baseURL: 'http://localhost:5084',
    headers: {
      'Content-Type': 'application/json',  
    },
  });
export async function AuthService(DataLogin) {
    try {
        const response = await apiClient.post('/api/auth/loginconfỉm',DataLogin ); 
        return response;
    } catch (error) {
        console.error("Error adding game:", error.response || error.message);
        return null; 
    }
}
export async function AuthNavigateAdmin(token) {
  try {
      const response = await apiClient.post('/api/auth/check',token ); 
      return response;
  } catch (error) {
      console.error("Error adding game:", error.response || error.message);
      return null; 
  }
}



    