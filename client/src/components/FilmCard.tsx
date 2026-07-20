import { Card, CardMedia, CardContent, Typography, Rating } from '@mui/material';
import { Link } from 'react-router-dom';
import type { FilmSearch } from '../types';

interface Props {
	film: FilmSearch;
}

export default function FilmCard({ film }: Props) {
	return (
		<Card
			component={Link}
			to={`/film/${film.kinopoiskId}`}
			sx={{
				height: '100%',
				textDecoration: 'none',
				transition: 'transform 0.2s',
				'&:hover': { transform: 'scale(1.02)' },
			}}
		>
			<CardMedia
				component="img"
				height="300"
				image={film.posterUrl || film.posterUrlPreview || 'https://via.placeholder.com/200x300?text=No+Poster'}
				alt={film.nameRu}
				sx={{ objectFit: 'cover' }}
			/>
			<CardContent>
				<Typography variant="subtitle1" sx={{ fontWeight: 'bold' }} noWrap>
					{film.nameRu}
				</Typography>
				<Typography variant="body2" color="text.secondary">
					{film.year || '—'}
					{film.type ? ` • ${film.type}` : ''}
				</Typography>
				{film.ratingKinopoisk != null && (
					<Rating value={film.ratingKinopoisk / 2} precision={0.1} readOnly size="small" sx={{ mt: 0.5 }} />
				)}
			</CardContent>
		</Card>
	);
}
