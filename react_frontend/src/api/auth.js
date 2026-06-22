import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/auth';

export const register = (userData) => axios.post(`${API_BASE}/register`, userData);
export const login = (credentials) => axios.post(`${API_BASE}/login`, credentials);
