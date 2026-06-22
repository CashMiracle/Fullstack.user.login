import React, { useState } from 'react';
import { register } from '../api/auth';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const [form, setForm] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const [showPasswords, setShowPasswords] = useState(false);
  const navigate = useNavigate();

  const capitalize = (value) =>
    value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();

  const handleChange = (e) => {
    const { name, value } = e.target;
    let newValue = value;

    if (name === 'firstName' || name === 'lastName') {
      newValue = capitalize(value);
    }

    setForm({ ...form, [name]: newValue });
    if (error) setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const cleaned = {
      firstName: form.firstName.trim(),
      lastName: form.lastName.trim(),
      email: form.email.trim().toLowerCase(),
      password: form.password.trim(),
      confirmPassword: form.confirmPassword.trim()
    };

    // 1. HIGHEST PRIORITY: Validate email format
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(cleaned.email)) {
      setError('Please enter a valid email address (e.g., name@domain.com).');
      return;
    }

    // 2. MEDIUM PRIORITY: Password minimum length
    if (cleaned.password.length < 8) {
      setError('Password must be at least 8 characters long.');
      return;
    }

    // 3. LOWEST PRIORITY: Passwords match
    if (cleaned.password !== cleaned.confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    try {
      await register({
        firstName: cleaned.firstName,
        lastName: cleaned.lastName,
        email: cleaned.email,
        password: cleaned.password
      });
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.error || 'Registration failed');
    }
  };

  return (
    <div>
      <h2>Register</h2>
      {error && <div style={{ color: 'red' }}>{error}</div>}
      {/* 👇 Added noValidate to disable browser validation */}
      <form onSubmit={handleSubmit} noValidate>
        <input
          name="firstName"
          placeholder="First Name"
          value={form.firstName}
          onChange={handleChange}
          required
        />
        <input
          name="lastName"
          placeholder="Last Name"
          value={form.lastName}
          onChange={handleChange}
          required
        />
        <input
          name="email"
          type="email"           // Keep type="email" for mobile keyboard, but noValidate prevents browser validation
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <input
          name="password"
          type={showPasswords ? 'text' : 'password'}
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />

        <input
          name="confirmPassword"
          type={showPasswords ? 'text' : 'password'}
          placeholder="Confirm Password"
          value={form.confirmPassword}
          onChange={handleChange}
          required
        />

        <button type="button" onClick={() => setShowPasswords(!showPasswords)}>
          {showPasswords ? 'Hide Passwords' : 'Show Passwords'}
        </button>

        <button type="submit">Register</button>
      </form>
      <p>Already have an account? <a href="/login">Login</a></p>
    </div>
  );
};

export default Register;
