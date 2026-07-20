import { useState } from 'react';
import { Card, CardContent, Typography, Box, IconButton, Rating, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Slider } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Link } from 'react-router-dom';
import type { Review, User } from '../types';
import { deleteReview, updateReview } from '../api/reviews';

interface Props {
	reviews: Review[];
	currentUser: User | null;
	onRefresh: () => void;
}

export default function ReviewList({ reviews, currentUser, onRefresh }: Props) {
	const [editOpen, setEditOpen] = useState(false);
	const [editingReview, setEditingReview] = useState<Review | null>(null);
	const [editRating, setEditRating] = useState(8);
	const [editTitle, setEditTitle] = useState('');
	const [editBody, setEditBody] = useState('');

	const handleDelete = async (reviewId: number) => {
		if (!confirm('Удалить отзыв?')) return;
		await deleteReview(reviewId);
		onRefresh();
	};

	const handleEditOpen = (review: Review) => {
		setEditingReview(review);
		setEditRating(review.rating);
		setEditTitle(review.title || '');
		setEditBody(review.body || '');
		setEditOpen(true);
	};

	const handleEditSave = async () => {
		if (!editingReview) return;
		await updateReview(editingReview.id, {
			rating: editRating,
			title: editTitle,
			body: editBody,
		});
		setEditOpen(false);
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
								<Typography variant="caption" color="text.secondary" component={Link} to={`/film/${review.filmId}`} sx={{ display: 'block', mt: 0.5, color: 'primary.main', textDecoration: 'none' }}>
									Фильм #{review.filmId} →
								</Typography>
							</Box>
							{currentUser?.id === review.userId && (
								<Box>
									<IconButton size="small" color="primary" onClick={() => handleEditOpen(review)}>
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

			<Dialog open={editOpen} onClose={() => setEditOpen(false)} maxWidth="sm" fullWidth>
				<DialogTitle>Редактировать отзыв</DialogTitle>
				<DialogContent sx={{ maxHeight: '70vh', overflowY: 'auto' }}>
					<Typography gutterBottom>Оценка: {editRating}/10</Typography>
					<Slider value={editRating} onChange={(_, v) => setEditRating(v as number)} min={1} max={10} step={1} marks valueLabelDisplay="auto" sx={{ mb: 2 }} />
					<TextField fullWidth label="Заголовок" value={editTitle} onChange={(e) => setEditTitle(e.target.value)} sx={{ mb: 2 }} />
					<TextField fullWidth multiline rows={3} label="Текст отзыва" value={editBody} onChange={(e) => setEditBody(e.target.value)} sx={{ mb: 2 }} />
				</DialogContent>
				<DialogActions>
					<Button onClick={() => setEditOpen(false)}>Отмена</Button>
					<Button variant="contained" onClick={handleEditSave}>Сохранить</Button>
				</DialogActions>
			</Dialog>
		</Box>
	);
}
