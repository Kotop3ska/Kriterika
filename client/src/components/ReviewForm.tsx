import { useState, useEffect } from 'react';
import {
	Box,
	TextField,
	Typography,
	Slider,
	Button,
	Card,
	CardContent,
} from '@mui/material';
import type { User, CriteriaRating, Criterion } from '../types';
import { getCriteria } from '../api/criteria';
import { createReview } from '../api/reviews';

interface Props {
	filmId: number;
	user: User;
	onSaved: () => void;
	onCancel: () => void;
}

export default function ReviewForm({ filmId, onSaved, onCancel }: Props) {
	const [rating, setRating] = useState(8);
	const [title, setTitle] = useState('');
	const [body, setBody] = useState('');
	const [criteria, setCriteria] = useState<Criterion[]>([]);
	const [criteriaValues, setCriteriaValues] = useState<Record<number, number>>({});
	const [submitting, setSubmitting] = useState(false);

	useEffect(() => {
		getCriteria().then((c) => {
			setCriteria(c);
			const initial: Record<number, number> = {};
			c.forEach((cr) => (initial[cr.id] = 8));
			setCriteriaValues(initial);
		});
	}, []);

	const handleSubmit = async () => {
		setSubmitting(true);
		try {
			const criteriaRatings: CriteriaRating[] = Object.entries(criteriaValues).map(([id, val]) => ({
				criterionId: Number(id),
				rating: val,
			}));
			await createReview({ filmId, rating, title, body, criteriaRatings });
			onSaved();
		} finally {
			setSubmitting(false);
		}
	};

	return (
		<Card>
			<CardContent>
				<Typography variant="h6" gutterBottom>
					Ваш отзыв
				</Typography>

				<Typography gutterBottom>Общая оценка: {rating}/10</Typography>
				<Slider
					value={rating}
					onChange={(_, v) => setRating(v as number)}
					min={1}
					max={10}
					step={1}
					marks
					valueLabelDisplay="auto"
					sx={{ mb: 2 }}
				/>

				<TextField
					fullWidth
					label="Заголовок"
					value={title}
					onChange={(e) => setTitle(e.target.value)}
					sx={{ mb: 2 }}
				/>

				<TextField
					fullWidth
					multiline
					rows={3}
					label="Текст отзыва"
					value={body}
					onChange={(e) => setBody(e.target.value)}
					sx={{ mb: 3 }}
				/>

				{criteria.map((cr) => (
					<Box key={cr.id} sx={{ mb: 1 }}>
						<Typography variant="body2" color="text.secondary">
							{cr.name}: {criteriaValues[cr.id] || 8}/10
						</Typography>
						<Slider
							value={criteriaValues[cr.id] || 8}
							onChange={(_, v) => setCriteriaValues((prev) => ({ ...prev, [cr.id]: v as number }))}
							min={1}
							max={10}
							step={1}
							size="small"
							valueLabelDisplay="auto"
						/>
					</Box>
				))}

				<Box sx={{ display: 'flex', gap: 1, mt: 2 }}>
					<Button variant="contained" onClick={handleSubmit} disabled={submitting}>
						{submitting ? 'Отправка...' : 'Отправить'}
					</Button>
					<Button onClick={onCancel}>Отмена</Button>
				</Box>
			</CardContent>
		</Card>
	);
}
