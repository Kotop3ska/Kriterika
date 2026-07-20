import { useState, useEffect, useCallback } from 'react';
import type { User } from '../types';
import { getMe } from '../api/users';

export function useAuth() {
	const [user, setUser] = useState<User | null>(null);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const token = localStorage.getItem('token');
		if (token) {
			getMe()
				.then(setUser)
				.catch(() => {
					localStorage.removeItem('token');
				})
				.finally(() => setLoading(false));
		} else {
			setLoading(false);
		}
	}, []);

	const saveAuth = useCallback((token: string, userData: User) => {
		localStorage.setItem('token', token);
		setUser(userData);
	}, []);

	const logout = useCallback(() => {
		localStorage.removeItem('token');
		setUser(null);
	}, []);

	return { user, loading, saveAuth, logout, isAuthenticated: !!user };
}
