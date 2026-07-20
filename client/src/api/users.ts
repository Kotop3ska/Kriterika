import api from './client';
import type { User } from '../types';

export async function getMe(): Promise<User> {
	const { data } = await api.get<User>('/users/me');
	return data;
}

export async function updateMe(updates: { email?: string; username?: string }): Promise<User> {
	const { data } = await api.put<User>('/users/me', updates);
	return data;
}
