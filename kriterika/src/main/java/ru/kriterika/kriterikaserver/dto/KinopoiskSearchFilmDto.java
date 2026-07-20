package ru.kriterika.kriterikaserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class KinopoiskSearchFilmDto {

	@JsonProperty("filmId")
	private Integer filmId;

	@JsonProperty("nameRu")
	private String nameRu;

	@JsonProperty("nameEn")
	private String nameEn;

	@JsonProperty("type")
	private String type;

	@JsonProperty("year")
	private String year;

	@JsonProperty("description")
	private String description;

	@JsonProperty("filmLength")
	private String filmLength;

	@JsonProperty("rating")
	private String rating;

	@JsonProperty("ratingVoteCount")
	private Integer ratingVoteCount;

	@JsonProperty("posterUrl")
	private String posterUrl;

	@JsonProperty("posterUrlPreview")
	private String posterUrlPreview;

	@JsonProperty("genres")
	private List<KinopoiskFilmDto.GenreDto> genres;

	@JsonProperty("countries")
	private List<KinopoiskFilmDto.CountryDto> countries;

	public Integer getFilmId() {
		return filmId;
	}

	public String getNameRu() {
		return nameRu;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getType() {
		return type;
	}

	public String getYear() {
		return year;
	}

	public String getDescription() {
		return description;
	}

	public String getFilmLength() {
		return filmLength;
	}

	public String getRating() {
		return rating;
	}

	public Integer getRatingVoteCount() {
		return ratingVoteCount;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public String getPosterUrlPreview() {
		return posterUrlPreview;
	}

	public List<KinopoiskFilmDto.GenreDto> getGenres() {
		return genres;
	}

	public List<KinopoiskFilmDto.CountryDto> getCountries() {
		return countries;
	}
}
