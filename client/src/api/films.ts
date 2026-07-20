import api from './client';
import type { Film, FilmSearch, PagedResponse, Review } from '../types';

export async function searchFilms(
	keyword?: string,
	year?: number,
	genreId?: number,
	countryId?: number,
	page: number = 0,
	size: number = 20,
	sortBy: string = 'kinopoiskId',
	sortDir: string = 'asc'
): Promise<PagedResponse<FilmSearch>> {
	const params: Record<string, string | number> = { page, size, sortBy, sortDir };
	if (keyword) params.keyword = keyword;
	if (year) params.year = year;
	if (genreId) params.genreId = genreId;
	if (countryId) params.countryId = countryId;

	const { data } = await api.get<PagedResponse<FilmSearch>>('/films', { params });
	return data;
}

export async function getFilmById(id: number): Promise<{ film: Film; averageRating: number; reviewCount: number }> {
	const { data } = await api.get(`/films/${id}`);
	return data;
}

export async function getFilmReviews(filmId: number): Promise<Review[]> {
	const { data } = await api.get<Review[]>(`/films/${filmId}/reviews`);
	return data;
}
