import { useState } from 'react';
import { useNavigate, Link as RouterLink } from 'react-router-dom';
import { Box, TextField, Button, Typography, Link, Alert } from '@mui/material';
import { register } from '../api/auth';

interface Props {
	onLogin: (token: string, user: any) => void;
}

export default function RegisterPage({ onLogin }: Props) {
	const navigate = useNavigate();
	const [email, setEmail] = useState('');
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const [loading, setLoading] = useState(false);

	const handleSubmit = async (e: React.FormEvent) => {
		e.preventDefault();
		setError('');
		setLoading(true);
		try {
			const data = await register(email, username, password);
			onLogin(data.token, data.user);
			navigate('/');
		} catch (err: any) {
			const details = err.response?.data?.details;
			setError(details ? details.join(', ') : err.response?.data?.error || 'Ошибка регистрации');
		} finally {
			setLoading(false);
		}
	};

	return (
		<Box sx={{ maxWidth: 400, mx: 'auto', py: 5 }}>
			<Typography variant="h4" gutterBottom>
				Регистрация
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
					label="Имя пользователя"
					value={username}
					onChange={(e) => setUsername(e.target.value)}
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
					helperText="Минимум 6 символов"
				/>
				<Button fullWidth variant="contained" type="submit" disabled={loading} sx={{ mt: 2 }}>
					{loading ? 'Регистрация...' : 'Зарегистрироваться'}
				</Button>
				<Typography sx={{ mt: 2, textAlign: 'center' }}>
					Уже есть аккаунт?{' '}
					<Link component={RouterLink} to="/login">
						Войти
					</Link>
				</Typography>
			</Box>
		</Box>
	);
}
