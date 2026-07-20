package ru.kriterika.kriterikaserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_genres")
@IdClass(FilmGenre.class)
public class FilmGenre {

	@Column(name = "film_id", nullable = false)
	@Id
	private Integer filmId;

	@Column(name = "genre_id", nullable = false)
	@Id
	private Integer genreId;

	public FilmGenre() {}

	public FilmGenre(Integer filmId, Integer genreId) {
		this.filmId = filmId;
		this.genreId = genreId;
	}

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
}
