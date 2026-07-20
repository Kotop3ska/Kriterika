import api from './client';
import type { Genre } from '../types';

export async function getGenres(): Promise<Genre[]> {
	const { data } = await api.get<Genre[]>('/genres');
	return data;
}
