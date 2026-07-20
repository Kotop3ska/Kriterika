import { Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function NotFoundPage() {
	const navigate = useNavigate();
	return (
		<Box sx={{ py: 10, textAlign: 'center' }}>
			<Typography variant="h1" color="text.secondary">404</Typography>
			<Typography variant="h5" gutterBottom>Страница не найдена</Typography>
			<Button variant="contained" onClick={() => navigate('/')}>
				На главную
			</Button>
		</Box>
	);
}
