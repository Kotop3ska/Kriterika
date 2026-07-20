import api from './client';
import type { Review, CriteriaRating } from '../types';

interface ReviewRequest {
	filmId: number;
	rating: number;
	title?: string;
	body?: string;
	criteriaRatings?: CriteriaRating[];
}

export async function createReview(review: ReviewRequest): Promise<{ id: number }> {
	const { data } = await api.post('/reviews', review);
	return data;
}

export async function updateReview(
	reviewId: number,
	update: { rating?: number; title?: string; body?: string; criteriaRatings?: CriteriaRating[] }
): Promise<Review> {
	const { data } = await api.put<Review>(`/reviews/${reviewId}`, update);
	return data;
}

export async function deleteReview(reviewId: number): Promise<void> {
	await api.delete(`/reviews/${reviewId}`);
}

export async function getReviewsByUserId(userId: number): Promise<Review[]> {
	const { data } = await api.get<Review[]>(`/reviews/user/${userId}`);
	return data;
}
