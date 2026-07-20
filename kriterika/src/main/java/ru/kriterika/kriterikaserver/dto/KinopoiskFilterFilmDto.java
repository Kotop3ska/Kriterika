package ru.kriterika.kriterikaserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class KinopoiskFilterFilmDto {

	@JsonProperty("kinopoiskId")
	private Integer kinopoiskId;

	@JsonProperty("nameRu")
	private String nameRu;

	@JsonProperty("nameEn")
	private String nameEn;

	@JsonProperty("nameOriginal")
	private String nameOriginal;

	@JsonProperty("posterUrl")
	private String posterUrl;

	@JsonProperty("posterUrlPreview")
	private String posterUrlPreview;

	@JsonProperty("year")
	private Integer year;

	@JsonProperty("type")
	private String type;

	@JsonProperty("ratingKinopoisk")
	private Double ratingKinopoisk;

	@JsonProperty("genres")
	private List<KinopoiskFilmDto.GenreDto> genres;

	@JsonProperty("countries")
	private List<KinopoiskFilmDto.CountryDto> countries;

	public Integer getKinopoiskId() {
		return kinopoiskId;
	}

	public String getNameRu() {
		return nameRu;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getNameOriginal() {
		return nameOriginal;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public String getPosterUrlPreview() {
		return posterUrlPreview;
	}

	public Integer getYear() {
		return year;
	}

	public String getType() {
		return type;
	}

	public Double getRatingKinopoisk() {
		return ratingKinopoisk;
	}

	public List<KinopoiskFilmDto.GenreDto> getGenres() {
		return genres;
	}

	public List<KinopoiskFilmDto.CountryDto> getCountries() {
		return countries;
	}
}
