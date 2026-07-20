import { useState, useEffect } from 'react';
import {
	Box,
	Typography,
	Select,
	MenuItem,
	FormControl,
	InputLabel,
	TextField,
	Pagination,
	Grid,
	CircularProgress,
	Button,
} from '@mui/material';
import type { FilmSearch, Genre, Country } from '../types';
import { searchFilms } from '../api/films';
import { getGenres } from '../api/genres';
import { getCountries } from '../api/countries';
import FilmCard from '../components/FilmCard';

export function SearchPage() {
	const [keyword, setKeyword] = useState('');
	const [films, setFilms] = useState<FilmSearch[]>([]);
	const [genres, setGenres] = useState<Genre[]>([]);
	const [countries, setCountries] = useState<Country[]>([]);
	const [selectedGenre, setSelectedGenre] = useState<number | ''>('');
	const [selectedCountry, setSelectedCountry] = useState<number | ''>('');
	const [page, setPage] = useState(0);
	const [totalPages, setTotalPages] = useState(1);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		Promise.all([getGenres(), getCountries()]).then(([g, c]) => {
			setGenres(g);
			setCountries(c);
		});
	}, []);

	useEffect(() => {
		setLoading(true);
		searchFilms(
			keyword || undefined,
			undefined,
			selectedGenre || undefined,
			selectedCountry || undefined,
			page,
			12
		)
			.then((res) => {
				setFilms(res.content);
				setTotalPages(res.totalPages);
			})
			.finally(() => setLoading(false));
	}, [keyword, selectedGenre, selectedCountry, page]);

	const handleSearch = () => {
		setPage(0);
	};

	return (
		<Box sx={{ py: 3 }}>
			<Typography variant="h4" gutterBottom>
				Поиск фильмов
			</Typography>

			<Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
				<TextField
					label="Поиск по названию"
					value={keyword}
					onChange={(e) => setKeyword(e.target.value)}
					onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
					sx={{ minWidth: 250 }}
					size="small"
				/>
				<FormControl size="small" sx={{ minWidth: 150 }}>
					<InputLabel>Жанр</InputLabel>
					<Select
						value={selectedGenre}
						label="Жанр"
						onChange={(e) => {
							setSelectedGenre(e.target.value as number | '');
							setPage(0);
						}}
					>
						<MenuItem value="">Все</MenuItem>
						{genres.map((g) => (
							<MenuItem key={g.id} value={g.id}>
								{g.name}
							</MenuItem>
						))}
					</Select>
				</FormControl>
				<FormControl size="small" sx={{ minWidth: 150 }}>
					<InputLabel>Страна</InputLabel>
					<Select
						value={selectedCountry}
						label="Страна"
						onChange={(e) => {
							setSelectedCountry(e.target.value as number | '');
							setPage(0);
						}}
					>
						<MenuItem value="">Все</MenuItem>
						{countries.map((c) => (
							<MenuItem key={c.id} value={c.id}>
								{c.name}
							</MenuItem>
						))}
					</Select>
				</FormControl>
				<Button variant="contained" onClick={handleSearch}>
					Найти
				</Button>
			</Box>

			{loading ? (
				<Box sx={{ display: 'flex', justifyContent: 'center', py: 5 }}>
					<CircularProgress />
				</Box>
			) : films.length === 0 ? (
				<Typography color="text.secondary" sx={{ py: 3 }}>
					Фильмы не найдены
				</Typography>
			) : (
				<>
					<Grid container spacing={2}>
						{films.map((film) => (
							<Grid key={film.kinopoiskId} size={{ xs: 12, sm: 6, md: 4, lg: 3 }}>
								<FilmCard film={film} />
							</Grid>
						))}
					</Grid>
					{totalPages > 1 && (
						<Box sx={{ display: 'flex', justifyContent: 'center', py: 3 }}>
							<Pagination
								count={totalPages}
								page={page + 1}
								onChange={(_, p) => setPage(p - 1)}
								color="primary"
							/>
						</Box>
					)}
				</>
			)}
		</Box>
	);
}
