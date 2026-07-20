import api from './client';
import type { Country } from '../types';

export async function getCountries(): Promise<Country[]> {
	const { data } = await api.get<Country[]>('/countries');
	return data;
}
