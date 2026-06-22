import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const [user, setUser] = useState({ firstName: '', lastName: '', email: '' });
  const navigate = useNavigate();

  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (userData) {
      setUser(JSON.parse(userData));
    } else {
      navigate('/login');
    }
  }, [navigate]);

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user'); 
    navigate('/login');
  };

  return (
    <div>
      <h1>Welcome to your Dashboard</h1>
      <p><strong>Full Name:</strong> {user.firstName} {user.lastName}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <button onClick={logout}>Logout</button>
    </div>
  );
};

export default Dashboard;
