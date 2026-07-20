import { useState, useEffect } from 'react';
import { Typography, Box, Card, CardContent, Button } from '@mui/material';
import type { User, Review } from '../types';
import { getReviewsByUserId } from '../api/reviews';
import ReviewList from '../components/ReviewList';

interface Props {
	user: User;
	onLogout: () => void;
}

export default function ProfilePage({ user, onLogout }: Props) {
	const [reviews, setReviews] = useState<Review[]>([]);

	useEffect(() => {
		getReviewsByUserId(user.id).then(setReviews);
	}, [user.id]);

	return (
		<Box sx={{ py: 3, maxWidth: 800 }}>
			<Typography variant="h4" gutterBottom>
				Профиль
			</Typography>

			<Card sx={{ mb: 3 }}>
				<CardContent>
					<Typography variant="h6">{user.username}</Typography>
					<Typography color="text.secondary">{user.email}</Typography>
					<Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
						Зарегистрирован: {new Date(user.createdAt).toLocaleDateString('ru-RU')}
					</Typography>
					<Button variant="outlined" color="error" onClick={onLogout} sx={{ mt: 2 }}>
						Выйти
					</Button>
				</CardContent>
			</Card>

			<Typography variant="h5" gutterBottom>
				Мои отзывы ({reviews.length})
			</Typography>
			<ReviewList reviews={reviews} currentUser={user} onRefresh={() => {
				getReviewsByUserId(user.id).then(setReviews);
			}} />
		</Box>
	);
}
