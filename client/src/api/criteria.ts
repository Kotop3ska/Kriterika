import api from './client';
import type { Criterion } from '../types';

export async function getCriteria(): Promise<Criterion[]> {
	const { data } = await api.get<Criterion[]>('/criteria');
	return data;
}
