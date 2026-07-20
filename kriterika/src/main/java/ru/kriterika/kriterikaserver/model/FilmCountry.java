package ru.kriterika.kriterikaserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "film_countries")
@IdClass(FilmCountry.class)
public class FilmCountry {

	@Column(name = "film_id", nullable = false)
	@Id
	private Integer filmId;

	@Column(name = "country_id", nullable = false)
	@Id
	private Integer countryId;

	public FilmCountry() {}

	public FilmCountry(Integer filmId, Integer countryId) {
		this.filmId = filmId;
		this.countryId = countryId;
	}

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
}
