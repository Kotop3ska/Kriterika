import { Card, CardContent, Typography, Box, IconButton, Rating } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import type { Review, User } from '../types';
import { deleteReview } from '../api/reviews';

interface Props {
	reviews: Review[];
	currentUser: User | null;
	onRefresh: () => void;
}

export default function ReviewList({ reviews, currentUser, onRefresh }: Props) {
	const handleDelete = async (reviewId: number) => {
		if (!confirm('Удалить отзыв?')) return;
		await deleteReview(reviewId);
		onRefresh();
	};

	if (reviews.length === 0) {
		return (
			<Typography color="text.secondary" sx={{ py: 2 }}>
				Пока нет отзывов
			</Typography>
		);
	}

	return (
		<Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
			{reviews.map((review) => (
				<Card key={review.id}>
					<CardContent>
						<Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
							<Box>
								<Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
									{review.username || `Пользователь #${review.userId}`}
								</Typography>
								<Rating value={review.rating} readOnly size="small" />
							</Box>
							{currentUser?.id === review.userId && (
								<Box>
									<IconButton size="small" color="primary">
										<EditIcon fontSize="small" />
									</IconButton>
									<IconButton size="small" color="error" onClick={() => handleDelete(review.id)}>
										<DeleteIcon fontSize="small" />
									</IconButton>
								</Box>
							)}
						</Box>
						{review.title && (
							<Typography variant="subtitle2" sx={{ mt: 1 }}>
								{review.title}
							</Typography>
						)}
						{review.body && (
							<Typography variant="body2" color="text.secondary" sx={{ mt: 0.5 }}>
								{review.body}
							</Typography>
						)}
						{review.criteriaRatings && review.criteriaRatings.length > 0 && (
							<Box sx={{ mt: 1, display: 'flex', gap: 1, flexWrap: 'wrap' }}>
								{review.criteriaRatings.map((cr) => (
									<Box key={cr.criterionId} sx={{ display: 'flex', alignItems: 'center', gap: 0.5 }}>
										<Typography variant="caption" color="text.secondary">
											{cr.criterionName || `#${cr.criterionId}`}
										</Typography>
										<Typography variant="caption" sx={{ fontWeight: 'bold' }}>
											{cr.rating}
										</Typography>
									</Box>
								))}
							</Box>
						)}
						<Typography variant="caption" color="text.secondary" sx={{ mt: 1, display: 'block' }}>
							{new Date(review.createdAt).toLocaleDateString('ru-RU')}
						</Typography>
					</CardContent>
				</Card>
			))}
		</Box>
	);
}
