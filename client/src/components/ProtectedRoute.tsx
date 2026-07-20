import { Navigate } from 'react-router-dom';
import type { ReactNode } from 'react';

interface Props {
	isAuthenticated: boolean;
	loading: boolean;
	children: ReactNode;
}

export default function ProtectedRoute({ isAuthenticated, loading, children }: Props) {
	if (loading) return null;
	if (!isAuthenticated) return <Navigate to="/login" replace />;
	return <>{children}</>;
}
