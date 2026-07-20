export interface User {
	id: number;
	email: string;
	username: string;
	createdAt: string;
}

export interface Film {
	kinopoiskId: number;
	imdbId?: string;
	nameRu: string;
	nameEn?: string;
	nameOriginal?: string;
	posterUrl?: string;
	posterUrlPreview?: string;
	coverUrl?: string;
	logoUrl?: string;
	description?: string;
	shortDescription?: string;
	year?: number;
	filmLength?: number;
	type?: string;
	ratingKinopoisk?: number;
	ratingKinopoiskVoteCount?: number;
	ratingImdb?: number;
	ratingImdbVoteCount?: number;
	ratingAgeLimits?: string;
	slogan?: string;
	productionStatus?: string;
	webUrl?: string;
	genres?: Genre[];
	countries?: Country[];
}

export interface FilmSearch {
	kinopoiskId: number;
	nameRu: string;
	nameEn?: string;
	posterUrl?: string;
	posterUrlPreview?: string;
	year?: number;
	type?: string;
	ratingKinopoisk?: number;
}

export interface Genre {
	id: number;
	name: string;
}

export interface Country {
	id: number;
	name: string;
}

export interface Criterion {
	id: number;
	name: string;
}

export interface Review {
	id: number;
	userId: number;
	username?: string;
	filmId: number;
	rating: number;
	title?: string;
	body?: string;
	criteriaRatings?: CriteriaRating[];
	createdAt: string;
}

export interface CriteriaRating {
	criterionId: number;
	criterionName?: string;
	rating: number;
}

export interface PagedResponse<T> {
	content: T[];
	totalElements: number;
	totalPages: number;
	number: number;
	size: number;
}

export interface AuthResponse {
	user: User;
	token: string;
}
