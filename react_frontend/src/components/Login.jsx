import React, { useState } from 'react';
import { login } from '../api/auth';
import { useNavigate } from 'react-router-dom';

const Login = () => {
	const [credentials, setCredentials] = useState({ email: '', password: '' });
	const [error, setError] = useState('');
	const navigate = useNavigate();

	const handleChange = (e) => {
		setCredentials({ ...credentials, [e.target.name]: e.target.value });
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			const response = await login(credentials);
			const { token, firstName, lastName, email } = response.data;
			localStorage.setItem('token', token);
			localStorage.setItem('user', JSON.stringify({ firstName, lastName, email }));
			navigate('/dashboard');
		} catch (err) {
			console.error(err);
			setError('Invalid credentials');
		}
	};

	return (
		<div>
		<h2>Login</h2>
		{error && <div style={{ color: 'red' }}>{error}</div>}
		<form onSubmit={handleSubmit}>
		<input
		name="email"
		type="email"
		placeholder="Email"
		onChange={handleChange}
		required
		/>
		<input
		name="password"
		type="password"
		placeholder="Password"
		onChange={handleChange}
		required
		/>
		<button type="submit">Login</button>
		</form>
		<p>Don't have an account? <a href="/register">Register</a></p>
		</div>
	);
};

export default Login;
