import { BrowserRouter, Routes, Route, useNavigate } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Button, Container, Box, CircularProgress } from '@mui/material';
import { useAuth } from './hooks/useAuth';
import ProtectedRoute from './components/ProtectedRoute';
import { SearchPage } from './pages/SearchPage';
import FilmDetail from './components/FilmDetail';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import ProfilePage from './pages/ProfilePage';
import NotFoundPage from './pages/NotFoundPage';

function NavBar({ user, onLogout }: { user: any; onLogout: () => void }) {
	const navigate = useNavigate();
	return (
		<AppBar position="static">
			<Toolbar>
				<Typography
					variant="h6"
					sx={{ flexGrow: 1, cursor: 'pointer' }}
					onClick={() => navigate('/')}
				>
					Kriterika
				</Typography>
				{user ? (
					<>
						<Button color="inherit" onClick={() => navigate('/profile')}>
							{user.username}
						</Button>
						<Button color="inherit" onClick={onLogout}>
							Выйти
						</Button>
					</>
				) : (
					<>
						<Button color="inherit" onClick={() => navigate('/login')}>
							Войти
						</Button>
						<Button color="inherit" onClick={() => navigate('/register')}>
							Регистрация
						</Button>
					</>
				)}
			</Toolbar>
		</AppBar>
	);
}

export default function App() {
	const { user, loading, saveAuth, logout, isAuthenticated } = useAuth();

	if (loading) {
		return (
			<Box sx={{ display: 'flex', justifyContent: 'center', py: 10 }}>
				<CircularProgress />
			</Box>
		);
	}

	return (
		<BrowserRouter>
			<NavBar user={user} onLogout={logout} />
			<Container maxWidth="lg">
				<Routes>
					<Route path="/" element={<SearchPage />} />
					<Route path="/film/:id" element={<FilmDetail user={user} />} />
					<Route path="/login" element={<LoginPage onLogin={saveAuth} />} />
					<Route path="/register" element={<RegisterPage onLogin={saveAuth} />} />
					<Route
						path="/profile"
						element={
							<ProtectedRoute isAuthenticated={isAuthenticated} loading={loading}>
								<ProfilePage user={user!} onLogout={logout} />
							</ProtectedRoute>
						}
					/>
					<Route path="*" element={<NotFoundPage />} />
				</Routes>
			</Container>
		</BrowserRouter>
	);
}
