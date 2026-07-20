import { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { Box, TextField, Button, Typography, Link, Alert } from '@mui/material';
import { login } from '../api/auth';

interface Props {
	onLogin: (token: string, user: any) => void;
}

export default function LoginPage({ onLogin }: Props) {
	const navigate = useNavigate();
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError('');
		setLoading(true);
		try {
			const data = await login(email, password);
			onLogin(data.token, data.user);
			navigate('/');
		} catch (err: any) {
			setError(err.response?.data?.error || 'Ошибка входа');
		} finally {
			setLoading(false);
		}
	};

	return (
		<Box sx={{ maxWidth: 400, mx: 'auto', py: 5 }}>
			<Typography variant="h4" gutterBottom>
				Вход
			</Typography>
			{error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
			<Box component="form" onSubmit={handleSubmit}>
				<TextField
					fullWidth
					label="Email"
					type="email"
					value={email}
					onChange={(e) => setEmail(e.target.value)}
					margin="normal"
					required
				/>
				<TextField
					fullWidth
					label="Пароль"
					type="password"
					value={password}
					onChange={(e) => setPassword(e.target.value)}
					margin="normal"
					required
				/>
				<Button fullWidth variant="contained" type="submit" disabled={loading} sx={{ mt: 2 }}>
					{loading ? 'Вход...' : 'Войти'}
				</Button>
				<Typography sx={{ mt: 2, textAlign: 'center' }}>
					Нет аккаунта?{' '}
					<Link component={RouterLink} to="/register">
						Зарегистрироваться
					</Link>
				</Typography>
			</Box>
		</Box>
	);
}
