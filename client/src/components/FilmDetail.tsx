import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
	Box,
	Typography,
	Chip,
	Rating,
	Button,
	Dialog,
	DialogContent,
	CircularProgress,
} from '@mui/material';
import type { Film, Review, User } from '../types';
import { getFilmById, getFilmReviews } from '../api/films';
import ReviewList from './ReviewList';
import ReviewForm from './ReviewForm';

interface Props {
	user: User | null;
}

export default function FilmDetail({ user }: Props) {
	const { id } = useParams<{ id: string }>();
	const navigate = useNavigate();
	const [film, setFilm] = useState<Film | null>(null);
	const [averageRating, setAverageRating] = useState(0);
	const [reviewCount, setReviewCount] = useState(0);
	const [reviews, setReviews] = useState<Review[]>([]);
	const [loading, setLoading] = useState(true);
	const [reviewFormOpen, setReviewFormOpen] = useState(false);

	const filmId = Number(id);

	useEffect(() => {
		if (!filmId) return;
		setLoading(true);
		Promise.all([getFilmById(filmId), getFilmReviews(filmId)])
			.then(([filmData, reviewsData]) => {
				setFilm(filmData.film);
				setAverageRating(filmData.averageRating);
				setReviewCount(filmData.reviewCount);
				setReviews(reviewsData);
			})
			.finally(() => setLoading(false));
	}, [filmId]);

	const handleReviewSaved = () => {
		setReviewFormOpen(false);
		getFilmReviews(filmId).then(setReviews);
		getFilmById(filmId).then((d) => {
			setAverageRating(d.averageRating);
			setReviewCount(d.reviewCount);
		});
	};

	if (loading) {
		return (
			<Box sx={{ display: 'flex', justifyContent: 'center', py: 10 }}>
				<CircularProgress />
			</Box>
		);
	}

	if (!film) {
		return (
			<Box sx={{ py: 5, textAlign: 'center' }}>
				<Typography>Фильм не найден</Typography>
				<Button onClick={() => navigate('/')} sx={{ mt: 2 }}>
					На главную
				</Button>
			</Box>
		);
	}

	return (
		<Box sx={{ py: 3 }}>
			<Box sx={{ display: 'flex', gap: 3, flexWrap: 'wrap' }}>
				<Box sx={{ flexShrink: 0 }}>
					<img
						src={film.posterUrl || 'https://via.placeholder.com/300x450?text=No+Poster'}
						alt={film.nameRu}
						style={{ width: 300, borderRadius: 8 }}
					/>
				</Box>
				<Box sx={{ flex: 1, minWidth: 300 }}>
					<Typography variant="h4" gutterBottom>
						{film.nameRu}
					</Typography>
					{film.nameEn && (
						<Typography variant="h6" color="text.secondary" gutterBottom>
							{film.nameEn}
						</Typography>
					)}

					<Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
						{film.ratingKinopoisk != null && (
							<Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
								<Typography variant="h5" sx={{ fontWeight: 'bold' }}>
									{film.ratingKinopoisk}
								</Typography>
								<Rating value={film.ratingKinopoisk / 2} precision={0.1} readOnly />
							</Box>
						)}
						{averageRating > 0 && (
							<Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
								<Typography variant="body1" color="text.secondary">
									Наш: {averageRating.toFixed(1)}
								</Typography>
								<Typography variant="body2" color="text.secondary">
									({reviewCount} отз.)
								</Typography>
							</Box>
						)}
					</Box>

					<Box sx={{ display: 'flex', gap: 1, flexWrap: 'wrap', mb: 2 }}>
						{film.year && <Chip label={film.year} size="small" />}
						{film.filmLength && <Chip label={`${film.filmLength} мин.`} size="small" />}
						{film.ratingAgeLimits && <Chip label={film.ratingAgeLimits} size="small" />}
						{film.type && <Chip label={film.type} size="small" variant="outlined" />}
					</Box>

						{film.description && (
							<Typography variant="body1" sx={{ mb: 2 }}>
								{film.description}
							</Typography>
						)}

					{user && (
						<Button variant="contained" onClick={() => setReviewFormOpen(true)} sx={{ mt: 1 }}>
							Написать отзыв
						</Button>
					)}
				</Box>
			</Box>

			<Dialog open={reviewFormOpen} onClose={() => setReviewFormOpen(false)} maxWidth="sm" fullWidth>
				<DialogContent sx={{ maxHeight: '70vh', overflowY: 'auto' }}>
					<ReviewForm filmId={filmId} user={user!} onSaved={handleReviewSaved} onCancel={() => setReviewFormOpen(false)} />
				</DialogContent>
			</Dialog>

			<Box sx={{ mt: 4 }}>
				<Typography variant="h5" gutterBottom>
					Отзывы ({reviews.length})
				</Typography>
				<ReviewList reviews={reviews} currentUser={user} onRefresh={handleReviewSaved} />
			</Box>
		</Box>
	);
}
